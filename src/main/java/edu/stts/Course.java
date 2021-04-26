package edu.stts;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutionException;

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
import com.google.cloud.firestore.Query.Direction;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;

@WebServlet(
    name = "Course",
    urlPatterns = {"/course"}
)
public class Course extends HttpServlet {
	  private Firestore db;
	  private String topic;
	  private String subtopic;
	  private String judul;
	  private String deskripsi;
	  private int viewer;
	  private int like;
	  private int free;
	  private int id;
	  private String video;
	  private String thumbnail;
	  private String instructor;
	  private String released;
	  
	  public String getReleased() {
		  return released;
	  }
	  public String getTopic() {
		  return topic;
	  }
	  public String getSubtopic() {
		  return subtopic;
	  }
	  public String getJudul() {
		  return judul;
	  }
	  public String getDeskripsi() {
		  return deskripsi;
	  }
	  public String getInstructor() {
		  return instructor;
	  }
	  public String getVideo() {
		  return video;
	  }
	  public String getThumbnail() {
		  return thumbnail;
	  }
	  public int getViewer() {
		  return viewer;
	  }
	  public int getLike() {
		  return like;
	  }
	  public int getId() {
		  return id;
	  }
	  public int getFree() {
		  return free;
	  }
	  public void setReleased(String released) {
		  this.released = released;
	  }
	  public void setFree(Integer free) {
		  this.free = free;
	  }
	  public void setId(Integer id) {
		  this.id = id;
	  }
	  public void setLike(Integer like) {
		  this.like = like;
	  }
	  public void setViewer(Integer viewer) {
		  this.viewer = viewer;
	  }
	  public void setThumbnail(String thumbnail) {
		  this.thumbnail = thumbnail;
	  }
	  public void setVideo(String video) {
		  this.video = video;
	  }
	  public void setInstructor(String instructor) {
		  this.instructor = instructor;
	  }
	  public void setDeskripsi(String deskripsi) {
		  this.deskripsi = deskripsi;
	  }
	  public void setJudul(String judul) {
		  this.judul = judul;
	  }
	  public void setTopic(String topic) {
	      this.topic = topic;
	  }
	  public void setSubtopic(String subtopic) {
	      this.subtopic = subtopic;
	  }
	  
	  public ArrayList<String>[] cari(String key, int mode) {
		  
		  ArrayList<String>[] output = new ArrayList[9]; 
		  for (int i = 0; i < 9; i++) { 
              output[i] = new ArrayList<String>(); 
          } 
		  try {
		        db = FirestoreOptions.newBuilder().setProjectId("lynda-310811").setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream("lynda-310811-08c61c30aea2.json"))).build().getService();    
		        CollectionReference courses = db.collection("courses");
		        Query query = null;
		        if(mode == 0) {
		        	query = courses.orderBy("released", Direction.DESCENDING);
		        }
		        else if(mode == 1) {
		        	query = courses.orderBy("released", Direction.ASCENDING);
		        }
		        else if(mode == 2) {
		        	query = courses.orderBy("like", Direction.DESCENDING);
		        }
		        else if(mode == 3) {
		        	query = courses.orderBy("like", Direction.ASCENDING);
		        }
		        
