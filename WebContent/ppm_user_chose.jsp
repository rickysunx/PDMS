<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@page import="com.pxl.pkb.vo.ppm_member" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>项目成员选择</title>
<link href="main.css" rel="stylesheet" type="text/css">
<link href="css/color.css" rel="stylesheet" type="text/css">
<style type="text/css">
body{
	margin: 1px;
	padding: 1px;
	background-color: #FFFFFF;
}
</style>
<script type="text/javascript">
function on_ok() {
	var oInputs = document.getElementsByName("membersel");
	var i;
	var result = new Object();
	result.selids = "";
	result.selnames = "";
	for(i=0;i<oInputs.length;i++) {
		if(oInputs[i].checked) {
			if(result.selids!="") {
				result.selids+=",";
				result.selnames+=",";
			}
			result.selids+=oInputs[i].value;
			result.selnames+=oInputs[i].memberName;
		}
	}
	window.dialogArguments.addmember(result);
	window.close();
}
function on_clear() {
	var oInputs = document.getElementsByName("membersel");
	var i;
	for(i=0;i<oInputs.length;i++) {
		oInputs[i].checked = false;
	}
}
function on_cancel() {
	window.dialogArguments.addproject(null);
	window.close();
}
</script>
</head>
<body>
<%
ppm_member[] ppmmembers =(ppm_member[])request.getAttribute("ppm_members");
%>
<form>
	<table width='100%' class="ppm_task_table" border="0" cellpadding="2" cellspacing="1">
		<tr class="ppm_task_header" height="20px">
			<td width="90px" align="center">选择</td>
			<td width="200px" align="center">成员姓名</td>
		</tr>
<%
 if(ppmmembers!=null&&ppmmembers.length!=0){
      for(int i=0;i<ppmmembers.length;i++) {
    	  ppm_member member = ppmmembers[i];
%>
		<tr class="ppm_task_rows" height="20px">
			<td align="center"><input type="radio" name="membersel" memberName="<%=member.getUserName()%>" id="membersel<%=member.getUserID()%>" value="<%=member.getUserID()%>"  ></td>
			<td align="center"><%=member.getUserName()%></td>
		</tr>
<%} 
 }
%>
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