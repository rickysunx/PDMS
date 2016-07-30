<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib prefix="pxl" uri="core"%>
<%@ page errorPage="ExceptionHandler.jsp" %>
<%@page import="com.pxl.pkb.framework.DataManagerObject"%>
<%@page import="com.pxl.pkb.vo.bd_userscore"%>
<%@page import="com.pxl.pkb.framework.ValueObject"%>
<%@page import="com.pxl.pkb.vo.ask_qst"%>
<%@page import="com.pxl.pkb.vo.ask_cate"%>
<%@page import="com.pxl.pkb.biz.Ask"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
DataManagerObject dmo = new DataManagerObject();
bd_user user=(bd_user)session.getAttribute(Consts.PKB_USER_SESSION_NAME);
Object[][] userscorevos= dmo.querySQL("select sum(userScore) from bd_userscore where userid="+user.getUserID());
%>
<%@page import="com.pxl.pkb.vo.doc_doc"%>
<%@page import="com.pxl.pkb.vo.bd_operanote"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<meta http-equiv="Pragma" content="no-cache">
<title>用户积分</title>
<link href="main.css" rel="stylesheet" type="text/css">
<link href="css/color.css" rel="stylesheet" type="text/css">
</head>
<body class="body">

<%
String strAskType = request.getParameter("askType");
String strCurrentPage=request.getParameter("currentPage");
int askType = (strAskType==null||strAskType.trim().length()==0)?0:Integer.parseInt(strAskType);
int currentPage=strCurrentPage==null?1:Integer.parseInt(strCurrentPage);

int totalPage=0;
ValueObject [] askQsts =null;
String url=null;
if(askType==0){
	totalPage=dmo.getTotalPage(ask_qst.class,"adduser="+user.getUserID()+" order by addtime desc",20.0);
	url="user_center.jsp?askType="+askType;
	askQsts = dmo.queryByWhereForPage(ask_qst.class,"adduser="+user.getUserID()+" order by addtime desc",currentPage,20);
}else if(askType==1){
	totalPage=dmo.getTotalPage(ask_qst.class,"qstID in(select qstid from ask_answer where adduser="+user.getUserID()+")",20.0);
	url="user_center.jsp?askType="+askType;
	askQsts = dmo.queryByWhereForPage(ask_qst.class,"qstID in(select qstid from ask_answer where adduser="+user.getUserID()+")",currentPage,20);
}else if(askType==2){
	totalPage=dmo.getTotalPage(doc_doc.class,"adduser="+user.getUserID()+" order by addtime desc",20.0);
	url="user_center.jsp?askType="+askType;
	askQsts = dmo.queryByWhereForPage(doc_doc.class,"adduser="+user.getUserID()+" order by addtime desc",currentPage,20);
}else{
	totalPage=dmo.getTotalPage(bd_userscore.class,"userid="+user.getUserID()+" order by addtime desc",20.0);
	url="user_center.jsp?askType="+askType;
	askQsts = dmo.queryByWhereForPage(bd_userscore.class,"userid="+user.getUserID()+" order by addtime desc",currentPage,20);
}
%>
<%@ include file="header.jsp" %>
<table class="bodytable" align="center"><tr><td>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr>
<td height="30"><a href="index.jsp">首页</a> &gt; 用户中心 </td>
</tr>
</table>
<table border="0" cellpadding="5" cellspacing="0" class="blockborder" width="400px">
	<tr>
		<td colspan="4" class="tableBackGround">用户积分</td>
	</tr>
	<tr>
		<td width="120px">注册时间</td>
		<td width="100px"><%=user.getAddTime() %></td>
		<td width="100px">注册IP</td>
		<td width="120px"><%=user.getAddIP() %></td>
	</tr>
	<tr>
		<td>用户积分</td>
		<td>
		<%if(userscorevos.length==0){
			out.print("0");
		}else{
			out.print(userscorevos[0][0]);		
		} 
		%>
		</td><td></td><td></td>
	</tr>
