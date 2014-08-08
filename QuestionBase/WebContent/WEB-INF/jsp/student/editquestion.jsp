<%@ page language="java" contentType="text/html; charset=UTF-8" 
import="java.util.*,com.questionbase.word.*,java.sql.*,java.io.*,java.sql.*,java.io.*,com.questionbase.hbm.factory.*,
	org.hibernate.*,com.questionbase.hbm.*;"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="questionBase" uri="/WEB-INF/questionBase.tld "%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.pageoffice.cn" prefix="po"%>
<%
	FileManager fm = new FileManager();
	fm.CreateDocHtmFolder(request.getSession().getServletContext().getRealPath("/"));
	
	String id = null;
	if (request.getParameter("id") != null
			&& request.getParameter("id").trim().length() > 0) {
		id = request.getParameter("id");
	}
	//Get Steam id
	Session heSession = QuestionBaseFactory.GetInstance()
				.getSessionFactory().openSession();
	System.out.println("QuestionBank id=" + id);	
	QuestionBank q = (QuestionBank)heSession.load(QuestionBank.class, Integer.parseInt(id.trim()) );
	id = q.getName();
	System.out.println("stream id=" + id);	
	String parentDocPath = request.getSession().getServletContext().getRealPath("doc");
	String parentHtmPath = request.getSession().getServletContext().getRealPath("htm");


	HTMPreview hp = new HTMPreview();
	String docTitleFileName = hp.getHtmFile(parentDocPath, parentHtmPath, id, "title");
	String docAnswerFileName = hp.getHtmFile(parentDocPath, parentHtmPath, id, "answer");;
	
	String docTitlePath = "";
	if(docTitleFileName!=null && docTitleFileName.trim().length()>0){
		docTitlePath = "../htm/" + docTitleFileName;
	}
	String docAnswerPaht = "";
	if(docAnswerFileName!=null && docAnswerFileName.trim().length()>0){
		docAnswerPaht = "../htm/" + docAnswerFileName;
	}
%>
<html>
<head>
<title>显示试题</title>
<link rel="stylesheet" type="text/css"
	href="../../QuestionBase/css/site.css" />
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/common/header.jsp" />
	<jsp:include page="/WEB-INF/jsp/common/QuestionTags.jsp" />
	<div id="main">
			<jsp:include page="/WEB-INF/jsp/common/banner.jsp" />
	<script language="javascript">
		var daanShowHide = "hide";
		$(function(){
			if("${question.name}"!=""&&"${question.name}"!="undefined")
			{
				$("#kemu").val("${question.courseCode}");
				$("#jiaocaibanben").val("${question.teachingMaterialVersion}");
				$("#nianji").val("${question.year}");
				$("#nandu").val("${question.difficulty}");
				$("#tixing").val("${question.questionTypes}");
				$("#leixing").val("${question.types}");
				$("#jigoumingcheng").val("${question.organization}");
				$("#zujuanrenyuan").val("${teacherCode}");
				$("#answerDiv").hide();
				$("#xianshidaan").click(function(){
					$("#answerDiv").show();
					daanShowHide = "show";
				});
			}
			$("#xianshidaan").click(function(){
				$("#answerDiv").show();
				daanShowHide = "show";
			});
			
			$("#shenqingdaan").click(function(){
				$.get("../student/askforanswer?qid=${question.code}",alert("答案已經申請，請等待審批"));
			});
		});
		function popwin(action) {
			var pageId = "pageOfficeNewWord";
			var nameValue = $("#name").val();
			var answerValue = $("#answer").val();
			var id = "";
			if (nameValue != "") {
				id = nameValue;
			} else if (answerValue != "") {
				id = answerValue;
			}
			var h = "../common/PageOfficeReadOnlyWord?";
			h = h + "Action=" + action + "&ID=" + id
					+ "&keepThis=true&TB_iframe=true&height=600&width=800";

			$("#" + pageId).attr("href", h);
			$("#" + pageId).click();
		}
	</script>
