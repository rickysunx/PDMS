<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%><%
response.setHeader("Cache-Control","no store");
response.setHeader("Pragma","no store");
response.setDateHeader("Expires",0);
String oldVersion = request.getParameter("ver");
String newVersion = "1.01";
if(newVersion.compareTo(oldVersion)>0) {
	out.print("Y|"+newVersion);
} else {
	out.print("N|"+newVersion);
}
%>