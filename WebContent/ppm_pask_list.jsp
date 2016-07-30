<%@ page language="java" contentType="text/html;charset=GBK" pageEncoding="GBK"%>
<%@page import="java.util.*,com.pxl.pkb.biz.Pub"%>
<%@page import="com.pxl.pkb.vo.*,com.pxl.pkb.extendvo.*,com.pxl.pkb.biz.Consts"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
String path = request.getContextPath();
String seqnumber = Pub.getCurrDate();
String[] arrs = seqnumber.split("-");
String seqstrnum="";
for(int i=0;i<arrs.length;i++){
	seqstrnum+=arrs[i];
}
%>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<meta http-equiv="Pragma" content="no-cache">
<title>项目</title>
<link href="main.css" rel="stylesheet" type="text/css">
<link href="css/color.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="js/checkOnlyOne.js"></script>
<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
<script type='text/javascript' src='/pkb/dwr/interface/mms.js'></script>
<script type='text/javascript' src='/pkb/dwr/engine.js'></script>
<script type='text/javascript' src='/pkb/dwr/util.js'></script>
<script type="text/javascript" src="js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="upload.js"></script>
<script type="text/javascript" src="js/city.js"></script>
<script type="text/javascript" src="<%=path%>/js/swfupload.js" ></script>
<script type="text/javascript" src="<%=path%>/js/queue.js"></script>
<script type="text/javascript" src="<%=path%>/js/fileprogress.js" ></script>
<script type="text/javascript" src="<%=path%>/js/handlers.js" charset="utf-8"></script>
<link href="<%=path%>/css/default.css" rel="stylesheet" type="text/css" />
<style type="text/css">
.tab{
	cellSpacing:expression(this.cellSpacing=0);   
	cellPadding:expression(this.cellPadding=0);
	margin-top: 5px;
	border: 1px solid #D5D5D5;
	border-bottom:1px solid #D5D5D5;
	border-left:1px solid #D5D5D5;
	border-collapse:collapse;
	text-align:center
}
.tab td {
	margin:2px;
	height:25px;
	border-bottom: 1px solid #D5D5D5;
	border-left: 1px solid #D5D5D5;
}
.TableHeader {
	BACKGROUND: #D5D5D5;
	font-weight: bold;
	height:28px;
}
</style>
<script type="text/javascript">
var addrownumber =0;
var rowss=0;
var divnumber=0;

