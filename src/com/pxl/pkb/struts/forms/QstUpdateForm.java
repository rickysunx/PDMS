package com.pxl.pkb.struts.forms;

import org.apache.struts.action.ActionForm;

public class QstUpdateForm extends ActionForm {

	private String qstID;
	private String askCate;
	private String qstTitle;
	private String qstContent;
	private String qstKeyword;
	
	public QstUpdateForm() {
	}

	public String getAskCate() {
		return askCate;
	}

	public void setAskCate(String askCate) {
		this.askCate = askCate;
	}

	public String getQstContent() {
		return qstContent;
	}

	public void setQstContent(String qstContent) {
		this.qstContent = qstContent;
	}

	public String getQstID() {
		return qstID;
	}

	public void setQstID(String qstID) {
		this.qstID = qstID;
	}

	public String getQstKeyword() {
		return qstKeyword;
	}

	public void setQstKeyword(String qstKeyword) {
		this.qstKeyword = qstKeyword;
	}

	public String getQstTitle() {
		return qstTitle;
	}

	public void setQstTitle(String qstTitle) {
		this.qstTitle = qstTitle;
	}

}
