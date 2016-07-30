var color_over = "#D2E5F1";
var color_selected = "#C0BDB5";
var color_nomarl = "#E9EEF1";
var msPerDay = 86400000;

var max_time = "2050-12-31 23:59";
var min_time = "1900-01-01 00:00"

function onDocmentclick() {
	dt_onDocmentclick();
}

function onDocumentkeypress() {
	dt_onDocumentkeypress();
}

document.onclick = onDocmentclick;
document.onkeypress = onDocumentkeypress;

function mydom(id) {
	return document.getElementById(id);
}

function getTaskTable() {
	return mydom('mainTaskTable');
}

function onBodyLoad() {
	mydom('footerTable').width = 1400;
	tt_init(mydom('mainTaskTable'));
}

function enableAllBtns(b) {
	var i;
	for(i=0;i<9;i++) {
		mydom("btn"+i).disabled = (!b);
	}
}

function on_ttSave(tt) {
	enableAllBtns(false);
	var drs = "";
	var i;
	for(i=0;i<tt.oidCount;i++) {
		drs+=tt.lineDRs[i];
		if(i<tt.oidCount-1) {
			drs+=",";
		}
	}
	mydom('lineDRs').value=drs;
	mydom('frmTask').submit();
}

function on_ttBack(tt) {
	if(confirm('确定要返回吗？如果没有保存数据，请先点击【保存】，否则将导致数据丢失。')) {
		window.location.href='PPMproject.do?method=list';
	}
}

function on_ttLevelUp(tt) {
	var selectedRows = tt_getSelectedRows(tt);
	var i;
	for(i=0;i<selectedRows.length;i++) {
		tt_levelUp(tt,selectedRows[i]);
	}
	tt_refreshLevel(tt);
}

function on_ttLevelDown(tt) {
	var selectedRows = tt_getSelectedRows(tt);
	var i;
	for(i=0;i<selectedRows.length;i++) {
		tt_levelDown(tt,selectedRows[i]);
	}
	tt_refreshLevel(tt);
}

function on_ttLinkTask(tt) {
	var selectedRows = tt_getSelectedRows(tt);
	var i;
	var data = tt.data;
	for(i=1;i<selectedRows.length;i++) {
		var row1 = selectedRows[i-1];
		var row2 = selectedRows[i];
		var oid1 = data[row1].oid;
		var oid2 = data[row2].oid;
		data[row2].preTaskID = data[row1].taskID;
		
		data[row1].endTime = mydom("endTime"+oid1).value;
		data[row2].startTime = data[row1].endTime;
		mydom("startTime"+data[row2].oid).value = data[row2].startTime;
		data[row2].timeSpan = mydom("timeSpan"+oid2).value;
		if(data[row2].timeSpan!=null && data[row2].timeSpan!="") {
			data[row2].endTime = dt_getDateByWorkDays(data[row2].startTime,data[row2].timeSpan,true);
			mydom("endTime"+oid2).value = data[row2].endTime;
		}
		
	}
	tt_refreshPreTask(tt);
}

function on_ttUnLinkTask(tt) {
	var selectedRows = tt_getSelectedRows(tt);
	var i;
	var data = tt.data;
	for(i=0;i<selectedRows.length;i++) {
		var row = selectedRows[i];
		data[row].preTaskID = 0;
	}
	tt_refreshPreTask(tt);
}

function tt_levelUp(tt,index) {
	var data = tt.data;
	var pid = data[index].parentTaskID;
	var i;
	var pid0 = 0;
	var oid = data[index].oid;
	
	//将后面的任务与本任务同样父任务的，修改为本任务的子任务。
	for(i=index+1;i<data.length;i++) {
		if(data[i].parentTaskID==pid) {
			data[i].parentTaskID = data[index].taskID;
			mydom('parentTaskID'+data[i].oid).value = data[index].taskID;
		}
	}
	
	if(pid!=0) {
		for(i=0;i<data.length;i++) {
			if(data[i].taskID==pid) {
				pid0 = data[i].parentTaskID;
			}
		}
	}
	data[index].parentTaskID = pid0;
	mydom('parentTaskID'+data[index].oid).value = pid0;
	
}

