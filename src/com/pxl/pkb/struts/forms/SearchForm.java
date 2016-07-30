package com.pxl.pkb.struts.forms;

import org.apache.struts.action.ActionForm;

public class SearchForm extends ActionForm {
	private String keyword;

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
}
