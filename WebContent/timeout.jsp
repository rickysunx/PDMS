<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%
Object oldURL=request.getAttribute("oldURL");
response.sendRedirect("login.jsp");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<meta http-equiv="Pragma" content="no-cache">
<title>超时</title>
</head>
<body class="body">
<table class="bodytable" align="center"><tr><td>
	登陆超时，请重新登陆。
	</td></tr></table>
</body>
</html>