function tt_refreshStartAndEndTimeByTaskID(tt,index) {
	var data = tt.data;
	var i;
	var j;
	var start_time = max_time;
	var end_time = min_time;
	var oid = data[index].oid;
	var taskid = data[index].taskID;
	
	for(i=0;i<data.length;i++) {
		if(data[i].parentTaskID==taskid) {
			if(!tt_isLeafNode(tt,data[i].taskID)) {
				tt_refreshStartAndEndTimeByTaskID(tt,i);
			}
			
			var start_time0 = data[i].startTime;
			var end_time0 = data[i].endTime;
			
			if(start_time0!=null && start_time0.length>0 && start_time0<start_time) {
				start_time = start_time0;
			}
			
			if(end_time0!=null && end_time0.length>0 && end_time0>end_time) {
				end_time = end_time0;
			}
		}
	}
	mydom("startTime"+oid).value = start_time;
	mydom("endTime"+oid).value = end_time;
	tt_changeTime(tt,oid);
}

function tt_refreshStartAndEndTime(tt) {
	
	var data = tt.data;
	var i;
	
	for(i=0;i<data.length;i++) {
		if(data[i].parentTaskID==0 && (!tt_isLeafNode(tt,data[i].taskID))) {
			tt_refreshStartAndEndTimeByTaskID(tt,i);
		}
	}
	
}

function tt_levelDown(tt,index) {
	//按索引网上寻找任务，若找到与本任务父任务一直的任务，则将其修改为本任务的父任务
	var i=index-1;
	var data = tt.data;
	var pid0 = tt.data[index].parentTaskID;
	
	while(true) {
		if(i<0) break;
		
		var pid = tt.data[i].parentTaskID;
		
		if(pid==pid0) {
			tt.data[index].parentTaskID = tt.data[i].taskID;
			mydom('parentTaskID'+tt.data[index].oid).value = tt.data[i].taskID;
			break;
		}
		
		i--;
	}
	
	
	
}

function tt_refreshPreTask(tt) {
	var i,j;
	var data = tt.data;
	
	for(i=0;i<data.length;i++) {
		var preTaskID = data[i].preTaskID;
		mydom('preTaskID'+data[i].oid).value=preTaskID;
		if(preTaskID==0) {
			mydom('preTaskName'+data[i].oid).value='';
		} else {
			for(j=0;j<data.length;j++) {
				if(data[j].taskID==preTaskID) {
					mydom('preTaskName'+data[i].oid).value=(data[j].seqNum+1);
				}
			}
		}
	}
}

function tt_refreshLevel(tt) {
	var i;
	var data = tt.data;
	for(i=0;i<data.length;i++) {
		var o = data[i];
		var html = "";
		html += tt_getRepeatSpace(tt_getLevelByTaskId(tt,o.taskID)*2);
		o.isLeaf = tt_isLeafNode(tt,o.taskID);
		if(!o.isLeaf) {
			html += "<img border=0 src='images/minus.png'>";
		} else {
			html += "<img border=0 src='images/blank.png'>";
		}
		mydom('taskLevel'+o.oid).innerHTML = html;
	}
	tt_refreshStartAndEndTime(tt);
}

function tt_getRepeatSpace(n) {
	var space = "";
	var i;
	for(i=0;i<n;i++) {
		space+="　";
	}
	return space;
}

function tt_isLeafNode(tt,taskID) {
	var i;
	var data = tt.data;
	for(i=0;i<data.length;i++) {
		if(data[i].parentTaskID==taskID) {
			return false;
		}
	}
	
	return true;
	
}

function tt_getIndexByOID(tt,oid) {
	var i;
	var data = tt.data;
	for(i=0;i<data.length;i++) {
		if(oid==data[i].oid) {
			return i;
		}
	}
	return -1;
}

function tt_getLevelByTaskId(tt,taskID) {
	var pid = 0;
	var i;
	var data = tt.data;
	for(i=0;i<data.length;i++) {
		if(data[i].taskID==taskID) {
			pid = data[i].parentTaskID;
			break;
		}
	}
	
	if(pid==0) return 0;
	
	return tt_getLevelByTaskId(tt,pid)+1;
}

//初始化任务表格
function tt_init(tt) {
	tt.colEditor = new Array();
	tt.data = new Array();
	tt.rowCount=0;
	tt.oidCount=0;
	tt.lineDRs = new Array(); //删除标志
	
	tt_init_data(tt);
	
	enableAllBtns(taskEditable);
}

