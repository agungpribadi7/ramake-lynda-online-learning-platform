<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@page import="edu.stts.SnapController"%>
  <%@page import="edu.stts.StorageClass"%>
 <%
 SnapController s = new SnapController();
 String token = s.getToken("10");
request.setAttribute("token", token);

StorageClass sto = new StorageClass();
 %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<script src="https://code.jquery.com/jquery-1.10.2.js"
	type="text/javascript"></script>
<body>
    <button id="pay-button">Pay!</button>
    <pre><div id="result-json">JSON result will appear here after payment:<br></div></pre> 

<!-- TODO: Remove ".sandbox" from script src URL for production environment. Also input your client key in "data-client-key" -->
    <script src="https://app.sandbox.midtrans.com/snap/snap.js" data-client-key="SB-Mid-client-qTIJEEl53alhacQw"></script>

   	<script type="text/javascript">
	function midtrans_report(result) {
		$.ajax({
			url: "test",
			type: 'POST',
			dataType: 'json',
			data: {nama : JSON.stringify(result)},
			
			success: function (data) {
				$("#result-json").text(data);
			},
			error:function(data,status,er) {
				alert("error: "+data+" status: "+status+" er:"+er);
			}
		});
   	}
   	
      document.getElementById('pay-button').onclick = function(){
        // SnapToken acquired from previous step
        snap.pay('${token}', {
          // Optional
          onSuccess: midtrans_report,
          // Optional
          onPending: midtrans_report,
          // Optional
          onError: midtrans_report
        });
      };
   	
   	
   	
   	
    </script>
  </body>
</html>