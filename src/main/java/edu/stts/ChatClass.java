package edu.stts;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.Query.Direction;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;

public class ChatClass {
	private Firestore db;
	public void firstChat(int iduser, int idinstructor) {
		  int adakah = 0;
		  try {
		        db = FirestoreOptions.newBuilder().setProjectId("lynda-310811").setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream("lynda-310811-e0f6c225dbfe.json"))).build().getService();    
		        CollectionReference chat = db.collection("chats");
		        Timestamp waktu = new Timestamp(System.currentTimeMillis());
		        Query query = chat.whereGreaterThan("id", -1);
				int idChat = -1;
				System.out.println("masuk first 1");
				ApiFuture<QuerySnapshot> querySnapshot1 = query.get();
			    int temp1 = querySnapshot1.get().size()-1;
	    		Query query3 = chat.offset(temp1).limit(1);	
	    		System.out.println("masuk first 3");
			    ApiFuture<QuerySnapshot> querySnapshot3 = query3.get();
			    List<QueryDocumentSnapshot> documents = querySnapshot3.get().getDocuments();
			    System.out.println("masuk first 2");
			    for (QueryDocumentSnapshot document : documents) {
					long longid = document.getLong("id");
					idChat = (int) longid+1;						
				}
			    System.out.println("masuk first 3 "+idChat);
				Query query2 = chat.whereEqualTo("iduser", iduser).whereEqualTo("idinstructor", idinstructor);
				ApiFuture<QuerySnapshot> querySnapshot2 = query2.get();
			    List<QueryDocumentSnapshot> documents2 = querySnapshot2.get().getDocuments();
			    Map<String, Object> c = new HashMap<>(); 
			    System.out.println("masuk first 4");
			    if(documents2.size() == 0) {
			    	 c.put("id", idChat);
					 c.put("date", waktu);
					 c.put("iduser", iduser);
					 c.put("idinstructor", idinstructor);
					 c.put("pengirim", iduser);
					 c.put("isi", "");
					 c.put("read", 1);
					String iddoc = StringUtils.leftPad(idChat+"", 4, "0"); 
					ApiFuture<WriteResult> writeResult =db.collection("chats").document(iddoc).set(c); 
			    }
			    db.close();
		  }
		  catch(Exception e) {
			  System.out.println("error get like");
		  }
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
	
	
	public ArrayList[] getAllChats(int iduser, int apaInstructor) throws FileNotFoundException, IOException {
		ArrayList output[] = new ArrayList[13];
		
		for (int i = 0; i < 13; i++) { 
            output[i] = new ArrayList(); 
        } 
		try {
			//System.out.println("apa instructor = "+apaInstructor);
			db = FirestoreOptions.newBuilder().setProjectId("lynda-310811").setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream("lynda-310811-e0f6c225dbfe.json"))).build().getService();    
	        CollectionReference chat = db.collection("chats");
	        Query query = null;
	        if(apaInstructor == 0) {
	        	query = chat.whereEqualTo("iduser", iduser).orderBy("idinstructor", Direction.ASCENDING).orderBy("date", Direction.ASCENDING);
	        }
	        else {
	        	query = chat.whereEqualTo("idinstructor", iduser).orderBy("iduser", Direction.ASCENDING).orderBy("date", Direction.ASCENDING);
	        }
			ApiFuture<QuerySnapshot> querySnapshot1 = query.get();
		    List<QueryDocumentSnapshot> documents = querySnapshot1.get().getDocuments();
		    CollectionReference instructor = db.collection("instructors");
		    CollectionReference user = db.collection("users");
		    System.out.println("masuk");
		    for (QueryDocumentSnapshot document : documents) {
		    	String isi = document.getString("isi");
		        SimpleDateFormat formatter = new SimpleDateFormat("MMMM/dd hh:mm:ss");  
		    	String date = formatter.format(document.getDate("date"));  
		    	int index_email = -1;
		    	int idinstructor = document.getLong("idinstructor").intValue();
		    	int iduserdichat = document.getLong("iduser").intValue();
		    	if(apaInstructor == 0) {
			    	index_email = document.getLong("idinstructor").intValue();
		    	}
		    	else { //kalau yang login instructor
		    		index_email = document.getLong("iduser").intValue();
		    	}
		    	
			    
			    Query query3 = user.whereEqualTo("id", index_email);
		    	ApiFuture<QuerySnapshot> querySnapshot3 = query3.get();
			    List<QueryDocumentSnapshot> documents3 = querySnapshot3.get().getDocuments();
			    for (QueryDocumentSnapshot doc2 : documents3) {
			    	output[0].add(doc2.getString("nama"));
			    	output[4].add(doc2.getString("email"));
			    	if(apaInstructor == 0) {
			    		output[3].add(idinstructor);//buat room
			    	}
			    	else if(apaInstructor == 1) {
			    		output[3].add(iduserdichat); //buat room
			    	}
			    }
			    //System.out.println("masuk 4 " + apaInstructor);
			    if(apaInstructor == 0) {
			    	if(!isi.equals("")){
			    		//System.out.println("MASUKKKK 5");
			    		output[5].add(idinstructor); //buat id isi chat
			    	}
			    }
			    else {
			    	if(!isi.equals("")){
			    		//System.out.println("MASUKKKK 5");
			    		output[5].add(iduserdichat); //buat id isi chat
			    	}
			    }
			    
			    if(!isi.equals("")) {
			    	output[6].add(document.getLong("pengirim").intValue());
				    output[7].add(document.getLong("read").intValue());
			    	output[1].add(isi);
			    	output[2].add(date);
			    	output[10].add(document.getLong("id").intValue());
			    }
		    }
		    //System.out.println("bawah");
	    	ArrayList tempIdIns = new ArrayList();
	    	Query querySemuaChat = null;
	    	if(apaInstructor == 0) {
	    		querySemuaChat = chat.whereEqualTo("iduser", iduser).orderBy("idinstructor", Direction.ASCENDING).orderBy("date", Direction.ASCENDING);;
	    	}
	    	else {
	    		querySemuaChat = chat.whereEqualTo("idinstructor", iduser).orderBy("iduser", Direction.ASCENDING).orderBy("date", Direction.ASCENDING);;
	    	}
	    	ApiFuture<QuerySnapshot> querySnapshotSemuaChat = querySemuaChat.get();
		    List<QueryDocumentSnapshot> documentsSemuaChat = querySnapshotSemuaChat.get().getDocuments();
		    ArrayList jumlahChatList = new ArrayList();
		    int angkaNotRead = 0;
		    int prevIdInstructor = -1;
		    int jumlahChat = 0;
		    //System.out.println("output 1 size = "+output[1].size());
		    for (QueryDocumentSnapshot doc : documentsSemuaChat) {
		    	int tempid = 0; //idlawan bicara
		    	if(apaInstructor == 0) {
		    		tempid = doc.getLong("idinstructor").intValue();
		    		
		    	}
		    	else if(apaInstructor == 1) {
		    		tempid = doc.getLong("iduser").intValue();
		    	}
		    	int notReadYet = doc.getLong("read").intValue();
		    	int pengirim = doc.getLong("pengirim").intValue(); 
		    	String isi = doc.getString("isi");
		    	//System.out.println("id - "+tempid);
		    	//System.out.println("read - "+notReadYet);
		    	System.out.println(tempid+"-"+pengirim+"-"+notReadYet);
		    	
		    	if(!isi.equals("") && tempid == prevIdInstructor) {
		    		jumlahChat += 1;
		    	}
		    	if(notReadYet == 0 && pengirim == tempid) {
		    		angkaNotRead += 1;
		    	}
		    	else if(tempid != prevIdInstructor && prevIdInstructor != -1){
		    		System.out.println("bawah masuk");
		    		output[8].add(angkaNotRead);
	    			output[9].add(prevIdInstructor);
	    			jumlahChatList.add(jumlahChat);
	    			angkaNotRead = 0;
	    			jumlahChat = 0;
		    	}
		    	
		    	prevIdInstructor = tempid; //nama variabel kalau user yang login
		    }
		    output[8].add(angkaNotRead);
		    output[9].add(prevIdInstructor);
		    jumlahChatList.add(jumlahChat);
		    System.out.println("Angka notread = "+angkaNotRead);
		    
		    
		    output[0] = removeDuplicates(output[0]); 
		    output[4] = removeDuplicates(output[4]);
		    //System.out.println("jumlah email di chat class = "+ output[4].size());
		    output[3] = removeDuplicates(output[3]);
		    
		    for(int i = 0;i<output[8].size();i++) {
		    	System.out.println("jumlah unread = "+output[8].get(i));
		    	System.out.println("jumlah chatlist = "+jumlahChatList.get(i));
		    }
		    
		    for(int i = 0;i< jumlahChatList.size(); i++) {
		    	int kapanUnreadMuncul = (int)jumlahChatList.get(i) - (int)output[8].get(i);
		    	System.out.println(kapanUnreadMuncul);
		    	if((int)output[8].get(i) == 0) {
		    		output[11].add(-1);
			    	output[12].add(output[9].get(i)); //buat dptin roomid untuk unread
		    	}
		    	else {
			    	output[11].add(kapanUnreadMuncul);
			    	output[12].add(output[9].get(i)); //buat dptin roomid untuk unread
		    	}
		    }
		    db.close();
		    return output;
		   
		}catch(Exception e) {
			System.out.println("error get all chat");
		}
		return output;
		
	}
	
	
}
