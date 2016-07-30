<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ page errorPage="ExceptionHandler.jsp" %>
<%@page import="com.pxl.pkb.biz.Pub"%>
<%@page import="com.pxl.pkb.vo.doc_cate"%>
<%@page import="com.pxl.pkb.framework.DataManagerObject"%>
<%@page import="com.pxl.pkb.framework.ValueObject"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
String path = request.getContextPath();
String strDocCateID = request.getParameter("doccateid");
int docCateID = (strDocCateID==null||strDocCateID.trim().length()==0)?0:Integer.parseInt(strDocCateID);
if(Pub.isUnCheckUser(session)) {
	throw new Exception("您的用户尚未通过审核，不能进行新建文档！");
}
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<meta http-equiv="Pragma" content="no-cache">
<title>新建文档</title>
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
<script type="text/javascript">
<%
DataManagerObject dmo=new DataManagerObject();
ValueObject[] doc_catevos= dmo.queryAll(doc_cate.class);
for(int i=0;i<doc_catevos.length;i++) {
	doc_cate cate=(doc_cate)doc_catevos[i];
}

%>

function onDocAddFormCheck() {
	var count=0;
	var cate="";
	for(var i=0;i<cates.length;i++){
		if(cates[i].getChecked()==true){
			count++;
			cate=cate+cates[i].cateId+"&";
		}
	}
	if(count==0) {
		alert('请选择文档类别');
		return false;
	}
	if(frmDocAdd.docTitle.value=='') {
		alert('文档标题不能为空');
		return false;
	}
	if(frmDocAdd.attachCount.value==0) {
		alert('尚未上传文档不能保存文档');
		return false;
	}
	cate=cate.substr(0,cate.lastIndexOf("&"));
	document.getElementById("docCate").value=cate;
	return true;
}
</script>

<script type="text/javascript">
var swfu;

		window.onload = function() {
			var settings = {
				flash_url : "<%=path%>/swfupload/swfupload.swf",
				upload_url: "flashupload.do?cate=DOC",
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
</script>
</head>
<body class="body">
<%@ include file="header.jsp" %>
<table class="bodytable" align="center">
	<tr>
		<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td height="30"><a href="index.jsp">首页</a> &gt; <a href="doc_list.jsp">文档</a> &gt; 新建文档 </td>
				</tr>
			</table>
			
			<form id="frmDocAdd" name="frmDocAdd" action="DocAdd.do" method="post" onsubmit="return onDocAddFormCheck();"  enctype="multipart/form-data">
				<table width="900" border="0" cellpadding="2" cellspacing="2" class="blockborder">
					<tr>
						<td height="20" class="tableBackGround" colspan="2">&nbsp;<span class="blocktitle">新建文档</span></td>
				  	</tr>
			  		<tr>
			  			<td>
			  				<table border="0" cellpadding="0" cellspacing="0">
			  					<tr>
			  						<td width="180" class="title">&nbsp;文档类别</td>
			  						<td rowspan="2" valign="top">
			  							<input type="hidden" id="docCate" name="docCate"/>
			  							<table>
			  								<tr>
												<td height="20" align="center">文档标题</td>
												<td height="20"><input id="docTitle" type="text" name="docTitle" style="width: 300px;height: 15px;"></td>
										  	</tr>
										  	<tr>
												<td height="20" align="center" valign="top">文档简介</td>
												<td height="20"><textarea id="docIntro" name="docIntro" style="width: 350px;height: 150px;"></textarea>
												</td>
										  	</tr>
										  	<tr>
												<td height="20" align="center" valign="top">关键字</td>
												<td height="20"><input id="docKeyword" type="text" name="docKeyword" style="width: 300px;height: 15px;"></td>
										  	</tr>
										  	<tr>
												<td height="20" align="center" valign="top">文档上传</td>
												<td height="20">
												<table >
									            <tr><td><div id="uploadFilesDiv"></div><div class="fieldset flash" id="fsUploadProgress"></div></td> 
									            </tr>
									            <tr > <td colspan="2" ><div>
				    <span id="spanButtonPlaceHolder"></span>
				    <input id="btnCancel" type="button" value="取消所有上传" onclick="swfu.cancelQueue();" disabled="disabled" style="margin-left: 2px;  font-size: 8pt; height: 20px;" />
			</div></td></tr>			
												</table>
											
												</td>
										  	</tr>
										  	<tr>
												<td height="20" align="center" valign="top"></td>
												<td height="20"><input type="submit" value="提交文档"></td>
										  	</tr>  
			  							</table>
			  						</td>
			  					</tr>
			  					<tr>
			  						<td valign="top"><div class="typeTree"><%Pub.getCateTree(doc_catevos,out,"doc_cate");%></div></td>
			  					</tr>
			  				</table>
			  			</td>
			  		</tr>
				</table>
			</form>
		</td>
	</tr>
</table>
<%@ include file="footer.jsp" %>
</body>
</html>