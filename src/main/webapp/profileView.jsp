<%@page import="edu.stts.ProfileForum"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.io.*" %>
<link rel="stylesheet" src="css/materialize.min.css">
<link rel="stylesheet" src="css/materialize.css">
<script type="text/javascript" src="js/materialize.min.js"></script>
<script type="text/javascript" src="js/materialize.js"></script>
<script src="jquery-3.3.1.min.js"></script>
<%@ taglib prefix="mt" tagdir="/WEB-INF/tags" %>
<%@page import="edu.stts.StorageClass"%>
<%@page import="edu.stts.ProfileForum"%>
<%@page import="edu.stts.Course"%>
<%@page import="edu.stts.ChatClass"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ page import="org.apache.commons.fileupload.*" %>
<%@ page import="org.apache.commons.fileupload.disk.*" %>
<%@ page import="org.apache.commons.fileupload.servlet.*" %>
<%@ page import="org.apache.commons.io.output.*" %>
<%@ page import="java.io.*" %>
<%@ page import="java.nio.file.*" %>
<%@ page import="java.util.regex.Pattern" %>
<%@ page import="javax.imageio.ImageIO" %>
<%@ page import="java.awt.image.BufferedImage" %>
<%@ page import="java.util.*" %>
<%@ page import="javax.servlet.http.*" %>

