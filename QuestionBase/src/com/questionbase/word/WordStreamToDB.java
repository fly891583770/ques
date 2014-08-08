package com.questionbase.word;

import java.io.*;
import java.util.*;

import com.questionbase.hbm.*;

import org.hibernate.*;

import com.questionbase.hbm.factory.*;
import org.htmlparser.beans.StringBean;

class HtmInfo{ 
	 private String htmPath;
	 public HtmInfo(String htmPath){
		 this.htmPath = htmPath;
	 }
	 
	 public String getText(int max){
		 File f = new File(htmPath);
		 if(htmPath==null || !htmPath.trim().endsWith(".htm") || !f.isFile() || max<=0 ) 
			 return "";

		 
		 StringBean sb = new StringBean();  
		 
		//设置不需要得到页面所包含的链接信息  
		sb.setLinks(false);  
		//设置将不间断空格由正规空格所替代  
		sb.setReplaceNonBreakingSpaces(true);  
		//设置将一序列空格由一个单一空格所代替  
		sb.setCollapse(true);  
		//传入要解析的URL  
		sb.setURL(htmPath);  
		//返回解析后的网页纯文本信息  
		String text = sb.getStrings();  
		
		int len = text.length();
		if(len>max) len = max;
		return text.toString().substring(0, len); 
	 }
}
public class WordStreamToDB {

	
	 
