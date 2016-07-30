/**
 * 
 */
package com.pxl.pkb.struts.forms;

import org.apache.struts.action.ActionForm;

/**
 * @author Ricky
 *
 */
public class AnswerAddForm extends ActionForm {

	private String answerContent;
	
	/**
	 * 
	 */
	public AnswerAddForm() {
	}

	public String getAnswerContent() {
		return answerContent;
	}

	public void setAnswerContent(String answerContent) {
		this.answerContent = answerContent;
	}

}
