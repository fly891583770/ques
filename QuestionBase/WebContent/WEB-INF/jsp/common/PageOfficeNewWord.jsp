<%@ page language="java"
	import="java.util.*,com.zhuozhengsoft.pageoffice.*,com.zhuozhengsoft.pageoffice.wordwriter.*
	,com.questionbase.hbm.*,org.hibernate.*,com.questionbase.hbm.factory.*,java.sql.*, 
	java.io.*, com.questionbase.word.*;"
	pageEncoding="gb2312"%>
<%@ taglib uri="http://java.pageoffice.cn" prefix="po"%>
<%
	//******************************׿��PageOffice�����ʹ��*******************************

	//test_____________________________________________________________________
	/*File f1 = new File("c:/word/1.doc");
	if(!f1.isFile()) System.out.println("1.doc is not file!");
	FileInputStream in1 = new FileInputStream(new File("c:/word/1.doc"));
	FileInputStream in2 = new FileInputStream(new File("c:/word/2.doc"));
	//WordStreamToDB wsd = new WordStreamToDB();
	WordStreamToDB.createQuestionToDB(null, in1, in2, request.getSession().getServletContext().getRealPath("/"));*/
	
	//________________________________________________________________________
	String id = request.getParameter("ID");
	String action = request.getParameter("Action");
	PageOfficeCtrl poCtrl1 = new PageOfficeCtrl(request);
	poCtrl1.setServerPage(request.getContextPath() + "/poserver.do"); //���б���
	//���ز˵���
	//poCtrl1.setMenubar(false);
	//���ع�����
	poCtrl1.setCustomToolbar(false);
	
	poCtrl1.setJsFunction_AfterDocumentOpened("AfterDocumentOpened");
	poCtrl1.setJsFunction_AfterDocumentSaved("saveCheck");
	
	//���ñ���ҳ��
	poCtrl1.setSaveFilePage("SaveNewFile?ID=" + id + "&Action="
			+ action);

	String strSummary = "";
	
	if(id.isEmpty()){
		//�½�Word�ļ���webCreateNew�����е����������ֱ�ָ���������ˡ��͡��½�Word�ĵ��İ汾�š�
		poCtrl1.webCreateNew("������", DocumentVersion.Word2003);
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
			poCtrl1.webCreateNew("������", DocumentVersion.Word2003);
		}else{
			//��Word�ļ�
			poCtrl1.webOpen("Openstream?id=" + id + "&Action=" + action, OpenModeType.docNormalEdit, "����");
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
	function Save() {
		var customResult = document.getElementById("PageOfficeCtrl1").CustomSaveResult;
		
        var arr = customResult.split("_");

		var result = arr[0];//customResult.substring(0, 2);
		var index = arr[1];//customResult.substring(2, customResult.length);
		var randomFileName = arr[2];
		if (result == "ok") {
			nameIFrame.width = 660;
			nameIFrame.height = 200;
			nameIFrame.src = "../htm/" + randomFileName + ".htm";
			nameIFrame.contentWindow.location.reload(true);
			nameHidden.value = index;
			alert('����ɹ���');
		} else {
			alert('����ʧ�ܣ�');
		}
	}
	function load() {
		if (action == "answer") {
			document.getElementById("txtSummary").style.display= "none";
			document.getElementById("lableSummary").style.display= "none";
		}
	}
	
	function request(paras) {
		var url = location.href;
		var paraString = url.substring(url.indexOf("?") + 1, url.length).split(
				"&");
		var paraObj = {};
		for (var i = 0; i < paraString.length; i++) {
			var j = paraString[i];
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
	function sendParaToParent(){
		var customResult = document.getElementById("PageOfficeCtrl1").CustomSaveResult;
		
        var arr = customResult.split("_");

		var result = arr[0];//customResult.substring(0, 2);
		var index = arr[1];//customResult.substring(2, customResult.length);
		var randomFileName = arr[2];
		if (result == "ok") {
			nameIFrame.width = 660;
			nameIFrame.height = 200;
			nameIFrame.src = "../htm/" + randomFileName + ".htm";
			nameIFrame.contentWindow.location.reload(true);
			nameHidden.value = index;
			//alert('����ɹ���');
		} else {
			//alert('����ʧ�ܣ�');
		}
	}
	function saveCheck(){
	
		var customResult = document.getElementById("PageOfficeCtrl1").CustomSaveResult;
		
        var arr = customResult.split("_");

		var result = arr[0];//customResult.substring(0, 2);
		var index = arr[1];//customResult.substring(2, customResult.length);
		var randomFileName = arr[2];
		if (result == "ok") {
			document.getElementById("Hidden1").value= ' ' + index;
			//alert('����ɹ���');	
		} else {
			//alert('����ʧ�ܣ�');
		}
		
		Save();
	}
	
	function AfterDocumentOpened() {
		if (action == "title") {
			document.getElementById("txtSummary").focus();
		}
    }
</script>
</head>
<body onload="load();" >


	<div id="content">

		<div>
			<!--  <input type="button" onclick="Save()" value="�ύ" />&nbsp;-->
		</div>
		 <input id="Hidden1" name="neworsave" type="hidden" value="" />
		 
		 <span id="lableSummary" style="color: Red; font-size: 14px;">������ժҪ��</span>
				<input id="txtSummary" name="txtSummary" type="text" value="<%=strSummary%>"/>
		<po:PageOfficeCtrl id="PageOfficeCtrl1" />
	</div>



</body>
</html>
