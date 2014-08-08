<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>abcdefg</title>
<link rel="stylesheet" type="text/css"
	href="../../QuestionBase/css/site.css" />
</head>
<body>
	<script type="text/javascript">
		var p = (window.parent);
		var shijuan = p.document.getElementById("shijuan");
		var wordPaperId = p.document.getElementById("wordPaperId");
		var code = request("code");
		if(code != null && code ){
			PostSubmit("Compose", null, null, code, wordPaperId.value);
		}else if (shijuan == null) {
			var questionstr = p.document.getElementById("questionstr").value;
			var fenstr = p.document.getElementById("fenstr").value;
			PostSubmit("Compose", questionstr, fenstr, null,wordPaperId.value);
		} else {
			PostSubmit("Compose", null, null, shijuan.value,wordPaperId.value);
		}

		function PostSubmit(url, timsg, fenmsg, shijuan,wordPaperId) {
			var postUrl = url;//提交地址
			var ExportForm = document.createElement("FORM");
			document.body.appendChild(ExportForm);
			ExportForm.method = "POST";
			if (timsg != null) {
				var newElement = document.createElement("input");
				newElement.setAttribute("name", "ti");
				newElement.setAttribute("type", "hidden");
				newElement.value = timsg;
				ExportForm.appendChild(newElement);
			}

			if (fenmsg != null) {
				var newElement2 = document.createElement("input");
				newElement2.setAttribute("name", "fen");
				newElement2.setAttribute("type", "hidden");
				newElement2.value = fenmsg;
				ExportForm.appendChild(newElement2);
			}

			if (shijuan != null) {
				var newElement4 = document.createElement("input");
				newElement4.setAttribute("name", "shijuan");
				newElement4.setAttribute("type", "hidden");
				ExportForm.appendChild(newElement4);
				newElement4.value = shijuan;
			}

			var newElement3 = document.createElement("input");
			newElement3.setAttribute("name", "showAnswer");
			newElement3.setAttribute("type", "hidden");
			newElement3.value = "true";
			ExportForm.appendChild(newElement3);
			
			var newElement5 = document.createElement("input");
			newElement5.setAttribute("name", "wordPaperId");
			newElement5.setAttribute("type", "hidden");
			newElement5.value = wordPaperId;
			ExportForm.appendChild(newElement5);

			ExportForm.action = postUrl;
			ExportForm.submit();
		};

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
</body>
</html>