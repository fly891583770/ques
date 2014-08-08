<%@ page language="java"
	import="java.sql.*,java.io.*,javax.servlet.*,javax.servlet.http.*,
	java.text.SimpleDateFormat,java.util.Date,com.zhuozhengsoft.pageoffice.*,
	java.util.*,com.questionbase.word.WordToHtml,com.questionbase.hbm.factory.*,
	org.hibernate.*,com.questionbase.hbm.*, java.util.Random,com.questionbase.word.*;"
	pageEncoding="gb2312"%>
<%
	FileManager fm = new FileManager();
	fm.CreateDocHtmFolder(request.getSession().getServletContext().getRealPath("/"));
	//Save as file
	//FileSaver fsFile = new FileSaver(request, response);
	String id = request.getParameter("ID");
	
	String action = request.getParameter("Action");
	int newID = 0;
	//定义保存对象
	FileSaver fs = new FileSaver(request, response);
	
	//test
	if (fs.getFormField("neworsave") != null
			&& fs.getFormField("neworsave").trim().length() > 0) {
		id = fs.getFormField("neworsave").trim();
	}
	
	//Get summary
	String strSummary = "无摘要";
	
	System.out.println(strSummary);
	if(fs.getFormField("txtSummary")!=null
		&& fs.getFormField("txtSummary").trim().length() > 0) {//Get from summary input
		strSummary = fs.getFormField("txtSummary").trim();
		 
		}else{//Get from word
			int maxSummaryLen = 50;//?
			int summaryLen = maxSummaryLen;
			int documentLen = fs.getDocumentText().trim().length();
			 if(documentLen>0){
				    if(documentLen<maxSummaryLen){
				     	summaryLen = documentLen;
				    }
				    strSummary = fs.getDocumentText().trim().substring(0, summaryLen);
			  }
			
		}
	System.out.println(strSummary);
	
	//Get doc path and htm path
	//FileManager fm = new FileManager();
	String randomFileName = fm.getRandomFileName();
	String fileName = randomFileName + ".doc";//"submit" + randID + ".doc";
	String parentPath = request.getSession().getServletContext()
				.getRealPath("htm")
				+ File.separator;
	String htmSubFolder = fm.getRandomFilePath(parentPath);
	String savepath = parentPath + htmSubFolder;
	String htmURL = htmSubFolder + randomFileName + ".htm";
	
	Session heSession = QuestionBaseFactory.GetInstance()
			.getSessionFactory().openSession();
	heSession.beginTransaction();

	if (id == null || id.equals("")) {
		Stream s = new Stream();
		if ("title".equals(action)) {
			s.setSummary(strSummary);//ok?
			s.setQuestionTitleURL(htmURL);
			s.setQuestionTitle(heSession.getLobHelper().createBlob(
					fs.getFileStream(), fs.getFileSize()));
			
			
		}

		if ("answer".equals(action)) {
			s.setQuestionAnswerURL(htmURL);
			s.setQuestionAnswer(heSession.getLobHelper().createBlob(
					fs.getFileStream(), fs.getFileSize()));
		}
		heSession.save(s);
		newID = s.getCode();
	} else {

		Query query = null;

		if ("title".equals(action)) {
			
			//delete old htm
			Stream s = (Stream)heSession.load(Stream.class, Integer.parseInt(id.trim()));
		    String oldHtmFile = s.getQuestionTitleURL();
		    if(oldHtmFile!=null
		    		&&oldHtmFile.trim().length()>0){
		    	System.out.println("delete old file" + savepath + oldHtmFile);
		    	fm.deleteFile(parentPath + oldHtmFile);
		    }
		    
		    //update table
			query = heSession
					.createQuery("update Stream o set o.questionTitle=?,o.summary=?,o.questionTitleURL=? where code=?");
			query.setParameter(
					0,
					heSession.getLobHelper().createBlob(fs.getFileStream(),
							fs.getFileSize()));
			query.setParameter(1, strSummary);
			query.setParameter(2, htmURL);
			query.setParameter(3, Integer.parseInt(id));
		}

		if ("answer".equals(action)) {
			
			//delete old htm
			Stream s = (Stream)heSession.load(Stream.class, Integer.parseInt(id.trim()));
		    String oldHtmFile = s.getQuestionAnswerURL();
		    if(oldHtmFile!=null
		    		&&oldHtmFile.trim().length()>0){
		    	fm.deleteFile(parentPath + oldHtmFile);
		    }
		    
		    //update table
			query = heSession
					.createQuery("update Stream o set o.questionAnswer=?,o.questionAnswerURL=? where code=?");
			query.setParameter(
					0,
					heSession.getLobHelper().createBlob(fs.getFileStream(),
							fs.getFileSize()));
			query.setParameter(1, htmURL);
			query.setParameter(2, Integer.parseInt(id));
		}
		
		query.executeUpdate();
		newID = Integer.parseInt(id);
	}

	heSession.getTransaction().commit();

	
	//word to html
	fs.saveToFile(request.getSession().getServletContext()
				.getRealPath("doc")
				+ File.separator + fileName);
	
	
	ArrayList<String> wordlist = new ArrayList<String>();
	String docPath = request.getSession().getServletContext()
			.getRealPath("doc")
			+ File.separator;
	wordlist.add(docPath + fileName);
	
	System.out.println("htm path: " + savepath);
	WordToHtml wordtohtml = new WordToHtml();
	wordtohtml.changeFiles(wordlist, savepath);
	
	fm.deleteFile(docPath + fileName);
	
	//设置保存结果
	fs.setCustomSaveResult("ok_" + newID + "_" + htmURL.substring(0, htmURL.length()-4));
	//fs.showPage(300,300);
	fs.close();

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>My JSP 'SaveNewFile.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

</head>

<body>
	<br>
</body>
</html>