<%
	Course course = new Course();
	ArrayList outputTopic = course.getAllTopic();
	ArrayList[] outputSubtopic = course.getAllSubTopic();
	ArrayList subtopic = new ArrayList();
	for(int j = 0;j < outputSubtopic[0].size(); j++){
		Map kartu2 = new HashMap();
		kartu2.put("id", outputSubtopic[0].get(j));
		//System.out.println("id user room = "+outputChat[3].get(j));
		kartu2.put("namaSub", outputSubtopic[1].get(j));
		subtopic.add(kartu2);
	}
	
	ProfileForum profile= new ProfileForum();
	StorageClass storage = new StorageClass();
	int idinstructor = -1;
	try{
		idinstructor = Integer.parseInt(request.getParameter("idinstructor"));
	}catch(Exception e){
		//System.out.println("error gk ada parameter instructor");	
	}
	
	String msg=(String)request.getAttribute("messege_upload");
	if((msg != null && !msg.isEmpty())){
		out.print("<script>$(document).ready(function() {");					
		out.print("M.toast({html:'"+msg+"',classes: 'rounded red'});"); 
		out.print("});</script>");	
	}
	
	System.out.println("id instructor "+idinstructor);
	HttpSession sessionku=request.getSession(false);  
	String adaUser=(String)sessionku.getAttribute("userlogin");  
	if(adaUser == null){
	    sessionku.setAttribute("error", "1");  
	    response.sendRedirect("/home.jsp");
	}
	int iduser =(int)sessionku.getAttribute("xIduser");
	int apaInstructor = (int)sessionku.getAttribute("apaInstructor");
	ChatClass c = new ChatClass();
	if(idinstructor != -1){
		c.firstChat(iduser, idinstructor);
	}
	ArrayList outputChat[] = c.getAllChats(iduser, apaInstructor);
	ArrayList room = new ArrayList();
	ArrayList imageList = new ArrayList();
	ArrayList chat = new ArrayList();
	ArrayList unread = new ArrayList();
	ArrayList foto = new ArrayList();
	ArrayList kapanMunculUnread = new ArrayList();
	for(int j = 0;j < outputChat[0].size(); j++){
		Map kartu2 = new HashMap();
		kartu2.put("idinstructor", outputChat[3].get(j));
		//System.out.println("id user room = "+outputChat[3].get(j));
		kartu2.put("nama", outputChat[0].get(j));
		
		//System.out.println("Email room = "+profile.getFotoFromEmail(outputChat[4].get(j)+""));
		foto.add(profile.getFotoFromEmail(outputChat[4].get(j)+""));
		room.add(kartu2);
		System.out.println(outputChat[3].get(j)+" id room");
	}
	//System.out.println("jumlah email di jsp = "+foto.size());
	int jumlahNotifMunculDiTab = 0;
	for(int j =0;j< outputChat[9].size(); j++){
		Map kartu2 = new HashMap();
		kartu2.put("idinstructor", outputChat[9].get(j)); //lawan bicara
		kartu2.put("unread", outputChat[8].get(j));
		if(Integer.parseInt(outputChat[8].get(j).toString()) != 0){
			jumlahNotifMunculDiTab += 1;
		}
		System.out.println("jumlah unread di jsp = "+outputChat[8].get(j));
		unread.add(kartu2);
		Map kartu = new HashMap();
		kartu.put("idRoomUnread", outputChat[12].get(j));
		kartu.put("kapanMuncul", outputChat[11].get(j));
		kapanMunculUnread.add(kartu);
	}
	
	for(int j = 0;j<outputChat[1].size(); j++){
		Map kartu2 = new HashMap();
		kartu2.put("idinstructor", outputChat[5].get(j));
		kartu2.put("pengirim", outputChat[6].get(j));
		kartu2.put("isi", outputChat[1].get(j));
		kartu2.put("date", outputChat[2].get(j));
		kartu2.put("idchat", outputChat[10].get(j));
		chat.add(kartu2);
	}
	int jumlahRoom = room.size();
	ArrayList imgList = storage.getImageBatch("foto", foto);
   
    if(request.getParameter("btnUpdate")!=null){ 
    	String nama = request.getParameter("name")+"";
    	String email = (String)sessionku.getAttribute("userlogin");
    	String pwd = request.getParameter("password");
    	String deskripsi = request.getParameter("deskripsi");
    	profile.Updateprofile(email, nama, pwd, deskripsi);
    }

    ArrayList info = profile.getInfoFromEmail((String)sessionku.getAttribute("userlogin"));

	if(info.get(3).toString().equals("1") || Integer.parseInt(info.get(3).toString()) == 1){
		request.setAttribute("deskripsi", info.get(4));
	}
	
	
	int idx=0;
	ArrayList list = new ArrayList();
	String id_email= sessionku.getAttribute("xIduser").toString();
	ArrayList<String>[] hasil = profile.getCoursepublik(id_email);
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
	request.setAttribute("kartu", list);
	ArrayList imageKartu1 = new ArrayList();
	
	int idx1=0;
	ArrayList list1 = new ArrayList();
	ArrayList<String>[] hasil1 = profile.getHistory(id_email);
	for(int j = 0; j<hasil1[idx1].size();j++){
		Map kartu = new HashMap();
		String judul = hasil1[idx1].get(j);
		if(judul.length() > 60){
			judul = judul.substring(0, 60)+"...";
		}
		kartu.put("judul", judul);
		imageKartu1.add(hasil1[idx1+1].get(j));
		kartu.put("viewer", hasil1[idx1+2].get(j));
		kartu.put("instructor", hasil1[idx1+3].get(j));
		kartu.put("released", hasil1[idx1+4].get(j).substring(0, 10));
		kartu.put("subtopic", hasil1[idx1+5].get(j));
		kartu.put("free", hasil1[idx1+6].get(j));
		kartu.put("id", hasil1[idx1+7].get(j));
		list1.add(kartu);
	}
	ArrayList outputImageKartu1 = storage.getImageBatch("thumbnail", imageKartu1);
	request.setAttribute("imgKartu1", outputImageKartu1);
	request.setAttribute("kartu1", list1);
	request.setAttribute("jumlahNotifDiTab", jumlahNotifMunculDiTab);
	request.setAttribute("subtopic", subtopic);
	request.setAttribute("outputTopic", outputTopic);
	request.setAttribute("kapanMunculUnread", kapanMunculUnread);
	request.setAttribute("chat", chat);
	request.setAttribute("iduser", iduser);
	request.setAttribute("nama", info.get(0));
	request.setAttribute("email", info.get(1));
	request.setAttribute("foto",storage.getImage("foto", String.valueOf(info.get(2))));
	request.setAttribute("room", room);
	request.setAttribute("unread", unread);
	request.setAttribute("imgList", imgList);
    request.setAttribute("jumlahRoom", jumlahRoom);
    request.setAttribute("apaInstructor", apaInstructor);
