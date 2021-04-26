<%@page import="com.google.cloud.dialogflow.v2beta1.Document"%>
<%@page import="edu.stts.HelloAppEngine"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*" %>


<%@page import="edu.stts.StorageClass"%>
<%@page import="edu.stts.Course"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<link rel="stylesheet" src="css/materialize.min.css">
<link rel="stylesheet" src="css/materialize.css">
<script type="text/javascript" src="js/materialize.min.js"></script>
<script type="text/javascript" src="js/materialize.js"></script>
<script src="jquery-3.3.1.min.js"></script>

<%@ taglib prefix="mt" tagdir="/WEB-INF/tags" %>
<jsp:useBean id="users" class="edu.stts.HelloAppEngine"></jsp:useBean>	
<jsp:setProperty property="*" name="users"/>

<%
HttpSession sessionku = request.getSession(false);  
try{	
	String error=(String)sessionku.getAttribute("error");
	if(error.equals("1")){
		out.print("<script>$(document).ready(function() {");		
		out.print("M.toast({html:'Anda Belum Login!',classes: 'rounded red'});"); 
		out.print("});</script>");
		sessionku.setAttribute("error", "-1");  
	}
	if(error.equals("2")){
		out.print("<script>$(document).ready(function() {");		
		out.print("M.toast({html:'Email Belum Diverifikasi, Silahkan Cek Email Anda',displayLength: Infinity,classes: 'rounded red'});"); 
		out.print("});</script>");
		sessionku.setAttribute("error", "-1"); 
	}
}catch (Exception e) {
	e.printStackTrace();
}
	
	
	HelloAppEngine App = new HelloAppEngine();

	String output = App.checkUsers(users.getEmail(),users.getName(),users.getPassword(),users.getCpassword());
	/*out.print("<script>$(document).ready(function() {");		
	out.print("M.toast({html:'"+users.getEmaillogin()+"-"+users.getPasslogin()+"',classes: 'rounded red'});"); 
	out.print("});</script>");*/
	String output2 = App.checkLogin(users.getEmaillogin(), users.getPasslogin());
	String output3 = App.checkEmaillupa(users.getEmailforgot());
	request.setAttribute("hasil2",output2);
	request.setAttribute("hasil", output);
	request.setAttribute("hasil3", output3);
	try{
		String token=request.getParameter("token");
		if(!token.equals("")){
			String tamp = App.checkVerif(token);
			if(tamp.equals("1")){
				out.print("<script>$(document).ready(function() {");		
				out.print("M.toast({html:'Berhasil Verifikasi!',classes: 'rounded green'});"); 
				out.print("});</script>");
				sessionku.setAttribute("verifStatus","1");
			}else{
				out.print("<script>$(document).ready(function() {");		
				out.print("M.toast({html:'Sudah Terverifikasi!',classes: 'rounded red'});"); 
				out.print("});</script>");
			}
		}
	}catch (Exception e) {
        e.printStackTrace();
    }
	
