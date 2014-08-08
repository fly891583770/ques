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
		var url = location.href;
		if(url.indexOf("searchquestion")!=-1)
		{
			
		}
	});
	
	function request(paras) {
		var url = location.href;
		var paraString = url.substring(url.indexOf("?") + 1, url.length)
				.split("&");
		var paraObj = {}
		for ( var i = 0; i < paraString.length; i++) {
			var j = paraString[i]
			paraObj[j.substring(0, j.indexOf("=")).toLowerCase()] = j
					.substring(j.indexOf("=") + 1, j.length);
		}
		var returnValue = paraObj[paras.toLowerCase()];
		if (typeof (returnValue) == "undefined") {
			return "";
		} else {
			return returnValue;
		}
	}

</script>
	<c:forEach var="u" items="${questions}">
		<input OnClick="saveToSession(${u.code})" type="checkbox" name="CheckboxGroup1" value="复选框" id="CheckboxGroup1_0" />
		<a href="javascript:openquestion(${u.code})">${u.summary}</a>
		题的状态： ${u.status}
		<br />
	</c:forEach>
	总共${totalrecord}页|当前第${currentpage+1}页|
	<c:if test="${currentpage<=0}">前一页</c:if>
	<c:if test="${currentpage>0}"><a href="javascript:submitpage(${currentpage-1})">前一页</a></c:if>|
	<c:if test="${currentpage>=(totalrecord-1)}">后一页</c:if>
	<c:if test="${currentpage<totalrecord-1}"><a href="javascript:submitpage(${currentpage+1})">后一页</a></c:if>
