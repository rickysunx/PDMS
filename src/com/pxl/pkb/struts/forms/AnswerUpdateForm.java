package com.pxl.pkb.struts.forms;

import org.apache.struts.action.ActionForm;

public class AnswerUpdateForm extends ActionForm {
	private String answerID;
	private String answerContent;
	public String getAnswerContent() {
		return answerContent;
	}
	public void setAnswerContent(String answerContent) {
		this.answerContent = answerContent;
	}
	public String getAnswerID() {
		return answerID;
	}
	public void setAnswerID(String answerID) {
		this.answerID = answerID;
	}
	
	
}
