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

<%@ page import="edu.stts.instructorForAdmin" %>
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
		ArrayList<instructorForAdmin> instructorList = new ArrayList<>();
		StorageClass storage = new StorageClass();
	%>
	<% 
		instructorList.clear();
		instructorList = meOnly.getAllInstructors();
		request.setAttribute("instructorList",instructorList);
	%>
	<div class='table-responsive'>
    	<table class='table table-striped table-hover table-condensed'>	
			<tr>
				<th>Instructor's ID</th>
				<th>Instructor's Name</th>
				<th>Detail</th>
				<th>Action</th>
			</tr>
			<c:forEach items="${instructorList}" var="instructor">
				<tr>
					<td><c:out value="${instructor.id}" /></td>
					<td><c:out value="${instructor.name}" /></td>
					<td>		
						<button class="btn btn-info" 
							data-toggle="modal" 
							data-id="<c:out value='${instructor.id}' />"
							data-target="#detail<c:out value='${instructor.id}' />">
							Detail
						</button>
					</td>

					<c:choose>
						<c:when test="${instructor.banned == 0}">
							<td>
								<button type='button' 
									class='btn btn-danger btnBan' 
									data-id="<c:out value='${instructor.id}' />">Ban</button>

							</td>
						</c:when>
						<c:when test="${instructor.banned == 1 }">
							<td><button type='button' 
								class='btn btn-warning btnBan'
								data-id="<c:out value='${instructor.id}' />">Lift Ban</button></td>
						</c:when>
					</c:choose>
				</tr>
			</c:forEach>
		</table>
	</div>
	<% 
		ArrayList<String> imgList = new ArrayList<>();
		for(int i = 0; i < instructorList.size(); i++){
			imgList.add(instructorList.get(i).getPhoto().substring(64));
		}
		
		ArrayList<String> imageList = storage.getImageBatch("foto", imgList);
		request.setAttribute("photoList",imageList);
	%>
		<c:forEach items="${instructorList}" var="instructor">
			<c:set var="oy" value='false' />
			<div id="detail<c:out value='${instructor.id}' />" class='modal fade' role='dialog'>
				<div class='modal-dialog'>
					<div class='modal-content'>
						<div class='modal-header'>
	            			<h4 class='modal-title'>Instructor Data</h4>
	            		</div>
	            		<div class='modal-body'>
	            			<center>
			            		<c:forEach items='${photoList}' var='currentPic' varStatus='loop'>
			            			<c:if test="${oy == 'false'}">
			            				<c:if test="${currentPic eq instructor.photo}">
		            						<img src='${currentPic}' style='width:150px;height:150px;border-radius:50%'>
			            					<c:set var="oy" value='true' />
			            				</c:if>
		            				</c:if>
		            	 		</c:forEach>
	            			</center>
	            			<c:choose>
	            				<c:when test="${instructor.accepted == 1}">
	            					Instructor Status   : <span style='color:green'>Certified Instructor</span><br>
	            				</c:when>
	            				<c:when test="${instructor.accepted == 2}">
	            					Instructor Status   : <span style='color:red'>Request Disapproved</span><br>
	            				</c:when>
	            				<c:otherwise>
	            					Instructor's Status   : <span style='color:gray'>Uncertified Instructor</span><br>
	            				</c:otherwise>
	            			</c:choose>
	            			Instructor's ID       : <c:out value="${instructor.id}" /><br>
		            		Instructor's Name     : <c:out value="${instructor.name}" /><br>
		            		Instructor's Email    : <c:out value="${instructor.email}" /> <br>
		            		Description           : 
	            			<c:choose>
	            				<c:when test="${instructor.description == ''}">
	            					<textarea class='adminnote' style='width:100%; height:100px' disabled>No Description</textarea>
	            				</c:when>
	            				<c:otherwise>
	            					<textarea class='adminnote' style='width:100%; height:100px' disabled><c:out value="${instructor.description}"/></textarea>
	            				</c:otherwise>
	            			</c:choose>
	            			<c:choose>
	            				<c:when test="${instructor.intro == ''}">
	            					Intro: <span style='font-style:italic; color:gray'>This Instructor Hasn't Set It's Intro Yet</span><br>
	            				</c:when>
	            				<c:otherwise>
	            					Intro: <c:out value="${instructor.intro}" /><br>
	            				</c:otherwise>
	            			</c:choose>
	            			<c:choose>
	            				<c:when test="${instructor.verification == 1}">
	            					Verification Status : <span style='color:green'>Completed</span><br>
	            				</c:when>
	            				<c:otherwise>
	            					Verification Status : <span style='color:red'>Verification Incomplete</span><br>
	            				</c:otherwise>
	            			</c:choose>
	            			<c:choose>
	            				<c:when test="${instructor.banned == 0}">
	            					Ban Status          : <span class='bannedSpan' data-id="<c:out value='${instructor.id }' />" style='color:green'>Clean</span><br>
	            				</c:when>
	            				<c:otherwise>
	            					Ban Status          : <span class='bannedSpan' data-id="<c:out value='${instructor.id }' />" style='color:red'>Banned</span><br>
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
	      text: "Do you want to ban this Instructor?",
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
	          data: {type:"banInstructor",id:id},
	          success: function(r){         
		       	swal({
		       	    title: "Ban Successful",
		       	    icon: "success",
		       	});
		       $('main').load('instructorMaster.jsp');
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
	      text: "Do you want to Lift the ban in this Instructor?",
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
	          data: {type:"unbanInstructor",id:id},
	          success: function(r){         
		       	swal({
		       	    title: "Ban Lifted",
		       	    icon: "success",
		       	});
		       	$('main').load('instructorMaster.jsp');
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