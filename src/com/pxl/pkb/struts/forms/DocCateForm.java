package com.pxl.pkb.struts.forms;

import org.apache.struts.action.ActionForm;

public class DocCateForm extends ActionForm {
	private String docCateID;
	private String docCateName;
	public int parentDocCate;
	public String canPost;
	public String docType;
	
	public String getDocCateID() {
		return docCateID;
	}
	public void setDocCateID(String docCateID) {
		this.docCateID = docCateID;
	}
	public String getDocCateName() {
		return docCateName;
	}
	public void setDocCateName(String docCateName) {
		this.docCateName = docCateName;
	}
	public int getParentDocCate() {
		return parentDocCate;
	}
	public void setParentDocCate(int parentDocCate) {
		this.parentDocCate = parentDocCate;
	}
	public String getCanPost() {
		return canPost;
	}
	public void setCanPost(String canPost) {
		this.canPost = canPost;
	}
	public String getDocType() {
		return docType;
	}
	public void setDocType(String docType) {
		this.docType = docType;
	}
	
}
