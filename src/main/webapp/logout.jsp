<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="edu.stts.CekPremium"%>
<%
CekPremium premium = new CekPremium();
HttpSession sessionku=request.getSession();  
sessionku.invalidate();
response.sendRedirect("/home.jsp");
%>