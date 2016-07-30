<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ page errorPage="ExceptionHandler.jsp" %>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.pxl.pkb.biz.Pub"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
if(!Pub.isAdminUser(session)) {
	throw new Exception("非管理员用户，无法访问！");
}
DataManagerObject dmo=new DataManagerObject();
ValueObject[] uservos= dmo.queryAll(bd_user.class);
%>
<%@page import="com.pxl.pkb.framework.DataManagerObject"%>
<%@page import="com.pxl.pkb.vo.bd_user"%>
<%@page import="com.pxl.pkb.framework.ValueObject"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Arrays"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<meta http-equiv="Pragma" content="no-cache">
		<title>批量上传</title>
		<link href="main.css" rel="stylesheet" type="text/css">
		<link href="css/color.css" rel="stylesheet" type="text/css">
		<script type="text/javascript">
			function selectit(action){
				var el = document.getElementsByTagName('input');
				var len = el.length;
				for(var i=0;i<len;i++){
					if((el[i].type=="checkbox") && (el[i].name=="users")){
						if(action=="selectAll")	{
							el[i].checked = true;
						}else if(action=="unselectAll"){
							el[i].checked = false;
						}else if(action=="antiSelect"){
							if(el[i].checked==true){
								el[i].checked=false;
							}else{
								el[i].checked=true;
							}			
						}
					}
				}
			}
			
			function createDirectory(){
				var userList=document.getElementsByName("users");
				var count=0;
				var error=0;
				for(var i=0;i<userList.length;i++){
					if(userList[i].checked==true){
						var docCount= userList[i].value;
						if(docCount>0){
							error++;
						}else{
							count++;
						}
					}
				}
				if(count==0){
					alert("请至少选择一位用户");
				}else if(error>0){
					alert("您选择了用户文档数不为0的用户，请重新选择");
				}else{
					var frm=document.getElementById("frmUsers");
					frm.action="BulkUpload.do?method=createDirectory";
					frm.submit();
				}
			}
			
			function bulkUpload(){
				var userList=document.getElementsByName("users");
				var count=0;
				var error=0;
				for(var i=0;i<userList.length;i++){
					if(userList[i].checked==true){
						var docCount= userList[i].value;
						if(docCount==0){
							error++;
						}else{
							count++;
						}
					}
				}
				if(count==0){
					alert("请至少选择一位用户");
				}else if(error>0){
					alert("您选择了用户文档数为0的用户，请重新选择");
				}else{
					var frm=document.getElementById("frmUsers");
					frm.action="BulkUpload.do?method=bulkUpload";
					frm.submit();
				}
			}
		</script>
	</head>
	<body>
		<table>
			<tr>
				<td>
					后台管理;&gt;批量上传
				</td>
			</tr>
		</table>
		<br />
		<table>
			<tr>
				<td height="25px">
					<input type="button" value="创建上传目录" onclick="createDirectory();" />
				</td>
				<td height="25px">
					<input type="button" value="导入" onclick="bulkUpload();" />
				</td>
				<td height="25px">
					<input type="button" value="全选" onclick="selectit('selectAll');" />
				</td>
				<td height="25px">
					<input type="button" value="全不选" onclick="selectit('unselectAll');" />
				</td>
				<td height="25px">
					<input type="button" value="反选" onclick="selectit('antiSelect');" />
				</td>
			</tr>
		</table>
		<form name="frmUsers" id="frmUsers" method="post">
			<table width="470" border="0" cellpadding="2" cellspacing="2"
				class="blockborder">
				<tr class="tableBackGround">
					<td height="20" width="200">
						<b>选择</b>
					</td>
					<td width="150" align="center">
						<b>编号</b>
					</td>
					<td width="150" align="center">
						<b>用户</b>
					</td>
					<td width="150" align="center">
						<b>文档数量</b>
					</td>
				</tr>
				<%
					HashMap map = (HashMap) request.getAttribute("userList");
					
					String[] userCode=new String[uservos.length];
					for(int i=0;i<uservos.length;i++){
						bd_user user=(bd_user)uservos[i];
						userCode[i]=user.getUserCode();
					}
					Arrays.sort(userCode);
					String[] userName=new String[uservos.length];
					String[] docCount=new String[uservos.length];
					
					for(int i=0;i<userCode.length;i++){
						for(int j=0;j<uservos.length;j++){
							bd_user user=(bd_user)uservos[j];
							if(user.getUserCode().equals(userCode[i])){
								userName[i]=user.getUserName();
								if(!map.containsKey(user.getUserName())){
									docCount[i]="0";
								}
								else{
									docCount[i]=map.get(user.getUserName()).toString();
								}
								break;
							}
						}
					}
					for(int i=0;i<userCode.length;i++){
				%>
				<tr align="center"
					bgcolor="<%=(i % 2 == 0) ? "#f9f9f9" : "#f7f7f7"%>">
					<td>
						<input type="checkbox" name="users" value="<%=userCode[i]%>" />
					</td>
					<td><%=userCode[i]%></td>
					<td><%=userName[i]%></td>
					<td><%=docCount[i]%>
						<input type="hidden" id="<%=userName[i]%>" value="<%=docCount[i]%>"/>
					</td>
				</tr>
				<%}%>
			</table>
		</form>
	</body>
	<%
		Object result=request.getAttribute("result");
		if(result!=null){
			out.println("<script type='text/javascript'>");
			out.println("alert('"+result.toString()+"')");
			out.println("</script>");
		}
	%>
</html>
