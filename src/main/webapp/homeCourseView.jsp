<%@page import="edu.stts.Course"%>
<%@page import="edu.stts.HelloAppEngine"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*" %>
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
<%@page import="edu.stts.StorageClass"%>
<%@page import="edu.stts.CekPremium"%>
<script src="jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="js/materialize.min.js"></script>
<%

HttpSession sessionku=request.getSession(false);  
String adaUser=(String)sessionku.getAttribute("userlogin");  
String kodeVerif = (String)sessionku.getAttribute("verifStatus");
int iduser = -1;
System.out.println(adaUser + " adaUser");

if(adaUser == null || adaUser.equals("nope")){
    sessionku.setAttribute("error", "1");  
    System.out.println("gk ada session");
    response.sendRedirect("/home.jsp");
}
else{
	iduser = (int)sessionku.getAttribute("xIduser");
	System.out.println(iduser +" iduser");
	CekPremium premium = new CekPremium();
	premium.cekExpired(iduser);
	
	StorageClass storage = new StorageClass();
	ArrayList imglist2 = new ArrayList();
	imglist2.add("data-analyst.jpg");
	imglist2.add("business-intellegence.jpg");
	imglist2.add("data-visualization.jpg");
	imglist2.add("database-admin.jpg");
	imglist2.add("cloud-development.jpg");
	ArrayList imageList2 = storage.getImageBatch("lainnya", imglist2);
	
	Course c = new Course();
	ArrayList<String>[] hasil = c.getTopRatedLimited("Technology", 6);
	ArrayList list = new ArrayList();
	ArrayList imageList = new ArrayList();
	int idx = 0;
	for(int j = 0; j<hasil[idx].size();j++){
		Map kartu = new HashMap();
		String judul = hasil[idx].get(j);
		if(judul.length() > 20){
			judul = judul.substring(0, 20)+"...";
		}
		kartu.put("judul", judul);
		imageList.add(hasil[idx+1].get(j));
		kartu.put("viewer", hasil[idx+2].get(j));
		kartu.put("like", hasil[idx+3].get(j));
		kartu.put("instructor", hasil[idx+4].get(j));
		kartu.put("released", hasil[idx+5].get(j));
		kartu.put("subtopic", hasil[idx+6].get(j));
		kartu.put("free", hasil[idx+7].get(j));
		kartu.put("index_email", hasil[idx+8].get(j));
		kartu.put("id", hasil[idx+9].get(j));
		kartu.put("notfound", storage.getImage("lainnya", "notfound.jpg"));
		list.add(kartu);
	}
	ArrayList<String>[] hasil2 = c.getTopRatedLimited("Creative", 6);
	ArrayList list2 = new ArrayList();
	int idx2 = 0;
	for(int j = 0; j < hasil2[idx2].size();j++){
		Map kartu = new HashMap();
		String judul = hasil2[idx2].get(j);
		if(judul.length() > 20){
			judul = judul.substring(0, 20)+"...";
		}
		kartu.put("judul", judul);
		imageList.add(hasil2[idx2+1].get(j));
		kartu.put("viewer", hasil2[idx2+2].get(j));
		kartu.put("like", hasil2[idx2+3].get(j));
		kartu.put("instructor", hasil2[idx2+4].get(j));
		kartu.put("released", hasil2[idx2+5].get(j));
		kartu.put("subtopic", hasil2[idx2+6].get(j));
		kartu.put("free", hasil[idx2+7].get(j));
		kartu.put("index_email", hasil2[idx2+8].get(j));
		kartu.put("id", hasil2[idx2+9].get(j));
		kartu.put("notfound", storage.getImage("lainnya", "notfound.jpg"));
		list2.add(kartu);
	}
	ArrayList<String>[] hasil3 = c.getTopRatedLimited("Business", 6);
	ArrayList list3 = new ArrayList();
	int idx3 = 0;
	for(int j = 0; j < hasil3[idx3].size();j++){
		Map kartu = new HashMap();
		String judul = hasil3[idx3].get(j);
		if(judul.length() > 20){
			judul = judul.substring(0, 20)+"...";
		}
		kartu.put("judul", judul);
		imageList.add(hasil3[idx3+1].get(j));
		kartu.put("viewer", hasil3[idx3+2].get(j));
		kartu.put("like", hasil3[idx3+3].get(j));
		kartu.put("instructor", hasil3[idx3+4].get(j));
		kartu.put("released", hasil3[idx3+5].get(j));
		kartu.put("subtopic", hasil3[idx3+6].get(j));
		kartu.put("free", hasil[idx3+7].get(j));
		kartu.put("index_email", hasil2[idx3+8].get(j));
		kartu.put("id", hasil3[idx3+9].get(j));
		kartu.put("notfound", storage.getImage("lainnya", "notfound.jpg"));
		list3.add(kartu);
	}
	ArrayList imageURL = storage.getImageBatch("thumbnail", imageList);
	if(kodeVerif.equals("0")){
		
		response.sendRedirect("/home.jsp");
		sessionku.removeAttribute("userlogin");
		sessionku.setAttribute("error", "2");
	}
	request.setAttribute("image", imageList2);
	request.setAttribute("image2", imageURL);
	request.setAttribute("kartu", list);
	request.setAttribute("kartu2", list2);
	request.setAttribute("kartu3", list3);
}
%>


