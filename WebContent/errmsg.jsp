<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<meta http-equiv="Pragma" content="no-cache">
<title>出错</title>
</head>
<body>
<%=request.getAttribute("errmsg") %>，<a href="#" onclick="backtoup();">点击</a>此处返回上一页
<script type="text/javascript">
	function backtoup(){
		window.history.back();
	}
</script>
</body>
</html>