function tt_newRowData() {
	var o = new Object();
	o.oid=-1;
	o.selected = false;
	o.taskID = -1;
	o.seqNum = 0;
	o.isLeaf = false;
	o.parentTaskID = 0;
	o.taskTitle = "";
	o.taskType = 0;
	o.taskTypeName="常规任务";
	o.startTime = "";
	o.endTime = "";
	o.timeSpan = 0;
	o.charger = 0;
	o.chargerName = "";
	o.pxlCharger = 0;
	o.pxlChargerName = "";
	o.assist = "";
	o.assistName = "";
	o.preTaskID = 0;
	o.notes = "";
	return o;
}

function on_addRow() {
	tt_addRow(getTaskTable(),tt_newRowData());
}

function on_insertRow() {
	var o = tt_newRowData();
	tt_insertRow(getTaskTable(),tt_getSelectedRow(getTaskTable()),o);
}

function on_deleteRow() {
	if(confirm("真的要删除任务吗？")) {
		tt_deleteRow(getTaskTable());
	}
}

function tt_deleteRow(tt) {
	var row;
	
	while(true) {
		row = tt_getSelectedRow(tt);
		if(row==-1) break;
		tt.lineDRs[tt.data[row].oid]=1;
		tt.data.splice(row,1);
		tt.deleteRow(row+1);
		tt.rowCount--;
		
		for(i=0;i<tt.rowCount;i++) {
			tt.data[i].seqNum = i;
			tt.rows[i+1].cells[0].innerText = (tt.data[i].seqNum+1);
			mydom('seqNum'+tt.data[i].oid).value = tt.data[i].seqNum;
		}
	}
}

function dt_isWorkDay(dt) {
	if(dt.getDay()>=1 && dt.getDay()<=5) {
		return true;
	} else {
		return false;
	}
}

/*
 * 将xxxx-xx-xx格式转换成日期
 */
function dt_getDateByStr(str) {
	return new Date(str.substr(0,4),(str.substr(5,2))-1,str.substr(8,2));
}

function dt_getXX(n) {
	if(n<10) return "0"+n;
	return ""+n;
}


function dt_getStrByDate(dt) {
	return dt.getFullYear()+"-"+dt_getXX(dt.getMonth()+1)+"-"+dt_getXX(dt.getDate());
}

function dt_calcTime(start,end) {
	var strStartTime = start.substr(11,5);
	var strEndTime = end.substr(11,5);
	
	var start_h = new Number(strStartTime.substr(0,2));
	var start_m = new Number(strStartTime.substr(3,2));
	
	var end_h = new Number(strEndTime.substr(0,2));
	var end_m = new Number(strEndTime.substr(3,2));
	
	var m = (end_h*60+end_m)-(start_h*60+start_m);
	return m/480.0;
}

/*
 * 输入开始和结束时间，计算出工期
 * 
 */
function dt_getWorkDays(start,end) {
	
	var strStartDate = start.substr(0,10);
	var strEndDate = end.substr(0,10);
	
	var dtStartDate = dt_getDateByStr(strStartDate);
	var dtEndDate = dt_getDateByStr(strEndDate);
	
	var workDayCount = 0;
	var i=0;
	while(true) {
		var dtThis = new Date(dtStartDate.valueOf()+i*msPerDay);
		if(dt_isWorkDay(dtThis)) {
			workDayCount++;
		}
		if((dtEndDate-dtThis)<=0) break;
		i++;
	}
	
	
	return (workDayCount-1)+dt_calcTime(start,end);
}

