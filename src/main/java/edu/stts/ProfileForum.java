package edu.stts;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.Date;
import java.sql.Timestamp;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.Part;
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
import edu.stts.StorageClass;
import org.apache.commons.lang3.*;

/**
 * Servlet implementation class ProfileForum
 */
@WebServlet(
    name = "ProfileForum",
    urlPatterns = {"/ProfileForum"}
)
@MultipartConfig
public class ProfileForum extends HttpServlet {
	  private Firestore db;
	  private int instruktor;
	  private String banned;
	  private String email;
	  private String foto;
	  private String nama;
	  private String password;
	  private String cpassword;
	  private String verifikasi;
	  private String deskripsi;
	  
	  public int getInstruktor() {
		  return instruktor;
	  }
	  public String getBanned() {
		  return banned;
	  }
	  public String getEmail() {
		  return email;
	  }
	  public String getFoto() {
		  return foto;
	  }
	  public String getNama() {
		  return nama;
	  }
	  public String getPassword() {
		  return password;
	  }
	  public String getCpassword() {
		  return cpassword;
	  }
	  public String getVerifikasi() {
		  return verifikasi;
	  }
	  public String getDeskripsi() {
		  return deskripsi;
	  }
	  
	  public void setInstruktor(int instruktor) {
		  this.instruktor = instruktor;
	  }
	  public void setBanned(String banned) {
		  this.banned = banned;
	  }
	  public void setEmail(String email) {
		  this.email = email;
	  }
	  public void setFoto(String foto) {
		  this.foto = foto;
	  }
	  public void setName(String nama) {
		  this.nama = nama;
	  }
	  public void setPassword(String password) {
		  this.password = password;
	  }
	  public void setCpassword(String cpassword) {
		  this.cpassword = cpassword;
	  }
	  public void setVerifikasi(String verifikasi) {
		  this.verifikasi = verifikasi;
	  }
	  public void setDeskripsi(String deskripsi) {
		  this.deskripsi = deskripsi;
	  }
	  
	  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	  {
		  try {
			  StorageClass storage= new StorageClass();
			  int subtopicupload = Integer.parseInt(request.getParameter("subtopic"));
			  String email = request.getParameter("emailcourse");
			  int free = Integer.parseInt(request.getParameter("free"));
			  String judul = request.getParameter("judul");
			  String deskripsi = request.getParameter("description");
			  Part file_thumbnail = request.getPart("file_thumbnail");
			  Part file_video = request.getPart("file_video");
			  String nama_thumbnail=file_thumbnail.getSubmittedFileName();
			  String nama_video=file_video.getSubmittedFileName();
			  if((email != null && !email.isEmpty()) && (judul != null && !judul.isEmpty()) && (deskripsi != null && !deskripsi.isEmpty()) && (nama_thumbnail != null && !nama_thumbnail.isEmpty())&& (nama_video != null && !nama_video.isEmpty())) {
				  
				  int idcourse=0;
				  String namaRandomgambar = randomString()+".jpg";
				  String namaRandomvideo = randomString()+".mp4";
				  db = FirestoreOptions.newBuilder().setProjectId("lynda-310811").setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream("lynda-310811-e0f6c225dbfe.json"))).build().getService();
				  CollectionReference courses = db.collection("courses");
				  Query query = courses.orderBy("id",Direction.DESCENDING).limit(1);
				  ApiFuture<QuerySnapshot> querySnapshot = query.get();
				  List<QueryDocumentSnapshot> doc = querySnapshot.get().getDocuments();
			      for (QueryDocumentSnapshot document : doc) {
			    	  idcourse=document.getLong("id").intValue();
		          }
			      idcourse=idcourse+1;
				  int index_email=1;
				  String instruktor="";
				  
				  CollectionReference user = db.collection("users");
				  Query query1 = user.whereEqualTo("email", email).limit(1);
			      ApiFuture<QuerySnapshot> future = query1.get();
			      List<QueryDocumentSnapshot> docs = future.get().getDocuments();
			      for (QueryDocumentSnapshot document : docs) {
			    	  index_email=document.getLong("id").intValue();
			    	  instruktor=document.getString("nama");
		          }
			      
			      String namasub="",namatopic="";
			      CollectionReference subtopic = db.collection("subtopic");
			      Query query2 = subtopic.whereEqualTo("id", subtopicupload);
			      ApiFuture<QuerySnapshot> future1 = query2.get();
			      List<QueryDocumentSnapshot> docs1 = future1.get().getDocuments();
			      for (QueryDocumentSnapshot document : docs1) {
		                namasub=document.getString("nama");
		                namatopic=document.getString("idtopic");
		          }
			      
			      Date date= new Date();
			      long time = date.getTime();
			      Timestamp ts = new Timestamp(time);
			      Map<String, Object> course = new HashMap<>();
				  course.put("ban", 0);
				  course.put("deskripsi", deskripsi);
				  course.put("free", free);
				  course.put("id", idcourse);
			      course.put("index_email",index_email);
				  course.put("instructor", instruktor);
				  course.put("judul", judul);
				  course.put("like", 0);
				  course.put("released",  ts);
				  course.put("subtopic", namasub);
				  course.put("thumbnail", namaRandomgambar);
				  course.put("topic", namatopic);
				  course.put("video", namaRandomvideo);
				  course.put("viewer", 0);
				  System.out.println("course masuk");
				  String id_course1 = StringUtils.leftPad(idcourse+"", 4, "0");
				  db.collection("courses").document(id_course1).set(course);
				  storage.upload(file_thumbnail, "thumbnail", namaRandomgambar);
				  storage.upload(file_video, "video", namaRandomvideo);
				  request.setAttribute("messege_upload", "Berhasil Upload Course!");
			  }else {
				  request.setAttribute("messege_upload", "Semua field harus diisi");
			  }
			  
		  }catch (Exception e) {
	        	e.printStackTrace();
	      }
		  request.getRequestDispatcher("/profileView.jsp").forward(request, response);

	  }
	  
	  public void getProfile(String email) { 
		  try {
			  db = FirestoreOptions.newBuilder().setProjectId("lynda-310811").setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream("lynda-310811-e0f6c225dbfe.json"))).build().getService();
		        CollectionReference profile = db.collection("users");
		        Query query = profile.whereEqualTo("email", email);
		        ApiFuture<QuerySnapshot> future = query.get();
		        List<QueryDocumentSnapshot> docs = future.get().getDocuments();
	        	for (QueryDocumentSnapshot document : docs) {
	                setName(document.getString("nama"));
	                setEmail(document.getString("email"));
	                setFoto(document.getString("foto"));
	                setPassword(document.getString("password"));
	                setCpassword(document.getString("password"));
	                setFoto(document.getString("foto"));
	                setInstruktor(document.getLong("apa_instructor").intValue());
	            }
	        }catch (Exception e) {
	        	e.printStackTrace();
	        }
	  }
	  
	 public void getDeskripsi(String email) { 
		  try {
			  	int id=0;
			  	db = FirestoreOptions.newBuilder().setProjectId("lynda-310811").setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream("lynda-310811-e0f6c225dbfe.json"))).build().getService();
			  	CollectionReference user = db.collection("users");
      			Query query = user.whereEqualTo("email", email);
		        ApiFuture<QuerySnapshot> future = query.get();
		        List<QueryDocumentSnapshot> docs = future.get().getDocuments();
		        for (QueryDocumentSnapshot document : docs) {
	                id=document.getLong("id").intValue();
	            }
		        
		        CollectionReference instruktor = db.collection("instructors");
        		Query query1 = instruktor.whereEqualTo("id_detail_email", id);
		        ApiFuture<QuerySnapshot> future1 = query1.get();
		        List<QueryDocumentSnapshot> docs1 = future1.get().getDocuments();
		        for (QueryDocumentSnapshot document : docs1) {
	                setDeskripsi(document.getString("deskripsi"));
	            }
	        }catch (Exception e) {
	        	e.printStackTrace();
	        }
	 }
	public String randomString() {
		String generatedString = RandomStringUtils.randomAlphanumeric(21);
	    return generatedString;
	}
	
	public int getLikes(int email) { 
		int out=0;
		  try {
			  db = FirestoreOptions.newBuilder().setProjectId("lynda-310811").setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream("lynda-310811-e0f6c225dbfe.json"))).build().getService();
		        CollectionReference profile = db.collection("courses");
		        Query query = profile.whereEqualTo("index_email", email);
		        ApiFuture<QuerySnapshot> future = query.get();
		        List<QueryDocumentSnapshot> docs = future.get().getDocuments();
	        	for (QueryDocumentSnapshot document : docs) {
	                out=out+document.getLong("like").intValue();
	            }
	        }catch (Exception e) {
	        	e.printStackTrace();
	        }
		  return out;
	  }
	
	public ArrayList getInfoFromEmail(String email) throws FileNotFoundException, IOException, InterruptedException, ExecutionException {
		  System.out.println("masuk 0"+email); 
		  db = FirestoreOptions.newBuilder().setProjectId("lynda-310811").setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream("lynda-310811-e0f6c225dbfe.json"))).build().getService();    

	      CollectionReference user = db.collection("users");
	        Query query = user.whereEqualTo("email", email);
	        ApiFuture<QuerySnapshot> future = query.get();
	        ArrayList output = new ArrayList();
	        System.out.println("masuk "+email);
	        
	        int id = -1;
	        List<QueryDocumentSnapshot> docs = future.get().getDocuments();
	      for (QueryDocumentSnapshot document : docs) {
	        output.add(document.getString("nama"));
	        output.add(document.getString("email"));
	        output.add(document.getString("foto"));
	        output.add(document.get("apa_instructor")+"");
	        id = document.getLong("id").intValue();
	      }
	      System.out.println("masuk 2 "+email);
	      CollectionReference instructor = db.collection("instructors");
	      Query query2 = instructor.whereEqualTo("id_detail_email", id);
	        ApiFuture<QuerySnapshot> future2 = query2.get();
	        List<QueryDocumentSnapshot> docs2 = future2.get().getDocuments();
	      for (QueryDocumentSnapshot document : docs2) {
	        output.add(document.getString("deskripsi"));
	      }
	      return output;
	}
	
	public ArrayList getInfoFromindexEmail(String email) throws FileNotFoundException, IOException, InterruptedException, ExecutionException {  
		  db = FirestoreOptions.newBuilder().setProjectId("lynda-310811").setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream("lynda-310811-e0f6c225dbfe.json"))).build().getService();    
		      CollectionReference user = db.collection("users");
		        Query query = user.whereEqualTo("id", Integer.parseInt(email));
		        ApiFuture<QuerySnapshot> future = query.get();
		        ArrayList output = new ArrayList();
		        
		        int id = -1;
		        List<QueryDocumentSnapshot> docs = future.get().getDocuments();
		      for (QueryDocumentSnapshot document : docs) {
		        output.add(document.getString("nama"));
		        output.add(document.getString("email"));
		        output.add(document.getString("foto"));
		        output.add(document.get("apa_instructor")+"");
		        id = document.getLong("id").intValue();
		      }
		      CollectionReference instructor = db.collection("instructors");
		      Query query2 = instructor.whereEqualTo("id_detail_email", id);
		        ApiFuture<QuerySnapshot> future2 = query2.get();
		        List<QueryDocumentSnapshot> docs2 = future2.get().getDocuments();
		      for (QueryDocumentSnapshot document : docs2) {
		        output.add(document.getString("deskripsi"));
		      }
		      return output;
	}
	
	public String Updateprofile(String index_email, String nama,String password, String deskripsi) { 
		 int output=0;
		 int id=0;
		 String param = "Update Tidak Berhasil!";
		 String id_profile = "";
		  try {
			  	String id_doc="";
				db = FirestoreOptions.newBuilder().setProjectId("lynda-310811").setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream("lynda-310811-e0f6c225dbfe.json"))).build().getService();
				CollectionReference user = db.collection("users");
        		Query query = user.whereEqualTo("email", index_email);
		        ApiFuture<QuerySnapshot> future = query.get();
		        List<QueryDocumentSnapshot> docs = future.get().getDocuments();
		        String foto = "";
		        int idprofile = -1;
		        for (QueryDocumentSnapshot document : docs) {
	                id=document.getLong("id").intValue();
	                foto = document.getString("foto");
	                idprofile = document.getLong("id").intValue();
	                id_doc=document.getId();
	            }
		        
		        
				Map<String, Object> profile = new HashMap<>();
				profile.put("nama",nama);
				if(!password.equals("")) {
					profile.put("password", md5.getMd5(password));
				}
				
			    int id_ins=0;
			    String idins_doc="";
			    CollectionReference instruktor = db.collection("instructors");
        		Query query1 = instruktor.whereEqualTo("id_detail_email", id);
		        ApiFuture<QuerySnapshot> future1 = query1.get();
		        List<QueryDocumentSnapshot> docs1 = future1.get().getDocuments();
		        for (QueryDocumentSnapshot document : docs1) {
		        	id_ins=document.getLong("id").intValue();
		        	idins_doc=document.getId();
	            }
		        param= id_ins+"-asdfs "+idins_doc;
			    Map<String, Object> instruktor1 = new HashMap<>();
			    instruktor1.put("deskripsi",deskripsi);
			    id_profile = StringUtils.leftPad(idprofile+"", 4, "0"); 
			    getProfile(index_email);
			    db.collection("users").document(id_profile).update(profile);
			    if(!idins_doc.equals("")) {
	    			db.collection("instructors").document(idins_doc).update(instruktor1);
	    		}

	      }catch (Exception e) {
	    	  param = "error";
	    	  e.printStackTrace();
	      }
		  return param;
	}
	public String getFotoFromEmail(String email) throws FileNotFoundException, IOException, InterruptedException, ExecutionException {
		 db = FirestoreOptions.newBuilder().setProjectId("lynda-310811").setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream("lynda-310811-e0f6c225dbfe.json"))).build().getService();    
	     CollectionReference courses = db.collection("users");
	        
       Query query = courses.whereEqualTo("email", email);
       ApiFuture<QuerySnapshot> future = query.get();
       String output = new String();
       int id = -1;
       List<QueryDocumentSnapshot> docs = future.get().getDocuments();
    	for (QueryDocumentSnapshot document : docs) {
    		output = document.getString("foto");
    	}
    	
    	return output;
	}
	
	public String getIdemail(String email) {
		String output="";
		  try {
		        db = FirestoreOptions.newBuilder().setProjectId("lynda-310811").setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream("lynda-310811-e0f6c225dbfe.json"))).build().getService();    
		        
		        CollectionReference users = db.collection("users");
		        Query query = users.whereEqualTo("email", email);
		        ApiFuture<QuerySnapshot> querySnapshot = query.get();
		        List<QueryDocumentSnapshot> documents = querySnapshot.get().getDocuments();
	            for (QueryDocumentSnapshot document : documents) {
	                output=document.getLong("id")+"";
	            }
	        }catch (Exception e) {
	        	e.printStackTrace();
	        }
		  return output;
	}
	
	public ArrayList<String>[] getHistory(String email) {
		  ArrayList<String>[] output = new ArrayList[8];
		  for (int i = 0; i < 8; i++) { 
		      output[i] = new ArrayList<String>(); 
		  } 
		  try {
		        db = FirestoreOptions.newBuilder().setProjectId("lynda-310811").setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream("lynda-310811-e0f6c225dbfe.json"))).build().getService();
		        CollectionReference view = db.collection("views");
		        Query query = view.whereEqualTo("iduser", Integer.parseInt(email)).orderBy("id", Direction.DESCENDING).limit(10);
		        ApiFuture<QuerySnapshot> querySnapshot = query.get();
		        List<QueryDocumentSnapshot> documents = querySnapshot.get().getDocuments();
	            for (QueryDocumentSnapshot document : documents) {
	            	CollectionReference courses = db.collection("courses");
			        Query query1 = courses.whereEqualTo("id", document.getLong("idcourse"));
			        ApiFuture<QuerySnapshot> querySnapshot1 = query1.get();
			        List<QueryDocumentSnapshot> documents1 = querySnapshot1.get().getDocuments();
		            for (QueryDocumentSnapshot document1 : documents1) {
		            	output[0].add(document1.getString("judul"));
		                output[1].add(document1.getString("thumbnail"));
		                output[2].add(document1.get("viewer")+"");
		                output[3].add(document1.get("instructor")+"");
		                output[4].add(document1.get("released")+"");
		                output[5].add(document1.getString("subtopic")+"");
		                if(Integer.parseInt(document1.get("free")+"") == 0) {
		                	output[6].add("PREMIUM");
		                }
		                else {
		                	output[6].add("FREE");
		                }
		                output[7].add(document1.getLong("id")+"");
		            }
	            }
	        }catch (Exception e) {
	        	e.printStackTrace();
	        }
		  return output;
	}
	
	public ArrayList<String>[] getCoursepublik(String email) {
		  ArrayList<String>[] output = new ArrayList[8]; 
		  
        for (int i = 0; i < 8; i++) { 
            output[i] = new ArrayList<String>(); 
        } 
		  try {
		        db = FirestoreOptions.newBuilder().setProjectId("lynda-310811").setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream("lynda-310811-e0f6c225dbfe.json"))).build().getService();    
		        System.out.print("masuk function get course "+email);
		        CollectionReference courses = db.collection("courses");
		        Query query1 = courses.whereEqualTo("index_email", Integer.parseInt(email));
		        ApiFuture<QuerySnapshot> querySnapshot = query1.get();
		        List<QueryDocumentSnapshot> documents = querySnapshot.get().getDocuments();
	            for (QueryDocumentSnapshot document : documents) {
	            	System.out.print("masuk for");
	                output[0].add(document.getString("judul"));
	                output[1].add(document.getString("thumbnail"));
	                output[2].add(document.get("viewer")+"");
	                output[3].add(document.get("instructor")+"");
	                output[4].add(document.get("released")+"");
	                output[5].add(document.getString("subtopic")+"");
	                if(Integer.parseInt(document.get("free")+"") == 0) {
	                	output[6].add("PREMIUM");
	                }
	                else {
	                	output[6].add("FREE");
	                }
	                output[7].add(document.getLong("id")+"");
	            }
	        }catch (Exception e) {
	        	e.printStackTrace();
	        }
		  return output;
	}
	
	public String getTotalVideo(String email) {
		  String output = "";
		  try {
		        db = FirestoreOptions.newBuilder().setProjectId("lynda-310811").setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream("lynda-310811-e0f6c225dbfe.json"))).build().getService();    
		        
		        CollectionReference courses = db.collection("courses");
		        int idx = 0;
		        Query query1 = courses.whereEqualTo("index_email", Integer.parseInt(email));
		        ApiFuture<QuerySnapshot> querySnapshot = query1.get();
		        
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
	
}
