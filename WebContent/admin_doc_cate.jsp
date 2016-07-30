<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@page import="com.pxl.pkb.biz.Pub"%>
<%@ page errorPage="ExceptionHandler.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
if(!Pub.isAdminUser(session)) {
	throw new Exception("非管理员用户，无法访问！");
}
%>
<%
DataManagerObject dmo = new DataManagerObject();
ValueObject [] catevos = dmo.queryAll(doc_cate.class);
doc_cate [] cates = new doc_cate[catevos.length];
HashMap hmOldCateName = new HashMap();
for(int i=0;i<cates.length;i++) {
	cates[i] = (doc_cate)catevos[i];
	hmOldCateName.put(new Integer(cates[i].getDocCateID()),cates[i].getDocCateName());
}
cates = Doc.getCatesByTreeOrder(cates);
%>
<%@page import="com.pxl.pkb.framework.DataManagerObject"%>
<%@page import="com.pxl.pkb.framework.ValueObject"%>
<%@page import="com.pxl.pkb.vo.doc_cate"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.pxl.pkb.biz.Doc"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<meta http-equiv="Pragma" content="no-cache">
<title>文档类别</title>
<link href="main.css" rel="stylesheet" type="text/css">
<link href="css/color.css" rel="stylesheet" type="text/css">
</head>
<body>
<table border="0" cellpadding="2" cellspacing="2">
	<tr><td>后台管理 &gt; 文档 &gt; 文档类别</td></tr>
	<tr><td><a href="admin_doc_cate_info.jsp?docCateID=0&type=0&method=add">新建根类别</a></td></tr>
</table>

<table width="1000" border="0" cellpadding="2" cellspacing="2" class="blockborder">
<tr class="tableBackGround">
	<td height="20" width="300"><b>类别名称</b></td>
	<td height="20" width="100"><b>能否上传文档</b></td>
	<td width="150" align="center"><b>操作</b></td>
</tr>
<%
for(int i=0;i<cates.length;i++) {
%>
<tr bgcolor="<%=(i%2==0)?"#f9f9f9":"#f7f7f7" %>">
	<td height="20" nowrap="nowrap"><%=cates[i].getDocCateName()%></td>
	<td height="20"><%if(cates[i].getCanPost().equals("0")){out.println("否");}else{out.println("是");}%></td>
	<td align="center">
		<a href="admin_doc_cate_info.jsp?method=add&type=1&docCateID=<%=cates[i].getDocCateID()%>">新增</a>
		<a href="DocCateDelete.do?doccateid=<%=cates[i].getDocCateID() %>" onclick="return confirm('是否要删除该类别？');">删除</a>
		<a href="InitDocCate.do?method=update&docCateID=<%=cates[i].getDocCateID()%>">编辑</a>
	</td>
</tr>
<%} %>
</table>
<form id="frmDocCate" action="" method="post">
	<input id="docCateID" name="docCateID" type="hidden">
	<input id="docCateName" name="docCateName" type="hidden">
</form>
</body>
</html>