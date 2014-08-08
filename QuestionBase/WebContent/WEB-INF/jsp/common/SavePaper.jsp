<%@ page language="java"
	import="java.sql.*,java.io.*,javax.servlet.*,javax.servlet.http.*,
	java.text.SimpleDateFormat,java.util.Date,com.zhuozhengsoft.pageoffice.*,
	java.util.*,com.questionbase.word.WordToHtml,com.questionbase.hbm.factory.*,
	org.hibernate.*,com.questionbase.hbm.*, java.util.Random,com.questionbase.word.*;"
	pageEncoding="gb2312"%>
<%
	//Save as file
	//FileSaver fsFile = new FileSaver(request, response);
	String id = request.getParameter("ID");
	if(id==null) id = "";
	
	int newID = 0;
	//定义保存对象
	FileSaver fs = new FileSaver(request, response);
	
	//test
	if (id.trim().length()==0
			&& fs.getFormField("neworsave") != null
			&& fs.getFormField("neworsave").trim().length() > 0) {
		id = fs.getFormField("neworsave").trim();
	}
	
	Session heSession = QuestionBaseFactory.GetInstance().getSessionFactory().openSession();
	heSession.beginTransaction();
	if (id == null || id.equals("")) {
		WordPaper s = new WordPaper();
		s.setQuestionPaper(heSession.getLobHelper().createBlob(	fs.getFileStream(), fs.getFileSize()));	
		heSession.save(s);
		newID = s.getCode();
		System.out.println("SavePaper.jsp newID=" + newID );
	} else {

		Query query = null;

	    //update table
		query = heSession
				.createQuery("update WordPaper o set o.questionPaper=? where code=?");
		query.setParameter(
					0,
					heSession.getLobHelper().createBlob(fs.getFileStream(),
							fs.getFileSize()));
	
		query.setParameter(1, Integer.parseInt(id));
		
		query.executeUpdate();
		newID = Integer.parseInt(id);
		System.out.println("SavePaper.jsp exist ID=" + newID );
	}

	heSession.getTransaction().commit();

	
	
	//设置保存结果
	fs.setCustomSaveResult("ok_" + newID );
	//fs.showPage(300,300);
	fs.close();

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

</body>
</html>