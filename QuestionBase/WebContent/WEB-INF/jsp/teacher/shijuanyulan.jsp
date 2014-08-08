<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>
<head>
<title>Insert title here</title>
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
	<script type="text/javascript"
		src="../../QuestionBase/js/jquery-1.1.4.js"></script>
	<script type="text/javascript"
		src="../../QuestionBase/js/interface-1.2.js"></script>
	<script type="text/javascript"
		src="../../QuestionBase/js/inestedsortable.js"></script>
	<script type="text/javascript" src="../../QuestionBase/js/thickbox.js"></script>
	<link rel="stylesheet" type="text/css"
		href="../../QuestionBase/css/thickbox.css" />
	<div id="header">
		<table style="width: 100%;">
			<tr style="width: 100%;">
				<td>
					<div id="title" style="width: 100%;">
						<style type="text/css">
.HeaderBK {
	height: 92px;
	width: 100%;
}
</style>

						<div id="Logo">
							<table style="width: 100%">
								<tr style="width: 100%">
									<td>write sth here</td>
								</tr>
							</table>
						</div>

					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div id="headbar">
						<center>
							<div id="Logo">
								<table style="width: 100%">
									<tr style="width: 100%">
										<td style="width: 100%"><img
											style="height: 50px; width: 100%;" class="HeaderBK"
											src="../../QuestionBase/img/bg_ci2.gif" /></td>
									</tr>
								</table>
							</div>
						</center>

						<span
							style="position: absolute; top: 65px; left: 950px; font-weight: bold; font-size: 24px; color: white;">
							题库系统 </span>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div id="menucontainer"></div>
				</td>
			</tr>
		</table>
	</div>
	<script type="text/javascript">
		$(function() {
			$('.page-list').Sortable({
				accept : 'page-item4',
				opacity : .8,
				helperclass : 'helper'
			});

			$("#save")
					.click(
							function() {
								var totle = 0;
								var slist = $(".fen");
								var fenstr = "";
								for (var i = 0; i < slist.length; i++) {
									var t = slist[i];

									if (t.value != null && t.value != ''
											&& t.value != undefined) {
										totle = Number(totle) + Number(t.value);
										fenstr = fenstr + "," + t.value;
									} else {
										alert("请设置分数");
										fenstr = "";
										return null;
									}
								}

								var qlist = $(".ti");
								var questionstr = "";
								for (var i = 0; i < qlist.length; i++) {
									var t = qlist[i];

									var code = t.attributes["code"].value;

									if (code != null && code != ''
											&& code != undefined) {
										questionstr = questionstr + "," + code;
									}
								}
								PostSubmit("savepaper", questionstr, fenstr);
							});

			$("#doc")
					.click(
							function() {
								var totle = 0;
								var slist = $(".fen");
								var fenstr = "";
								for (var i = 0; i < slist.length; i++) {
									var t = slist[i];

									if (t.value != null && t.value != ''
											&& t.value != undefined) {
										totle = Number(totle) + Number(t.value);
										fenstr = fenstr + "," + t.value;
									} else {
										alert("请设置分数");
										fenstr = "";
										return null;
									}
								}

								var qlist = $(".ti");
								var questionstr = "";
								for (var i = 0; i < qlist.length; i++) {
									var t = qlist[i];

									var code = t.attributes["code"].value;

									if (code != null && code != ''
											&& code != undefined) {
										questionstr = questionstr + "," + code;
									}
								}

								if (totle != 100) {
									alert("总分不为100，请调整分数。");
								} else {
									//PostSubmit("../common/Compose", questionstr, fenstr);
									$("#questionstr").val(questionstr);
									$("#fenstr").val(fenstr);
									
									var h = "../common/ShijuanYuLan?";
									var wordPaperIdValue = document.getElementById("wordPaperId").value;
									h = h + "wordPaperId="+wordPaperIdValue+"&";
								    h = h + "showAnswer=True&keepThis=true&TB_iframe=true&height=600&width=800";
								    
									$("#Compose").attr("href",h);
									$("#Compose").click();
									
								}

							});
		});

		function del(code) {
			$("#" + code).remove();
		}

		function add(code) {
			var str = prompt("请输入分数", "");
			if (isNaN(str)) {
				alert("请输入数字");
				add(code);
				return;
			}
			if (str == "") {
				alert("请输入分数");
				add(code);
				return;
			}
			$("#" + code).val(str);
		}

		function PostSubmit(url, timsg, fenmsg) {
			var postUrl = url;//提交地址
			var ExportForm = document.createElement("FORM");
			document.body.appendChild(ExportForm);
			ExportForm.method = "POST";

			var newElement = document.createElement("input");
			newElement.setAttribute("name", "ti");
			newElement.setAttribute("type", "hidden");

			var newElement2 = document.createElement("input");
			newElement2.setAttribute("name", "fen");
			newElement2.setAttribute("type", "hidden");
			//试卷名称
			var newElement3 = document.createElement("input");
			newElement3.setAttribute("name", "name");
			newElement3.setAttribute("type", "hidden");
			//类型
			var newElement4 = document.createElement("input");
			newElement4.setAttribute("name", "type");
			newElement4.setAttribute("type", "hidden");
			//组卷人员
			var newElement4 = document.createElement("input");
			newElement4.setAttribute("name", "zujuanrenyuan");
			newElement4.setAttribute("type", "hidden");
			//机构名称
			var newElement4 = document.createElement("input");
			newElement4.setAttribute("name", "jigoumingcheng");
			newElement4.setAttribute("type", "hidden");
			//年级
			var newElement4 = document.createElement("input");
			newElement4.setAttribute("name", "nianji");
			newElement4.setAttribute("type", "hidden");
			//教材版本
			var newElement4 = document.createElement("input");
			newElement4.setAttribute("name", "jiaocaibanben");
			newElement4.setAttribute("type", "hidden");
			//科目
			var newElement4 = document.createElement("input");
			newElement4.setAttribute("name", "kemu");
			newElement4.setAttribute("type", "hidden");
			
			//wordId
			var newElement5 = document.createElement("input");
			newElement5.setAttribute("name", "wordPaperId");
			newElement5.setAttribute("type", "hidden");
			
			ExportForm.appendChild(newElement);
			ExportForm.appendChild(newElement2);
			ExportForm.appendChild(newElement3);
			ExportForm.appendChild(newElement4);
			ExportForm.appendChild(newElement5);
			
			newElement.value = timsg;
			newElement2.value = fenmsg;
			newElement3.value = "${shijuanmingcheng}";
			newElement4.value = "";
			newElement5.value = document.getElementById('wordPaperId').value;
			
			ExportForm.action = postUrl;
			ExportForm.submit();
		};
	</script>

	<div id="main">
		<h1 align="center">${shijuanmingcheng }</h1>
		<input type="button" value="保存试卷" id="save" /> <input type="button"
			value="用Word预览试卷" id="doc" />
		<div style="MARGIN-RIGHT: auto; MARGIN-LEFT: auto; width: 1000px">
			<c:forEach var="u" items="${qlist}" varStatus="s">
				<b>第${s.index+1 }题：</b>
				<br />

				<div class="page-list">
					<c:forEach var="q" items="${u}" varStatus="qstatus">
						<div class="clear-element page-item4 sort-handle left">
							<table id="${q.code}" code="${q.code}"
								style="width: 800px; border-style: solid; margin: 10px"
								class="ti">
								<tr align="center">
									<td>序号</td>
									<td>${qstatus.index+1}</td>
									<td>分数</td>
									<td><input id="fenShu${q.code}" type="text" class="fen" /></td>
									<td>最后一次使用时间</td>
									<td>2014/1/2</td>
									<td><input type="button" onclick="del('${q.code}')"
										value="删除本题"></td>
									<td><input type="button" onclick="add('fenShu${q.code}')"
										value="设置分数"></td>
								</tr>
								<tr>
									<td colspan="7">${q.summary}</td>
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

	<input type="hidden" id="questionstr" />
	<input type="hidden" id="fenstr" />
	<input type="hidden" id="wordPaperId" value=""/>
	<a
		href="../common/Compose?Action=title&ID=&keepThis=true&TB_iframe=true&height=600&width=800" title="试卷预览"
		id="Compose" class="thickbox"></a>
	<jsp:include page="/WEB-INF/jsp/common/footer.jsp" />
</body>
</html>