package edu.stts;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;

/**
 * Servlet implementation class AjaxMidtrans
 */
@WebServlet("/AjaxMidtrans")
public class AjaxMidtrans extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AjaxMidtrans() {
        super();
        // TODO Auto-generated constructor stub
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String senderEmail = "giovano1@mhs.stts.edu";
		final String passwordEmail = "giovano123.";
		JSONObject obj = new JSONObject(request.getParameter("object"));
		int iduser = Integer.parseInt((String)request.getParameter("iduser"));
		String paymentcode = obj.getString("payment_code");
		String transaksiid_midtrans = obj.getString("transaction_id");
		String email = (String)request.getParameter("email");
		String hargaString = obj.getString("gross_amount");
		int harga = Integer.parseInt(hargaString.substring(0, hargaString.length()-3));
		System.out.println("id: " + iduser);
		System.out.println("harga: "+obj.getString("gross_amount"));
		System.out.println("test: "+obj.getString("payment_code"));
		
		try {
			System.out.println("masuk ajax 0");
			Firestore db = FirestoreOptions.newBuilder().setProjectId("proyek-cc-lynda").setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream("lynda-310811-08c61c30aea2.json"))).build().getService();
			CollectionReference transaction = db.collection("transactions");
			Query query = transaction.whereGreaterThan("id", -1);
			int idTrans = -1;
			ApiFuture<QuerySnapshot> querySnapshot1 = query.get();
		    int temp1 = querySnapshot1.get().size()-1;
    		Query query3 = transaction.offset(temp1).limit(1);			    		
		    ApiFuture<QuerySnapshot> querySnapshot3 = query3.get();
		    List<QueryDocumentSnapshot> documents = querySnapshot3.get().getDocuments();
		    for (QueryDocumentSnapshot document : documents) {
				long longid = document.getLong("id");
				idTrans = (int) longid+1;						
			}
		    if(documents.size() == 0) {
		    	idTrans = 0;
		    }
		    System.out.println("masuk 2");
		    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			 Map<String, Object> c = new HashMap<>(); 
   			 c.put("id", idTrans);
   			 c.put("iduser", iduser);
   			 c.put("paymentcode", paymentcode);
   			 c.put("harga", harga);
   			 c.put("tipe", 0);
   			 c.put("date", timestamp);
   			 c.put("catatan", 0);
   			 c.put("accepted", 0);
   			 c.put("midtrans_trans_id", transaksiid_midtrans.toString());
   			 String iddoc = StringUtils.leftPad(idTrans+"", 4, "0"); 
   			 System.out.println("masuk ajax "+ iddoc);
   			 ApiFuture<WriteResult> writeResult =db.collection("transactions").document(iddoc).set(c);
   			System.out.println("masuk 3");
   			
   			Properties props = new Properties();
 			props.put("mail.smtp.auth", "true");
 			props.put("mail.smtp.starttls.enable", "true");
 			props.put("mail.smtp.host", "smtp.gmail.com");
 			props.put("mail.smtp.port", "587");
			Session session = Session.getInstance(props, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(senderEmail, passwordEmail);
				}
			});
			
			try {
				System.out.println("proses ke "+email);
				String msg = "Lakukan pembayaran dengan kode "+paymentcode+" dengan harga Rp "+harga+" di jenis pembayaran yang kamu pilih. Terima kasih!";
				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress("giovano1@mhs.stts.edu","Lynda Course"));
				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
				message.setSubject("Lakukan Pembayaran Untuk Mengisi Wallet");
				message.setContent(msg,"text/html");
				
				Transport.send(message);
				System.out.println("proses selesai");
			}catch(MessagingException e) {
				System.out.println("proses gagal");
				throw new RuntimeException(e);
			}
   			
			response.setStatus(HttpServletResponse.SC_OK);
			response.getWriter().write("1");
			response.getWriter().flush();
			response.getWriter().close();
		}catch(Exception e) {
			response.setStatus(HttpServletResponse.SC_OK);
			response.getWriter().write("-1");
			response.getWriter().flush();
			response.getWriter().close();
		}
		
		
	}

}
