<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>审批学生试题答案申请</title>
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
            	 
            	 if($selectsubBox.length==0)
            	 {
            		 alert("请至少选择一道题通过申请。");
            	 }
            	 else
            	 {
                	 $.each($selectsubBox,function(key,val){
                		 result = result +":"+val.id;
                	 });
                	 
                	 $.get("../teacher/approvedquestion?id="+result,alert("已批准所选的申请"));
            	 }
             });
         });  
     </script>  
		
			<%--需要修改的部分 --%>

			<div class="mainpage">
			<b>学生提交的申请：</b>
				<table class="showtable">
					<tr width=60%>
						<th width=5%><input type="checkbox" id="checkAll" name="checkAll" / ></th>
						<th width=15%>学生姓名</th>
						<th width=40%>试题预览</th>
					</tr>
					
					<c:forEach var="u" items="${hash}">
						<c:forEach var="q" items="${u.value}">
							<tr>
								<td><input type="checkbox" id="${u.key.code}|${q.code}" name="${u.key.code}|${q.code}" class="subbox" / ></td>
								<td><c:out value="${u.key.name}" /></td>
								<td><c:out value="${q.summary}" /></td>
							</tr>
						
						</c:forEach>
					</c:forEach>
				</table>
				<input type="button" name="aplly" id="aplly" value="通过申请" />
			</div>
			
			<%--需要修改的部分 --%>

	</div>
	<jsp:include page="/WEB-INF/jsp/common/footer.jsp" />
</body>
</html>