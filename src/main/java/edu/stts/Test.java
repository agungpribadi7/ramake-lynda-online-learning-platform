package edu.stts;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;

/**
 * Servlet implementation class Test
 */

public class Test {

	
	public String readFile(String path) {
		String filePath = path;
		 
        return readLineByLineJava8( filePath );
    }
 
 
    //Read file content into the string with - Files.lines(Path path, Charset cs)
 
    private static String readLineByLineJava8(String filePath) 
    {
        StringBuilder contentBuilder = new StringBuilder();
 
        try (Stream<String> stream = Files.lines( Paths.get(filePath), StandardCharsets.UTF_8)) 
        {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        }
        catch (IOException e) 
        {
            e.printStackTrace();
        }
 
        return contentBuilder.toString();
    }
	
	/*public String insertUser() {
		int id = 0;
		Map<String, Object> usersbaru = new HashMap<>();
		usersbaru.put("apa_instructor", "0");
		usersbaru.put("banned", "0");
		usersbaru.put("email",email);		
		usersbaru.put("foto","none.jpg");
		usersbaru.put("id", id);
		usersbaru.put("nama", name);
		usersbaru.put("password", md5.getMd5(password));
		usersbaru.put("tokenLupa", "");
		usersbaru.put("verifikasi", "0");
		usersbaru.put("kodeverif",randomString(10));
		usersbaru.put("wallet", 0);
		usersbaru.put("premium", 0);
		CollectionReference temp2 = db.collection("users");
		Query query2 = temp2.whereEqualTo("email", email);
		ApiFuture<QuerySnapshot> querySnapshot2 = query2.get();
		int total  = querySnapshot2.get().size();			    		
		if(total <= 0){
			String param = "";
			if(id<10) {
				param = "000"+id+"";	
			}else if(id<100) {
				param = "00"+id+"";	
			}else if(id<1000){
				param = "0"+id+"";	
			}else {
				param = id+"";
			}
			ApiFuture<WriteResult> writeResult = db.collection("users").document(param).set(usersbaru);
		}
		return "adsf";
	}*/
	


}
