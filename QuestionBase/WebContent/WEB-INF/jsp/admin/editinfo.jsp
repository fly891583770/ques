<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@   taglib uri="/WEB-INF/questionBase.tld " prefix="questionBase"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">  
<title>编辑${edittype}</title>
<link rel="stylesheet" type="text/css" href="../../QuestionBase/css/site.css" />

</head>
<body>
	<jsp:include page="/WEB-INF/jsp/common/header.jsp" />
	<jsp:include page="/WEB-INF/jsp/common/QuestionTags.jsp" />
	<script type="text/javascript">
		$(function(){
			var type = "${edittype}";
			var status = "${status}";
			if(type=="章节信息")
			{
				$("#subform").attr("action","savezjxx");
			}
			else{
				$("#subform").attr("action","savezsd");
			}
			if (status=="ok") {
				alert("上传成功");
			}
			else if (status=="error") {
				alert("上传失败");
			}
			$("#edittype").val(type);
		});
		
		
	</script>
	<div id="main">
	<jsp:include page="/WEB-INF/jsp/common/banner.jsp" />
		<form id="subform" method=post enctype="multipart/form-data">

			<div class="mainpage">
				<table>
					<tr height=80%>
						<td>

						</td>
						<td valign="top">
						
								请设置${edittype}的相关信息：<br />
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
										<td class="condition-result">
										<questionBase:nianji
												id="nianji" name="nianji" />
										</td>
									</tr>
									<tr style="display:none">
										<td class="condition-title">机构名称</td>
										<td class="condition-result"><questionBase:jigoumingcheng
												id="jigoumingcheng" name="jigoumingcheng" /></td>
									</tr>
								</table>

							<input type="file" id="xmlFile" name="xmlFile" />
							
						</td>
					</tr>
				</table>
			</div>
			
			<input type="hidden" name="edittype" id="edittype" />
			<input type="submit" id="sub" value="提交文件" />
		</form>
	</div>
	<jsp:include page="/WEB-INF/jsp/common/footer.jsp" />
</body>
</html>