	 private static String updateDB(String strSummary, String docTitlePath, String docAnswerPath, String htmTitleURL, String htmAnswerURL){
		 if((docTitlePath==null && docAnswerPath==null ) 
				 || (docTitlePath.trim().length()==0 && docAnswerPath.trim().length()==0)
				 || (!docTitlePath.trim().endsWith(".doc") && !docAnswerPath.trim().endsWith(".doc") )
				 || !(new File(docTitlePath)).exists() && !(new File(docAnswerPath)).exists()){
			 return "";
		 }
	 
		 String id = "";
		 Session heSession = QuestionBaseFactory.GetInstance().getSessionFactory().openSession();
		 heSession.beginTransaction();
		 Stream s = new Stream();
		 s.setSummary(strSummary);
		 s.setQuestionTitleURL(htmTitleURL);
		 s.setQuestionAnswerURL(htmAnswerURL);
		 try{
			 if(docTitlePath!= null && docTitlePath.trim().endsWith(".doc")){
				 File fTitle = new File(docTitlePath);
				 FileInputStream fs = new FileInputStream(fTitle);
				 s.setQuestionTitle(heSession.getLobHelper().createBlob(fs, fTitle.length()));//fs.getFileStream(), fs.getFileSize()));
	   
			 }
			 if(docAnswerPath!=null && docAnswerPath.trim().endsWith(".doc")){
				 File fAnswer = new File(docAnswerPath);
				 FileInputStream fs = new FileInputStream(fAnswer);
				 s.setQuestionAnswer(heSession.getLobHelper().createBlob(fs, fAnswer.length()));//fs.getFileStream(), fs.getFileSize()));
			 }
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		 heSession.save(s);
		 id = String.valueOf(s.getCode());
		 heSession.getTransaction().commit();
		 
		 //print
		 System.out.println("insert to DB summary=" + strSummary + " TitleWord=" + docTitlePath + " AnswerWord=" +  docAnswerPath + " TitleHtml=" + htmTitleURL + " AnswerHtml=" + htmAnswerURL );
	  
		 //delete doc
		 FileManager fm = new FileManager();
		 fm.deleteFile(docTitlePath);
		 fm.deleteFile(docAnswerPath);
		 return id;
	 }
	 
	 public static String createQuestionToDB(String strSummary,InputStream titleStream, InputStream answerStream, String rootFolder){
		
		 System.out.println("createQuestionToDB.java");
		 if(rootFolder==null){
			 System.out.println("rootFolder is null!");
			 return "";
		 }
		 File f = new File(rootFolder);
		 if(!f.isDirectory()){
			 System.out.println(rootFolder + " is not folder!");
			 return "";
		 }
		 if((!rootFolder.endsWith(File.separator)) && (!rootFolder.endsWith("/"))) {
			 rootFolder += File.separator;
		 }
		 
		 if(titleStream==null && answerStream==null){
			 System.out.println("InputStream is null!");
			 return "";
		 }
		 System.out.println("createQuestionToDB: root folder: " + rootFolder);
		//htm folder
		 String htmParentFolder = rootFolder  + "htm";
		 String docParentPath	= rootFolder  + "doc";
		 File htmFolder = new File(htmParentFolder);
		 File docFolder = new File(docParentPath);
		 if(!htmFolder.exists()){
			 htmFolder.mkdir();
		 }
		 if(!docFolder.exists()){
			 docFolder.mkdir();
		 }
		 
		 FileManager fm = new FileManager();
		 String randomTitleFileName = fm.getRandomFileName();
		 String randomAnswerFileName = fm.getRandomFileName();
		 //rename doc file
		 String docTitleDestName = "";
		 String docAnswerDestName = "";
		 boolean bDestTitle = true, bDestAnswer = true;
		 if(titleStream!=null ){
			 docTitleDestName = docParentPath + File.separator;
			 docTitleDestName += randomTitleFileName + ".doc";
			 //File docTitleFile = new File(docTitlePath);
			 //bDestTitle = docTitleFile.renameTo( new File(docTitleDestName));
			 byte fileBytes[] = new byte[1024];
			 try{
				 int bytesWritten = 0;
				 int byteCount = 0;

				 FileOutputStream outputStream = new FileOutputStream(docTitleDestName);
				 
				 
				 while((byteCount = titleStream.read(fileBytes))!= -1)
				 {
					 //System.out.println("read file: " + byteCount);
					 outputStream.write(fileBytes, 0, byteCount);
					 bytesWritten += byteCount;
					// System.out.println("Written bytes: " + bytesWritten);
				 }
				 System.out.println("Written bytes: " + bytesWritten);
				 outputStream.flush();
				 titleStream.close();
				 outputStream.close();
			 }catch(Exception e){
				 e.printStackTrace();
			 }
		 }
		 if(answerStream!=null){
			 docAnswerDestName = docParentPath + File.separator;
			 docAnswerDestName += randomAnswerFileName + ".doc";
			 //File docAnswerFile = new File(docAnswerPath);
			 //bDestAnswer = docAnswerFile.renameTo( new File(docAnswerDestName));
			 byte fileBytes[] = new byte[1024];
			 try{
				 int bytesWritten = 0;
				 int byteCount = 0;

				 FileOutputStream outputStream = new FileOutputStream(docAnswerDestName);
				 
				 
				 while((byteCount = answerStream.read(fileBytes))!= -1)
				 {
					 outputStream.write(fileBytes, 0, byteCount);
					 bytesWritten += byteCount;
				 }
				 outputStream.flush();
				 titleStream.close();
				 outputStream.close();
			 }catch(Exception e){
				 e.printStackTrace();
			 }
		 }
		 System.out.println("rename: " + docTitleDestName);
		 System.out.println("rename: " + docAnswerDestName);
		 
		 if(!bDestTitle && !bDestAnswer) return "";
		 //doc to htm
		 String htmTitleURL = "";//DB url
		 String htmAnswerURL = "";//DB url
		 String htmRandomFolder = fm.getRandomFilePath(htmParentFolder);
	  
		 //htmSaveFolder: htm full path (folder)
		 String htmSaveFolder = htmParentFolder;
		 if(!htmSaveFolder.endsWith(File.separator)) htmSaveFolder += File.separator;
		 htmSaveFolder += htmRandomFolder;
		 ArrayList<String> wordlist = new ArrayList<String>();
		 if(titleStream!=null){
			 wordlist.add(docTitleDestName);
			 htmTitleURL += htmRandomFolder;
			 if(!htmTitleURL.endsWith(File.separator)) htmTitleURL += File.separator;
			 htmTitleURL += randomTitleFileName + ".htm";
		 }
		 if(answerStream!=null){
			 wordlist.add(docAnswerDestName);
			 htmAnswerURL += htmRandomFolder;
			 if(!htmAnswerURL.endsWith(File.separator)) htmAnswerURL += File.separator;
			 htmAnswerURL += randomAnswerFileName + ".htm";
		 }
		 WordToHtml wordtohtml = new WordToHtml();
		 wordtohtml.changeFiles(wordlist, htmSaveFolder);
	  
		 //Get summary
		 if(strSummary==null || strSummary.trim().length()==0){
			 String htmTitleFullPath = htmParentFolder + File.separator + htmTitleURL;
			 if((new File(docTitleDestName)).exists()){
				 HtmInfo hi = new HtmInfo(htmTitleFullPath);
				 System.out.println("get summary from " + htmTitleFullPath);
				 strSummary = hi.getText(50);
			 }
		 }
		 System.out.println("summary: " + strSummary);
		 
		 //Update DB
		 System.out.println("htm: " + htmTitleURL);
		 System.out.println("htm: " + htmAnswerURL);
		 return updateDB(strSummary, docTitleDestName, docAnswerDestName,  htmTitleURL, htmAnswerURL);
	 	}
}

//Sample________________________________________________________________
/*ServiceWordToDB swd = new ServiceWordToDB();
String ret = swd.createQuestionToDB(null, 
		InputStream titleInputStream,
		InputStream answerInputStream,
		request.getSession().getServletContext().getRealPath("/"));
System.out.println("return id = " + ret );*/
//___________________________________________________________________
