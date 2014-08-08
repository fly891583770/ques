<%@ page language="java"
		import="java.util.*,com.zhuozhengsoft.pageoffice.*,com.zhuozhengsoft.pageoffice.wordwriter.*
	,com.questionbase.hbm.*,org.hibernate.*,com.questionbase.hbm.factory.*,java.sql.*;"
	pageEncoding="gb2312"%>
<%@ taglib uri="http://java.pageoffice.cn" prefix="po"%>
<%
	//******************************׿��PageOffice�����ʹ��*******************************

	String id = request.getParameter("ID");
	String action = request.getParameter("Action");
	System.out.println("PageOfficeReadOnlyWord.jsp ID=" + id + " Action=" + action);
	PageOfficeCtrl poCtrl1 = new PageOfficeCtrl(request);
	poCtrl1.setServerPage(request.getContextPath() + "/poserver.do"); //���б���
	
	poCtrl1.setAllowCopy(false);//��ֹ����
	poCtrl1.setMenubar(false);//���ز˵���
	poCtrl1.setOfficeToolbars(false);//����Office������
	poCtrl1.setCustomToolbar(false);//�����Զ��幤����
	poCtrl1.setJsFunction_AfterDocumentOpened("AfterDocumentOpened");

	//����ҳ�����ʾ����
	poCtrl1.setCaption("ֻ��");
	
	
	//���ñ���ҳ��
	//poCtrl1.setSaveFilePage("SaveNewFile?ID=" + id + "&Action="
	//		+ action);

	String strSummary = "";
		if((id.isEmpty())){
		//�½�Word�ļ���webCreateNew�����е����������ֱ�ָ���������ˡ��͡��½�Word�ĵ��İ汾�š�

		//poCtrl1.webCreateNew("������", DocumentVersion.Word2003);
		//poCtrl1.webOpen("Openstream.jsp?id=1", OpenModeType.docNormalEdit, "����");
	}else{
		Session heSession = QuestionBaseFactory.GetInstance()
				.getSessionFactory().openSession();
		Stream s = (Stream)heSession.load(Stream.class, Integer.parseInt(id.trim()));
		strSummary = s.getSummary();
		Blob wordBlob = null;
		if ("title".equals(action)) {
			wordBlob = s.getQuestionTitle();
		}
		if ("answer".equals(action)) {
			wordBlob = s.getQuestionAnswer();
		}
		if(wordBlob==null){
			//poCtrl1.webCreateNew("������", DocumentVersion.Word2003);
		}else{
			//��Word�ļ�
			poCtrl1.webOpen("Openstream?id=" + id + "&Action=" + action, OpenModeType.docReadOnly, "����");
		}
	}
	
	poCtrl1.setTagId("PageOfficeCtrl1"); //���б���
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title></title>
<link href="../images/csstg.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
	var p = (window.parent);
	var action = request("Action");
	var id = request("ID");
	var nameIFrame;
	var nameHidden;
	if (action == "title") {
		nameIFrame = p.document.getElementById("nameIframe");
		nameHidden = p.document.getElementById("name");
	} else if (action == "answer") {
				
		nameIFrame = p.document.getElementById("answerIframe");
		nameHidden = p.document.getElementById("answer");
	}
	
	function load() {
		
		if (action == "answer") {
			
			//Hide summary 
			document.getElementById("txtSummary").style.display= "none";
			document.getElementById("lableSummary").style.display= "none";
		}
	}

	function request(paras) {
		var url = location.href;
		var paraString = url.substring(url.indexOf("?") + 1, url.length).split(
				"&");
		var paraObj = {}
		for (var i = 0; i < paraString.length; i++) {
			var j = paraString[i]
			paraObj[j.substring(0, j.indexOf("=")).toLowerCase()] = j
					.substring(j.indexOf("=") + 1, j.length);
		}
		var returnValue = paraObj[paras.toLowerCase()];
		if (typeof (returnValue) == "undefined") {
			return "";
		} else {
			return returnValue;
		}
	}
	
	function AfterDocumentOpened() {
        document.getElementById("PageOfficeCtrl1").SetEnableFileCommand(3, false); // ��ֹ����
        document.getElementById("PageOfficeCtrl1").SetEnableFileCommand(4, false); // ��ֹ���
        document.getElementById("PageOfficeCtrl1").SetEnableFileCommand(5, false); //��ֹ��ӡ
        document.getElementById("PageOfficeCtrl1").SetEnableFileCommand(6, false); // ��ֹҳ������
        
        
    }
</script>

</head>
<body onload="load();" >


	<div id="content">

		<div>
			<!--  <input type="button" onclick="Save()" value="�ύ" />&nbsp;-->
		</div>
		
		 
		 <span id="lableSummary" style="color: Red; font-size: 14px;">������ժҪ��</span>
				<input id="txtSummary" name="txtSummary" type="text"  value="<%=strSummary%>" readonly/>
		<po:PageOfficeCtrl id="PageOfficeCtrl1" />
	</div>



</body>
</html>
