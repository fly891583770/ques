<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@   taglib uri="/WEB-INF/questionBase.tld " prefix="questionBase"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>检索试题</title>
<link rel="stylesheet" type="text/css"
	href="../../QuestionBase/css/site.css" />


</head>
<body>
	<jsp:include page="/WEB-INF/jsp/common/header.jsp" />
	<jsp:include page="/WEB-INF/jsp/common/QuestionTags.jsp" />
	<script type="text/javascript">
		$(function() {
			$("#pagehao").val('${currentpage}');

			$("#tijiaoshenghe").click(
					function() {
						var xuanzhong = $(window.parent.document).find(
								'#xuanzhongti').val();
						window.parent.location = "submitquestion?questions="
								+ xuanzhong;

					});
		});
	</script>
	
	<script type="text/javascript">
		$(function() {
			$("#zhishidianButton")
					.click(
							function() {
								$("#zsdLink").attr("href", "../student/zsd?");
								var h = $("#zsdLink").attr("href");
								var courceCode = $("#kemu").val();
								if (courceCode == "" || courceCode.indexOf(",")!=-1) {
									alert("请选择科目");
									return;
								}
								var teachingMaterialVersion = $(
										"#jiaocaibanben").val();
								if (teachingMaterialVersion == "" || teachingMaterialVersion.indexOf(",")!=-1) {
									alert("请选择教材版本");
									return;
								}

								var year = $("#nianji").val();
								if (year == ""|| year.indexOf(",")!=-1) {
									alert("请选择年级");
									return;
								}

								var schoolCode = $("#jigoumingcheng").val();
								if (schoolCode == ""|| schoolCode.indexOf(",")!=-1) {
									alert("机构名称");
									return;
								}
								h = h + "&courceCode=" + courceCode
										+ "&teachingMaterialVersion="
										+ teachingMaterialVersion + "&year="
										+ year + "&schoolCode=" + schoolCode;
								h = h
										+ "&keepThis=true&TB_iframe=true&height=300&width=500";
								$("#zsdLink").attr("href", h);
								$("#zsdLink").click();
							});
			$("#zhangjieButton")
					.click(
							function() {
								$("#zhangjieLink").attr("href",
										"../student/zhangjie?");
								var h = $("#zhangjieLink").attr("href");
								var courceCode = $("#kemu").val();
								if (courceCode == "" || courceCode.indexOf(",")!=-1) {
									alert("请选择科目");
									return;
								}
								var teachingMaterialVersion = $(
										"#jiaocaibanben").val();
								if (teachingMaterialVersion == "" || teachingMaterialVersion.indexOf(",")!=-1) {
									alert("请选择教材版本");
									return;
								}

								var year = $("#nianji").val();
								if (year == ""|| year.indexOf(",")!=-1) {
									alert("请选择年级");
									return;
								}

								var schoolCode = $("#jigoumingcheng").val();
								if (schoolCode == ""|| schoolCode.indexOf(",")!=-1) {
									alert("机构名称");
									return;
								}
								h = h + "&courceCode=" + courceCode
										+ "&teachingMaterialVersion="
										+ teachingMaterialVersion + "&year="
										+ year + "&schoolCode=" + schoolCode;
								h = h
										+ "&keepThis=true&TB_iframe=true&height=300&width=500";
								$("#zhangjieLink").attr("href", h);
								$("#zhangjieLink").click();
							});
		});
	</script>
	<div id="main">
	<jsp:include page="/WEB-INF/jsp/common/banner.jsp" />
		<form method=post action="questionlist" target="result">
			<div class="mainpage">
				<table>
					<tr height=80% vAlign="top">
						<td>
							<div class="condition">
								请选择试题筛选条件<br />
								<table style="wdith: 100%; border: 1px">
									<tr>
										<td class="condition-title">科目</td>
										<td class="condition-result"><questionBase:kemu id="kemu"
												name="kemu" /></td>
									</tr>
									<tr>
										<td class="condition-title">教材版本</td>
										<td class="condition-result"><questionBase:jiaocaibanben
												id="jiaocaibanben" name="jiaocaibanben" /></td>
									</tr>
									<tr>
										<td class="condition-title">年级</td>
										<td class="condition-result"><questionBase:nianji
												id="nianji" name="nianji" /></td>
									</tr>
									<tr>
										<td class="condition-title">难度</td>
										<td class="condition-result"><select name="nandu"
											id="nandu">
												<option value="">全部难度</option>
												<option value="1">简单</option>
												<option value="2">中等</option>
												<option value="3">困难</option>
										</select></td>
									</tr>
									<tr>
										<td class="condition-title">题型</td>
										<td class="condition-result"><select name="tixing"
											id="tixing">
												<option value="">全部题型</option>
												<option value="1">填空</option>
												<option value="2">选择</option>
												<option value="3">判断</option>
												<option value="4">问答</option>
												<option value="5">计算</option>
										</select></td>
									</tr>
									<tr>
										<td class="condition-title">类型</td>
										<td class="condition-result"><select name="leixing"
											id="leixing">
												<option value="">全部类型</option>
												<option value="1">类型1</option>
												<option value="2">类型2</option>
										</select></td>
									</tr>
									<tr>
										<td class="condition-title">机构名称</td>
										<td class="condition-result"><questionBase:jigoumingcheng
												id="jigoumingcheng" name="jigoumingcheng" /></td>
									</tr>
									<tr>
										<td class="condition-title">出题人员</td>
										<td class="condition-result"><input type="text"
											name="chutirenyuan" id="chutirenyuan" /></td>
									</tr>
									<tr>
										<td class="condition-title">试卷名称</td>
										<td class="condition-result"><input type="text"
											name="shijuanmingcheng" id="shijuanmingcheng" /></td>
									</tr>
									<tr>
										<td class="condition-title">知识点</td>
										<td class="condition-result"><input type="hidden"
											name="zhishidian" id="zhishidian" /><br /> <textarea
												id="zhishidianArea" name="zhishidianArea"
												readonly="readonly" cols="21" rows="5"
												style="font-size: 12px; color: #F00"></textarea><br/><input
											type="button" id="zhishidianButton" value="展开" /></td>
									</tr>
									<tr>
										<td class="condition-title">章节</td>
										<td class="condition-result"><input type="hidden"
											name="zhangjie" id="zhangjie" /> <br /> <textarea
												id="zhangjieArea" name="zhangjieArea" readonly="readonly"
												cols="21" rows="5" style="font-size: 12px; color: #F00"></textarea>
											<br /> <input type="button" value="展开" id="zhangjieButton" /></td>
									</tr>
									<tr colspan="2">
										<td><input type="submit" name="search" id="search"
											value="查询" /></td>
									</tr>
								</table>
							</div>
						</td>
						<td valign="top">试题列表：&nbsp;
							<p>
								<iframe name="result" frameborder="0" height="450px"
									scrolling="no" width="750px"></iframe>
						</td>
					</tr>
				</table>
			</div>
			<input type="hidden" name="pagehao" id="pagehao" value="0" /> <input
				type="hidden" name="xuanzhongti" id="xuanzhongti" />
				<a href="../student/zsd?keepThis=true&TB_iframe=true&height=300&width=500"	id="zsdLink" title="知识点" class="thickbox"></a>
			<a href="../student/zhangjie?keepThis=true&TB_iframe=true&height=300&width=500" id="zhangjieLink" title="章节" class="thickbox"></a> 
		</form>
	</div>
	<jsp:include page="/WEB-INF/jsp/common/footer.jsp" />
</body>
</html>