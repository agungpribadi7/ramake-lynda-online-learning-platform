package edu.stts;

/*
 * Jangan Diotak-atik ntar aku bingung (kecuali mau benerin / nambahin)
 */

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.sql.Timestamp;


import javax.annotation.Nullable;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.EventListener;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreException;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.Query.Direction;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
public class adminFunctions {
	private Firestore db;
	public ArrayList<instructorForAdmin> getAllInstructors(){
		ArrayList<instructorForAdmin> instructorList = new ArrayList<>();
		
		try {
		    db = FirestoreOptions.newBuilder().setProjectId("lynda-310811").setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream("lynda-310811-e0f6c225dbfe.json"))).build().getService();
		    ApiFuture<QuerySnapshot> future = db.collection("instructors").orderBy("id_detail_email").get();
			List<QueryDocumentSnapshot> instructors = future.get().getDocuments();
			for (QueryDocumentSnapshot instructor : instructors) {
				String Laccepted = instructor.getString("accepted");
				long Lid = instructor.getLong("id");
				String desc = instructor.getString("deskripsi");
				
				String intro = instructor.getString("intro");
				
				CollectionReference userData = db.collection("users");
			    Query query = userData.whereEqualTo("id", instructor.getLong("id_detail_email"));
			    ApiFuture<QuerySnapshot> querySnapshot = query.get();
			    List<QueryDocumentSnapshot> asd = querySnapshot.get().getDocuments();
			    if(asd.size() > 0) {
			    	String Lbanned = asd.get(0).getString("banned");
			    	String Lverif = asd.get(0).getString("verifikasi");	
			    	String email = asd.get(0).getString("email");
			    	String foto = asd.get(0).getString("foto");
			    	String name = asd.get(0).getString("nama");
			    	int accepted = Integer.parseInt(Laccepted);
					int banned = Integer.parseInt(Lbanned);
					int id = (int) Lid;
					int verif = Integer.parseInt(Lverif);
					instructorForAdmin loadedInstructor = new instructorForAdmin(accepted,banned,id,verif,desc,email,foto,intro,name);
					instructorList.add(loadedInstructor);
			    }
			}
			db.close();
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return instructorList;
	}
	
	public ArrayList<studentForAdmin> getAllStudents(){
		ArrayList<studentForAdmin> studentList = new ArrayList<>();
		
		try {
		    db = FirestoreOptions.newBuilder().setProjectId("lynda-310811").setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream("lynda-310811-e0f6c225dbfe.json"))).build().getService();
		    ApiFuture<QuerySnapshot> future = db.collection("users").whereEqualTo("apa_instructor", "0").get();
			List<QueryDocumentSnapshot> students = future.get().getDocuments();
			for (QueryDocumentSnapshot student : students) {
				long Lid = student.getLong("id");
				String name = student.getString("nama");
		    	String Lbanned = student.getString("banned");
		    	String Lverif = student.getString("verifikasi");	
		    	String email = student.getString("email");
		    	String foto = student.getString("foto");
		    	long Lwallet = student.getLong("wallet");
			    
				int banned = Integer.parseInt(Lbanned);
				int id = (int) Lid;
				int wallet = (int) Lwallet;
				int verif = Integer.parseInt(Lverif);
				studentForAdmin loadedStudent = new studentForAdmin(banned,id,verif, wallet,email,foto,name);
				studentList.add(loadedStudent);
			}
			db.close();
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return studentList;
	}
	
	public ArrayList<topicForAdmin> getAllTopics(){
		ArrayList<topicForAdmin> topicList = new ArrayList<>();
		
		try {
		    db = FirestoreOptions.newBuilder().setProjectId("lynda-310811").setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream("lynda-310811-e0f6c225dbfe.json"))).build().getService();
		    ApiFuture<QuerySnapshot> future = db.collection("topic").get();
			List<QueryDocumentSnapshot> topics = future.get().getDocuments();
			for (QueryDocumentSnapshot topic : topics) {
				long Lid = topic.getLong("id");
				String name = topic.getString("nama");

				topicForAdmin loadedTopic = new topicForAdmin((int) Lid, name);
				topicList.add(loadedTopic);
			}
			db.close();
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return topicList;
	}
	
	public ArrayList<subtopicForAdmin> getAllSubtopics(){
		ArrayList<subtopicForAdmin> subtopicList = new ArrayList<>();
		
		try {
		    db = FirestoreOptions.newBuilder().setProjectId("lynda-310811").setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream("lynda-310811-e0f6c225dbfe.json"))).build().getService();
		    ApiFuture<QuerySnapshot> future = db.collection("subtopic").get();
			List<QueryDocumentSnapshot> subtopics = future.get().getDocuments();
			for (QueryDocumentSnapshot subtopic : subtopics) {
				long index_id = subtopic.getLong("index_topic");
				String idtopic = subtopic.getString("idtopic");
				long Lid = subtopic.getLong("id");
				String name = subtopic.getString("nama");

				subtopicForAdmin subloadedTopic = new subtopicForAdmin((int) index_id, idtopic, (int) Lid, name);
				subtopicList.add(subloadedTopic);
			}
			db.close();
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return subtopicList;
	}
	
	public ArrayList<courseForAdmin> getAllCourse(){
		ArrayList<courseForAdmin> courseList = new ArrayList<>();
		
		try {
		    db = FirestoreOptions.newBuilder().setProjectId("lynda-310811").setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream("lynda-310811-e0f6c225dbfe.json"))).build().getService();
		    ApiFuture<QuerySnapshot> future = db.collection("courses").get();
			List<QueryDocumentSnapshot> courses = future.get().getDocuments();
			for (QueryDocumentSnapshot course : courses) {
				long id = course.getLong("id");
				long ban = course.getLong("ban");
				long free = course.getLong("free");
				long views = course.getLong("viewer");
				long likes = course.getLong("like");
				String desc = course.getString("deskripsi");
				String instructor = course.getString("instructor");
				String judul = course.getString("judul");
				String release = course.getTimestamp("released") + "";
				String subtopic = course.getString("subtopic");
				String topic = course.getString("topic");
				String thumbnail = course.getString("thumbnail");
				String video = course.getString("video");

				courseForAdmin loadedCourse = new courseForAdmin((int) ban, (int) id, (int) free, (int) likes, (int) views
						, desc,instructor,judul,release,subtopic,topic,thumbnail,video);
				courseList.add(loadedCourse);
			}
			db.close();
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return courseList;
	}
	
	public ArrayList<transactionForAdmin> getAllTransactions(){
		ArrayList<transactionForAdmin> transactionList = new ArrayList<>();
		
		try {
		    db = FirestoreOptions.newBuilder().setProjectId("lynda-310811").setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream("lynda-310811-e0f6c225dbfe.json"))).build().getService();
		    ApiFuture<QuerySnapshot> future = db.collection("transactions").orderBy("tipe", Direction.ASCENDING).get();
			List<QueryDocumentSnapshot> transactions = future.get().getDocuments();
			for (QueryDocumentSnapshot transaction : transactions) {
				long accepted = transaction.getLong("accepted");
				long userid = transaction.getLong("iduser");
				long total = transaction.getLong("harga");
				long id = transaction.getLong("id");
				long tipe = transaction.getLong("tipe");
				String date = transaction.getTimestamp("date")+"";
				transactionForAdmin subloadedTopic = new transactionForAdmin((int) accepted, (int) total, (int) id,(int) userid, date,(int)tipe);
				transactionList.add(subloadedTopic);
			}
			db.close();
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return transactionList;
	}
}