function dt_getDateByWorkDays(dt,days,bStart) {
	var minutes_workday_start = 9*60;
	var minutes_workday_end = 17*60;
	var minutes_workday = 8*60;
		
	var int_days = Math.floor(days-0.00001);
	var days_left = days-int_days;
	var minutes_left = Math.round(days_left*minutes_workday);
	
	
	if(bStart) {
		var strStartTime = dt.substr(11,5);
		var start_h = new Number(strStartTime.substr(0,2));
		var start_m = new Number(strStartTime.substr(3,2));
		
		var minutes_start = start_h*60+start_m;
		var minutes_end = minutes_start+minutes_left;
		if(minutes_end==minutes_workday_end) {
			int_days+=1;
		} else if(minutes_end>minutes_workday_end) {
			int_days+=1;
			minutes_left-=minutes_workday;
			minutes_end-=minutes_workday;
		}
		
		var end_h = Math.floor(minutes_end/60);
		var end_m = minutes_end%60;
					
		var str_end_date = "";
		
		var start_date = dt_getDateByStr(dt);
		var end_date = null;
		
		var workDayCount = 0;
		var i = 0;
		
		while(true) {
			end_date = new Date(start_date.valueOf()+(i*msPerDay));
			if(dt_isWorkDay(end_date)) {
				workDayCount++;
			}
			if(workDayCount>=int_days) {
				break;
			}
			i++;
		}
		
		str_end_date = dt_getStrByDate(end_date)+" "+dt_getXX(end_h)+":"+dt_getXX(end_m);
		
		return str_end_date;
		
	} else {
		var strEndTime = dt.substr(11,5);
		var end_h = new Number(strEndTime.substr(0,2));
		var end_m = new Number(strEndTime.substr(3,2));
		
		var minutes_end = end_h*60+end_m;
		var minutes_start = minutes_end-minutes_left;
		if(minutes_start==minutes_workday_start) {
			int_days+=1;
		} else if (minutes_start<minutes_workday_start) {
			int_days+=1;
			minutes_left-=minutes_workday;
			minutes_start+=minutes_workday;
		}
		
		var start_h = Math.floor(minutes_start/60);
		var start_m = minutes_start%60;
		
		var str_start_date = "";
		
		var end_date = dt_getDateByStr(dt);
		var start_date = new Date(end_date.valueOf()-(int_days*msPerDay));
		var workDayCount = 0;
		var i = 0;
		
		while(true) {
			start_date = new Date(end_date.valueOf()-(i*msPerDay));
			if(dt_isWorkDay(start_date)) {
				workDayCount++;
			}
			if(workDayCount>=int_days) {
				break;
			}
			i++;
		}
		
		str_start_date = dt_getStrByDate(start_date)+" "+dt_getXX(start_h)+":"+dt_getXX(start_m);
		
		return str_start_date;
		
	}
	
}

function tt_changeTime(tt,oid) {
	var start = mydom('startTime'+oid);
	var end = mydom('endTime'+oid);
	var timeSpan = mydom('timeSpan'+oid);
	var data = tt.data;
	
	var start_time = start.value;
	var end_time = end.value;
	
	var index = tt_getIndexByOID(tt,oid);
	data[index].startTime = start_time;
	data[index].endTime = end_time;
	
	if(start_time==null || start_time.length==0) return;
	if(end_time==null || end_time.left==0) return;
												
	timeSpan.value = dt_getWorkDays(start_time,end_time);
}

function tt_onStartTimeChange(tt,oid) {
	if(!taskEditable) return;
	tt_changeTime(tt,oid);
	
	var index = tt_getIndexByOID(tt,oid);
	var taskID = tt.data[index];
	if(tt_isLeafNode(tt,taskID)) {
		tt_refreshStartAndEndTime(tt);
	}
}

function tt_onEndTimeChange(tt,oid) {
	if(!taskEditable) return;
	tt_changeTime(tt,oid);
	
	var index = tt_getIndexByOID(tt,oid);
	var taskID = tt.data[index];
	if(tt_isLeafNode(tt,taskID)) {
		tt_refreshStartAndEndTime(tt);
	}
}

/*
 * 根据时间间隔计算起止日期
 */
function tt_onTimeSpanChange(tt,oid) {
	var start = mydom('startTime'+oid);
	var end = mydom('endTime'+oid);
	var timeSpan = mydom('timeSpan'+oid);
	
	var start_time = start.value;
	var end_time = end.value;
	var time_span = timeSpan.value;
	
	if(isNaN(time_span)) {
		alert('输入不是数字');
		timeSpan.focus();
		return;	
	}
	
	if(start_time==null || start_time.length==0) {
		if(end_time!=null && end_time.length>0) {
			start.value = dt_getDateByWorkDays(end_time,time_span,false);
		}
	} else {
		end.value = dt_getDateByWorkDays(start_time,time_span,true);
	}
	
}

function on_timeClicked(tt,oInput,oid,isStart) {
	if(!taskEditable) return;
	var index = tt_getIndexByOID(tt,oid);
	var data = tt.data;
	if(index>=0) {
		if(tt_isLeafNode(tt,data[index].taskID)) {
			var val = oInput.value;
			var d = new Date();
			if(isStart) {
				if(val=='') {
					val = dt_getStrByDate(d)+" 09:00";
					oInput.value = val;
				}
				setDayHM(oInput);
			} else {
				if(val=='') {
					val = dt_getStrByDate(d)+" 17:00";
					oInput.value = val;
				}
				setDayHM(oInput);
			}
		}
	}
	
}

