package com.pxl.pkb.struts.forms;

import org.apache.struts.action.ActionForm;

public class DocEditForm extends ActionForm {
	private String docCate;
	private String docTitle;
	private String docIntro;
	private String docKeyword;
	private String docID;
	
	public String getDocCate() {
		return docCate;
	}
	public void setDocCate(String docCate) {
		this.docCate = docCate;
	}
	public String getDocIntro() {
		return docIntro;
	}
	public void setDocIntro(String docIntro) {
		this.docIntro = docIntro;
	}
	public String getDocKeyword() {
		return docKeyword;
	}
	public void setDocKeyword(String docKeyword) {
		this.docKeyword = docKeyword;
	}
	public String getDocTitle() {
		return docTitle;
	}
	public void setDocTitle(String docTitle) {
		this.docTitle = docTitle;
	}
	public String getDocID() {
		return docID;
	}
	public void setDocID(String docID) {
		this.docID = docID;
	}
}
