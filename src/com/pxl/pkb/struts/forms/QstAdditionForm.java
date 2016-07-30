package com.pxl.pkb.struts.forms;

import org.apache.struts.action.ActionForm;

public class QstAdditionForm extends ActionForm {
	private String qstID;
	private String qstAddition;
	private String returnPage;
	public String getQstAddition() {
		return qstAddition;
	}
	public void setQstAddition(String qstAddtion) {
		this.qstAddition = qstAddtion;
	}
	public String getQstID() {
		return qstID;
	}
	public void setQstID(String qstID) {
		this.qstID = qstID;
	}
	public String getReturnPage() {
		return returnPage;
	}
	public void setReturnPage(String returnPage) {
		this.returnPage = returnPage;
	}
	
}
