package com.pxl.pkb.struts.forms;

import org.apache.struts.action.ActionForm;

public class CommentAddForm extends ActionForm {

	private String objID;
	private String commentType;
	private String commentContent;
	private String returnPage;
	
	public CommentAddForm() {
	}

	public String getCommentContent() {
		return commentContent;
	}

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

	public String getCommentType() {
		return commentType;
	}

	public void setCommentType(String commentType) {
		this.commentType = commentType;
	}

	public String getObjID() {
		return objID;
	}

	public void setObjID(String objID) {
		this.objID = objID;
	}

	public String getReturnPage() {
		return returnPage;
	}

	public void setReturnPage(String returnPage) {
		this.returnPage = returnPage;
	}


}
