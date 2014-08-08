<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="questionBase" uri="/WEB-INF/questionBase.tld "%>

<!DOCTYPE html>
<html>
<head>
<title>追加各种属性关联</title>
<link rel="stylesheet" type="text/css"
	href="../../QuestionBase/css/site.css" />
</head>

<body>
	<jsp:include page="/WEB-INF/jsp/common/header.jsp" />
	<script type="text/javascript">
		$(function() {
			var courseData = ${courseData};
			var teachingMaterialVersionInfoData = ${teachingMaterialVersionInfoData};
			var questionTypeData = ${questionTypeData};
			var typesInfoData = ${typesInfoData};
			var courseAndMaterialVersion = ${courseAndMaterialVersion};
			var courseAndQuestionType = ${courseAndQuestionType};
			var courseAndTypes = ${courseAndTypes};
			
			for (var i = 0; i < courseData.length; i++) {
				var str = "<tr id=\""+ courseData[i].key + "\">";
				str = str + "<td>"+ courseData[i].value + "</td>";
				str = str + "<td>";
				str = str + makeTeachingMaterialVersionStr(courseData[i].key);
				str = str + "</td>";
				str = str + "<td>";
				str = str + makeQuestionTypeStr(courseData[i].key);
				str = str + "</td>";
				str = str + "<td>";
				str = str + makeTypeStr(courseData[i].key);
				str = str + "</td>";
				str = str + "<td>";
				str = str + "<input type=\"button\" value=\"提交\" class=\"batchAdd\"/>";
				str = str + "</td>";
				str = str + "</tr>";
			    $("#table").append(str);
			    
			    doCheckTeachingMaterialVersion(courseData[i].key);
			    doCheckQuestionType(courseData[i].key);
			    doCheckTypes(courseData[i].key);
			}
			
			$(".batchAdd").click(function(){
				//$("#courseAndMaterialVersion").val(JSON.stringify(courseAndMaterialVersion));
				//$("#courseAndQuestionType").val(JSON.stringify(courseAndQuestionType));
				//$("#courseAndTypes").val(JSON.stringify(courseAndTypes));
				$("#courseAndMaterialVersion").val(courseAndMaterialVersion.toJSONString());
				$("#courseAndQuestionType").val(courseAndQuestionType.toJSONString());
				$("#courseAndTypes").val(courseAndTypes.toJSONString());
				$("form").submit();
				
			});
			
			function makeTeachingMaterialVersionStr(key){
				var teachingMaterialVersionInfoStr = "";
				for (var i = 0; i < teachingMaterialVersionInfoData.length; i++) {
					teachingMaterialVersionInfoStr = teachingMaterialVersionInfoStr + 
						"<input name=\"teachingMaterialVersion"+ key +"\" type=\"radio\" value=\""+ 
						teachingMaterialVersionInfoData[i].key +"\" />"+ teachingMaterialVersionInfoData[i].value +"<br />";
				}
				return teachingMaterialVersionInfoStr;
			}
			
			function makeQuestionTypeStr(key){
				var questionTypeStr = "";
				for (var i = 0; i < questionTypeData.length; i++) {
					questionTypeStr = questionTypeStr + 
						"<input name=\"questionType"+ key +"\" type=\"checkbox\" value=\""+ 
						questionTypeData[i].key +"\" />"+ questionTypeData[i].value +"<br />";
				}
				return questionTypeStr;
			}
			
			function makeTypeStr(key){
				var typeStr = "";
				for (var i = 0; i < typesInfoData.length; i++) {
					typeStr = typeStr + 
						"<input name=\"typesInfo"+ key +"\" type=\"checkbox\" value=\""+ 
						typesInfoData[i].key +"\" />"+ typesInfoData[i].value +"<br />";
				}
				return typeStr;
			}

			function doCheckTeachingMaterialVersion(selectCourseValue){
				$.each(courseAndMaterialVersion,function(i,n){  
					if(selectCourseValue==n.key){
						$("input[name=teachingMaterialVersion"+ selectCourseValue +"][value="+ n.value + "]").attr("checked",true);
					}
				});  
				

				$("input[name=teachingMaterialVersion"+ selectCourseValue +"]").click(function(){
					  var courseKey = $(this).attr("name").replace(/teachingMaterialVersion/,"");
					  var addFlag = true;
					  for(var i=0;i<courseAndMaterialVersion.length;i++){
						  if(courseAndMaterialVersion[i].key==courseKey){
							  courseAndMaterialVersion[i].value =  $(this).attr("value");
							  addFlag = false; 
						  };
					  }
					  
					  if(addFlag){
						  courseAndMaterialVersion.push({"key":courseKey,"value":$(this).attr("value")});
					  };
				});
			};
			
			function doCheckQuestionType(selectCourseValue){
				$.each(courseAndQuestionType,function(i,n){  
					if(selectCourseValue==n.key){
						$("input[name=questionType"+ selectCourseValue +"][value="+ n.value + "]").attr("checked",true);
					}
				});  
				
				$("input[name=questionType"+ selectCourseValue +"]").click(function(){
					  var courseKey = $(this).attr("name").replace(/questionType/,"");
					  var deleteFlag = !$(this).attr("checked");
					  var addFlag = true;
					  for(var i=0;i<courseAndQuestionType.length;i++){
						  if(courseAndQuestionType[i].key==courseKey && 
								  courseAndQuestionType[i].value == $(this).attr("value")){
							  if(deleteFlag){
								  courseAndQuestionType.splice(i,1);
								  addFlag = false;
								  break;
							  };
						  };
					  }
					  
					  if(addFlag){
						  courseAndQuestionType.push({"key":courseKey,"value":$(this).attr("value")});
					  };
				});
			};
			
			function doCheckTypes(selectCourseValue){
				$.each(courseAndTypes,function(i,n){  
					if(selectCourseValue==n.key){
						$("input[name=typesInfo"+ selectCourseValue +"][value="+ n.value + "]").attr("checked",true);
					}
				});  
				
				$("input[name=typesInfo"+ selectCourseValue +"]").click(function(){
					  var courseKey = $(this).attr("name").replace(/typesInfo/,"");
					  var deleteFlag = !$(this).attr("checked");
					  var addFlag = true;
					  for(var i=0;i<courseAndTypes.length;i++){
						  if(courseAndTypes[i].key==courseKey && 
								  courseAndTypes[i].value == $(this).attr("value")){
							  if(deleteFlag){
								  courseAndTypes.splice(i,1);
								  addFlag = false;
								  break;
							  };
						  };
					  }
					  
					  if(addFlag){
						  courseAndTypes.push({"key":courseKey,"value":$(this).attr("value")});
					  };
					  
				});
			};
		});
	</script>

	<div id="main">
		<jsp:include page="/WEB-INF/jsp/common/banner.jsp" />
		<br />
		<form id="mainform" method=post
			action="../admin/saveAttributeRelation" enctype="multipart/form-data">
			<table class="showtable" id="table">
				<tr>
					<th>科目</th>
					<th>教材版本</th>
					<th>题型</th>
					<th>类型</th>
					<th>提交操作</th>
				</tr>
			</table>
			<br /> <input type="hidden" value="" id="courseAndMaterialVersion"
				name="courseAndMaterialVersion" /> <input type="hidden" value=""
				id="courseAndQuestionType" name="courseAndQuestionType" />
				<input type="hidden" value=""
				id="courseAndTypes" name="courseAndTypes" />

		</form>

		<br />
		<jsp:include page="/WEB-INF/jsp/common/footer.jsp" />
	</div>
</body>
</html>
