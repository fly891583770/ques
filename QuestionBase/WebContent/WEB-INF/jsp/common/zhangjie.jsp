<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>登陆试题</title>
<link rel="stylesheet" type="text/css"
	href="../../QuestionBase/css/site.css" />
<link rel="stylesheet" type="text/css"
	href="../../QuestionBase/css/screen.css" />
<link rel="stylesheet" type="text/css"
	href="../../QuestionBase/css/jquery.treeview.css" />


<script type="text/javascript"
	src="../../QuestionBase/js/jquery-1.4.1.js"></script>
<script type="text/javascript"
	src="../../QuestionBase/js/jquery.treeview.js"></script>
<script type="text/javascript"
	src="../../QuestionBase/js/jquery.cookie.js"></script>
<script type="text/javascript"
	src="../../QuestionBase/js/jquery.treeview.async.js"></script>
<script type="text/javascript"
	src="../../QuestionBase/js/jquery.treeview.edit.js"></script>
</head>
<body>
	<script type="text/javascript">
		$(function() {
			var courceCode = request("courceCode");
			var teachingMaterialVersion = request("teachingMaterialVersion");
			var year = request("year");
			var num =  request("num");
			var schoolCode = request("schoolCode");
			var url = "/QuestionBase/common/zhangjie?courceCode=" + courceCode
					+ "&teachingMaterialVersion=" + teachingMaterialVersion
					+ "&year=" + year + "&schoolCode=" + schoolCode;
			$("#categorys").treeview(
					{
						animated : "normal",
						url : url,
						callback : function(id, text) {
							var pEle = window.parent.document
									.getElementById("zhangjie" + num + "Area");
							var hEle = window.parent.document
									.getElementById("zhangjie" + num);
							hEle.value = hEle.value + id + ",";
							pEle.value = pEle.value + text + "\r\n";
						}
					});
		});

		function request(paras) {
			var url = location.href;
			var paraString = url.substring(url.indexOf("?") + 1, url.length)
					.split("&");
			var paraObj = {};
			for ( var i = 0; i < paraString.length; i++) {
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
	</script>
	<ul id="categorys" class="filetree treeview">
	</ul>
</body>
</html>