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
 * Servlet implementation class updatefoto
 */
@WebServlet("/updatefoto")
@MultipartConfig
public class updatefoto extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	public String randomString() {
		String generatedString = RandomStringUtils.randomAlphanumeric(21);
	    return generatedString;
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String param = "";
		StorageClass storage = new StorageClass();
		String namaRandom = randomString()+".jpg";
		Part file_foto = request.getPart("file_foto");
		String nama=file_foto.getSubmittedFileName();
		if(nama != null && !nama.isEmpty()){
			Firestore db;
			String email = request.getParameter("emailuser");
			try {
		        db = FirestoreOptions.newBuilder().setProjectId("lynda-310811").setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream("lynda-310811-e0f6c225dbfe.json"))).build().getService();    
				CollectionReference user = db.collection("users");
	    		Query query = user.whereEqualTo("email", email).limit(1);
		        ApiFuture<QuerySnapshot> future = query.get();
		        List<QueryDocumentSnapshot> docs = future.get().getDocuments();
		        String foto = "";
		        int idprofile = -1;
		        for (QueryDocumentSnapshot document : docs) {
	                foto = document.getString("foto");
	                idprofile = document.getLong("id").intValue();
	            }
				
		    	if(!foto.contains("none")) {
		    		int sudah_hapus = storage.deleteStorage("foto", foto);
		    	}
		    	
		    	storage.upload(file_foto,"foto", namaRandom);
		    	Map<String, Object> profile = new HashMap<>();
	    		profile.put("foto", namaRandom);
	    		String id_profile = StringUtils.leftPad(idprofile+"", 4, "0"); 
		    	db.collection("users").document(id_profile).update(profile);
				param = "Update is Made";
			}catch (Exception e) {
		    	  e.printStackTrace();
		    }
	    }
		else {
			param = "Please Fill All Field";
		}
		request.setAttribute("messege_upload", param);
		request.getRequestDispatcher("/profileView.jsp").forward(request, response);
	}

}















