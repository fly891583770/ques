<%@ page language="java"
	import="java.util.*,com.zhuozhengsoft.pageoffice.wordwriter.*,
	com.zhuozhengsoft.pageoffice.*,com.questionbase.question.*,com.questionbase.hbm.*,org.hibernate.*,com.questionbase.hbm.factory.*;"
	pageEncoding="gb2312"%>
<%@ taglib uri="http://java.pageoffice.cn" prefix="po"%>
<%
	Enumeration enu=request.getParameterNames();  
	System.out.println("TitleAndAnswer.jsp    parameter");
	while(enu.hasMoreElements()){  
		String paraName=(String)enu.nextElement();  
		System.out.println(paraName+": "+request.getParameter(paraName));  
	}  
	String id = request.getParameter("ID");
	String operateStr="";
	
	if(id!=null){
		Session heSession = QuestionBaseFactory.GetInstance()
				.getSessionFactory().openSession();	
		
		String preType = "";
		int pNum = 1;
		
        operateStr += "function Create(){\n";
        //Disable "save"
         operateStr += "document.getElementById('PageOfficeCtrl1').SetEnableFileCommand(3, false);\n";
        // document.getElementById('PageOfficeCtrl1').Document.Application 微软office VBA对象的根Application对象
        operateStr += "var obj = document.getElementById('PageOfficeCtrl1').Document.Application;\n";
        operateStr += "obj.Selection.EndKey(6);\n"; // 定位光标到文档末尾
        operateStr += "obj.Selection.MoveRight(1,1);\n";
		//加入题目类型
		operateStr += "obj.Selection.TypeParagraph();"; //用来换行
		
		//The title exist?
		Stream s = (Stream)heSession.load(Stream.class, Integer.parseInt(id.trim()));
		if(s.getQuestionTitle()!=null){
		// 插入指定的题到文档中
		   operateStr += "document.getElementById('PageOfficeCtrl1').InsertDocumentFromURL('Openstream?id=" + id + "&Action=title');\n";
		}else{
		    operateStr += "obj.Selection.TypeParagraph();"; //用来换行
		}
		System.out.println("Stream ID:" + id);
		// 下面两句代码用来移动光标位置
     	operateStr += "obj.Selection.EndKey(5,1);\n";
     	operateStr += "obj.Selection.MoveRight(1,1);\n";
		
     	         
	   //Display answer?
	    boolean bDisplayAnswer = false;
	    String strShowAnswer = request.getParameter("showAnswer");
		if(strShowAnswer!=null && strShowAnswer.compareToIgnoreCase("true")==0){
			bDisplayAnswer = true;
		}
		if(bDisplayAnswer){
			//Answer		
			operateStr += "obj.Selection.Font.Bold=true;\n";
			operateStr += "obj.Selection.TypeParagraph();"; //用来换行     
			operateStr += "obj.Selection.Range.Text = '答案';\n"; //题型 
		     // 下面两句代码用来移动光标位置
		     operateStr += "obj.Selection.EndKey(5,1);\n";
		     operateStr += "obj.Selection.MoveRight(1,2);\n";
		     operateStr += "obj.Selection.TypeParagraph();"; //用来换行      
			//The answer exist?
			if(s.getQuestionAnswer()!=null){
				// 插入指定的题到文档中
				operateStr += "document.getElementById('PageOfficeCtrl1').InsertDocumentFromURL('Openstream?id=" + id + "&Action=answer');\n";
			}else{
				operateStr += "obj.Selection.TypeParagraph();"; //用来换行
			}
		}
		operateStr += "\n}\n";
	}else{
		 
		 //Creat an empty document
		 operateStr += "function Create(){\n";
	     //Disable "save"
	     operateStr += "document.getElementById('PageOfficeCtrl1').SetEnableFileCommand(3, false);\n";
	     // document.getElementById('PageOfficeCtrl1').Document.Application 微软office VBA对象的根Application对象
	     operateStr += "var obj = document.getElementById('PageOfficeCtrl1').Document.Application;\n";
	     operateStr += "obj.Selection.EndKey(6);\n"; // 定位光标到文档末尾
		 operateStr += "\n}\n";
	}
      //******************************卓正PageOffice组件的使用*******************************
	PageOfficeCtrl poCtrl1 = new PageOfficeCtrl(request);
	poCtrl1.setServerPage(request.getContextPath()+"/poserver.do"); //此行必须

    //
    poCtrl1.setAllowCopy(false);//禁止拷贝
	poCtrl1.setMenubar(false);//隐藏菜单栏
	poCtrl1.setOfficeToolbars(false);//隐藏Office工具条
	poCtrl1.setCustomToolbar(false);//隐藏自定义工具栏
	
	
	
	poCtrl1.setJsFunction_AfterDocumentOpened("Create()");

	
	//打开Word文件
	//poCtrl1.webOpen("template/template1.doc", OpenModeType.docNormalEdit, "张三");
	//新建word
	poCtrl1.webCreateNew("张佚名", DocumentVersion.Word2003);
	poCtrl1.setTagId("PageOfficeCtrl1"); //此行必须	


%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>在Word文档中显示题干和答案</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

</head>
<script type="text/javascript">
         <%=operateStr %>
    </script>
<body>
	<div style="width: auto; height: 700px;">
		<po:PageOfficeCtrl id="PageOfficeCtrl1"></po:PageOfficeCtrl>
	</div>
</body>
</html>