%>
		 
		 
		 
		 
		 
		 
<style>
div.speechku {
  position: relative;
  width: auto;
  height: 50px;
  text-align: center;
  line-height: 50px;
  background-color: #c8e6c9;
  border: 1px solid #666;
  border-radius: 30px;
  padding: 0 10px;
  color:white;
}

div.speech {
  position: relative;
  width: auto;
  height: 50px;
  text-align: center;
  line-height: 50px;
  background-color: #e0e0e0;
  border: 1px solid #bdbdbd;
  border-radius: 30px;
  padding: 0 10px;
}

</style>

<mt:template title="HOME">
	<jsp:attribute name="content">
		<input type="hidden" id="jumlahRoom" value="${jumlahRoom}">
		<input type="hidden" id="iduser" value="${iduser}">
		<input type="hidden" id="apaInstructor" value="${apaInstructor}">
		<div class="container z-depth-3" style="margin-top:50px;">
			<div class="row">
				<c:if test = "${apaInstructor == 0}">
		    		<div class="col s12 m12 l12">
					  <ul class="tabs">
						<li class="tab col s4"><a class="active" href="#biodata">Biodata</a></li>
						<li class="tab col s4"><a href="#history">History View</a></li>
						<li class="tab col s4"><a href="#Notifikasi">Notifikasi</a></li>
					  </ul>
					</div>
			    </c:if>
		    	<c:if test = "${apaInstructor == 1}">
		    		<div class="col s12 m12 l12">
					  <ul class="tabs">
						<li class="tab col s2"><a class="active" href="#biodata">Biodata</a></li>
						<li class="tab col s2"><a href="#courses">Courses</a></li>
						<li class="tab col s3"><a href="#history">History View</a></li>
						<li class="tab col s3"><a href="#upload">Upload Course</a></li>
						<li class="tab col s2"><a href="#Notifikasi">Notifikasi</a></li>
					  </ul>
					</div>
			    </c:if>
				
		    </div>
			<div class="row">
				<div id="biodata">
 					<div class="col s4" style="padding-left:100px;"> 
 						<img src="<c:out value="${foto}" />" style="width:200px;height:170px;"><br><br> 
 						<a class="waves-effect waves-light btn modal-trigger" href="#modal1">Change Photo</a> 
 					  	<div id="modal1" class="modal"> 
 						 	<div class="modal-content"> 
 						    	<center><h4>Change Picture</h4></center> 
 						 	</div> 
 						 	<form action="updatefoto" method="post" enctype="multipart/form-data"> 
 						 		<div class="container"> 
							 		<input type="hidden" name="emailuser" value="${email}">
 							 		<div class="row"> 
 							 			<div class="col s4"> 
 							 				Choose Picture  
 							 			</div> 
 							 			<div class="col s8"> 
 							 				<input type="file" name="file_foto"> 
 							 			</div> 
 							 		</div>
 						 		</div> 
 							    <center><div style="margin-bottom:50px;"> 
 									<button class="btn waves-effect waves-light" type="submit" name="btnChange">Change!</button> 
 								</div></center> 
 						    </form> 
 						 </div> 
 					</div> 
					
 					  <div class="col s8"> 
 						<div style="padding:0px;padding-left:20px;padding-right:20px;"> 
 						  <div class="row"> 
 							<form method = "post" class="col s12"> 
 							  <div class="row"> 
 								<div class="input-field col s12"> 
 								  <i class="material-icons prefix">account_circle</i> 
 								  <input id="name" type="text" name="name" class="validate" value="${nama}"> 
 								  <label for="name">Nama</label> 
 								</div> 
 							  </div> 
 							  <div class="row"> 
 								<div class="input-field col s12"> 
 								  <i class="material-icons prefix">contact_mail</i> 
 								  <input disabled id="email" type="email" name="email" value="${email}"> 
 								  <label for="email">Email</label> 
 								</div> 
 							  </div> 
 							  <div class="row"> 
 								<div class="input-field col s12"> 
 								  <i class="material-icons prefix">lock</i> 
 								  <input id="password" type="password" name="password" class="validate"> 
 								  <label for="password">Password</label> 
 								</div> 
 							  </div> 
 							  <div class="row"> 
 								<div class="input-field col s12"> 
 								  <i class="material-icons prefix">lock</i> 
 								  <input id="cpassword" type="password" name="cpassword" class="validate"> 
 								  <label for="cpassword">Konfirmasi Passowrd</label> 
 								</div> 
 							  </div> 
							  
 							  <c:choose> 
 				    			<c:when test="${not empty deskripsi}"> 
 									  <div class="row"> 
 										  <div class="input-field col s12"> 
 										    <i class="material-icons prefix">mode_edit</i> 
 											<textarea id="Deskripsi_bio" name="deskripsi" class="materialize-textarea" data-length="120">${deskripsi}</textarea> 
 											<label for="Deskripsi_bio">Deskripsi</label> 
 										  </div> 
 									 </div> 
 								</c:when> 
 							 </c:choose> 
							 
							  <div class="row center-align">
								<button class="btn waves-effect waves-light" type="submit" name="btnUpdate">Update</button>
							  </div>
							</form>
						  </div>
						</div>
					  </div>
				</div>
				<c:if test = "${apaInstructor == 1}">
		    		<div id="courses" class="col s12">
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
			    </c:if>
				
				<div id="history" class="col s12" style="height:1000px;overflow:auto;">
				<c:forEach items="${kartu1}" var="current" varStatus="loopKartu1">
					<a href="courseView.jsp?id=<c:out value="${current.id}" />">
						<div class="col l12" style="margin-bottom:20px;">
							<div class="col s12 m5 l4">
								<c:forEach items="${imgKartu1}" var="img" varStatus="loopImgKartu1">
									<c:if test = "${loopImgKartu1.index == loopKartu1.index}">
										<img src="<c:out value="${img}" />" width="100%">
									</c:if>
								</c:forEach>
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
				<c:if test = "${apaInstructor == 1}">
			    	<div id="upload" class="col s12">
						<div class="card-stacked">
							<div class="card-content" style="padding:0px;padding-left:20px;padding-right:20px;">
								<strong>Upload Course</strong><br><br>
								<form action="ProfileForum" method="post" enctype="multipart/form-data"> 
	 						 		<div class="container">
	 						 			<input type="hidden" name="emailcourse" value="${email}">
	 							 		<div class="row"> 
	 							 			<div class="col s4"> 
	 							 				Subtopic  
	 							 			</div> 
	 							 			<div class="col s8"> 
	 							 				<select name="subtopic">
		 							 				<c:forEach items="${subtopic}" var="current" varStatus="loop">
														<option value="${current.id}">${current.namaSub}</option>
		 							 				</c:forEach>
		 							 			</select>
	 							 			</div> 
	 							 		</div>
	 							 		<div class="row"> 
	 							 			<div class="col s4"> 
	 							 				Select type of your course  
	 							 			</div> 
	 							 			<div class="col s8"> 
	 							 				<select name="free">
													<option value="1">Free</option>
													<option value="0">Premium</option>
												</select>
	 							 			</div> 
	 							 		</div>   
	 						 			<div class="row"> 
	 							 			<div class="col s4"> 
	 							 				Title  
	 							 			</div> 
	 							 			<div class="col s8"> 
	 							 				<input type="text" name="judul"> 
	 							 			</div> 
	 							 		</div>
	 							 		<div class="row"> 
	 							 			<div class="col s4"> 
	 							 				Description  
	 							 			</div> 
	 							 			<div class="col s8"> 
	 							 				<textarea name="description" id="description" value="" rows="4" cols="30"></textarea> 
	 							 			</div> 
	 							 		</div>
	 							 		<div class="row"> 
	 							 			<div class="col s4"> 
	 							 				Thumbnail  
	 							 			</div> 
	 							 			<div class="col s8"> 
	 							 				<input type="file" name="file_thumbnail"> 
	 							 			</div> 
	 							 		</div>  
	 							 		<div class="row"> 
	 							 			<div class="col s4"> 
	 							 				Choose video  
	 							 			</div> 
	 							 			<div class="col s8"> 
	 							 				<input type="file" name="file_video"> 
	 							 			</div> 
	 							 		</div>
	 						 		</div> 
	 							    <center><div style="margin-bottom:50px;"> 
	 									<button class="btn waves-effect waves-light" type="submit" name="btnUploadCourse">Upload!</button> 
	 								</div></center> 
	 						    </form> 							  
							</div>
						</div>
					</div>
			    </c:if>
 				
 				
 			
				
				
				
				
				
				
				
 				<div id="Notifikasi" class="col s12" > 
 						<div id="roomChat" class="col s4 notifikasiContent" style="border-right:2px solid grey;height:700px;overflow-y:auto;"> 
 							<c:forEach items="${room}" var="room" varStatus="loop"> 
 								<c:if test = "${apaInstructor == 0}"> 
 									<div id="room${loop.index}" onclick="tampilChat(${loop.index}, ${room.idinstructor})" style="margin:0px;border-bottom: 2px solid grey;height:100px;" data-roomid="${room.idinstructor}"> 
 								</c:if> 
 								<c:if test = "${apaInstructor == 1}"> 
 									<div id="room${loop.index}" onclick="tampilChatInstructor(${loop.index}, ${room.idinstructor})" style="margin:0px;border-bottom: 2px solid grey;height:100px;" > 
 								</c:if> 
 									<input type="hidden" id="idinstructor${loop.index}" value="${room.indinstructor}"> 
 										<div class="imgProfile" style="float:left;diplay:block;"> 
 											<c:forEach items="${imgList}" var="img" varStatus="loop2"> 
 												<c:choose> 
 			   										<c:when test="${loop2.index == loop.index}"> 
 														<img src="${img}" width="65px" height="65px" alt="" class="circle"> 
 													</c:when> 
 												</c:choose> 
 											</c:forEach> 
 										</div> 
 										<div class="namaInstructor" style="margin-top:-1%;margin-left:75px; margin-bottom:15px;"> 
 											<h5>${room.nama}</h5> 
 											<div id="unread${loop.index}" class="unread" style="margin-bottom:50px;height:50px;"> 
 												<c:forEach items="${unread}" var="un" varStatus="loopUnread"> 
 													<c:if test = "${room.idinstructor == un.idinstructor}"> 
 															<c:if test = "${un.unread > 0}"> 
 																<span class="new badge" id="isiunread${un.idinstructor}">${un.unread}</span> 
 															</c:if> 
 															<c:if test = "${un.unread == 0}"> 
 																<span class="new badge" id="isiunread${un.idinstructor}" style="display:none;">${un.unread}</span> 
 															</c:if> 
 													</c:if> 
 												</c:forEach> 
 											</div>  
 										</div> 
 									</div> 
 								</c:forEach> 
 						</div> 
 						<div class="col s8"> 
 							<c:forEach items="${room}" var="room" varStatus="loop">
 								<c:forEach items="${kapanMunculUnread}" var="kapanMunculUnread" varStatus="loop3"> 
 									<c:if test = "${room.idinstructor == kapanMunculUnread.idRoomUnread}"> 
 									 	<c:set var = "kapanMunculDiRoomIni" value = "${kapanMunculUnread.kapanMuncul}"/>
 									</c:if>
 								</c:forEach>		 
 								<c:set var="count" value="0" scope="page" />
 								<div id="nama${loop.index}" style="display:none;border-bottom:2px solid grey;height:50px;"> 
 									<b>${room.nama}</b> 
 								</div> 
 								<div id="chatroom${loop.index }" class="isichat${room.idinstructor}" style="display:none;height:450px;overflow-x:auto;"> 
 									<c:forEach items="${chat}" var="chat" varStatus="loop2"> 
 										
											<c:choose> 
		   										<c:when test="${room.idinstructor == chat.idinstructor}"> 
		   											<c:if test = "${count == kapanMunculDiRoomIni}">
		   												<center><div class="chip" id="chip${room.idinstructor}">Unread Messages</div></center>
		   											</c:if> 
		   											
		   											<c:if test = "${chat.pengirim == iduser}"> 
		   											<div id="tampungan" class="right">
		   												${chat.date}
		   												<div class="speechku green darken-1 right" id="speech${chat.idchat}">${chat.isi}</div> 
		   											</div>
											        	
											    	</c:if> 
											    	<c:if test = "${chat.pengirim != iduser}"> 
											    	<div id="tampungan">
		   												${chat.date}
											        	<div class="speech left" id="speech${chat.idchat}">${chat.isi}</div> 
											        </div>
											    	</c:if> 
											    	<div style="clear:both;"></div> 
											    	<c:set var="count" value="${count + 1}" scope="page"/>
		   										</c:when> 
		   									</c:choose> 
 									</c:forEach> 
 								 </div> 
							
 								<c:if test = "${apaInstructor == 0 && room.idinstructor != 119}"> 
 									<textarea rows="4" cols="50" style="height:140px;display:none;" onkeypress="processUser(event, this, ${loop.index}, ${room.idinstructor})" id="ketik${loop.index}" value=""></textarea> 
 								</c:if> 
 								<c:if test = "${apaInstructor == 1 && room.idinstructor != 119}"> 
 									<textarea rows="4" cols="50" style="height:140px;display:none;" onkeypress="processInstructor(event, this, ${loop.index}, ${room.idinstructor})" id="ketik${loop.index}" value=""></textarea> 
 								</c:if> 
 							</c:forEach> 
 						</div> 
 				</div> 
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
 			</div> 
 		</div> 
		<script src="jquery-3.3.1.min.js"></script>
		<script type="text/javascript" src="js/materialize.min.js"></script>
		<script>
			var months = ["January", "Febuary", "Maret","April", "May", "June", "July", "August", "September", "October", "November", "December"];
			var jumlahRoom = $("#jumlahRoom").val();
			var iduser = $("#iduser").val();
			var kalimat = "";
			var idtujuanpesan = -1;
			var apakahIniInstructor = $("#apaInstructor").val();
			
			function tampilChat(idHistory, idinstructor){
				idtujuanpesan = idinstructor;
				var unread = $("#unread"+idHistory).html();
				for(i = 0;i<jumlahRoom; i++){
					$("#nama"+i).hide();
					$("#chatroom"+i).hide();
					$("#ketik"+i).hide();
				}
				$("#chatroom"+idHistory).show();
				$("#nama"+idHistory).show();
				$("#ketik"+idHistory).show();
				if(unread != ""){
					$.ajax({
						url: "UnreadAjax",
						type: 'POST',
						dataType: 'text',
						data: {user : iduser, instructor: idinstructor, apainstructor : 0},
						success: function (data) {
							if(data == "1"){
								$("#isiunread"+iduserdichat).hide();
							}
						},
						error:function(data,status,er) {
							//alert("error: "+data+" status: "+status+" er:"+er);
						}
					});
				}
				
			}
			
			function tampilChatInstructor(idHistory, iduserdichat){
				idtujuanpesan = iduserdichat;
				var unread = $("#unread"+idHistory).html();
				for(i = 0;i<jumlahRoom; i++){
					$("#nama"+i).hide();
					$("#chatroom"+i).hide();
					$("#ketik"+i).hide();
				}
				$("#chatroom"+idHistory).show();
				$("#nama"+idHistory).show();
				$("#ketik"+idHistory).show();
				if(unread != ""){
					$.ajax({
						url: "UnreadAjax",
						type: 'POST',
						dataType: 'text',
						data: {instructor : iduser, user: iduserdichat, apainstructor : 1},
						success: function (data) {
							if(data == "1"){
								$("#isiunread"+iduserdichat).hide();
							}
						},
						error:function(data,status,er) {
							//alert("error: "+data+" status: "+status+" er:"+er);
						}
					});
				}
				
			}
			
			
			$(document).ready(function(){
				$('.modal').modal();
				$('.tabs').tabs();
				$('.sidenav').sidenav();
				$('select').formSelect();
			});
			var character = null;
			function processInstructor(e, textarea, loopindex, idinstructor) {
				$("#chip"+idinstructor).remove();
				$("#isiunread"+idinstructor).html(0);
				var code = (e.keyCode ? e.keyCode : e.which);
			    if (code == 13) { //Enter keycode
			    	var rawD = new Date();
			    	var dUTC =   Date.UTC(rawD.getUTCFullYear(), rawD.getUTCMonth(), rawD.getUTCDate(), rawD.getUTCHours(), rawD.getUTCMinutes(), rawD.getUTCSeconds());
			    	var d = new Date(dUTC);
						$.ajax({
							url: "ChatAjax",
							type: 'POST',
							dataType: 'text',
							data: {instructor : iduser, user : idtujuanpesan, pesan : kalimat, pengirim : iduser},
							success: function (data) {
								if(data == "-1"){
									alert("gagal");
								}
								else if(data == "1"){
									$("#ketik"+loopindex).val("");
									$("#chatroom"+loopindex).append("<div id='tampungan' class='right'>"+d+"<div class='speechku green darken-1 right'>"+kalimat+"</div><div style='clear:both;'></div></div>");
									kalimat = "";
								}
							},
							error:function(data,status,er) {
								//alert("error: "+data+" status: "+status+" er:"+er);
							}
						});
			    }
			    else{
			    	var character = String.fromCharCode(e.which);
					kalimat += character;
			    }
			}
			
			function processUser(e, textarea, loopindex, idinstructor) {
				$("#chip"+idinstructor).remove();
				$("#isiunread"+idinstructor).html(0);
				var code = (e.keyCode ? e.keyCode : e.which);
			    if (code == 13) { //Enter keycode
			    	var rawD = new Date();
			    	var dUTC =  Date.UTC(rawD.getUTCFullYear(), rawD.getUTCMonth(), rawD.getUTCDate(), rawD.getUTCHours(), rawD.getUTCMinutes(), rawD.getUTCSeconds());
			    	var d = new Date(dUTC);
						$.ajax({
							url: "ChatAjax",
							type: 'POST',
							dataType: 'text',
							data: {instructor : idtujuanpesan, user : iduser, pesan : kalimat, pengirim : iduser},
							success: function (data) {
								if(data == "-1"){
									alert("gagal");
								}
								else if(data == "1"){
									$("#ketik"+loopindex).val("");
									$("#chatroom"+loopindex).append("<div id='tampungan' class='right'>"+d+"<div class='speechku green darken-1 right'>"+kalimat+"</div><div style='clear:both;'></div></div>");
									kalimat = "";
								}
							},
							error:function(data,status,er) {
								//alert("error: "+data+" status: "+status+" er:"+er);
							}
						});
			    }
			    else{
			    	var character = String.fromCharCode(e.which);
					kalimat += character;
			    }
			}
			
 			var refresh = setInterval(refreshChat, 1000);

			
			var isi = [];
			var idasal = [];
			var date = [];
			var idChat = [];
			

			
			function refreshChat() {
				if(apakahIniInstructor == 1){
					
					$.ajax({
						url: "RefreshChatAjax",
						type: 'POST',
						dataType: 'text',
						data: {id : iduser, apakahinstructor : 1},
						success: function (data) {
							if(data != "-1"){
								data = data.substring(1, data.length-1);
								var result = data.split("], ");
								for(i = 0;i < result.length; i++){
									if(i==result.length-1){
										result[i] = result[i].substring(1, result[i].length-1);
									}
									else{
										result[i] = result[i].substring(1, result[i].length);
									}
									var splitKoma = result[i].split(", ");
									for(j = 0; j < splitKoma.length; j++){
										if(i == 0){
											isi.push(splitKoma[j]);
										}
										if(i == 1){
											idasal.push(splitKoma[j]);
										}
										if(i == 2){
											date.push(splitKoma[j]);
										}
										if(i == 3){
											idChat.push(splitKoma[j]);
										}
									}
								}
								for (var i = 0; i < isi.length; i++) {
									var isidariroomchat = $('.isichat'+idasal[i]).html();
									if(isidariroomchat.indexOf("speech"+idChat[i]) == -1){
										var jumlahMessageUnread = $("#isiunread"+idasal[i]).html();
										if(jumlahMessageUnread == 0){
											$(".isichat"+idasal[i]).append('<center><div class="chip" id="chip'+idasal[i]+'">Unread Messages</div></center>');
										}
										
										$("#isiunread"+idasal[i]).show();
										$("#isiunread"+idasal[i]).html(parseInt(jumlahMessageUnread)+1);
										$(".isichat"+idasal[i]).append('<div id="tampungan">'+date[i]+'<div class="speech left" id="speech'+idChat[i]+'">'+isi[i]+'</div><div style="clear:both;"></div></div> ');
									}
									//alert(data[0]);
						        }
							}
						},
						error:function(data,status,er) {
							//alert("error: "+data+" status: "+status+" er:"+er);
						}
					});
				}
				if(apakahIniInstructor == 0){
					$.ajax({
						url: "RefreshChatAjax",
						type: 'POST',
						dataType: 'text',
						data: {id : iduser, apakahinstructor : 0},
						success: function (data) {
							if(data != "-1"){
								data = data.substring(1, data.length-1);
								var result = data.split("], ");
								for(i = 0;i < result.length; i++){
									if(i==result.length-1){
										result[i] = result[i].substring(1, result[i].length-1);
									}
									else{
										result[i] = result[i].substring(1, result[i].length);
									}
									var splitKoma = result[i].split(", ");
									for(j = 0; j < splitKoma.length; j++){
										if(i == 0){
											isi.push(splitKoma[j]);
										}
										if(i == 1){
											idasal.push(splitKoma[j]);
										}
										if(i == 2){
											date.push(splitKoma[j]);
										}
										if(i == 3){
											idChat.push(splitKoma[j]);
										}
									}
								}
								for (var i = 0; i < isi.length; i++) {
									var isidariroomchat = $('.isichat'+idasal[i]).html();
									if(isidariroomchat.indexOf("speech"+idChat[i]) == -1){
										var jumlahMessageUnread = $("#isiunread"+idasal[i]).html();
										if(jumlahMessageUnread == 0){
											$(".isichat"+idasal[i]).append('<center><div class="chip" id="chip'+idasal[i]+'">Unread Messages</div></center>');
										}
										$("#isiunread"+idasal[i]).show();
										$("#isiunread"+idasal[i]).html(parseInt(jumlahMessageUnread)+1);
										$(".isichat"+idasal[i]).append('<div id="tampungan">'+date[i]+'<div class="speech left" id="speech'+idChat[i]+'">'+isi[i]+'</div><div style="clear:both;"></div></div>');
									}
									//alert(data[0]);
						        }
							}
						},
						error:function(data,status,er) {
							//alert("error: "+data+" status: "+status+" er:"+er);
						}
					});
				}
			}
		</script>
	</jsp:attribute>
</mt:template>
    