<%@page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@page import="javax.servlet.http.HttpSession" %>
<%@page import="org.springframework.web.context.request.ServletRequestAttributes" %>
<%@page import="org.springframework.web.context.request.RequestContextHolder" %>
<%@page import="com.questionbase.logic.person.Parent" %>

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
			<ul>
			<%
			Parent s = (Parent) session.getAttribute("LoginUser");
			%>
				<li><a href="../parent/searchquestion">检索试题</a></li>
				<li><a href="../parent/exercisebook?parentid=<%=s.getFamilyCode() %>">查看孩子习题集</a></li>
				<li><a href="../parent/getapplyquestion">审批习题答案</a></li>
			</ul>
		</div>
	</div>