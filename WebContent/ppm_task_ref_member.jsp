<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.pxl.ppm.itfs.IMember"%>
<%@page import="com.pxl.ppm.framework.BeanFactory"%>
<%@page import="com.pxl.pkb.vo.ppm_member"%>
<html>
<%
String strMultiSelect = request.getParameter("multi");
String strPxlOnly = request.getParameter("pxl");
String strProjectID = request.getParameter("projectID");
boolean multiSelect = (strMultiSelect!=null && strMultiSelect.equals("1"));
boolean pxlOnly = (strPxlOnly!=null && strPxlOnly.equals("1"));
int projectID = Integer.parseInt(strProjectID);
IMember member = (IMember)BeanFactory.getBean("Member");
ppm_member [] members = member.queryMemberByProject(projectID,pxlOnly);

%>
<head>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>人员选择</title>
<link href="main.css" rel="stylesheet" type="text/css">
<link href="css/color.css" rel="stylesheet" type="text/css">
<style type="text/css">
body{
	margin: 1px;
	padding: 1px;
	background-color: #FFFFFF;
}
</style>
<script type="text/javascript" src="ppm_task_ref_member.js"></script>
</head>
<body onload="initData();">
<form>
	<table width='100%' class="ppm_task_table" border="0" cellpadding="2" cellspacing="1">
		<tr class="ppm_task_header" height="20px">
			<td width="40px" align="center">选择</td>
			<td width="100px" align="center">成员姓名</td>
			<td width="100px" align="center">所属单位</td>
			<td width="100px" align="center">单位职务</td>
			<td width="100px" align="center">客户项目角色</td>
			<td width="100px" align="center">用友项目角色</td>
		</tr>
<%for(int i=0;i<members.length;i++) {
	ppm_member m = members[i];
%>
		<tr class="ppm_task_rows" height="20px">
			<td align="center"><input type="<%=multiSelect?"checkbox":"radio" %>" name="memSel" userName="<%=m.getUserName() %>" value="<%=m.getMemberID() %>"></td>
			<td align="center"><%=m.getUserName() %></td>
			<td align="center"><%=m.getUnit() %></td>
			<td align="center"><%=m.getPosition() %></td>
			<td align="center"><%=m.getCustRole() %></td>
			<td align="center"><%=m.getUfidaRole() %></td>
		</tr>
<%} %>
	</table>
	<table width="100%">
		<tr>
			<td align="center">
				<input type="button" value="确定" onclick="on_ok();">
				<input type="button" value="清空" onclick="on_clear();">
				<input type="button" value="取消" onclick="on_cancel();">
			</td>
		</tr>
	</table>
</form>
</body>
</html>