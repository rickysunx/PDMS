 //对用户的校验进行JavaScript操作
 function  codeCheck(){
     var jqueryObj = $("#projectCode");
     var projectCode = jqueryObj.val();
     $.post("AJAXAction.do?method=checkonly&projectCode="+projectCode,null,callback);
    }
 function callback(data){  
    var codeCheck =document.getElementById("codeCheck");
     var codevalue =document.getElementById("codevalue");
     var jqueryObj = $("#projectCode");
    if(data==1){
    	 codeCheck.style.display='';
         codeCheck.style.visibility='visible';
         codevalue.value="1"
         jqueryObj.focus();
         return false;
    }else{
    	 codeCheck.style.display='none';
         codeCheck.style.visibility='hidden';
         codevalue.value="0"
         return true;
    }
 }
 
function getWrokdetailByTaskId(taskId){
   $.post("AJAXAction.do?method=getWorkdetatil&taskId="+taskId,null,callbackWorkDetail);
}


function deteWorkDetail(data){
	if(null!=data&&''!=data&&'undefined'!=data){
	  var strs = new Array();
	  strs = data.split(",");
     var table = document.getElementById('table1'+strs[1]);
		table.deleteRow(strs[0]);
	}
	
}

function callbackdeteProblem(data){
	if(null!=data&&''!=data&&'undefined'!=data){
	  var strs = new Array();
	  strs = data.split(",");
     var table = document.getElementById('table2'+strs[1]);
		table.deleteRow(strs[0]);
	}
	
}

function callbackdeteoutput(data){
	if(null!=data&&''!=data&&'undefined'!=data){
	  var strs = new Array();
	  strs = data.split(",");
     var table = document.getElementById('table3'+strs[1]);
		table.deleteRow(strs[0]);
	}
}