function tt_insertRow(tt,index,o) {
	var i;
	
	//往DATA中一条插入数据
	if(index==-1) {
		//在末尾插入一行
		tt.data[tt.rowCount] = o;
		o.seqNum = tt.rowCount;
	} else {
		//在中间插入一行
		for(i=tt.rowCount-1;i>=index;i--) {
			tt.data[i+1] = tt.data[i];
		}
		tt.data[index] = o;
		o.seqNum = index;
		
		for(i=0;i<tt.rowCount+1;i++) {
			tt.data[i].seqNum = i;
		}
	}
	var num = o.seqNum;
	var oid = tt.oidCount;
	var oTR = tt.insertRow(index==-1?-1:index+1);
	tt.lineDRs[oid]=0;
	o.oid=oid;
	if(o.taskID==-1) o.taskID=-(oid+1);
	
	oTR.height="20px";
	oTR.className="ppm_task_rows";
	oTR.onmouseover=function a() {tt_onMouseOver(getTaskTable(),this);};
	oTR.onmouseout=function a(){tt_onMouseOut(getTaskTable(),this);};
	oTR.onclick=function a(){tt_onClick(getTaskTable(),this,event);};
	
	var tdSeqNum = oTR.insertCell(-1);
	tdSeqNum.align = "center";
	tdSeqNum.innerText = (num+1);
	
	var tdTaskTitle = oTR.insertCell(-1);
	tdTaskTitle.align = "left";
	tdTaskTitle.innerHTML = "<span id='taskLevel"+oid+"'></span><input id='taskTitle"+oid+"' name='taskTitle"+oid+"' class='ppm_task_text' style='width: 200px;' type='text' value='"+o.taskTitle+"'>";
	
	var tdTaskType = oTR.insertCell(-1);
	tdTaskType.align = "center";
	
	tdTaskType.innerHTML ="<input id='taskTypeName"+oid+"' name='taskTypeName"+oid+"' onclick='tt_onTaskTypeNameClick(getTaskTable(),"+oid+");' class='ppm_task_text' style='width: 60px;' type='text' value='"+o.taskTypeName+"' readonly>"
		+"<select id='taskType"+oid+"' name='taskType"+oid+"' onchange='tt_onTaskTypeBlur(getTaskTable(),"+oid+");' onblur='tt_onTaskTypeBlur(getTaskTable(),"+oid+");' style='width: 80px;height: 20px;font-size: 12px;display: none;'>    "
		+"	<option value='0'"+(o.taskType==0?" selected":"")+">常规任务</option>                                                                                                                                                                                              "
		+"	<option value='1'"+(o.taskType==1?" selected":"")+">重要任务</option>                                                                                                                                                                                              "
		+"	<option value='2'"+(o.taskType==2?" selected":"")+">里程碑任务</option>                                                                                                                                                                                            "
		+"	<option value='3'"+(o.taskType==3?" selected":"")+">其他</option>                                                                                                                                                                                                  "
		+"</select>";
	
	var tdStartTime =  oTR.insertCell(-1);
	tdStartTime.align = "center";
	tdStartTime.innerHTML="<input id='startTime"+oid+"' name='startTime"+oid+"' onchange='tt_onStartTimeChange(getTaskTable(),"+oid+");' value='"+o.startTime+"' class='ppm_task_text' style='width: 100px;' type='text' readonly ondblclick='on_timeClicked(getTaskTable(),this,"+oid+",true);'>";
	
	var tdEndTime = oTR.insertCell(-1);
	tdEndTime.align = "center";
	tdEndTime.innerHTML="<input id='endTime"+oid+"' name='endTime"+oid+"' onchange='tt_onEndTimeChange(getTaskTable(),"+oid+");' value='"+o.endTime+"' class='ppm_task_text' style='width: 100px;' type='text' readonly ondblclick='on_timeClicked(getTaskTable(),this,"+oid+",false);'>"
		+"<input id='taskID"+oid+"' name='taskID"+oid+"' style='display:none;' value='"+o.taskID+"' type='text'>"
		+"<input id='seqNum"+oid+"' name='seqNum"+oid+"' style='display:none;' value='"+o.seqNum+"' type='text'>"
		+"<input id='parentTaskID"+oid+"' name='parentTaskID"+oid+"' style='display:none;' value='"+o.parentTaskID+"' type='text'>";
	
	var tdTimeSpan = oTR.insertCell(-1);
	tdTimeSpan.align = "center";
	tdTimeSpan.innerHTML="<input id='timeSpan"+oid+"' name='timeSpan"+oid+"' onblur='tt_onTimeSpanChange(getTaskTable(),"+oid+");' value='"+o.timeSpan+"' class='ppm_task_text' style='width: 40px;' type='text'>";

	var tdCharger = oTR.insertCell(-1);
	tdCharger.align = "center";
	tdCharger.innerHTML="<input ondblclick='tt_showMemberRef(this,mydom(\"charger"+oid+"\"),false,false);' id='chargerName"+oid+"' name='chargerName"+oid+"' value='"+o.chargerName+"' class='ppm_task_text' style='width: 50px;' type='text' readonly>"
		+"<input id='charger"+oid+"' name='charger"+oid+"' class='ppm_task_text' style='display:none;' type='text' value='"+o.charger+"'>";
	
	var tdPxlCharger = oTR.insertCell(-1);
	tdPxlCharger.align = "center";
	tdPxlCharger.innerHTML="<input ondblclick='tt_showMemberRef(this,mydom(\"pxlCharger"+oid+"\"),false,true);' id='pxlChargerName"+oid+"' name='pxlChargerName"+oid+"' value='"+o.pxlChargerName+"' class='ppm_task_text' style='width: 70px;' type='text' readonly>"
		+"<input id='pxlCharger"+oid+"' name='pxlCharger"+oid+"' class='ppm_task_text' style='display:none;' type='text' value='"+o.pxlCharger+"'>";
		
	var tdAssist = oTR.insertCell(-1);
	tdAssist.align = "left";
	tdAssist.innerHTML="<input ondblclick='tt_showMemberRef(this,mydom(\"assist"+oid+"\"),true,false);' id='assistName"+oid+"' name='assistName"+oid+"' value='"+o.assistName+"' class='ppm_task_text' style='width: 80px;' type='text' readonly>"
		+"<input id='assist"+oid+"' name='assist"+oid+"' class='ppm_task_text' style='display:none;' value='"+o.assist+"' type='text'>";
	
	var tdPreTask = oTR.insertCell(-1);
	tdPreTask.align = "center";
	tdPreTask.innerHTML="<input id='preTaskName"+oid+"' name='preTaskName"+oid+"' value='' class='ppm_task_text' style='width:80px;' type='text' readonly>"
		+"<input id='preTaskID"+oid+"' name='preTaskID"+oid+"' style='display:none;' type='text' value='"+o.preTaskID+"'>";
	
	var tdNotes = oTR.insertCell(-1);
	tdNotes.align = "left";
	tdNotes.innerHTML="<textarea ondblclick='tt_showNotes(this);' id='notes"+oid+"' name='notes"+oid+"' class='ppm_task_text' style='width:80px;height:20px;' type='text' readonly>"+o.notes+"</textarea>";
	
	tt.rowCount++;
	tt.oidCount++;
	mydom('oidCount').value=tt.oidCount;
	
	
	for(i=0;i<tt.rowCount;i++) {
		tt.rows[i+1].cells[0].innerText = (tt.data[i].seqNum+1);
		mydom('seqNum'+tt.data[i].oid).value=tt.data[i].seqNum;
	}
	
	//mydom('debugInfo').innerText = tt.innerHTML;
	
	mydom('taskTitle'+oid).readOnly = (!taskEditable);
	mydom('timeSpan'+oid).readOnly = (!taskEditable);
	
}

