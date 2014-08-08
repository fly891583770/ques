<%@ page language="java" contentType="text/html; charset=UTF-8"	isELIgnored="true"  pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>题库系统</title>
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
    <td>所属单位：学生</td>
  </tr>
  <tr>
    <td><div align="center">欢迎访问题库系统</div></td>
  </tr>
</table>
<hr />

<table width="100%" border="0">
  <tr>
    <td>您可以进行一下操作：</td>
  </tr>
  <tr>
    <td><a href="searchquestion">检索试题</a></td>
  </tr>
  <tr>
    <td><a href="exercisebook">查看习题集</a></td>
  </tr>
</table>

<%--需要修改的部分 --%>
	
	
	</div>
	<jsp:include page="/WEB-INF/jsp/common/footer.jsp" />
</body>
</html>