function getcity(currProvince,numbercity){ 
	 var currProvincecurrProvince = currProvince; 
	 var i,j,k; 
	document.getElementById("city"+numbercity).length = 0 ;
	for (i = 0 ;i <cityArray.length;i++){    
		   if(cityArray[i][0]==currProvince){    
				tmpcityArray = cityArray[i][1].split("|") 
				for(j=0;j<tmpcityArray.length;j++) 
					document.getElementById("city"+numbercity).options[document.getElementById("city"+numbercity).length] = new Option(tmpcityArray[j],tmpcityArray[j]);                            
			}
			
	} 
} 
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
var filerow=0;
var table1nameber;
var row1number=0;
var table2nameber;
var row2number=0;
var table3nameber;
var row3number=0;
var taskIdd=0;
function addrow(tab,taskId){
 if(null!=tab&&''!=tab&&tab!='undefined'){
  var table = document.getElementById(tab+taskId);
  var row =table.insertRow();
  if(tab=='table1'){
    if(table1nameber!=null&&table1nameber!=''&&'undefined'!=table1nameber&&table1nameber.length!=0){
       row1number=table1nameber.substring(8,table1nameber.length); 
       table1nameber="";
    }
    row1number++;
    
    var cell0=row.insertCell();
    var cell1=row.insertCell();  
    var cell2=row.insertCell();
    var cell3=row.insertCell();
    cell0.innerHTML="<input type='text' name='seqnumadd' id='seqnum' readonly='readonly' value='<%=seqstrnum%>"+row1number+"' style='width:100%;height:100%' />"
    cell1.innerHTML="<input type='text' name='detailContentadd' id='detailContent' style='width:100%;height:100%' />"
    cell2.innerHTML="<select id='workAddress' name='workAddressadd' onchange='getcity(this.options[this.selectedIndex].value,"+row1number+")'><option value='北京市'>北京市</option><option value='上海市'>上海市</option> <option value='天津市'>天津市</option> <option value='重庆市'>重庆市</option> <option value='河北省'>河北省</option><option value='山西省'>山西省</option> <option value='内蒙古自治区'>内蒙古自治区</option><option value='辽宁省'>辽宁省</option><option value='吉林省'>吉林省</option><option value='黑龙江省'>黑龙江省</option> <option value='江苏省'>江苏省</option><option value='浙江省'>浙江省</option><option value='安徽省'>安徽省</option> <option value='福建省'>福建省</option> <option value='江西省'>江西省</option> <option value='山东省'>山东省</option><option value='河南省'>河南省</option> <option value='湖北省'>湖北省</option><option value='湖南省'>湖南省</option><option value='广东省'>广东省</option><option value='广西壮族自治区'>广西壮族自治区</option><option value='海南省'>海南省</option><option value='四川省'>四川省</option><option value='贵州省'>贵州省</option> <option value='云南省'>云南省</option><option value='西藏自治区'>西藏自治区</option><option value='陕西省'>陕西省</option><option value='甘肃省'>甘肃省</option><option value='宁夏回族自治区'>宁夏回族自治区</option><option value='青海省'>青海省</option><option value='新疆维吾尔族自治区'>新疆维吾尔族自治区</option><option value='香港特别行政区'>香港特别行政区</option><option value='澳门特别行政区'>澳门特别行政区</option><option value='台湾省'>台湾省</option> </select><select id='city"+row1number+"' name='city'></select>"
    cell3.innerHTML="<img src='images/b_drop.png' onclick='javaScript:deleteRow(this);' style='cursor: hand' />"
  
  }
   if(tab=='table2'){
    if(table2nameber!=null&&table2nameber!=''&&'undefined'!=table2nameber&&table2nameber.length!=0){
       row2number=table2nameber.substring(8,table2nameber.length); 
       table2nameber="";
    }
    row2number++;
    var cell0=row.insertCell();
    var cell1=row.insertCell();  
    var cell2=row.insertCell();
    var cell3=row.insertCell();
    var cell4=row.insertCell();
    var cell5=row.insertCell();
    var cell6=row.insertCell();
    var cell7=row.insertCell();
    cell0.innerHTML="<input type='text' name='problemCodeadd' id=''readonly='readonly' value='<%=seqstrnum%>"+row2number+"' style='width:100%;height:100%' />"
    cell1.innerHTML="<input type='text' name='descriptionadd' id='' style='width:100%;height:100%' />"
    cell2.innerHTML="<input type='text' name='planaddadd' id='' style='width:100%;height:100%' />"
    cell3.innerHTML="<input type='hidden' name='memberIdadd' id='memberId"+addrownumber+"' /><input type=text name=userName id=userName"+addrownumber+" style='width:60%;height:100%' disabled='disabled' />&nbsp;<img src='images/choose.gif' style='cursor: hand;'  onclick=showDialog('PPMTask.do?method=choseCharge&opercharg=charg&addmody=add&taskId="+taskId+"&addrownumber="+addrownumber+"','620','290'); >"
    cell4.innerHTML="<input type='hidden' name='useroperadd' id='useroperId"+addrownumber+"'/><input type=text name=useroperName id=useroperName"+addrownumber+" style='width:60%;height:100%' disabled='disabled'/>&nbsp;<img src='images/choose.gif' style='cursor: hand;'  onclick=showDialog('PPMTask.do?method=choseCharge&opercharg=oper&addmody=add&taskId="+taskId+"&addrownumber="+addrownumber+"','620','290'); >"
    cell5.innerHTML="<input type='text' name='planTimeadd' id='' style='width:100%;height:100%' onfocus=WdatePicker({dateFmt:'yyyy-MM-dd'}); />"
    cell6.innerHTML="<select id='statu' name='statuadd' style='width:90%;height:100%' ><option value='0'>已解决</option><option value='1'>未解决</option></select>"
    cell7.innerHTML="<img src='images/b_drop.png' onclick='javaScript:deleteRow(this);' style='cursor: hand' />"
    addrownumber++;
  }
  if(tab=='table3'){
    filerow=table.rows.length-2;
     if(table3nameber!=null&&table3nameber!=''&&'undefined'!=table3nameber&&table3nameber.length!=0){
       row3number=table3nameber.substring(8,table3nameber.length); 
       table3nameber="";
    }
    row3number++;
   var cell0=row.insertCell();
   var cell1=row.insertCell();
   var cell2 = row.insertCell();
   var cell3 = row.insertCell();
   cell2.setAttribute("width","200"); 
   cell0.innerHTML="<input type='text' name='seqnumadd' id='' readonly='readonly' value='<%=seqstrnum%>"+row3number+"' style='width:100%;height:100%' />"
   cell1.innerHTML="<input type='text' name='outputadd' id='' style='width:100%;height:100%' />"
   cell2.innerHTML="<span id='uploadFilesDiv' width='20'></span><span width='20' id='fsUploadProgress"+filerow+taskId+"'></span>&nbsp;<div><span id='spanButtonPlaceHolder'></span><input id='btnCancel' type='hidden' /></span>&nbsp;"
   cell3 .innerHTML="<img src='images/b_drop.png' onclick='javaScript:deleteRow(this);' style='cursor: hand' />"
   fileupload(filerow,taskId);
  }
  }
  return false;
}

function tdClicks(id,name,font,taskId){
   if(null!=id&&''!=id&&id!='undefined'){
   document.getElementById(font+taskId+id).style.display='none'; 
   document.getElementById(font+taskId+id).style.visibility='hidden';
   document.getElementById(name+taskId+id).style.display='';
   document.getElementById(name+taskId+id).style.visibility='visible';
    
  }
   return false;
}
function tdClickProblem(id,name,font,taskId,img){
   if(null!=id&&''!=id&&id!='undefined'){
   document.getElementById(font+taskId+id).style.display='none';
   document.getElementById(font+taskId+id).style.visibility='hidden';
   document.getElementById(name+taskId+id).style.display='';
   document.getElementById(name+taskId+id).style.visibility='visible';
   document.getElementById(img+id).style.display='';
   document.getElementById(img+id).style.visibility='visible';
   }
}
function deleteRow(obj){
 if(confirm('您确定要删除此行信息')){
   var mytr=obj.parentNode.parentNode;
   var mytable=obj.parentNode.parentNode.parentNode.parentNode;
   mytable.lastChild.removeChild(mytr);
   rowIndex--;
  
 }
}
function opendiv(id,taskId){
     closediv(divnumber);
     divnumber = id;
  if(null!=taskId&&''!=taskId&&taskId!='undefined'){
      var userId = document.getElementById("userId").value;
      var strs = new Array(taskId,userId);
       mms.getWrokdetailByTaskId(strs,callbackWorkDetail);
       mms.getOutputlist(taskId,callbackoutput);
       mms.getProblemlist(taskId,callbackProblem); 
  }
  if(null!=id&&''!=id&&id!='undefined'){
   document.getElementById('div'+id).style.display='';
   document.getElementById('div'+id).style.visibility='visible';
    document.getElementById('imgopen'+id).style.display='none';
   document.getElementById('imgopen'+id).style.visibility='hidden';
    document.getElementById('imgclose'+id).style.display='';
   document.getElementById('imgclose'+id).style.visibility='visible';
  }
}
function closediv(id){
  if(null!=id&&''!=id&&id!='undefined'){
   document.getElementById('div'+id).style.display='none';
   document.getElementById('div'+id).style.visibility='hidden';
    document.getElementById('imgopen'+id).style.display='';
   document.getElementById('imgopen'+id).style.visibility='visible';
    document.getElementById('imgclose'+id).style.display='none';
   document.getElementById('imgclose'+id).style.visibility='hidden';
  }
}