%>
<%	

	StorageClass storage = new StorageClass();
	ArrayList imgHome = new ArrayList();
	Course c= new Course();
	int idx=0;
	ArrayList list = new ArrayList();
	ArrayList<String>[] hasil = c.getTopRated(4);
	for(int j = 0; j<hasil[idx].size();j++){
		Map kartu = new HashMap();
		String judul = hasil[idx].get(j);
		if(judul.length() > 60){
			judul = judul.substring(0, 60)+"...";
		}
		kartu.put("judul", judul);
		kartu.put("thumbnail", storage.getImage("thumbnail", hasil[idx+1].get(j)));
		kartu.put("id", hasil[idx+2].get(j));
		list.add(kartu);
	}
	request.setAttribute("kartu", list);
	String imgBanner = storage.getImage("lainnya", "home-banner.jpg");
	System.out.println("link banner "+imgBanner);
	imgHome.add(imgBanner);
	imgHome.add(storage.getImage("lainnya", "notify-bg.jpg"));
	request.setAttribute("imageku", imgHome);
	
	int idx1=0;
	ArrayList list1 = new ArrayList();
	ArrayList<String>[] hasil1 = App.getRandomTeacher(4);
	for(int j = 0; j<hasil1[idx1].size();j++){
		Map kartu = new HashMap();
		kartu.put("nama", hasil1[idx1].get(j));
		kartu.put("foto", storage.getImage("foto", hasil1[idx1+1].get(j)));
		kartu.put("id", hasil1[idx1+2].get(j));
		list1.add(kartu);
	}
	request.setAttribute("kartu1", list1);

	if(request.getParameter("btnRegister")!=null){ 
		String tamppass = users.getPassword();
		String tampcpass = users.getCpassword();
		if(users.getEmail()!=null && users.getName()!=null && users.getPassword()!=null && users.getCpassword()!=null){				
			if((request.getAttribute("hasil")=="Register Gagal, Email Sudah Terdaftar!")){	
				out.print("<script>$(document).ready(function() {");					
				out.print("M.toast({html:'"+request.getAttribute("hasil")+"',classes: 'rounded red'});"); 
				out.print("});</script>");			
			}else if(!tamppass.equals(tampcpass)){
				out.print("<script>$(document).ready(function() {");		
				out.print("M.toast({html:'Kata Sandi dan Konfirmasi Sandi Tidak Sama!',classes: 'rounded red'});"); 
				out.print("});</script>");
			}else if(request.getAttribute("hasil")=="Berhasil Register!"){
				out.print("<script>$(document).ready(function() {");		
				out.print("M.toast({html:'"+request.getAttribute("hasil")+"',classes: 'rounded green'});"); 
				out.print("});</script>");
			}
		}else{
			out.print("<script>$(document).ready(function() {");		
			out.print("M.toast({html:'Ada Field yang Kosong',classes: 'rounded red'});"); 
			out.print("});</script>");
		}
	}if(request.getParameter("btnLogin")!=null){
		if((users.getEmaillogin()!=null || !users.getEmaillogin().equals("null")) && (users.getPasslogin()!=null || !users.getPasslogin().equals("null"))){
			String email = users.getEmaillogin().toString();
			int iduser = App.getIdUser(email);
			System.out.println(iduser+" iduser home");
			int apaInstructor = App.apakahInstructor(email);
			if(apaInstructor == 0){
				if(request.getAttribute("hasil2")=="Berhasil Login!"){
			        sessionku.setAttribute("userlogin", email);  
			        sessionku.setAttribute("xIduser", iduser);  
			        sessionku.setAttribute("verifStatus", users.getVerifikasi(email)); 
			        int cekJabatan = App.apakahInstructor(email);
			        sessionku.setAttribute("apaInstructor", cekJabatan);
					response.sendRedirect("homeCourseView.jsp");
				}else if((request.getAttribute("hasil2")=="Gagal Login, Email atau Password Tidak Ada!")||(request.getAttribute("hasil2")=="Anda telah di ban!")){
					out.print("<script>$(document).ready(function() {");					
					out.print("M.toast({html:'"+request.getAttribute("hasil2")+"',classes: 'rounded red'});"); 
					out.print("});</script>");
				}
			}else if(apaInstructor == 1){
				int accept = App.getInfoAcceptedInstructor(iduser);
				System.out.println(accept+" accepted");
				if(request.getAttribute("hasil2")=="Berhasil Login!"){
					if(accept == 1){
						sessionku.setAttribute("userlogin", email);  
				        sessionku.setAttribute("xIduser", iduser);  
				        sessionku.setAttribute("verifStatus", users.getVerifikasi(email)); 
				        int cekJabatan = App.apakahInstructor(email);
				        sessionku.setAttribute("apaInstructor", cekJabatan);
						response.sendRedirect("homeCourseView.jsp");
					}
					else if(accept == 0){
						out.print("<script>$(document).ready(function() {");		
						out.print("M.toast({html:'Form registrasi kamu sedang diperiksa',classes: 'rounded red'});"); 
						out.print("});</script>");
					}
					else if(accept == 2){
						out.print("<script>$(document).ready(function() {");		
						out.print("M.toast({html:'Form registrasi kamu ditolak',classes: 'rounded red'});"); 
						out.print("});</script>");
					}
				}
				else if((request.getAttribute("hasil2")=="Gagal Login, Email atau Password Tidak Ada!")||(request.getAttribute("hasil2")=="Anda telah di ban!")){
					out.print("<script>$(document).ready(function() {");					
					out.print("M.toast({html:'"+request.getAttribute("hasil2")+"',classes: 'rounded red'});"); 
					out.print("});</script>");
				}
				
			}
			
		}else{
			out.print("<script>$(document).ready(function() {");		
			out.print("M.toast({html:'Ada Field yang Kosong',classes: 'rounded red'});"); 
			out.print("});</script>");
		}
	}
	if(request.getParameter("btnLupapassword")!=null){
		if(users.getEmailforgot()!=null){
			if(request.getAttribute("hasil3")=="0"){
				out.print("<script>$(document).ready(function() {");					
				out.print("M.toast({html:'Email Belum Terdaftar!',classes: 'rounded red'});"); 
				out.print("});</script>");
			}else if(request.getAttribute("hasil3")=="1"){
				out.print("<script>$(document).ready(function() {");					
				out.print("M.toast({html:'Email Reset Password Sudah Terkirim',classes: 'rounded green'});"); 
				out.print("});</script>");
			}
		}else{
			out.print("<script>$(document).ready(function() {");		
			out.print("M.toast({html:'Email Harus Diisi',classes: 'rounded red'});"); 
			out.print("});</script>");
		}
	}
