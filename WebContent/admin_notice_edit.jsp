<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ page errorPage="ExceptionHandler.jsp" %>
<%@page import="com.pxl.pkb.biz.Pub"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
if(!Pub.isAdminUser(session)) {
	throw new Exception("非管理员用户，无法访问！");
}
%>
<%@ page import="com.pxl.pkb.vo.nt_notice" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<link href="main.css" style="text/css" rel="stylesheet">
<link href="css/color.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="ckeditor/ckeditor.js"></script>
<script charset="utf-8" src="editor/kindeditor.js"></script>
<title>编辑通知</title>
<script type="text/javascript">
	function noticeCateControl(){
		var type=document.getElementById("noticeType").value;
		var lblNoticeCate=document.getElementById("lblNoticeCate");
		var txtNoticeCate=document.getElementById("noticeCate");
		var promptNoticeCate=document.getElementById("promptNoticeCate");
		if(type==0){
			lblNoticeCate.style.display="none";
			txtNoticeCate.style.display="none";
			promptNoticeCate.style.display="none";
		}else{
			lblNoticeCate.style.display="block";
			txtNoticeCate.style.display="block";
			promptNoticeCate.style.display="block";
		}
	}
	function check(){
		var _noticeTitle=document.getElementById("noticeTitle").value;
		var _noticeContent=document.getElementById("noticeContent").value;
		var noticeType=document.getElementById("noticeType").value;
		var _noticeCate=document.getElementById("noticeCate").value;
		
		var noticeTitle=_noticeTitle.replace(/\s/g,'');
		var noticeContent=_noticeContent.replace(/\s/g,'');
		var noticeCate=_noticeCate.replace(/\s/g,'');
		if(noticeTitle.length==0||noticeTitle==""){
			alert("通知标题必填");
			return false;
		}else if(noticeTitle.length>50){
			alert("通知标题必需小于50个字符");
			return false;
		}else if(_noticeContent.length==0||_noticeContent==""){
			alert("通知内容必填");
			return false;
		}else if(noticeType==1&&(noticeCate.length==0||noticeCate=="")){
			alert("制度类别必填");
			return false;
		}else{
			return true;
		}
	}
</script>
</head>
<body onload="noticeCateControl();">
	<table>
		<tr>
			<td style="size: 12px;">后台管理&gt;通知&gt;编辑通知</td>
		</tr>
	</table>
	<%
		nt_notice notice=(nt_notice)request.getAttribute("notice");
		String check_0="";
		String check_1="";
		if(notice.getNoticeType()==0){
			check_0="selected";
		}
		else{
			check_1="selected";
		}
	%>
	<form action="NoticeEidt.do" method="post" onsubmit="return check();">
		<table border="0" cellpadding="2" cellspacing="2" class="blockborder">
			<tr class="tableBackGround">
				<td colspan="3"><b><span class="blocktitle">编辑通知</span></b>
					<input type="hidden" id="noticeID" name="noticeID" value="<%=notice.getNoticeID()%>"/>
					<input type="hidden" id="addUser" name="addUser" value="<%=notice.getAddUser()%>"/>
					<input type="hidden" id="addTime" name="addTime" value="<%=notice.getAddTime()%>"/>
				</td>
			</tr>
				
			<tr>
				<td width="80px;">通知主题</td>
				<td>
					<input id="noticeTitle" name="noticeTitle" type="text" size="50" value="<%=notice.getNoticeTitle()%>"/>
				</td>
				<td class="helpRequired">请输入通知主题</td>
			</tr>
			<tr>
				<td>通知内容</td>
				<td><textarea id="noticeContent" name="noticeContent" style="width:700px;height:300px;" cols="20" rows="10"><%=notice.getNoticeContent()%></textarea></td>
				<td class="helpRequired">请输入通知内容
<script>        
KE.show({                
id : 'noticeContent'        
});
</script>
				</td>
			</tr>
			<tr>
				<td>通知类型</td>
				<td>
					<select id="noticeType" name="noticeType" style="width: 152px;" tabindex="<%=notice.getNoticeType()%>" onchange="noticeCateControl();">
						<option value="0" <%=check_0%>>通知</option>
						<option value="1" <%=check_1%>>公司制度</option>
					</select>
				</td>
				<td class="helpRequired">请选择通知类型</td>
			</tr>
			<tr>
				<td><span id="lblNoticeCate">制度类别</span></td>
				<td><input id="noticeCate" name="noticeCate" type="text" size="20" value="<%=notice.getNoticeCate()%>"/></td>
				<td class="helpRequired"><span id="promptNoticeCate">请填写制度类别</span></td>
			</tr>
			<tr>
				<td></td>
				<td colspan="2"><input type="submit" value="确定"/>&nbsp;&nbsp;
				<input type="button" value="取消" onclick="window.history.back();"/></td>
			</tr>
		</table>
	</form>
</body>

</html>
