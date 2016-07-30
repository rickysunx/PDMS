function mydom(id) {
	return document.getElementById(id);
}

function initData() {
	var args = window.dialogArguments;
	mydom("txtNotes").innerText = args;
	
}

function on_ok() {
	window.returnValue=mydom("txtNotes").innerText;
	window.close();
}

function on_cancel() {
	window.returnValue = null;
	window.close();
}