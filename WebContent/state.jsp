<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.pxl.pkb.framework.DataManagerObject"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>运行状态</title>
</head>
<body>

<table border="1">
<tr>
<td width="70">freeMem</td>
<td  width="100" align="right"><%=new DecimalFormat(",###").format(Runtime.getRuntime().freeMemory())%></td>
</tr>

<tr>
<td>totalMem</td>
<td align="right"><%=new DecimalFormat(",###").format(Runtime.getRuntime().totalMemory())%></td>
</tr>

<tr>
<td>maxMem</td>
<td align="right"><%=new DecimalFormat(",###").format(Runtime.getRuntime().maxMemory())%></td>
</tr>
</table>

<%
DataManagerObject dmo = new DataManagerObject();
Object [][] data = dmo.querySQL("show processlist");

%>
<br>
<table border="1">
<tr>
	<td>Id</td>
	<td>User</td>
	<td>Host</td>
	<td>db</td>
	<td>Command</td>
	<td>Time</td>
	<td>State</td>
	<td>Info</td>
</tr>

<%
for(int i=0;i<data.length;i++) {
%>

<tr>
	<td><%=data[i][0] %></td>
	<td><%=data[i][1] %></td>
	<td><%=data[i][2] %></td>
	<td><%=data[i][3] %></td>
	<td><%=data[i][4] %></td>
	<td><%=data[i][5] %></td>
	<td><%=data[i][6] %></td>
	<td><%=data[i][7] %></td>
</tr>

<%} %>

</table>



</body>
</html>