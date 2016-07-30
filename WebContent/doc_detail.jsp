<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ page errorPage="ExceptionHandler.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.pxl.pkb.framework.DataManagerObject"%>
<%@page import="com.pxl.pkb.framework.ValueObject"%>
<%@page import="com.pxl.pkb.vo.doc_doc"%>
<%@page import="com.pxl.pkb.biz.Doc"%>
<%@page import="com.pxl.pkb.vo.doc_cate"%>
<%@page import="com.pxl.pkb.biz.Pub"%>
<%@page import="com.pxl.pkb.biz.User"%>
<%@page import="com.pxl.pkb.biz.Consts"%>
<%
String strDocID = request.getParameter("docid");
int docid = Integer.parseInt(strDocID);
DataManagerObject dmo = new DataManagerObject();
ValueObject [] docs = dmo.queryByWhere(doc_doc.class," DocID="+docid);
doc_doc doc = null;
if(docs.length==1) {
	doc = (doc_doc) docs[0];
} else {
	out.println("文档查找出错");
	return;
}

//更新文档点击次数
dmo.updateBySQL("update doc_doc set ClickCount=ClickCount+1 where DocID="+doc.getDocID());

doc_cate [] catePath = Doc.getDocCatePath(doc.getDocCateID());

//查询文档版本
ValueObject [] docvers = dmo.queryByWhere(doc_ver.class," DocID="+docid+" order by AddTime desc");

bd_user currUser = (bd_user) session.getAttribute(Consts.PKB_USER_SESSION_NAME);
%>

<%@page import="com.pxl.pkb.vo.doc_ver"%>
<%@page import="com.pxl.pkb.biz.Attach"%>
<%@page import="com.pxl.pkb.vo.sys_attach"%>
<%@page import="java.io.File"%>
<%@page import="com.pxl.pkb.biz.Comment"%>
<%@page import="com.pxl.pkb.framework.PxlInputStream"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<meta http-equiv="Pragma" content="no-cache">
<title>文档详细页</title>
<link href="main.css" rel="stylesheet" type="text/css">
<link href="css/color.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="ckeditor/ckeditor.js"></script>
<script type="text/javascript">
function showDocUpdateDiv() {
	if(docUpdateDiv.style.display=='none') {
		docUpdateDiv.style.display='';
	} else {
		docUpdateDiv.style.display='none';
	}
}

function showDocCommentDiv() {
	if(docCommentDiv.style.display=='none') {
		docCommentDiv.style.display='';
	} else {
		docCommentDiv.style.display='none';
	}
}

function onCommentFormCheck(theForm) {
	if(theForm.commentContent.value=='') {
		alert('评论不能为空！');
		return false;
	}
	return true;
}
function checkFileName(){
	var _fileName=document.getElementById("fileName").value;
	var _myfile=document.getElementById("myfile").value;
	var fileName=null;
	var myfile=null;
	if(_fileName.indexOf(".")==-1){
		myfile=_myfile.substr(_myfile.lastIndexOf("\\")+1,_myfile.indexOf(".")-_myfile.lastIndexOf("\\")-1);
		fileName=_fileName;
	}else{
		myfile=_myfile.substr(_myfile.lastIndexOf("\\")+1,_myfile.indexOf(".")-_myfile.lastIndexOf("\\")-1);
		fileName=_fileName.substr(0,_fileName.indexOf("."));
	}
	if(myfile!=fileName){
		alert("文件名必须一致");
		return false;
	}else{
		return true;
	}
}
</script>
</head>
<body class="body">
<%@ include file="header.jsp" %>
<table class="bodytable" align="center"><tr><td>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr>
<td height="30"><a href="index.jsp">首页</a> &gt; <a href="doc_list.jsp">文档</a> <%

for(int i=0;i<catePath.length;i++) {
	out.print(" &gt; <a href=\"doc_list.jsp?doccateid="+
			catePath[i].getDocCateID()+"\">"+catePath[i].getDocCateName()+"</a> ");
}

