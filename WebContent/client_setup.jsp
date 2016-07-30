<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>客户端安装</title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<meta http-equiv="Pragma" content="no-cache">
<link href="main.css" rel="stylesheet" type="text/css">
<link href="css/color.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
function isClientInstalled() {
	try {
		var pxldesktop = new ActiveXObject("pxl4ie.PxlExtend");
		return true;
	} catch (e) {
		return false;
	}
}

function pxlRedirect() {
	if(isClientInstalled()) {
		window.location.href='index.jsp';
	}
}

pxlRedirect();
</script>
</head>
<body>
<table>
<tr>
<td>
您尚未安装普信联客户端，安装客户端可以获得及时的消息提示，更加快捷的掌握知识动态。<br>
<a href='pxl_setup.exe'>请点击此处下载安装普信联客户端。</a><br>
<a href='index.jsp'>点击这里转入普信联首页。</a>
</td>
</tr>
</table>
</body>
</html>