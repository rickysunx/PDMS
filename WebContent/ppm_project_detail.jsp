<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ page errorPage="ExceptionHandler.jsp" %>
<%@ page import="com.pxl.pkb.vo.ppm_project,com.pxl.pkb.vo.ppm_member"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<meta http-equiv="Pragma" content="no-cache">
<title>项目详细页</title>
<link href="main.css" rel="stylesheet" type="text/css">
<link href="css/color.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="ckeditor/ckeditor.js"></script>
<script type="text/javascript">
function edit(projectstatu){
   if(null!=projectstatu&&''!=projectstatu&&projectstatu!='undefined'){
      if(projectstatu==1){
         alert("项目已发布，不能编辑");
         return false;
      }else{
        return true;
      }
   }

}
</script>
</head>
<body class="body">
<jsp:include flush="true" page="header.jsp" />
<table class="bodytable" align="center"><tr><td>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr>
<td height="30"><a href="index.jsp">首页</a>&nbsp;&gt;&nbsp;<a href="PPMproject.do?method=list&refproject=list">项目</a>&nbsp;&gt;&nbsp;项目详细页</td>
</tr>
</table>
<%
 ppm_project ppmproject =(ppm_project)request.getAttribute("ppmproject");
ppm_member[] members =(ppm_member[])request.getAttribute("members");
%>
<table width="900" border="0" cellpadding="2" cellspacing="2" class="blockborder">
	  <tr>
		<td height="20" class="tableBackGround">
		<input id="fileName" type="hidden" value="<%=ppmproject.getProjectID()%>"/>
		<span class="blocktitle">项目详细信息</span>
		</td>
	  </tr>
	  <tr>
		<td>
		<table width="100%" border="0" cellpadding="3" cellspacing="1">
		<tr>
			<td width="70" bgcolor="#ECF7DF" align="center" >项目编码</td>
			<td bgcolor="#ECF7DF"><%=ppmproject.getProjectCode()%></td>
		</tr>
		<tr>
			<td bgcolor="#ECF7DF" align="center" valign="top">项目名称</td>
			<td bgcolor="#ECF7DF"><%=ppmproject.getProjectName()%></td>
		</tr>
		<tr>
			<td bgcolor="#ECF7DF" align="center" valign="top">项目属性</td>
			<td bgcolor="#ECF7DF"><%=ppmproject.getProjectValue()%></td>
		</tr>
		<tr>
			<td bgcolor="#ECF7DF" align="center">当前版本</td>
			<td bgcolor="#ECF7DF"><%=request.getAttribute("vername")%></td>
		</tr>
		<tr>
			<td bgcolor="#ECF7DF" align="center">操作</td>
			<td bgcolor="#ECF7DF">
				<a href='PPMproject.do?method=modify&projectId=<%=ppmproject.getProjectID()%>' onclick="return edit('<%=ppmproject.getProjectStatus()%>');">编辑</a>
				<a href="PPMproject.do?method=delete&projectId=<%=ppmproject.getProjectID()%>" onclick="return confirm('您确定要删除此项目');">删除</a>
			</td>
		</tr>
		</table>
		</td>
	  </tr>
</table><br/>
<table width="900" border="0" cellpadding="2" cellspacing="2" class="blockborder">
	  <tr>
		<td height="20" class="tableBackGround">
		<img src="images/filetype/allfile.png"> <span class="blocktitle">项目成员列表</span>
		</td>
	  </tr>
	  <tr>
		<td>
		<table width="100%" border="0" cellpadding="2" cellspacing="1">
		<tr>
			<td width="200" align="left">成员编号</td>
			<td align="right">姓名</td>
			<td width="100" align="center" >单位</td>
			<td width="100" align="center" >职务</td>
			<td width="100" align="center">客户项目角色</td>
			<td width="100" align="center">用友项目角色</td>
		</tr>
		<tr>
    		<td height="4" colspan="6"><div class="listdotline"></div></td>
  		</tr>
  		<%
  		if(members!=null&&members.length!=0){
  			for(int i=0;i<members.length;i++){
  				ppm_member member = members[i];
  				
  		%>
  		<tr>
			<td  width="200" align="left"><%=member.getMemberID()%></td>
			<td align="right"><%=member.getUserName()%></td>
			<td width="100" align="center" ><%=member.getUnit()%></td>
			<td width="100" align="center" ><%
			if(member.getPosition()!=null){
				out.println(member.getPosition());
			}%></td>
			<td width="100" align="center"><%
			if(member.getPosition()!=null){
				out.println(member.getCustRole());
			}%></td>
			<td width="100" align="center"><%=member.getUfidaRole()%></td>
		</tr>
  		<%	
  		 }
  		} else{
  		 %>
  			<tr>
			<td  colspan="6" ><font color="red" >没有项目成员</font></td>
	        </tr>
  			<%
  			
  		}
  		%>
		
		</table>
		</td>
	  </tr>
</table>
</td></tr></table>
<%@ include file="footer.jsp" %>
</body>
</html>