function callbackWorkDetail(data){
      if(null!=data&&''!=data){
        var table = document.getElementById("table1"+data[0].taskID);
	    var rowNum = table.rows.length;
       for(var i=rowNum-1;i>=1;i--){
        table.deleteRow(i);
        }
       for(var i=0;i<data.length;i++){
         var s =i+1;
        var row =table.insertRow();
        var cell0=row.insertCell();
        var cell1=row.insertCell();  
        var cell2=row.insertCell();
        var cell3=row.insertCell();
        cell0.innerHTML="<span onclick=tdClicks('"+i+"','seqnum','fonseq','"+data[i].taskID+"')><font id='fonseq"+data[i].taskID+i+"'>"+data[i].seqNum+"</font><input type=hidden name='workdetailIdlist' id='workdetailIdlist"+i+"' value='"+data[i].workDetailID+"'><input value='"+data[i].seqNum+"' type='text' id='seqnum"+data[i].taskID+i+"' name='seqnumlist' style='width: 100%;height: 100%;visibility: hidden;display: none;'></span>"
        cell1.innerHTML="<span onclick=tdClicks('"+i+"','detailContent','fondet','"+data[i].taskID+"')><font id='fondet"+data[i].taskID+i+"'>"+data[i].detailContent+"</font><input value='"+data[i].detailContent+"' type='text' id='detailContent"+data[i].taskID+i+"' name='detailContentlist' style='width: 100%;height: 100%;visibility: hidden;display: none;'></span>"
        cell2.innerHTML="<span onclick=tdClicksWrokAddress('"+i+"','workAddresslist','fonwork','"+data[i].taskID+"')><font id='fonwork"+data[i].taskID+i+"'>"+data[i].workAddress+"</font><input type='hidden' name='workAddresslist' value="+data[i].workAddress+" ><select id='workAddresslist"+data[i].taskID+i+"' style='width: 50%;height: 100%;visibility: hidden;display: none;' name='company' onchange='getcity(this.options[this.selectedIndex].value,"+data[i].taskID+i+")'><option value='北京市'>北京市</option><option value='上海市'>上海市</option> <option value='天津市'>天津市</option> <option value='重庆市'>重庆市</option> <option value='河北省'>河北省</option><option value='山西省'>山西省</option> <option value='内蒙古自治区'>内蒙古自治区</option><option value='辽宁省'>辽宁省</option><option value='吉林省'>吉林省</option><option value='黑龙江省'>黑龙江省</option> <option value='江苏省'>江苏省</option><option value='浙江省'>浙江省</option><option value='安徽省'>安徽省</option> <option value='福建省'>福建省</option> <option value='江西省'>江西省</option> <option value='山东省'>山东省</option><option value='河南省'>河南省</option> <option value='湖北省'>湖北省</option><option value='湖南省'>湖南省</option><option value='广东省'>广东省</option><option value='广西壮族自治区'>广西壮族自治区</option><option value='海南省'>海南省</option><option value='四川省'>四川省</option><option value='贵州省'>贵州省</option> <option value='云南省'>云南省</option><option value='西藏自治区'>西藏自治区</option><option value='陕西省'>陕西省</option><option value='甘肃省'>甘肃省</option><option value='宁夏回族自治区'>宁夏回族自治区</option><option value='青海省'>青海省</option><option value='新疆维吾尔族自治区'>新疆维吾尔族自治区</option><option value='香港特别行政区'>香港特别行政区</option><option value='澳门特别行政区'>澳门特别行政区</option><option value='台湾省'>台湾省</option> </select><select id='city"+data[i].taskID+i+"' name='citylist' style='width: 50%;height: 100%;visibility: hidden;display: none;' ></select></span>"
        cell3.innerHTML="<img src='images/b_drop.png' onclick=javaScript:delework('"+data[i].workDetailID+"',"+s+"); style='cursor: hand' />"
        table1nameber=""+data[data.length-1].seqNum+"";
       }
	}
}
function tdClicksWrokAddress(id,name,font,taskId){
if(null!=id&&''!=id&&id!='undefined'){
   document.getElementById(font+taskId+id).style.display='none'; 
   document.getElementById(font+taskId+id).style.visibility='hidden';
   document.getElementById(name+taskId+id).style.display='';
   document.getElementById(name+taskId+id).style.visibility='visible';
   document.getElementById('city'+taskId+id).style.display='';
   document.getElementById('city'+taskId+id).style.visibility='visible';
  }

}
function delework(workdetailID,obj){
	if(confirm('您确定要删除此行信息')){
    $.post("AJAXAction.do?method=detelWorkdetatil&workdetailID="+workdetailID+"&obj="+obj,null,deteWorkDetail);
 }
}
function saveworkDetail(number){
   var seqnumlist = document.getElementsByName("seqnumlist");
   var detailContentlist = document.getElementsByName("detailContentlist");
   var company = document.getElementsByName("company");
   var citylist = document.getElementsByName("citylist");
   var workAddresslist = document.getElementsByName("workAddresslist");
   var seqnumadd = document.getElementsByName("seqnumadd");
   var detailContentadd = document.getElementsByName("detailContentadd");
   var workAddressadd = document.getElementsByName("workAddressadd");
   var workdetailIdlist = document.getElementsByName("workdetailIdlist");
   var city = document.getElementsByName("city");
   var allstrs = new Array(new Array(),new Array(),new Array(),new Array(),new Array(),new Array(),new Array(),new Array(),new Array(),new Array(),new Array());
   for(i=0;i<seqnumlist.length;i++){
       allstrs[0][i]=seqnumlist[i].value;
       allstrs[1][i]=detailContentlist[i].value;
       allstrs[2][i]=workAddresslist[i].value;
       allstrs[7][i]=workdetailIdlist[i].value
       allstrs[9][i]=company[i].value;
       allstrs[10][i]=citylist[i].value;
   }
   for(i=0;i<seqnumadd.length;i++){
      allstrs[3][i]=seqnumadd[i].value;
      allstrs[4][i]=detailContentadd[i].value;
      allstrs[5][i]=workAddressadd[i].value;
      allstrs[8][i]=city[i].value;
   }
   allstrs[6][0]=document.getElementById("taskId"+number).value;
   allstrs[6][1]=document.getElementById("userId"+number).value;
   allstrs[6][2] =number;
   mms.saveWorkdetatil(allstrs,savebackworkdetail);

}
function savebackworkdetail(data){
  if(null!=data&&''!=data&&data!='undefined'){
     opendiv(data[0],data[1]);
  }
}

