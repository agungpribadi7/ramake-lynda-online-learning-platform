<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="mt" tagdir="/WEB-INF/tags" %>
<%@ page import="java.util.*" %>
<link rel="stylesheet" src="css/materialize.min.css">
<link rel="stylesheet" src="css/materialize.css">
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<script type="text/javascript" src="js/materialize.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<script type="text/javascript" src="js/materialize.js"></script>
<script src="jquery-3.3.1.min.js"></script>
<%@ page import="java.io.*" %>
<%@page import="edu.stts.StorageClass"%>
<%@page import="edu.stts.ProfileForum"%>
<%@ page import="javax.servlet.http.*" %>
<%@ taglib prefix="mt" tagdir="/WEB-INF/tags" %>

<%
	HttpSession sessionku=request.getSession(false);  
	String adaUser=(String)sessionku.getAttribute("userlogin");  
	if(adaUser == null){
	    sessionku.setAttribute("error", "1");  
	    response.sendRedirect("/home.jsp");
	}
	StorageClass storage = new StorageClass();
	ProfileForum profile= new ProfileForum();
	ArrayList info = profile.getInfoFromindexEmail(request.getParameter("key"));
	if(info.get(3).toString().equals("1") || Integer.parseInt(info.get(3).toString()) == 1){
		request.setAttribute("deskripsi", info.get(4));
	}
	int idx=0;
	ArrayList list = new ArrayList();
	ArrayList<String>[] hasil = profile.getCoursepublik((String)request.getParameter("key"));
	for(int j = 0; j<hasil[idx].size();j++){
		Map kartu = new HashMap();
		String judul = hasil[idx].get(j);
		if(judul.length() > 60){
			judul = judul.substring(0, 60)+"...";
		}
		kartu.put("judul", judul);
		kartu.put("thumbnail", storage.getImage("thumbnail", hasil[idx+1].get(j)));
		kartu.put("viewer", hasil[idx+2].get(j));
		kartu.put("instructor", hasil[idx+3].get(j));
		kartu.put("released", hasil[idx+4].get(j).substring(0, 10));
		kartu.put("subtopic", hasil[idx+5].get(j));
		kartu.put("free", hasil[idx+6].get(j));
		kartu.put("id", hasil[idx+7].get(j));
		list.add(kartu);
	}
	int likes= profile.getLikes(Integer.parseInt(request.getParameter("key")));
	request.setAttribute("kartu", list);
	request.setAttribute("nama", info.get(0));
	request.setAttribute("foto",storage.getImage("foto", String.valueOf(info.get(2))));
	request.setAttribute("like", likes);
	//String total = profile.getTotalVideo((String)sessionku.getAttribute("userlogin"));
	String total = profile.getTotalVideo(request.getParameter("key"));
	request.setAttribute("total", total);
%>

<mt:template title="HOME">
	<jsp:attribute name="content">
		<section id="teacher-profile-public" class="z-depth-3">
			<div class="container">
				<div class="row">
					<div class="col l4" style="margin-top:20px;">
						<div class="col l12 center"style="padding-bottom:10px;">
							<img src="<c:out value="${foto}"/>" style="max-width: 100%;height: 200;" width="200" alt="" class="circle responsive-img">
						</div>
						<div class="col l12 center" style="margin-top:30px;margin-left:70px;">
							<div style="font-size:40px;" class='col s6 waves-effect waves-light' id="likeContent"><i style="font-size:50px;" class="material-icons">thumb_up</i>&nbsp;${like}</div>
						</div>
					</div>
					<div class="col l8">
						<h3 class="light grey-text text-darken-3">${nama}</h3>
						<p>
							${deskripsi}
						</p>
					</div>
				</div>
			</div>
		</section>
		
		<div class="container z-depth-3" style="margin-top:70px;">
			<div class="row" style="padding:10px; padding-bottom:0px; margin-bottom:0px;">
				<h5>${total} Courses</h5>
			</div>
			<hr/>
			<div class="row" >
			<c:forEach items="${kartu}" var="current">
				<a href="courseView.jsp?id=<c:out value="${current.id}" />">
					<div class="col l12" style="margin-bottom:20px;">
						<div class="col s12 m5 l4">
						  <img src="<c:out value="${current.thumbnail}" />" width="100%">
						</div>
						<div class="col s12 m7 l8">
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
				</a>
			</c:forEach>
			</div>
		</div>
      <!--JavaScript at end of body for optimized loading-->
		<script src="jquery-3.3.1.min.js"></script>
		<script type="text/javascript" src="js/materialize.min.js"></script>
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
			
		</script>
	</jsp:attribute>
</mt:template>