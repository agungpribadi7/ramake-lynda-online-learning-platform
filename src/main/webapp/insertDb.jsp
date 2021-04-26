<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="edu.stts.Test" %>
<%

Test test = new Test();
String output = test.readFile("D:/eclipse/projek/Lynda/instructor.txt");
System.out.println(output);

%>