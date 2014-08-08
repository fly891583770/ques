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
	
	function openpaper(id)
	{
		window.parent.location = "paperview?code="+id;
	}


</script>
	<c:forEach var="u" items="${papers}">
		<input OnClick="saveToSession(${u.code})" type="checkbox" name="CheckboxGroup1" value="复选框" id="CheckboxGroup1_0" />
		<a href="javascript:openpaper(${u.code})">${u.name}</a>
		试卷的状态： ${u.status}
		<br />
	</c:forEach>
	总共${totalrecord}页|当前第${currentpage+1}页|
	<c:if test="${currentpage<=0}">前一页</c:if>
	<c:if test="${currentpage>0}"><a href="javascript:submitpage(${currentpage-1})">前一页</a></c:if>|
	<c:if test="${currentpage>=(totalrecord-1)}">后一页</c:if>
	<c:if test="${currentpage<totalrecord-1}"><a href="javascript:submitpage(${currentpage+1})">后一页</a></c:if>
