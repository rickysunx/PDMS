<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ page errorPage="ExceptionHandler.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
bd_user loginUser = (bd_user)session.getAttribute(Consts.PKB_USER_SESSION_NAME);
bd_user user = User.findUser(loginUser.getUserID());
%>
<%@page import="com.pxl.pkb.biz.User"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<meta http-equiv="Pragma" content="no-cache">
<title>用户信息管理</title>
<link href="main.css" rel="stylesheet" type="text/css">
<link href="css/color.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="js/city.js"></script>
<script type="text/javascript" src="js/userinfo.js"></script>
<script type="text/javascript">
<%
Object objSaveInf = request.getAttribute("SaveInf");
if(objSaveInf!=null) {
	out.println("alert('"+objSaveInf+"');");
}
%>

	function onUserRegFormCheck(){
		var _passWord=document.getElementById("passWord").value;
		var _passWord2=document.getElementById("passWord2").value;
		var _userName=document.getElementById("userName").value;
		var _email=document.getElementById("email").value;
		var _companyList=document.getElementById("companyList").value;
		var _txtCompany=document.getElementById("txtCompany").value;
		var _jobList=document.getElementById("jobList").value;
		var _txtJob=document.getElementById("txtJob").value;
		
		var passWord=_passWord.replace(/\s/g,'');
		var passWord2=_passWord2.replace(/\s/g,'');
		var userName=_userName.replace(/\s/g,'');
		var email=_email.replace(/\s/g,'');
		var txtCompany=_txtCompany.replace(/\s/g,'');
		var txtJob=_txtJob.replace(/\s/g,'');
		
		var re_Password=/[a-z]|[A-Z]|([a-z,A-Z])/;
		var _rePassword=/\d/;
		
		var re_email=/^([a-zA-Z0-9]+[_|\-|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\-|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
		
		if(_userName.length<=0){
			alert("真实姓名必填");
			return false;
		}else if(_userName.length!=userName.length){
			alert("真实姓名不允许含有空格");
			return false;
		}else if(email.length==0||email==""){
			alert("邮箱地址必填");
			return false;
		}else if(re_email.test(email)==false){
			alert("邮箱地址不正确");
			return false;
		}else if(_companyList=="其他"&&_txtCompany.length==0){
			alert("请填写您的任职公司");
			return false;
		}else if(_companyList=="其他"&&(_txtCompany.length!=txtCompany.length)){
			alert("任职公司不允许含有空格");
			return false;
		}else if(_jobList=="其他"&&_txtJob.length==0){
			alert("请填写您的任职岗位");
			return false;
		}else if(_jobList=="其他"&&(_txtJob.length!=_txtJob.length)){
			alert("任职岗位不允许含有空格");
			return false;
		}else{
			if(_companyList=="其他"){
				document.getElementById("company").value=_txtCompany;
			}else{
				document.getElementById("company").value=document.getElementById("companyList").value;
			}
			if(_jobList=="其他"){
				document.getElementById("job").value=_txtJob;
			}else{
				document.getElementById("job").value=document.getElementById("jobList").value;
			}
			return true;
		}
	}
</script>
</head>
<body class="body" onload="showUserInfo();">
<%@ include file="header.jsp" %>
<table class="bodytable" align="center"><tr><td>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr>
<td height="30"><a href="index.jsp">首页</a> &gt; 用户信息管理 </td>
</tr>
</table>
<form id="frmUserManage" name="frmUserManage" action="UserManage.do" method="post" onsubmit="return onUserRegFormCheck();">
<input id="userID" name="userID" type="hidden" value="<%=user.getUserID() %>">
<table width="900" border="0" cellpadding="2" cellspacing="2" class="blockborder">
  <tr>
	<td height="20" class="tableBackGround" colspan="3">&nbsp;<span class="blocktitle">用户信息管理</span></td>
  </tr>
  <tr>
  	<td height="20" width="60">真实姓名</td>
  	<td><input id="userName" name="userName" type="text" class="logininput" value=<%=user.getUserName() %>></td>
  	<td class="helpRequired">请输入您的真实姓名</td>
  </tr>
  <tr>
  	<td height="20">密码</td>
  	<td><input id="passWord" name="passWord" type="password" class="logininput"></td>
  	<td class="helpRequired">请输入您的新密码，如果不改，请留空</td>
  </tr>
  <tr>
  	<td height="20">重复密码</td>
  	<td><input id="passWord2" name="passWord2" type="password" class="logininput"></td>
  	<td class="helpRequired">重复密码必须与密码一致</td>
  </tr>
  <tr>
  	<td height="20">性别</td>
  	<td><select id="sex" name="sex">
  	<option value="0"<%=user.getSex().equals("0")?" selected":"" %>>男</option>
  	<option value="1"<%=user.getSex().equals("1")?" selected":"" %>>女</option>
  	</select></td>
  	<td class="helpRequired">请选择您的性别</td>
  </tr>
  <tr>
  	<td height="20">邮箱</td>
  	<td><input id="email" name="email" type="text" class="logininput" value="<%=user.getEMail()%>"></td>
  	<td class="helpRequired">请输入您的邮箱</td>
  </tr>
  <tr>
  	<td height="20">电话</td>
  	<td><input id="phone" name="phone" type="text" class="logininput" value="<%=user.getPhone()%>"></td>
  	<td class="helpRequired">请输入你的11位电话号码或手机号码，如果没有可不填</td>
  </tr>
  <tr>
  	<td height="20">QQ</td>
  	<td><input id="qq" name="qq" type="text" class="logininput" value="<%=user.getQQ()%>"></td>
  	<td class="helpRequired">请输入您的QQ号码，如果没有，可不填</td>
  </tr>
  <tr>
  	<td height="20">MSN</td>
  	<td><input id="msn" name="msn" type="text" class="logininput" value="<%=user.getMSN()%>"></td>
  	<td class="helpRequired">请输入您的MSN号码，如果没有，可不填</td>
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
  	<td height="20">.</td>
  	<td colspan="2"><input type="submit" value=" 保存 ">&nbsp;&nbsp;<input type="button" value=" 返回 " onclick="window.location.href='index.jsp'"/></td>
  </tr>
</table>
</form>
<br>
</td></tr></table>
<script type="text/javascript">
	var companies=new Array("普信联","用友集团","用友分公司");
	var jobs=new Array("实施","需求","测试","开发","运维","售前");
	function showUserInfo(){
		hidden();
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
			document.getElementById("companyList").selectedIndex=companyCount;
		}else{
			document.getElementById("companyList").selectedIndex=3;
			document.getElementById("txtCompany").value=company;
		}
		if(jobCount!=-1){
			document.getElementById("jobList").selectedIndex=jobCount;
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
<%@ include file="footer.jsp" %>
</body>
</html>