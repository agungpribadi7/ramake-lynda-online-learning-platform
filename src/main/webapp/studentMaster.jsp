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

<%@ page import="edu.stts.studentForAdmin" %>
<%@ page import="edu.stts.adminFunctions" %>
<%@ page import="edu.stts.StorageClass" %>

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
		ArrayList<studentForAdmin> studentList = new ArrayList<>();
		StorageClass storage = new StorageClass();
	%>
	<% 
		studentList.clear();
		studentList = meOnly.getAllStudents();
		request.setAttribute("studentList",studentList);
	%>
	<div class='table-responsive'>
    	<table class='table table-striped table-hover table-condensed'>	
			<tr>
				<th>Student's ID</th>
				<th>Student's Name</th>
				<th>Detail</th>
				<th>Action</th>
			</tr>
			<c:forEach items="${studentList}" var="student">
				<tr>
					<td><c:out value="${student.id}" /></td>
					<td><c:out value="${student.name}" /></td>
					<td>		
						<button class="btn btn-info" 
							data-toggle="modal" 
							data-id="<c:out value='${student.id}' />"
							data-target="#detail<c:out value='${student.id}' />">
							Detail
						</button>
					</td>

					<c:choose>
						<c:when test="${student.banned == 0}">
							<td>
								<button type='button' 
									class='btn btn-danger btnBan' 
									data-id="<c:out value='${student.id}' />">Ban</button>
							</td>
						</c:when>
						<c:when test="${student.banned == 1 }">
							<td><button type='button' 
							class='btn btn-warning btnBan'
							data-id="<c:out value='${student.id}' />">Lift Ban</button></td>
						</c:when>
					</c:choose>
				</tr>
			</c:forEach>
		</table>
	</div>
	<% 
		ArrayList<String> imgList = new ArrayList<>();
		for(int i = 0; i < studentList.size(); i++){
			imgList.add(studentList.get(i).getPhoto().substring(64));
		}
		ArrayList<String> imageList = storage.getImageBatch("foto", imgList);
		request.setAttribute("photoList",imageList);
	%>
		<c:forEach items="${studentList}" var="student">
			<c:set var="oy" value='false' />
			<div id="detail<c:out value='${student.id}' />" class='modal fade' role='dialog'>
				<div class='modal-dialog'>
					<div class='modal-content'>
						<div class='modal-header'>
	            			<h4 class='modal-title'>Student Data</h4>
	            		</div>
	            		<div class='modal-body'>
	            			<center>
			            		<c:forEach items='${photoList}' var='currentPic' varStatus='loop'>
			            			<c:if test="${oy == 'false'}">
			            				<c:if test="${currentPic eq student.photo}">
			            					<img src='${currentPic}' style='width:150px;height:150px;border-radius:50%'>
			            					<c:set var="oy" value='true' />
			            				</c:if>
		            				</c:if>
		            	 		</c:forEach>
	            			</center>
	            			Student's ID       : <c:out value="${student.id}" /><br>
		            		Student's Name     : <c:out value="${student.name}" /><br>
		            		Student's Email    : <c:out value="${student.email}" /> <br>
		            		Student's Balance  : IDR <c:out value="${student.wallet }" /> <br>
	            			<c:choose>
	            				<c:when test="${student.verification == 1}">
	            					Verification Status : <span style='color:green'>Completed</span><br>
	            				</c:when>
	            				<c:otherwise>
	            					Verification Status : <span style='color:red'>Verification Incomplete</span><br>
	            				</c:otherwise>
	            			</c:choose>
	            			<c:choose>
	            				<c:when test="${student.banned == 0}">
	            					Ban Status          : <span class='bannedSpan' data-id="<c:out value='${student.id }' />" style='color:green'>Clean</span><br>
	            				</c:when>
	            				<c:otherwise>
	            					Ban Status          : <span class='bannedSpan' data-id="<c:out value='${student.id }' />" style='color:red'>Banned</span><br>
	            				</c:otherwise>
	            			</c:choose>
	            			
			            </div>
			            <div class='modal-footer'>
			            	<button type='button' class='btn btn-default' data-dismiss='modal'>Close</button>
			            </div>
			        </div>
			    </div>
			</div>
		</c:forEach>

</body>

<script>
$(".btnBan").click(function(e){
	if($(this).html() == "Ban"){
	    swal({
	      title: "Ban Confirmation",
	      text: "Do you want to ban this User?",
	      icon: "warning",
	      buttons: true,
	      dangerMode: true,
	    })
	    .then((willDelete) => {
	      if (willDelete) {
	        let id = $(this).data("id");
	        $.ajax({
	          method: "POST",
	          url:"adminAjax",
	          data: {type:"banStudent",id:id},
	          success: function(r){         
		       	swal({
		       	    title: "Ban Successful",
		       	    icon: "success",
		       	});
		    	$('main').load('studentMaster.jsp');
	          }
	        });        
	      } else {
	    	  swal({
	    	      title: "Canceled",
	    	      icon: "error",
	    	  })
	      }
	    });
	}
	else if($(this).html() == "Lift Ban"){
		swal({
	      title: "Lift Ban Confirmation",
	      text: "Do you want to Lift the ban in this User?",
	      icon: "warning",
	      buttons: true,
	      dangerMode: true,
	    })
	    .then((willDelete) => {
	      if (willDelete) {
	        let id = $(this).data("id");
	        $.ajax({
	          method: "POST",
	          url:"adminAjax",
	          data: {type:"unbanStudent",id:id},
	          success: function(r){         
		       	swal({
		       	    title: "Ban Lifted",
		       	    icon: "success",
		       	});
		       	$('main').load('studentMaster.jsp');
	          }
	        });        
	      } else {
	    	  swal({
	    	      title: "Canceled",
	    	      icon: "error",
	    	  })
	      }
	    });
	}
});

</script>
</html>