		        ApiFuture<QuerySnapshot> future = query.get();
		        List<QueryDocumentSnapshot> docs = future.get().getDocuments();
	        	for (QueryDocumentSnapshot document : docs) {
	        		String judul = document.getString("judul").toLowerCase();
	        		if(judul.contains(key.toLowerCase())) {
	        			 output[0].add(document.getString("judul"));
	 	                 output[1].add(document.getString("thumbnail"));
	 	                 output[2].add(document.get("viewer")+"");
	 	                 output[3].add(document.get("like")+"");
	 	                 output[4].add(document.get("instructor")+"");
	 	                 output[5].add(document.get("released")+"");
	 	                 output[6].add(document.getString("subtopic")+"");
	 	                 
	 	                 if(Integer.parseInt(document.get("free")+"") == 0) {
	 	                 	 output[7].add("PREMIUM");
	 	                 }
	 	                 else {
	 	                	 output[7].add("FREE");
	 	                 }
	 	                output[8].add(document.get("id")+"");
	        		}
	               
	            }
	        }catch (Exception e) {
	        	e.printStackTrace();
	        }
		  return output;
	  }
	  
	  public ArrayList<String>[] getAllCourseFromTopic(String subtopic, int order) {
		  ArrayList<String>[] output = new ArrayList[9]; 
		  
          for (int i = 0; i < 9; i++) { 
              output[i] = new ArrayList<String>(); 
          } 
		  try {
			  System.out.println("masuk function");
		        db = FirestoreOptions.newBuilder().setProjectId("lynda-310811").setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream("lynda-310811-08c61c30aea2.json"))).build().getService();    
		        CollectionReference courses = db.collection("courses");
		        Query query = null;
		        order = 1; //force
		        if(order == 0) {
		        	query = courses.whereEqualTo("subtopic", subtopic).orderBy("id", Direction.ASCENDING).orderBy("released", Direction.DESCENDING);
		        }
		        else {
		        	query = courses.whereEqualTo("subtopic", subtopic);
		        }
		        ApiFuture<QuerySnapshot> future = query.get();
		        List<QueryDocumentSnapshot> docs = future.get().getDocuments();
	        	for (QueryDocumentSnapshot document : docs) {
	                System.out.println(document.getString("judul"));
	                output[0].add(document.getString("judul"));
	                output[1].add(document.getString("thumbnail"));
	                output[2].add(document.get("viewer")+"");
	                output[3].add(document.get("like")+"");
	                output[4].add(document.get("instructor")+"");
	                output[5].add(document.get("released")+"");
	                output[6].add(document.getString("subtopic")+"");
	                if(Integer.parseInt(document.get("free")+"") == 0) {
	                	output[7].add("PREMIUM");
	                }
	                else {
	                	output[7].add("FREE");
	                }
	                output[8].add(document.get("id")+"");
	            }
		        
	        }catch (Exception e) {
	        	e.printStackTrace();
	        }
		  return output;
	  }
	  
	  public String getTotalVideoFromSubTopic(String subtopic) {
		  String output = "";
		  try {
		        db = FirestoreOptions.newBuilder().setProjectId("lynda-310811").setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream("lynda-310811-08c61c30aea2.json"))).build().getService();    
		        CollectionReference courses = db.collection("courses");
		        int idx = 0;
		        Query query = courses.whereEqualTo("subtopic", subtopic);
		        ApiFuture<QuerySnapshot> querySnapshot = query.get();
		        
		        List<QueryDocumentSnapshot> documents = querySnapshot.get().getDocuments();
	            for (QueryDocumentSnapshot document : documents) {
	                Map<String, Object> map = document.getData();
	                idx += 1;
	            }
	            output = String.valueOf(idx);
	        }catch (Exception e) {
	        	e.printStackTrace();
	        }
		  return output;
	  }
	  
	  public static <T> ArrayList<T> removeDuplicates(ArrayList<T> list) 
	    { 
	        ArrayList<T> newList = new ArrayList<T>(); 
	        for (T element : list) { 
	            if (!newList.contains(element)) { 
	                newList.add(element); 
	            } 
	        } 
	        return newList; 
	    } 
	  
	  public ArrayList<String> getSubTopicFromTopic(String topic) {
		  ArrayList<String> sub = new ArrayList<String>();
		  ArrayList<String> outputReturn = new ArrayList<String>();
		  try {
		        db = FirestoreOptions.newBuilder().setProjectId("lynda-310811").setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream("lynda-310811-08c61c30aea2.json"))).build().getService();    
		        CollectionReference courses = db.collection("courses");
		        Query query = courses.whereEqualTo("topic", topic);
		        ApiFuture<QuerySnapshot> querySnapshot = query.get();
		        List<QueryDocumentSnapshot> documents = querySnapshot.get().getDocuments();
		        int idx = 0;
	            for (QueryDocumentSnapshot document : documents) {
	                sub.add(document.getString("subtopic"));
	            }
	            outputReturn = removeDuplicates(sub);
	        }catch (Exception e) {
	        	e.printStackTrace();
	        }
		  return outputReturn;
	  }
	  
	  public ArrayList<String> getAllTopic() {
		  ArrayList<String> topic = new ArrayList<String>();
		  try {
		        db = FirestoreOptions.newBuilder().setProjectId("lynda-310811").setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream("lynda-310811-08c61c30aea2.json"))).build().getService();    
		        CollectionReference topics = db.collection("topic");
		        ApiFuture<QuerySnapshot> querySnapshot = topics.get();
		        List<QueryDocumentSnapshot> documents = querySnapshot.get().getDocuments();
		        int idx = 0;
	            for (QueryDocumentSnapshot document : documents) {
	                topic.add(document.getString("nama"));
	            }
	        }catch (Exception e) {
	        	e.printStackTrace();
	        }
		  return topic;
	  }
	  
	  public String getFirstSubTopicFromTopic(String topic) {
		  String subpertama = "";
		  try {
			  	
		        db = FirestoreOptions.newBuilder().setProjectId("lynda-310811").setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream("lynda-310811-08c61c30aea2.json"))).build().getService();    
		        CollectionReference subtopic = db.collection("subtopic");
		        Query query = subtopic.whereEqualTo("idtopic", topic);
		        ApiFuture<QuerySnapshot> querySnapshot = query.get();
		        List<QueryDocumentSnapshot> documents = querySnapshot.get().getDocuments();
		        int idx = 0;
		        for (QueryDocumentSnapshot document : documents) {
		        	if(idx==0) {
		        		subpertama = document.getString("nama");
		        	}
		        	idx += 1;
		        }
	        }catch (Exception e) {
	        	e.printStackTrace();
	        }
		  return subpertama;
	  }
	  
	  public ArrayList<String>[] getTopRated(Integer limit) {
		  ArrayList<String>[] output = new ArrayList[3]; 
		  
          for (int i = 0; i < 3; i++) { 
              output[i] = new ArrayList<String>(); 
          } 
		  try {
		        db = FirestoreOptions.newBuilder().setProjectId("lynda-310811").setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream("lynda-310811-08c61c30aea2.json"))).build().getService();    
		        CollectionReference courses = db.collection("courses");
		        Query query = courses.whereEqualTo("free", 1).orderBy("like", Direction.ASCENDING).limit(limit);
		        ApiFuture<QuerySnapshot> querySnapshot = query.get();
		        List<QueryDocumentSnapshot> documents = querySnapshot.get().getDocuments();
	            for (QueryDocumentSnapshot document : documents) {
	                Map<String, Object> map = document.getData();
	                output[0].add(document.getString("judul"));
	                output[1].add(document.getString("thumbnail"));
	                output[2].add(document.get("id")+"");
	            }
	        }catch (Exception e) {
	        	e.printStackTrace();
	        }
		  return output;
	  }
	  
	  public ArrayList<String>[] getTopRatedLimited(String topic, Integer limit) {
		  ArrayList<String>[] output = new ArrayList[10]; 
		  
          for (int i = 0; i < 10; i++) { 
              output[i] = new ArrayList<String>(); 
          } 
		  try {
		        db = FirestoreOptions.newBuilder().setProjectId("lynda-310811").setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream("lynda-310811-08c61c30aea2.json"))).build().getService();    
		        CollectionReference courses = db.collection("courses");
		        Query query = courses.whereEqualTo("topic", topic).orderBy("like", Direction.DESCENDING).limit(limit);
		        ApiFuture<QuerySnapshot> querySnapshot = query.get();
		        List<QueryDocumentSnapshot> documents = querySnapshot.get().getDocuments();
	            for (QueryDocumentSnapshot document : documents) {
	                Map<String, Object> map = document.getData();
	                output[0].add(document.getString("judul"));
	                output[1].add(document.getString("thumbnail"));
	                output[2].add(document.get("viewer")+"");
	                output[3].add(document.get("like")+"");
	                output[4].add(document.get("instructor")+"");
	                output[5].add(document.get("released")+"");
	                output[6].add(document.getString("subtopic")+"");
	                if(document.getLong("free").intValue() == 0) {
	                 	 output[7].add("PREMIUM");
	                 }
	                 else if(document.getLong("free").intValue() == 1){
	                	 output[7].add("FREE");
	                 }
	                output[8].add(document.get("index_email")+"");
	                output[9].add(document.get("id")+"");
	            }
	        }catch (Exception e) {
	        	e.printStackTrace();
	        }
		  return output;
	  }
	 
	  public ArrayList[] getRelatedVideo(int id) {
		  ArrayList output[] = null;
		  StorageClass storage = new StorageClass();
		  try {
		        db = FirestoreOptions.newBuilder().setProjectId("lynda-310811").setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream("lynda-310811-08c61c30aea2.json"))).build().getService();    
		        CollectionReference courses = db.collection("courses");
		        Query query = courses.whereEqualTo("id", id);
		        ApiFuture<QuerySnapshot> future = query.get();
		        List<QueryDocumentSnapshot> docs = future.get().getDocuments();
		        topic = "";
	        	for (QueryDocumentSnapshot document : docs) {
	        		topic = document.getString("topic");
	        	}
	        	Query query2 = courses.whereEqualTo("topic", topic).limit(10);
	        	ApiFuture<QuerySnapshot> future2 = query2.get();
			    List<QueryDocumentSnapshot> docs2 = future2.get().getDocuments();
			    output = new ArrayList[7];
			    for (int i = 0; i < 7; i++) { 
		              output[i] = new ArrayList(); 
		          } 
			    for (QueryDocumentSnapshot document : docs2) {
			    	output[0].add(document.getLong("id").intValue()+"");
	        		output[1].add(document.getLong("viewer").intValue()+"");
	        		output[2].add(document.getString("instructor"));
	        		output[3].add(document.getString("judul"));
	        		output[4].add(document.getLong("free").intValue()+"");
	        		output[5].add(document.getString("subtopic"));
	        		output[6].add(document.getString("thumbnail"));
	        	}
		  }
		  catch(Exception e) {
			  System.out.println("error");
		  }
		  return output;
	  }
	  
	  public int getIndexEmailFromInstructor(String name) {
		  int idx = -1;
		  try {
			  db = FirestoreOptions.newBuilder().setProjectId("lynda-310811").setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream("lynda-310811-08c61c30aea2.json"))).build().getService();    
			  CollectionReference instructor = db.collection("instructors");
			  Query query = instructor.whereEqualTo("nama", name).limit(1);
			  ApiFuture<QuerySnapshot> querySnapshot = query.get();
			  List<QueryDocumentSnapshot> documents = querySnapshot.get().getDocuments();
			  for (QueryDocumentSnapshot document : documents) {
				  Map<String, Object> map = document.getData();
				  idx = document.getLong("id_detail_email").intValue();
			  }
		  }catch(Exception e) {
			  System.out.println("error");
		  }
		  
		  return idx;
	  }
	  
	  public ArrayList getDetailVideo(int id) {
		  ArrayList output = new ArrayList();
		  StorageClass storage = new StorageClass();
		  try {
		        db = FirestoreOptions.newBuilder().setProjectId("lynda-310811").setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream("lynda-310811-08c61c30aea2.json"))).build().getService();    
		        CollectionReference courses = db.collection("courses");
		        Query query = courses.whereEqualTo("id", id);
		        ApiFuture<QuerySnapshot> future = query.get();
		        System.out.println("masuk detial");
		        List<QueryDocumentSnapshot> docs = future.get().getDocuments();
	        	for (QueryDocumentSnapshot document : docs) {
	        		output.add(id+"");
	        		output.add(document.getLong("viewer").intValue()+"");
	        		output.add(document.getLong("like").intValue()+"");
	        		output.add(document.getString("judul"));
	        		output.add(document.getString("deskripsi"));
	        		output.add(document.getLong("free").intValue()+"");
	        		String tanggal = document.get("released").toString();
	        		//int idx = tanggal.indexOf("T");
	        		//tanggal = tanggal.substring(0, idx);
	        		System.out.println(tanggal+ " tanggal");
	        		output.add(tanggal);
	        		output.add(document.getString("instructor"));
	        		System.out.println(tanggal+ " instructor");
	        		output.add(storage.getImage("video", document.getString("video"))); //get link video
	        		System.out.println(tanggal+ " video");
	        		output.add(getIndexEmailFromInstructor(document.getString("instructor"))+"");
	        		System.out.println(tanggal+ " index email");
	        		output.add(storage.getImage("lainnya", "viponly.jpg")); //get link error
	        		System.out.println(tanggal+ " error");
	        		//output.add(document.getDate("released"));
	        	}
		  }
		  catch(Exception e) {
			  System.out.println("error");
		  }
		  return output;
	  }
	  
	  public String getLinkFoto(int index_email) {
		  String link = "";
		  try {
		        db = FirestoreOptions.newBuilder().setProjectId("lynda-310811").setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream("lynda-310811-08c61c30aea2.json"))).build().getService();    
		        CollectionReference user = db.collection("users");
		        Query query = user.whereEqualTo("id", index_email);
		        ApiFuture<QuerySnapshot> future = query.get();
		        List<QueryDocumentSnapshot> docs = future.get().getDocuments();
		        for (QueryDocumentSnapshot document : docs) {
		        	link = document.getString("foto");
		        }
		  }
		  catch(Exception e) {
			  System.out.println("error");
		  }
		  return link;
	  }
	  
	  public void setView(int iduser, int idvideo) {
		  try {
		        db = FirestoreOptions.newBuilder().setProjectId("lynda-310811").setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream("lynda-310811-08c61c30aea2.json"))).build().getService();    
		        CollectionReference view = db.collection("views");
		        
		        Query query = view.whereEqualTo("iduser", iduser).whereEqualTo("idcourse", idvideo);
		        ApiFuture<QuerySnapshot> future = query.get();
		        List<QueryDocumentSnapshot> docs = future.get().getDocuments();
		        if(docs.size() == 0) {
			        int idview = -1;
			        Query query1 = view.whereGreaterThan("id", -1);
		    		ApiFuture<QuerySnapshot> querySnapshot1 = query1.get();
				    int temp1 = querySnapshot1.get().size()-1;
		    		Query query3 = view.offset(temp1).limit(1);			    		
				    ApiFuture<QuerySnapshot> querySnapshot3 = query3.get();
				    List<QueryDocumentSnapshot> documents = querySnapshot3.get().getDocuments();
					for (QueryDocumentSnapshot documentss : documents) {
						long longid = documentss.getLong("id");
						idview = (int) longid+1;						
					}
					
		        	Map<String, Object> c = new HashMap<>(); 
    	   			 c.put("id", idview);
    	   			 c.put("iduser", iduser);
    	   			 c.put("idcourse", idvideo);
    	   			 String iddoc = StringUtils.leftPad(idview+"", 4, "0"); 
    	   			ApiFuture<WriteResult> writeResult =db.collection("views").document(iddoc).set(c); 
    	   			
    	   			CollectionReference course = db.collection("courses");
    	   			Query queryCourse = course.whereEqualTo("id", idvideo);
    		        ApiFuture<QuerySnapshot> futureCourse = queryCourse.get();
    		        List<QueryDocumentSnapshot> docsCourse = futureCourse.get().getDocuments();
    		        int jumlah_view = 0;
    		        int apa_free = -1;
    		        Map<String, Object> cCourse = new HashMap<>(); 
    		        int idinstructor = -1;
    		        for (QueryDocumentSnapshot document : docsCourse) {
    		        	jumlah_view = document.getLong("viewer").intValue()+1;
    		        	cCourse.put("viewer", jumlah_view);
    		        	apa_free = document.getLong("free").intValue();
    		        	idinstructor = document.getLong("index_email").intValue();
    		        }
    		        String id_course = StringUtils.leftPad(idvideo+"", 4, "0"); 
    		        db.collection("courses").document(id_course).update(cCourse);
    		        
    		        
		        	CollectionReference transaction = db.collection("transactions");
					Query queryTrans = transaction.whereGreaterThan("id", -1);
					int idTrans = -1;
					ApiFuture<QuerySnapshot> querySnapshotTrans = queryTrans.get();
				    int tempTrans = querySnapshotTrans.get().size()-1;
		    		Query queryTrans2 = transaction.offset(tempTrans).limit(1);			    		
				    ApiFuture<QuerySnapshot> querySnapshotTrans2 = queryTrans2.get();
				    List<QueryDocumentSnapshot> documentTrans = querySnapshotTrans2.get().getDocuments();
				    for (QueryDocumentSnapshot document : documentTrans) {
						long longid = document.getLong("id");
						idTrans = (int) longid+1;						
					}
				    
				    CollectionReference user = db.collection("users");
					Query queryUser = user.whereEqualTo("id", idinstructor);
					ApiFuture<QuerySnapshot> querySnapshotUsers = queryUser.get();
				    int wallet = 0;
				    List<QueryDocumentSnapshot> documentUser = querySnapshotUsers.get().getDocuments();
				    for (QueryDocumentSnapshot document : documentUser) {
						wallet = document.getLong("wallet").intValue();
						if(apa_free == 0) {
							wallet += 30;
						}
						else if(apa_free == 1) {
							wallet += 10;
						}
						System.out.println(wallet+" wallet");
					}
					System.out.println("masuk sini");
		   			 
				    
				    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
					 Map<String, Object> c2 = new HashMap<>(); 
		   			 c2.put("id", idTrans);
		   			 c2.put("iduser", idinstructor);
		   			 c2.put("paymentcode", "");
		   			if(apa_free == 0) {
		   				c2.put("harga", 30);
		   			}
		   			else if(apa_free == 1){
		   				c2.put("harga", 10);
		   			}
		   			 c2.put("midtrans_trans_id", "");
		   			 c2.put("tipe", 2);
		   			 c2.put("date", timestamp);
		   			 c2.put("accepted", 1);
		   			 c2.put("catatan", 0);
		   			 String iddocTrans = StringUtils.leftPad(idTrans+"", 4, "0");
		   			 
		   			 ApiFuture<WriteResult> writeResultTrans =db.collection("transactions").document(iddocTrans).set(c2);
		   			 
				    
					Map<String, Object> c3 = new HashMap<>(); 
					c3.put("wallet", wallet);
		   			String iddocUser = StringUtils.leftPad(idinstructor+"", 4, "0"); 
		   			ApiFuture<WriteResult> writeResultUser =db.collection("users").document(iddocUser).update(c3);
    		        
		        }
		  }
		  catch(Exception e) {
			  System.out.println("error");
		  }
	  }
	  
	  public int getLike(int iduser, int idvideo) {
		  int adakah = 0;
		  try {
		        db = FirestoreOptions.newBuilder().setProjectId("lynda-310811").setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream("lynda-310811-08c61c30aea2.json"))).build().getService();    
		        CollectionReference view = db.collection("likes");
		        System.out.println("id video = "+idvideo+" iduser = " + iduser);
		        Query query = view.whereEqualTo("iduser", iduser).whereEqualTo("idcourse", idvideo);
		        ApiFuture<QuerySnapshot> future = query.get();
		        List<QueryDocumentSnapshot> docs = future.get().getDocuments();
		        System.out.println("jumlah document di likes = " + docs.size());
		        if(docs.size() > 0) {
		        	adakah = 1;
		        }
		        db.close();
		  }
		  catch(Exception e) {
			  System.out.println("error get like");
		  }
		  return adakah;
	  }
	  
	  public ArrayList[] getAllSubTopic() {
		  ArrayList output[] = new ArrayList[3];
		  for (int i = 0; i < 3; i++) { 
              output[i] = new ArrayList<String>(); 
          } 
		  try {
		        db = FirestoreOptions.newBuilder().setProjectId("lynda-310811").setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream("lynda-310811-08c61c30aea2.json"))).build().getService();    
		        CollectionReference subtopic = db.collection("subtopic");
		        ApiFuture<QuerySnapshot> future = subtopic.get();
		        List<QueryDocumentSnapshot> docs = future.get().getDocuments();
		        for (QueryDocumentSnapshot document : docs) {
		        	output[0].add(document.getLong("id").intValue());
		        	output[1].add(document.getString("nama"));
		        }
		        db.close();
		  }
		  catch(Exception e) {
			  System.out.println("error get all subtopic");
		  }
		  
		  return output;
	  }
	  
	  public int getInfopremium(int email) throws FileNotFoundException, IOException, InterruptedException, ExecutionException {  
		  int output=0;
		  db = FirestoreOptions.newBuilder().setProjectId("lynda-310811").setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream("lynda-310811-08c61c30aea2.json"))).build().getService();    
		      CollectionReference user = db.collection("users");
		        Query query = user.whereEqualTo("id",  (Integer)email);
		        ApiFuture<QuerySnapshot> future = query.get();
		        List<QueryDocumentSnapshot> docs = future.get().getDocuments();
		      for (QueryDocumentSnapshot document : docs) {
		        output=document.getLong("premium").intValue();
		      }
		  return output;
	  }
}
