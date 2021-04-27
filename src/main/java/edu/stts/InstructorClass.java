package edu.stts;

import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.StringUtils;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.cloud.firestore.Query.Direction;

public class InstructorClass {
	protected int accepted,banned,id,email_id,verification;
	protected String description,email,photo,intro,name;
	Firestore db;
	public String registerInstructor(String nama, String email, String password, String link) throws UnsupportedEncodingException {
		final String senderEmail = "giovano1@mhs.stts.edu";
		final String passwordEmail = "giovano123.";
		String temp = "";
		 try { 
		    db = FirestoreOptions.newBuilder().setProjectId("lynda-310811").setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream("lynda-310811-e0f6c225dbfe.json"))).build().getService();    
			CollectionReference users = db.collection("users");
	        Query query = users.whereEqualTo("email", email);
	        ApiFuture<QuerySnapshot> querySnapshot = query.get();
	        
	        int iduser = -1;
	        int tamp = querySnapshot.get().size();
	    	if(tamp > 0) {
	    		temp = "Email had already been registered before!";		    			
	    	}else if(!email.equals("") && !nama.equals("") && !password.equals("") && !link.equals("")){
	    			
	    			Query query0 = users.whereGreaterThan("id", -1);
		    		ApiFuture<QuerySnapshot> querySnapshot0 = query0.get();
				    int temp1 = querySnapshot0.get().size()-1;
		    			
		    		Query query01 = users.orderBy("id").offset(temp1).limit(1);			    		
				    ApiFuture<QuerySnapshot> querySnapshot3 = query01.get();
				    List<QueryDocumentSnapshot> documents = querySnapshot3.get().getDocuments();
					for (QueryDocumentSnapshot documentss : documents) {
						long longid = documentss.getLong("id");
						iduser = (int) longid+1;						
					}
					Map<String, Object> usersbaru = new HashMap<>();
		    		usersbaru.put("apa_instructor", "1");
		    		usersbaru.put("banned", "0");
		    		usersbaru.put("email",email);		
		    		usersbaru.put("foto","none.jpg");
		    		usersbaru.put("id", iduser);
		    		usersbaru.put("nama", nama);
		    		usersbaru.put("password", md5.getMd5(password));
		    		usersbaru.put("premium", 0);
		    		usersbaru.put("verifikasi", "1");
		    		usersbaru.put("kodeverif", "");
		    		usersbaru.put("tokenLupa", "");
		    		usersbaru.put("wallet", 0);
		    		String iddokumen0 = StringUtils.leftPad(iduser+"", 4, "0"); 
		    		ApiFuture<WriteResult> writeResult = db.collection("users").document(iddokumen0).set(usersbaru);
		    		
		    		CollectionReference instructor = db.collection("instructors");
					 Query query1 = instructor.whereGreaterThan("id", -1);
					 int id = -1;
					 ApiFuture<QuerySnapshot> querySnapshot1 = query1.get();
				     int temp2 = querySnapshot1.get().size()-1;
		    			
		    		 Query query3 = instructor.orderBy("id").offset(temp2).limit(1);			    		
				     ApiFuture<QuerySnapshot> querySnapshot4 = query3.get();
				     List<QueryDocumentSnapshot> documents1 = querySnapshot4.get().getDocuments();
					 for (QueryDocumentSnapshot documentss : documents1) {
						long longid = documentss.getLong("id");
						id = (int) longid+1;						
					 }
					 
					 Map<String, Object> c = new HashMap<>(); 
					 c.put("accepted", "0");
					 c.put("deskripsi", "");
					 c.put("id", id);
					 c.put("id_detail_email", iduser);
					 c.put("intro", link);
					 String iddokumen1 = StringUtils.leftPad(id+"", 4, "0"); 
					 System.out.println(iddokumen1);
					 ApiFuture<WriteResult> writeResult1 =db.collection("instructors").document(iddokumen1).set(c); 
		    	}
		    	else {
		    		temp = "Fill All Input Fields";
		    	}
		    	db.close();
		 } 
		 catch (Exception e) { 
			 e.printStackTrace(); 
		 }
		 if(temp.equals("")) {
			 System.out.println("masuk kirim email");
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
				String msg = "Your Registration Application will be processed and if you are eligble to be instructor, you will receive our reply within 24 hours at most.";
				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress("giovano1@mhs.stts.edu","Lynda Course"));
				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
				message.setSubject("Instructor Registration at Gusanova");
				message.setContent(msg,"text/html");
				
				Transport.send(message);
				temp = "1";
				 System.out.println("proses selesai");
			}catch(MessagingException e) {
				System.out.println("proses gagal");
				throw new RuntimeException(e);
			}
		}
		return temp;
	}
	
	public int getIdDetailEmailByName(String name) {
		int id = -1;
		try {
			db = FirestoreOptions.newBuilder().setProjectId("lynda-310811").setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream("lynda-310811-e0f6c225dbfe.json"))).build().getService();    
			CollectionReference instructor = db.collection("instructors");
	        Query query = instructor.whereEqualTo("nama", name).limit(1);
	        ApiFuture<QuerySnapshot> querySnapshot = query.get();

		    List<QueryDocumentSnapshot> documents = querySnapshot.get().getDocuments();
			for (QueryDocumentSnapshot documentss : documents) {
				id = documentss.getLong("id_detail_email").intValue();			
			}
		}catch(Exception e) {
			System.out.println("error");
		}
		return id;
	}
	
}
