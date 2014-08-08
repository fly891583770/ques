<%@page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style type="text/css">
*{margin:0;padding:0;list-style-type:none;}
a,img{border:0;}
.demo{width:98%;padding:0px;background:#FFF;border:1px solid #CCC;}

/* menu */
.menu{height:45px;overflow:hidden;}
.menu ul li{float:left;position:relative;text-align:center;line-height:45px;height:45px;overflow:hidden;}
.menu ul li a{position:relative;/* 必要元素 */display:block;width:110px;height:45px;font-size:12px;font-weight:bold;text-decoration:none;cursor:pointer;}
.menu ul li a span{position:absolute;/* 必要元素 */left:0;width:110px;}
.menu ul li a span.out{top:0px;}
.menu ul li a span.over,.menu ul li a span.bg{top:-45px;}
/* 实例一 */
#menu{background:#F8F0D5;}
#menu ul li a{color:#000;}
#menu ul li a span.over{color:#FFF;}
#menu ul li span.bg{height:45px;background:url('../../QuestionBase/img/04.png') center center no-repeat;}
</style>

<script type="text/javascript" src="../../QuestionBase/js/jquery-1.4.1.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	
	//实例一
	$("#menu li a").wrapInner('<span class="out"></span>' ).append('<span class="bg"></span>');
	$("#menu li a").each(function(){
		$('<span class="over">' +  $(this).text() + '</span>').appendTo(this);
	});

	$("#menu li a").hover(function(){
		$(".out",this).stop().animate({'top':'45px'},250);
		$(".over",this).stop().animate({'top':'0px'},250);
		$(".bg",this).stop().animate({'top':'0px'},120);
	},function(){
		$(".out",this).stop().animate({'top':'0px'},250);
		$(".over",this).stop().animate({'top':'-45px'},250);
		$(".bg",this).stop().animate({'top':'-45px'},120);
	});

});
</script>	
	<div class="demo">
		<div id="menu" class="menu">
			<table style="border:none;border:0px;width:100%">
			<tr>
			<td>
			<ul>
			<c:if test="${sessionScope.type eq '学生'}">
				<li><a href="../student/searchquestion">检索试题</a></li>
				<li><a href="../student/searchpaper">检索试卷</a></li>
				<li><a href="../student/exercisebook">查看习题集</a></li>
			</c:if>
			<c:if test="${sessionScope.type eq '家长'}">
				<li><a href="../parent/searchquestion">检索试题</a></li>
				<li><a href="../parent/exercisebook">查看孩子习题集</a></li>
				<li><a href="../parent/getapplyquestion">审批习题答案</a></li>
			</c:if>
			<c:if test="${sessionScope.type eq '老师'}">
				<li><a href="../teacher/submitquestionlist">登陆试题</a></li>
			    <li><a href="../teacher/searchquestion">检索试题</a></li>
			    <li><a href="../teacher/searchpaper">检索试卷</a></li>
			    <li><a href="../teacher/getapplyquestion">审批显示答案</a></li>
			    <li><a href="../teacher/creatpaper">组卷</a></li>
			    <li><a href="../teacher/submitpaperlist">试卷提交审核</a></li>
			    <li><a href="../teacher/batchAddQuestion">批提交试题</a></li>
			</c:if>
			<c:if test="${sessionScope.type eq '主任'}">
				<li><a href="../teacher/submitquestionlist">登陆试题</a></li>
			    <li><a href="../teacher/searchquestion">检索试题</a></li>
			    <li><a href="../teacher/searchpaper">检索试卷</a></li>
			    <li><a href="../teacher/getapplyquestion">审批显示答案</a></li>
			    <li><a href="../teacher/creatpaper">组卷</a></li>
			    <li><a href="../teacher/submitpaperlist">试卷提交审核</a></li>
			    <li><a href="../director/getapllyquestions">审批试题</a></li>
			    <li><a href="../director/getapllypaper">审批试卷</a></li>
			    <li><a href="../teacher/batchAddQuestion">批提交试题</a></li>
			</c:if>
			<c:if test="${sessionScope.type eq '管理员'}">
				<li><a href="../admin/addattribute">追加各种属性</a></li>
				<li><a href="../admin/addAttributeRelation">追加各种属性关联</a></li>
				<li><a href="../admin/editzjxx">编辑章节</a></li>
				<li><a href="../admin/editzsd">编辑知识点</a></li>
			</c:if>
			</ul>
			</td>
			<td style="text-align:right">

			<div style=" color: black; font-size: 16px; top: 110px; font-weight: bold; left: 1200px;"><a href="../account/logout">注销</a></div>
			</td></tr></table>
							
			
		</div>
	</div>