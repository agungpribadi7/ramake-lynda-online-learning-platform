<%@page import="com.google.cloud.dialogflow.v2beta1.Document"%>
<%@page import="edu.stts.HelloAppEngine"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*" %>
<%@page import="edu.stts.StorageClass"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<link rel="stylesheet" src="css/materialize.min.css">
<link rel="stylesheet" src="css/materialize.css">
<script type="text/javascript" src="js/materialize.min.js"></script>
<script type="text/javascript" src="js/materialize.js"></script>
<script src="jquery-3.3.1.min.js"></script>
<%@ taglib prefix="mt" tagdir="/WEB-INF/tags" %>
<%
	HelloAppEngine App = new HelloAppEngine();
	String token=request.getParameter("token");
	if(request.getParameter("btnUbahpassword")!=null){
		if(request.getParameter("passwordbaru")!=null && request.getParameter("cpasswordbaru")!=null){
			String tamppass = request.getParameter("passwordbaru");
			String tampcpass = request.getParameter("cpasswordbaru");
			if(tamppass.equals(tampcpass)){
				String output=App.checkPasswordbaru(token,request.getParameter("passwordbaru"));
				if(output=="1"){
					out.print("<script>$(document).ready(function() {");					
					out.print("M.toast({html:'Password sudah terganti',classes: 'rounded red'});"); 
					out.print("});</script>");
				}else if(output=="0"){
					out.print("<script>$(document).ready(function() {");					
					out.print("M.toast({html:'Session expired',classes: 'rounded red'});"); 
					out.print("});</script>");
				}
				response.sendRedirect("home.jsp");
			}else{
				out.print("<script>$(document).ready(function() {");					
				out.print("M.toast({html:'Password baru dan konfirmasi password tidak sama',classes: 'rounded red'});"); 
				out.print("});</script>");
			}
		}else{
			out.print("<script>$(document).ready(function() {");		
			out.print("M.toast({html:'Ada field yang tidak terisi',classes: 'rounded red'});"); 
			out.print("});</script>");
		}
	}
	
%>


<mt:template title="HOME">
 	<jsp:attribute name="content">
		<div style="margin:40px;">
			<h4>Ubah Password Baru</h4>
			<form method="post" class="col s12">
				<div class="row">
				<div class="input-field col s12">
				  <input id="passwordbaru" name="passwordbaru" type="password" class="validate">
				  <i id="show-pass" class="show-pass material-icons" data-target="#passwordbaru">remove_red_eye</i>
				  <label for="passwordbaru">Password Baru</label>
				</div>
			  </div>
			  <div class="row">
				<div class="input-field col s12">
				  <input id="cpasswordbaru" name="cpasswordbaru" type="password" class="validate">
				  <i id="show-pass1" class="show-pass material-icons" data-target="#cpasswordbaru">remove_red_eye</i>
				  <label for="cpasswordbaru">Konfirmasi Password Baru</label>
				</div>
			  </div>
			  <div class="col s12 center"><br>
				<button id="btnUbahpassword" class="btn waves-effect waves-light" type="submit" name="btnUbahpassword">Ubah Password
				</button>
			  </div>
			</form>
		</div>
		<script>
		$("#show-pass").click(function(){
	        if($($(this).attr("data-target")).attr('type')=='password'){
	            $($(this).attr("data-target")).attr('type','text');
	            $(this).addClass('grey-text text-lighten-1');
	        }else{
	            $($(this).attr("data-target")).attr('type','password');
	            $(this).removeClass('grey-text text-lighten-1');
	        }
	    });
		$("#show-pass1").click(function(){
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