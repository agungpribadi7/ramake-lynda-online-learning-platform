<%@page import="edu.stts.ChatClass"%>
<%@page import="edu.stts.Course"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.*"%>
<%@page import="java.sql.Timestamp" %>
<%@page import="edu.stts.WalletClass" %>
<%
WalletClass premium = new WalletClass();
ArrayList output = premium.getDurasiPremium(113);
if(output.size() > 0) {
	   int durasi = (int) output.get(0);
	   Date tanggal = (Date)output.get(1);
	   Calendar cal = Calendar.getInstance();
	   cal.setTimeZone(TimeZone.getTimeZone("GMT"));
	   cal.setTime(tanggal);
	   cal.add(Calendar.DATE, durasi);
	   Timestamp timestamp = new Timestamp(cal.getTimeInMillis());
}

%>


<li data-id="528">
  <a title="LOGOUT" href="/logout">
          <i class="glyphicon glyphicon-user"></i>LOGOUT</a>
  <div style="width: 350px;" data-width="350" data-class="menu-login-row">My other content</div>
</li>




<script src="jquery-3.3.1.min.js"></script>
<script>
$('[data-id="528"]').html("asd");


</script>