%>
<mt:template title="HOME">
 	<jsp:attribute name="content">
		
		<div id="mLogin" class="modal" style="width:500px;height:700px;z-index: 1003; display: none; opacity: 0; top: 4%; transform: scaleX(0.8) scaleY(0.8);">
			<div id="loginDiv">
				<div class="modal-header deep-purple darken-3" style="padding:25px;height:80px;">
					<h4 class="left" style="color:white;">Login</h4>
					<h6 class="right" style="color:white;">Belum punya akun? <a data-target="mRegister" class="modal-trigger modal-action modal-close">Register</a></h6>
				</div>
				<div class="modal-content row">
					<form method="post">
						<div class="input-field col s12">
							<input id="emaillogin" type="email" class="validate" name="emaillogin" autocomplete="on">
								<label for="emaillogin">Email</label>
							<span class="helper-text" data-error="Mohon masukkan email dengan benar">Mohon masukkan email anda. Contoh : contoh@contoh.com</span>
						</div>
						<div class="input-field col s12 pass-field">
							<input type="password" class="password" id="passlogin" name="passlogin">
							<i id="show-pass3" class="show-pass material-icons" data-target="#passlogin">remove_red_eye</i>
							<label for="passlogin">Kata sandi</label>
						</div>
						<a data-target="mLupaspassword" class="modal-trigger modal-action modal-close">Lupa kata sandi?</a>
						<div class="col s12 center"><br>
							<button id="btnLoginn" class="btn waves-effect waves-light" type="submit" name="btnLogin">Login
								<i class="material-icons right">send</i>
							</button>
						</div>
					</form>
				</div>
			</div>
			<div class="g-signin2" data-onsuccess="onSignIn" data-theme="dark"></div>
		</div>			
		
		<div id="mLupaspassword" class="modal" style="width:500px;height:700px;z-index: 1003; display: none; opacity: 0; top: 4%; transform: scaleX(0.8) scaleY(0.8);">
			<div class="modal-header deep-purple darken-3" style="padding:25px;height:80px;">
				<h4 class="left" style="color:white;">Lupa Password</h4>
				<h6 class="right" style="color:white;"><a data-target="mLogin" class="modal-trigger modal-action modal-close">Kembali ke Login</a></h6>
			</div>
			<div class="modal-content row" style="margin-top:10%;">
				<form method="post">
					<div class="input-field col s12">
						<input id=emailforgot name="emailforgot" type="email" class="validate">
						<label for="emailforgot">Email</label>
						<span class="helper-text" data-error="Mohon masukkan email yang benar"></span>
					</div>
					<div class="col s12 center"><br>
						<button id="btnLupapassword" class="btn waves-effect waves-light" type="submit" name="btnLupapassword">Atur Ulang Password
						
						</button>
					</div>
				</form>
			</div>
		</div>
		<div id="mRegister" class="modal" style="width:500px;height:700px;z-index: 1003; display: none; opacity: 0; top: 4%; transform: scaleX(0.8) scaleY(0.8);">
			<div class="modal-header deep-purple darken-3" style="padding:25px;height:80px;">
				<h4 class="left" style="color:white;">Register</h4>
				<h6 class="right" style="color:white;">Sudah punya akun? <a data-target="mLogin" class="modal-trigger modal-action modal-close">Login</a></h6>
			</div>							
			<div class="modal-content row">							
				<form method="post">
					<div class="input-field col s12">
						<input id="name" type="text" name="name" autocomplete="on">
						<label for="name">Nama</label>
						<span class="helper-text" data-error="Mohon masukkan nama"></span>
					</div>
					<div class="input-field col s12">
						<input id="emailReg" type="email" name="email" class="validate" autocomplete="on">					
						<label for="emailReg">Email</label>
						<span class="helper-text" data-error="Mohon masukkan email dengan benar">Mohon masukkan email anda. Contoh : contoh@contoh.com</span>
					</div>
					<div class="input-field col s12 pass-field">
						<input type="password" name="password" id="password">					
						<label for="password">Kata Sandi</label>
						<i id="show-pass" class="show-pass material-icons" data-target="#password">remove_red_eye</i>
					</div>
					<div class="input-field col s12 pass-field">
						<input id="cpassword" type="password" name="cpassword" class="">
						<label for="cpassword">Konfirmasi Kata Sandi</label>
						<i id="show-pass2" class="show-pass material-icons" data-target="#cpassword">remove_red_eye</i>
					</div>
					<div class="col s12 center"><br>
						<button id="btnRegis" class="btn waves-effect waves-light" type="submit" name="btnRegister">Register
						    <i class="material-icons right">send</i>
						</button>
					</div>						
				</form>				
			</div>
		</div>
		<div class="parallax-container center valign-wrapper" style="height: 800px;">
			<div class="container">
				<div class="row">
				  <div class="col s12 white-text">
					<h3 style="color:white;">We Ensure better education
					<br>for a better world</h3>
					<p style="color:white;">In the history of modern astronomy, there is probably no one greater leap forward than the building and launch of the space telescope known as the Hubble.</p>
					<a class="waves-effect waves-light btn-large teal lighten-2">Get Started</a>
				  </div>
				</div>
			</div>
			<div class="parallax">
				<c:forEach  items="${imageku}" var="current" varStatus="loop">
					<c:choose>
					    <c:when test="${loop.index == 0}">
					    	<img src="${current}">
					    </c:when>
					</c:choose>
				</c:forEach>
			</div>
		</div>
		<div class=" grey lighten-2" style="height:auto;">
			<div class="container">
				<div class="row">
					<div class="col s12 m4 left valign-wrapper" style="padding:10px 80px;"> <i class="medium material-icons">attach_money</i>
						<b><h5>Business</b></h5>
					</div>
					<div class="col s12 m4 left valign-wrapper" style="padding:10px 80px;"> <i class="medium material-icons">wb_incandescent</i>&nbsp;
						<b><h5>Creative</b></h5>
					</div>
					<div class="col s12 m4 valign-wrapper" style="padding:10px 80px;"> <i class="medium material-icons">computer</i>&nbsp;
						<b><h5>Technology</b></h5>
					</div>
				</div>
			</div>
		</div>
        <br><br>
		<div class="container center">
			<h4>Popular Free Course</h4>
			<div style="margin-bottom:20px;">We pick the best rating course for you!</div>
			<div class="carousel carousel-slider center">
				<c:forEach items="${kartu}" var="current">
					<div class="carousel-item">
						<img src="<c:out value="${current.thumbnail}" />" width="100%">
						<div class="carousel-fixed-item center">
							<a href="courseView.jsp?id=<c:out value="${current.id}" />" class="btn waves-effect white black-text darken-text-2">${current.judul}</a>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>
		<br>
		<br>
		<div class="container center">
			<div class="text"><h4>Meet Our Instructor</h4>
			Pick our astonishing lecturer<br> to be your role model<br><br><br>
				<div class="row">
					<c:forEach  items="${kartu1}" var="current">
						<div class="col s12 m3">
							<a href="profile-teacher-public.jsp?key=<c:out value="${current.id}" />">
								<img src="<c:out value="${current.foto}" />" alt="" class="circle responsive-img" width="100%">
								 <br>
								<h5>${current.nama}</h5>
							</a>
						</div>
					</c:forEach>
				</div>
			</div>
		</div>
		<br><br>
		<div class="parallax-container center valign-wrapper" style="height: 400px;">
			<div class="container">
				<div class="row">
				  <div class="col s12 white-text">
					<h3 style="color:white;">BECOME AN INSTRUCTOR</h3>
					<p style="color:white;">In the history of modern astronomy, there is probably no one greater leap forward than the building and launch of the space telescope known as the Hubble.</p>
					<a class="waves-effect waves-light btn-large teal lighten-2" href="registerInstructor.jsp">Get Started</a>
				  </div>
				</div>
			</div>
			<div class="parallax">
				<c:forEach  items="${imageku}" var="current" varStatus="loop">
					<c:choose>
					    <c:when test="${loop.index == 1}">
					    	<img src="${current}">
					    </c:when>
					</c:choose>
				</c:forEach> <br>
			</div>
		</div>	
		<script>
			$(document).ready(function(){
				$('.parallax').parallax();
				$('.modal').modal();
				$('.tabs').tabs();
				$('.sidenav').sidenav();
			});
			$('.carousel.carousel-slider').carousel({
				fullWidth: true,
				indicators: true
			});	
			$("#show-pass").click(function(){
		        if($($(this).attr("data-target")).attr('type')=='password'){
		            $($(this).attr("data-target")).attr('type','text');
		            $(this).addClass('grey-text text-lighten-1');
		        }else{
		            $($(this).attr("data-target")).attr('type','password');
		            $(this).removeClass('grey-text text-lighten-1');
		        }
		    });
			$("#show-pass2").click(function(){
		        if($($(this).attr("data-target")).attr('type')=='password'){
		            $($(this).attr("data-target")).attr('type','text');
		            $(this).addClass('grey-text text-lighten-1');
		        }else{
		            $($(this).attr("data-target")).attr('type','password');
		            $(this).removeClass('grey-text text-lighten-1');
		        }
		    });
			$("#show-pass3").click(function(){
		        if($($(this).attr("data-target")).attr('type')=='password'){
		            $($(this).attr("data-target")).attr('type','text');
		            $(this).addClass('grey-text text-lighten-1');
		        }else{
		            $($(this).attr("data-target")).attr('type','password');
		            $(this).removeClass('grey-text text-lighten-1');
		        }
		    });
		</script>		
	</jsp:attribute>		
</mt:template>
