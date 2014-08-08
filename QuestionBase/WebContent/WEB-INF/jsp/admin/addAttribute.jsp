<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="questionBase" uri="/WEB-INF/questionBase.tld "%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>追加各种属性</title>
<link rel="stylesheet" type="text/css"
	href="../../QuestionBase/css/site.css" />
</head>

<body>
	<jsp:include page="/WEB-INF/jsp/common/header.jsp" />
	<script type="text/javascript">
		$(function() {
			$("#teachingMaterialVersionButton")
					.click(
							function() {
								var value = $("#teachingMaterialVersion").val();
								if (value == "") {
									alert("请输入值");
									return;
								}

								var str = "<tr><td><input type=\"hidden\" name=\"teachingMaterialVersion\" value=\""+ value +"\"/>"
										+ value + "</td></tr>";
								$(this).parent("td").parent("tr")
										.before($(str));
								$("#teachingMaterialVersion").val("");
							});
			$("#questionTypeButton")
					.click(
							function() {
								var value = $("#questionType").val();
								if (value == "") {
									alert("请输入值");
									return;
								}

								var str = "<tr><td><input type=\"hidden\" name=\"questionType\" value=\""+ value +"\"/>"
										+ value + "</td></tr>";
								$(this).parent("td").parent("tr")
										.before($(str));
								$("#questionType").val("");
							});
			$("#typesButton")
					.click(
							function() {
								var value = $("#types").val();
								if (value == "") {
									alert("请输入值");
									return;
								}

								var str = "<tr><td><input type=\"hidden\" name=\"types\" value=\""+ value +"\"/>"
										+ value + "</td></tr>";
								$(this).parent("td").parent("tr")
										.before($(str));
								$("#types").val("");
							});

		});
	</script>

	<div id="main">
		<jsp:include page="/WEB-INF/jsp/common/banner.jsp" />
		<br />
		<form id="mainform" method=post action="../admin/saveAttribute"
			enctype="multipart/form-data">
			<table class="showtable" id="table">
				<tr width=100%>
					<th>教材版本</th>
					<th>题型</th>
					<th>类型</th>
				</tr>
				<tr width=100%>
					<td><questionBase:TeachingMaterialVersion /></td>
					<td><questionBase:QuestionType /></td>
					<td><questionBase:TypesInfo /></td>
				</tr>
			</table>
			<input type="submit" value="提交" id="batchAdd" />
		</form>

		<br />
		<jsp:include page="/WEB-INF/jsp/common/footer.jsp" />
	</div>
</body>
</html>