function tt_showNotes(oInput) {
	if(!taskEditable) return;
	var param = new Object();
	var result;
	var sFeatures = "dialogHeight:400px; dialogWidth:500px; center:yes; resizable:no; status:no;";
	var sUrl = "ppm_task_notes.jsp";
	result = window.showModalDialog(sUrl,oInput.value,sFeatures);
	if(result!=null) {
		oInput.value = result;
	}
}

function tt_showMemberRef(oInputName,oInputID,bMultiSelect,bPxlOnly) {
	if(!taskEditable) return;
	var param = new Object();
	var result;
	var sFeatures = "dialogHeight:400px; dialogWidth:500px; center:yes; resizable:yes; status:no;";
	result = window.showModalDialog
		(("ppm_task_ref_member.jsp?"
		 +"multi="+(bMultiSelect?"1":"0")
		 +"&pxl="+(bPxlOnly?"1":"0")
		 +"&projectID="+projectID),oInputID.value,sFeatures);

	if(result!=null) {
		oInputName.value=result.selnames;
		oInputID.value=result.selids;
	}
}

function tt_addRow(tt,rowData) {
	tt_insertRow(tt,-1,rowData);
}

function tt_getTR(tt,index) {
	return tt.rows[index+1];
}

function tt_getRowIndex(tt,tr) {
	var rows = tt.rows;
	var i;
	for(i=0;i<rows.length;i++) {
		if(rows(i)==tr) {
			return (i-1);
		}
	}
}

