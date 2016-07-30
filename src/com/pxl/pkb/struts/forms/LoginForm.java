package com.pxl.pkb.struts.forms;

import org.apache.struts.action.ActionForm;

public class LoginForm extends ActionForm {
	private String userCode = null;
	private String passWord = null;
	
	public LoginForm() {
		super();
	}
	
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	
	
}
