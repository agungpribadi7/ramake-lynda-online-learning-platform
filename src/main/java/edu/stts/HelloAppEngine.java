package edu.stts;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;

@WebServlet(
    name = "HelloAppEngine",
    urlPatterns = {"/hello"}
)
public class HelloAppEngine extends HttpServlet {

	  private String emaillogin;
	  private String passlogin;
	  private String email;
	  private String name;
	  private String password;
	  private String cpassword;
	  private String emailforgot;
	  private String passwordbaru;
	  private String cpasswordbaru;
	  static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	  static SecureRandom rnd = new SecureRandom();
	  
	  public String getEmaillogin() {
		  return emaillogin;
	  }
	  public String getPasslogin() {
		  return passlogin;
	  }
	  public void setEmaillogin(String emaillogin) {
	      this.emaillogin = emaillogin;
	  }
	  public void setPasslogin(String passlogin) {
	      this.passlogin = passlogin;
	  }
	  public String getEmail() {
		  return email;
	  }
	  public String getName() {
		  return name;
	  }
	  public String getPassword() {
		  return password;
	  }
	  public String getCpassword() {
		  return cpassword;
	  }
	  public void setEmail(String email) {
	      this.email = email;
	  }
	  public void setName(String name) {
	      this.name = name;
	  }
	  public void setPassword(String password) {
	      this.password = password;
	  }
	  public void setCpassword(String cpassword) {
	      this.cpassword = cpassword;
	  }
	  public String getEmailforgot() {
		  return emailforgot;
	  }
	  public void setEmailforgot(String emailforgot) {
		  this.emailforgot = emailforgot;
	  }
	  public String getPasswordbaru() {
		  return passwordbaru;
	  }
	  public void setPasswordbaru(String passwordbaru) {
		  this.passwordbaru = passwordbaru;
	  }
	  public String getCpasswordbaru() {
		  return cpasswordbaru;
	  }
	  public void setCpasswordbaru(String cpasswordbaru) {
		  this.cpasswordbaru = cpasswordbaru;
	  }
	  