function tt_getRowAndCol(tt,td) {
	var rows = tt.rows;
	var i,j;
	for(i=0;i<rows.length;i++) {
		var cells = rows[i].cells;
		for(j=0;j<cells.length;j++) {
			if(cells[j]==td) {
				var rowAndCol = new Object;
				rowAndCol.row = (i-1);
				rowAndCol.col = (j);
				return rowAndCol;
			}
		}
	}
}

/*
 * tt:table对象
 * b:是否选择
 */
function tt_selectAllRows(tt,b) {
	var i;
	for(i=0;i<tt.rowCount;i++) {
		tt_selectRow(tt,i,b);
	}
}

/*
 * tt:table对象
 * index:行数
 * b:是否选择
 */
function tt_selectRow(tt,index,b) {
	var data = tt.data;
	data[index].selected = b;
	var oTR = tt_getTR(tt,index);
	if(b) {
		oTR.style.backgroundColor = color_selected;
	} else {
		oTR.style.backgroundColor = color_nomarl;
	}
}

function tt_getSelectedRow(tt) {
	var data = tt.data;
	var i;
	for(i=0;i<data.length;i++) {
		
		if(data[i].selected) {
			return i;
		}
	}
	return -1;
}

function tt_getSelectedRows(tt) {
	var data = tt.data;
	var selectedRows = new Array();
	var count = 0;
	var i;
	
	for(i=0;i<data.length;i++) {
		if(data[i].selected) {
			selectedRows[count] = i;
			count++;
		}
	}
	
	return selectedRows;
}

function tt_getRowSelected(tt,index) {
	var data = tt.data;
	return data[index].selected;
}

function tt_onMouseOver(tt,tr) {
	var index = tt_getRowIndex(tt,tr);
	if(tt_getRowSelected(tt,index)) {
		tr.style.backgroundColor = color_selected;
	} else {
		tr.style.backgroundColor = color_over;
	}
	
}

function tt_onMouseOut(tt,tr) {
	var index = tt_getRowIndex(tt,tr);
	if(tt_getRowSelected(tt,index)) {
		tr.style.backgroundColor = color_selected;
	} else {
		tr.style.backgroundColor = color_nomarl;
	}
}

function tt_onDblClick(tt,td) {
	var rowAndCol = tt_getRowAndCol(tt,td);
	var row = rowAndCol.row;
	var col = rowAndCol.col;
	if(col==1) {
		
	}
}

function tt_onClick(tt,tr,e) {
	if(!taskEditable) return;
	var row_index = tt_getRowIndex(tt,tr);
	if(e.shiftKey) {
		var old_row = tt_getSelectedRow(tt);
		if(old_row==-1) {
			tt_selectRow(tt,row_index,true);
		} else {
			tt_selectAllRows(tt,false);
			var minRow = Math.min(old_row,row_index);
			var maxRow = Math.max(old_row,row_index);
			var i;
			for(i=minRow;i<=maxRow;i++) {
				tt_selectRow(tt,i,true);
			}
		}
	} else if(e.ctrlKey) {
		tt_selectRow(tt,row_index,!tt_getRowSelected(tt,row_index));
	} else {
		tt_selectAllRows(tt,false);
		tt_selectRow(tt,row_index,true);
	}
	
}

function tt_onTaskTypeNameClick(tt,newid) {
	if(!taskEditable) return;
	var text = mydom("taskTypeName"+newid);
	var select = mydom("taskType"+newid);
	text.style.display = 'none';
	select.style.display = '';
	select.focus();
}

function tt_onTaskTypeBlur(tt,newid) {
	var text = mydom("taskTypeName"+newid);
	var select = mydom("taskType"+newid);
	text.style.display = '';
	select.style.display = 'none';
	text.value = select.options[select.selectedIndex].text;
}