function callbackoutput(data){
   if(data==null||''==data||'undefined'==data){
     rowss=0;
   }
  if(null!=data&&''!=data){
        var table = document.getElementById("table3"+data[0].taskID);
	    var rowNum = table.rows.length;
       for(var i=rowNum-1;i>=1;i--){
        table.deleteRow(i);
        }
         rowss=data.length;
    for(var i=0;i<data.length;i++){
        var s =i+1;
       var attachname =data[i].attachname;
        var row =table.insertRow();
        var cell0=row.insertCell();
        var cell1=row.insertCell();
        var cell2 = row.insertCell();
        var cell3 = row.insertCell();
        cell2.setAttribute("width","200");  
        cell0.innerHTML="<span onclick=tdClicks('"+i+"','seqnums','fonseqs','"+data[i].taskID+"')><font id='fonseqs"+data[i].taskID+i+"'>"+data[i].seqNum+"</font><input type=hidden name='outputIdlist' id='outputIdlist"+i+"' value='"+data[i].outputID+"'><input value='"+data[i].seqNum+"' type='text' id='seqnums"+data[i].taskID+i+"' name='seqnumslist' style='width: 100%;height: 100%;visibility: hidden;display: none;'></span>";
        cell1.innerHTML="<span onclick=tdClicks('"+i+"','outputName','fonoutName','"+data[i].taskID+"')><font id='fonoutName"+data[i].taskID+i+"'>"+data[i].outputName+"</font><input value='"+data[i].outputName+"' type='text' id='outputName"+data[i].taskID+i+"' name='outputNamelist' style='width: 100%;height: 100%;visibility: hidden;display: none;'></span>";
        cell2.innerHTML="<span id='uploadFilesDiv' width='20'></span><span width='20' id='fsUploadProgress"+i+data[i].taskID+"'></span>&nbsp;<div><span id='spanButtonPlaceHolder'></span><input id='btnCancel' type='hidden'/></span>&nbsp;<a href='DownloadFile?outputid="+data[i].outputID+"'>下载</a>"
        cell3.innerHTML="<img src='images/b_drop.png' onclick=javaScript:deleoutput('"+data[i].outputID+"',"+s+"); style='cursor: hand' />";
        table3nameber=""+data[data.length-1].seqNum+"";
        fileupload(i,data[i].taskID);
       }    
     }
  
}
function deleoutput(outputId,obj){
if(confirm('您确定要删除此行信息')){
    $.post("AJAXAction.do?method=deleoutput&outputID="+outputId+"&obj="+obj,null,callbackdeteoutput);
 }
}

