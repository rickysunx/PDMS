function mydom(id) {
	return document.getElementById(id);
}

function initData() {
	var args = window.dialogArguments;
	var ss = args.split(",");
	var oInputs = document.getElementsByName("memSel");
	var i,j;
	for(i=0;i<oInputs.length;i++) {
		oInputs[i].checked = false;
		for(j=0;j<ss.length;j++) {
			if(oInputs[i].value==ss[j]) {
				oInputs[i].checked = true;
				break;
			}
		}
	}
}

function on_clear() {
	var oInputs = document.getElementsByName("memSel");
	var i;
	for(i=0;i<oInputs.length;i++) {
		oInputs[i].checked = false;
	}
}

function on_ok() {
	var oInputs = document.getElementsByName("memSel");
	var i;
	var result = new Object();
	result.selids = "";
	result.selnames = "";
	for(i=0;i<oInputs.length;i++) {
		if(oInputs[i].checked) {
			if(result.selids!="") {
				result.selids+=",";
				result.selnames+=",";
			}
			result.selids+=oInputs[i].value;
			result.selnames+=oInputs[i].userName;
		}
	}
	window.returnValue=result;
	window.close();
}

function on_cancel() {
	window.returnValue = null;
	window.close();
}