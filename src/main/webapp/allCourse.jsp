<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*" %>
<%@page import="edu.stts.Course"%>
<%@page import="edu.stts.StorageClass"%>
<%@ taglib prefix="mt" tagdir="/WEB-INF/tags" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<%
HttpSession sessionku=request.getSession(false);  
String adaUser=(String)sessionku.getAttribute("userlogin");  
if(adaUser == null){
    sessionku.setAttribute("error", "1");  
    response.sendRedirect("/home.jsp");
}
String nowTopic = "";
String nowSubTopic = "";
try{
	nowTopic = request.getParameter("topic");
	nowSubTopic = request.getParameter("subtopic");
}catch (Exception e) { 
	nowTopic = "Technology";
	nowSubTopic = "Cloud Development";
}

Course c = new Course();
ArrayList<String> subtopic = c.getSubTopicFromTopic(nowTopic);
ArrayList<String> topic = c.getAllTopic();
String total = c.getTotalVideoFromSubTopic(nowSubTopic);
int order = 0;
String halamanString = request.getParameter("halaman");
int totalInteger = Integer.parseInt(total);
int totalPage = 0;
ArrayList totalHalaman = new ArrayList();
for(int i = 0;i < totalPage; i++){
	totalHalaman.add(i+1);
}
StorageClass storage = new StorageClass();
request.setAttribute("halamanString", halamanString);
request.setAttribute("subtopic", subtopic);
request.setAttribute("nowTopic", nowTopic);
request.setAttribute("nowSubTopic", nowSubTopic);
request.setAttribute("topic", topic);
request.setAttribute("total", total);
ArrayList<String>[] hasil = c.getAllCourseFromTopic(nowSubTopic, order);
ArrayList list = new ArrayList();
int idx = 0;
System.out.println("masuk allcourse");
for(int j = 0; j<hasil[idx].size();j++){
	System.out.println("masuk for");
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
	System.out.println("masuk kartu "+judul);
	list.add(kartu);
}

Map<String, String> subPertama = new HashMap<String, String>();
for(int i = 0;i<topic.size();i++){
	subPertama.put(topic.get(i),c.getFirstSubTopicFromTopic(topic.get(i)));
}
request.setAttribute("first", subPertama);
request.setAttribute("kartu", list);
request.setAttribute("totalHalaman", totalHalaman);
%>


      	
<mt:template title="HOME">
	<jsp:attribute name="content">
	<div class="container">
		<h3>${nowTopic} - ${nowSubTopic}</h3>
		<h5>Select Topic</h5>
		<div class="row">
			<c:forEach items="${topic}" var="current">
				<form method="get" action="allCourse.jsp" class="left" style="margin:5px;">
					<input type="hidden" name="subtopic" value="${first.get(current)}">
					<input type="hidden" name="halaman" value="1">
					<c:set var="topicIteration"><c:out value="${current}" /></c:set>
					<c:choose>
					    <c:when test="${nowTopic == topicIteration}">
					        <button id="btnSeeMore" class="btn waves-effect waves-light white" style="color:purple;border:1px solid blue" type="submit" name="topic" value="<c:out value="${current}" />">
					    		<c:out value="${current}" />
							</button>	
					    </c:when>    
					    <c:otherwise>
					        <button id="btnSeeMore" class="btn waves-effect waves-light" type="submit" name="topic" value="<c:out value="${current}" />">
					    		<c:out value="${current}" />
							</button>	
					    </c:otherwise>
					</c:choose>
			    </form>
			</c:forEach>
		</div>
		<div class="row">
			<h5>Select Subtopic</h5>
			<c:forEach items="${subtopic}" var="current">
				<form method="get" action="allCourse.jsp" class="left" style="margin:5px;">
					<input type="hidden" name="halaman" value="1">
					<input type="hidden" name="topic" value="${nowTopic}">
					
					<c:set var="subtopicIteration"><c:out value="${current}" /></c:set>
					<c:choose>
					    <c:when test="${nowSubTopic == subtopicIteration}">
					        <button class="btn waves-effect waves-light white" type="submit" style="color:purple;border:1px solid blue;" name="subtopic" value="<c:out value="${current}" />">
					    		<c:out value="${current}" />
							</button>
					    </c:when>    
					    <c:otherwise>
					        <button class="btn waves-effect waves-light" type="submit" name="subtopic" value="<c:out value="${current}" />">
					    		<c:out value="${current}" />
							</button>
					    </c:otherwise>
					</c:choose>
					
			    		
			    </form>
			</c:forEach>
		</div>
		<div class="row">
			<h5>Showing ${total} courses!</h5><br>
			<c:forEach items="${kartu}" var="current">
				<a href="courseView.jsp?id=${current.id}">
			    	<div class="col s12 left">
				    	<div class="card left col-content" >
				    		<div class="responsive-img left col s4">
				    			<img src="<c:out value="${current.thumbnail}" />" width="100%">
				    		</div>
				    		<div class="col s8">
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
