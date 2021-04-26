package edu.stts;

import java.io.*;
import javax.servlet.ServletException;
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
import com.google.cloud.firestore.Query.Direction;
import com.google.firestore.v1.DocumentTransform.FieldTransform.ServerValue;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.lang.Math;
import java.sql.Timestamp;
@WebServlet("/bacaTxtFile")
public class bacaTxtFile extends HttpServlet {
	private Firestore db;
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public bacaTxtFile() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      try { 
			 db = FirestoreOptions.newBuilder().setProjectId("lynda-310811").setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream("lynda-310811-08c61c30aea2.json"))).build().getService();
			 //for(int i = 0;i< 115;i++) {
				 //Map<String, Object> c = new HashMap<>(); 
				 //c.put("kodeverif", "");
				 //String id = StringUtils.leftPad(i+"", 4, "0"); 
				 //db.collection("users").document(id).update(c);
	
			 //}
		 } catch (Exception e) { // TODO Auto-generated catch block
			 e.printStackTrace(); 
		 }
		
		
/*
		try {
		  	
	        CollectionReference courses = db.collection("courses");
	        ApiFuture<QuerySnapshot> future = courses.get();
	        List<QueryDocumentSnapshot> docs = future.get().getDocuments();
	        System.out.println(docs.size());
	        Random rand = new Random(); 
		    ArrayList history[] = new ArrayList[docs.size()];
		    ArrayList idd[] = new ArrayList[docs.size()];
		    ArrayList id_user_like[] = new ArrayList[docs.size()];
		    ArrayList idcourselike[] = new ArrayList[docs.size()];
		    for (int i = 0; i < docs.size(); i++) { 
	              history[i] = new ArrayList(); 
	              idd[i] = new ArrayList();
	              id_user_like[i] = new ArrayList();
	              idcourselike[i] = new ArrayList();
	          } 
		    int idx = 0;
		    
        	for (QueryDocumentSnapshot document : docs) {
        		System.out.println("masuk 1");
        		int jumlah_user_like= document.getLong("like").intValue();
        		int viewer = document.getLong("viewer").intValue();
        		System.out.println(viewer);
        		int id = document.getLong("id").intValue();
        		System.out.println(id);
        		int tempjumlah = 0;
         		for(int i = 0; i< viewer; i++) {
         			System.out.println("masuk 2");
         			int random = rand.nextInt(110);
         			if(!history[idx].contains(random)) {
         				history[idx].add(random);
         				
         				idd[idx].add(id); //id video
         				if(tempjumlah < jumlah_user_like) {
         					System.out.println("masuk4");
         					id_user_like[idx].add(random);
         					idcourselike[idx].add(id);
         				}
         				tempjumlah += 1;
         			}
         		}
         		System.out.println("masuk 3");
         		idx+= 1;
         	}
        	
        	System.out.println("Pembatas 1");
     		int idx1 = 0;
     		for(int i = 0;i<docs.size();i++) { 
     			for(int j = 0;j<history[i].size(); j++) {
     				 Map<String, Object> c = new HashMap<>(); 
     	   			 c.put("id", idx1);
     	   			 c.put("iduser", history[i].get(j));
     	   			 c.put("idcourse", idd[i].get(j));
     	   			 String iddoc = StringUtils.leftPad(idx1+"", 4, "0"); 
     	   			ApiFuture<WriteResult> writeResult =db.collection("views").document(iddoc).set(c); 
     	   			idx1 += 1;
     			}
     		}
     		
     		int idx2 = 0;
     		for(int i = 0;i<docs.size();i++) { 
     			for(int j = 0;j<id_user_like[i].size(); j++) {
     				 Map<String, Object> c = new HashMap<>(); 
     	   			 c.put("id", idx2);
     	   			 c.put("iduser", id_user_like[i].get(j));
     	   			 c.put("idcourse", idcourselike[i].get(j));
     	   			 String iddoc = StringUtils.leftPad(idx2+"", 4, "0"); 
     	   			ApiFuture<WriteResult> writeResult =db.collection("likes").document(iddoc).set(c); 
     	   			idx2 += 1;
     			}
     		}
   			System.out.println("sukses");
        }catch (Exception e) {
        	System.out.println("error");
        	e.printStackTrace();
        }*/
		
		
     			 
     			 
     			 
     			 