%></td>
</tr>
</table>

<table width="900" border="0" cellpadding="2" cellspacing="2" class="blockborder">
	  <tr>
		<td height="20" class="tableBackGround">
		<img src="images/filetype/allfile.png"> <span class="blocktitle">文档详细信息</span>
		</td>
	  </tr>
	  <tr>
		<td>
		<table width="100%" border="0" cellpadding="3" cellspacing="1">
		<tr>
			<td width="70" bgcolor="#ECF7DF" align="center" >文档标题</td>
			<td bgcolor="#ECF7DF"><%=Pub.text2html(doc.getDocTitle()) %><input id="fileName" type="hidden" value="<%=Pub.text2html(doc.getDocTitle()) %>"/></td>
		</tr>
		<tr>
			<td bgcolor="#ECF7DF" align="center" valign="top">文档简介</td>
			<td bgcolor="#ECF7DF"><%=Pub.text2html(doc.getDocIntro()) %></td>
		</tr>
		<tr>
			<td bgcolor="#ECF7DF" align="center">关键字</td>
			<td bgcolor="#ECF7DF"><%=doc.getDocKeyword() %></td>
		</tr>
		<tr>
			<td bgcolor="#ECF7DF" align="center">建立人</td>
			<td bgcolor="#ECF7DF"><%
			bd_user createUser = User.findUser(doc.getAddUser());
			if(createUser!=null) {
				out.println(createUser.getUserName());
			}
			%></td>
		</tr>
		<tr>
			<td bgcolor="#ECF7DF" align="center">创建时间</td>
			<td bgcolor="#ECF7DF"><%=doc.getAddTime()%></td>
		</tr>
		<tr>
			<td bgcolor="#ECF7DF" align="center">更新时间</td>
			<td bgcolor="#ECF7DF"><%=doc.getUpdateTime()%></td>
		</tr>
		<tr>
			<td bgcolor="#ECF7DF" align="center">操作</td>
			<td bgcolor="#ECF7DF">
			<%if(currUser.getUserID()==doc.getAddUser()) {%>
				<a href='doc_edit.jsp?docid=<%=doc.getDocID() %>'>编辑</a>
				<a href='javascript:showDocUpdateDiv();'>更新</a>
				<a href='DocDelete.do?docid=<%=doc.getDocID()%>&doccateid=<%=doc.getDocCateID() %>' onclick="return confirm('真的要删除该文档？');">删除</a>
			<%} %>
				<a href='DocDownload.do?docid=<%=doc.getDocID()%>&outputid='>下载</a>
				<a href='javascript:showDocCommentDiv();'>评论</a>
				<%=Comment.getCommentViewLink(2,doc.getDocID(),null) %>
				<%if(Pub.isSwfFileExists(docid)) { %>
				<a href='doc_read.jsp?docid=<%=doc.getDocID() %>' target='_blank'>阅读</a>
				<%} else { %>
				<span id="docConvert">阅读（文件正在转换...）</span>
				<iframe src="doc_convert.jsp?docid=<%=docid %>" style="display: none;"></iframe>
				<%} %>
				<%if(Pub.isAdminUser(session)) {%>
				<a href="doc_convert.jsp?docid=<%=docid %>" target="_blank">手工重新转换</a>
				<%} %>
			</td>
		</tr>
		</table>
		
		<div id="docUpdateDiv" style="display: none;">
		<form id="frmDocUpdate" action="DocUpdate.do" method="post" enctype="multipart/form-data" onsubmit="return checkFileName();">
		<input id="docid" name="docid" type="hidden" value="<%=docid %>">
		请选择文档的最新文件 <input id="myfile" type="file" name="myfile" class="fileinput">
		<input type="submit" class="filesubmit" id="mySubmit" value="上传">
		<span class="helpRequired">&nbsp;&nbsp;更新文档时，文档名称必须保持和原有版本文档名称一致,点击上传</span>
		</form>
		</div>
		
		<div id="docCommentDiv" style="display: none;">
		<form action="CommentAdd.do" method="post" onsubmit="return onCommentFormCheck(this);">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr><td colspan="2">请输入评论信息：</td></tr>
				<tr>
					<td valign="top" width="350px">
						<input name="objID" value="<%=doc.getDocID() %>" type="hidden">
						<input name="commentType" value="2" type="hidden">
						<input name="returnPage" value="doc_detail.jsp?docid=<%=doc.getDocID() %>" type="hidden">
						<textarea id="qstComment" name="commentContent" style="width: 300px;height:100px;font-size: 12px;"></textarea>
					</td>
					<td class="helpRequired">请输入您要评论的内容，点击发表评论</td>
				</tr>
				<tr><td colspan="2"><input type="submit" value="发表评论"></td></tr>
			</table>
		</form>
		</div>
		</td>
	  </tr>
