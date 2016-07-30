<%@page import="com.pxl.pkb.biz.BatchImportThread"%>
<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%><%
response.setHeader("Cache-Control", "no-cache");
out.println("document.getElementById('sUserIndex').innerText='"+(BatchImportThread.userIndex+1)+"';");
out.println("document.getElementById('sUserCount').innerText='"+(BatchImportThread.userCount)+"';");
out.println("document.getElementById('sFileIndex').innerText='"+(BatchImportThread.fileIndex+1)+"';");
out.println("document.getElementById('sFileCount').innerText='"+(BatchImportThread.fileCount)+"';");
out.println("document.getElementById('sCurrInfo').innerText='"+BatchImportThread.currInfo+"';");
%>