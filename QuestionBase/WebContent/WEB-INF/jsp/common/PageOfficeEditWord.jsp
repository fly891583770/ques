<%@ page language="java"
 import="java.util.*,com.zhuozhengsoft.pageoffice.*,com.zhuozhengsoft.pageoffice.wordwriter.*,
 com.questionbase.hbm.*,org.hibernate.*,com.questionbase.hbm.factory.*,java.sql.Blob;"
 pageEncoding="gb2312"%>
<%@ taglib uri="http://java.pageoffice.cn" prefix="po"%>
<%
 //******************************卓正PageOffice组件的使用*******************************

 String id = request.getParameter("ID");
 String action = request.getParameter("Action");
 PageOfficeCtrl poCtrl1 = new PageOfficeCtrl(request);
 poCtrl1.setServerPage(request.getContextPath() + "/poserver.do"); //此行必须
 //隐藏菜单栏
 //poCtrl1.setMenubar(false);
 //隐藏工具栏
 poCtrl1.setCustomToolbar(false);
 
 poCtrl1.setJsFunction_AfterDocumentOpened("AfterDocumentOpened");
 poCtrl1.setJsFunction_AfterDocumentSaved("Save");
 
 //设置保存页面
 poCtrl1.setSaveFilePage("SaveNewFile?ID=" + id + "&Action="
   + action);

 boolean bHasFileInDB = false;
 Blob questionBlob = null;
 Stream st = null;
 Session heSession = QuestionBaseFactory.GetInstance().getSessionFactory()
   .openSession();
 Query query = heSession
   .createQuery("select o from Stream o where o.code=?");
 try {
  query.setParameter(0, Integer.parseInt(id));
  st = (Stream)query.uniqueResult();
  if ("title".equals(action)) {
   questionBlob = st.getQuestionTitle();
  }
  if ("answer".equals(action)) {
   questionBlob = st.getQuestionAnswer();
  }
  
  
  
 } catch (Exception e) {
 }
 
 if(questionBlob!=null){
  bHasFileInDB = true;
 }
 if(bHasFileInDB){
  //打开Word文件
  poCtrl1.webOpen("Openstream?ID=" + id + "&Action=" + action, OpenModeType.docNormalEdit, "张三");
 }else{
  poCtrl1.webCreateNew("张三", DocumentVersion.Word2003);
 }
 poCtrl1.setTagId("PageOfficeCtrl1"); //此行必须
 
 String strSummary ="";
 if(st!=null){
  strSummary  = st.getSummary();
 }
  
 System.out.println("pageofficeedit summary="+strSummary);
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
  //document.getElementById("PageOfficeCtrl1").WebSave();
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
   
   alert('保存成功！');
  } else {
   alert('保存失败！');
  }
 }
 function load() {
  
  if (action == "answer") {
   
   //Hide summary 
   document.getElementById("txtSummary").style.display= "none";
   document.getElementById("lableSummary").style.display= "none";
  }
 }
 function unload() {
  Save();
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
   
   //alert('保存成功！');
  } else {
   //alert('保存失败！');
  }
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
   <!--  <input type="button" onclick="Save()" value="提交" />&nbsp;-->
  </div>
   <input id="Hidden1" name="neworsave" type="hidden" value="" />
   
   <span id="lableSummary" style="font-size: 14px;" >摘要：</span>
    <input id="txtSummary" name="txtSummary" type="text"  value="<%=strSummary%>"/>
  <po:PageOfficeCtrl id="PageOfficeCtrl1" />
 </div>

 

</body>
</html>

