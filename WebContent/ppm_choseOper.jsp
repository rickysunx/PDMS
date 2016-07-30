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
<title>负责人列表</title>
<link href="main.css" rel="stylesheet" type="text/css">
<link href="css/color.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
function chose(){
 var checkMemberIdValue = document.getElementsByName("checkMemberIdValue");
 var addrownumber =document.getElementById("addrownumber").value;
 var taskId=document.getElementById("taskId").value;
 var addmody=document.getElementById("addmody").value;
if(null!=checkMemberIdValue&&checkMemberIdValue!=''&&checkMemberIdValue!='undefined'){
    var keyarr = new Array();
    var valuearr = new Array();
    var m =0;
     for (var i = 0; i <checkMemberIdValue.length; i++) {
            if(checkMemberIdValue[i].checked){
              var str =checkMemberIdValue[i].value;
              var arg = str.split(',');
              keyarr[m]= arg[0];
              valuearr[m]=arg[1];
              m++;
            }
		}
		if(keyarr!=null&&valuearr!=null){
	        window.dialogArguments.addOper(keyarr,valuearr,addrownumber,taskId,addmody); 
		}
  }
  window.close();
}
</script>
</head>
<body class="body" style="background-color: white;border: 0;">
<table  align="center"><tr><td>
<table width="100%"  border="0" cellpadding="0" cellspacing="0">
	  <tr>
		<td height="25" width="20" class="tab_blank">&nbsp;</td>
		<td class="tab_active" width="120">
		<span class="tab_text">员工列表</span>
		</td>
		<td class="tab_blank">&nbsp;</td>
	  </tr>
</table>
<table width="100%" border="0" cellspacing="2" cellpadding="0">
  <tr>
   
    <td width="450" height="25">员工姓名</td>
     <td width="50" >选择</td>
  </tr>

	<tr>
    	<td height="2" colspan="4"><div class="listdotline"></div></td>
  	</tr>
  	<caption></caption>
  	<% 
  	  String addrownumber =(String)request.getAttribute("addrownumber");
	  String taskId =(String)request.getAttribute("taskId");
  	  String addmody =(String)request.getAttribute("addmody");
  	   if(null!=ppmmembers&&!ppmmembers.equals("")){
  		 for(int i=0;i<ppmmembers.length;i++){
  			 ppm_member ppmmember = ppmmembers[i];
             int memberId = ppmmember.getMemberID();
  	  		%>
  	  		<tr>
  	  	<td height="25"><%=ppmmember.getUserName()%></td>
  	  	<td height="25"><input type="checkbox" name="checkMemberIdValue" value="<%=memberId%>,<%=ppmmember.getUserName()%>" id="checkMemberIdValue" /></td>
  	    <input type="hidden" name="memberId" id="memberId<%=memberId%>"  value="<%=memberId%>" >
  	    <input type="hidden" name="userName" id="userName<%=memberId%>"  value="<%=ppmmember.getUserName()%>" >
  	    <input type="hidden" name="addrownumber" id="addrownumber" value="<%=addrownumber%>" />
  	    <input type="hidden" name="taskId" value="<%=taskId%>" id="taskId" ></input>
  	    </tr>
  	  		<%
  	  	} 
  		 %>
  		   <tr>
    	<td height="2" colspan="4" align="center" ><input type="button" name="button1" id="button1" value="确定" onclick="return chose();" ></td>
        	</tr>
  		 <%
  	   }else{
  		   %>
  		   <tr>
    	<td height="2" colspan="4"><font color="red">暂无用户</font></td>
  	</tr>
  		   <%
  		   
  	   }
  	  
  	%>	
<input type="hidden" name="addmody" value="<%=addmody%>" id="addmody" />
</table>
<br>
</td></tr></table>
</body>
</html>