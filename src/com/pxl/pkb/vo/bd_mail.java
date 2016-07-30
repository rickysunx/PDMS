package com.pxl.pkb.vo;
import com.pxl.pkb.framework.ValueObject;
public class bd_mail extends ValueObject {

	public int MailID = 0;
	public String MailSubject = null;
	public String MailContext = null;

	public int getMailID() {
		return MailID;
	}

	public void setMailID(int MailID) {
		this.MailID = MailID;
	}

	public String getMailSubject() {
		return MailSubject;
	}

	public void setMailSubject(String MailSubject) {
		this.MailSubject = MailSubject;
	}

	public String getMailContext() {
		return MailContext;
	}

	public void setMailContext(String MailContext) {
		this.MailContext = MailContext;
	}

	public String getTableName(){
		return "bd_mail";
	}
	public String[] getFieldNames(){
		return new String[]{"MailID","MailSubject","MailContext"};
	}
	public String[] getFieldTypeNames(){
		return new String[]{"INT","VARCHAR","VARCHAR"};
	}
	public String getPrimaryKey(){
		return "MailID";
	}
}
