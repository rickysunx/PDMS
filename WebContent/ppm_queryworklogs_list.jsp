<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@ page import="com.pxl.pkb.vo.ppm_workdetail,java.util.List,com.pxl.pkb.vo.bd_user,com.pxl.pkb.extendvo.ppm_problemon,com.pxl.pkb.vo.ppm_output,com.pxl.ppm.framework.BeanFactory,com.pxl.ppm.itfs.IMember"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<meta http-equiv="Pragma" content="no-cache">
<title>任务</title>
<link href="main.css" rel="stylesheet" type="text/css">
<link href="css/color.css" rel="stylesheet" type="text/css">
</head>
<%
ppm_workdetail[] workdetails =(ppm_workdetail[])request.getAttribute("workdetail");
List  problemlist =(List)request.getAttribute("problemlist");
List outputlist =(List)request.getAttribute("outputlist");
%>
<body>
<font size="2" >[详细工作内容]</font>
<br>
<table width='100%' class="ppm_task_table" border="0" cellpadding="2" cellspacing="1" id="mainTaskTable">
	<tr class="ppm_task_header" height="20px">
	    <td width="" >填写人</td>
       <td width="50px" align="center">内容序号</td>
		<td width="400px">详细工作内容</td>
		<td width="150px" align="center">工作地点</td>
		
	</tr>
	<%
	if(workdetails!=null){
		int personnum=0;
		for(int i=0;i<workdetails.length;i++){
			ppm_workdetail workdetail =workdetails[i];
			IMember mdom =(IMember)BeanFactory.getBean("Member");
			bd_user user = mdom.queryUserByID(workdetail.getUserID());  
			if(workdetail.getUserID()!=personnum){
				%>
				<tr class="ppm_task_rows" height="20px">
		        <td width="50px" align="center" colspan="4" ><%=user.getUserName()%></td>
	            </tr>	
				<%
			}
			personnum = workdetail.getUserID();
			%>
		<tr class="ppm_task_rows" height="20px">
		<td width="50px" align="center"></td>
		<td width="50px" align="center"><%=workdetail.getSeqNum()%></td>
		<td width="400px"><%=workdetail.getDetailContent()%></td>
		<td width="100px" align="center"><%=workdetail.getWorkAddress()%></td>
	    </tr>	
			<%
	   }
	}
	 
	
	%>			
</table>
<font size="2" >[产出物]</font>
<br>
<table width='100%' class="ppm_task_table" border="0" cellpadding="2" cellspacing="1" id="mainTaskTable">
	<tr class="ppm_task_header" height="20px">
       <td width="50px" align="center">产出物序号</td>
		<td width="400px">产出物名称</td>
		<td width="150px" align="center">附件</td>
	</tr>
	<%
	if(outputlist!=null){
		for(int i=0;i<outputlist.size();i++){
			ppm_output output =(ppm_output)outputlist.get(i);
			%>
		<tr class="ppm_task_rows" height="20px">
		<td width="100px" align="center"><%=output.getSeqNum()%></td>
		<td width="300px"><%=output.getOutputName()%></td>
		<td width="100px" align="center">下载</td>
	</tr>	
			<%
	   }
	}
	 
	
	%>			
</table>
<font size="2" >[遗留问题及风险提醒]</font>
<br>
<table width='100%' class="ppm_task_table" border="0" cellpadding="2" cellspacing="1" id="mainTaskTable">
	<tr class="ppm_task_header" height="20px">
       <td width="100px" align="center">问题风险序号</td>
		<td width="100px">说明</td>
		<td width="100px">解决方案</td>
		<td width="100px">负责人</td>
		<td width="100px">配合人</td>
		<td width="100px">预计解决时间</td>
		<td width="50px">解决状态</td>
	</tr>
	<%
	if(problemlist!=null){
		for(int i=0;i<problemlist.size();i++){
			ppm_problemon problemon =(ppm_problemon)problemlist.get(i);
			%>
		<tr class="ppm_task_rows" height="20px">
		 <td width="50px" align="center"><%=problemon.getProblemCode()%></td>
		<td width="200px"><%=problemon.getDescription()%></td>
		<td width="200px"><%=problemon.getPlan()%></td>
		<td width="100px"><%=problemon.getChargerName()%></td>
		<td width="100px"><%String[] useroper=problemon.getUseroper();
		 for(int j=0;j<useroper.length;j++){
			 if(j==(useroper.length-1)){
				 out.print(useroper[j]);
			 }else{
				 out.print(useroper[j]+",");
			 }
		 }
		%></td>
		<td width="100px"><%=problemon.getPlanTime()%></td>
		<td width="50px"><%
		int status = problemon.getProblemStatus();
		 if(status==0){
			 out.println("已解决");
		 }else{
			 out.println("未解决");
		 }
		%></td>
	   </tr>	
			<%
	   }
	}
	 
	
	%>			
</table>
</body>
</html>