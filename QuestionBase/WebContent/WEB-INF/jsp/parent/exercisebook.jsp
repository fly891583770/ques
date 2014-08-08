<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>查看习题册</title>
	<link rel="stylesheet" type="text/css" href="../../QuestionBase/css/site.css" />
	<script type="text/javascript" src="../../QuestionBase/js/jquery-1.4.1.js"></script>

<script type="text/javascript">
	function openquestionlist(id)
	{
		window.parent.location = "exercisebookquestionlist?id="+id;
	}
</script>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/common/header.jsp" />
	<div id="main">
	
	<jsp:include page="/WEB-INF/jsp/common/banner.jsp" />
<%--需要修改的部分 --%>	


<p>您孩子保存的习题册：</p>
<table class="showtable" width="60%" border="0">
	<th width="10%">编号</th><th width="30%">习题册名称</th><th  width="30%">习题册状态</th>
	<c:forEach var="u" items="${exercisebooks}" varStatus="index">
		<tr>
			<td>${index.index+1 }</td>
			<td>
				<a href="javascript:openquestionlist(${u.code})">${u.name}</a>
			</td>
			<td>-</td>
		</tr>
	</c:forEach>
</table>

<%--需要修改的部分 --%>
	</div>
	<jsp:include page="/WEB-INF/jsp/common/footer.jsp" />
</body>
</html>