<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%><%
response.setHeader("Cache-Control","no store");
response.setHeader("Pragma","no store");
response.setDateHeader("Expires",0);
String time = (String)request.getAttribute("time");
String text = (String)request.getAttribute("text");
out.print(time+"|"+text);
%>