package edu.stts;

import java.io.FileInputStream;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;

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

public class WalletClass {
	final String senderEmail = "giovano1@mhs.stts.edu";
	final String passwordEmail = "giovano123.";
	public int getWalletFromId(int id) {
		int wallet = -1;
		try {
			Firestore db = FirestoreOptions.newBuilder().setProjectId("lynda-310811").setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream("lynda-310811-e0f6c225dbfe.json"))).build().getService();    
	        CollectionReference user = db.collection("users");
	        Query query = user.whereEqualTo("id", id);
	        ApiFuture<QuerySnapshot> future = query.get();
	        List<QueryDocumentSnapshot> docs = future.get().getDocuments();
	        
        	for (QueryDocumentSnapshot document : docs) {
        		wallet = document.getLong("wallet").intValue();
        		return wallet;
        	}
		}catch(Exception e) {
			System.out.println("error wallet");
		}
		return wallet;
	}
	
	public int beliPremium(int durasi, int iduser, int harga) {
		int output = -1;
		String iddoc = "";
		 System.out.println("Durasi  sebelum = "+durasi);
		try {
			Firestore db = FirestoreOptions.newBuilder().setProjectId("lynda-310811").setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream("lynda-310811-e0f6c225dbfe.json"))).build().getService();
			CollectionReference transaction = db.collection("transactions");
			Query query = transaction.whereEqualTo("iduser", iduser).whereEqualTo("accepted", 1).whereEqualTo("tipe", 1);		    		
		    ApiFuture<QuerySnapshot> querySnapshot = query.get();
		    List<QueryDocumentSnapshot> documents = querySnapshot.get().getDocuments();
		    int idTrans = -1;
		    int durasiAwal = -1;
		    if(documents.size() > 0) {
		    	int hargaAwal = 0;
		    	for (QueryDocumentSnapshot document : documents) {
		    		idTrans = document.getLong("id").intValue();
		    		durasiAwal = document.getLong("catatan").intValue();
		    		hargaAwal = document.getLong("harga").intValue();
		    	}
		    	hargaAwal += harga;
		    	durasi += durasiAwal;
		    	Map<String, Object> c = new HashMap<>(); 
	   			c.put("catatan", durasi);
	   			c.put("harga", hargaAwal);
	   			String iddocTrans = StringUtils.leftPad(idTrans+"", 4, "0"); 
	   			ApiFuture<WriteResult> writeResult =db.collection("transactions").document(iddocTrans).update(c);
		    }else {
		    	Query query2 = transaction.whereGreaterThan("id", -1);		    		
		    	ApiFuture<QuerySnapshot> querySnapshot1 = query2.get();
			    int temp1 = querySnapshot1.get().size()-1;
	    		Query query3 = transaction.offset(temp1).limit(1);			    		
			    ApiFuture<QuerySnapshot> querySnapshot3 = query3.get();
			    List<QueryDocumentSnapshot> documents2 = querySnapshot3.get().getDocuments();
		    	for (QueryDocumentSnapshot document : documents2) {
					long longid = document.getLong("id");
					idTrans = (int) longid+1;						
				}
			    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
				 Map<String, Object> c = new HashMap<>(); 
	   			 c.put("id", idTrans);
	   			 c.put("iduser", iduser);
	   			 c.put("paymentcode", "");
	   			 c.put("midtrans_trans_id", "");
	   			 c.put("harga", harga);
	   			 c.put("tipe", 1);
	   			 c.put("date", timestamp);
	   			 c.put("catatan", durasi);
	   			 c.put("accepted", 1);
	   			 iddoc = StringUtils.leftPad(idTrans+"", 4, "0"); 
	   			 ApiFuture<WriteResult> writeResult =db.collection("transactions").document(iddoc).set(c);
		    }
		    
   			CollectionReference user = db.collection("users");
			Query query2 = user.whereEqualTo("id", iduser);
			ApiFuture<QuerySnapshot> querySnapshot2 = query2.get();
			List<QueryDocumentSnapshot> documents2 = querySnapshot2.get().getDocuments();
			int wallet = 0;
			String email = "";
			for (QueryDocumentSnapshot document : documents2) {
				wallet = document.getLong("wallet").intValue();
				email = document.getString("email");
			}
			System.out.println(wallet);
			wallet -= harga;
			if(wallet < 0) {
				output = 2;
				db.collection("transactions").document(iddoc).delete();
			}
			else {
				Map<String, Object> c2 = new HashMap<>(); 
				 c2.put("wallet", wallet);
				 c2.put("premium", 1);
				 String id = StringUtils.leftPad(iduser+"", 4, "0"); 
				 db.collection("users").document(id).update(c2);
				 output = 1;
				 
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
					Date tanggal = new Date();
				    Calendar cal = Calendar.getInstance();
				    cal.setTimeZone(TimeZone.getTimeZone("GMT"));
				    cal.setTime(tanggal);
				    System.out.println("Durasi = "+durasi);
				    cal.add(Calendar.DATE, durasi);
				    Timestamp timestamp = new Timestamp(cal.getTimeInMillis());
					String msg = "Status premium kamu akan habis pada tanggal "+ timestamp +". Terima kasih!";
					Message message = new MimeMessage(session);
					message.setFrom(new InternetAddress("giovano1@mhs.stts.edu","Lynda Course"));
					message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
					message.setSubject("Payment Succeeded");
					message.setContent(msg,"text/html");
					
					Transport.send(message);
					System.out.println("proses selesai");
				}catch(MessagingException e) {
					System.out.println("proses gagal");
					throw new RuntimeException(e);
				}
			}
		}catch(Exception e) {
			System.out.println("error beli premium");
		}
		return output;
	}
	
	public ArrayList getDurasiPremium(int iduser) {
		ArrayList output = new ArrayList();
		try {
			Firestore db = FirestoreOptions.newBuilder().setProjectId("lynda-310811").setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream("lynda-310811-e0f6c225dbfe.json"))).build().getService();
			CollectionReference transaction = db.collection("transactions");
    		Query query = transaction.whereEqualTo("iduser", iduser).whereEqualTo("accepted", 1).whereEqualTo("tipe", 1);			    		
		    ApiFuture<QuerySnapshot> querySnapshot3 = query.get();
		    List<QueryDocumentSnapshot> documents = querySnapshot3.get().getDocuments();
		    for (QueryDocumentSnapshot document : documents) {
				output.add(document.getLong("catatan").intValue());			
				output.add(document.getDate("date"));
			}
		}
		catch(Exception e) {
			System.out.println("error durasi premium");
		}
		return output;
	}
	
	public String getDurasiPremiumDikurangiHariini(int iduser) {
		String sisahari = "";
		try {
		   ArrayList output = getDurasiPremium(iduser);
 		   int durasi = -1;
 		   
 		   Calendar cal;
 		   Timestamp timestamp;
 		   Timestamp timestampOneDayBefore;
 		   Timestamp sekarang = new Timestamp(System.currentTimeMillis());
 		   if(output.size() > 0) {
 			   durasi = (int) output.get(0);
 			   Date tanggal = (Date)output.get(1);
 			   cal = Calendar.getInstance();
 			   cal.setTimeZone(TimeZone.getTimeZone("GMT"));
 			   cal.setTime(tanggal);
 			   cal.add(Calendar.DATE, durasi);
 			   SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
 			   String formatted = format1.format(cal.getTime());
 			   sisahari = formatted;
 		   }
		}catch(Exception e) {
			
		}
		return sisahari;
	}
	
	public void disablePremium(int iduser) {
		
		try {
			Firestore db = FirestoreOptions.newBuilder().setProjectId("lynda-310811").setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream("lynda-310811-e0f6c225dbfe.json"))).build().getService();
			Map<String, Object> c = new HashMap<>(); 
			c.put("premium", 0);
			String id = StringUtils.leftPad(iduser+"", 4, "0"); 
			db.collection("users").document(id).update(c);
		}catch(Exception e) {
			System.out.println("error disable premium");
		}
	}
	
	
	
}
