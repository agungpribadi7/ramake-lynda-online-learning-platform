
<%@page import="edu.stts.SnapController"%>
<%@page import="edu.stts.StorageClass"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="mt" tagdir="/WEB-INF/tags" %>
<%
	HttpSession sessionku=request.getSession(false);  
	String adaUser=(String)sessionku.getAttribute("userlogin");  
	System.out.println(adaUser + " adaUser");
	
	int iduser=(int)sessionku.getAttribute("xIduser");  
	System.out.println(iduser + " iduser");
	
	if(adaUser == null || adaUser.equals("nope")){
	    sessionku.setAttribute("error", "1");  
	    System.out.println("gk ada session");
	    response.sendRedirect("/home.jsp");
	}
	SnapController s = new SnapController();
	String token1 = s.getToken("60000");
	String token2 = s.getToken("150000");
	String token3 = s.getToken("400000");
	request.setAttribute("token60", token1);
	request.setAttribute("token150", token2);
	request.setAttribute("token400", token3);
	request.setAttribute("iduser", iduser);
	request.setAttribute("email", adaUser);
 %>
<mt:template title="HOME">
 	<jsp:attribute name="content">
 		 
 	<br><br><br>
 	<div class="container">
 		<center><h4>Top Up Wallet</h4></center><br>
	 	<div class="row">
		    <div class="col l4">
				<div class="card white darken-1">
					<div id="contentCard1">
						<div class="card-content black-text">
					  		<span class="card-title center">TOP UP</span>
					  		<br>
					  		<span><i class="fa fa-star"></i>&nbsp Rp 60.000</span>
						</div>
						<div class="card-action center">
					  		<button class="waves-effect waves-light btn modal-trigger" id="pay-button1">PAY</button>
						</div>
					</div>	
					
					<center><div class="preloader-wrapper big active" id="pre1" style="display:none;">
				    	<div class="spinner-layer spinner-blue-only">
					      	<div class="circle-clipper left">
					        	<div class="circle"></div>
					      	</div>
					      	<div class="gap-patch">
					        	<div class="circle"></div>
					      	</div>
					      	<div class="circle-clipper right">
					        	<div class="circle"></div>
					        </div>
				    	</div>
				  	</div></center>
			  	</div>
			</div>
			<div class="col l4">
				<div class="card white darken-1">
					<div id="contentCard2">
						<div class="card-content black-text">
					  		<span class="card-title center">TOP UP</span>
					  		<br>
					  		<span><i class="fa fa-star"></i>&nbsp Rp 150.000</span>
						</div>
						<div class="card-action center">
					  		<button class="waves-effect waves-light btn modal-trigger" id="pay-button2">PAY</button>
						</div>
					</div>
					<center><div class="preloader-wrapper big active" id="pre2" style="display:none;">
				    	<div class="spinner-layer spinner-blue-only">
					      	<div class="circle-clipper left">
					        	<div class="circle"></div>
					      	</div>
					      	<div class="gap-patch">
					        	<div class="circle"></div>
					      	</div>
					      	<div class="circle-clipper right">
					        	<div class="circle"></div>
					        </div>
				    	</div>
				  	</div></center>
			  	</div>
			</div>
			<div class="col l4">
				
				<div class="card white darken-1">
					<div id="contentCard3">
						<div class="card-content black-text">
					  		<span class="card-title center">TOP UP</span>
					  		<br>
					  		<span><i class="fa fa-star"></i>&nbsp Rp 400.000</span>
						</div>
						<div class="card-action center">
					  		<button class="waves-effect waves-light btn modal-trigger" id="pay-button3">PAY</button>
						</div>
					</div>
					<center><div class="preloader-wrapper big active" id="pre3" style="display:none;">
				    	<div class="spinner-layer spinner-blue-only">
					      	<div class="circle-clipper left">
					        	<div class="circle"></div>
					      	</div>
					      	<div class="gap-patch">
					        	<div class="circle"></div>
					      	</div>
					      	<div class="circle-clipper right">
					        	<div class="circle"></div>
					        </div>
				    	</div>
				  	</div></center>
			  	</div>
			</div>
		</div>
	</div>
    <script src="https://app.sandbox.midtrans.com/snap/snap.js" data-client-key="SB-Mid-client-0dvSs2kozHsFjvLJ"></script>

	<script type="text/javascript">
		var cardKe = -1;
		function midtrans_report(result) {
			$.ajax({
				url: "AjaxMidtrans",
				type: 'POST',
				dataType: 'json',
				data: {object : JSON.stringify(result), iduser: ${iduser}, email: "${email}" },
				beforeSend: function () {
                    $("#contentCard"+cardKe).hide();
                    $("#pre"+cardKe).show();
                },
				success: function (data) {
					if(data == "1"){	
						M.toast({html:'Detail telah dikirim ke email Anda!',classes: 'rounded green'});
					}
					else if(data == "-1"){
						M.toast({html:'Pembayaran gagal!',classes: 'rounded red'});
					}
				},
				error:function(data,status,er) {
					M.toast({html:'Terjadi kesalahan!',classes: 'rounded red'});
				},
                complete: function () {
               		$("#contentCard"+cardKe).show();
                       $("#pre"+cardKe).hide();
                }
			});
	  	}
	  	
	     document.getElementById('pay-button1').onclick = function(){
	    	 cardKe = 1;
	       // SnapToken acquired from previous step
	       snap.pay('${token60}', {
	         // Optional
	         onSuccess: midtrans_report,
	         // Optional
	         onPending: midtrans_report,
	         // Optional
	         onError: midtrans_report
	       });
	     };
	     document.getElementById('pay-button2').onclick = function(){
	    	cardKe = 2;
	       // SnapToken acquired from previous step
	       snap.pay('${token150}', {
	         // Optional
	         onSuccess: midtrans_report,
	         // Optional
	         onPending: midtrans_report,
	         // Optional
	         onError: midtrans_report
	       });
	     };
	     document.getElementById('pay-button3').onclick = function(){
	    	 cardKe = 3;
	       // SnapToken acquired from previous step
	       snap.pay('${token400}', {
	         // Optional
	         onSuccess: midtrans_report,
	         // Optional
	         onPending: midtrans_report,
	         // Optional
	         onError: midtrans_report
	       });
	     };
    </script>
 	</jsp:attribute>
 </mt:template>