function callbackProblem(data){
if(null!=data&&''!=data){
        var table = document.getElementById("table2"+data[0].taskID);
	    var rowNum = table.rows.length;
       for(var i=rowNum-1;i>=1;i--){
        table.deleteRow(i);
       }
    for(var i=0;i<data.length;i++){
      var args = data[i].useroper;
      var useroper =args;
      var str =new Array();
        var m=0;
        for(var j=0;j<useroper.length;j++){
           if(''!=useroper[j]&&null!=useroper[j]&&'null'!=useroper[j]){
             str[m]=useroper[j];
              m++;
           }
        }
         var s =i+1;
        var status = data[i].problemStatus;
        var row =table.insertRow();
        var cell0=row.insertCell();
        var cell1=row.insertCell();  
        var cell2=row.insertCell();
        var cell3=row.insertCell();
        var cell4=row.insertCell();
        var cell5=row.insertCell();
        var cell6=row.insertCell();
        var cell7=row.insertCell();
        cell0.innerHTML="<span onclick=tdClicks('"+i+"','problemCode','foncode','"+data[i].taskID+"')><font id='foncode"+data[i].taskID+i+"'>"+data[i].problemCode+"</font><input type=hidden name='problemIdlist' id='problemIdlist"+i+"' value='"+data[i].problemID+"'><input value='"+data[i].problemCode+"' type='text' id='problemCode"+data[i].taskID+i+"' name='problemCodelist' style='width: 100%;height: 100%;visibility: hidden;display: none;'></span>"
        cell1.innerHTML="<span onclick=tdClicks('"+i+"','description','fondes','"+data[i].taskID+"')><font id='fondes"+data[i].taskID+i+"'>"+data[i].description+"</font><input value='"+data[i].description+"' type='text' id='description"+data[i].taskID+i+"' name='descriptionlist' style='width: 100%;height: 100%;visibility: hidden;display: none;'></span>"
        cell2.innerHTML="<span onclick=tdClicks('"+i+"','plan','fonplan','"+data[i].taskID+"')><font id='fonplan"+data[i].taskID+i+"'>"+data[i].plan+"</font><input value='"+data[i].plan+"' type='text' id='plan"+data[i].taskID+i+"' name='planlist' style='width: 100%;height: 100%;visibility: hidden;display: none;'></span>"
        cell3.innerHTML="<span onclick=tdClickProblem('"+i+"','charger','foncharger','"+data[i].taskID+"','imgChar')><font id='foncharger"+data[i].taskID+i+"'>"+data[i].chargerName+"</font><input type=hidden name='memberIdlist' value='"+data[i].charger+"'  id='memberId"+data[i].taskID+i+"'>  <input value='"+data[i].chargerName+"' type='text' id='charger"+data[i].taskID+i+"' name='chargerlist' disabled='disabled' style='width: 60%;height: 100%;visibility: hidden;display: none;'>&nbsp;<img src='images/choose.gif' id='imgChar"+i+"' style='cursor: hand;visibility: hidden;display: none;'  onclick=showDialog('PPMTask.do?method=choseCharge&opercharg=charg&addmody=mody&taskId="+data[i].taskID+"&addrownumber="+i+"','620','290');></span> "
        cell4.innerHTML="<span onclick=tdClickProblem('"+i+"','useroper','fonuseroper','"+data[i].taskID+"','imgOper')><font id ='fonuseroper"+data[i].taskID+i+"'>"+str+"</font><input type='hidden' name='useroperIdlist' value='"+data[i].useroperid+"'  id='useroperId"+data[i].taskID+i+"'/><input value='"+str+"' type='text' id='useroper"+data[i].taskID+i+"' name='useroperlist' disabled='disabled' style='width: 60%;height: 100%;visibility: hidden;display: none;'> &nbsp;<img src='images/choose.gif' style='cursor: hand;visibility: hidden;display: none;' id='imgOper"+i+"'  onclick=showDialog('PPMTask.do?method=choseCharge&opercharg=oper&addmody=mody&taskId="+data[i].taskID+"&addrownumber="+i+"','620','290'); ></span>"
        cell5.innerHTML="<span onclick=tdClicks('"+i+"','planTime','fonplanTime','"+data[i].taskID+"')><font id='fonplanTime"+data[i].taskID+i+"'>"+data[i].planTime+"</font><input value='"+data[i].planTime+"' type='text' id='planTime"+data[i].taskID+i+"' name='planTimelist' style='width: 100%;height: 100%;visibility: hidden;display: none;' onfocus=WdatePicker({dateFmt:'yyyy-MM-dd'}); ></span>"
        if(status=='0'){
         cell6.innerHTML="<span onclick=tdClicks('"+i+"','status','fonstatus','"+data[i].taskID+"')><font id='fonstatus"+data[i].taskID+i+"'>已解决</font><input type='hidden' value='"+status+"' name='problemsta' id='problemsta' ><select id='status"+data[i].taskID+i+"' name='statuslist"+data[i].taskID+"' style='width: 90%;height: 100%;visibility: hidden;display: none;' ><option value=''></option><option value='0'>已解决</option><option value='1'>未解决</option></select></span>"
        }
        if(status=='1'){
         cell6.innerHTML="<span onclick=tdClicks('"+i+"','status','fonstatus','"+data[i].taskID+"')><font id='fonstatus"+data[i].taskID+i+"'>未解决</font><input type='hidden' value='"+status+"' name='problemsta' id='problemsta' ><select id='status"+data[i].taskID+i+"' name='statuslist"+data[i].taskID+"' style='width: 90%;height: 100%;visibility: hidden;display: none;' ><option value=''></option><option value='0'>已解决</option><option value='1'>未解决</option></select></span>"
        }
        cell7.innerHTML="<img src='images/b_drop.png' onclick=javaScript:deleProblem('"+data[i].problemID+"',"+s+"); style='cursor: hand' />"
       table2nameber=""+data[data.length-1].problemCode+"";
       }    
     }
}
function addContent(arr){
if(arr!=null&&arr!=''&&arr!='undefined'){
  var arrs = new Array();
     arrs = arr;
      var addrownumbers =arrs[2];
      var taskId = arr[3];
     if(arr[4]=='add'){
      document.getElementById("memberId"+addrownumbers).value=arrs[0];
     document.getElementById("userName"+addrownumbers).value=arrs[1];
      }else{
       document.getElementById("memberId"+taskId+addrownumbers).value=arrs[0];
       document.getElementById("charger"+taskId+addrownumbers).value=arrs[1];
      }
    
   }
}
function addOper(keyarr,valuearr,addrownumbers,taskID,addmody){
if(keyarr!=null&&keyarr!=''&&keyarr!='undefined'&&valuearr!=null&&valuearr!=''&&valuearr!='undefined'){
    if(addmody=='add'){
     document.getElementById("useroperId"+addrownumbers).value=keyarr;
     document.getElementById("useroperName"+addrownumbers).value=valuearr;
    }else{
           document.getElementById("useroperId"+taskID+addrownumbers).value=keyarr;
     document.getElementById("useroper"+taskID+addrownumbers).value=valuearr;
    }
    
   }
}
function savaProblem(number,taskId){
   var problemCodeadd = document.getElementsByName("problemCodeadd");
   var descriptionadd = document.getElementsByName("descriptionadd");
   var planaddadd = document.getElementsByName("planaddadd");
   var memberIdadd = document.getElementsByName("memberIdadd");
   var useroperadd= document.getElementsByName("useroperadd");
   var planTimeadd= document.getElementsByName("planTimeadd");
   var statu = document.getElementsByName("statuadd");
   var problemIdlist = document.getElementsByName("problemIdlist");
   var problemCodelist = document.getElementsByName("problemCodelist");
   var descriptionlist = document.getElementsByName("descriptionlist");
   var planlist = document.getElementsByName("planlist");
   var memberIdlist = document.getElementsByName("memberIdlist");
   var useroperlist= document.getElementsByName("useroperIdlist");
   var planTimelist= document.getElementsByName("planTimelist");
   var statuslist = document.getElementsByName("statuslist"+taskId);
   var problemsta = document.getElementsByName("problemsta");
   var allstrs = new Array(new Array(),new Array(),new Array(),new Array(),new Array(),new Array(),new Array(),new Array(),new Array(),new Array(),new Array(),new Array(),new Array(),new Array(),new Array(),new Array(),new Array());
   for(i=0;i<descriptionadd.length;i++){
       allstrs[0][i]=problemCodeadd[i].value;
       allstrs[1][i]=descriptionadd[i].value;
       allstrs[2][i]=planaddadd[i].value;
       allstrs[3][i]=memberIdadd[i].value;
       allstrs[4][i]=useroperadd[i].value;
       allstrs[5][i]=planTimeadd[i].value;
       allstrs[6][i]=statu[i].value;
   }
   for(i=0;i<problemIdlist.length;i++){
      allstrs[8][i]=problemIdlist[i].value;
      allstrs[9][i]=problemCodelist[i].value;
      allstrs[10][i]=descriptionlist[i].value;
      allstrs[11][i]=planlist[i].value;
      allstrs[12][i]=memberIdlist[i].value;
      allstrs[13][i]=useroperlist[i].value;
      allstrs[14][i]=planTimelist[i].value;
      allstrs[15][i]=statuslist[i].value;
      allstrs[16][i]=problemsta[i].value
   }
   allstrs[7][0]=document.getElementById("taskId"+number).value;
   allstrs[7][1] =number;
   mms.savaProblem(allstrs,savebackworkdetail);
}
function deleProblem(problemId,obj){
if(confirm('您确定要删除此行信息')){
    $.post("AJAXAction.do?method=detelProblem&problemID="+problemId+"&obj="+obj,null,callbackdeteProblem);
 }
}

