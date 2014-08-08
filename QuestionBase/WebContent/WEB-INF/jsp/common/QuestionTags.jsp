<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/questionBase.tld " prefix="questionBase"%>
<script type="text/javascript">
	$(function() {
		$("#zhishidianButton")
				.click(
						function() {
							$("#zsdLink").attr("href", "../student/zsd?");
							var h = $("#zsdLink").attr("href");
							var courceCode = $("#kemu").val();
							if (courceCode == ""
									|| courceCode.indexOf(",") != -1) {
								alert("请选择科目");
								return;
							}
							var teachingMaterialVersion = $("#jiaocaibanben")
									.val();
							if (teachingMaterialVersion == ""
									|| teachingMaterialVersion.indexOf(",") != -1) {
								alert("请选择教材版本");
								return;
							}

							var year = $("#nianji").val();
							if (year == "" || year.indexOf(",") != -1) {
								alert("请选择年级");
								return;
							}

							var schoolCode = $("#jigoumingcheng").val();
							if (schoolCode == ""
									|| schoolCode.indexOf(",") != -1) {
								alert("机构名称");
								return;
							}
							h = h + "&courceCode=" + courceCode
									+ "&teachingMaterialVersion="
									+ teachingMaterialVersion + "&year=" + year
									+ "&schoolCode=" + schoolCode;
							h = h
									+ "&keepThis=true&TB_iframe=true&height=300&width=500";
							$("#zsdLink").attr("href", h);
							$("#zsdLink").click();
						});
		$("#zhangjieButton")
				.click(
						function() {
							$("#zhangjieLink").attr("href",
									"../student/zhangjie?");
							var h = $("#zhangjieLink").attr("href");
							var courceCode = $("#kemu").val();
							if (courceCode == ""
									|| courceCode.indexOf(",") != -1) {
								alert("请选择科目");
								return;
							}
							var teachingMaterialVersion = $("#jiaocaibanben")
									.val();
							if (teachingMaterialVersion == ""
									|| teachingMaterialVersion.indexOf(",") != -1) {
								alert("请选择教材版本");
								return;
							}

							var year = $("#nianji").val();
							if (year == "" || year.indexOf(",") != -1) {
								alert("请选择年级");
								return;
							}

							var schoolCode = $("#jigoumingcheng").val();
							if (schoolCode == ""
									|| schoolCode.indexOf(",") != -1) {
								alert("机构名称");
								return;
							}
							h = h + "&courceCode=" + courceCode
									+ "&teachingMaterialVersion="
									+ teachingMaterialVersion + "&year=" + year
									+ "&schoolCode=" + schoolCode;
							h = h
									+ "&keepThis=true&TB_iframe=true&height=300&width=500";
							$("#zhangjieLink").attr("href", h);
							$("#zhangjieLink").click();
						});
		$(".kemu").each(function(){
			var count = $(this).attr("id").replace(/kemu/,"");
			var selectValue = $("#kemu"+count).children('option:selected').val(); 
			var orgId = $("#jigoumingcheng"+count).children('option:selected').val();
			initTMV("jiaocaibanben"+count,selectValue,orgId);
			initQuestionType("tixing"+count,selectValue,orgId);
			initTypeInfo("leixing"+count,selectValue,orgId);
		});
		
		
		$(".kemu").click(function(){
			var count = $(this).attr("id").replace(/kemu/,"");
			var selectValue = $("#kemu"+count).children('option:selected').val(); 
			var orgId = $("#jigoumingcheng"+count).children('option:selected').val();
			initTMV("jiaocaibanben"+count,selectValue,orgId);
			initQuestionType("tixing"+count,selectValue,orgId);
			initTypeInfo("leixing"+count,selectValue,orgId);
		});
		
		$(".jigoumingcheng").click(function(){
			var count = $(this).attr("id").replace(/jigoumingcheng/,"");
			var selectValue = $("#kemu"+count).children('option:selected').val(); 
			var orgId = $("#jigoumingcheng"+count).children('option:selected').val();
			initTMV("jiaocaibanben"+count,selectValue,orgId);
			initQuestionType("tixing"+count,selectValue,orgId);
			initTypeInfo("leixing"+count,selectValue,orgId);
		});
		
		function initTypeInfo(id,selectValue,orgId){
			var orgids = orgId.split(",");
			var selectValues = selectValue.split(",");
			var keyList = new Array();
			var keyListIndex = 0;
			$("#" + id + " option").remove();
			for(var i=0;i<orgids.length;i++){
				for(var j=0;j<typesJson.length;j++){
					if(typesJson[j].key == orgids[i]){
						for(var k=0;k<typesJson[j].value.length;k++){
							for(var m=0;m<selectValues.length;m++){
								if(selectValues[m]==typesJson[j].value[k].key){
									if(typesJson[j].value[k].value!=""){
										for(var n=0;n<typesJson[j].value[k].value.length;n++){
											if(!contains(keyList,typesJson[j].value[k].value[n].key)){
												keyList[keyListIndex] = typesJson[j].value[k].value[n].key;
												keyListIndex++;
												$("#" + id).append("<option value=" + typesJson[j].value[k].value[n].key +">" + typesJson[j].value[k].value[n].value +"</option>");  
											}
										}
									}										
								}
							}
						}
					}			
				}
			}
			var optionValue = "";
			for(var i=0;i<keyList.length;i++){
				optionValue = optionValue + keyList[i] + ",";
			}
			
			if(optionValue.length>0){
				optionValue = optionValue.substring(0, optionValue.length-1);
			}
			
			$("#" + id).prepend("<option value=\"" + optionValue + "\"> 全部类型</option>");
		}
		
		function initQuestionType(id,selectValue,orgId){
			var orgids = orgId.split(",");
			var selectValues = selectValue.split(",");
			var keyList = new Array();
			var keyListIndex = 0;
			$("#" + id + " option").remove();
			for(var i=0;i<orgids.length;i++){
				for(var j=0;j<questionTypeJson.length;j++){
					if(questionTypeJson[j].key == orgids[i]){
						for(var k=0;k<questionTypeJson[j].value.length;k++){
							for(var m=0;m<selectValues.length;m++){
								if(selectValues[m]==questionTypeJson[j].value[k].key){
									if(questionTypeJson[j].value[k].value!=""){
										for(var n=0;n<questionTypeJson[j].value[k].value.length;n++){
											if(!contains(keyList,questionTypeJson[j].value[k].value[n].key)){
												keyList[keyListIndex] = questionTypeJson[j].value[k].value[n].key;
												keyListIndex++;
												$("#" + id).append("<option value=" + questionTypeJson[j].value[k].value[n].key +">" + questionTypeJson[j].value[k].value[n].value +"</option>");  
											}
										}
									}										
								}
							}
						}
					}			
				}
			}
			
			var optionValue = "";
			for(var i=0;i<keyList.length;i++){
				optionValue = optionValue + keyList[i] + ",";
			}
			
			if(optionValue.length>0){
				optionValue = optionValue.substring(0, optionValue.length-1);
			}
			
			$("#" + id).prepend("<option value=\"" + optionValue + "\"> 全部题型</option>");
		}
		
		function initTMV(id,selectValue,orgId){
			var orgids = orgId.split(",");
			var selectValues = selectValue.split(",");
			var keyList = new Array();
			var keyListIndex = 0;
			$("#" + id + " option").remove();
			for(var i=0;i<orgids.length;i++){
				for(var j=0;j<tmvJson.length;j++){
					if(tmvJson[j].key == orgids[i]){
						for(var k=0;k<tmvJson[j].value.length;k++){
							for(var m=0;m<selectValues.length;m++){
								if(selectValues[m]==tmvJson[j].value[k].key){
									if(tmvJson[j].value[k].value!=""){
										if(!contains(keyList,tmvJson[j].value[k].value[0].key)){
											keyList[keyListIndex] = tmvJson[j].value[k].value[0].key;
											keyListIndex++;
											$("#" + id).append("<option value=" + tmvJson[j].value[k].value[0].key +">" + tmvJson[j].value[k].value[0].value +"</option>");  
										}
									}										
								}
							}
						}
					}			
				}
			}
			
			var optionValue = "";
			for(var i=0;i<keyList.length;i++){
				optionValue = optionValue + keyList[i] + ",";
			}
			
			if(optionValue.length>0){
				optionValue = optionValue.substring(0, optionValue.length-1);
			}
			
			$("#" + id).prepend("<option value=\"" + optionValue + "\"> 全部版本</option>");
			
			
			$("#" + id + " option[value='"+  $("#" + id).val() +"']").attr("selected", true);
		}
		
		function contains(a, obj) {
	        for (var i = 0; i < a.length; i++) {
	            if (a[i] === obj) {
	                return true;
	            }
	        }
	        return false;
	    }
	});
</script>
<questionBase:AllAttributeJson />
<div>
	<a
		href="../student/zsd?keepThis=true&TB_iframe=true&height=300&width=500"
		id="zsdLink" title="知识点" class="thickbox"></a> <a
		href="../student/zhangjie?keepThis=true&TB_iframe=true&height=300&width=500"
		id="zhangjieLink" title="章节" class="thickbox"></a>
</div>