var uploadFileArray = new Array();
function deleteUploadFile(index,targetID) {
	if(confirm('真的要删除该文件吗？')) {
		document.getElementById("progresstext"+index).style.visibility="hidden";
		document.getElementById("progresstext"+index).style.display="none";
		document.getElementById("font"+index).style.visibility="hidden";
		document.getElementById("font"+index).style.display="none";
		document.getElementById("imagedelete"+index).style.visibility="hidden";
		document.getElementById("imagedelete"+index).style.display="none";
		document.getElementById(index).style.display="none";
		document.getElementById(index).style.visibility="hidden";
		uploadFileArray.splice(index,1);
		updateSpan();
	}
}

function updateSpan() {
	var str = "";
	var i;
	str+="<input name='attachCount' id='attachCount' type='hidden' style='display:none;' value='"+uploadFileArray.length+"'>";
	for(i=0;i<uploadFileArray.length;i++) {
		str+="<input name='attach"+i+"' type='hidden'  style='display:none' value='"+uploadFileArray[i].attachID+"'><br>";
	}
	var uploadFilesDiv = document.getElementById("uploadFilesDiv");
	uploadFilesDiv.innerHTML = str;
}

function addUploadFile(attachID,fileName) {
	var file = new Object;
	file.attachID = attachID;
	file.fileName = fileName;
	uploadFileArray.push(file);
	updateSpan();
}

function updateUploadFile(attachID,fileName) {
	var file = new Object;
	file.attachID = attachID;
	file.fileName = fileName;
	uploadFileArray[0]=file;
	
	//只用于doc_add.jsp
	if(frmDocAdd.docTitle.value=='') {
		frmDocAdd.docTitle.value=fileName.substr(0,fileName.lastIndexOf("."));
	}
	
	updateSpan();
}

function outaddUploadFile(attachID,fileName,taskId,number){
	var file = new Object;
	file.attachID = attachID;
	file.fileName = fileName;
	uploadFileArray.push(file);
	updataSpan(taskId,number);
}

function updataSpan(taskId,num){
  taskID=taskId;
  number=num;
 var str = "";
	str+="<input name='attachCount"+taskId+"' id='attachCount"+taskId+num+"' type='text' style='display:none;' value='"+uploadFileArray.length+"'>";
	for(i=0;i<uploadFileArray.length;i++) {
		str+="<input name='attach"+taskId+num+"' type='text' id='attach"+taskId+num+i+"'  style='display:none' value='"+uploadFileArray[i].attachID+"'><br>";
	}
	uploadFileArray = new Array(); ;
	var uploadFilesDiv = document.getElementById("uploadFilesDiv"+taskId);
	uploadFilesDiv.innerHTML =uploadFilesDiv.innerHTML+str;
}