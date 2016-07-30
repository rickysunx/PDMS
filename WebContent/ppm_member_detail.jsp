<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ page errorPage="ExceptionHandler.jsp" %>
<%@ page import="com.pxl.pkb.vo.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<meta http-equiv="Pragma" content="no-cache">
<title>项目成员详细页</title>
<link href="main.css" rel="stylesheet" type="text/css">
<link href="css/color.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="ckeditor/ckeditor.js"></script>
</head>
<body class="body">
<jsp:include flush="true" page="header.jsp" />
<%ppm_member ppmmember =(ppm_member)request.getAttribute("ppm_member");
  ppm_project ppmproject =(ppm_project)request.getAttribute("ppmprojects");
%>
<table class="bodytable" align="center"><tr><td>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr>
<td height="30"><a href="index.jsp">首页</a>&nbsp;&gt;&nbsp;<a href="PPMproject.do?method=list&refproject=list">项目</a>&nbsp;&gt;&nbsp;<a href="PPMMember.do?method=list&projectId=<%if(ppmproject!=null)out.print(ppmproject.getProjectID());%>">项目成员</a>&nbsp;&gt;&nbsp;<a href="doc_list.jsp">项目成员详情</a></td>
</tr>
</table>

<table width="900" border="0" cellpadding="2" cellspacing="2" class="blockborder">
	  <tr>
		<td height="20" class="tableBackGround"><span class="blocktitle">项目成员详细信息</span>
		</td>
	  </tr>
	  <tr>
		<td>
		<table width="100%" border="0" cellpadding="3" cellspacing="1">
		<tr>
			<td bgcolor="#ECF7DF" align="center" valign="top" width="120" >姓名</td>
			<td bgcolor="#ECF7DF"><%=ppmmember.getUserName()%></td>
		</tr>
		<tr>
			<td bgcolor="#ECF7DF" align="center">所属单位</td>
			<td bgcolor="#ECF7DF"><%=ppmmember.getUnit()%></td>
		</tr>
		<tr>
			<td bgcolor="#ECF7DF" align="center">单位职务</td>
			<td bgcolor="#ECF7DF"><%=ppmmember.getPosition()%></td>
		</tr>
		<tr>
			<td bgcolor="#ECF7DF" align="center">客户项目角色</td>
			<td bgcolor="#ECF7DF"><%=ppmmember.getCustRole()%></td>
		</tr>
		<tr>	
			<td bgcolor="#ECF7DF" align="center">用友项目角色</td>
			<td bgcolor="#ECF7DF"><%=ppmmember.getUfidaRole()%></td>
		</tr>
		<tr>
			<td bgcolor="#ECF7DF" align="center">联系电话</td>
			<td bgcolor="#ECF7DF"><%=ppmmember.getTel() %></td>
		</tr>
		<tr>	
			<td bgcolor="#ECF7DF" align="center">电子邮件</td>
			<td bgcolor="#ECF7DF"><%=ppmmember.getEMail()%></td>
		</tr>
			<tr>	
			<td bgcolor="#ECF7DF" align="center">所在项目</td>
			<td bgcolor="#ECF7DF"><%=ppmproject.getProjectName()%></td>
		</tr>
		<%
		  if(ppmproject!=null){ 
			  if(ppmproject.getProjectStatus()==0){
				  %>
				  <tr>
				<td bgcolor="#ECF7DF" align="center">操作</td>
				<td bgcolor="#ECF7DF">
					<a href='PPMMember.do?method=modify&memberId=<%=ppmmember.getMemberID()%>'>编辑</a>
					<a href="PPMMember.do?method=delete&memberId=<%=ppmmember.getMemberID()%>" onclick="return confirm('您确定要删除此项目成员吗')">删除</a>
				</td>
			</tr>
				  <%
				  
			  }
		  }
		
		%>
		
		</table>
		</td>
	  </tr>
</table><br/>

</td></tr></table>
<jsp:include flush="true" page="footer.jsp" />
</body>
</html>