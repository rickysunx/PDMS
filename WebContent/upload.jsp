<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ page errorPage="ExceptionHandler.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
String path = request.getContextPath();
String cate = request.getParameter("cate");
cate = (request.getAttribute("cate")!=null)?((String)request.getAttribute("cate")):cate;
%>
<%@page import="com.pxl.pkb.vo.sys_attach"%>
<%@page import="com.pxl.pkb.biz.Pub"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<meta http-equiv="Pragma" content="no-cache">
<title>upload</title>
<script type="text/javascript" src="<%=path%>/js/swfupload.js" ></script>
<script type="text/javascript" src="<%=path%>/js/queue.js"></script>
<script type="text/javascript" src="<%=path%>/js/fileprogress.js" ></script>
<script type="text/javascript" src="<%=path%>/js/handlers.js" charset="utf-8"></script>
<style type="text/css">
body {
	margin: 0px;
	padding: 2px;
	font-size: 12px;
}
a:link {
	font-size: 12px;
	color: #022040;
	text-decoration: none;
}
a:visited {
	font-size: 12px;
	color: #022040;
	text-decoration: none;
}
a:active {
	font-size: 12px;
	color: #022040;
	text-decoration: none;
}
a:hover {
	font-size: 12px;
	color: #8C0000;
	text-decoration:underline
}

.fileinput {
	font-size: 12px;
	height: 20px;
	width: 200px;
	background-color: #F0F0F0;
	border: 1px solid #666666;
}
.filesubmit {
	height: 20px;
	width: 50px;
	background-color: #F0F0F0;
	border: 1px solid #666666;
}
</style>
<%
  if(cate.equals("DOC")){
%>
<script type="text/javascript">
var swfu;

		window.onload = function() {
			var settings = {
				flash_url : "<%=path%>/swfupload/swfupload.swf",
				upload_url: "flashupload.do?cate=<%=cate%>",
				post_params: {"PHPSESSID" : "session_id();"},
				file_size_limit : "100 MB",
				file_types : "*.*",
				file_types_description : "All Files",
				file_upload_limit : 1,
				file_queue_limit : 0,
				custom_settings : {
					progressTarget : "fsUploadProgress",
					cancelButtonId : "btnCancel"
				},
				debug: false,

				// Button settings
				button_image_url: "",	// Relative to the Flash file
				button_width: "65",
				button_height: "29",
				button_placeholder_id: "spanButtonPlaceHolder",
				button_text: '<span >浏览</span>',
				button_text_style: ".theFont { font-size: 10; }",
				button_text_left_padding: 12,
				button_text_top_padding: 3,
				
				// The event handler functions are defined in handlers.js
				file_queued_handler : fileQueued,
				file_queue_error_handler : fileQueueError,
				file_dialog_complete_handler : fileDialogComplete,
				upload_start_handler : uploadStart,
				upload_progress_handler : uploadProgress,
				upload_error_handler : uploadError,
				upload_success_handler : uploadSuccess,
				upload_complete_handler : uploadComplete,
				queue_complete_handler : queueComplete	// Queue plugin event
			};

			swfu = new SWFUpload(settings);
	     };

function onCheckForm() {
	if(frmFile.myfile.value=='') {
		alert('请选择文件后再上传');
		return false;
	}
	return true;
}
function onclickqutoe(){
   swfu.stopUpload();
}
</script>


<%
  }
  if(cate.equals("QST")){
	  %>
	<script type="text/javascript">
var swfu;

		window.onload = function() {
			var settings = {
				flash_url : "<%=path%>/swfupload/swfupload.swf",
				upload_url: "flashupload.do?cate=<%=cate%>",
				post_params: {"PHPSESSID" : "session_id();"},
				file_size_limit : "100 MB",
				file_types : "*.*",
				file_types_description : "All Files",
				file_upload_limit : 100,
				file_queue_limit : 0,
				custom_settings : {
					progressTarget : "fsUploadProgress",
					cancelButtonId : "btnCancel"
				},
				debug: false,

				// Button settings
				button_image_url: "",	// Relative to the Flash file
				button_width: "65",
				button_height: "29",
				button_placeholder_id: "spanButtonPlaceHolder",
				button_text: '<span >浏览</span>',
				button_text_style: ".theFont { font-size: 10; }",
				button_text_left_padding: 12,
				button_text_top_padding: 3,
				
				// The event handler functions are defined in handlers.js
				file_queued_handler : fileQueued,
				file_queue_error_handler : fileQueueError,
				file_dialog_complete_handler : fileDialogComplete,
				upload_start_handler : uploadStart,
				upload_progress_handler : uploadProgress,
				upload_error_handler : uploadError,
				upload_success_handler : uploadSuccess,
				upload_complete_handler : uploadComplete,
				queue_complete_handler : queueComplete	// Queue plugin event
			};

			swfu = new SWFUpload(settings);
	     };

function onCheckForm() {
	if(frmFile.myfile.value=='') {
		alert('请选择文件后再上传');
		return false;
	}
	return true;
}
function onclickqutoe(){
   swfu.stopUpload();
}
</script>  
	  
	  <%
  }
%>

</head>
<body>
<form  method="post" action="upload.do?cate=<%=cate%>" onsubmit="return onCheckForm();" enctype="multipart/form-data" id="frmFile">
<!-- 
<input id="myfile" type="file" name="myfile" class="fileinput">
<input type="submit" class="filesubmit" id="mySubmit" value="上传">
 -->
<div class="fieldset flash" id="fsUploadProgress">
	  </div>
		<div id="divStatus"></div>
			<div>
				<span id="spanButtonPlaceHolder"></span>
				<input id="btnCancel" type="button" value="取消所有上传" onclick="swfu.cancelQueue();" disabled="disabled" style="margin-left: 2px; font-size: 8pt; height: 20px;" />
			</div>

</form>
</body>
</html>