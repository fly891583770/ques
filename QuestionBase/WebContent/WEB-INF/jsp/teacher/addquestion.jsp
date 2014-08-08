<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="questionBase" uri="/WEB-INF/questionBase.tld "%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>登陆试题</title>
<link rel="stylesheet" type="text/css"
	href="../../QuestionBase/css/site.css" />
</head>

<body>
	<jsp:include page="/WEB-INF/jsp/common/header.jsp" />
	<jsp:include page="/WEB-INF/jsp/common/QuestionTags.jsp" />
	<div id="main">
	<jsp:include page="/WEB-INF/jsp/common/banner.jsp" />
	<script type="text/javascript">
	 function popwin(action) {
		    var pageId = "pageOfficeNewWord";
		    var nameValue = $("#name").val();
		    var answerValue = $("#answer").val();
		    var id ="";
		    if(nameValue!=""){
		    	id = nameValue;
		    }else if(answerValue!=""){
		    	id = answerValue;
		    }
		    var h = "../common/PageOfficeNewWord?";
		    
		    if(answerValue !=""){
		    	h = h + "showAnswer=true&";
		    }
		    
		    h = h + "Action="+action+"&ID="+id+"&keepThis=true&TB_iframe=true&height=600&width=800";
		    
			$("#"+pageId).attr("href",h);
			$("#"+pageId).click();
		   }
	$(function(){
		$("#showWord").click(function(){
		    var pageId = "TitleAndAnswerWord";
		    var nameValue = $("#name").val();
		    var answerValue = $("#answer").val();
		    var id ="";
		    if(nameValue!=""){
		    	id = nameValue;
		    }else if(answerValue!=""){
		    	id = answerValue;
		    }
		    
		    if(id==""){
		    	alert("请先输入题干或者答案");
		    	return ;
		    }
		    
		    var h = "../common/TitleAndAnswerWord?";
		    
		    if(answerValue !=""){
		    	h = h + "showAnswer=true";
		    }
		    h = h + "&ID="+id+"&keepThis=true&TB_iframe=true&height=600&width=800";
		    
			$("#"+pageId).attr("href",h);
			$("#"+pageId).click();
		});
		
		$("#back").click(function(){
			window.opener.callBack(); 
		});
		$("#add").click(function(){
			
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

			var nianji = $("#nianji").val();
			if (nianji == ""|| nianji.indexOf(",")!=-1) {
				alert("请选择年级");
				return;
			}
			
			var nandu = $("#nandu").val();
			if (nandu == ""|| nandu.indexOf(",")!=-1) {
				alert("请选择难度");
				return;
			}
			
			var tixing = $("#tixing").val();
			if (tixing == ""|| tixing.indexOf(",")!=-1) {
				alert("请选择题型");
				return;
			}
			
			var leixing = $("#leixing").val();
			if (leixing == ""|| leixing.indexOf(",")!=-1) {
				alert("请选择类型");
				return;
			}
			
			$.ajax({                 
				cache: true,                 
				type: "POST",                 
				url:"../teacher/savequestion",
				data:$('#mainform').serialize(),           
				async: false,                 
				error: function(request) {
					alert("提交新题失败");                 
					},  
				success: function(data) {   
					alert("提交新题成功");    
					window.parent.location ="searchquestion";                 
					}             
				});
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
		<form id="mainform" method=post action="../teacher/savequestion">
			<%--需要修改的部分 --%>

			<div class="mainpage">

				<table>
					<tr>
						<td>
							<div class="condition">
								试题属性<br />
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
										<td class="condition-result">
										<questionBase:nandu value="question"
												id="nandu" name="nandu"/></td>
									</tr>
									<tr>
										<td class="condition-title">题型</td>
										<td class="condition-result"><questionBase:tixing value="question"
												id="tixing" name="tixing" /></td>
									</tr>
									<tr>
										<td class="condition-title">类型</td>
										<td class="condition-result"><questionBase:leixing value="question"
												id="leixing" name="leixing"/></td>
									</tr>
									<tr>
										<td class="condition-title">机构名称</td>
										<td class="condition-result"><questionBase:jigoumingcheng
												id="jigoumingcheng" name="jigoumingcheng" /></td>
									</tr>
									<tr>
										<td class="condition-title">知识点</td>
										<td class="condition-result"><questionBase:zhishidian value="question"
												id="zhishidian" name="zhishidian"  /></td>
									</tr>
									<tr>
										<td class="condition-title">章节</td>
										<td class="condition-result"><questionBase:zhangjie value="question"
												id="zhangjie" name="zhangjie" /></td>
									</tr>
									<tr>
										<td colspan="2">
											<div style="text-align: center">
											    <input type="button" name="showWord" id="showWord" value="用Word显示" />
											    &nbsp;&nbsp;&nbsp;&nbsp;
												<input type="button" name="add" id="add" value="保存" />
											</div>
				
										</td>
									</tr>
								</table>
							</div>

						</td>

						<td valign="top">试题：
							<div class="ressult">
								<p>题干：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" value="使用Word编辑题干" onclick="popwin('title');"/></p>
                                <input id="name" type="hidden" name="name" value="${question.name}"/>
								<iframe id="nameIframe" width=0 height=0 frameborder=0 scrolling=auto src ="" ></iframe>
								<p>答案：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" value="使用Word编辑答案" onclick="popwin('answer');"/></p>
								<input id="answer" type="hidden" name="answer" value="${question.answer}"/>
								<iframe id="answerIframe" width=0 height=0 frameborder=0 scrolling=auto src ="" ></iframe>
								<p>备注：</p>

								<textarea name="submitComment"  cols="80" rows="3">${question.submitComment}</textarea>

							</div>
						</td>
					</tr>

				</table>

			</div>
			<a href="../student/zsd?keepThis=true&TB_iframe=true&height=300&width=500"	id="zsdLink" title="知识点" class="thickbox"></a>
			<a href="../student/zhangjie?keepThis=true&TB_iframe=true&height=300&width=500" id="zhangjieLink" title="章节" class="thickbox"></a> 
			<%--需要修改的部分 --%>
		</form>
		<a href="../common/PageOfficeNewWord?Action=title&ID=&keepThis=true&TB_iframe=true&height=600&width=800" id="pageOfficeNewWord" title="编辑试题" class="thickbox"></a>
		<a href="../common/TitleAndAnswerWord?Action=title&ID=&keepThis=true&TB_iframe=true&height=600&width=800" id="TitleAndAnswerWord" title="显示试题" class="thickbox"></a>
	</div>
	<jsp:include page="/WEB-INF/jsp/common/footer.jsp" />
</body>
</html>