</table><br/>
<table width="600"  border="0" cellpadding="0" cellspacing="0">
	  <tr>
		<td height="25" width="20" class="tab_blank">&nbsp;</td>
		<%
		if(askType==0) {
			out.print("<td class=\"tab_active\" width=\"120\">");
			out.print("<span class=\"tab_text\">我的提问</span>");
		} else {
			out.print("<td class=\"tab_normal\" width=\"120\">");
			out.print("<a href=\"user_center.jsp?askType=0\"><span class=\"tab_text\">我的提问</span></a>");
		}
		out.print("</td>");
		%>
		<td height="25" width="20" class="tab_blank">&nbsp;</td>
		<%
		if(askType==1) {
			out.print("<td class=\"tab_active\" width=\"120\">");
			out.print("<span class=\"tab_text\">我回答过的问题</span>");
		} else {
			out.print("<td class=\"tab_normal\" width=\"120\">");
			out.print("<a href=\"user_center.jsp?askType=1\"><span class=\"tab_text\">我回答过的问题</span></a>");
		}
		out.print("</td>");
		%>
		<td height="25" width="20" class="tab_blank">&nbsp;</td>
		<%
		if(askType==2) {
			out.print("<td class=\"tab_active\" width=\"120\">");
			out.print("<span class=\"tab_text\">我上传的文档</span>");
		} else {
			out.print("<td class=\"tab_normal\" width=\"120\">");
			out.print("<a href=\"user_center.jsp?askType=2\"><span class=\"tab_text\">我上传的文档</span></a>");
		}
		out.print("</td>");
		%>
		<td height="25" width="20" class="tab_blank">&nbsp;</td>
		<%
		if(askType==3) {
			out.print("<td class=\"tab_active\" width=\"120\">");
			out.print("<span class=\"tab_text\">我的积分记录</span>");
		} else {
			out.print("<td class=\"tab_normal\" width=\"120\">");
			out.print("<a href=\"user_center.jsp?askType=3\"><span class=\"tab_text\">我的积分记录</span></a>");
		}
		out.print("</td>");
		%>
		<td height="25" width="20" class="tab_blank">&nbsp;</td>
	  </tr>
</table>
<table width="600" border="0" cellspacing="2" cellpadding="0">
  <tr>
      	<%
	    if(askType==0||askType==1){
	    	out.println("<td width='330' height='25'>标题</td>");
	    	out.println("<td width='70'><div align='center'>回答数</div></td>");
	    	out.println("<td width='50'><div align='center'>状态</div></td>");
	    	out.println("<td><div align='center'>提问时间</div></td>");
	    }else if(askType==2){
	    	out.println("<td width='330' height='25'>标题</td>");
	    	out.println("<td width='70'><div align='center'>点击数</div></td>");
	    	out.println("<td width='50'><div align='center'>下载数</div></td>");
	    	out.println("<td><div align='center'>上传时间</div></td>");    	
	    }else{
	    	out.println("<td width='200' height='25'>加分数</td>");
	    	out.println("<td width='200'><div align='center'>加分原因</div></td>");
	    	out.println("<td><div align='center'>加分时间</div></td>");    
	    }
  	%>
  </tr>
<%
if(askQsts.length==0) {
%>
  <tr>
    <td height="2" colspan="4"><div class="listdotline"></div></td>
  </tr>
   <tr>
    <td height="25" colspan="4">该分类暂无问题</td>
  </tr>
<%
} else {
	for(int i=0;i<askQsts.length;i++) {
		if(askType==0||askType==1){
			ask_qst qst = (ask_qst)askQsts[i];
%>
  <tr>
    <td height="2" colspan="4"><div class="listdotline"></div></td>
  </tr>
  <tr>
    <td height="25"><div align="left"><a href='ask_qst_view.jsp?qstid=<%=qst.getQstID()%>' target='_blank'><%=qst.getQstTitle() %></a></div></td>
    <td><div align="center"><%=qst.getReplyCount() %></div></td>
    <td><div align="center"><%=qst.getQstStatus().equals("0")?
    		"<img border=0 src='images/icn_time.gif'>":
    		"<img border=0 src='images/icn_ok.gif'>" %></div></td>
    <td><div align="center"><%=qst.getAddTime() %></div></td>
  </tr>
<%}else if(askType==2){
	doc_doc doc = (doc_doc)askQsts[i];
%>
  <tr>
    <td height="2" colspan="4"><div class="listdotline"></div></td>
  </tr>
  <tr>
    <td height="25"><div align="left"><a href='doc_detail.jsp?docid=<%=doc.getDocID()%>' target='_blank'><%=doc.getDocTitle()%></a></div></td>
    <td><div align="center"><%=doc.getClickCount()%></div></td>
    <td><div align="center"><%=doc.getDownloadCount()%></div></td>
    <td><div align="center"><%=doc.getAddTime() %></div></td>
  </tr>
<%}else{
	bd_userscore userscore=(bd_userscore)askQsts[i];
%>
	  <tr>
    <td height="2" colspan="4"><div class="listdotline"></div></td>
  </tr>
  <tr>
    <td height="25"><div align="left"><%=userscore.getUserScore()%></div></td>
    <td><div align="center"><%=userscore.getScoreContent()%></div></td>
    <td><div align="center"><%=userscore.getAddTime() %></div></td>
  </tr>		
<%		}
	}
}
%>
<tr><td></td></tr><tr><td><pxl:pageSplit url="<%=url%>" currentPage="<%=currentPage%>" totalPage="<%=totalPage%>"/></td></tr>
</table>
</td></tr></table>
<%@ include file="footer.jsp" %>
</body>
</html>