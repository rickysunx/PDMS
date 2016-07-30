<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ page errorPage="ExceptionHandler.jsp"%>
<%@page import="com.pxl.pkb.vo.bd_user"%>
<%@page import="com.pxl.pkb.biz.Consts"%>
<%
	bd_user loginuser = (bd_user) session.getAttribute(Consts.PKB_USER_SESSION_NAME);
%>
<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
<link href="css/smoothness/jquery-ui-1.8.1.custom.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript" src="jquery.pnotify.js"></script>
<link href="jquery.pnotify.default.css" rel="stylesheet" type="text/css" />
<link href="css/color.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="js/popupmsg.js"></script>

<table width="1024"  cellspacing="0" cellpadding="0" align="center" bgcolor="white" id="headerTable">
	<tr>
		<td width="724">
			<table cellspacing="0" cellpadding="0" width="724">
				<tr><td height="25" bgcolor="#ffffff">&nbsp;</td></tr>
				<tr><td height="28" background="images/logo_back.png">&nbsp;&nbsp;
			<a href='index.jsp'><img src="images/logo.png" border="0"></a></td></tr>
			</table>
		</td>
		
		<td width="300" background="images/logonpic.png" valign="<%=(loginuser==null)?"top":"middle" %>">
		
			<table cellspacing="0" cellpadding="0">
				<tr>
					<td width="60"></td>
					<td valign="top">
					<%if(loginuser==null) { %>
					<form action="login.do" method="post" style="margin: 0px;">
						<table border="0" cellspacing="0" cellpadding="0">
						
							<tr><td width="150">
							<table border="0" cellspacing="2" cellpadding="0">
								<tr>
									<td width="46"><span style="color: white;font-size: 12px;"><b>用户名</b></span></td>
									<td width="80"><input id="userCode" name="userCode" type="text" style="width: 80px;height: 12px;font-size: 12px;"/></td>
								</tr>
								<tr>
									<td width="46"><span style="color: white;font-size: 12px;"><b>密　码</b></span></td>
									<td width="80"><input id="passWord" name="passWord" type="password" style="width: 80px;height: 12px;font-size: 12px;"/></td>
								</tr>
							</table>
							</td><td width="100">
							<table>
								<tr><td><input type="image" src="images/logonbtn.png" width="50" height="22" border="0"/></td></tr>
								<tr><td><a href="user_reg.jsp" style="color: red;font-size: 12px;"><b>注册新用户</b></a></td></tr>
							</table>
							</td></tr>
						
						</table>
						</form>
						<% } else {%>	
		<table cellspacing="0" cellpadding="0">
			<tr>
				<td width="8"></td>
				<td width="130"><span style="color: white;font-size: 12px;"><b>当前用户：<%
					if(loginuser!=null){
						out.println(loginuser.getUserName());
					}
				%></b></span></td>
				<td><a href='user_manage.jsp' style="color: white;font-size: 12px;"><b>个人管理</b></a></td>
			</tr>
			<tr>
				<td></td>
				<td><a onclick="return confirm('真的要退出登录？');" href='logout.do' style="color: red;font-size: 12px;"><b>退出登录</b></a></td>
				<td><a href='user_center.jsp' style="color: white;font-size: 12px;"><b>个人信息</b></a></td>
			</tr>
				
			</table>
		<%} %>
					</td>
				</tr>
				
			</table>
		
		</td>
	</tr>
	<tr style="background-image: url('images/chinese_logo_back.png')">
		<td height="25" colspan="2">
			&nbsp;&nbsp;
			<a href='index.jsp'><img src="images/chnlogo.png" border="0"></a>
		</td>
	</tr>
	<%
		if (loginuser != null&&(!loginuser.getIsAdmin().equals("3"))) {
	%>
	<tr style="background-image: url('images/manu_back.png')">
		<td height="28" colspan="2">
			&nbsp;&nbsp;
			<b><a href="index.jsp" class="manu">首页</a>&nbsp;&nbsp;/&nbsp;&nbsp;
				<a href="notice.jsp?currentPage=1" class="manu">通知</a>&nbsp;&nbsp;/&nbsp;&nbsp;
				<%if(loginuser.getIsAdmin().equals("0")||loginuser.getIsAdmin().equals("1")){ %>
				<a href="system.jsp?currentPage=1" class="manu">公司制度</a>&nbsp;&nbsp;/&nbsp;&nbsp;
				<%}%>
				<a href="ask_list.jsp?currentPage=1" class="manu">问答</a>&nbsp;&nbsp;/&nbsp;
				<a href="doc_list.jsp?currentPage=1" class="manu">文档</a>&nbsp;&nbsp;/&nbsp;&nbsp;
				<a href="experts.jsp?currentPage=1" class="manu">专家信息</a>&nbsp;&nbsp;/&nbsp;&nbsp;
				<a href="PPMproject.do?method=list&refproject=list" class="manu">项目</a>&nbsp;&nbsp;/&nbsp;&nbsp;
				<a href="PPMTask.do?method=list" class="manu">工作日志填写</a>&nbsp;&nbsp;/&nbsp;&nbsp;
				<a href="PPMTask.do?method=querylist&projectselid=&memberselid=&startdate=&enddate=" class="manu">工作日志查询</a>
				<%if(loginuser!=null && loginuser.getIsAdmin().equals("1")){%>&nbsp;&nbsp;/&nbsp;&nbsp;
				<a href="admin.jsp" class="manu">后台管理</a>&nbsp;&nbsp;<%}%></b>
		</td>
	</tr>
	<%}%>
</table>


<SCRIPT LANGUAGE="JavaScript">


var time='';

function showMsg(content) {
/*
$.pnotify({pnotify_title: "<span class='mytext'>通知</span>",
			pnotify_text: content
});
*/
popmsg(content);
//window.showModelessDialog("index.jsp",null,"status:no;");
//window.open('index.jsp','newwindow','height=250,width=250,toolbar=no,scrollbars=no,menubar=no');
}

function requestLoop() {
	var xmlHttp;
	
	xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");
	
	xmlHttp.onreadystatechange = function() {
		if(xmlHttp.readyState==4) {
			if(xmlHttp.status==200) {
				var text = xmlHttp.responseText;
				var index = text.indexOf("|");
				time = text.substring(0,index);
				var content = text.substring(index+1,text.length);
				if(content!='') {
					showMsg(content);
				}
				window.setTimeout("requestLoop();",6000);
			}
		}
	}
	
	xmlHttp.open("GET","NotifyGet.do?type=0"+((time=='')?'':'&time='+time),true);
	xmlHttp.send(null);
	
}

//window.setTimeout("requestLoop();",3000);

</SCRIPT>
