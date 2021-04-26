<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*" %>
<%@page import="edu.stts.StorageClass"%>
<%@page import="edu.stts.Course"%>
<%@ taglib prefix="mt" tagdir="/WEB-INF/tags" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<script src="jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="js/materialize.min.js"></script>
<%
HttpSession sessionku=request.getSession(false);  
String adaUser=(String)sessionku.getAttribute("userlogin");
Integer id_user=(Integer)sessionku.getAttribute("xIduser");  
if(adaUser == null){
    sessionku.setAttribute("error", "1");  
    response.sendRedirect("/home.jsp");
}else{
	String idCourse = request.getParameter("id");
	StorageClass storage = new StorageClass();
	Course c = new Course();
	ArrayList output = c.getDetailVideo(Integer.parseInt(idCourse));


	int iduser =(int)sessionku.getAttribute("xIduser");
	c.setView(iduser, Integer.parseInt(idCourse));
	int cekLike = c.getLike(iduser, Integer.parseInt(idCourse));
	int cekpremium=c.getInfopremium(iduser);
	ArrayList list = new ArrayList();
	Map kartu = new HashMap();
	kartu.put("viewer", output.get(1));
	kartu.put("like", output.get(2));
	kartu.put("judul", output.get(3));
	kartu.put("deskripsi", output.get(4));
	kartu.put("free", output.get(5));
	kartu.put("released", output.get(6));
	kartu.put("instructor", output.get(7));
	kartu.put("link", output.get(8));
	kartu.put("error", output.get(10));
	
	int indexemail = -1;
	indexemail = Integer.parseInt(output.get(9).toString());
	String fotoProfile = c.getLinkFoto(indexemail);
	fotoProfile = storage.getImage("foto", fotoProfile);
	kartu.put("index_email", indexemail);

	list.add(kartu);
	request.setAttribute("detail", list);
	request.setAttribute("premium_cek", cekpremium);
	ArrayList relatedOutput[] = c.getRelatedVideo(Integer.parseInt(idCourse));
	ArrayList relatedList = new ArrayList();
	ArrayList imageList = new ArrayList();
	for(int j = 0;j < relatedOutput[0].size(); j++){
		Map kartu2 = new HashMap();
		kartu2.put("id", relatedOutput[0].get(j));
		kartu2.put("viewer", relatedOutput[1].get(j));
		kartu2.put("instructor", relatedOutput[2].get(j));
		String judul = relatedOutput[3].get(j).toString();
		if(judul.length() > 25){
			judul = judul.substring(0, 25) + "...";
		}
		kartu2.put("judul",judul);
		kartu2.put("free", relatedOutput[4].get(j));
		kartu2.put("subtopic", relatedOutput[5].get(j));
		imageList.add(relatedOutput[6].get(j));
		relatedList.add(kartu2);
	}
	ArrayList imgList = storage.getImageBatch("thumbnail", imageList);
	request.setAttribute("id_user", id_user);
	request.setAttribute("idinstructor", output.get(9).toString());
	request.setAttribute("imgInstructor", fotoProfile);
	request.setAttribute("imageList", imgList);
	request.setAttribute("relatedList", relatedList);
	request.setAttribute("cekLike", cekLike);
	request.setAttribute("iduser", iduser);
	request.setAttribute("idcourse", idCourse);
}

