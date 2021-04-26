package edu.stts;

import java.io.FileInputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;

public class CekPremium {
	static int ctr = 0;
	static int active = 0;
	public void cekExpired(int iduser) {
	   Timer t = new Timer();
	   WalletClass premium = new WalletClass();
	   int idx = 0;
	   //mengecek setiap 10 menit sekali, jika waktu sekarang belum diantara harisebelumexpired dan hariexpired, timer tidak jalan 
	   TimerTask tt = new TimerTask() {  
	       @Override  
	       public void run() {  
	    	   try {
	    		   ctr += 1;
	    		   System.out.println("masuk timer");
	    		   ArrayList output = premium.getDurasiPremium(iduser);
	    		   int durasi = -1;
	    		   
	    		   Calendar cal;
	    		   Timestamp timestamp;
	    		   Timestamp timestampOneDayBefore;
	    		   Timestamp sekarang = new Timestamp(System.currentTimeMillis());
	    		   if(output.size() > 0) {
	    			   durasi = (int) output.get(0);
	    			   Date tanggal = (Date)output.get(1);
	    			   cal = Calendar.getInstance();
	    			   cal.setTimeZone(TimeZone.getTimeZone("GMT"));
	    			   cal.setTime(tanggal);
	    			   cal.add(Calendar.DATE, durasi);
	    			   timestamp = new Timestamp(cal.getTimeInMillis());
	    			   
	    			   Calendar calBefore = Calendar.getInstance();
	    			   calBefore.setTimeZone(TimeZone.getTimeZone("GMT"));
	    			   calBefore.setTime(tanggal);
	    			   calBefore.add(Calendar.DATE, -1);
	    			   Timestamp timestampBefore = new Timestamp(calBefore.getTimeInMillis());
	    			   if(sekarang.after(timestampBefore) && sekarang.before(timestamp)) {
	    				   active = 1;
	    			   }
	    			   if (sekarang.after(timestamp)) {
	    				   premium.disablePremium(iduser);
	    			   }
	    		   }
	    		   else {
	    			   t.cancel();
	    		   }
	    		   
	    		   if(active == 0 || ctr == 6) {
	    			   t.cancel();
	    		   }
	    	   }catch(Exception e) {
	    		   
	    	   }
	       };  
	   };  
	   t.scheduleAtFixedRate(tt,0,10*60*1000);    
   }
	
}
