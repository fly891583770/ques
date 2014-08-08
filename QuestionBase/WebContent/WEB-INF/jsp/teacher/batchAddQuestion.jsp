<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="questionBase" uri="/WEB-INF/questionBase.tld "%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>登陆试题</title>
<link rel="stylesheet" type="text/css"
	href="../../QuestionBase/css/site.css" />
</head>

<body>
<jsp:include page="/WEB-INF/jsp/common/header.jsp" />
<jsp:include page="/WEB-INF/jsp/common/QuestionTags.jsp" />
<script type="text/javascript">
		var rowid = "1";
		
		$(function() {
			
			AddClickEvent();
			
			$("#batchAdd").click(function() {
				var v = false;
				
				$("#table tr:gt(0)").each(function () {
					v = false;
					
			        var nodes = $(this)[0].childNodes;
			        kemu = nodes[1].childNodes[0].value;
			        if(contains(kemu,",",false)){
			        	alert("请选择科目");
			        	nodes[1].childNodes[0].focus();
			        	return false;
			        }
			        
			        jiaocaibanben = nodes[2].childNodes[0].value;
			        if(contains(jiaocaibanben,",",false)){
			        	alert("请选择教材版本");
			        	nodes[2].childNodes[0].focus();
			        	return false;
			        }
			        
			        nianji = nodes[3].childNodes[0].value;
			        if(contains(nianji,",",false)){
			        	alert("请选择年级");
			        	nodes[3].childNodes[0].focus();
			        	return false;
			        }
			        
			        nandu = nodes[4].childNodes[0].value;
			        if(contains(nandu,",",false)){
			        	alert("请选择难度");
			        	nodes[4].childNodes[0].focus();
			        	return false;
			        }
			        
			        tixing = nodes[5].childNodes[0].value;
			        if(contains(tixing,",",false)){
			        	alert("请选题型");
			        	nodes[5].childNodes[0].focus();
			        	return false;
			        }
			        
			        leixing = nodes[6].childNodes[0].value;
			        if(contains(leixing,",",false)){
			        	alert("请选类型");
			        	nodes[6].childNodes[0].focus();
			        	return false;
			        }
			        
			        jigoumingcheng = nodes[7].childNodes[0].value;
			        if(contains(jigoumingcheng,",",false)){
			        	alert("请选机构名称");
			        	nodes[7].childNodes[0].focus();
			        	return false;
			        }
			        zhaiyao = nodes[10].childNodes[0].value;
			        if(contains(jigoumingcheng,",",false)){
			        	alert("请写摘要");
			        	nodes[10].childNodes[0].focus();
			        	return false;
			        }
			        v = true;
			 	});
				if(v){
					alert("Begin UpLoad");
					$("form").submit();
				}
			});
			
		});
		
		function contains(string,substr,isIgnoreCase)
		{
			if(isIgnoreCase){
				string=string.toLowerCase();
				substr=substr.toLowerCase();
			}
			var startChar=substr.substring(0,1);
			var strLen=substr.length;
			for(var j=0;j<string.length-strLen+1;j++){
				if(string.charAt(j)==startChar){//如果匹配起始字符,开始查找				
					if(string.substring(j,j+strLen)==substr){//如果从j开始的字符与str匹配，那ok
					
						return true;
					}  
				}
			}
			return false;
		}
		
		function AddClickEvent(){
			$("#zhishidianButton" + rowid)			 
			.click(
					function() {
						var num = $(this).prevAll()[1].id.replace("zhishidian", "").replace("Area", "");
						$("#zsdLink").attr("href", "../student/zsd?");
						var h = $("#zsdLink").attr("href");
						var courceCode = $("#kemu" + num).val();
						if (courceCode == "" || courceCode.indexOf(",")!=-1) {
							alert("请选择科目");
							return;
						}
						var teachingMaterialVersion = $(
								"#jiaocaibanben"+ num).val();
						if (teachingMaterialVersion == "" || teachingMaterialVersion.indexOf(",")!=-1) {
							alert("请选择教材版本");
							return;
						}

						var year = $("#nianji"+ num).val();
						if (year == ""|| year.indexOf(",")!=-1) {
							alert("请选择年级");
							return;
						}

						var schoolCode = $("#jigoumingcheng"+ num).val();
						if (schoolCode == ""|| schoolCode.indexOf(",")!=-1) {
							alert("请选择机构名称");
							return;
						}
						h = h + "&courceCode=" + courceCode
								+ "&teachingMaterialVersion="
								+ teachingMaterialVersion + "&year="
								+ year + "&schoolCode=" + schoolCode;
						h = h + "&num=" + num;
						h = h
								+ "&keepThis=true&TB_iframe=true&height=300&width=500";
						$("#zsdLink").attr("href", h);
						$("#zsdLink").click();
					});
	$("#zhangjieButton" + rowid)
			.click(
					function() {
						var num = $(this).prevAll()[1].id.replace("zhangjie", "").replace("Area", "");
						$("#zhangjieLink").attr("href",
								"../student/zhangjie?");
						var h = $("#zhangjieLink").attr("href");
						var courceCode = $("#kemu"+ num).val();
						if (courceCode == "" || courceCode.indexOf(",")!=-1) {
							alert("请选择科目");
							return;
						}
						var teachingMaterialVersion = $(
								"#jiaocaibanben"+ num).val();
						if (teachingMaterialVersion == "" || teachingMaterialVersion.indexOf(",")!=-1) {
							alert("请选择教材版本");
							return;
						}

						var year = $("#nianji"+ num).val();
						if (year == ""|| year.indexOf(",")!=-1) {
							alert("请选择年级");
							return;
						}

						var schoolCode = $("#jigoumingcheng"+ num).val();
						if (schoolCode == ""|| schoolCode.indexOf(",")!=-1) {
							alert("请选择机构名称");
							return;
						}
						h = h + "&courceCode=" + courceCode
								+ "&teachingMaterialVersion="
								+ teachingMaterialVersion + "&year="
								+ year + "&schoolCode=" + schoolCode;
						h = h + "&num=" + num;
						h = h
								+ "&keepThis=true&TB_iframe=true&height=300&width=500";
						$("#zhangjieLink").attr("href", h);
						$("#zhangjieLink").click();
					});
	$("#del" + rowid)	
				.click(
					function() {
						var vbtnDel=$(this);
						var vTr=vbtnDel.parent("td").parent("tr");
						vTr.remove(); 
					});
		}

		 function createUploadIframe(id, uri) {
		        //create frame
		        var frameId = 'jUploadFrame' + id;
		        var iframeHtml = '<iframe id="' + frameId + '" name="' + frameId + '" style="position:absolute; top:-9999px; left:-9999px"';
		        if (window.ActiveXObject) {
		            if (typeof uri == 'boolean') {
		                iframeHtml += ' src="' + 'javascript:false' + '"';

		            }
		            else if (typeof uri == 'string') {
		                iframeHtml += ' src="' + uri + '"';

		            }
		        }
		        iframeHtml += ' />';
		        jQuery(iframeHtml).appendTo(document.body);

		        return jQuery('#' + frameId).get(0);
		    }
		    function createUploadForm (id, fileElementId, data) {
		        //create form	
		        var formId = 'jUploadForm' + id;
		        var fileId = 'jUploadFile' + id;
		        var form = jQuery('<form  action="" method="POST" name="' + formId + '" id="' + formId + '" enctype="multipart/form-data"></form>');
		        var oldElement = jQuery('#' + fileElementId);
		        var newElement = jQuery(oldElement).clone();
		        jQuery(oldElement).attr('id', fileId);
		        jQuery(oldElement).before(newElement);
		        jQuery(oldElement).appendTo(form);

		        if (data) {   
		            for (var i in data) {   
		                $('<input type="hidden" name="' + i + '" value="' + data[i] + '" />').appendTo(form);   
		            }   
		        }  

		        //set attributes
		        jQuery(form).css('position', 'absolute');
		        jQuery(form).css('top', '-1200px');
		        jQuery(form).css('left', '-1200px');
		        jQuery(form).appendTo('body');
		        return form;
		    }

		    function ajaxFileUpload (s) {
		        // TODO introduce global settings, allowing the client to modify them for all requests, not only timeout		
		        s = jQuery.extend({}, jQuery.ajaxSettings, s);
		        var id = new Date().getTime()
		        var form = createUploadForm(id, s.fileElementId, (typeof (s.data) == 'undefined' ? false : s.data));
		        var io = createUploadIframe(id, s.secureuri);
		        var frameId = 'jUploadFrame' + id;
		        var formId = 'jUploadForm' + id;
		        // Watch for a new set of requests
		        if (s.global && !jQuery.active++) {
		            jQuery.event.trigger("ajaxStart");
		        }
		        var requestDone = false;
		        // Create the request object
		        var xml = {}
		        if (s.global)
		            jQuery.event.trigger("ajaxSend", [xml, s]);
		        // Wait for a response to come back
		        var uploadCallback = function (isTimeout) {
		            var io = document.getElementById(frameId);
		            try {
		                if (io.contentWindow) {
		                    xml.responseText = io.contentWindow.document.body ? io.contentWindow.document.body.innerHTML : null;
		                    xml.responseXML = io.contentWindow.document.XMLDocument ? io.contentWindow.document.XMLDocument : io.contentWindow.document;

		                } else if (io.contentDocument) {
		                    xml.responseText = io.contentDocument.document.body ? io.contentDocument.document.body.innerHTML : null;
		                    xml.responseXML = io.contentDocument.document.XMLDocument ? io.contentDocument.document.XMLDocument : io.contentDocument.document;
		                }
		            } catch (e) {
		                handleError(s, xml, null, e);
		            }
		            if (xml || isTimeout == "timeout") {
		                requestDone = true;
		                var status;
		                try {
		                    status = isTimeout != "timeout" ? "success" : "error";
		                    // Make sure that the request was successful or notmodified
		                    if (status != "error") {
		                        // process the data (runs the xml through httpData regardless of callback)
		                        var data = uploadHttpData(xml, s.dataType);
		                        // If a local callback was specified, fire it and pass it the data
		                        if (s.success)
		                            s.success(data, status);

		                        // Fire the global callback
		                        if (s.global)
		                            jQuery.event.trigger("ajaxSuccess", [xml, s]);
		                    } else
		                        handleError(s, xml, status);
		                } catch (e) {
		                    status = "error";
		                    handleError(s, xml, status, e);
		                }

		                // The request was completed
		                if (s.global)
		                    jQuery.event.trigger("ajaxComplete", [xml, s]);

		                // Handle the global AJAX counter
		                if (s.global && ! --jQuery.active)
		                    jQuery.event.trigger("ajaxStop");

		                // Process result
		                if (s.complete)
		                    s.complete(xml, status);

		                jQuery(io).unbind()

		                setTimeout(function () {
		                    try {
		                        jQuery(io).remove();
		                        jQuery(form).remove();

		                    } catch (e) {
		                        handleError(s, xml, null, e);
		                    }

		                }, 100)

		                xml = null

		            }
		        }
		        // Timeout checker
		        if (s.timeout > 0) {
		            setTimeout(function () {
		                // Check to see if the request is still happening
		                if (!requestDone) uploadCallback("timeout");
		            }, s.timeout);
		        }
		        try {

		            var form = jQuery('#' + formId);
		            jQuery(form).attr('action', s.url);
		            jQuery(form).attr('method', 'POST');
		            jQuery(form).attr('target', frameId);
		            if (form.encoding) {
		                jQuery(form).attr('encoding', 'multipart/form-data');
		            }
		            else {
		                jQuery(form).attr('enctype', 'multipart/form-data');
		            }
		            jQuery(form).submit();

		        } catch (e) {
		            handleError(s, xml, null, e);
		        }

		        jQuery('#' + frameId).load(uploadCallback);
		        return { abort: function () { } };

		    }
		   function  handleError (s, xhr, status, e) {
		        // If a local callback was specified, fire it  
		        if (s.error) {
		            s.error.call(s.context || window, xhr, status, e);
		        }
		        // Fire the global callback  
		        if (s.global) {
		            (s.context ? jQuery(s.context) : jQuery.event).trigger("ajaxError", [xhr, s, e]);
		        }
		    }

		   function uploadHttpData (r, type) {
		        var data = !type;
		        data = type == "xml" || data ? r.responseXML : r.responseText;
		        // If the type is "script", eval it in global context
		        if (type == "script")
		            jQuery.globalEval(data);
		        // Get the JavaScript object, if JSON is used.
		        if (type == "json")
		            eval("data = " + data);
//		            data = eval("(" + data.replace("<pre>","").replace("</pre>","") + ")");
		        // evaluate scripts within html
		        if (type == "html")
		            jQuery("<div>").html(data).evalScripts();

		        return data;
		    }
		   
		   function addRow(){
			   if(rowid==""){
				   rowid = 1;
			   }
			   rowid = Number(rowid) + 1;
			   var tr = $("#table tr").eq(1).clone();
			   tr.find("td").each(function (index) {
				   if(index==0){
					   var noTd = rowid + "<br><input type=\"button\" value=\"追加一行\" onclick=\"addRow();\"/><input type=\"button\" id=\"del" + rowid +"\" value=\"删除本行\"/>";
					   $(this).html(noTd);
				   }
				   if(index>=1 && index<=7){
					   var selectNode = $(this).children("select");
					   var oldId = selectNode.attr("id");
					   var newId = deleteNum(oldId);
					   selectNode.attr("id",newId + rowid);
				   }
				   
				   if(index==8 || index==9){
					   $(this).children("input").each(function(){
						   var oldinputId = $(this).attr("id");
						   var newinputId = deleteNum(oldinputId);
						   $(this).attr("id",newinputId + rowid);
					   });
	
					   var textareaNode = $(this).children("textarea");
					   var oldtextareaId = textareaNode.attr("id");
					   var newtextareaId = deleteNum(oldtextareaId);
					   textareaNode.attr("id",newtextareaId + rowid + "Area");
				   }
				   
				   if(index>=10 && index<=12){
					   var inputNode = $(this).children("input");
					   var oldId = inputNode.attr("id");
					   var newId = deleteNum(oldId);
					   inputNode.attr("id",newId + rowid);
				   }
	            });
			   tr.appendTo("#table"); 
			   
			   AddClickEvent();
		   }
		   
			
			function deleteNum(string)
			{
				for(var j=0;j<string.length;j++){
					var subStr = string.charAt(j);
					
					if(!isNaN(subStr)){
						return string.substr(0,j);
					}
					
				}
				return string;
			}		
			
		
	</script>
	
	<div id="main">
		<jsp:include page="/WEB-INF/jsp/common/banner.jsp" />
		 <br/>
		<form id="mainform" method=post action="../teacher/saveBatchQuestion" enctype="multipart/form-data">
		<div  style="overflow-y:auto; width:100%">
		<table class="showtable" id="table">
			<tr width=100%>
			    <th>No.</th>
				<th>科目</th>
				<th>教材版本</th>
				<th>年级</th>
				<th>难度</th>
				<th>题型</th>
				<th>类型</th>
				<th>机构名称</th>
				<th>知识点</th>
				<th>章节</th>
				<th>摘要</th>
				<th>题干</th>
				<th>答案</th>
			</tr>
			<tr width=100%>
			    <td>1<br><input type="button" value="追加一行" onclick="addRow();"/><input type="button" id="del" value="删除本行"/></td>
				<td><questionBase:kemu id="kemu1" name="kemu" /></td>
				<td><questionBase:jiaocaibanben id="jiaocaibanben1" name="jiaocaibanben" /></td>
				<td><questionBase:nianji id="nianji1" name="nianji" /></td>
				<td><questionBase:nandu value="question" id="nandu1" name="nandu"/></td>
				<td><questionBase:tixing value="question" id="tixing1" name="tixing" /></td>
				<td><questionBase:leixing value="question" id="leixing1" name="leixing"/></td>
				<td><questionBase:jigoumingcheng id="jigoumingcheng1" name="jigoumingcheng" /></td>
				<td><questionBase:zhishidian value="question" id="zhishidian1" name="zhishidian"  /></td>
				<td><questionBase:zhangjie value="question" id="zhangjie1" name="zhangjie" /></td>
				<td><input type="text" name="summary" id="summary1"/></td>
				<td><input type="file" name="tiGanFile" id="tiGanFile1"/></td>
				<td><input type="file" name="daAnFile" id="daAnFile1"/></td>
			</tr>
		</table>
		</div>
		<br>
		<a href="../student/zsd?keepThis=true&TB_iframe=true&height=300&width=500"	id="zsdLink" title="知识点" class="thickbox"></a>
	    <a href="../student/zhangjie?keepThis=true&TB_iframe=true&height=300&width=500" id="zhangjieLink" title="章节" class="thickbox"></a>
	    <input type="button" value="批量提交" id="batchAdd"/>
	    
	    </form> 
	    
	    <br/>
		<jsp:include page="/WEB-INF/jsp/common/footer.jsp" />
		</div>
</body>
</html>