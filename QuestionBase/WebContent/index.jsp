<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="css/site.css" />
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/common/header.jsp" />
	<script>
		$(function() {
			$("#xs").click(function() {
				$("#username").val("fl_19");
				$("#password").val("0000");
				$("form").submit();
			});
			$("#jz").click(function() {
				$("#username").val("fjt_19");
				$("#password").val("0000");
				$("form").submit();
			});
			$("#ls").click(function() {
				$("#username").val("myp_19");
				$("#password").val("0000");
				$("form").submit();
			});
			$("#zr").click(function() {
				$("#username").val("zcf_19");
				$("#password").val("0000");
				$("form").submit();
			});
			$("#gly").click(function() {
				$("#username").val("admin");
				$("form").submit();
			});

			if (request("TimeOut") == "True") {
				$("#TimeOut").html("过长时间没有操作，或者没有登录，请重新登录");
			}
		});

		function request(paras) {
			var url = location.href;
			var paraString = url.substring(url.indexOf("?") + 1, url.length)
					.split("&");
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
	</script>
	<div id="main">

		<div class="title_head" id='TimeOut'></div>
		<%--需要修改的部分 --%>
		<center>
			<div class="title_head">请输入用户名和密码:</div>
			<br />

			<form method="post" action="./account/login">
				<table class="table_abc" id="Table1" cellspacing="0" cellpadding="0"
					border="0" width="100%">
					<tr class="table_form_tr">
						<td class="table_td_lable" width="50">
							<div id="loginLabel">
								<span id="Label6"><b>用户名：</b></span>
							</div>
						</td>
						<td>
							<div id="userNameInput">
								<input name="username" id="username" type="text"
									style="width: 200px;" class="required" />
							</div>
						</td>
					</tr>
					<tr class="table_form_tr">
						<td class="table_td_lable">
							<div id="passwordLabel">
								<span id="Label7"><b>密码：</b></span>
							</div>
						</td>
						<td>
							<div>
								<input name="password" id="password" type="password"
									style="width: 200px;" class="required" />
							</div>
						</td>
					</tr>
				</table>
				<br> <input type="submit" value="登陆" />
			</form>
		</center>
		<%--需要修改的部分 --%>
		<!-- 
		<input type="button" id="xs" value="学生" /> 
		<input type="button" id="jz" value="家长" /> 
		<input type="button" id="ls" value="老师" /> 
		<input type="button" id="zr" value="主任" />
		<input type="button" id="gly" value="管理员" />
		 -->
	</div>
	<jsp:include page="/WEB-INF/jsp/common/footer.jsp" />
</body>
</html>