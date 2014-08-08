<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>审核试题入库申请</title>
<link rel="stylesheet" type="text/css"
	href="../../QuestionBase/css/site.css" />
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/common/header.jsp" />
	<div id="main">
	<jsp:include page="/WEB-INF/jsp/common/banner.jsp" />
	 <script type="text/javascript">  
         $(function() {  
            $("#checkAll").click(function() {  
                 $('input[class="subbox"]').attr("checked",this.checked);   
             });  

             var $subBox = $("input[class='subbox']");  
             $subBox.click(function(){  
                 $("#checkAll").attr("checked",$subBox.length == $("input[class='subbox']:checked").length ? true : false);  
             });  
             
             $("#aplly").click(function(){
            	 var result="" ;
            	 var $selectsubBox = $("input[class='subbox']:checked");
            	 
            	 $.each($selectsubBox,function(key,val){
            		 result = result +":"+val.id;
            	 });
            	 
            	 $.get("../director/agreeapllyquestion?id="+result,alert("已批准所选的申请"));
            	 
             });
             
             $("#disagree").click(function(){
            	 var result="" ;
            	 var $selectsubBox = $("input[class='subbox']:checked");
            	 
            	 $.each($selectsubBox,function(key,val){
            		 result = result +":"+val.id;
            	 });
            	 
            	 $.get("../director/disagreeapllyquestion?id="+result,alert("已拒绝所选的申请"));
            	 
             });
             
         });  
     </script>  
		
			<%--需要修改的部分 --%>

			<div class="mainpage">
			<b>提交的试题审批申请：</b>
				<table class="showtable" width=80%>
					<tr>
						<th width=5%><input type="checkbox" id="checkAll" name="checkAll" / ></th>
						<th width=15%>老师姓名</th>
						<th width=40%>试题预览</th>
						<th width=20%>注释信息</th>
					</tr>
					
					<c:forEach var="q" items="${questions}">
						<tr>
							<td><input type="checkbox" id="${q.code}" name="${q.code}" class="subbox" / ></td>
							<td><c:out value="${q.teacherName}" /></td>
						<td><a href="../teacher/editquestion?id=${q.code}" ><c:out value="${q.summary}" /></a></td>
							<td><c:out value="${q.submitComment}" /></td>
						</tr>
					</c:forEach>
				</table>
			</div>
			<input type="button" name="aplly" id="aplly" value="通过申请" />
			<input type="button" name="disagree" id="disagree" value="拒绝申请" />
			<%--需要修改的部分 --%>

	</div>
	<jsp:include page="/WEB-INF/jsp/common/footer.jsp" />
</body>
</html>