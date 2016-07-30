<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<meta http-equiv="Pragma" content="no-cache">
<title>Task Response</title>
<script type="text/javascript">
function onPageLoad() {
	<%
	boolean ok = ((Boolean)request.getAttribute("ok")).booleanValue();
	if(ok) {
		out.println("parent.window.location.reload();");
	} else {
		out.println("alert('出错信息：'+'"+request.getAttribute("errorinfo")+"');");
		out.println("parent.enableAllBtns(true);");
	}
	%>
}
</script>
</head>
<body onload="onPageLoad();">

</body>
</html>