<%@ taglib prefix="mt" tagdir="/WEB-INF/tags" %>



<mt:template title="HOME">
	<jsp:attribute name="content">
<style>
	.tabs-content.carousel .carousel-item { height:auto; overflow-x: visible;}
	#top_course:hover{
		-moz-box-shadow: 0 0 10px #4d4d4d;
	      -webkit-box-shadow: 0 0 10px #4d4d4d;
	      box-shadow: 0 0 10px #4d4d4d;
	}
</style>
<div class="parallax-container center valign-wrapper" style="height: 500px;">
	<div class="container">
		<div class="row">
		  <div class="col 6 right">
			<h3 style="text-align:right;" class="deep-purple accent-1" >Over 1000 expert-led, online courses<br> and video tutorials</h3>
		  </div>
		</div>
	</div>
	<div class="parallax">
		<img src="https://m.media-amazon.com/images/M/MV5BMTY2MjM3MzY3N15BMl5BanBnXkFtZTgwMjQyNjg4NjE@._V1_SX1777_CR0,0,1777,999_AL_.jpg">
	</div>
</div>

<div class="container" style="margin-top:2%;">
	<div class="row">
			<div  class="col s12 m6" style="position: relative;text-align: center;color: white;margin-top:1%;padding-left:5%;">
				<a href="allCourse.jsp?topic=Technology&subtopic=Cloud Administration">
					<c:forEach  items="${image}" var="current" varStatus="loop">
						<c:choose>
						    <c:when test="${loop.index == 0}">
						    	<img id="top_course" src="${current}" width="95%">
						    </c:when>
						</c:choose>
					</c:forEach>
					<div class="centered" style="text-shadow: 2px 2px 4px #000000;font-size:20px;color:white;position: absolute;top: 50%;left: 35%;">Cloud Administration</div>
				</a>
			</div>
		
		<div class="col s12 m3 left" style="position: relative;text-align: center;color: white;">
			<a href="allCourse.jsp?topic=Creative&subtopic=2D Animation">
				<c:forEach  items="${image}" var="current" varStatus="loop">
					<c:choose>
					    <c:when test="${loop.index == 1}">
					    	<img id="top_course" src="${current}" class="responsive-img">
					    </c:when>
					</c:choose>
				</c:forEach>
				<div class="centered" style="text-shadow: 2px 2px 4px #000000;font-size:18px;color:white;position: absolute;top:50%;left:30%;">2D Animation</div>
			</a>
		</div>
		<div class="col s12 m3 left" style="position: relative;text-align: center;color: white;">
			<a href="allCourse.jsp?topic=Business&subtopic=Talent Management">
				<c:forEach  items="${image}" var="current" varStatus="loop">
					<c:choose>
					    <c:when test="${loop.index == 2}">
					    	<img id="top_course" src="${current}" class="responsive-img">
					    </c:when>
					</c:choose>
				</c:forEach>
				<div class="centered" style="text-shadow: 2px 2px 4px #000000;font-size:18px;color:white;position: absolute;top:50%;left:20%;">Talent Management</div>
			</a>
		</div>
		<div class="col s12 m3 left" style="position: relative;text-align: center;color: white;">
			<a href="allCourse.jsp?topic=Technology&subtopic=Database Administration">
				<c:forEach  items="${image}" var="current" varStatus="loop">
					<c:choose>
					    <c:when test="${loop.index == 3}">
					    	<img id="top_course" src="${current}" class="responsive-img">
					    </c:when>
					</c:choose>
				</c:forEach>
				<div class="centered" style="text-shadow: 2px 2px 4px #000000;font-size:18px;color:white;position: absolute;top:50%;left:20%;">Database Administration</div>
			</a>
		</div>
		<div class="col s12 m3 left" style="position: relative;text-align: center;color: white;">
			<a href="allCourse.jsp?topic=Technology&subtopic=Big Data">
				<c:forEach  items="${image}" var="current" varStatus="loop">
					<c:choose>
					    <c:when test="${loop.index == 4}">
					    	<img id="top_course" src="${current}" class="responsive-img">
					    </c:when>
					</c:choose>
				</c:forEach>
				<div class="centered" style="text-shadow: 2px 2px 4px #000000;font-size:18px;color:white;position: absolute;top:50%;left:40%;">Big Data</div>
			</a>
		</div>
		
	</div>
	<center><h5>Browse Our Top Courses</h5></b>
	<div class="row">
		<div class="col s12">
			<ul id="tabs-swipe-demo" class="tabs" style="left:13%;width:100%;overflow-x:visible;">
		    	<li class="tab col s3" ><a href="#swipe-1">Technology</a></li>
		    	<li class="tab col s3"><a class="active" href="#swipe-2">Creative</a></li>
		    	<li class="tab col s3"><a href="#swipe-3">Business</a></li>
		    </ul></center>
		    <div id="swipe-1" class="col s12 row">
		    	<c:forEach items="${kartu}" var="current" varStatus="loop1">
				<a href="courseView.jsp?id=<c:out value="${current.id}" />">
			    	<div class="col s12 m4 left ">
				    	<div class="card left col-content" >
				    		<div class="card-image responsive-img">
				    			<c:forEach items="${image2}" var="current2" varStatus="loop2">
									<c:choose>
									    <c:when test="${loop1.index == loop2.index}">
									    	<img src="<c:out value="${current2}" />" onerror="this.src='${current.notfound}';">
									    </c:when>
									</c:choose>
								</c:forEach>
				    			
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
		    <div id="swipe-2" class="col s12 row">
		    	<c:forEach items="${kartu2}" var="current"  varStatus="loop3">
				<a href="courseView.jsp?id=<c:out value="${current.id}" />">
			    	<div class="col s12 m4 left ">
				    	<div class="card left col-content" >
				    		<div class="card-image responsive-img">
				    			<c:forEach items="${image2}" var="current2" varStatus="loop4">
									<c:choose>
									    <c:when test="${(loop3.index+6) == loop4.index}">
									    	<img src="<c:out value="${current2}" />" onerror="this.src='${current.notfound}';">
									    </c:when>
									</c:choose>
								</c:forEach>
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
			<div id="swipe-3" class="col s12 row">
		        <c:forEach items="${kartu3}" var="current"  varStatus="loop5">
				<a href="courseView.jsp?id=<c:out value="${current.id}" />">
			    	<div class="col s12 m4 left ">
				    	<div class="card left col-content" >
				    		<div class="card-image responsive-img">
				    			<c:forEach items="${image2}" var="current2" varStatus="loop6">
									<c:choose>
									    <c:when test="${(loop5.index+12) == loop6.index}">
									    	<img onerror="this.src='${current.notfound}';" src="<c:out value="${current2}" />">
									    </c:when>
									</c:choose>
								</c:forEach>
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
	</div>

    <center>
	    <form method="get" action="allCourse.jsp">
	    	<button id="btnSeeMore" class="btn waves-effect waves-light" type="submit" name="topic" value="Technology">
	    		See More
			</button>	
			<input type="hidden" name="subtopic" value="Cloud Development">
	    </form>
    	
	</center>
</div>
<br><br>

<script>
	$(document).ready(function(){
		$('.parallax').parallax();
		$('.modal').modal();
		$('ul.tabs').tabs({
	      swipeable : false
	    });
		$('.sidenav').sidenav();
		
	});
	$('.carousel.carousel-slider').carousel({
		fullWidth: true,
		indicators: true
	});
	function heightTabs(){
		$(".tabs-content").css('height','800px');
		$(".tabs-content").css('overflow-y','auto');
		$(".carousel-item").css('height','auto');
		$(".carousel-item").css('overflow-y','auto');
	}
	function instruktor(key){
		location.href = "/profile-teacher-public.jsp?key=" + key;
	}
	setInterval(heightTabs, 250);
</script>
	</jsp:attribute>
</mt:template>