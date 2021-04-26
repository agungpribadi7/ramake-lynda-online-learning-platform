<%@page import="edu.stts.StorageClass"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*" %>
<%@page import="edu.stts.Course"%>
<%@ taglib prefix="mt" tagdir="/WEB-INF/tags" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
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
<%

HttpSession sessionku=request.getSession(false);  
String adaUser=(String)sessionku.getAttribute("userlogin");  
if(adaUser == null){
    sessionku.setAttribute("error", "1");  
    response.sendRedirect("/home.jsp");
}

String keyword = request.getParameter("key");
Course c = new Course();

ArrayList<String>[] hasil = c.cari(keyword, 0);
int idx = 0;
ArrayList list = new ArrayList();
StorageClass storage = new StorageClass();
for(int j = 0; j<hasil[idx].size();j++){
	Map kartu = new HashMap();
	String judul = hasil[idx].get(j);
	if(judul.length() > 30){
		judul = judul.substring(0, 30)+"...";
	}
	kartu.put("judul", judul);
	kartu.put("thumbnail", storage.getImage("thumbnail", hasil[idx+1].get(j)));
	kartu.put("viewer", hasil[idx+2].get(j));
	kartu.put("like", hasil[idx+3].get(j));
	kartu.put("instructor", hasil[idx+4].get(j));
	kartu.put("released", hasil[idx+5].get(j).substring(0, 10));
	kartu.put("subtopic", hasil[idx+6].get(j));
	kartu.put("free", hasil[idx+7].get(j));
	kartu.put("id", hasil[idx+8].get(j));
	list.add(kartu);
}
request.setAttribute("kartu", list);



%>
<mt:template title="HOME">
	<jsp:attribute name="content">
		<div class="container">
			<div class="row">
				<c:forEach items="${kartu}" var="current">
				<a href="courseView.jsp?id=<c:out value="${current.id}" />">
			    	<div class="col s12 m4 left ">
				    	<div class="card left col-content" >
				    		<div class="card-image responsive-img">
				    			<img src="<c:out value="${current.thumbnail}" />">
				    		</div>
				    		<div class="card-content">
				    			<c:out value="${current.judul}" />
				    			<div class="row">
				    				<span class="col s12"><c:out value="${current.instructor}" /></span><br>
				    				<div class="chip white" style="border:2px solid orange">
									    <c:out value="${current.subtopic}" />
									</div>
									<div class="chip white" style="border:2px solid orange">
									    <c:out value="${current.free}" />
									</div>
				    			</div>
			    				<c:out value="${current.viewer}" /> views &#183 <c:out value="${current.released}" />
				    		</div>
			    		</div>
			    	</div>
			    </a>
		      	</c:forEach>
			</div>
		</div>
	</jsp:attribute>
</mt:template>