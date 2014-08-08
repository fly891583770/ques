<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>Insert title here</title>
	<link rel="stylesheet" type="text/css" href="../../QuestionBase/css/site.css" />
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/common/header.jsp" />
	<div id="main">
	
	
<%--需要修改的部分 --%>	

<table width="100%" border="0">
  <tr>
    <td>用户姓名：${user.name}</td>
  </tr>
  <tr>
    <td>所属单位：${type} </td>
  </tr>
  <tr>
    <td><div align="center">欢迎访问题库系统</div></td>
  </tr>
</table>
<hr />

<%-- 学生 --%>
<c:if test="${type=='学生'}">
<table width="100%" border="0">
  <tr>
    <td>您可以进行一下操作：</td>
  </tr>
  <tr>
    <td><input type="button" class="toplink" onclick="location.href='../student/searchquestion'" value="检索试题" /></td>
  </tr>
  <tr>
    <td><input type="button" class="toplink" onclick="location.href='../student/searchpaper'" value="检索试卷" /></td>
  </tr>
  <tr>
    <td><input type="button" class="toplink" onclick="location.href='../student/exercisebook'" value="查看习题集" /></td>
  </tr>
</table>
</c:if>
<%-- 学生 --%>



<%-- 家长--%>
<c:if test="${type=='家长'}">
<table width="100%" border="0">
  <tr>
    <td>您可以进行一下操作：</td>
  </tr>
  <tr>
    <td><input type="button" class="toplink" onclick="location.href='../parent/searchquestion'" value="检索试题" /></td>
  </tr>
  <tr>
    <td><input type="button" class="toplink" onclick="location.href='../parent/exercisebook'" value="查看孩子习题集" /></td>
  </tr>
  <tr>
    <td><input type="button" class="toplink" onclick="location.href='../parent/getapplyquestion'" value="审批习题答案" /></td>
  </tr>
</table>
</c:if>
<%-- 家长 --%>

<%-- 老师--%>
<c:if test="${type=='老师'}">
<table width="100%" border="0">
  <tr>
    <td>您可以进行一下操作：</td>
  </tr>
  <tr>
    <td><input type="button" class="toplink" onclick="location.href='../teacher/submitquestionlist'" value="登陆试题" /></td>
  </tr>
  <tr>
    <td><input type="button" class="toplink" onclick="location.href='../teacher/searchquestion'" value="检索试题" /></td>
  </tr>
  <tr>
    <td><input type="button" class="toplink" onclick="location.href='../teacher/searchpaper'" value="检索试卷" /></td>
  </tr>
    <tr>
    <td><input type="button" class="toplink" onclick="location.href='../teacher/getapplyquestion'" value="审批显示答案" /></td>
  </tr>
  <tr>
    <td><input type="button" class="toplink" onclick="location.href='../teacher/creatpaper'" value="组卷" /></td>
  </tr>
  <tr>
    <td><input type="button" class="toplink" onclick="location.href='../teacher/submitpaperlist'" value="试卷提交审核" /></td>
  </tr>
  <tr>
    <td><input type="button" class="toplink" onclick="location.href='../teacher/batchAddQuestion'" value="批提交试题" /></td>
  </tr>
</table>
</c:if>
<%-- 老师--%>

<%-- 老师--%>
<c:if test="${type=='主任'}">
<table width="100%" border="0">
  <tr>
    <td>您可以进行一下操作：</td>
  </tr>
  <tr>
    <td><input type="button" class="toplink" onclick="location.href='../teacher/submitquestionlist'" value="登陆试题" /></td>
  </tr>
  <tr>
    <td><input type="button" class="toplink" onclick="location.href='../teacher/searchquestion'" value="检索试题" /></td>
  </tr>
  <tr>
    <td><input type="button" class="toplink" onclick="location.href='../teacher/creatpaper'" value="组卷" /></td>
  </tr>
  <tr>
    <td><input type="button" class="toplink" onclick="location.href='../teacher/searchpaper'" value="检索试卷" /></td>
  </tr>
    <tr>
    <td><input type="button" class="toplink" onclick="location.href='../teacher/getapplyquestion'" value="审批显示答案" /></td>
  </tr>
  <tr>
    <td><input type="button" class="toplink" onclick="location.href='../teacher/submitpaperlist'" value="试卷提交主任审批" /></td>
  </tr>
   <tr>
    <td><input type="button" class="toplink" onclick="location.href='../director/getapllyquestions'" value="审批试题" /></td>
  </tr>
  <tr>
    <td><input type="button" class="toplink" onclick="location.href='../director/getapllypaper'" value="审批试卷" /></td>
  </tr>
  <tr>
    <td><input type="button" class="toplink" onclick="location.href='../teacher/batchAddQuestion'" value="批提交试题" /></td>
  </tr>
</table>
</c:if>
<%-- 老师--%>

<%-- 管理员 --%>
<c:if test="${type=='管理员'}">
<table width="100%" border="0">
  <tr>
    <td>您可以进行一下操作：</td>
  </tr>
  <tr>
    <td><input type="button" class="toplink" onclick="location.href='../admin/addattribute'" value="追加各种属性" /></td>
  </tr>
  <tr>
    <td><input type="button" class="toplink" onclick="location.href='../admin/addAttributeRelation'" value="追加各种属性关联" /></td>
  </tr>
  <tr>
    <td><input type="button" class="toplink" onclick="location.href='../admin/editzjxx'" value="编辑章节" /></td>
  </tr>
  <tr>
    <td><input type="button" class="toplink" onclick="location.href='../admin/editzjxx'" value="编辑知识点" /></td>
  </tr>
</table>
</c:if>
<%-- 学生 --%>

<%--需要修改的部分 --%>
	
	
	</div>
	<jsp:include page="/WEB-INF/jsp/common/footer.jsp" />
</body>
</html>