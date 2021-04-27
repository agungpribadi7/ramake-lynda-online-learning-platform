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

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;

/**
 * Servlet implementation class UnreadAjax
 */
@WebServlet("/UnreadAjax")
public class UnreadAjax extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UnreadAjax() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int iduser = Integer.parseInt(request.getParameter("user")+"");
		int apainstructor = Integer.parseInt(request.getParameter("apainstructor")+"");
		int idinstructor = Integer.parseInt(request.getParameter("instructor"));
		System.out.println("id instructor unread = "+idinstructor);
		try {
			Firestore db = FirestoreOptions.newBuilder().setProjectId("proyek-cc-lynda").setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream("lynda-310811-e0f6c225dbfe.json"))).build().getService();
			CollectionReference chat = db.collection("chats");
			Query query = null;
			if(apainstructor == 0) {
				query = chat.whereEqualTo("iduser", iduser).whereEqualTo("idinstructor", idinstructor).whereEqualTo("pengirim", idinstructor);
			}
			else {
				query = chat.whereEqualTo("iduser", iduser).whereEqualTo("idinstructor", idinstructor).whereEqualTo("pengirim", iduser);
			}
		    ApiFuture<QuerySnapshot> querySnapshot3 = query.get();
		    List<QueryDocumentSnapshot> documents = querySnapshot3.get().getDocuments();
		    System.out.println("masuk ajax un 1");
		    for (QueryDocumentSnapshot document : documents) {
		    	Map<String, Object> c = new HashMap<>(); 
				 c.put("read", 1);
				 int idxDoc = document.getLong("id").intValue();
				 String id = StringUtils.leftPad(idxDoc+"", 4, "0"); 
				 System.out.println("doc id = "+id);
				 db.collection("chats").document(id).update(c);					
			}
		    System.out.println("masuk ajax un 2");
		    response.setStatus(HttpServletResponse.SC_OK);
			response.getWriter().write("1");
			response.getWriter().flush();
			response.getWriter().close();
		}catch(Exception e) {
			System.out.println("error unread");
			response.setStatus(HttpServletResponse.SC_OK);
			response.getWriter().write("-1");
			response.getWriter().flush();
			response.getWriter().close();
		}
		
	}

}
