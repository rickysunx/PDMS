package com.pxl.pkb.struts.forms;

import org.apache.struts.action.ActionForm;

public class QstAddForm extends ActionForm {

	private String askCate;
	private String qstTitle;
	private String qstContent;
	private String qstKeyword;
	
	public QstAddForm() {
	}

	public String getAskCate() {
		return askCate;
	}

	public String getQstContent() {
		return qstContent;
	}

	public void setQstContent(String qstContent) {
		this.qstContent = qstContent;
	}

	public String getQstTitle() {
		return qstTitle;
	}

	public void setQstTitle(String qstTitle) {
		this.qstTitle = qstTitle;
	}

	public void setAskCate(String askCate) {
		this.askCate = askCate;
	}

	public String getQstKeyword() {
		return qstKeyword;
	}

	public void setQstKeyword(String qstKeyword) {
		this.qstKeyword = qstKeyword;
	}

	

}
