<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/questionBase.tld " prefix="questionBase"%>
<!DOCTYPE html>
<html>
<head>
<title>检索试题</title>
<link rel="stylesheet" type="text/css" href="../../QuestionBase/css/site.css" />
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/common/header.jsp" />
	<jsp:include page="/WEB-INF/jsp/common/QuestionTags.jsp" />
	<script type="text/javascript">
		$(function() {
			$("#pagehao").val('${currentpage}');
		});
	</script>
	<div id="main">

		<jsp:include page="/WEB-INF/jsp/common/banner.jsp" />
		<form method=post action="questionlist" target="result">
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
										<td class="condition-title">难度</td>
										<td class="condition-result"><questionBase:nandu 
												id="nandu" name="nandu" /></td>
									</tr>
									<tr>
										<td class="condition-title">题型</td>
										<td class="condition-result"><questionBase:tixing 
												id="tixing" name="tixing" /></td>
									</tr>
									<tr>
										<td class="condition-title">类型</td>
										<td class="condition-result"><questionBase:leixing 
												id="leixing" name="leixing" /></td>
									</tr>
									<tr>
										<td class="condition-title">机构名称</td>
										<td class="condition-result"><questionBase:jigoumingcheng
												id="jigoumingcheng" name="jigoumingcheng" /></td>
									</tr>
									<tr>
										<td class="condition-title">出题人员</td>
										<td class="condition-result"><questionBase:chutirenyuan 
												id="chutirenyuan" name="chutirenyuan" /></td>
									</tr>
									<tr>
										<td class="condition-title">试卷名称</td>
										<td class="condition-result"><input type="text"
											name="shijuanmingcheng" id="shijuanmingcheng" /></td>
									</tr>
									<tr>
										<td class="condition-title">知识点</td>
										<td class="condition-result"><questionBase:zhishidian
												id="zhishidian" name="zhishidian" /></td>
									</tr>
									<tr>
										<td class="condition-title">章节</td>
										<td class="condition-result"><questionBase:zhangjie
												id="zhangjie" name="zhangjie" /></td>
									</tr>
									<tr colspan="2">
										<td><input type="submit" name="search" id="search"
											value="查询" /></td>
									</tr>
								</table>
							</div>
						</td>
						<td valign="top">
						<iframe name="result" frameborder="0" height="450px" scrolling="no" width="750px"></iframe>
						</td>
					</tr>
				</table>
			</div>
			<input	type="hidden" name="pagehao" id="pagehao" value="0" /> 
			<input	type="hidden" name="xuanzhongti" id="xuanzhongti" />
		</form>
	</div>
	<jsp:include page="/WEB-INF/jsp/common/footer.jsp" />
</body>
</html>