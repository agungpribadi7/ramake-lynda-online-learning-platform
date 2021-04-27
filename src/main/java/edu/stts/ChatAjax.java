package edu.stts;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

/**
 * Servlet implementation class ChatAjax
 */
@WebServlet("/ChatAjax")
public class ChatAjax extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChatAjax() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int iduser = Integer.parseInt(request.getParameter("user")+"");
		int idinstructor = Integer.parseInt(request.getParameter("instructor")+"");
		int pengirim = Integer.parseInt(request.getParameter("pengirim")+"");
		String pesan = request.getParameter("pesan");
		
		try {
			Firestore db = FirestoreOptions.newBuilder().setProjectId("lynda-310811").setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream("lynda-310811-e0f6c225dbfe.json"))).build().getService();    
			CollectionReference chat = db.collection("chats");
			Query query2 = null;
			
			query2 = chat.whereGreaterThan("id", -1);
			int idChat = -1;
			ApiFuture<QuerySnapshot> querySnapshot1 = query2.get();
		    int temp1 = querySnapshot1.get().size()-1;
    		Query query3 = chat.offset(temp1).limit(1);			    		
		    ApiFuture<QuerySnapshot> querySnapshot3 = query3.get();
		    List<QueryDocumentSnapshot> documents = querySnapshot3.get().getDocuments();
		    for (QueryDocumentSnapshot document : documents) {
				long longid = document.getLong("id");
				idChat = (int) longid+1;						
			}
			System.out.println("id chat = "+idChat);
			Query query = null;
			Map<String, Object> c = new HashMap<>(); 
   			 c.put("id", idChat);
   			 c.put("iduser", iduser);
   			 c.put("idinstructor", idinstructor);
   			 c.put("isi", pesan);
   			 c.put("pengirim", pengirim);
   			c.put("read", 0);
   			 Timestamp timestamp = new Timestamp(System.currentTimeMillis());
   			 c.put("date", timestamp);
   			 String iddoc = StringUtils.leftPad(idChat+"", 4, "0"); 
   			ApiFuture<WriteResult> writeResult =db.collection("chats").document(iddoc).set(c);
		    response.setStatus(HttpServletResponse.SC_OK);
			response.getWriter().write("1");
			response.getWriter().flush();
			response.getWriter().close();
			db.close();
		}catch(Exception e) {
			System.out.println("error unread");
			response.setStatus(HttpServletResponse.SC_OK);
			response.getWriter().write("-1");
			response.getWriter().flush();
			response.getWriter().close();
		}
	}

}