	  String randomString(int len){
	     StringBuilder sb = new StringBuilder(len);
	     for( int i = 0; i < len; i++ ) 
	        sb.append(AB.charAt( rnd.nextInt(AB.length()) ) );
	     return sb.toString();
	  }
	  public String checkLogin(String emaillogin, String passlogin) {
		  String templogin = "";
		  Firestore db;
		  try {
			  if(!emaillogin.equals("") && !passlogin.equals("")) {
				  db = FirestoreOptions.newBuilder().setProjectId("lynda-310811").setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream("lynda-310811-e0f6c225dbfe.json"))).build().getService();
				  CollectionReference users = db.collection("users");
				  Query query = users.whereEqualTo("email", emaillogin).whereEqualTo("password", md5.getMd5(passlogin))/*.whereEqualTo("banned", "0")*/;
				  ApiFuture<QuerySnapshot> querySnapshot = query.get();
				  int tamp = querySnapshot.get().size();
			    	if(tamp > 0) {
			    		String ban="";
						List<QueryDocumentSnapshot> documents = querySnapshot.get().getDocuments();
						for (QueryDocumentSnapshot documentss : documents) {
							ban = documentss.getString("banned");					
						}
						if(ban.equalsIgnoreCase("1")) {
							templogin = "Anda telah di ban!";
						}else {
							templogin = "Berhasil Login!";
						}
			    	}else {
			    		templogin = "Gagal Login, Email atau Password Tidak Ada!";
			    	}
			    	db.close();
			  }
			  
		  }catch (Exception e) {
			  e.printStackTrace();
		  }		  
		  return templogin;
	  }
	  public String checkUsers(String email, String name, String password, String cpassword) {
		  final String senderEmail = "giovano1@mhs.stts.edu";
		  final String passwordEmail = "giovano123.";
		  Firestore db;
		  String temp = "";
		  int id = 0;
		  try {
		        db = FirestoreOptions.newBuilder().setProjectId("lynda-310811").setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream("lynda-310811-e0f6c225dbfe.json"))).build().getService();    
		        CollectionReference users = db.collection("users");
		        Query query = users.whereEqualTo("email", email);
		        ApiFuture<QuerySnapshot> querySnapshot = query.get();
		        int tamp = querySnapshot.get().size();
		    	if(tamp > 0) {
		    		temp = "Register Gagal, Email Sudah Terdaftar!";		    			
		    	}else if(!email.equals("") && !name.equals("") && !password.equals("") && !cpassword.equals("")){
		    		if(password.equals(cpassword)) {
		    			Query query1 = users.whereGreaterThan("id", -1);
			    		ApiFuture<QuerySnapshot> querySnapshot1 = query1.get();
					    int temp1 = querySnapshot1.get().size()-1;
			    			
			    		Query query3 = users.orderBy("id").offset(temp1).limit(1);			    		
					    ApiFuture<QuerySnapshot> querySnapshot3 = query3.get();
					    List<QueryDocumentSnapshot> documents = querySnapshot3.get().getDocuments();
						for (QueryDocumentSnapshot documentss : documents) {
							long longid = documentss.getLong("id");
							id = (int) longid+1;						
						}
						Map<String, Object> usersbaru = new HashMap<>();
			    		usersbaru.put("apa_instructor", "0");
			    		usersbaru.put("banned", "0");
			    		usersbaru.put("email",email);		
			    		usersbaru.put("foto","none.jpg");
			    		usersbaru.put("id", id);
			    		usersbaru.put("nama", name);
			    		usersbaru.put("password", md5.getMd5(password));
			    		usersbaru.put("tokenLupa", "");
			    		usersbaru.put("verifikasi", "0");
			    		usersbaru.put("kodeverif",randomString(10));
			    		usersbaru.put("wallet", 0);
			    		usersbaru.put("premium", 0);
			    		CollectionReference temp2 = db.collection("users");
			    		Query query2 = temp2.whereEqualTo("email", email);
			    		ApiFuture<QuerySnapshot> querySnapshot2 = query2.get();
			    		int total  = querySnapshot2.get().size();			    		
			    		if(total <= 0){
			    			String param = "";
			    			if(id<10) {
			    				param = "000"+id+"";	
			    			}else if(id<100) {
			    				param = "00"+id+"";	
			    			}else if(id<1000){
			    				param = "0"+id+"";	
			    			}else {
			    				param = id+"";
			    			}
			    			ApiFuture<WriteResult> writeResult = db.collection("users").document(param).set(usersbaru);
			    			
			    			//email
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
			    			String kodeverif = "";
			    			  try {
			    			        db = FirestoreOptions.newBuilder().setProjectId("lynda-310811").setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream("lynda-310811-e0f6c225dbfe.json"))).build().getService();    
			    			        CollectionReference userss = db.collection("users");
			    			        Query queryy = userss.whereEqualTo("email", email);
			    			        ApiFuture<QuerySnapshot> querySnapshott = queryy.get();
			    			        List<QueryDocumentSnapshot> documentss = querySnapshott.get().getDocuments();
			    			        for (QueryDocumentSnapshot document : documentss) {
			    			        	kodeverif = document.getString("kodeverif");		
			    					}
			    			  }
			    			  catch(Exception e) {
			    				  System.out.println("error");
			    			  }
			    			try {
			    				String link="https://lynda-310811.appspot.com/home.jsp?token="+kodeverif;
			    				String msg = "Terima kasih "+name+", telah mendaftar di Lynda Course." + "<br>"+"Silahkan melakukan verifikasi email, berikut merupakan link untuk verifikasi: "+"<a href='"+link+"'>https://lynda-310811.appspot.com/home.jsp?token="+kodeverif+"</a>";
			    				Message message = new MimeMessage(session);
			    				message.setFrom(new InternetAddress("giovano1@mhs.stts.edu","Lynda Course"));
			    				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
			    				message.setSubject("Verifikasi Email Lynda");
			    				message.setContent(msg,"text/html");
			    				
			    				Transport.send(message);
			    				temp = "Berhasil Register!";
			    			}catch(MessagingException e) {
			    				System.out.println(e);
			    				throw new RuntimeException(e);
			    			}
			    		}
		    		}		    		
		    	}
		    	db.close();
	        }catch (Exception e) {
	            e.printStackTrace();
	        }
		  return temp;
	  }
	  
	  public String checkVerif(String token) {
		  String temp = "";
		  Firestore db;
		  try {	
			  	db = FirestoreOptions.newBuilder().setProjectId("lynda-310811").setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream("lynda-310811-e0f6c225dbfe.json"))).build().getService();    
		        CollectionReference users = db.collection("users");
		        Query query = users.whereEqualTo("kodeverif", token).whereEqualTo("verifikasi", "0");
		        ApiFuture<QuerySnapshot> querySnapshot = query.get();
		        List<QueryDocumentSnapshot> documents = querySnapshot.get().getDocuments();
		        String documentID = documents.get(0).getId();
			    int tamp = querySnapshot.get().size();
			    if(tamp>0){
			    	Map<String, Object> updateVerif = new HashMap<>();
			    	updateVerif.put("verifikasi","1");
			    	ApiFuture<WriteResult> writeResult = db.collection("users").document(documentID).update(updateVerif);
			    	db.close();
					temp="1";
			    }else {
			    	temp="0";
			    }
	        }catch (Exception e) {
	            e.printStackTrace();
	        }
		  return temp;
	  }
	  
	  public int apakahInstructor(String email) {
		  Firestore db;
		  String temp = "";
		  int apa = 0;
		  try {
		        db = FirestoreOptions.newBuilder().setProjectId("lynda-310811").setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream("lynda-310811-e0f6c225dbfe.json"))).build().getService();    
		        CollectionReference users = db.collection("users");
		        Query query = users.whereEqualTo("email", email).limit(1);
		        ApiFuture<QuerySnapshot> querySnapshot = query.get();
		        List<QueryDocumentSnapshot> documents = querySnapshot.get().getDocuments();
		        for (QueryDocumentSnapshot document : documents) {
					apa = Integer.parseInt(document.getString("apa_instructor"));		
				}
		        db.close();
		  }
		  catch(Exception e) {
			  System.out.println("error");
		  }
		  
		  return apa;
	  }
	  
	  public ArrayList<String>[] getRandomTeacher(Integer limit) {
		  Firestore db;
		  ArrayList<String>[] output = new ArrayList[3]; 
		  
          for (int i = 0; i < 3; i++) { 
              output[i] = new ArrayList<String>(); 
          } 
		  try {
		        db = FirestoreOptions.newBuilder().setProjectId("lynda-310811").setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream("lynda-310811-e0f6c225dbfe.json"))).build().getService();    
	    		ApiFuture<QuerySnapshot> future = db.collection("instructors").get();
	    		int total  = future.get().size();
	    		for(int i = 0; i < limit; i++) {
	    			Random r = new Random();
				  	int low = 0;
				  	int high = total;
				  	int result = r.nextInt(high-low) + low;
				  	int id_email=0;
			        CollectionReference instruktor = db.collection("instructors");
			        Query query = instruktor.whereEqualTo("id", result);
			        ApiFuture<QuerySnapshot> querySnapshot = query.get();
			        List<QueryDocumentSnapshot> documents = querySnapshot.get().getDocuments();
			        
			        if(documents.size() == 0) {
			        	i -=1;
			        }else {
			        	for (QueryDocumentSnapshot document : documents) {
			                id_email=document.getLong("id_detail_email").intValue();
						}
				        
				        CollectionReference users = db.collection("users");
				        Query query1 = users.whereEqualTo("id", id_email);
				        ApiFuture<QuerySnapshot> querySnapshot1 = query1.get();
				        List<QueryDocumentSnapshot> documents1 = querySnapshot1.get().getDocuments();
				        for (QueryDocumentSnapshot document : documents1) {
				        	System.out.println("masuk random");				        	output[0].add(document.getString("nama"));
			                output[1].add(document.getString("foto"));
			                output[2].add(document.get("id")+"");
						}
			        }
	    		}
		  }
		  catch(Exception e) {
			  System.out.println("error");
		  }
		  return output;
	  }
	  
	  public int getIdUser(String email) {
		  Firestore db;
		  String temp = "";
		  int id = -1;
		  try {
		        db = FirestoreOptions.newBuilder().setProjectId("lynda-310811").setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream("lynda-310811-e0f6c225dbfe.json"))).build().getService();    
		        CollectionReference users = db.collection("users");
		        System.out.println(email+" email");
		        Query query = users.whereEqualTo("email", email).limit(1);
		        ApiFuture<QuerySnapshot> querySnapshot = query.get();
		        List<QueryDocumentSnapshot> documents = querySnapshot.get().getDocuments();
		        for (QueryDocumentSnapshot document : documents) {
					long longid = 
					id = document.getLong("id").intValue();		
				}
		        db.close();
		  }
		  catch(Exception e) {
			  System.out.println("error");
		  }
		 
		  return id;
	  }
	  
	  public int getInfoAcceptedInstructor(int iduser) {
		  int output = 0;
		  Firestore db;
		  try {
			  	System.out.println(iduser+" iduser");
		        db = FirestoreOptions.newBuilder().setProjectId("lynda-310811").setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream("lynda-310811-e0f6c225dbfe.json"))).build().getService();    
		        CollectionReference instructor = db.collection("instructors");
		        Query query = instructor.whereEqualTo("id_detail_email", iduser);
		        ApiFuture<QuerySnapshot> querySnapshot = query.get();
		        List<QueryDocumentSnapshot> documents = querySnapshot.get().getDocuments();
		        for (QueryDocumentSnapshot document : documents) {
					output = Integer.parseInt(document.getString("accepted"));		
				}
		        db.close();
		  }
		  catch(Exception e) {
			  System.out.println("error get info accepted instructor");
		  }
		  return output;
	  }
	  
	  public String getVerifikasi(String email) {
		  Firestore db;
		  String kodeverif = "";
		  try {
		        db = FirestoreOptions.newBuilder().setProjectId("lynda-310811").setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream("lynda-310811-e0f6c225dbfe.json"))).build().getService();    
		        CollectionReference users = db.collection("users");
		        Query query = users.whereEqualTo("email", email);
		        ApiFuture<QuerySnapshot> querySnapshot = query.get();
		        List<QueryDocumentSnapshot> documents = querySnapshot.get().getDocuments();
		        for (QueryDocumentSnapshot document : documents) {
		        	kodeverif = document.getString("verifikasi");		
				}
		        db.close();
		  }
		  catch(Exception e) {
			  System.out.println("error");
		  }
		  return kodeverif;
	  }

	  public String checkEmaillupa(String email) {
		  final String senderEmail = "giovano1@mhs.stts.edu";
		  final String passwordEmail = "giovano123.";
		  String temp = "";
		  Firestore db;
		  try {	
			  	db = FirestoreOptions.newBuilder().setProjectId("lynda-310811").setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream("lynda-310811-e0f6c225dbfe.json"))).build().getService();    
		        CollectionReference users = db.collection("users");
		        Query query = users.whereEqualTo("email", email);
			    ApiFuture<QuerySnapshot> querySnapshot = query.get();
			    int tamp = querySnapshot.get().size();
			    if(tamp>0){
			    	String token=randomString(10);
			    	int idprofile = -1;
			    	Map<String, Object> usersada = new HashMap<>();
					ApiFuture<QuerySnapshot> querySnapshot1 = query.get();
			    	List<QueryDocumentSnapshot> documents = querySnapshot1.get().getDocuments();
					for (QueryDocumentSnapshot documentss : documents) {
						
						idprofile = documentss.getLong("id").intValue();
						usersada.put("apa_instructor", documentss.getString("apa_instructor"));
						usersada.put("banned", documentss.getString("banned"));
						usersada.put("email",email);		
						usersada.put("foto",documentss.getString("foto"));
						usersada.put("id", idprofile);
						usersada.put("nama", documentss.getString("nama"));
						usersada.put("password", documentss.getString("password"));
						usersada.put("tokenLupa",token);
						usersada.put("verifikasi",documentss.getString("verifikasi"));
					}
					String id_profile = StringUtils.leftPad(idprofile+"", 4, "0");
					db.collection("users").document(id_profile).update(usersada);
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
	    				String link="http://lynda-310811.appspot.com/lupapassword.jsp?token="+token;
	    				String msg = "Klik link ini untuk mengganti password anda" + "<br>"+"Berikut merupakan link untuk mengganti password: "
	    						+ "<a href='"+link+"'>http://lynda-310811.appspot.com/lupapassword.jsp?token="+token+"</a>";
	    				Message message = new MimeMessage(session);
	    				message.setFrom(new InternetAddress("giovano1@mhs.stts.edu","Lynda Course"));
	    				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
	    				message.setSubject("Ganti Password");
	    				message.setContent(msg,"text/html");
	    				
	    				Transport.send(message);
	    				temp = "1";
	    			}catch(MessagingException e) {
	    				throw new RuntimeException(e);
	    			}
			    }else {
			    	temp="0";
			    }
			    db.close();
	        }catch (Exception e) {
	            e.printStackTrace();
	        }
		  return temp;
	  }
	  
	  public String checkPasswordbaru(String token, String password) {
		  String temp = "";
		  Firestore db;
		  try {	
			  	db = FirestoreOptions.newBuilder().setProjectId("lynda-310811").setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream("lynda-310811-e0f6c225dbfe.json"))).build().getService();    
		        CollectionReference users = db.collection("users");
		        Query query = users.whereEqualTo("tokenLupa", token);
			    ApiFuture<QuerySnapshot> querySnapshot = query.get();
			    int tamp = querySnapshot.get().size();
			    if(tamp>0){
			    	int idprofile = -1;
			    	Map<String, Object> usersada = new HashMap<>();
					ApiFuture<QuerySnapshot> querySnapshot1 = query.get();
			    	List<QueryDocumentSnapshot> documents = querySnapshot1.get().getDocuments();
					for (QueryDocumentSnapshot documentss : documents) {
						idprofile = documentss.getLong("id").intValue();
						usersada.put("apa_instructor", documentss.getString("apa_instructor"));
						usersada.put("banned", documentss.getString("banned"));
						usersada.put("email",documentss.getString("email"));		
						usersada.put("foto",documentss.getString("foto"));
						usersada.put("id", idprofile);
						usersada.put("nama", documentss.getString("nama"));
						usersada.put("password", md5.getMd5(password));
						usersada.put("tokenLupa","");
						usersada.put("verifikasi",documentss.getString("verifikasi"));
					}
					String id_profile = StringUtils.leftPad(idprofile+"", 4, "0");
					db.collection("users").document(id_profile).update(usersada);
					temp="1";
			    }else {
			    	temp="0";
			    }
			    db.close();
	        }catch (Exception e) {
	            e.printStackTrace();
	        }
		  return temp;
	  }
	
}