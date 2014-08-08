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
        // document.getElementById('PageOfficeCtrl1').Document.Application ΢��office VBA����ĸ�Application����
        operateStr += "var obj = document.getElementById('PageOfficeCtrl1').Document.Application;\n";
        operateStr += "obj.Selection.EndKey(6);\n"; // ��λ��굽�ĵ�ĩβ
        operateStr += "obj.Selection.MoveRight(1,1);\n";
		//������Ŀ����
		operateStr += "obj.Selection.TypeParagraph();"; //��������
		
		//The title exist?
		Stream s = (Stream)heSession.load(Stream.class, Integer.parseInt(id.trim()));
		if(s.getQuestionTitle()!=null){
		// ����ָ�����⵽�ĵ���
		   operateStr += "document.getElementById('PageOfficeCtrl1').InsertDocumentFromURL('Openstream?id=" + id + "&Action=title');\n";
		}else{
		    operateStr += "obj.Selection.TypeParagraph();"; //��������
		}
		System.out.println("Stream ID:" + id);
		// ����������������ƶ����λ��
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
			operateStr += "obj.Selection.TypeParagraph();"; //��������     
			operateStr += "obj.Selection.Range.Text = '��';\n"; //���� 
		     // ����������������ƶ����λ��
		     operateStr += "obj.Selection.EndKey(5,1);\n";
		     operateStr += "obj.Selection.MoveRight(1,2);\n";
		     operateStr += "obj.Selection.TypeParagraph();"; //��������      
			//The answer exist?
			if(s.getQuestionAnswer()!=null){
				// ����ָ�����⵽�ĵ���
				operateStr += "document.getElementById('PageOfficeCtrl1').InsertDocumentFromURL('Openstream?id=" + id + "&Action=answer');\n";
			}else{
				operateStr += "obj.Selection.TypeParagraph();"; //��������
			}
		}
		operateStr += "\n}\n";
	}else{
		 
		 //Creat an empty document
		 operateStr += "function Create(){\n";
	     //Disable "save"
	     operateStr += "document.getElementById('PageOfficeCtrl1').SetEnableFileCommand(3, false);\n";
	     // document.getElementById('PageOfficeCtrl1').Document.Application ΢��office VBA����ĸ�Application����
	     operateStr += "var obj = document.getElementById('PageOfficeCtrl1').Document.Application;\n";
	     operateStr += "obj.Selection.EndKey(6);\n"; // ��λ��굽�ĵ�ĩβ
		 operateStr += "\n}\n";
	}
      //******************************׿��PageOffice�����ʹ��*******************************
	PageOfficeCtrl poCtrl1 = new PageOfficeCtrl(request);
	poCtrl1.setServerPage(request.getContextPath()+"/poserver.do"); //���б���

    //
    poCtrl1.setAllowCopy(false);//��ֹ����
	poCtrl1.setMenubar(false);//���ز˵���
	poCtrl1.setOfficeToolbars(false);//����Office������
	poCtrl1.setCustomToolbar(false);//�����Զ��幤����
	
	
	
	poCtrl1.setJsFunction_AfterDocumentOpened("Create()");

	
	//��Word�ļ�
	//poCtrl1.webOpen("template/template1.doc", OpenModeType.docNormalEdit, "����");
	//�½�word
	poCtrl1.webCreateNew("������", DocumentVersion.Word2003);
	poCtrl1.setTagId("PageOfficeCtrl1"); //���б���	


%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>��Word�ĵ�����ʾ��ɺʹ�</title>

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
