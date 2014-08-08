<%@ page language="java"
	import="java.util.*,com.zhuozhengsoft.pageoffice.*,com.zhuozhengsoft.pageoffice.wordwriter.*,java.sql.*,java.io.*,com.questionbase.hbm.factory.*,
	org.hibernate.*,com.questionbase.hbm.*;"
	pageEncoding="gb2312"%>
<%@ taglib uri="http://java.pageoffice.cn" prefix="po"%>
<%
	//For debug
	Enumeration enu=request.getParameterNames();  
  	System.out.println("Compose.jsp    parameter");
	while(enu.hasMoreElements()){  
		String paraName=(String)enu.nextElement();  
		System.out.println(paraName+": "+request.getParameter(paraName));  
	} 
	
	String id = request.getParameter("ID");

	if (request.getParameter("id") != null
			&& request.getParameter("id").trim().length() > 0) {
		id = request.getParameter("id");
	}
	
	Session heSession = QuestionBaseFactory.GetInstance()
			.getSessionFactory().openSession();
	
	
	//Stream s = (Stream)heSession.load(Stream.class, Integer.parseInt(id.trim())); //heSession.load((Stream) Stream.class, Integer.parseInt(id.trim()) );
	WordPaper s = (WordPaper)heSession.load(WordPaper.class, Integer.parseInt(id.trim()));
	Blob wordContent = null;
	
	//Get content from DB
	wordContent = s.getQuestionPaper();
	
	if(wordContent!=null){
	System.out.println("wordContent!=null");
	byte[] imageBytes = wordContent.getBytes(1,(int)wordContent.length());
	
	int fileSize = imageBytes.length;
	InputStream in = wordContent.getBinaryStream();		
	
	
	response.reset();
	response.setContentType("application/msword"); // application/x-excel, application/ms-powerpoint, application/pdf
	response.setHeader("Content-Disposition",
					"attachment; filename=down.doc"); //fileNӦ���Ǳ�����(utf-8)
	response.setContentLength(fileSize);

	OutputStream outputStream = response.getOutputStream();
	outputStream.write(imageBytes);

	outputStream.flush();
	outputStream.close();
	outputStream = null;
	}else{
		System.out.println("wordContent==null");
		//byte[] imageBytes = " ".toCharArray();
		
		//int fileSize = imageBytes.length;
		//InputStream in = wordContent.getBinaryStream();	
		String strOutput = " ";
		byte[] contentBytes = strOutput.getBytes();
		response.reset();
		response.setContentType("application/msword"); // application/x-excel, application/ms-powerpoint, application/pdf
		response.setHeader("Content-Disposition",
						"attachment; filename=down.doc"); //fileNӦ���Ǳ�����(utf-8)
		response.setContentLength(contentBytes.length);

		OutputStream outputStream = response.getOutputStream();
		outputStream.write(contentBytes);

		outputStream.flush();
		outputStream.close();
		outputStream = null;
	}
	response.flushBuffer();
	out.clear();
	out = pageContext.pushBody();

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>

		<title>My JSP 'Openstream.jsp' starting page</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->


	</head>

	<body >
	openstream
		
	</body>
</html>
