<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>
<head>
<title>试卷预览</title>
<link rel="stylesheet" type="text/css"
	href="../../QuestionBase/css/site.css" />

<style type="text/css">
div.wrap {
	border: 1px solid #BBBBBB;
	padding: 1em 1em 1em 1em;
}

.page-list {
	list-style: none;
	margin: 0;
	padding: 0;
	display: block;
}

.clear-element {
	clear: both;
}

.page-item1>div,.page-item2>div,.page-item3>div,.page-item4>div {
	background: #f8f8f8;
	margin: 0.25em 0 0 0;
}

.left {
	text-align: left;
}

.right {
	text-align: right;
}

.sort-handle {
	cursor: move;
}

.helper {
	border: 2px dashed #777777;
}

.current-nesting {
	background-color: yellow;
}

.bold {
	color: red;
	font-weight: bold;
}
</style>

</head>
<body>
	<jsp:include page="/WEB-INF/jsp/common/header.jsp" />
	
	<div id="main">
		<jsp:include page="/WEB-INF/jsp/common/banner.jsp" />
		<h1 align="center">${name}</h1>
		<div style="MARGIN-RIGHT: auto; MARGIN-LEFT: auto; width: 1000px">
			<c:forEach var="u" items="${qlist}" varStatus="s">
				<b>第${s.index+1 }题：</b>
				<br />

				<div class="page-list">
					<c:forEach var="q" items="${u}" varStatus="qstatus">
						<div class="clear-element page-item4 sort-handle left">
							<table style="width: 800px; border-style: solid; margin: 10px"
								class="ti">
								<tr>
									<td width=700px>${qstatus.index+1}&nbsp;.&nbsp;${q.question.name}</td>
									<td>(本题${q.score}分)</td>
								</tr>
							</table>
						</div>
					</c:forEach>
				</div>

				<br />
				<br />
			</c:forEach>
		</div>
	</div>
	<jsp:include page="/WEB-INF/jsp/common/footer.jsp" />
</body>
</html>