//		String[] bulan = new String[] {"January", "Febuary", "Maret", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
//        PrintWriter out = response.getWriter();
//        try { 
//			 db = FirestoreOptions.newBuilder().setProjectId("proyek-cc-lynda").setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream("lynda-310811-08c61c30aea2.json"))).build().getService();
//			 for(int i = 0;i<63;i++) {
//				 Random rnd = new Random();
//				 String hari = StringUtils.leftPad(rnd.nextInt(29)+1+"", 2, "0");
//				 int bulann = rnd.nextInt(11)+1;
//				 Date date = new Date();
//				 String tangall = "2019-"+bulann+"-"+hari+" 10:10:10";
//				 out.println(tangall);
//			     Timestamp tanggal = Timestamp.valueOf("2019-"+bulann+"-"+hari+" 10:10:10");
//				 Map<String, Object> c = new HashMap<>(); 
//				 c.put("released", tanggal);
//				 String id = StringUtils.leftPad(i+"", 4, "0"); 
//				 c.put("id", i);
//				 db.collection("courses").document(id).update(c);
//	
//			 }
//		 } catch (Exception e) { // TODO Auto-generated catch block
//			 out.println("ERROR GAN!"); 
//			 e.printStackTrace(); 
//		 }
        //46
      	 /*String[] bulan = new String[] {"January", "Febuary", "Maret", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
		 for(int i = 0;i<297;i++) {
			 Map<String, Object> c = new HashMap<>(); 
			 //c.put("topic", "Technology");
			 
			 Random rnd = new Random();
			 Integer hari = rnd.nextInt(25)+1;
			 String bulann = bulan[rnd.nextInt(11)];
			 String released_date = bulann + " " + String.valueOf(hari) + ", 2019";
			 c.put("released", i);
			 String id = StringUtils.leftPad(i+"", 4, "0"); 
			 db.collection("courses").document(id).update(c);

		 }*/

		 String[] bulan = new String[] {"January", "Febuary", "Maret", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

		 File file = new File("D:\\eclipse\\cloudcomp\\dataset.txt"); 
		 PrintWriter out = response.getWriter();
		 BufferedReader br = new BufferedReader(new FileReader(file));
		  
		 String st; 
		 String semua_baris = "";
		 while ((st = br.readLine()) != null) {
			 semua_baris += st; 
			 		 } 
		 br.close();
		 String[] hasil_split = semua_baris.split("###");
		 String[] id = new String[hasil_split.length];
		 String[] topic = new String[hasil_split.length];
		 String[] subtopic = new String[hasil_split.length];
		 String[] judul = new String[hasil_split.length];
		 String[] deskripsi = new String[hasil_split.length]; 
		 String[] instructor = new String[hasil_split.length];
		 Integer[] viewer = new Integer[hasil_split.length]; 
		 Integer[] like = new Integer[hasil_split.length];
		 Integer[] free = new Integer[hasil_split.length];
		 String[] video = new String[hasil_split.length];
		 String[] thumbnail = new String[hasil_split.length];
		 
		 
		 int idx_course = 0;
		 for(int i = 0; i < hasil_split.length; i++) {
			 String[] column = hasil_split[i].split("@@@");
			 Random rand = new Random();
			 //if(i%4==0 && (i < 148 || i > 176) && !column[2].equalsIgnoreCase("Cloud Security") && !column[2].equalsIgnoreCase("Database Development")) {
				// Generate random integers in range 0 to 999 
			 int random = rand.nextInt(2);
			 free[idx_course] = 0; 
			 video[idx_course] = i+".mp4";
			 thumbnail[idx_course] = i+".jpg";
			 if(random==1) { 
				 free[idx_course] = 1; 
			 }
			 int view = rand.nextInt(40)+10;
			 int liked = view - 5;
			 if(view<=5) {
				 liked = 0;
			 }
			 if(i>=40) { 
				 id[idx_course] =StringUtils.leftPad(idx_course+"", 4, "0"); 
				 topic[idx_course] = column[1]; 
				 subtopic[idx_course] =column[2]; 
				 judul[idx_course] = column[3]; 
				 deskripsi[idx_course] = column[4]; 
				 instructor[idx_course] = column[5];
				 
				 viewer[idx_course] = view;
				 like[idx_course] = liked;
			 } 
			 else { 
				 id[idx_course] = StringUtils.leftPad(idx_course+"", 4, "0"); 
				 topic[idx_course] = column[1]; 
				 subtopic[idx_course] = column[2]; 
				 judul[idx_course] = column[4]; 
				 deskripsi[idx_course] = column[5]; 
				 instructor[idx_course] = column[6]; 
				 viewer[idx_course] = view; 
				 like[idx_course] = liked;
			 } 
			 
			 idx_course += 1;
			 //}
			 
		 }
		 //for(int i = 0; i < idx_course; i++) {
			 //out.println(id[i]+"-"+topic[i]+"-"+subtopic[i]+"-"+viewer[i]+"-"+like[i]+"-"+instructor[i]+"-"+video[i]+"<br>"); 
		 //} 
      
		 /*File file_instructor = new File("D:\\eclipse\\cloudcomp\\instructor.txt"); 
		 BufferedReader br_instructor = new BufferedReader(new FileReader(file_instructor));
		  
		 String st_instructor; 
		 String semua_baris_instructor = "";
		 while ((st_instructor = br_instructor.readLine()) != null) {
			 semua_baris_instructor += st_instructor;
			 
		 } 
		 System.out.println("<br>");
		 br_instructor.close();
		 String[] hasil_split_instructor = semua_baris_instructor.split("###");
		 Integer[] id_instructor = new Integer[hasil_split_instructor.length];
		 String[] nama_instructor = new String[hasil_split_instructor.length];
		 String[] deskripsi_instructor = new String[hasil_split_instructor.length]; 
		 String[] foto = new String[hasil_split_instructor.length];
		 String[] jabatan = new String[hasil_split_instructor.length];
		 String[] email = new String[hasil_split_instructor.length];
		 String[] password = new String[hasil_split_instructor.length];
		 
		 int idx_instructor = 0;
		 
		 for(int j = 0; j< hasil_split_instructor.length; j++) {
			 String[] column = hasil_split_instructor[j].split("@@@");
			 String result = column[1].replace(" ", "");
			 for(int i = 0; i<idx_course;i++) {
				 String banding = instructor[i].replace(" ", "");
				 banding = banding.toLowerCase();
				 if(result.equalsIgnoreCase(banding)) {
					 id_instructor[idx_instructor] = idx_instructor;
					 nama_instructor[idx_instructor] = column[1];
					 email[idx_instructor] = banding+"@gmail.com";
					 password[idx_instructor] = md5.getMd5(banding);
					 deskripsi_instructor[idx_instructor] = column[2];
					 jabatan[idx_instructor] = column[3];
					 foto[idx_instructor] = j+".jpg";
					 idx_instructor++;
					 i = idx_course;
				 }
			 }
		 }
		 
		 for(int i = 0; i < idx_instructor; i++) {
			 System.out.println(id_instructor[i]+"-"+nama_instructor[i]+"-"+deskripsi_instructor[i]+"-"+jabatan[i]+"-"+foto[i]+"<br><br>"); 
		 }*/
		 
		 try { 
			 //db = FirestoreOptions.newBuilder().setProjectId("proyek-cc-lynda").setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream("lynda-310811-08c61c30aea2.json"))).build().getService();
			  
			 // Insert and update (if the document already exists, it will be updated)
			 //out.println(hasil_split.length);
			 /*for(int i = 0;i<idx_course;i++) { 
				 /*Random rnd = new Random();
				 Integer hari = rnd.nextInt(25)+1;
				 String bulann = bulan[rnd.nextInt(11)];
				 String released_date = bulann + " " + String.valueOf(hari) + ", 2019";
				 Map<String, Object> c = new HashMap<>(); 
				 c.put("released", released_date);
				 c.put("topic", topic[i]);
				 c.put("subtopic", subtopic[i]);
				 c.put("judul", judul[i]);
				 c.put("deskripsi", deskripsi[i]);
				 c.put("instructor", instructor[i]);
				 c.put("viewer", viewer[i]); 
				 c.put("like",like[i]);
				 c.put("free", free[i]); 
				 c.put("video", video[i]);
				 c.put("thumbnail", thumbnail[i]);
				 ApiFuture<WriteResult> writeResult =db.collection("courses").document(id[i]).set(c); */
			 	 InstructorClass ic = new InstructorClass();
				 for(int i = 0; i< idx_course; i++) {
			 		 Map<String, Object> c = new HashMap<>(); 
			 		 //c.put("topic", "Technology");
			 		 c.put("index_email", ic.getIdDetailEmailByName(instructor[i]));
					 ApiFuture<WriteResult> writeResult =db.collection("courses").document(id[i]).update(c); 
			 	 }
		 
		 } catch (Exception e) { // TODO Auto-generated catch block
			 //out.println("ERROR GAN!"); 
			 e.printStackTrace(); 
		 }
		
		
// USER
		// USER
		// USER
		// USER
		// USER
		// USER
//		File file = new File("D:\\python\\email.txt"); 
//		PrintWriter out = response.getWriter();
//		BufferedReader br = new BufferedReader(new FileReader(file)); 
//		String st; 
//		String semua_baris = "";
//
//		String[] nama = new String[1000];
//		String[] email = new String[1000];
//		String[] password = new String[1000];
//		int ke = 0;
//		int idx = 0;
//		int kembar = 0;
//		while ((st = br.readLine()) != null) {
//			if(ke%2 == 0) {
//				for(int k = 0;k<idx;k++) {
//					if(st == email[k]) {
//						kembar = 1;
//					}
//				}
//				if(kembar==0) {
//					email[idx] = st;
//					String[] pisah = email[idx].split("@");
//					password[idx] = pisah[0].toLowerCase();
//					
//				}
//			}
//			else {
//				if(kembar==0) {
//					nama[idx] = st;
//					idx += 1;
//				}
//				kembar = 0;
//			}
//			ke++;
//		} 
//		br.close();
//		out.print(idx+"");
//		for(int i = 0; i < idx; i++) {
//			password[i] = md5.getMd5(password[i]);
//			email[i] = email[i].toLowerCase();
//		}
//////////////INSTRUCTOR
//////////////INSTRUCTOR
//////////////INSTRUCTOR
//////////////INSTRUCTOR
//////////////INSTRUCTOR
		/*System.out.println(idx_instructor+"");
		try { 
			 // Insert and update (if the document already exists, it will be updated)
			 for(int i = 0;i<idx_instructor;i++) {
				 Map<String, Object> c = new HashMap<>();
				 //c.put("id",i);
				 //c.put("nama", nama_instructor[i]);
				 //c.put("deskripsi", deskripsi_instructor[i]);
				 //c.put("accepted", "1");
				 //c.put("id_detail_email", id_instructor[i]);
				 //c.put("accepted", 1);
				 
				 String id_ins = StringUtils.leftPad(i+"", 4, "0"); 
				 //System.out.println(id_ins+"");
				 //ApiFuture<WriteResult> test = db.collection("instructors").document(id_ins).set(c); 
				 
				 c = new HashMap<>();
				 //c.put("id", i);
				 //c.put("nama", nama_instructor[i]);
				 //c.put("email", email[i]);
				 //c.put("password", password[i]);
				 //c.put("apa_instructor", "1");
				 //c.put("verifikasi", "1");
				 //c.put("foto", i+".jpg");
				 //c.put("banned", "0");
				 c.put("premium", 0);
				 ApiFuture<WriteResult> writeResult2 = db.collection("users").document(id_ins).update(c); 
			 } // Delete
			
			
		 
		 } catch (Exception e) { // TODO Auto-generated catch block
			 System.out.println("ERROR GAN!"); 
			 e.printStackTrace(); 
		 }
		 System.out.println("selesai");*/
	}

}
