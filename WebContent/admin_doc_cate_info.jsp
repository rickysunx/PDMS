<%@ page language="java" pageEncoding="GBK"%>
<%@page import="com.pxl.pkb.biz.Pub"%>
<%@page import="com.pxl.pkb.vo.doc_cate"%>
<%@ page errorPage="ExceptionHandler.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
if(!Pub.isAdminUser(session)) {
	throw new Exception("非管理员用户，无法访问！");
}
%>
<%
DataManagerObject dmo=new DataManagerObject();
String method=request.getParameter("method");
doc_cate docCate=null;
int parentDocCate=0;
if(method!=null&&method.equals("update")){
	docCate=(doc_cate)request.getAttribute("docCate");
	parentDocCate=docCate.getParentDocCate();
}else{
	String type=request.getParameter("type");
	String docCateID=request.getParameter("docCateID");
	if(type.equals("0")){
		parentDocCate=Integer.parseInt(docCateID);
	}else{
		docCate=(doc_cate)dmo.queryByWhere(doc_cate.class,"DocCateID="+docCateID)[0];
		parentDocCate=docCate.getDocCateID();
	}
}
String strDocCateID = request.getParameter("doccateid");
int docCateID = (strDocCateID==null||strDocCateID.trim().length()==0)?0:Integer.parseInt(strDocCateID);
if(Pub.isUnCheckUser(session)) {
	throw new Exception("您的用户尚未通过审核，不能进行新建文档！");
}
%>
<%@page import="com.pxl.pkb.framework.DataManagerObject"%>
<html>
	<head>
		<title>新建文档类别</title>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<meta http-equiv="Pragma" content="no-cache">
		<link href="main.css" rel="stylesheet" type="text/css">
		<link href="css/color.css" rel="stylesheet" type="text/css">
		<script type="text/javascript">
			function checkForm(){
				var docCateName=document.getElementById("docCateName").value;
				if(docCateName==null||docCateName==""){
					alert("文档类别必填");
					return false;
				}else{
					return true;
				}
			}
		</script>
	</head>
	<body>
		<form id="frmDocCate" action="DocCate.do?method=<%=method%>" method="post" onsubmit="return checkForm();">
				<table width="1000" border="0" cellpadding="2" cellspacing="2" class="blockborder">
				<tr class="tableBackGround">
					<td colspan="3"><%if(method.equals("add")){out.print("新建文档类别");}else{out.print("编辑文档类别");} %>
					<input id="docCateID" name="docCateID" type="hidden" value="<%if(method!=null&&method.equals("update")){out.print(docCate.getDocCateID());}%>"/>
				</td></tr>
				<tr>
					<td>类别名称：</td>
					<td>
						<input id="docCateName" name="docCateName" type="text"  value="<%if(method!=null&&method.equals("update")){out.print(docCate.getDocCateName());}%>"/>
					</td>
					<td class="helpRequired">请输入类别名称</td>
				</tr>
				<tr>
					<td>上级节点</td>
					<td>
						<select id="parentDocCate" name="parentDocCate">
						<option value="0">-请选择类别-</option>
						<%
							out.println(Pub.getCateOption("doc_cate","DocCateID","DocCateName","ParentDocCate",docCateID));
						%>
						</select>
					</td>
					<td class="helpRequired">请选择该类别的上级节点，不选则默认为根节点</td>
				</tr>
				<tr>
					<td>是否允许上传文档</td>
					<td><%
						if(method!=null&&method.equals("update")){
							if(docCate.getCanPost().equals("1")){
								out.println("<input type='radio' id='canPost' name='canPost' value='1' checked='checked'/>允许");
								out.println("<input type='radio' id='canPost' name='canPost' value='0'/>不允许");
							}else{
								out.println("<input type='radio' id='canPost' name='canPost' value='1'/>允许");
								out.println("<input type='radio' id='canPost' name='canPost' value='0' checked='checked'/>不允许");
							}
						}else{
							out.println("<input type='radio' id='canPost' name='canPost' value='1'/>允许");
							out.println("<input type='radio' id='canPost' name='canPost' value='0' checked='checked'/>不允许");
						}
					%>
					<td class="helpRequired">请选择该类别是否允许上传文档</td>
				</tr>
				<tr>
					<td>文档类型</td>
					<td><select id="docType" name="docType">
					<%
						if(method!=null&&method.equals("update")){
					%>
						<option value='0' <%if(docCate.getDocType().equals("0")){out.println("selected");}%>>知识类</option>
						<option value='1' <%if(docCate.getDocType().equals("1")){out.println("selected");}%>>产品类</option>
						<option value='2' <%if(docCate.getDocType().equals("2")){out.println("selected");}%>>项目类</option>
					<%
						}else{
					%>
						<option value='0' selected>知识类</option>
						<option value='1'>产品类</option>
						<option value='2'>项目类</option>
					<%		
						}
					%>
					</select></td>
					<td class="helpRequired">请选择该类别文档的文档类型</td>
				</tr>
				<tr>
					<td></td>
					<td><input type="submit" value="确定"/>&nbsp;&nbsp;<input value="返回" type="button" onclick="window.history.back();"/></td>
				</tr>
			</table>
		</form>
		<input type="hidden" id="parentDocCateID" value="<%=parentDocCate%>"/>
		<script type="text/javascript">
			var parentDocCateID=document.getElementById("parentDocCateID").value;
			var options=document.getElementById('parentDocCate').options
			for(var i=0;i<options.length;i++){
				if(options[i].value==parentDocCateID){
					document.getElementById('parentDocCate').selectedIndex=i;
				}
			}
		</script>
	</body>
</html>
