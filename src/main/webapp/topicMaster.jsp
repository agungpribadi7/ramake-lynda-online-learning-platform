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
		ArrayList<topicForAdmin> topicList = new ArrayList<>();
	%>
	<% 
		topicList.clear();
		topicList = meOnly.getAllTopics();
		request.setAttribute("topicList",topicList);
	%>
	<center>
	<button class="btn btn-success" 
		data-toggle="modal" 
		data-target="#newtopic">
		Add new Topic
	</button></center>
	<div class='table-responsive'>
    	<table class='table table-striped table-hover table-condensed'>	
			<tr>
				<th>Topic ID</th>
				<th>Topic Name</th>
				<th>Action</th>
			</tr>
			<c:forEach items="${topicList}" var="topic">
				<tr>
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
            			<h4 class='modal-title'>Edit Topic's Title</h4>
            		</div>
            		<div class='modal-body'>
            			<input type='text' style='width:100%' class='topicName' placeholder="Name here...">
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
           			<h4 class='modal-title'>Insert New Topic</h4>
           		</div>
           		<div class='modal-body'>
					Topic : <input type='text' style='width:80%' id='subtopicName' placeholder="Name here...">
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
	var name = "";
	
	$(".topicName").each(function(){
      if($(this).val() != ""){
        name = $(this).val();
      }
    });
	
	
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
			  data: {type:"changeTopic",id:id,name:name},
			  success: function(r){         
				swal({
				    title: "Name Changed",
				    icon: "success",
				});
				$('main').load('topicMaster.jsp');
			  }
			});   
	}
});

$(".addNew").click(function(e){
	let id = -1;
	let name = $("#subtopicName").val();
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
			  data: {type:"addTopic",id:id,name:name},
			  success: function(r){         
				swal({
				    title: "Topic Added",
				    icon: "success",
				});
				$('main').load('topicMaster.jsp');
			  }
			});   
	}
});
</script>

</html>