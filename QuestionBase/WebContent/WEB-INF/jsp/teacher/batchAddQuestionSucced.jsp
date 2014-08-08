<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="questionBase" uri="/WEB-INF/questionBase.tld "%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>登陆试题成功</title>
<link rel="stylesheet" type="text/css"
	href="../../QuestionBase/css/site.css" />
</head>

<body>
	<jsp:include page="/WEB-INF/jsp/common/header.jsp" />
	<script type="text/javascript">
		
	</script>

	<div id="main">
		<jsp:include page="/WEB-INF/jsp/common/banner.jsp" />
		成功导入试题。 <a href="/account/login">返回</a>
		<jsp:include page="/WEB-INF/jsp/common/footer.jsp" />
	</div>
</body>
</html>