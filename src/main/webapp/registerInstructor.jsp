<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="edu.stts.Course"%>
<%@page import="edu.stts.StorageClass"%>
<%@page import="edu.stts.InstructorClass"%>
<%@ taglib prefix="mt" tagdir="/WEB-INF/tags" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<link rel="stylesheet" src="css/materialize.min.css">
<link rel="stylesheet" src="css/materialize.css">
<script type="text/javascript" src="js/materialize.min.js"></script>
<script type="text/javascript" src="js/materialize.js"></script>
<script src="jquery-3.3.1.min.js"></script>
<%
StorageClass storage = new StorageClass();
InstructorClass instructor = new InstructorClass();
String image = storage.getImage("lainnya", "bgregins.jpg");
request.setAttribute("image",image);

try{
	if(!request.getParameter("btnSend").equalsIgnoreCase("null")){
		String nama = request.getParameter("name");
		String email = request.getParameter("email");
		String pwd = request.getParameter("password");
		String url = request.getParameter("link");
		String code = instructor.registerInstructor(nama, email, pwd, url);
		if(code.equals("1")){
			out.print("<script>$(document).ready(function() {");		
			out.print("M.toast({html:'Detail has been sent to your email!',classes: 'rounded green'});"); 
			out.print("});</script>");
		}
		else{
			out.print("<script>$(document).ready(function() {");		
			out.print("M.toast({html:'"+code+"',classes: 'rounded red'});"); 
			out.print("});</script>");
		}
	}
}catch(Exception e){
	System.out.println("error");
}
%>
<mt:template title="HOME">
	<jsp:attribute name="content">
		<div class="parallax-container valign-wrapper" style="height: 600px;">
			<div class="container" style="margin-left:20%;margin-top:-5%;">
				<div class="right" style="background-color: rgba(50, 115, 220, 0.9);height:500px;width:300px;">
					<form method="post">
						<h5 style="color:white">Be an Instructor now!<br></h5>
						<div class="input-field col s12 white" style="margin-top:20px;">
							<input id="name" type="text" class="validate" name="name">
								<label for="emaillogin" style="color:red;">Full Name</label>
						</div>
						<div class="input-field col s12 white" style="margin-top:20px;">
							<input id="email" type="email" class="validate" name="email">
								<label for="email" style="color:red;">Email</label>
						</div>
						<div class="input-field col s12 white" style="margin-top:20px;">
							<input id="password" type="password" class="validate" name="password">
								<label for="password" style="color:red;">Password</label>
						</div>
						<div style="color:white">How Would You Describe Your Teaching Technique? Send Your Video Link to Us!</div>
						<div class="input-field col s12 white" style="margin-top:20px;">
							<input id="link" type="text" class="validate" name="link" placeholder="https://www.youtube.com/watch?v=SGLauKdl7f0">
								<label for="link" style="color:red;">Link URL</label>
						</div>
						<button id="btnLoginn" class="btn waves-effect waves-light" type="submit" name="btnSend">Send
							<i class="material-icons right">send</i>
						</button>
					</form>
				</div>
			</div>
			<div class="parallax">
				<img src="${image}" style="padding-top:200px;">
			</div>
		</div>
		<script>
		$(document).ready(function(){
			$('.parallax').parallax();
			$('.modal').modal();
			$('.tabs').tabs();
			$('.sidenav').sidenav();
		});
		
		</script>
	</jsp:attribute>
</mt:template>