</table><br/>
<table width="900" border="0" cellpadding="2" cellspacing="2" class="blockborder">
	<tr>
		<td class="helpRequired">注意事项：同一帐号每日最多下载5个文档，重复下载算一次</td>
	</tr>
</table>
<br>
<table width="900" border="0" cellpadding="2" cellspacing="2" class="blockborder">
	  <tr>
		<td height="20" class="tableBackGround">
		<img src="images/filetype/allfile.png"> <span class="blocktitle">文档版本</span>
		</td>
	  </tr>
	  <tr>
		<td>
		<table width="100%" border="0" cellpadding="2" cellspacing="1">
		<tr>
			<td width="250">版本</td>
			<td width="200" align="left">文件名</td>
			<td align="right">大小</td>
			<td align="center">上传人</td>
			<td align="center" width="100">操作</td>
			<td width="150" align="center">版本时间</td>
		</tr>
		<%
		String uploadPath = Pub.getUploadPath();//application.getRealPath("META-INF/upload/");
		for(int i=0;i<docvers.length;i++) {
			doc_ver ver = (doc_ver)docvers[i];
			ValueObject [] attachs = dmo.queryByWhere(sys_attach.class," AttachCate='DOC' and ObjID="+ver.getDocVerID());
			sys_attach attach = null;
			if(attachs.length>0) {
				attach = (sys_attach)attachs[0];
			}
		%>
		<tr>
    		<td height="4" colspan="6"><div class="listdotline"></div></td>
  		</tr>
		<tr>
			<td><%=ver.getDocVerName() %></td>
			<td align="left"><%=attach==null?"未找到附件信息":Pub.getFullFileName(attach.getFileName(),attach.getFileType()) %></td>
			<td align="right"><%
			if(attach!=null) {
				String filePath = uploadPath+File.separator+attach.getAttachCate()+File.separator+
					attach.getAddTime().substring(0,10)+File.separator+attach.getAttachID();
				File f = new File(filePath);
				if(f.exists()) {
					PxlInputStream pin = new PxlInputStream(f.getAbsolutePath());
					out.println(Attach.getFriendlyFileSize(pin.getFileSize()));
					pin.close();
				} else {
					out.println("0");
				}
			}
			%></td>
			<td align="center"><%
			if(attach!=null) {
				bd_user uploadUser = User.findUser(attach.getAddUser());
				out.println(uploadUser.getUserName());
			}
			%></td>
			<td align="center">
			<%if(currUser.getUserID()==doc.getAddUser()) {%>
				<a href='DocVerDelete.do?docverid=<%=ver.getDocVerID()%>&docid=<%=doc.getDocID()%>' onclick="return confirm('真的要删除该版本吗？');">删除</a>
			<%} %>
				<a href='DocDownload.do?docverid=<%=ver.getDocVerID()%>&docid=<%=doc.getDocID()%>&outputid='>下载</a>
			</td>
			<td align="center"><%=ver.getAddTime() %></td>
		</tr>
		<%} %>
		</table>
		</td>
	  </tr>
</table>
<br></td></tr></table>
<%@ include file="footer.jsp" %>
</body>
</html>