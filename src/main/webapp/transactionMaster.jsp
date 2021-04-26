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

<%@ page import="edu.stts.transactionForAdmin" %>
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
		ArrayList<transactionForAdmin> transactionList = new ArrayList<>();
		StorageClass storage = new StorageClass();
	%>
	<% 
		transactionList.clear();
		transactionList = meOnly.getAllTransactions();
		request.setAttribute("transactionList",transactionList);
	%>
	<div class='table-responsive'>
    	<table class='table table-striped table-hover table-condensed'>	
			<tr>
				<th>Transaction ID</th>
				<th>Detail</th>
				<th>Action</th>
				<th>Tipe</th>
			</tr>
			<c:forEach items="${transactionList}" var="transaction">
				<tr>
					<td><c:out value="${transaction.id}" /></td>
					<td>		
						<button class="btn btn-info" 
							data-toggle="modal" 
							data-id="<c:out value='${transaction.id}' />"
							
							data-target="#detail<c:out value='${transaction.id}' />">
							Detail
						</button>
					</td>
					<td>
						<c:choose>
	            			<c:when test="${transaction.tipe == 0}">
	            				<span>Wallet Top Up</span>
	            			</c:when>
	            			<c:when test="${transaction.tipe == 1}">
	            				<span>Premium Payment</span>
	            			</c:when>
	            			<c:when test="${transaction.tipe == 2}">
	            				<span>Video Adsense</span>
	            			</c:when>
		            		</c:choose>
					</td>
					<c:choose>
						<c:when test="${transaction.accepted == 0 && transaction.tipe == 0}">
							<td>
								<button type='button' 
									class='btn btn-danger btnAccept' 
									data-id="<c:out value='${transaction.id}' />"
									data-iduser="<c:out value='${transaction.iduser}' />"
								data-total="<c:out value='${transaction.total}' />">Accept</button>
							</td>
						</c:when>
						<c:otherwise>
							<td style="font-style:italic;color:gray'">Accepted</td>
						</c:otherwise>
					</c:choose>
				</tr>
			</c:forEach>
		</table>
	</div>
		<c:forEach items="${transactionList}" var="transaction">
			<c:set var="oy" value='false' />
			<div id="detail<c:out value='${transaction.id}' />" class='modal fade' role='dialog'>
				<div class='modal-dialog'>
					<div class='modal-content'>
						<div class='modal-header'>
	            			<h4 class='modal-title'>Transaction Data</h4>
	            		</div>
	            		<div class='modal-body'>
	            			Transaction ID       : <c:out value="${transaction.id}" /><br>
		            		Transaction Total     : <c:out value="${transaction.total}" /><br>
		            		Transaction Date    : <c:out value="${transaction.date}" /> <br>
		            		User ID : <c:out value="${transaction.iduser}" /> <br>
		            		<c:choose>
		            			<c:when test="${transaction.tipe == 0}">
		            				<span style="color:yellow; background-color:black">Wallet Top Up</span>
		            			</c:when>
		            			<c:when test="${transaction.tipe == 1}">
		            				<span style="color:yellow; background-color:black">Premium Payment</span>
		            			</c:when>
		            			<c:when test="${transaction.tipe == 2}">
		            				<span style="color:yellow; background-color:black">Video Adsense</span>
		            			</c:when>
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

$(".btnAccept").click(function(e){
	let id = $(this).data('id');
	let iduser = $(this).data('iduser');
	let total = $(this).data('total');
	$.ajax({
		method: "POST",
		url:"adminAjax",
		data: {type:"acceptTransaction",id:id,iduser:iduser,total:total},
		success: function(r){         
   			swal({
     	   		title: "Payment Accepted",
     	    	icon: "success",
     		});
			$('main').load('transactionMaster.jsp');
        }
	});        
});
  
</script>
</html>