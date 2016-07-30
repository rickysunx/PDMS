package com.pxl.pkb.struts.forms;

import org.apache.struts.action.ActionForm;

public class AskCateForm extends ActionForm {
	private String askCateID;
	private String askCateName;
	private int parentAskCate;
	private String canPost;
	private String askType;
	public String getAskType() {
		return askType;
	}
	public void setAskType(String askType) {
		this.askType = askType;
	}
	public String getAskCateID() {
		return askCateID;
	}
	public void setAskCateID(String askCateID) {
		this.askCateID = askCateID;
	}
	public String getAskCateName() {
		return askCateName;
	}
	public void setAskCateName(String askCateName) {
		this.askCateName = askCateName;
	}
	public String getCanPost() {
		return canPost;
	}
	public void setCanPost(String canPost) {
		this.canPost = canPost;
	}
	public int getParentAskCate() {
		return parentAskCate;
	}
	public void setParentAskCate(int parentAskCate) {
		this.parentAskCate = parentAskCate;
	}
	
}
