package edu.stts;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.Charset;
import java.io.InputStream;

import com.google.api.gax.paging.Page;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.WriteChannel;
import com.google.cloud.storage.Acl;
import com.google.cloud.storage.Acl.Role;
import com.google.cloud.storage.Acl.User;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import javax.servlet.http.Part;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class StorageClass {
	
	public String getImage(String namaFolder, String namaFile) throws FileNotFoundException, IOException {
		Storage storage = StorageOptions.newBuilder()
				.setProjectId("lynda-310811")
				.setCredentials(ServiceAccountCredentials
				.fromStream(new FileInputStream("lynda-310811-e0f6c225dbfe.json"))).build()
				.getService();
	  	
	  	String bucketName = "lynda-310811.appspot.com";
		return "https://storage.googleapis.com/"+bucketName+"/"+namaFolder+"/"+namaFile;
	}

	
	public ArrayList getImageBatch(String namaFolder, ArrayList namaFile) throws FileNotFoundException, IOException {
		Storage storage = StorageOptions.newBuilder()
				.setProjectId("lynda-310811")
				.setCredentials(ServiceAccountCredentials
				.fromStream(new FileInputStream("lynda-310811-e0f6c225dbfe.json"))).build()
				.getService();
	  	
	  	String bucketName = "lynda-310811.appspot.com";
	  	ArrayList output = new ArrayList();
	  	
	  	for(int i = 0; i < namaFile.size(); i++) {
			output.add("https://storage.googleapis.com/"+bucketName+"/"+namaFolder+"/"+namaFile.get(i));
	  	}
	  	return output;
	}
	
	public Integer deleteStorage(String folder, String namafile) throws FileNotFoundException, IOException {
		Storage storage = StorageOptions.newBuilder()
				.setProjectId("lynda-310811")
				.setCredentials(ServiceAccountCredentials
				.fromStream(new FileInputStream("lynda-310811-e0f6c225dbfe.json"))).build()
				.getService();
		BlobId blobId = BlobId.of("lynda-310811.appspot.com", folder+"/"+namafile);
		boolean deleted = storage.delete(blobId);
		if(deleted) {
			return 1;
		}
		return -1;
	}
	
	@SuppressWarnings("deprecation")
	public void upload(Part file1, String folder, String namafile) throws IOException {
		
  	  	Part file = file1;//file fotonya
	    Storage storage = StorageOptions.newBuilder()
		        .setProjectId("lynda-310811")
		        .setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream("lynda-310811-e0f6c225dbfe.json")))
		        .build()
		        .getService();
		
		String bucketName = "lynda-310811.appspot.com"; // nama bucketnya
		BlobInfo blobInfo = BlobInfo
                .newBuilder(bucketName, folder+"/"+namafile) //rename nama fotomu, misal pake ID
                .setAcl(new ArrayList<>(Arrays.asList(Acl.of(User.ofAllUsers(), Role.READER))))
                .build();
		Blob blob = storage.create(blobInfo, file.getInputStream()); 
	}

}