function saveOutput(number){
   var seqnumadd = document.getElementsByName("seqnumadd");
   var outputadd = document.getElementsByName("outputadd");
   var outputIdlist= document.getElementsByName("outputIdlist");
   var outputNamelist= document.getElementsByName("outputNamelist");
   var seqnumslist = document.getElementsByName("seqnumslist");
   var taskId = document.getElementById("taskId"+number).value;
  var allstrs = new Array(new Array(),new Array(),new Array(),new Array(),new Array(),new Array(),new Array(),new Array());
  for(i=0;i<outputadd.length;i++){
   allstrs[0][i]=seqnumadd[i].value;
   allstrs[1][i]=outputadd[i].value; 
   var s = parseFloat(rowss)+parseFloat(i);
   var attachCount = document.getElementsByName("attach"+taskId+s);
    var attach="null";
     for(var j=0;j<attachCount.length;j++){
        attach=attach+","+attachCount[j].value;
      }
     allstrs[7][i]=attach;
  }
 for(i=0;i<outputIdlist.length;i++){
     allstrs[2][i]=outputIdlist[i].value;
     allstrs[3][i]=outputNamelist[i].value;
     allstrs[4][i]=seqnumslist[i].value;
     var attachCount = document.getElementsByName("attach"+taskId+i);
     var attach="null";
     for(var j=0;j<attachCount.length;j++){
        attach=attach+","+attachCount[j].value;
      }
     allstrs[6][i]=attach;
 }
   allstrs[5][0]=taskId
   allstrs[5][1] =number;
   mms.saveOutput(allstrs,savebackworkdetail);
}

function savepask(taskId,number){
   var finished = document.getElementById("finished"+taskId+number).value;
  $.post("AJAXAction.do?method=savepask&taskId="+taskId+"&finished="+finished+"&number="+number,null,callbackpask);
}
function callbackpask(data){
   if(data!=null&&''!=data&&data!='undefined'){
    var arr =data.split(",");
   document.getElementById("fonfinished"+arr[1]+arr[0]).innerHTML=arr[2]+"%";
   document.getElementById("finished"+arr[1]+arr[0]).value=arr[2];
   document.getElementById("finished"+arr[1]+arr[0]).style.display='none'; 
   document.getElementById("finished"+arr[1]+arr[0]).style.visibility='hidden';
   document.getElementById("fonfinished"+arr[1]+arr[0]).style.display='';
   document.getElementById("fonfinished"+arr[1]+arr[0]).style.visibility='visible';

   }
}
</script>
<script type="text/javascript">
function  fileupload(num,taskId){
     var swfu;
			var settings = {
				flash_url : "<%=path%>/swfupload/swfupload.swf",
				upload_url: "flashupload.do?cate=OUTPUT&taskId="+taskId+"&number="+num,
				post_params: {"PHPSESSID" : "session_id();"},
				file_size_limit : "100 MB",
				file_types : "*.*",
				file_types_description : "All Files",
				file_upload_limit : 1,
				file_queue_limit : 0,
				custom_settings : {
					progressTarget : "fsUploadProgress"+num+taskId,
					cancelButtonId : "btnCancel"
				},
				debug: false,
				button_width: "35",
				button_height: "25",
				button_placeholder_id: "spanButtonPlaceHolder",
				button_text: "<span>上传</span>",
				button_text_style: ".theFont { font-size: 10; }",
				button_text_left_padding: 0,
				button_text_top_padding: 3,
				
				// The event handler functions are defined in handlers.js
				file_queued_handler : fileQueued,
				file_queue_error_handler : fileQueueError,
				file_dialog_complete_handler : fileDialogComplete,
				upload_start_handler : uploadStart,
				upload_progress_handler : uploadProgress,
				upload_error_handler : uploadError,
				upload_success_handler : uploadSuccess,
				upload_complete_handler : uploadComplete,
				queue_complete_handler : queueComplete	// Queue plugin event
			};
			swfu = new SWFUpload(settings);
}
</script>
</head>
<body class="body">
<jsp:include flush="true" page="header.jsp"></jsp:include>
<table class="bodytable" align="center"><tr><td>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr>
<td height="30"><a href="index.jsp">首页</a>&nbsp;&gt;&nbsp;<a href="ppm_project_list.jsp?currentPage=1">xx项目实施/开发日志</a> </td>
</tr>
</table>
<table width="900"  border="0" cellpadding="2" cellspacing="2" class="blockborder">
	  <tr>
		<td height="20" class="tableBackGround">&nbsp;<span class="blocktitle">当日工作任务</span></td>
	  </tr>

