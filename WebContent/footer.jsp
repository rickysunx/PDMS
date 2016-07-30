<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ page errorPage="ExceptionHandler.jsp" %>
<%@page import="com.pxl.pkb.biz.Consts"%>
<table align="center" width="1024" border="0" bgcolor="white" id="footerTable">
	<tr>
		<td style="background-image: url('images/footer_back.gif')" height="28">&nbsp;&nbsp;
			<a class='foota' href='pxl_about.jsp'>关于普信联</a>&nbsp;&nbsp;
			<a class='foota' href='pxl_hr.jsp'>招贤纳士</a>&nbsp;&nbsp;
			<a class='foota' href='#'>联系我们</a>&nbsp;&nbsp;
			<a class='foota' href='#'>版权条约</a>&nbsp;&nbsp;
			<%
			if(session.getAttribute(Consts.PKB_USER_SESSION_NAME)!=null) {
			%>
			<a class='foota' href='pxl_setup.exe'>下载客户端</a>&nbsp;&nbsp;
			<%
			}
			%>
		</td>
	</tr>
	<tr align="center">
		<td height="20"><a href="http://www.miibeian.gov.cn/" target="_blank">京ICP备10049567号</a></td>
	</tr>
</table>