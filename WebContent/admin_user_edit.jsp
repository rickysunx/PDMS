<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ page errorPage="ExceptionHandler.jsp" %>
<%@page import="com.pxl.pkb.biz.Pub"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
if(!Pub.isAdminUser(session)) {
	throw new Exception("非管理员用户，无法访问！");
}
%>
<%
String strUserID = request.getParameter("userid");
int userid = Integer.parseInt(strUserID);
bd_user user = User.findUser(userid);
%>
<%@page import="com.pxl.pkb.vo.bd_user"%>
<%@page import="com.pxl.pkb.biz.User"%>
<%@page import="com.pxl.pkb.biz.Consts"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<meta http-equiv="Pragma" content="no-cache">
<title>用户编辑</title>
<link href="main.css" rel="stylesheet" type="text/css">
<link href="css/color.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="js/city.js"></script>
<script type="text/javascript" src="js/userinfo.js"></script>
<script type="text/javascript">
	function lock(){
		setValues();
		frmUserManage.errorCount.value=5;
		frmUserManage.submit();
	};
	function unLock(){
		setValues();
		frmUserManage.errorCount.value=0;
		frmUserManage.submit();
	};
	function setValues(){
		var companyList=document.getElementById("companyList").value;
		var jobList=document.getElementById("jobList").value;
		var txtJob=document.getElementById("txtJob").value;
		var txtCompany=document.getElementById("txtCompany").value;
		if(companyList=="其他"){
			document.getElementById("company").value=txtCompany;
		}else{
			document.getElementById("company").value=document.getElementById("companyList").value;
		}
		if(jobList=="其他"){
			document.getElementById("job").value=txtJob;
		}else{
			document.getElementById("job").value=document.getElementById("jobList").value;
		}
	}
	function updateUser(){
		setValues();
		frmUserManage.submit();
	}
</script>
</head>
<body onload="showUserInfo();">
<table border="0" cellpadding="2" cellspacing="2">
<tr><td>后台管理 &gt; 用户编辑 </td></tr>
</table>