</table>
<br>
<% 
bd_user user = (bd_user)request.getSession().getAttribute(Consts.PKB_USER_SESSION_NAME);
 ppm_taskon[] ppmtask =(ppm_taskon[])request.getAttribute("ppmtask"); 
String[][] usernames =(String[][])request.getAttribute("usernames");
%>
<table width="900"  border="0" cellpadding="0" cellspacing="0">
	  <tr>
		<td height="25" width="20" class="tab_blank">&nbsp;</td>
		<td class="tab_active" width="120">
		<span class="tab_text">工作任务列表</span>
		</td>
		<td class="tab_blank">&nbsp;</td>
	  </tr>
</table>
<form id="frm" name="frm"  method="post"  >
<input type="hidden" name="userId" id="userId" value="<%=user.getUserID()%>" />
<table width="900" border="0" cellspacing="2" cellpadding="0">
  <tr>
    <td  height="25" width="20"></td>
    <td  height="25" width="60" >任务序号</td>
    <td  height="25">工作任务</td>
    <td height="25" >所在项目</td>
    <td  height="25">开始日期</td>
    <td  height="25">截止日期</td>
    <td  height="25">工期</td>
    <td  height="25">负责人</td>
    <td  height="25">普信联负责人</td>
    <td  height="25">配合人</td>
    <td height="25" width="100" >完成标志</td>
    <td height="25" align="center" >操作</td>
  </tr>

	<tr>
    	<td height="2" colspan="15"><div class="listdotline"></div></td>
  	</tr>
  	<caption></caption>
  	<%
  	 if(null!=ppmtask){
  		 for(int i=0;i<ppmtask.length;i++){
  			 ppm_taskon task =ppmtask[i];
  			 %>
  			 <tr>
	<td  height="25"><img src="images/xtree/Tplus.png" id="imgopen<%=i%>" style="cursor: hand;" onclick="javaScript:opendiv('<%=i%>','<%=task.getTaskID()%>');" />
                     <img src="images/xtree/Tminus.png" id="imgclose<%=i%>"  style="visibility: hidden;display: none;cursor: hand" onclick="javaScript:closediv('<%=i%>');" >	
	</td>
    <td  height="25"><%=task.getSeqNum()%></td>
    <td  height="25"><%=task.getTaskTitle()%></td>
      <td  height="25"><%=task.getProjectName()%></td>
    <td  height="25"><%=task.getStartTime()%></td>
    <td  height="25"><%=task.getEndTime()%></td>
    <td  height="25"><%=task.getTimeSpan()%></td>
    <td  height="25"><%=task.getChargename()%></td>
    <td  height="25"><%=task.getPxlUsername()%></td>
    <td  height="25">
     <%
       List userlist = new ArrayList();
       for(int m=0;m<usernames[i].length;m++){
    	   String username = usernames[i][m];
    		   if(null!=username&&!"".equals(username)){
    			userlist.add(username);
    	       }
       }
       for(int n=0;n<userlist.size();n++){
    	      if(n==userlist.size()-1){
    	    	  out.print(userlist.get(n));
    	      }else{
    	    	  out.print(userlist.get(n)+",");
    	      }
    		
    	  }
       %> 
    </td>
    <td height="25"><font id="fonfinished<%=task.getTaskID()%><%=i%>" ><%=task.getFinishedPercent()%>%</font><input type="text" name="finished" maxlength="3" onkeyup="value=value.replace(/[^\d\-]/g,'');"  style="width: 60%;height: 100%;visibility: hidden;display: none;" id="finished<%=task.getTaskID()%><%=i%>" value="<%=task.getFinishedPercent()%>"></td>
    <td height="25"><a href="#" onclick="return tdClicks('<%=i%>','finished','fonfinished','<%=task.getTaskID()%>');" >修改</a>&nbsp;<a href="#" onclick="javaScript:savepask('<%=task.getTaskID()%>','<%=i%>');" >保存</a></td>
   </tr>
   <tr>
  <td>&nbsp;</td>
  <td colspan="13" >
    <input type="hidden" name="taskId" id="taskId<%=i%>" value="<%=task.getTaskID()%>"  >
    <input type="hidden" name="userId" id="userId<%=i%>" value="<%=user.getUserID()%>" >
    <div style="visibility: hidden;display: none;" id="div<%=i%>" >
     [详细工作内容] &nbsp;&nbsp;<a href="#" onclick="return addrow('table1','<%=task.getTaskID()%>');" >增加</a>&nbsp;<a href="#" onclick="return saveworkDetail('<%=i%>');" >保存</a><br>
     <table border="1" width="800" class="tab" id="table1<%=task.getTaskID()%>" align="center" >
       <tr class="TableHeader" >
       <td width="100">内容序号</td>
       <td>详细工作内容</td>
       <td width="200" >工作地点</td>
       <td width="200" >删除</td>  
       </tr>   
    	   
     </table>
     <br>
     [产出物]&nbsp;&nbsp;&nbsp;<a href="#" onclick="return addrow('table3','<%=task.getTaskID()%>');">增加</a>&nbsp;<a href="#" onclick="return saveOutput('<%=i%>');">保存</a><br>
     <table border="1" width="800" class="tab" id="table3<%=task.getTaskID()%>"  align="center" >
       <tr class="TableHeader" >
       <td width="100">产出物序号</td>
       <td>产出物名称</td> 
       <td>附件</td>
       <td>删除</td>
       </tr>        
     </table>
     <br>
     <br>
    [遗留问题及风险提醒]&nbsp;&nbsp;<a href="#" onclick="return addrow('table2','<%=task.getTaskID()%>');" >增加</a>&nbsp;<a href="#" onclick="return savaProblem('<%=i%>','<%=task.getTaskID()%>');">保存</a><br><br>
       <table border="1" width="800" class="tab" align="center" id="table2<%=task.getTaskID()%>" >
       <tr class="TableHeader" >
       <td width="100">问题风险序号</td>
       <td>说明</td>
       <td>解决方案</td>
       <td width="100">负责人</td>
       <td width="100" >配合人</td>
       <td width="100">预计解决时间</td>
       <td width="100">解决状态</td> 
       <td width="50">删除</td> 
       </tr>   
     </table>
    </div> 
  </td>
  </tr>	
  	<div id='uploadFilesDiv<%=task.getTaskID()%>'></div>		 <%
  			 
  		 }
  	 }
  	
  	%>
