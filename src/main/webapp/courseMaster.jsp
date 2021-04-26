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

<%@ page import="edu.stts.courseForAdmin" %>
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
		ArrayList<courseForAdmin> courseList = new ArrayList<>();
		StorageClass storage = new StorageClass();
	%>
	<% 
		courseList.clear();
		courseList = meOnly.getAllCourse();
		request.setAttribute("courseList",courseList);
	%>
	<div class='table-responsive'>
    	<table class='table table-striped table-hover table-condensed'>	
			<tr>
				<th>Course's ID</th>
				<th>Course's Title</th>
				<th>Detail</th>
				<th>Action</th>
			</tr>
			<c:forEach items="${courseList}" var="course">
				<tr>
					<td><c:out value="${course.id}" /></td>
					<td><c:out value="${course.judul}" /></td>
					<td>		
						<button class="btn btn-info" 
							data-toggle="modal" 
							data-id="<c:out value='${course.id}' />"
							data-target="#detail<c:out value='${course.id}' />">
							Detail
						</button>
					</td>

					<c:choose>
						<c:when test="${course.ban == 0}">
							<td>
								<button type='button' 
									class='btn btn-danger btnBan' 
									data-id="<c:out value='${course.id}' />">Ban</button>

							</td>
						</c:when>
						<c:when test="${course.ban == 1 }">
							<td><button type='button' 
								class='btn btn-warning btnBan'
								data-id="<c:out value='${course.id}' />">Lift Ban</button></td>
						</c:when>
					</c:choose>
				</tr>
			</c:forEach>
		</table>
	</div>
	<% 
		ArrayList<String> videoList = new ArrayList<>();
		for(int i = 0; i < courseList.size(); i++){
			videoList.add(courseList.get(i).getVideo().substring(65));
		}
		
		ArrayList<String> vidList = storage.getImageBatch("video", videoList);
		request.setAttribute("vidList",vidList);
	%>
		<c:forEach items="${courseList}" var="course">
			<c:set var="oy" value='false' />
			<div id="detail<c:out value='${course.id}' />" class='modal fade' role='dialog'>
				<div class='modal-dialog'>
					<div class='modal-content'>
						<div class='modal-header'>
	            			<h4 class='modal-title'><c:out value="${course.judul}" /></h4>
	            		</div>
	            		<div class='modal-body'>
	            			Course ID       : <c:out value="${course.id}" /><br>
	            			Course Owner    : <c:out value="${course.instructor}" /><br>
	            			Course Topic    : <c:out value="${course.topic}" /><br>
	            			Course Sub-Topic    : <c:out value="${course.subtopic}" /><br>
		            		Course Release Date    : <c:out value="${course.released}" /> <br>
		            		Course Viewer    : <c:out value="${course.viewer}" /><br>
		            		Course Likes     : <c:out value="${course.like}" /><br>
		            		Description           : 
	            			<c:choose>
	            				<c:when test="${course.desc == ''}">
	            					<textarea class='adminnote' style='width:100%; height:100px' disabled>No Description</textarea>
	            				</c:when>
	            				<c:otherwise>
	            					<textarea class='adminnote' style='width:100%; height:100px' disabled><c:out value="${course.desc}"/></textarea>
	            				</c:otherwise>
	            			</c:choose>
	            			<c:choose>
	            				<c:when test="${course.free == 0}">
	            					<span style='font-style:italic; color:blue'>This Course is for Premium Users Only</span><br>
	            				</c:when>
	            				<c:otherwise>
	            					<span style='font-style:italic; color:brown'>This Course is Available to All Users</span><br>
	            				</c:otherwise>
	            			</c:choose>
	            			<c:choose>
	            				<c:when test="${course.ban == 0}">
	            					Ban Status          : <span style='color:green'>Available</span><br>
	            				</c:when>
	            				<c:otherwise>
	            					Ban Status          : <span style='color:red'>Banned</span><br>
	            				</c:otherwise>
	            			</c:choose>
	            			<center>
			            		<c:forEach items='${vidList}' var='vid' varStatus='loop'>
			            			<c:if test="${oy == 'false'}">
			            				<c:if test="${vid eq course.video}">
		            						<video width="470" height="255" poster="${course.thumbnail}" controls>
											    <source src="${vid}" type="video/mp4">
											</video>
			            					<c:set var="oy" value='true' />
			            				</c:if>
		            				</c:if>
		            	 		</c:forEach>
	            			</center>
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
	      text: "Do you want to ban this Course?",
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
	          data: {type:"banCourse",id:id},
	          success: function(r){         
		       	swal({
		       	    title: "Ban Successful",
		       	    icon: "success",
		       	});
		       $('main').load('courseMaster.jsp');
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
	      text: "Do you want to Lift the ban in this Course?",
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
	          data: {type:"unbanCourse",id:id},
	          success: function(r){         
		       	swal({
		       	    title: "Ban Lifted",
		       	    icon: "success",
		       	});
		       	$('main').load('courseMaster.jsp');
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