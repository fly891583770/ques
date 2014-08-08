<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@   taglib uri="/WEB-INF/questionBase.tld " prefix="questionBase"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>检索试卷</title>
<link rel="stylesheet" type="text/css" href="../../QuestionBase/css/site.css" />
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/common/header.jsp" />
	<jsp:include page="/WEB-INF/jsp/common/QuestionTags.jsp" />
	<script type="text/javascript">
		$(function(){
			$("#pagehao").val('${currentpage}');
			
			$("#tijiaoshenghe").click( function() {
				var xuanzhong = $(window.parent.document).find('#xuanzhongti').val();
				window.parent.location = "submitquestion?questions="+xuanzhong;
				
		});
	});
	</script>
	<script type="text/javascript">
		$(function() {
			$("#pagehao").val('${currentpage}');
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
		<form method=post action="paperlist" target="result">
			<div class="mainpage">
				<table>
					<tr height=80%  vAlign="top">
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
										<td class="condition-title">机构名称</td>
										<td class="condition-result"><questionBase:jigoumingcheng
												id="jigoumingcheng" name="jigoumingcheng" /></td>
									</tr>
									<tr>
										<td class="condition-title">组卷人员</td>
										<td class="condition-result">
											<questionBase:chutirenyuan 
												id="chutirenyuan" name="chutirenyuan" /></td>
									</tr>
									<tr>
										<td class="condition-title">试卷名称</td>
										<td class="condition-result"><input type="text"
											name="shijuanmingcheng" id="shijuanmingcheng" /></td>
									</tr>
									<tr>
										<td class="condition-title">类型</td>
										<td class="condition-result">
											<questionBase:leixing 
												id="leixing" name="leixing" /></td>
									</tr>

									<tr colspan="2">
										<td><input type="submit" name="search" id="search"
											value="查询" /></td>
									</tr>
								</table>
							</div>
						</td>
						<td valign="top">
							试题列表：&nbsp;
							<p>
							<iframe name="result" frameborder="0" height="450px" scrolling="no" width="750px"></iframe>
						</td>
					</tr>
				</table>
			</div>
			<a href="../student/zsd?keepThis=true&TB_iframe=true&height=300&width=500"	id="zsdLink" title="知识点" class="thickbox"></a>
			<a href="../student/zhangjie?keepThis=true&TB_iframe=true&height=300&width=500" id="zhangjieLink" title="章节" class="thickbox"></a> 
			<input type="hidden" name="pagehao" id="pagehao" value="0" />
			<input type="hidden" name="xuanzhongti" id="xuanzhongti" />
		</form>
	</div>
	<jsp:include page="/WEB-INF/jsp/common/footer.jsp" />
</body>
</html>