<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ page import="java.io.FileInputStream" %>
<%@ page import="java.io.IOException" %>
<%@ page import="java.io.PrintWriter" %>

<%@ page import ="com.google.api.core.ApiFuture" %>
<%@ page import ="com.google.auth.oauth2.ServiceAccountCredentials" %>
<%@ page import ="com.google.cloud.firestore.CollectionReference" %>
<%@ page import ="com.google.cloud.firestore.DocumentSnapshot" %>
<%@ page import ="com.google.cloud.firestore.Firestore" %>
<%@ page import ="com.google.cloud.firestore.FirestoreOptions" %>
<%@ page import ="com.google.cloud.firestore.Query" %>
<%@ page import ="com.google.cloud.firestore.QueryDocumentSnapshot" %>
<%@ page import ="com.google.cloud.firestore.QuerySnapshot" %>
<%@ page import ="com.google.cloud.firestore.WriteResult" %>

<%@ page import="edu.stts.subtopicForAdmin" %>
<%@ page import="edu.stts.topicForAdmin" %>
<%@ page import="edu.stts.adminFunctions" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
  	<script src='http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js'></script>
  	<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
 	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js" integrity="sha384-b/U6ypiBEHpOf/4+1nzFpr53nxSS+GLCkfwBdFNTxtclqqenISfwAzpKaMNFNmj4" crossorigin="anonymous"></script>
  	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/js/bootstrap.min.js" integrity="sha384-h0AbiXch4ZDo7tp9hKZ4TsHbi047NrKGLO3SEJAg45jXxnGIfYzk4Si90RDIqNm1" crossorigin="anonymous"></script>
</head>
<body>
	
	<%!
		private Firestore db;
		adminFunctions meOnly = new adminFunctions();
		ArrayList<subtopicForAdmin> topicList = new ArrayList<>();
		ArrayList<topicForAdmin> hajimeyou = new ArrayList<>();
		ArrayList<String> headerList = new ArrayList<>();
	%>
	<% 
		topicList.clear();
		topicList = meOnly.getAllSubtopics();
		request.setAttribute("topicList",topicList);
		hajimeyou.clear();
		hajimeyou = meOnly.getAllTopics();
		headerList.clear();
		for(topicForAdmin asd : hajimeyou){
			headerList.add(asd.getName());
		}
		request.setAttribute("headerList",headerList);
	%>
	<center>
	<button class="btn btn-success" 
		data-toggle="modal" 
		data-target="#newtopic">
		Add new Subtopic
	</button></center>
	<div class='table-responsive'>
    	<table class='table table-striped table-hover table-condensed'>	
			<tr>
				<th>Subtopic's Parent</th>
				<th>Subtopic ID</th>
				<th>Subtopic Name</th>
				<th>Action</th>
			</tr>
			<c:forEach items="${topicList}" var="topic">
				<tr>
					<td><c:out value="${topic.idtopic}" /></td>
					<td><c:out value="${topic.id}" /></td>
					<td><c:out value="${topic.name}" /></td>
					<td>		
						<button class="btn btn-info" 
							data-toggle="modal" 
							data-id="<c:out value='${topic.id}' />"
							data-target="#detail<c:out value='${topic.id}' />">
							Edit
						</button>
					</td>
				</tr>
			</c:forEach>
		</table>
	</div>
	<c:forEach items="${topicList}" var="topic">
		<div id="detail<c:out value='${topic.id}' />" class='modal fade' role='dialog'>
			<div class='modal-dialog'>
				<div class='modal-content'>
					<div class='modal-header'>
            			<h4 class='modal-title'>Edit Subtopic's Title</h4>
            		</div>
            		<div class='modal-body'>
            			<input type='text' style='width:100%' class='topicName' data-id="<c:out value='${topic.id}' />" placeholder="Name here..." >
		            </div>
		            <div class='modal-footer'>
		            	<button type='button' class='btn btn-info changeName' data-id="<c:out value='${topic.id}' />" data-dismiss='modal'>Save</button>
		            	<button type='button' class='btn btn-default' data-dismiss='modal'>Close</button>
		            </div>
		        </div>
		    </div>
		</div>
	</c:forEach>
	<div id="newtopic" class='modal fade' role='dialog'>
		<div class='modal-dialog'>
			<div class='modal-content'>
				<div class='modal-header'>
           			<h4 class='modal-title'>Insert New Subtopic</h4>
           		</div>
           		<div class='modal-body'>
           			Header : 
           			<select id="headSelect">
           				<c:forEach items="${headerList}" var="head">
						  	<option value="${head}">${head}</option>
					  	</c:forEach>
					</select> <br>
					Title : <input type='text' style='width:80%' id='subtopicName' placeholder="Name here...">
	            </div>
	            <div class='modal-footer'>
	            	<button type='button' class='btn btn-info addNew' data-dismiss='modal'>Save</button>
	            	<button type='button' class='btn btn-default' data-dismiss='modal'>Close</button>
				</div>
			</div>
		</div>
	</div>

</body>
<script>
$(".changeName").click(function(e){
	
	let id = $(this).data("id");
	let name = "";
	$(".topicName").each(function(){
      if($(this).val() != ""){
        name = $(this).val();
      }
    });
	alert(name);

	if(name == ''){
		swal({
		    title: "Name Is Empty",
		    icon: "error",
		});
	}
	else{
		$.ajax({
		  method: "POST",
		  url:"adminAjax",
		  data: {type:"changeSubtopic",id:id,name:name},
		  success: function(r){         
			swal({
			    title: "Name Changed",
			    icon: "success",
			});
			$('main').load('subtopicMaster.jsp');
		  }
		});   
	}
});

$(".addNew").click(function(e){
	let header = $("#headSelect option:selected").text();
	let id = -1;
	let name = $("#subtopicName").val();
	if(name == '' || header == ''){
		swal({
		    title: "Name Is Empty",
		    icon: "error",
		});
	}
	else{
		$.ajax({
			  method: "POST",
			  url:"adminAjax",
			  data: {type:"addSubtopic",id:id,name:name,header:header},
			  success: function(r){         
				swal({
				    title: "Subtopic Added",
				    icon: "success",
				});
				$('main').load('subtopicMaster.jsp');
			  }
			});   
	}
});
</script>

</html>