<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="core" prefix="pxl"%>
<%@ page errorPage="ExceptionHandler.jsp" %>
<%@page import="com.pxl.pkb.framework.*"%>
<%@page import="com.pxl.pkb.vo.*"%>
<%@page import="com.pxl.pkb.biz.Doc"%>
<%
String strDocCateID = request.getParameter("doccateid");
int docCateID = (strDocCateID==null||strDocCateID.trim().length()==0)?0:Integer.parseInt(strDocCateID);
DataManagerObject dmo = new DataManagerObject();
String docCateName = "文档";
ValueObject [] docCates = dmo.queryByWhere(doc_cate.class,"DocCateID="+docCateID);
doc_cate docCate = null;
if(docCates.length>0) {
	docCate = (doc_cate)docCates[0];
	docCateName = ((doc_cate)docCates[0]).getDocCateName();
}
ValueObject [] docCateChildren = dmo.queryByWhere(doc_cate.class," ParentDocCate="+docCateID);
doc_cate [] catePath = Doc.getDocCatePath(docCateID);

int totalPage=dmo.getTotalPage(doc_doc.class," (DocCateID="+docCateID+
		" or DocID in (select DocID from doc_doccate where CateID="+docCateID+"))  order by UpdateTime desc",20.0);
String strCurrentPage=request.getParameter("currentPage");
int currentPage=strCurrentPage==null?1:Integer.parseInt(strCurrentPage);
String url="doc_list.jsp?doccateid="+strDocCateID;

ValueObject [] docs = dmo.queryByWhereForPage(doc_doc.class," (DocCateID="+docCateID+
		" or DocID in (select DocID from doc_doccate where CateID="+docCateID+"))  order by UpdateTime desc",currentPage,20);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<meta http-equiv="Pragma" content="no-cache">
<title>文档</title>
<link href="main.css" rel="stylesheet" type="text/css">
<link href="css/color.css" rel="stylesheet" type="text/css">
</head>
<body class="body">
<%@ include file="header.jsp" %>
<table class="bodytable" align="center"><tr><td>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr>
<td height="30"><a href="index.jsp">首页</a> &gt; <a href="doc_list.jsp?currentPage=1">文档</a> <%

for(int i=0;i<catePath.length;i++) {
	if(i==catePath.length-1) {
		out.print(" &gt; "+catePath[i].getDocCateName());
	} else {
		out.print(" &gt; <a href=\"doc_list.jsp?currentPage=1&doccateid="+catePath[i].getDocCateID()+"\">"+catePath[i].getDocCateName()+"</a> ");
	}
}

%></td>
</tr>
</table>

<table width="900"  border="0" cellpadding="2" cellspacing="2" class="blockborder">
	  <tr>
		<td height="20" class="tableBackGround">&nbsp;<span class="blocktitle"><%=docCateName %></span></td>
	  </tr>
	  <tr>
		<td height="20"><%
if(docCateChildren.length==0) {
	out.println("无子分类");
} else {
	for(int i=0;i<docCateChildren.length;i++) {
		doc_cate cate = (doc_cate)docCateChildren[i];
		out.println("<a href=doc_list.jsp?currentPage=1&doccateid="+cate.getDocCateID()+">"+cate.getDocCateName()+"</a>&nbsp;");
	}
}
		%></td>
	  </tr>
</table>
<br>
<table width="900"  border="0" cellpadding="0" cellspacing="0">
	  <tr>
		<td height="25" width="20" class="tab_blank">&nbsp;</td>
		<td class="tab_active" width="120">
		<span class="tab_text">文档列表</span>
		</td>
		<td class="tab_blank">&nbsp;</td>
	  </tr>
</table>
<table width="900" border="0" cellspacing="2" cellpadding="0">
  <tr>
    <td width="670" height="25">标题</td>
    <td width="70"><div align="center">操作</div></td>
    <td><div align="center">更新时间</div></td>
  </tr>
<%
if(docs.length==0) {
%>
	<tr>
    	<td height="2" colspan="4"><div class="listdotline"></div></td>
  	</tr>
  	<tr>
    	<td height="25" colspan="4">该分类暂无文档</td>
  	</tr>
<%
} else {
	for(int i=0;i<docs.length;i++) {
		doc_doc doc = (doc_doc) docs[i];
		%>
	<tr>
    	<td height="2" colspan="4"><div class="listdotline"></div></td>
  	</tr>
  	<tr>
  	<td height="25"><%
  	out.print("<img width=16 height=16 src='images/filetype/allfile.png'>");
  	%> <a href='doc_detail.jsp?docid=<%=doc.getDocID() %>' target='_blank'><%=doc.getDocTitle() %></a></td>
    <td><div align="center">
    <a href='DocDownload.do?docid=<%=doc.getDocID()%>&outputid='><img border="0" src="images/icn_download.gif" alt="下载"></a>
    </div></td>
    <td><div align="center"><%=doc.getAddTime() %></div></td>
    </tr>
		<%
	}
}
%>
  <tr><td></td></tr><tr><td><pxl:pageSplit currentPage="<%=currentPage%>" totalPage="<%=totalPage%>" url="<%=url%>"/></td></tr>
</table>
<form name="doc" action="Search.do?searchType=2" method="post">
<table width="900"  border="0" cellpadding="1" cellspacing="1" class="blockborder">
  <tr>
	<td height="20" class="tableBackGround">
		
		<table border="0" cellpadding="0" cellspacing="0" >
		<tr><td width="200" height="30"></td><td><input name="keyword" type="text" style="width: 400px;"><span class="helpRequired">&nbsp;&nbsp;请输入您要搜索的关键字</span></td></tr>
		<tr><td height="30"></td><td><input type="submit" value="搜索文档">
		<input onclick="<%
		
		if(docCate!=null) {
			out.println("window.location.href='doc_add.jsp?doccateid="+docCate.getDocCateID()+"'");
		} else {
			out.println("window.location.href='doc_add.jsp?doccateid=0'");
		}
			
		%>" type="button" value="新建文档"></td></tr>
		</table>
	</td>
  </tr>
</table>
</form>
<br>
</td></tr></table>
<%@ include file="footer.jsp" %>
</body>
</html>