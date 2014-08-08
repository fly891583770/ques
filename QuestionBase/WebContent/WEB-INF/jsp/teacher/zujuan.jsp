<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@   taglib uri="/WEB-INF/questionBase.tld " prefix="questionBase"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>组卷</title>
<link rel="stylesheet" type="text/css" href="../../QuestionBase/css/site.css" />

</head>
<body>
	<jsp:include page="/WEB-INF/jsp/common/header.jsp" />
	<jsp:include page="/WEB-INF/jsp/common/QuestionTags.jsp" />
	<script type="text/javascript">
	var mingcheng="";

		$(function(){
			$("#pagehao").val('${currentpage}');
			
			$("#search").click(function(){
				document.forms[0].action="zujuantilist";
				document.forms[0].target="result";
				document.forms[0].submit();
			});

			$("#zujuanyulan").click(
					function() {
						
						if(document.getElementById("shijuanmingcheng").value==''){
							alert("请填写试卷名称");
						}else if(document.getElementById("xuanzhongti").value==''){
							alert("请至少选择一道题");
						}else{
							document.forms[0].action="zujuanyulan";
							document.forms[0].target="";
							document.forms[0].submit();
						}
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
		<form method=post>
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
												name="kemu"   value="question" /></td>
									</tr>
									<tr>
										<td class="condition-title">教材版本</td>
										<td class="condition-result"><questionBase:jiaocaibanben value="question"
												id="jiaocaibanben" name="jiaocaibanben"  /></td>
									</tr>
									<tr>
										<td class="condition-title">年级</td>
										<td class="condition-result"><questionBase:nianji value="question"
												id="nianji" name="nianji"  /></td>
									</tr>
									<tr>
										<td class="condition-title">难度</td>
										<td class="condition-result">
											<questionBase:nandu value="question"
												id="nandu" name="nandu" /></td>
									</tr>
									<tr>
										<td class="condition-title">题型</td>
										<td class="condition-result">
											<questionBase:tixing value="question"
												id="tixing" name="tixing" /></td>
									</tr>
									<tr>
										<td class="condition-title">类型</td>
										<td class="condition-result">
											<questionBase:leixing value="question"
												id="leixing" name="leixing" /></td>
									</tr>
									<tr>
										<td class="condition-title">机构名称</td>
										<td class="condition-result"><questionBase:jigoumingcheng value="question"
												id="jigoumingcheng" name="jigoumingcheng" /></td>
									</tr>
									<tr>
										<td class="condition-title">出题人员</td>
										<td class="condition-result">
											<questionBase:chutirenyuan value="question"
												id="chutirenyuan" name="chutirenyuan" /></td>
									</tr>
									<tr>
										<td class="condition-title">知识点</td>
										<td class="condition-result">
										<questionBase:zhishidian value="question"
												id="zhishidian" name="zhishidian"  /></td>
									</tr>
									<tr>
										<td class="condition-title">章节</td>
										<td class="condition-result">
											<questionBase:zhangjie value="question"
												id="zhangjie" name="zhangjie" />
										</td>
									</tr>
									<tr colspan="2">
										<td><input type="button" name="search" id="search"
											value="查询" />
											</td>
									</tr>
								</table>
							</div>
						</td>
						<td valign="top">
							试卷名称：&nbsp;&nbsp;&nbsp;<input name="shijuanmingcheng" type="text" id="shijuanmingcheng" />
							<input type="button" value="组卷预览" id="zujuanyulan" />
							<p>
							<iframe name="result" frameborder="0" height="450px" scrolling="auto" width="750px"></iframe>
						</td>
					</tr>
				</table>
			</div>
			<input type="hidden" name="pagehao" id="pagehao" value="0" />
			<input type="hidden" name="xuanzhongti" id="xuanzhongti" />
			<a href="../student/zsd?keepThis=true&TB_iframe=true&height=300&width=500"	id="zsdLink" title="知识点" class="thickbox"></a>
			<a href="../student/zhangjie?keepThis=true&TB_iframe=true&height=300&width=500" id="zhangjieLink" title="章节" class="thickbox"></a> 
			
		</form>
	</div>
	<jsp:include page="/WEB-INF/jsp/common/footer.jsp" />
</body>
</html>