package com.pxl.pkb.struts.forms;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class DocUpdateForm extends ActionForm {
	
	private String docid;
	private FormFile myfile;
	
	public String getDocid() {
		return docid;
	}
	public void setDocid(String docid) {
		this.docid = docid;
	}
	public FormFile getMyfile() {
		return myfile;
	}
	public void setMyfile(FormFile myfile) {
		this.myfile = myfile;
	}

}
