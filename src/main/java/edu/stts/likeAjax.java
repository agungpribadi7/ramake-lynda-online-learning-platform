package edu.stts;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

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

/**
 * Servlet implementation class likeAjax
 */
@WebServlet("/likeAjax")
public class likeAjax extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public likeAjax() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int iduser = Integer.parseInt(request.getParameter("user")+"");
		int tipe = Integer.parseInt(request.getParameter("tipe"));
		int idvideo = Integer.parseInt(request.getParameter("course"));
		String leftPadIdvideo = StringUtils.leftPad(idvideo+"", 4, "0"); 
		System.out.println("tipe "+ tipe);
		try {
			
			Firestore db = FirestoreOptions.newBuilder().setProjectId("lynda-310811").setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream("lynda-310811-e0f6c225dbfe.json"))).build().getService();    
			CollectionReference like = db.collection("likes");
			Query query = null;
			
			if(tipe == 0) {
				query = like.whereGreaterThan("id", -1);
				int idLike = -1;
				ApiFuture<QuerySnapshot> querySnapshot1 = query.get();
				System.out.println("ajax 1");
			    int temp1 = querySnapshot1.get().size()-1;
			    System.out.println("ajax 4");
	    		Query query3 = like.offset(temp1).limit(1);			   
	    		System.out.println("ajax 2");
			    ApiFuture<QuerySnapshot> querySnapshot3 = query3.get();
			    List<QueryDocumentSnapshot> documents = querySnapshot3.get().getDocuments();
			    System.out.println("ajax 3");
			    for (QueryDocumentSnapshot document : documents) {
					long longid = document.getLong("id");
					idLike = (int) longid+1;						
				}
			    System.out.println("idLike baru " + idLike);
 				 Map<String, Object> c = new HashMap<>(); 
 	   			 c.put("id", idLike);
 	   			 c.put("iduser", iduser);
 	   			 c.put("idcourse", idvideo);
 	   			 String iddoc = StringUtils.leftPad(idLike+"", 4, "0"); 
 	   			ApiFuture<WriteResult> writeResult =db.collection("likes").document(iddoc).set(c); 
     	   			
				
				CollectionReference course = db.collection("courses");
	   			Query queryCourse = course.whereEqualTo("id", idvideo);
		        ApiFuture<QuerySnapshot> futureCourse = queryCourse.get();
		        List<QueryDocumentSnapshot> docsCourse = futureCourse.get().getDocuments();
		        int jumlah_like = 0;
		        System.out.println("jumlah size doc " + docsCourse.size());
		        Map<String, Object> cCourse = new HashMap<>(); 
		        for (QueryDocumentSnapshot document : docsCourse) {
		        	jumlah_like = document.getLong("like").intValue()+1;
		        	cCourse.put("like", jumlah_like);
		        }
		        System.out.println("id video " + idvideo);
		        System.out.println("jumlah like " + jumlah_like);
		        System.out.println("id doc " + leftPadIdvideo);
		        db.collection("courses").document(leftPadIdvideo).update(cCourse);
		        response.setStatus(HttpServletResponse.SC_OK);
				response.getWriter().write("0");
				response.getWriter().flush();
				response.getWriter().close();
			}
			else if(tipe == 1) {
				query = like.whereEqualTo("idcourse", idvideo).whereEqualTo("iduser", iduser);
				ApiFuture<QuerySnapshot> querySnapshot3 = query.get();
			    List<QueryDocumentSnapshot> documents = querySnapshot3.get().getDocuments();
			    String leftPadIdLike = null;
				for (QueryDocumentSnapshot document : documents) {
					leftPadIdLike = document.getLong("id").intValue()+"";
					leftPadIdLike = StringUtils.leftPad(leftPadIdLike+"", 4, "0"); 
				}
				
				db.collection("likes").document(leftPadIdLike).delete();
				CollectionReference course = db.collection("courses");
	   			Query queryCourse = course.whereEqualTo("id", idvideo);
		        ApiFuture<QuerySnapshot> futureCourse = queryCourse.get();
		        List<QueryDocumentSnapshot> docsCourse = futureCourse.get().getDocuments();
		        int jumlah_like = 0;
		        Map<String, Object> cCourse = new HashMap<>(); 
		        for (QueryDocumentSnapshot document : docsCourse) {
		        	jumlah_like = document.getLong("like").intValue()-1;
		        	cCourse.put("like", jumlah_like);
		        }
		        db.collection("courses").document(leftPadIdvideo).update(cCourse);
		        response.setStatus(HttpServletResponse.SC_OK);
				response.getWriter().write("1");
				response.getWriter().flush();
				response.getWriter().close();
			}
		}catch(Exception e) {
			response.setStatus(HttpServletResponse.SC_OK);
			response.getWriter().write("-1");
			response.getWriter().flush();
			response.getWriter().close();
		}
		
		
		
	}

}
