<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" src="../../QuestionBase/js/jquery-1.4.1.js"></script>

<script type="text/javascript">
	var xanzhong;
	function submitpage(pagehao)
	{
		$(window.parent.document).find('#pagehao').val(pagehao);
		$(window.parent.document).find('#search').click();
	}
	
	function saveToSession(code)
	{
		var xuanzhong = $(window.parent.document).find('#xuanzhongti').val();
		
		$(window.parent.document).find('#xuanzhongti').val(xuanzhong+","+code);
		
		xuanzhong = $(window.parent.document).find('#xuanzhongti').val();
		
		$("#"+code).hide();
	}
	
	function openquestion(id)
	{
		window.parent.location = "editquestion?id="+id;
	}


</script>
	<c:forEach var="u" items="${questions}">
	<table id="${u.code}" border="1" width="740px" padding-bottom="5px">
		<tr align="center">
			<td>出题人</td><td>${u.teacherName}</td>
			<td>组卷使用次数</td><td>${u.count }</td>
			<td>最后一次使用时间</td><td>${u.updateTime }</td>
			<td><input type="button" onclick="saveToSession('${u.code}')" value="加入组卷备选题筐"></td>
		</tr>
		<tr>
			<td colspan="7">${u.summary}</td>
		</tr>
	</table>
	</c:forEach>
	总共${totalrecord}页|当前第${currentpage+1}页|
	<c:if test="${currentpage<=0}">前一页</c:if>
	<c:if test="${currentpage>0}"><a href="javascript:submitpage(${currentpage-1})">前一页</a></c:if>|
	<c:if test="${currentpage>=(totalrecord-1)}">后一页</c:if>
	<c:if test="${currentpage<totalrecord-1}"><a href="javascript:submitpage(${currentpage+1})">后一页</a></c:if>
