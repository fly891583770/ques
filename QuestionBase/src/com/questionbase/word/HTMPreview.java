package com.questionbase.word;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import com.questionbase.hbm.*;
import org.hibernate.*;
import com.questionbase.hbm.factory.*;
import java.sql.*;

public class HTMPreview {
	
	 private String createHtmFile(String parentDocPath, String parentHtmPath, String id, String action){
		 
		  System.out.println("Create htm start--------------");
		  FileManager fm = new FileManager();
		  
		  Session heSession = QuestionBaseFactory.GetInstance()
		    .getSessionFactory().openSession();
		  Stream s = (Stream)heSession.load(Stream.class, Integer.parseInt(id.trim()) );
		  //create htm file for preview
		  //doc path and filename
		  String docPath = parentDocPath;
		  if(!docPath.endsWith(File.separator)){
		   docPath += File.separator;
		  }
		  String randomFilename = fm.getRandomFileName();
		  docPath += randomFilename + ".doc";
		  
		  //htm folder
		  String htmURL =  fm.getRandomFilePath(parentHtmPath);//fm-----------------------------random folder
		  String htmPath = parentHtmPath;
		  if(!htmPath.endsWith(File.separator)){
		   htmPath += File.separator;
		  }
		  htmPath += htmURL;
		  //transfer word to htm   docPath,htmPath
		  //TBD--------------------------------------
		  //Get content from DB
		  Blob wordQuestion = null;
		  if(("title").equalsIgnoreCase(action)){
		   wordQuestion = s.getQuestionTitle();
		  }else if(("answer").equalsIgnoreCase(action)){
		   wordQuestion = s.getQuestionAnswer();
		  }
		  if(wordQuestion!=null){
			  try{
				  //******读取磁盘文件，输出文件流 开始*******************************
				  byte[] fileBytes = wordQuestion.getBytes(1,(int)wordQuestion.length());
				  FileOutputStream outputStream =  new FileOutputStream( docPath);
				  outputStream.write(fileBytes);
				  outputStream.flush();
				  outputStream.close();
				  outputStream = null;
				  //******读取磁盘文件，输出文件流 结束*******************************
			  } catch (Exception e) {
			  }
			  
			  ArrayList<String> wordlist = new ArrayList<String>();
			  wordlist.add(docPath);
			  WordToHtml wordtohtml = new WordToHtml();
			  wordtohtml.changeFiles(wordlist, htmPath);
			  //-----------------------------------------
		  
		  
			  htmURL += randomFilename + ".htm";//to DB
		  
			  //Update URL in DB
			  heSession.beginTransaction();
			  Query query = null;
			  if(("title").equalsIgnoreCase(action)){
				  //set title URL
				  //TBD---------------------------------------------
				  query = heSession
						  .createQuery("update Stream o set o.questionTitleURL=? where code=" + id);
				  query.setParameter( 0, htmURL);
				  System.out.println("update DB htmURL=" + htmURL);
			  }else if(("answer").equalsIgnoreCase(action)){
				  //set answer URL
				  //TBD------------------------------------
				  query = heSession
						  .createQuery("update Stream o set o.questionAnswerURL=? where code=" + id);
				  query.setParameter( 0, htmURL);
			  }
			  query.executeUpdate();
			  heSession.getTransaction().commit();
		  
			  //delete doc
			  fm.deleteFile(docPath);//fm-------------------------------
		  
			  System.out.println("Create htm file: " + htmURL);
		  }else{
			  htmURL = null;
		  }
		  return htmURL;
		  
		 }
		public String getHtmFile(String parentDocPath, String parentHtmPath, String id, String action){
		  
			
		  if(!("title").equalsIgnoreCase(action)
		    && !("answer").equalsIgnoreCase(action)){
		   return "";
		  }
		  
		  if(parentDocPath!=null && parentDocPath.trim().length()>0
		    && parentHtmPath!=null && parentHtmPath.trim().length()>0
		    && id!=null && id.trim().length()>0
		    && action!=null && action.trim().length()>0){
		   String htmURL = "";
		   
		   Session heSession = QuestionBaseFactory.GetInstance()
		     .getSessionFactory().openSession();
		   Stream s = (Stream)heSession.load(Stream.class, Integer.parseInt(id.trim()) );
		      
		   //Get htm sub path from DB
		   if(("title").equalsIgnoreCase(action)){
		    //get title URL
		    //TBD---------------------------------------------
		    htmURL = s.getQuestionTitleURL();
		   }else if(("answer").equalsIgnoreCase(action)){
		    //get answer URL
		    //TBD------------------------------------
		    htmURL = s.getQuestionAnswerURL();
		   }
		   System.out.println("getHtmFile--------- htmURL=" + htmURL);
		   
		   String htmPath = parentHtmPath;
		   if(!htmPath.endsWith(File.separator)){
		    htmPath += File.separator;
		   }
		   
		   boolean bHtmExist = false;
		   if(htmURL!=null && htmURL.trim().length()>0){//No htm URL in DB
			   htmPath += htmURL;
			   System.out.println("getHtmFile--------- htmPath=" + htmPath);
			   File f = new File(htmPath);
			   if(f.exists()){
				   bHtmExist = true;
				   return htmURL;//return relative path
			   }else{//The URL in DB does not exist
				   bHtmExist = false;
			   }
		   }

		    if(!bHtmExist){
		    	return createHtmFile(parentDocPath, parentHtmPath, id, action);
		    }
		   
		  }
		  return "";
		  
		  
		 }

}
