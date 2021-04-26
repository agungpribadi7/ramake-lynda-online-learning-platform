<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@ attribute name="title" required="true" rtexprvalue="true" %>
<%@ attribute name="content" fragment="true" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%
HttpSession sessionku=request.getSession(false);  
String adaUser=(String)sessionku.getAttribute("userlogin");
Integer cekin=(Integer)sessionku.getAttribute("apaInstructor");
System.out.println(adaUser+" bbbb");
if(adaUser != null){
 System.out.println(adaUser+" sdaa");
 request.setAttribute("instruktor", cekin);
 request.setAttribute("user", adaUser);
}
else{
 request.setAttribute("instruktor", cekin);
 System.out.println("masuk else tag");
 request.setAttribute("user", "nope");
}
%>
<!DOCTYPE html>
  <html>
    <head>
      <!--Import Google Icon Font-->
      <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
      <!--Import materialize.css-->
      <link type="text/css" rel="stylesheet" href="css/materialize.min.css"  media="screen,projection"/>
      <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
   <link type="text/css" rel="stylesheet" href="css/register.css"/>
  
      <!--Let browser know website is optimized for mobile-->
      <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
   <!--<meta name="google-signin-scope" content="profile email"> -->
   <!--<meta name="google-signin-client_id" content="YOUR_CLIENT_ID.apps.googleusercontent.com"> -->

    </head>
<body>
<script src="jquery-3.3.1.min.js"></script>
<script src="https://apis.google.com/js/platform.js" async defer></script>
<script type="text/javascript" src="js/materialize.min.js"></script>

	<div class="navbar-fixed" >
		<nav class="deep-purple darken-3">
				<div class="nav-wrapper">
						<a href="#" data-target="slide-out" class="sidenav-trigger col s3"><i class="material-icons">menu</i></a>
						<div class="hide-on-med-and-down">
							<a href="home.jsp" class="brand-logo left"><img src="https://storage.cloud.google.com/lynda-310811.appspot.com/lainnya/logo.png" width="100%"></a>
						</div>
					
						<ul id="mobile-nav" class="right hide-on-med-and-down">
							<c:set var = "userku" value="${user}"/>
							
							
							<c:if test="${user ne 'nope'}">
								<b><li style="background-color:#FFDF00;"><a href="premium.jsp"><font color="black">Premium</font></a></li></b>
							    <b><li><a href="profileView.jsp">Profile</a></li></b>
							    <b><li><a href="homeCourseView.jsp">Courses</a></li></b>
							    <b><li><a href="logout.jsp">Logout</a></li></b>
							</c:if>
							<c:if test="${user == 'nope'}">
							    <b><li><a class="modal-trigger" href="#mLogin">Login</a></li></b>
							</c:if>
						</ul>
						<div class="show-on-med hide-on-large-only">
							<a href="home.jsp" class="brand-logo right"><img src="https://storage.cloud.google.com/lynda-310811.appspot.com/lainnya/logo.png" width="100%"></a>
						</div>
						<c:if test="${user ne 'nope'}">
						    <div id="search-form">
				                <div class="input-field" style="width:65%;">
				                    <i class="material-icons" id="search-icon">search</i>
				                    <input id="search-box" type="text" placeholder="Cari" autocomplete="off">
				                </div>
				            </div>
						</c:if>
				</div>
		</nav>
	</div>
	<ul id="slide-out" class="sidenav left">
		<c:if test="${user ne 'nope'}">
			<b><li style="background-color:#FFDF00;"><a href="premium.jsp">Premium</a></li></b>
		    <b><li><a href="profileView.jsp">Profile</a></li></b>
		    <b><li><a href="courses.html">Courses</a></li></b>
		    <b><li><a href="logout.jsp">Logout</a></li></b>
		</c:if>
		<c:if test="${user == 'nope'}">
		    <b><li><a class="modal-trigger" href="#mLogin">Login</a></li></b>
		</c:if>
		
  	</ul>

<jsp:invoke fragment="content"></jsp:invoke>
<footer class="page-footer deep-purple darken-3">
  <div class="container">
    <div class="row">
      <div class="col l6 s12">
        <h5 class="white-text">We Are Using Cloud!</h5>
        <p class="grey-text text-lighten-4">This website is a final project of Cloud Computing subject at Institut Sains Terpadu dan Teknologi Surabaya, Indonesia. We are consisted of 4 smart students! Agung Pribadi, Felicia Febriani, Giovano Purnomo, Irvan Ferdinan </p>
      </div>
      <div class="col l4 offset-l2 s12">
        <h5 class="white-text">Quick Links</h5>
        <ul>
          <li><a class="grey-text text-lighten-3" href="#!">About Us</a></li>
          <li><a class="grey-text text-lighten-3" href="#!">Terms and Conditions</a></li>
          <li><a class="grey-text text-lighten-3" href="#!">Courses List</a></li>
          <li><a class="grey-text text-lighten-3" href="#!">Instructor List</a></li>
        </ul>
      </div>
    </div>
  </div>
  <div class="footer-copyright">
    <div class="container">
    © 2019 Lynda Project
    </div>
  </div>
</footer>
<script>
$(document).ready(function() {
 $("#search-box").focusin(function() {
     $("#search-form").addClass("search-focus");
 });
 $("#search-box").focusout(function() {
     $("#search-form").removeClass("search-focus");
 });
 $("#search-icon").click(function() {
     $("#search-box").focus();
 })
});

$('#search-box').keydown(function(e) {
    if (e.keyCode == 13) {
        var query = $("#search-box").val().replace(/ /g, '+');
        location.href = "/cari.jsp?key=" + query;
    }
});
</script>
</body>
</html>