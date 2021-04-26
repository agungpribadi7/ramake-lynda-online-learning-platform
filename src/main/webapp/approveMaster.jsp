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
		ArrayList<instructorForAdmin> instructorListAll= new ArrayList<>();
		StorageClass storage = new StorageClass();
		ArrayList<instructorForAdmin> instructorList = new ArrayList<>();
	%>
	<% 
		instructorListAll.clear();
		instructorListAll = meOnly.getAllInstructors();
		instructorList.clear();
		for(int i = 0 ; i<instructorListAll.size() ; i++){
			if(instructorListAll.get(i).getAccepted() == 0)
				instructorList.add(instructorListAll.get(i));
		}
		request.setAttribute("instructorList",instructorList);
	%>
	<div class='table-responsive'>
    	<table class='table table-striped table-hover table-condensed'>	
			<tr>
				<th>Instructor's ID</th>
				<th>Instructor's Name</th>
				<th>Action</th>
			</tr>
			<c:forEach items="${instructorList}" var="instructor">
				<tr>
					<td><c:out value="${instructor.id}" /></td>
					<td><c:out value="${instructor.name}" /></td>
					<td>		
						<button 
							class="btn btn-info approve" 
							data-id="<c:out value='${instructor.id}' />">
							Approve
						</button>
						<button 
							class="btn btn-danger disapprove"  
							data-id="<c:out value='${instructor.id}' />">
							Disapprove
						</button>
					</td>
				</tr>
			</c:forEach>
		</table>
	</div>
</body>

<script>

$(".approve").click(function(e){
    swal({
      title: "Approval Confirmation",
      text: "Do you want to approve this User to be an Instructor?",
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
          data: {type:"approveInstructor",id:id},
          success: function(r){         
	       	swal({
	       	    title: "Instructor License Approved",
	       	    icon: "success",
	       	});
	       	$("main").load('approveMaster.jsp');
          }
        });        
      } else {
    	  swal({
    	      title: "Canceled",
    	      icon: "error",
    	  })
      }
    });
  });
  
$(".disapprove").click(function(e){
    swal({
      title: "Approval Confirmation",
      text: "Reject This Instructor's License?",
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
          data: {type:"disapproveInstructor",id:id},
          success: function(r){         
	       	swal({
	       	    title: "Instructor Rejected",
	       	    icon: "success",
	       	});
	       	$("main").load('approveMaster.jsp');
          }
        });        
      } else {
    	  swal({
    	      title: "Canceled",
    	      icon: "error",
    	  })
      }
    });
  });
</script>
</html>