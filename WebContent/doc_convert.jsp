<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@page import="com.pxl.pkb.biz.Doc"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
String strDocID = request.getParameter("docid");
Exception ex = null;
int docid = Integer.parseInt(strDocID);
try {
	Doc.convertDocToSwf(docid);
} catch (Exception e) {
	ex = e;
}
%>

<%@page import="com.pxl.pkb.biz.Pub"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>转换文档</title>
<script type="text/javascript">

var oSpan = parent.document.getElementById("docConvert");
<%
	if(ex==null) {
		if(Pub.isSwfFileExists(docid)) {
			out.println("oSpan.innerHTML=\"<a href='doc_read.jsp?docid="+docid+"' target='_blank'>阅读</a>\";");
		} else {
			out.println("oSpan.innerHTML='阅读（转换失败，可刷新后再试！）';");
		}
	} else {
		out.println("oSpan.innerHTML='阅读（转换失败，可刷新后再试！）';");
		out.println("alert(\"转换失败，错误信息："+ex.getMessage()+"\");");
	}

%>
</script>
</head>
<body>
<%
if(ex!=null) {
	out.println("出错："+ex.getMessage());
} else {
	out.println("已经发送转换请求");
}
%>
</body>
</html>