<script type="text/javascript">
		$(function() {
			$("#pagehao").val('${currentpage}');
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
			    if(daanShowHide == "show")
			    	h = h + "showAnswer=true";
			    else
			    	h = h + "showAnswer=false";
			    h = h + "&ID="+id+"&keepThis=true&TB_iframe=true&height=600&width=800";
			    
				$("#"+pageId).attr("href",h);
				$("#"+pageId).click();
			});
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
		<form method=post action="student/searchquestion">
			<%--需要修改的部分 --%>

			<div class="mainpage">
				<table>
					<tr  vAlign="top">
						<td>
							<div class="condition">
								试题属性<br />
								<table style="wdith: 100%; border: 1px">
									<tr>
										<td class="condition-title">科目</td>
										<td class="condition-result"><questionBase:kemu id="kemu"
												name="kemu"  disabled = "true" value="question" /></td>
									</tr>
									<tr>
										<td class="condition-title">教材版本</td>
										<td class="condition-result"><questionBase:jiaocaibanben value="question"
												id="jiaocaibanben" name="jiaocaibanben" disabled = "true" /></td>
									</tr>
									<tr>
										<td class="condition-title">年级</td>
										<td class="condition-result"><questionBase:nianji value="question"
												id="nianji" name="nianji" disabled = "true" /></td>
									</tr>
									<tr>
										<td class="condition-title">难度</td>
										<td class="condition-result">
											<questionBase:nandu value="question"
												id="nandu" name="nandu" disabled = "true" /></td>
									</tr>
									<tr>
										<td class="condition-title">题型</td>
										<td class="condition-result">
											<questionBase:tixing value="question"
												id="tixing" name="tixing" disabled = "true" /></td>
									</tr>
									<tr>
										<td class="condition-title">类型</td>
										<td class="condition-result">
											<questionBase:leixing value="question"
												id="leixing" name="leixing" disabled = "true" /></td>
									</tr>
									<tr>
										<td class="condition-title">机构名称</td>
										<td class="condition-result"><questionBase:jigoumingcheng value="question"
												id="jigoumingcheng" name="jigoumingcheng" disabled = "true" /></td>
									</tr>
									<tr>
										<td class="condition-title">出题人员</td>
										<td class="condition-result">
											<questionBase:chutirenyuan value="question"
												id="chutirenyuan" name="chutirenyuan" disabled = "true" /></td>
									</tr>
									<tr>
										<td class="condition-title">知识点</td>
										<td class="condition-result">
										<questionBase:zhishidian value="question"
												id="zhishidian" name="zhishidian" disabled = "true" /></td>
									</tr>
									<tr>
										<td class="condition-title">章节</td>
										<td class="condition-result">
											<questionBase:zhangjie value="question"
												id="zhangjie" name="zhangjie" disabled = "true" />
										</td>
									</tr>
									<tr>
										<td colspan="2">
											<div style="text-align: center">
											<c:choose>
											<c:when test="${status}"><input type="button" name="xianshidaan" id="xianshidaan" value="显示答案" /></c:when>
											<c:otherwise><input type="button" name="shenqingdaan" id="shenqingdaan" value="申请答案" /></c:otherwise>
											</c:choose>
												<input type="button" name="showWord" id="showWord" value="用Word显示" />
												<input type="button" name="back" id="back" value="返回" onclick="history.back(-1);" />
											</div>
										</td>
									</tr>
								</table>
							</div>

						</td>

						<td valign="top">试题：
							<div class="ressult">
								<p>
									题干：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input
										type="button" value="使用Word查看题干" onclick="popwin('title');" />
								</p>
								<input id="name" type="hidden" name="name" value="${question.name}" />
								<iframe id="nameIframe" width=660 height=220 frameborder=0
									scrolling=auto src="<%=docTitlePath%>"></iframe>
									
								<div id ="answerDiv">	
									<p>
										答案：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input
											type="button" value="使用Word查看答案" onclick="popwin('answer');" />
									</p>
									<input id="answer" type="hidden" name="answer" value="${question.answer}" />
									<iframe id="answerIframe" width=660 height=220 frameborder=0
										scrolling=auto src="<%=docAnswerPaht%>"></iframe>
									
								</div>
								
							</div>
						</td>
					</tr>
				</table>
			</div>
		</form> 
			<a href="../common/PageOfficeReadOnlyWord?Action=title&ID=&keepThis=true&TB_iframe=true&height=600&width=750" id="pageOfficeNewWord" title="查看试题" class="thickbox"></a>
			<a href="../common/TitleAndAnswerWord?Action=title&ID=&keepThis=true&TB_iframe=true&height=600&width=800" id="TitleAndAnswerWord" title="显示试题" class="thickbox"></a>
			<a href="../student/zsd?keepThis=true&TB_iframe=true&height=300&width=500"	id="zsdLink" title="知识点" class="thickbox"></a>
			<a href="../student/zhangjie?keepThis=true&TB_iframe=true&height=300&width=500" id="zhangjieLink" title="章节" class="thickbox"></a>
	</div>
	<jsp:include page="/WEB-INF/jsp/common/footer.jsp" />
</body>
</html>