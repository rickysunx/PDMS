<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="core" prefix="pxl"%>
<%@ page errorPage="ExceptionHandler.jsp" %>
<%@page import="com.pxl.pkb.framework.*"%>
<%@page import="com.pxl.pkb.vo.*"%>
<%@page import="com.pxl.pkb.biz.Doc"%>
<%
  ppm_member[] ppmmembers =(ppm_member[])request.getAttribute("ppm_members");
  
  %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<meta http-equiv="Pragma" content="no-cache">
<title>员工列表</title>
<link href="main.css" rel="stylesheet" type="text/css">
<link href="css/color.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
function chose(number){
if(number!=null&&number!=''&&number!='undefined'){
   var arr = new Array();
   arr[0]=document.getElementById("userId"+number).value;
   arr[1] = document.getElementById("userName"+number).value;
   arr[2] = document.getElementById("unit"+number).value;
   arr[3] = document.getElementById("position"+number).value;
   arr[4] = document.getElementById("tel"+number).value;
   arr[5] = document.getElementById("eMail"+number).value;
   if(arr!=null&&arr!=''&&arr!='undefined'){
   window.dialogArguments.addContent(arr); 
   }
  }
    window.close();
}
</script>
</head>
<body class="body">
<table style="background-color: white;border: 0;" align="center"><tr><td>
<table width="600"  border="0" cellpadding="0" cellspacing="0">
	  <tr>
		<td height="25" width="20" class="tab_blank">&nbsp;</td>
		<td class="tab_active" width="120">
		<span class="tab_text">员工列表</span>
		</td>
		<td class="tab_blank">&nbsp;</td>
	  </tr>
</table>
<table width="600" border="0" cellspacing="2" cellpadding="0">
  <tr>
    <td width="500" height="25">员工姓名</td>
    <td width="100"><div align="center">操作</div></td>
  </tr>

	<tr>
    	<td height="2" colspan="4"><div class="listdotline"></div></td>
  	</tr>
  	<caption></caption>
  	<% 
  	   if(null!=ppmmembers&&!ppmmembers.equals("")){
  		 for(int i=0;i<ppmmembers.length;i++){
  			 ppm_member ppmmember = ppmmembers[i];
  			 int userId =ppmmember.getUserID();
  	  		%>
  	  		<tr>
  	  	<td height="25">
  	  	<%=ppmmember.getUserName()%></td>
  	    <td><div align="center">
  	    <img src="images/check.png" style="cursor: hand;" onclick="javaScript:chose('<%=ppmmember.getUserID()%>')" />
  	    </div></td>
  	    <input type="hidden" name="userId" id="userId<%=userId%>"  value="<%=userId%>" >
  	    <input type="hidden" name="userName" id="userName<%=userId%>"  value="<%=ppmmember.getUserName()%>" >
  	    <input type="hidden" name="userName" id="unit<%=userId%>"  value="<%=ppmmember.getUnit()%>" >
  	    <input type="hidden" name="userName" id="position<%=userId%>"  value="<%=ppmmember.getPosition()%>" >
  	    <input type="hidden" name="userName" id="tel<%=userId%>"  value="<%=ppmmember.getTel()%>" >
  	    <input type="hidden" name="userName" id="eMail<%=userId%>"  value="<%=ppmmember.getEMail()%>" >
  	    </tr>
  	  		<%
  	  	}   
  	   }else{
  		   %>
  		   <tr>
    	<td height="2" colspan="4"><font color="red">暂无用户</font></td>
  	</tr>
  		   <%
  		   
  	   }
  	  
  	%>	
</table>
<br>
</td></tr></table>
</body>
</html>