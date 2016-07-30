<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ page errorPage="ExceptionHandler.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.pxl.pkb.framework.DataManagerObject"%>
<%@page import="com.pxl.pkb.framework.ValueObject"%>
<%@page import="com.pxl.pkb.vo.doc_doc"%>
<%@page import="com.pxl.pkb.biz.Doc"%>
<%@page import="com.pxl.pkb.vo.doc_cate"%>
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

//Params p = Params.getInstance();
//response.setContentType("application/x-shockwave-flash");
//String flashPaperPath = p.getFlashPaperPath();
//String flashPaperFileName = flashPaperPath + File.separator + docid + ".swf";

//File f = new File(flashPaperFileName);
if(!Pub.isSwfFileExists(docid)) throw new Exception("阅读文件不存在");

//更新文档点击次数
dmo.updateBySQL("update doc_doc set ClickCount=ClickCount+1 where DocID="+doc.getDocID());

doc_cate [] catePath = Doc.getDocCatePath(doc.getDocCateID());

//查询文档版本
ValueObject [] docvers = dmo.queryByWhere(doc_ver.class," DocID="+docid+" order by AddTime desc");

bd_user currUser = (bd_user) session.getAttribute(Consts.PKB_USER_SESSION_NAME);
%>

<%@page import="com.pxl.pkb.vo.doc_ver"%>
<%@page import="com.pxl.pkb.framework.Params"%>
<%@page import="java.io.File"%>
<%@page import="com.pxl.pkb.biz.Pub"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<meta http-equiv="Pragma" content="no-cache">
<title>文档详细页</title>
<link href="main.css" rel="stylesheet" type="text/css">
<link href="css/color.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="ckeditor/ckeditor.js"></script>
<script type="text/javascript">
function onPageLoad() {
	var theReader = document.getElementById('theFlashReader');
}

</script>
</head>
<body class="body" onload="onPageLoad();">
<%@ include file="header.jsp" %>
<table class="bodytable" align="center">
	<tr>
	<td>
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
	</td>
	</tr>
	
	<tr>
		<td align="center">
		<object id="theFlashReader" classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000" 
			codebase="http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,0,0" 
			width="800" height="600" id="f1" align="middle">
			<param name="allowScriptAccess" value="sameDomain" />
			<param name="movie" value="GetReader?docid=<%=docid %>" />
			<param name="quality" value="high" />
			<param name="bgcolor" value="#ffffff" />
			<embed src="GetReader?docid=<%=docid %>" quality="high" bgcolor="#ffffff" 
				width="800" height="600" align="middle" 
				allowScriptAccess="sameDomain" type="application/x-shockwave-flash" 
				pluginspage="http://www.macromedia.com/go/getflashplayer" />
		</object>
		</td>
	</tr>
</table>

<%@ include file="footer.jsp" %>
</body>
</html>