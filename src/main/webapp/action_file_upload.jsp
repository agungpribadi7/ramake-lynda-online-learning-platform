<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.io.*,java.util.*, javax.servlet.*" %>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="org.apache.commons.fileupload.*" %>
<%@ page import="org.apache.commons.fileupload.disk.*" %>
<%@ page import="org.apache.commons.fileupload.servlet.*" %>
<%@ page import="org.apache.commons.io.output.*" %>
<%@ page import="java.io.IOException" %>
<%@ page import="java.nio.file.*" %>
<%@ page import="java.util.regex.Pattern" %>
<%@ page import="javax.imageio.ImageIO" %>
<%@ page import="java.awt.image.BufferedImage" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Guru File Upload</title>
</head>
<body>
<%
   File file ;
   int maxFileSize = 1000000 * 1024;
   int maxMemSize = 50000 * 1024;
   
   
 
   String contentType = request.getContentType();
   if ((contentType.indexOf("multipart/form-data") >= 0)) {
	   Path tempDir = null;
      DiskFileItemFactory factory = new DiskFileItemFactory();
      factory.setSizeThreshold(maxMemSize);
      ServletFileUpload upload = new ServletFileUpload(factory);
      upload.setSizeMax( maxFileSize );
      try{ 
    	 
         List fileItems = upload.parseRequest(request);
         String[] pisah = fileItems.toString().split(", ");
         System.out.println(fileItems);
         
         String pattern = Pattern.quote(System.getProperty("file.separator"));
         String[] fName = pisah[0].split(pattern);
         System.out.println(fName[fName.length-1]);

         String posisi = pisah[0].replace("[name=", "");
         tempDir = Files.createTempDirectory(fName[fName.length-1].replace(".jpg", ""));
         
         BufferedImage img = null;
         try {
             img = ImageIO.read(new File(posisi));
             File outputfile = new File("saved.png");
             ImageIO.write(img, "jpg", new File(tempDir+"\\"+fName[fName.length-1]));
         }catch(Exception e){
        	 System.out.println("error");
         }
         
      }catch(Exception ex) {
         System.out.println(ex);
      }
      System.out.println(tempDir);
   }else{
      out.println("<p>No file uploaded</p>"); 
   }
%>
</body>
</html>