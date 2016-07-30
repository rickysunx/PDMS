<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>备注</title>
<script type="text/javascript" src="ppm_task_notes.js"></script>
</head>
<body onload="initData();">
<form>
	<table width='100%' class="ppm_task_table" border="0" cellpadding="2" cellspacing="1">
		<tr class="ppm_task_header">
			<td><textarea id="txtNotes" style="width: 480px;height: 350px;"></textarea></td>
		</tr>
	</table>
	<table width="100%">
		<tr>
			<td align="center">
				<input type="button" value="确定" onclick="on_ok();">
				<input type="button" value="取消" onclick="on_cancel();">
			</td>
		</tr>
	</table>
</form>
</body>
</html>