%>
<mt:template title="HOME">
	<jsp:attribute name="content">
		<div class="row grey lighten-5">
			<script src="jquery-3.3.1.min.js"></script>
			<c:forEach items="${detail}" var="detail" varStatus="loop">
				<div class='containerku' style='padding-left:5%;'>
					<div style="margin:20px;">
						<div id='kolomVideo' class='left col s12 m9'>
							<div class='row'>
								<h4> ${detail.judul} </h4>
								<div class='col s12 m8'>	
									${detail.viewer}x viewed<br>
								</div>
								<div class='col s12 m4'>
									Show respect by clicking like button!
									<c:choose>
   										<c:when test="${cekLike == 0}">
											<div id="like"><div class='col s6 waves-effect waves-light' id="likeContent" onclick="likeBtn(${iduser}, ${idcourse}, 0)">${detail.like} &nbsp;<i class="material-icons">thumb_up</i></div></div>
										</c:when>
										<c:when test="${cekLike == 1}">
											<div id="like"><div class='col s6 waves-effect waves-light' id="likeContent" onclick="likeBtn(${iduser}, ${idcourse}, 1)">${detail.like} &nbsp;<i class="material-icons" style="color:lime;">thumb_up</i></div></div>
										</c:when>
									</c:choose>
									
								</div>
							</div>
							<br>
								<c:if test="${detail.free == 1}">
						    		<video class="responsive-video" width="90%"  controls>
										<source src="${detail.link}">
									</video>
						    	</c:if>
						    	<c:if test="${detail.free == 0}">
						    		<c:if test="${premium_cek == 1}">
							    		<video class="responsive-video" width="90%"  controls>
											<source src="${detail.link}">
										</video>
							    	</c:if>
							    	<c:if test="${premium_cek == 0}">
							    		<img src="${detail.error}" width='100%' class='responsive-img'>
							    	</c:if>
						    	</c:if>
								
							
						</div>
						<div class="col s12 m3 right" style="overflow:auto;" id="related">
							<h5>Related course</h5><br>
					    		<c:forEach items="${relatedList}" var="current" varStatus="loop2">
					    			<a href="courseView.jsp?id=${current.id}">
											<div class='row'>
												<div class='col l5 m12' >
													<c:forEach items="${imageList}" var="current3" varStatus="loop3">
														<c:choose>
				    										<c:when test="${loop2.index == loop3.index}">
																<img src="${current3}" width='100%' class='responsive-img'>
															</c:when>
														</c:choose>
													</c:forEach>
												</div>
												<div class='col m12 l7'><a>${current.judul}</a><br>${current.instructor}<br>${current.viewer}x viewed<br>
													<div class="chip white" style="border:2px solid orange">
														<c:choose>
				    										<c:when test="${current.free == 1}">
													    		FREE
													    	</c:when>
													    	<c:when test="${current.free == 0}">
													    		PREMIUM
													    	</c:when>
													    </c:choose>
													</div>
												
												</div>
											</div>
										</a>
									
								</c:forEach>
						</div>
						
						<div class='col s12 m9 left' id="instructor">
							<h5>Instructor</h5>
							<hr>
							<div class='row'>
							<br><br>
								<div class="imgProfile" style="float:left;diplay:block;">
									<img src="${imgInstructor}" width="65px" height="65px" alt="" class="circle">
								</div>
								<div class="contentComment" style="margin-top:-1%;margin-left:75px;">
									<h5><a href="javascript:void(0)" onclick="instruktor('${detail.index_email}')">${detail.instructor}</a></h5><br>
									<c:if test = "${premium_cek == 1}">
										<c:if test = "${detail.index_email != id_user}">
											<a href="profileView.jsp?idinstructor=${idinstructor}#Notifikasi">
												<button id="btnChat" class="btn waves-effect waves-light" type="submit" name="btnChat">
									    			CHAT ME
												</button>	
											</a>
										</c:if>
									</c:if>
								</div>
							</div>
						</div>
						
						<div class='col s12 m9 left' id="VideoDescription">
							<div class='row'>
							<br><br>
							<h5> Course Detail </h5>
							Released: ${detail.released}<br> <br>
							<div id='contentDescription'>
								<c:out value="${detail.deskripsi}" />
							</div>
							<br>
							<div style="color:grey;" onclick="showAllDeskripsi()" id="showMore">Show more</div>
							<div style="color:grey;display:none;" onclick="deskripsiAwal()" id="showLess">Show less</div>
							</div>
						</div>
					</div>
				</div>
			</c:forEach>
			<input type="hidden" id="iduser" value="${iduser}">
			<input type="hidden" id="idcourse" value="${idcourse}">
			
			<script>
				var deskripsi = $("#contentDescription").html();
				var jumlahLike = parseInt($("#likeContent").html());
				var idcourse = parseInt($("#idcourse").val());
				var iduser = parseInt($("#iduser").val());
				function likeBtn(iduser, idcourse, tipe) {
					$.ajax({
						url: "likeAjax",
						type: 'POST',
						dataType: 'text',
						data: {user : iduser, course : idcourse, tipe : tipe},
						
						success: function (data) {
							console.log(data+" ajax");
							if(data == "0"){
								jumlahLike = jumlahLike + 1;
								$("#like").html("<div class='col s6 waves-effect waves-light' onclick='likeBtn("+iduser+", "+idcourse+", 1)'>"+(jumlahLike) +"&nbsp;<i class='material-icons' style='color:lime;'>thumb_up</i></div>");
							}
							else if(data == "1"){
								jumlahLike = jumlahLike - 1;
								$("#like").html("<div class='col s6 waves-effect waves-light' onclick='likeBtn("+iduser+", "+idcourse+", 0)'>"+(jumlahLike) +"&nbsp;<i class='material-icons'>thumb_up</i></div>");
							}
						},
						error:function(data,status,er) {
							alert("error: "+data+" status: "+status+" er:"+er);
						}
					});
			   	}
				
				function deskripsiAwal(){
					$("#contentDescription").html("");
					$("#showLess").hide();
					$("#showMore").show();
					$("#contentDescription").html(deskripsi.substr(0,300));
				}
				function showAllDeskripsi(){
					$("#contentDescription").html(deskripsi);
					$("#showMore").hide();
					$("#showLess").show();
				}
				function instruktor(key){
					location.href = "/profile-teacher-public.jsp?key=" + key;
				}
				$(document).ready(function(){
					deskripsiAwal();
				});
				
			</script>
		</div>
	</jsp:attribute>
</mt:template>