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
		
		if(xuanzhong!=null&&xuanzhong!="")
		{
			var tis = xuanzhong.split(',');
		}
		$(window.parent.document).find('#xuanzhongti').val(xuanzhong+","+code);
		
		xuanzhong = $(window.parent.document).find('#xuanzhongti').val();
	}
	
	function openquestion(id)
	{
		window.parent.location = "editquestion?id="+id;
	}
	
	$(function(){
/* 		$("#search").click( function() {
				var xuanzhong = $(window.parent.document).find('#xuanzhongti').val();
				var name = prompt("习题册名：","");
				window.parent.location = "createexercisebook?name="+name+"&questions="+xuanzhong;
				
		}); */
	});


</script>

	试题列表：
<p>
	<c:forEach var="u" items="${questions}">
		<a href="javascript:openquestion(${u.code})">${u.summary}</a>
		<br />
	</c:forEach>
</p>
	总共${totalrecord}页|当前第${currentpage+1}页|
	<c:if test="${currentpage<=0}">前一页</c:if>
	<c:if test="${currentpage>0}"><a href="javascript:submitpage(${currentpage-1})">前一页</a></c:if>|
	<c:if test="${currentpage>=(totalrecord-1)}">后一页</c:if>
	<c:if test="${currentpage<totalrecord-1}"><a href="javascript:submitpage(${currentpage+1})">后一页</a></c:if>
