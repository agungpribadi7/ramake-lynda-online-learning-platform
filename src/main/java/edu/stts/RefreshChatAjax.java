package edu.stts;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

/**
 * Servlet implementation class RefreshChatAjax
 */
@WebServlet("/RefreshChatAjax")
public class RefreshChatAjax extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RefreshChatAjax() {
        super();
        // TODO Auto-generated constructor stub
    }
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id")+"");
		int apakahinstructor = Integer.parseInt(request.getParameter("apakahinstructor")+"");
		
		ArrayList output[] = new ArrayList[4];
		//System.out.println("masuk");
		for (int i = 0; i < 4; i++) { 
            output[i] = new ArrayList(); 
        } 
		try {
			Firestore db = FirestoreOptions.newBuilder().setProjectId("proyek-cc-lynda").setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream("lynda-310811-e0f6c225dbfe.json"))).build().getService();
			CollectionReference chat = db.collection("chats");
			Query query = null;
			if(apakahinstructor == 1) {
				query = chat.whereEqualTo("idinstructor", id);
			}
			else if(apakahinstructor == 0){
				query = chat.whereEqualTo("iduser", id);
			}
			//System.out.println("masuk 2");
			ApiFuture<QuerySnapshot> querySnapshot1 = query.get();
		    List<QueryDocumentSnapshot> documents = querySnapshot1.get().getDocuments();
		    for (QueryDocumentSnapshot document : documents) {
		    	int read = document.getLong("read").intValue();
		    	int pengirimChat = document.getLong("pengirim").intValue();
		    	
		    	if(pengirimChat != id && read == 0) {
		    		output[0].add(document.getString("isi"));
		    		if(apakahinstructor == 0) {
		    			output[1].add(document.getLong("idinstructor").intValue());
		    		}
		    		if(apakahinstructor == 1) {
		    			output[1].add(document.getLong("iduser").intValue());
		    		}
		    		SimpleDateFormat formatter = new SimpleDateFormat("MMMM/dd hh:mm:ss");  
			    	String date = formatter.format(document.getDate("date"));  
		    		output[2].add(date);
		    		output[3].add(document.getLong("id").intValue());
		    	}
		    }
		    //System.out.println("masuk 3 "+ documents.size());
		}
		catch(Exception e) {
			response.setStatus(HttpServletResponse.SC_OK);
		}
		if(output[0] == null || output[0].size() == 0) {
			response.getWriter().write("-1");
			response.getWriter().flush();
			response.getWriter().close();
		}
		else {
			response.setStatus(HttpServletResponse.SC_OK);
			response.getWriter().write(Arrays.toString(output));
			response.getWriter().flush();
			response.getWriter().close();
		}
		
	}
}
