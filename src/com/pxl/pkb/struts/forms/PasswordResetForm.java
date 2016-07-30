package com.pxl.pkb.struts.forms;

import org.apache.struts.action.ActionForm;

public class PasswordResetForm extends ActionForm {
	private String userID;
	private String password;
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	
}
