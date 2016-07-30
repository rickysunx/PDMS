<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ page errorPage="ExceptionHandler.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.pxl.pkb.biz.Pub"%>
<%@page import="com.pxl.pkb.framework.DataManagerObject"%>
<%@page import="com.pxl.pkb.framework.ValueObject"%>
<%@page import="com.pxl.pkb.vo.ask_cate"%>
<%
String path = request.getContextPath();
String strAskCateID = request.getParameter("askcateid");
int askCateID = (strAskCateID==null||strAskCateID.trim().length()==0)?0:Integer.parseInt(strAskCateID);
if(Pub.isUnCheckUser(session)) {
	throw new Exception("您的用户尚未通过审核，不能进行提问！");
}
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<meta http-equiv="Pragma" content="no-cache">
<title>增加问题</title>
<link href="main.css" rel="stylesheet" type="text/css">
<link href="css/color.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="upload.js"></script>
<link href="xtree/xtree.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="xtree/xtree.js"></script>
<script type="text/javascript" src="xtree/webfxcheckboxtreeitem.js"></script>
<script type="text/javascript" src="ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="<%=path%>/js/swfupload.js" ></script>
<script type="text/javascript" src="<%=path%>/js/queue.js"></script>
<script type="text/javascript" src="<%=path%>/js/fileprogress.js" ></script>
<script type="text/javascript" src="<%=path%>/js/handlers.js" charset="utf-8"></script>
<link href="<%=path%>/css/default.css" rel="stylesheet" type="text/css" />
<style type="text/css">
*{margin:0;padding:0;}
</style>
</head>
<body class="body">
<script type="text/javascript">
<%
DataManagerObject dmo=new DataManagerObject();
ValueObject[] ask_catevos= dmo.queryAll(ask_cate.class);
%>
function onQstAddCheck() {
	var count=0;
	var cate="";
	var editor_data = CKEDITOR.instances.qstContent.getData();
	for(var i=0;i<cates.length;i++){
		if(cates[i].getChecked()==true){
			count++;
			cate=cate+cates[i].cateId+"&";
		}
	}
	if(count==0) {
		alert('请选择问题类别');
		return false;
	}
	if(frmQstAdd.qstTitle.value.length==0) {
		alert('标题不能为空');
		return false;
	}
	if(editor_data.length==0) {
		alert('内容不能为空');
		return false;
	}
	cate=cate.substr(0,cate.lastIndexOf("&"));
	document.getElementById("askCate").value=cate;
	return true;
}

</script>
<script type="text/javascript">
var swfu;

		window.onload = function() {
			var settings = {
				flash_url : "<%=path%>/swfupload/swfupload.swf",
				upload_url: "flashupload.do?cate=QST",
				post_params: {"PHPSESSID" : "session_id();"},
				file_size_limit : "100 MB",
				file_types : "*.*",
				file_types_description : "All Files",
				file_upload_limit : 1000,
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
				button_text: '<span >浏览 </span>',
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
</script>
<%@ include file="header.jsp"%>
	<table class="bodytable" align="center"><tr><td>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
	<td height="30"><a href="index.jsp">首页</a> &gt; <a href="ask_list.jsp">问答</a> &gt; 增加问题 </td>
	</tr>
	</table>
	
	<table width="900" height="460" border="0" cellpadding="2" cellspacing="2" class="blockborder">
	  <tr>
		<td height="20" class="tableBackGround" colspan="3">&nbsp;<span class="blocktitle">增加问题</span></td>
	  </tr>  
	  <tr>
	  	<td>
			<form id="frmQstAdd" name="frmQstAdd" action="QstAdd.do" method="post" onsubmit="return onQstAddCheck();">
				<table border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td  class="title">问题类别</td>
						<td rowspan="2">
							<table border="0" cellpadding="2" cellspacing="2">
								<tr>
									<td colspan="3"><input type="hidden" id="askCate" name="askCate"/></td>
								</tr>
								<tr>
									<td height="20" align="center" width="50">问题标题</td>
									<td height="20" width="320px"><input id="qstTitle" type="text" name="qstTitle" style="width: 300px;height: 15px;"></td>
									<td class="helpRequired">请输入问题的标题</td>
								</tr>
								<tr>
									<td height="20" align="center" valign="top">问题内容</td>
									<td><textarea id="qstContent" name="qstContent" style="width: 380px;height: 200px;"></textarea>
										<script type="text/javascript">
											CKEDITOR.replace('qstContent');
										</script>
									</td>
									<td class="helpRequired">请输入您要提问的详细内容</td>
								</tr>
								<tr>
									<td height="20" align="center" valign="top">关键字</td>
									<td height="20"><input id="qstKeyword" type="text" name="qstKeyword" style="width: 300px;height: 15px;"></td>
									<td class="helpRequired">请输入您要提问的关键字</td>
								</tr>
								<tr>
									<td height="20" align="center" valign="top">附件</td>
									<td height="20">
							     <div class="fieldset flash" id="fsUploadProgress"></div><div>
				    <span id="spanButtonPlaceHolder"></span>
				    <input id="btnCancel" type="button" value="取消所有上传" onclick="swfu.cancelQueue();" disabled="disabled" style="margin-left: 2px; font-size: 8pt; height: 20px;" />
			</div>
</td>
									<td class="helpRequired">请选择您要上传的附件并上传，如果没有可不选</td>
								</tr>
								<tr>
									<td height="20" align="center" valign="top"></td>
									<td height="20" colspan="2"><input type="submit" value="提交问题"/></td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td valign="top"><div class="typeTree"><%Pub.getCateTree(ask_catevos,out,"ask_cate");%></div></td>
					</tr>
				</table>
				<div id="uploadFilesDiv"></div>
			</form>
	  	</td>
	  </tr>  
	</table>
	</td></tr></table>
	
<%@ include file="footer.jsp" %>
</body>
</html>