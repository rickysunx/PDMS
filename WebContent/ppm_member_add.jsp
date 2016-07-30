<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ page errorPage="ExceptionHandler.jsp" %>
<%@page import="com.pxl.pkb.biz.Pub"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
if(Pub.isUnCheckUser(session)) {
	throw new Exception("您的用户尚未通过审核，不能进行新增项目！");
}
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<meta http-equiv="Pragma" content="no-cache">
<title>新增项目成员</title>
<link href="main.css" rel="stylesheet" type="text/css">
<link href="css/color.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="upload.js"></script>
<link href="xtree/xtree.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="xtree/xtree.js"></script>
<script type="text/javascript" src="xtree/webfxcheckboxtreeitem.js"></script>
<script type="text/javascript" src="ckeditor/ckeditor.js"></script>
<script type="text/javascript">
function showDialog(url,cwidth,cheight)
{  
    var date = new Date();
    url = url+"&date="+date;
 	canshu="status:no;help:no;"
	+ "dialogWidth:" + cwidth + "px;"
	+ "dialogHeight:" + cheight + "px;"
	 url = encodeURI(url);   
     url = encodeURI(url);
    	window.showModalDialog(url,window,canshu); 
}
function addContent(arr){
if(arr!=null&&arr!=''&&arr!='undefined'){
  var arrs = new Array();
     arrs = arr;
     document.getElementById("userID").value=arrs[0];
     document.getElementById("userName").value=arrs[1];
     if(arrs[2]=='null'){
         document.getElementById("unit").value='';
     }else{
         document.getElementById("unit").value=arrs[2];
      }
     if(arrs[3]=='null'){
        document.getElementById("position").value='';
     }else{
        document.getElementById("position").value=arrs[3];
      }
     document.getElementById("tel").value=arrs[4];
     document.getElementById("email").value=arrs[5];
     if(arrs[1]!=null&&arrs[1]!=''&&arrs[1]!='undefined'){
         document.getElementById("checkName").style.display='none';
       document.getElementById("checkName").style.visibility='hidden';
     } 
  }
}
function checkForm(){
   var userName = document.getElementById("userName").value;
   var eMail = document.getElementById("email").value;
   var unit = document.getElementById("unit").value;
   var position = document.getElementById("position").value;
   var custRole = document.getElementById("custRole").value;
   var ufidaRole = document.getElementById("ufidaRole").value;
   var tel = document.getElementById("tel").value;
   var re_email=/^([a-zA-Z0-9]+[_|\-|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\-|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
   if(userName==null||userName==''||userName=='undefined'){
        document.getElementById("checkName").style.display='';
        document.getElementById("checkName").style.visibility='visible';
        return false;
   }else if(unit==null||unit==''||unit=='undefined'){
        document.getElementById("checkUnit").style.display='';
        document.getElementById("checkUnit").style.visibility='visible';
        return false;
   }else if(position==null||position==''||position=='undefined'){
        document.getElementById("checkPosition").style.display='';
        document.getElementById("checkPosition").style.visibility='visible';
        return false;
   }else if((custRole==null||custRole==''||custRole=='undefined')&&(ufidaRole==null||ufidaRole==''||ufidaRole=='undefined')){
       alert("【客户项目角色】和【用友项目角色】两者必须选一");
        return false;
   }else if(tel==null||tel==''||tel=='undefined'){
        document.getElementById("checkTel").style.display='';
        document.getElementById("checkTel").style.visibility='visible';
        return false;
   }else if(re_email.test(eMail)==false){
        document.getElementById("checkEmail").style.display='';
        document.getElementById("checkEmail").style.visibility='visible';
       return false; 
   }
}
function ongoFormOk(codename,checkname){
     var namevalue = document.getElementById(codename).value;
     if(namevalue!=null&&namevalue!=''&&namevalue!='undefined'){
       document.getElementById(checkname).style.display='none';
       document.getElementById(checkname).style.visibility='hidden';
     }
}

</script>
</head>
<body class="body">
<%@ include file="header.jsp" %>
<table class="bodytable" align="center">
	<tr>
		<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td height="30"><a href="index.jsp">首页</a>&nbsp;&gt;&nbsp;<a href="PPMproject.do?method=list&refproject=list">项目</a>&nbsp;&gt;&nbsp;<a href="PPMMember.do?method=list&projectId=<%=request.getAttribute("projectId")%>">项目成员</a>&nbsp;&gt;&nbsp;新增项目成员 </td>
				</tr>
			</table>
			
			<form id="frmmember" name="frmmember" action="PPMMember.do?method=add"   method="post">
				<table width="900" border="0" cellpadding="2" cellspacing="2" class="blockborder">
					<tr>
						<td height="20" class="tableBackGround" colspan="2">&nbsp;<span class="blocktitle">新增项目成员</span></td>
				  	</tr>
			  		<tr>
			  			<td>
			  				<table border="0" cellpadding="0" cellspacing="0">
			  					<tr>
			  						<td rowspan="2" valign="top">
			  							<input type="hidden" id="docCate" name="docCate"/>
			  							<table>
			  								<tr>
												<td height="20" align="center" colspan="1" >姓&nbsp;&nbsp;&nbsp;&nbsp;名</td>
												<input type="hidden" name="userID" id="userID" value="" />
												<input type="hidden" name="projectID" id="projectID" value="<%=request.getAttribute("projectId")%>" />
												<td height="20" colspan="3" ><input id="userName" type="text" name="userName" style="width: 300px;height: 15px;" onblur="javaScript:ongoFormOk('userName','checkName');" >&nbsp;<span style="color: red" >*</span>&nbsp;<img src="images/choose.gif" style="cursor: hand;" onclick="javaScript:showDialog('PPMMember.do?method=choseUser&tag=member','620','290');" >
												<span id="checkName" style="display: none;visibility:hidden;" ><font color="red">姓名不能为空</font></span>
												</td>
										  	</tr>
										  	<tr>
												<td height="20" align="center">所属单位</td>
												<td height="20"><input id="unit" type="text" name="unit" style="width: 300px;height: 15px;" onblur="javaScript:ongoFormOk('unit','checkUnit');" >&nbsp;<span style="color: red" >*</span>
												  <span id="checkUnit" style="display: none;visibility:hidden;" ><font color="red">所属单位不能为空</font></span>
												</td>
											</tr>
											<tr>	
												<td height="20" align="center">单位职务</td>
												<td height="20"><input id="position" type="text" name="position" style="width: 300px;height: 15px;" onblur="javaScript:ongoFormOk('position','checkPosition');" >&nbsp;<span style="color: red" >*</span>
												  <span id="checkPosition" style="display: none;visibility:hidden;" ><font color="red">单位职务不能为空</font></span>
												</td>
										  	</tr>
										  	<tr>
												<td height="20" align="center">客户项目角色</td>
												<td height="20"><select style="width: 300px;" id="custRole" name="custRole">
					                                           <option value="" ></option>
					                                            <option value="项目总监" >项目总监</option>
					                                            <option value="项目经理" >项目经理</option>
					                                            <option value="项目执行经理" >项目执行经理</option>
					                                            <option value="关键用户" >关键用户</option>
					                                            <option value="其他" >其他</option>
												</select>
                                                 </td>
                                            </tr>
                                            <tr>
												<td height="20" align="center">用友项目角色</td>
												<td height="20">
                                                   <select style="width: 300px;" id="ufidaRole" name="ufidaRole">
                                                                <option value="" ></option>
					                                            <option value="项目监理" >项目监理</option>
					                                            <option value="项目总监" >项目总监</option>
					                                            <option value="项目经理" >项目经理</option>
					                                            <option value="项目执行经理" >项目执行经理</option>
					                                            <option value="方案经理" >方案经理</option>
					                                            <option value="顾问" >顾问</option>
					                                            <option value="其他" >其他</option>
					                               </select>
					            
                                                </td>
										  	</tr>
										  	<tr>
												<td height="20" align="center">联系电话</td>
												<td height="20"><input id="tel" type="text" name="tel" style="width: 300px;height: 15px;" maxlength="11" onkeyup="value=value.replace(/[^\d\-]/g,'');" onblur="javaScript:ongoFormOk('tel','checkTel');" >
												<span style="color: red" >*</span>
												<span id="checkTel" style="display: none;visibility:hidden;" ><font color="red">联系电话不能为空</font></span>
												</td>
											</tr>
											<tr>
												<td height="20" align="center">电子邮件</td>
												<td height="20"><input id="email" type="text" name="email" style="width: 300px;height: 15px;" onblur="javaScript:ongoFormOk('email','checkEmail');" >
												<span style="color: red" >*</span>
												<span id="checkEmail" style="display: none;visibility:hidden;" ><font color="red">邮箱地址不正确</font></span>
				
												</td>
										  	</tr>
										  	<tr>
												<td height="20" align="center" valign="top">备注</td>
												<td height="20"><textarea id="notes" name="notes" style="width: 350px;height: 150px;"></textarea>
										  	</tr>
										  
										  	<tr>
												<td height="20" align="center" valign="top"></td>
												<td height="20"><input type="submit" value="提交" onclick="return checkForm();" ></td>
										  	</tr>  
			  							</table>
			  						</td>
			  					</tr>
			  				</table>
			  			</td>
			  		</tr>
				</table>
			</form>
		</td>
	</tr>
</table>
<%@ include file="footer.jsp" %>
</body>
</html>