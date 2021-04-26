package edu.stts;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Timestamp;
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
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
 * Servlet implementation class adminAjax
 */

@WebServlet("/adminAjax")
public class adminAjax extends HttpServlet {
	Firestore db;
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public adminAjax() {
        super();
        // TODO Auto-generated constructor stub
    }


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String type = request.getParameter("type");
		String Sid = request.getParameter("id");
		int id = Integer.parseInt(Sid);
		final String senderEmail = "giovano1@mhs.stts.edu";
		final String passwordEmail = "giovano123.";
		if(type.equals("banInstructor")) {
			try {
			    db = FirestoreOptions.newBuilder().setProjectId("lynda-310811").setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream("lynda-310811-08c61c30aea2.json"))).build().getService();
			    CollectionReference userData = db.collection("users");
			    Query query = userData.whereEqualTo("id", id);
			    ApiFuture<QuerySnapshot> querySnapshot = query.get();
			    List<QueryDocumentSnapshot> asd = querySnapshot.get().getDocuments();
			    String documentID = asd.get(0).getId();
			    CollectionReference courseData = db.collection("courses");
			    Query queryCourse = courseData.whereEqualTo("index_email", id);
			    ApiFuture<QuerySnapshot> querySnapshotCourse = query.get();
			    List<QueryDocumentSnapshot> asdf = querySnapshotCourse.get().getDocuments();
			    for(QueryDocumentSnapshot course : asdf) {
			    	Map<String, Object> bannedCourse = new HashMap<>();
				    bannedCourse.put("ban", 1);
				    ApiFuture<WriteResult> writeResult = db.collection("courses").document(course.getId()).update(bannedCourse);
			    }
			    Map<String, Object> updatedInstructor = new HashMap<>();
			    updatedInstructor.put("banned", "1");
			    ApiFuture<WriteResult> writeResult = db.collection("users").document(documentID).update(updatedInstructor);
			    db.close();
			}
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(type.equals("unbanInstructor")) {
			try {
			    db = FirestoreOptions.newBuilder().setProjectId("lynda-310811").setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream("lynda-310811-08c61c30aea2.json"))).build().getService();
			    CollectionReference userData = db.collection("users");
			    Query query = userData.whereEqualTo("id", id);
			    ApiFuture<QuerySnapshot> querySnapshot = query.get();
			    List<QueryDocumentSnapshot> asd = querySnapshot.get().getDocuments();
			    String documentID = asd.get(0).getId();
			    CollectionReference courseData = db.collection("courses");
			    Query queryCourse = courseData.whereEqualTo("index_email", id);
			    ApiFuture<QuerySnapshot> querySnapshotCourse = query.get();
			    List<QueryDocumentSnapshot> asdf = querySnapshotCourse.get().getDocuments();
			    for(QueryDocumentSnapshot course : asdf) {
			    	Map<String, Object> bannedCourse = new HashMap<>();
				    bannedCourse.put("ban", 0);
				    ApiFuture<WriteResult> writeResult = db.collection("courses").document(course.getId()).update(bannedCourse);
			    }
			    Map<String, Object> updatedInstructor = new HashMap<>();
			    updatedInstructor.put("banned", "0");
			    ApiFuture<WriteResult> writeResult = db.collection("users").document(documentID).update(updatedInstructor);
			    db.close();
			}
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(type.equals("banStudent")) {
			try {
			    db = FirestoreOptions.newBuilder().setProjectId("lynda-310811").setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream("lynda-310811-08c61c30aea2.json"))).build().getService();
			    CollectionReference userData = db.collection("users");
			    Query query = userData.whereEqualTo("id", id);
			    ApiFuture<QuerySnapshot> querySnapshot = query.get();
			    List<QueryDocumentSnapshot> asd = querySnapshot.get().getDocuments();
			    String documentID = asd.get(0).getId();
			    Map<String, Object> updatedStudent = new HashMap<>();
			    updatedStudent.put("banned", "1");
			    ApiFuture<WriteResult> writeResult = db.collection("users").document(documentID).update(updatedStudent);
			    db.close();
			}
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(type.equals("unbanStudent")) {
			try {
			    db = FirestoreOptions.newBuilder().setProjectId("lynda-310811").setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream("lynda-310811-08c61c30aea2.json"))).build().getService();
			    CollectionReference userData = db.collection("users");
			    Query query = userData.whereEqualTo("id", id);
			    ApiFuture<QuerySnapshot> querySnapshot = query.get();
			    List<QueryDocumentSnapshot> asd = querySnapshot.get().getDocuments();
			    String documentID = asd.get(0).getId();
			    Map<String, Object> updatedStudent = new HashMap<>();
			    updatedStudent.put("banned", "0");
			    ApiFuture<WriteResult> writeResult = db.collection("users").document(documentID).update(updatedStudent);
			    db.close();
			}
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(type.equals("approveInstructor")) {
			try {
			    db = FirestoreOptions.newBuilder().setProjectId("lynda-310811").setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream("lynda-310811-08c61c30aea2.json"))).build().getService();
			    CollectionReference userData = db.collection("instructors");
			    Query query = userData.whereEqualTo("id", id);
			    ApiFuture<QuerySnapshot> querySnapshot = query.get();
			    List<QueryDocumentSnapshot> asd = querySnapshot.get().getDocuments();
			    String documentID = asd.get(0).getId();
			    CollectionReference userDataX = db.collection("users");
			    Query queryX = userDataX.whereEqualTo("id", asd.get(0).getLong("id_detail_email"));
			    ApiFuture<QuerySnapshot> querySnapshotX = queryX.get();
			    List<QueryDocumentSnapshot> asdf = querySnapshotX.get().getDocuments();
			    String email = asdf.get(0).getString("email");
			    Map<String, Object> updated = new HashMap<>();
			    updated.put("accepted", "1");
			    ApiFuture<WriteResult> writeResult = db.collection("instructors").document(documentID).update(updated);
			    db.close();
			    
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
			   
			    String msg = "Your Instructor License Has Been Verified" + "<br>"+"You can now upload videos of your teachings to Lynda Course.<br>Thanks for Joining Us!";
				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress("giovano1@mhs.stts.edu","Lynda Course"));
				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
				message.setSubject("Instructor License Message");
				message.setContent(msg,"text/html");
				
				Transport.send(message);
			}
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(type.equals("disapproveInstructor")) {
			try {
			 	db = FirestoreOptions.newBuilder().setProjectId("lynda-310811").setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream("lynda-310811-08c61c30aea2.json"))).build().getService();
			    CollectionReference userData = db.collection("instructors");
			    Query query = userData.whereEqualTo("id", id);
			    ApiFuture<QuerySnapshot> querySnapshot = query.get();
			    List<QueryDocumentSnapshot> asd = querySnapshot.get().getDocuments();
			    String documentID = asd.get(0).getId();
			    CollectionReference userDataX = db.collection("users");
			    Query queryX = userDataX.whereEqualTo("id", asd.get(0).getLong("id_detail_email"));
			    ApiFuture<QuerySnapshot> querySnapshotX = queryX.get();
			    List<QueryDocumentSnapshot> asdf = querySnapshotX.get().getDocuments();
			    String email = asdf.get(0).getString("email");
			    Map<String, Object> updated = new HashMap<>();
			    updated.put("accepted", "2");
			    ApiFuture<WriteResult> writeResult = db.collection("instructors").document(documentID).update(updated);
			    db.close();
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
			   
			    String msg = "Your Instructor License Has Been Rejected" + "<br>"+"Sorry, You're not verified enough to be one of our Instructors. Try again next time.";
				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress("giovano1@mhs.stts.edu","Lynda Course"));
				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
				message.setSubject("Instructor License Message");
				message.setContent(msg,"text/html");
				
				Transport.send(message);
			}
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(type.equals("changeTopic")) {
			String name = request.getParameter("name");
			try {
			    db = FirestoreOptions.newBuilder().setProjectId("lynda-310811").setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream("lynda-310811-08c61c30aea2.json"))).build().getService();
			    CollectionReference topicData = db.collection("topic");
			    Query query = topicData.whereEqualTo("id", id);
			    ApiFuture<QuerySnapshot> querySnapshot = query.get();
			    List<QueryDocumentSnapshot> asd = querySnapshot.get().getDocuments();
			    String documentID = asd.get(0).getId();	
			    String nameX = asd.get(0).getString("nama");
			    Map<String, Object> updatedTopic = new HashMap<>();
			    updatedTopic.put("nama", name);
			    ApiFuture<WriteResult> writeResultX = db.collection("topic").document(documentID).update(updatedTopic);
			    
			    //Course Change
			    
			    CollectionReference courseData = db.collection("courses");
			    Query queryCourse = courseData.whereEqualTo("topic", nameX);
			    ApiFuture<QuerySnapshot> querySnapshotCourse = queryCourse.get();
			    List<QueryDocumentSnapshot> asdf = querySnapshotCourse.get().getDocuments();
			    for(QueryDocumentSnapshot course : asdf) {
			    	Map<String, Object> changedCourse = new HashMap<>();
				    changedCourse.put("topic", name);
				    ApiFuture<WriteResult> writeResult = db.collection("courses").document(course.getId()).update(changedCourse);
			    }
			    
			    //Subtopic Change
			    
			    CollectionReference subData = db.collection("subtopic");
			    Query querySub = subData.whereEqualTo("idtopic", nameX);
			    ApiFuture<QuerySnapshot> querySnapshotSub = querySub.get();
			    List<QueryDocumentSnapshot> asdfx = querySnapshotSub.get().getDocuments();
			    for(QueryDocumentSnapshot subx : asdfx) {
			    	Map<String, Object> csubx = new HashMap<>();
				    csubx.put("idtopic", name);
				    ApiFuture<WriteResult> writeResult = db.collection("subtopic").document(subx.getId()).update(csubx);
			    }
			    db.close();
			}
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(type.equals("changeSubtopic")) {
			String name = request.getParameter("name");
			try {
			    db = FirestoreOptions.newBuilder().setProjectId("lynda-310811").setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream("lynda-310811-08c61c30aea2.json"))).build().getService();
			    CollectionReference topicData = db.collection("subtopic");
			    Query query = topicData.whereEqualTo("id", id);
			    ApiFuture<QuerySnapshot> querySnapshot = query.get();
			    List<QueryDocumentSnapshot> asd = querySnapshot.get().getDocuments();
			    String documentID = asd.get(0).getId();	
			    String nameX = asd.get(0).getString("nama");
			    Map<String, Object> updatedTopic = new HashMap<>();
			    updatedTopic.put("nama", name);
			    ApiFuture<WriteResult> writeResultX = db.collection("subtopic").document(documentID).update(updatedTopic);
			    
			    //Course Change
			    
			    CollectionReference courseData = db.collection("courses");
			    Query queryCourse = courseData.whereEqualTo("subtopic", nameX);
			    ApiFuture<QuerySnapshot> querySnapshotCourse = queryCourse.get();
			    List<QueryDocumentSnapshot> asdf = querySnapshotCourse.get().getDocuments();
			    for(QueryDocumentSnapshot course : asdf) {
			    	Map<String, Object> changedCourse = new HashMap<>();
				    changedCourse.put("subtopic", name);
				    ApiFuture<WriteResult> writeResult = db.collection("courses").document(course.getId()).update(changedCourse);
			    }
			    db.close();
			}
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(type.equals("addSubtopic")) {
			String name = request.getParameter("name");
			String header = request.getParameter("header");
			int forId = -1;
			String forDoc ="";
			try {
			    db = FirestoreOptions.newBuilder().setProjectId("lynda-310811").setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream("lynda-310811-08c61c30aea2.json"))).build().getService();
			    
			    CollectionReference topicDataX = db.collection("topic");
			    Query queryXX = topicDataX.whereEqualTo("nama", header);
			    ApiFuture<QuerySnapshot> querySnapshotXX = queryXX.get();
			    List<QueryDocumentSnapshot> asdXX = querySnapshotXX.get().getDocuments();
			    long idTopic = asdXX.get(0).getLong("id");
			    
			    CollectionReference topicData = db.collection("subtopic");
			    Query query = topicData.whereGreaterThan("id", -1);	   
			    ApiFuture<QuerySnapshot> querySnapshot = query.get();
			    int temp = querySnapshot.get().size() - 1;
			    
			    Query queryX = topicData.offset(temp).limit(1);
			    ApiFuture<QuerySnapshot> queryXSnapshot = queryX.get();
			    List<QueryDocumentSnapshot> documents = queryXSnapshot.get().getDocuments();
			    String doc = documents.get(0).getId();
			    int docId = Integer.parseInt(doc);
				for (QueryDocumentSnapshot document : documents) {
					long longid = document.getLong("id");
					forId = (int) longid + 1;
				}
				
				if(docId < 10)
					forDoc = "000" + (docId+1) + "";
				else if(docId < 100)
					forDoc = "00" + (docId+1) + "";
				else if(docId < 1000)
					forDoc = "0" + (docId+1) + "";
				else if(docId >= 1000)
					forDoc = (docId+1) + "";
				
			    Map<String, Object> newsubtopic = new HashMap<>();
			    newsubtopic.put("id", forId);
			    newsubtopic.put("nama", name);
			    newsubtopic.put("index_topic", idTopic);
			    newsubtopic.put("idtopic", header);
			    ApiFuture<WriteResult> writeResultX = db.collection("subtopic").document(forDoc).set(newsubtopic);
			    
			    db.close();
			}
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(type.equals("addTopic")) {
			String name = request.getParameter("name");
			int forId = -1;
			String forDoc ="";
			try {
			    db = FirestoreOptions.newBuilder().setProjectId("lynda-310811").setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream("lynda-310811-08c61c30aea2.json"))).build().getService();
			    
			    CollectionReference topicData = db.collection("topic");
			    Query query = topicData.whereGreaterThan("id", -1);	   
			    ApiFuture<QuerySnapshot> querySnapshot = query.get();
			    int temp = querySnapshot.get().size() - 1;
			    
			    Query queryX = topicData.offset(temp).limit(1);
			    ApiFuture<QuerySnapshot> queryXSnapshot = queryX.get();
			    List<QueryDocumentSnapshot> documents = queryXSnapshot.get().getDocuments();
			    String doc = documents.get(0).getId();
			    int docId = Integer.parseInt(doc);
				for (QueryDocumentSnapshot document : documents) {
					long longid = document.getLong("id");
					forId = (int) longid + 1;
				}
				
				if(docId < 10)
					forDoc = "000" + (docId+1) + "";
				else if(docId < 100)
					forDoc = "00" + (docId+1) + "";
				else if(docId < 1000)
					forDoc = "0" + (docId+1) + "";
				else if(docId >= 1000)
					forDoc = (docId+1) + "";
				
			    Map<String, Object> newsubtopic = new HashMap<>();
			    newsubtopic.put("id", forId);
			    newsubtopic.put("nama", name);
			    ApiFuture<WriteResult> writeResultX = db.collection("topic").document(forDoc).set(newsubtopic);
			    
			    db.close();
			}
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(type.equals("banCourse")) {
			try {
			    db = FirestoreOptions.newBuilder().setProjectId("lynda-310811").setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream("lynda-310811-08c61c30aea2.json"))).build().getService();
			    CollectionReference courseData = db.collection("courses");
			    Query query = courseData.whereEqualTo("id", id);
			    ApiFuture<QuerySnapshot> querySnapshot = query.get();
			    List<QueryDocumentSnapshot> asd = querySnapshot.get().getDocuments();
			    String documentID = asd.get(0).getId();
			    Map<String, Object> updatedCourse = new HashMap<>();
			    updatedCourse.put("ban", 1);
			    ApiFuture<WriteResult> writeResult = db.collection("courses").document(documentID).update(updatedCourse);
			    db.close();
			}
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(type.equals("unbanCourse")) {
			try {
			    db = FirestoreOptions.newBuilder().setProjectId("lynda-310811").setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream("lynda-310811-08c61c30aea2.json"))).build().getService();
			    CollectionReference courseData = db.collection("courses");
			    Query query = courseData.whereEqualTo("id", id);
			    ApiFuture<QuerySnapshot> querySnapshot = query.get();
			    List<QueryDocumentSnapshot> asd = querySnapshot.get().getDocuments();
			    String documentID = asd.get(0).getId();
			    Map<String, Object> updatedCourse = new HashMap<>();
			    updatedCourse.put("ban", 0);
			    ApiFuture<WriteResult> writeResult = db.collection("courses").document(documentID).update(updatedCourse);
			    db.close();
			}
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(type.equals("acceptTransaction")) {
			try {
				String Siduser = request.getParameter("iduser");
				int iduser = Integer.parseInt(Siduser);
				String Stotal = request.getParameter("total");
				int total = Integer.parseInt(Stotal);
			    db = FirestoreOptions.newBuilder().setProjectId("lynda-310811").setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream("lynda-310811-08c61c30aea2.json"))).build().getService();
			    CollectionReference transactionData = db.collection("transactions");
			    Query query = transactionData.whereEqualTo("id", id);
			    ApiFuture<QuerySnapshot> querySnapshot = query.get();
			    List<QueryDocumentSnapshot> asd = querySnapshot.get().getDocuments();
			    String documentID = asd.get(0).getId();
			    Map<String, Object> updatedtransaction = new HashMap<>();
			    updatedtransaction.put("accepted", 1);
			    ApiFuture<WriteResult> writeResult = db.collection("transactions").document(documentID).update(updatedtransaction);
			    
			    CollectionReference u = db.collection("users");
			    Query quer = u.whereEqualTo("id", iduser);
			    ApiFuture<QuerySnapshot> querSnapshot = quer.get();
			    List<QueryDocumentSnapshot> as = querSnapshot.get().getDocuments();
			    String documentI = as.get(0).getId();
			    long walletNow = as.get(0).getLong("wallet");
			    String email = as.get(0).getString("email");
			    int goodWallet = (int) walletNow + total;
			    Map<String, Object> uu = new HashMap<>();
			    uu.put("wallet", goodWallet);
			    ApiFuture<WriteResult> writeResultX = db.collection("users").document(documentI).update(uu);
			    
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
					String msg = "Wallet kamu telah terisi sejumlah Rp "+total+". Terima kasih!";
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
			    
			    db.close();
			}
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