<tr><td></td></tr>
</table>
</form>
</td></tr>
<tr><td>
历史遗留问题及风险清单<br>
<%
ppm_problemon[] problem =(ppm_problemon[])request.getAttribute("problem");
String[][] strs =(String[][])request.getAttribute("useroper"); %>
 <table border="1" width="700" class="tab"  align="left" >
<tr class="TableHeader">
<td height="25" width="100" >问题风险序号</td>
<td height="25" width="100" >说明</td>
<td height="25" width="100">所做项目</td>
<td height="25" width="100" >项目任务</td>
<td height="25" width="100">解决方案</td>
<td height="25" width="100" >配合人</td>
<td height="25" width="50" >负责人</td>
<td height="25" width="100">预计解决时间</td>
<td height="25" width="100">解决状态</td>
</tr>
<% if(null!=problem){
	for(int i=0;i<problem.length;i++){
		%>
		<tr>
<td height="25" width="100" ><%=problem[i].getProblemCode()%></td>
<td height="25" width="100" ><%=problem[i].getDescription() %></td>
<td height="25" width="100" ><%=problem[i].getProjectName() %></td>
<td height="25" width="100" ><%=problem[i].getTaskName()%></td>
<td height="25" width="100"><%=problem[i].getPlan() %></td>
<td height="25" width="100" >
<%
  List list = new ArrayList();
  for(int j=0;j<strs[i].length;j++){
	 String useroper = strs[i][j];
	   if(null!=useroper&&!"".equals(useroper)){
		list.add(useroper);
        }
  }
  for(int n=0;n<list.size()-1;n++){
	  if(n==list.size()-2){
		  out.print(list.get(n));  
	  }else{
		  out.print(list.get(n)+",");
	  }
  }
%>
</td>
<td height="25" width="50" ><%=strs[i][list.size()]%></td>
<td height="25" width="100"><%=problem[i].getPlanTime()%></td>
<td height="25" width="100"><%
   if(problem[i].getProblemStatus()==0){
	   out.print("已解决");
   }else{
	   out.print("未解决");
   }
%></td>
</tr>

		<%
		}
}else{
	%>
	<tr><td height="25" colspan="7" ><font color="red">没有相关信息</font></td></tr>
	<%
}
%>
</table>
</td></tr>
<tr><td>
后续五天任务提示<br>
<% 
  ppm_taskon[] fivetask =(ppm_taskon[])request.getAttribute("lastFivetask");
  String[][] lastusernames = (String[][])request.getAttribute("lastusernames");
%>
<table border="1" width="700" class="tab"  align="left">
<tr class="TableHeader">
    <td  height="25" width="60" >任务序号</td>
    <td  height="25">工作任务</td>
    <td height="25" >所在项目</td>
    <td  height="25">开始日期</td>
    <td  height="25">截止日期</td>
    <td  height="25">工期</td>
    <td  height="25">负责人</td>
    <td  height="25">普信联负责人</td>
    <td  height="25">配合人</td>
    <td height="25" width="100" >完成标志</td>
  </tr>
  <%if(null!=fivetask&&!"".equals(fivetask)){
	  for(int i=0;i<fivetask.length;i++){
		  ppm_taskon task = fivetask[i];
		  %>
		  <tr>
	    <td  height="25" width="60" ><%=task.getSeqNum()%></td>
	    <td  height="25"><%=task.getTaskTitle() %></td>
	    <td  height="25"><%=task.getProjectName()%></td>
	    <td  height="25"><%=task.getStartTime()%></td>
	    <td  height="25"><%=task.getEndTime()%></td>
	    <td  height="25"><%=task.getTimeSpan()%></td>
	    <td  height="25"><%=task.getChargename()%></td>
	    <td  height="25"><%=task.getPxlUsername() %></td>
	    <td  height="25">     <%
       List userlist = new ArrayList();
       for(int m=0;m<lastusernames[i].length;m++){
    	   String username = lastusernames[i][m];
    		   if(null!=username&&!"".equals(username)){
    			userlist.add(username);
    	       }
       }
       for(int n=0;n<userlist.size();n++){
    	      if(n==userlist.size()-1){
    	    	  out.print(userlist.get(n));
    	      }else{
    	    	  out.print(userlist.get(n)+",");
    	      }
    		
    	  }
       %> </td>
	    <td height="25" width="100" ><%=task.getFinishedPercent()%>%</td>
	  </tr>
		  <%

	  }
	 
  } %> 
  
</table>
</td></tr>
</table>
<jsp:include flush="true" page="footer.jsp"></jsp:include>
</body>
</html>