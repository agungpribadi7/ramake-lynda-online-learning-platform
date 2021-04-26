<%@page import="edu.stts.ProfileForum"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.io.*" %>
<link rel="stylesheet" src="css/materialize.min.css">
<link rel="stylesheet" src="css/materialize.css">
<script type="text/javascript" src="js/materialize.min.js"></script>
<script type="text/javascript" src="js/materialize.js"></script>
<script src="jquery-3.3.1.min.js"></script>
<%@ taglib prefix="mt" tagdir="/WEB-INF/tags" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ page import="edu.stts.WalletClass"%>
<%@ page import="java.io.*" %>
<%@ page import="java.nio.file.*" %>
<%@ page import="java.util.regex.Pattern" %>
<%@ page import="java.util.*" %>
<%@ page import="javax.servlet.http.*" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %> 
<!-- buat format uang  -->
<script type="text/javascript" src="js/materialize.js"></script>
<script src="jquery-3.3.1.min.js"></script>
<%
HttpSession sessionku=request.getSession(false);  
String adaUser=(String)sessionku.getAttribute("userlogin");  
int iduser=(int)sessionku.getAttribute("xIduser");  
WalletClass wallet = new WalletClass();


if(adaUser == null){
    sessionku.setAttribute("error", "1");  
    response.sendRedirect("/home.jsp");
}

int output = -2;
if(request.getParameter("btnaccept1")!=null){ 
	output = wallet.beliPremium(30, iduser, 50000);
	System.out.println("masuk 1");
}
else if(request.getParameter("btnaccept2")!=null){ 
	output = wallet.beliPremium(90, iduser, 130000);
	System.out.println("masuk 2");
}
else if(request.getParameter("btnaccept3")!=null){ 
	output = wallet.beliPremium(180, iduser, 200000);
	System.out.println("masuk 3");
}
if(output == 1){
	out.print("<script>$(document).ready(function() {");					
	out.print("M.toast({html:'Pembelian Berhasil',classes: 'rounded green'});"); 
	out.print("});</script>");
}
else if(output == 2){
	out.print("<script>$(document).ready(function() {");					
	out.print("M.toast({html:'Wallet tidak mencukupi',classes: 'rounded red'});"); 
	out.print("});</script>");
}
else if(output == -1){
	out.print("<script>$(document).ready(function() {");					
	out.print("M.toast({html:'Terjadi kesalahan',classes: 'rounded green'});"); 
	out.print("});</script>");
}
int uangku = wallet.getWalletFromId(iduser);
String durasiPremium = wallet.getDurasiPremiumDikurangiHariini(iduser);
request.setAttribute("durasiPremium", durasiPremium);
request.setAttribute("wallet", uangku);
%>
<mt:template title="HOME">
	<jsp:attribute name="content">
		
		<div class="container" style="margin-top:20px;">
			<div class="row">
				<div style="margin-left:10px;font-size:20px;"><i class="fa fa-money"></i>&nbsp Wallet: 
					<fmt:setLocale value = "id_ID"/>
					<fmt:formatNumber value = "${wallet}" type = "currency"/> 
					<a style="background-color:#4CAF50" class="waves-effect waves-light btn" href="topup.jsp">Top up</a><br>
					<c:set var = "durasi" value="${durasiPremium}"/>
					<c:if test="${durasi ne ''}">
						Expired Date : ${durasi}
					</c:if>
				</div>
				<div class="col l4">
				  <div class="card white darken-1">
					<div class="card-content black-text">
					  <span class="card-title center">BECOME PREMIUM</span>
					  <br>
					  <span><i class="fa fa-star"></i>&nbsp Unblock All Video</span>
					  <br><br>
					  <span><i class="fa fa-star"></i>&nbsp Chat with Instructor</span>
					  <br><br>
					  <span><i class="fa fa-star"></i>&nbsp 1 Month - Rp.50.000,00</span>
					</div>
					<div class="card-action center">
						<btn style="background-color:#4CAF50" class="waves-effect waves-light btn modal-trigger" href="#premium1">Join Now</a>
					</div>
				  </div>
				  
				</div>
				
				<div class="col l4">
				  <div class="card white darken-1">
					<div class="card-content black-text">
					  <span class="card-title center">BECOME PREMIUM</span>
					  <br>
					  <span><i class="fa fa-star"></i>&nbsp Unblock All Video</span>
					  <br><br>
					  <span><i class="fa fa-star"></i>&nbsp Chat with Instructor</span>
					  <br><br>
					  <span><i class="fa fa-star"></i>&nbsp 3 Months - Rp.130.000,00</span>
					</div>
					<div class="card-action center">
					  <a style="background-color:#4CAF50" class="waves-effect waves-light btn modal-trigger" href="#premium2">Join Now</a>
					</div>
				  </div>
				</div>
				<div class="col l4">
				  <div class="card white darken-1">
					<div class="card-content black-text">
					  <span class="card-title center">BECOME PREMIUM</span>
					  <br>
					  <span><i class="fa fa-star"></i>&nbsp Unblock All Video</span>
					  <br><br>
					  <span><i class="fa fa-star"></i>&nbsp Chat with Instructor</span>
					  <br><br>
					  <span><i class="fa fa-star"></i>&nbsp 6 Months - Rp.200.000,00</span>
					</div>
					<div class="card-action center">
					  <a style="background-color:#4CAF50" class="waves-effect waves-light btn modal-trigger" href="#premium3">Join Now</a>
					</div>
				  </div>
				</div>
		    </div>
		   	<div id="premium1" class="modal">
				<div class="modal-content">
				  <center>
				  <p style="font-size:20px;">Are you sure want to pay Rp.50.000,00?</p></center>
				</div>
					<center><div style="margin-bottom:20px;">
						<form method="post">
							<button style="background-color:#4CAF50" class="btn waves-effect waves-light" type="submit" name="btnaccept1">Yes</button>
							<button style="background-color:#ff0000" class="btn waves-effect waves-light" type="submit" name="btndecline1">No</button>
						</form>
					</div></center>
			 </div>
			 <div id="premium2" class="modal">
				<div class="modal-content">
				  <center>
				  <p style="font-size:20px;">Are you sure want to pay Rp.130.000,00?</p></center>
				</div>
					<center><div style="margin-bottom:20px;">
						<form method="post">
							<button style="background-color:#4CAF50" class="btn waves-effect waves-light" type="submit" name="btnaccept2">Yes</button>
							<button style="background-color:#ff0000" class="btn waves-effect waves-light" type="submit" name="btndecline2">No</button>
						</form>
					</div></center>
			 </div>
			 <div id="premium3" class="modal">
				<div class="modal-content">
				  <center>
				  <p style="font-size:20px;">Are you sure want to pay Rp.200.000,00?</p></center>
				</div>
					<center><div style="margin-bottom:20px;">
						<form method="post">
							<button style="background-color:#4CAF50" class="btn waves-effect waves-light" type="submit" name="btnaccept3">Yes</button>
							<button style="background-color:#ff0000" class="btn waves-effect waves-light" type="submit" name="btndecline3">No</button>
						</form>
					</div></center>
			 </div>
	  </div>
	  <script>
	  	$(document).ready(function(){
			$('.modal').modal();
		});
	  </script>
	</jsp:attribute>
</mt:template>