<form id="frmUserManage" name="frmUserManage" action="UserManage.do?userType=admin" method="post" onsubmit="updateUser();">
<input id="userID" name="userID" type="hidden" value="<%=user.getUserID() %>">
<input id="returnPage" name="returnPage" type="hidden" value="admin_user_manager.jsp">
<table width="600" border="0" cellpadding="2" cellspacing="2" class="blockborder">
  <tr>
	<td height="20" class="tableBackGround" colspan="3">&nbsp;<span class="blocktitle">用户信息管理</span></td>
  </tr>
  <tr>
  	<td height="20" width="60">真实姓名</td>
  	<td width="300"><input id="userName" name="userName" type="text" class="logininput" value=<%=user.getUserName() %>></td>
  	<td class="helpRequired">请填写该用户的真实姓名</td>
  </tr>
  <tr>
  	<td height="20">性别</td>
  	<td><select id="sex" name="sex">
  	<option value="0"<%=user.getSex().equals("0")?" selected":"" %>>男</option>
  	<option value="1"<%=user.getSex().equals("1")?" selected":"" %>>女</option>
  	</select></td>
  	<td class="helpRequired">请填写该用户的性别</td>
  </tr>
  <tr>
  	<td height="20">邮箱</td>
  	<td><input id="email" name="email" type="text" class="logininput" value="<%=user.getEMail()%>"></td>
  	<td class="helpRequired">请填写该用户的邮箱，如果没有，可不填</td>
  </tr>
  <tr>
  	<td height="20">电话</td>
  	<td><input id="phone" name="phone" type="text" class="logininput" value="<%=user.getPhone()%>"></td>
  	<td class="helpRequired">请填写该用户的11位电话号码或手机号码，可不填</td>
  </tr>
  <tr>
  	<td height="20">QQ</td>
  	<td><input id="qq" name="qq" type="text" class="logininput" value="<%=user.getQQ()%>"></td>
  	<td class="helpRequired">请填写该用户的QQ号码</td>
  </tr>
  <tr>
  	<td height="20">MSN</td>
  	<td><input id="msn" name="msn" type="text" class="logininput" value="<%=user.getMSN()%>"></td>
  	<td class="helpRequired">请填写该用户的MSN号码</td>
  </tr>
  <tr>
  	<td height="20">用户角色</td>
  	<td><select id="isAdmin" name="isAdmin">
  	<%for(int i=0;i<Consts.ROLE_NAMES.length;i++) { 
  		out.println("<option value=\""+i+"\""+
  				(user.getIsAdmin().equals((""+i))?" selected":"")+">"+
  				Consts.ROLE_NAMES[i]+"</option>");
  	} %>
  	</select></td>
  	<td class="helpRequired">请选择该用户的用户角色</td>
  </tr>
    <tr>
  	<td height="20">任职公司</td>
  	<td height="20">
  		<select id="companyList" name="companyList" onchange="controlCompany();">
  			<option value="普信联">普信联</option>
  			<option value="用友集团">用友集团</option>
  			<option value="用友分公司">用友分公司</option>
  			<option value="其他">其他</option>
  		</select><br><span id="lblCompany">请填写您的任职公司：<input type="text" id="txtCompany"></span>
  		<input type="hidden" id="company" name="company" value="<%=user.getCompany()%>"/>
  	</td>
  	<td class="helpRequired">请选择您的任职公司，必填</td>
  </tr>
  <tr>
  	<td height="20">任职地区</td>
  	<td height="20">
  		<select id="province" name="province" onchange="getCity(this.options[this.selectedIndex].value)">
  			<option value="北京市">北京市</option> 
	        <option value="上海市">上海市</option> 
	        <option value="天津市">天津市</option> 
	        <option value="重庆市">重庆市</option> 
	        <option value="河北省">河北省</option> 
	        <option value="山西省">山西省</option> 
	        <option value="内蒙古自治区">内蒙古自治区</option> 
	        <option value="辽宁省">辽宁省</option> 
	        <option value="吉林省">吉林省</option> 
	        <option value="黑龙江省">黑龙江省</option> 
	        <option value="江苏省">江苏省</option>
	        <option value="浙江省">浙江省</option>
	        <option value="安徽省">安徽省</option> 
	        <option value="福建省">福建省</option> 
	        <option value="江西省">江西省</option> 
	        <option value="山东省">山东省</option> 
	        <option value="河南省">河南省</option> 
	        <option value="湖北省">湖北省</option> 
	        <option value="湖南省">湖南省</option> 
	        <option value="广东省">广东省</option> 
	        <option value="广西壮族自治区">广西壮族自治区</option> 
	        <option value="海南省">海南省</option> 
	        <option value="四川省">四川省</option> 
	        <option value="贵州省">贵州省</option> 
	        <option value="云南省">云南省</option> 
	        <option value="西藏自治区">西藏自治区</option> 
	        <option value="陕西省">陕西省</option> 
	        <option value="甘肃省">甘肃省</option> 
	        <option value="宁夏回族自治区">宁夏回族自治区</option> 
	        <option value="青海省">青海省</option> 
	        <option value="新疆维吾尔族自治区">新疆维吾尔族自治区</option> 
	        <option value="香港特别行政区">香港特别行政区</option> 
	        <option value="澳门特别行政区">澳门特别行政区</option> 
	        <option value="台湾省">台湾省</option> 
  		</select>
  		<select id="city" name="city"></select>
  	</td>
  	<td class="helpRequired">请选择您的的任职地区，必填</td>
  </tr>
  <tr>
  	<td height="20">任职岗位</td>
  	<td height="20">
  		<select id="jobList" name="jobList" onchange="controlJob();">
  			<option value="实施">实施</option>
			<option value="需求">需求</option>
			<option value="测试">测试</option>
			<option value="开发">开发</option>
			<option value="运维">运维</option>
			<option value="售前">售前</option>
			<option value="其他">其他</option>
  		</select><br>
		<span id="lblJob">请输入您的任职岗位：<input type="text" id="txtJob"></span>	
		<input type="hidden" id="job" name="job" value="<%=user.getJob()%>"/>	
  	</td>
  	<td class="helpRequired">请选择你的任职岗位，必填</td>
  </tr>
  <tr>
  	<td height="20">状态</td>
  	<td>
  	<input type="hidden" id="errorCount" name="errorCount"/>
  	<%=user.getErrorCount()<5?"未锁定&nbsp;&nbsp;<input type='button' value='锁定' onclick='lock();'/>":"已锁定&nbsp;&nbsp;<input type='button' onclick='unLock();' value='解锁'/>"%>
  	</td>
  	<td class="helpRequired">点击解锁/锁定该用户账号</td>
  </tr>
  <tr>
  	<td height="20"></td>
  	<td>
  	<input type="submit" value=" 保存 ">
  	<input type="button" value=" 返回 " onclick="window.history.back();">
  	</td>
  </tr>
</table>
<script type="text/javascript">
	var companies=new Array("普信联","用友集团","用友分公司");
	var jobs=new Array("实施","需求","测试","开发","运维","售前");
	function showUserInfo(){
		hidden();
		var companyList=document.getElementById("companyList").value;
		var company=document.getElementById("company").value;
		var province=document.getElementById("province").options;
		var city=document.getElementById("city").options;
		var job=document.getElementById("job").value;
		var companyCount=-1;
		var jobCount=-1;
		var provinceCount=-1;
		var cityCount=-1;
		for(var provinceIndex=0;provinceIndex<province.length;provinceIndex++){
			if(province[provinceIndex].text=="<%=user.getProvince()%>"){
				provinceCount=provinceIndex;
			}
		}
		for(var index=0;index<companies.length;index++){
			if(companies[index]==company){
				companyCount=index;
			}
		}
		for(var index=0;index<jobs.length;index++){
			if(jobs[index]==job){
				jobCount=index;
			}
		}
		if(companyCount!=-1){
			document.getElementById("companyList").selectedIndex=companyCount
		}else{
			document.getElementById("companyList").selectedIndex=3;
			document.getElementById("txtCompany").value=company;
		}
		if(jobCount!=-1){
			document.getElementById("jobList").selectedIndex=jobCount
		}else{
			document.getElementById("jobList").selectedIndex=6;
			document.getElementById("txtJob").value=job;
		}
		controlCompany();
		controlJob();
		if(provinceCount!=-1){
			document.getElementById("province").selectedIndex=provinceCount;
			getCity(document.getElementById("province").options[document.getElementById("province").selectedIndex].value);
			for(var cityIndex=0;cityIndex<city.length;cityIndex++){
				if(city[cityIndex].text=="<%=user.getCity()%>"){
					cityCount=cityIndex;
				}
			}
			if(cityCount!=-1){
				document.getElementById("city").selectedIndex=cityCount;
			}
		}
	}
</script>
</form>
</body>
</html>