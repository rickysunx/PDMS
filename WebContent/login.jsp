<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ page errorPage="ExceptionHandler.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<meta http-equiv="Pragma" content="no-cache">
<title>普信联（北京）信息技术有限公司</title>
<meta name="Keywords" content="普信联,普信联（北京）信息技术有限公司" />
<meta name="Description" content="普信联（北京）信息技术有限公司" />
<link href="main.css" rel="stylesheet" type="text/css">
</head>
<body class="body">
<%@ include file="header.jsp" %>
<table border="0" align="center" width="1024" bgcolor="white">
	<tr align="center">
		<td width="200" valign="top"><%@ include file="lefter.jsp"%></td>
		<td rowspan="8" valign="top">
		<object classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000" codebase="http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,0,0" width="300" height="420" id="f1" align="middle">
			<param name="allowScriptAccess" value="sameDomain" />
			<param name="movie" value="media/logo_flash.swf" />
			<param name="quality" value="high" />
			<param name="bgcolor" value="#ffffff" />
			<embed src="media/logo_flash.swf" quality="high" bgcolor="#ffffff" width="300" height="400" name="首页flash" align="middle" allowScriptAccess="sameDomain" type="application/x-shockwave-flash" pluginspage="http://www.macromedia.com/go/getflashplayer" />
		</object>
		</td>
	</tr>
</table>
<%@ include file="footer.jsp"%>
</body>
<script type="text/javascript">

<%
	if (request.getAttribute("errinfo") != null) {
		out.println("alert('"+request.getAttribute("errinfo")+"');");
	}
%>

</script>
</html>