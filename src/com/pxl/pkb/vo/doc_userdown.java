package com.pxl.pkb.vo;
import com.pxl.pkb.framework.ValueObject;
public class doc_userdown extends ValueObject {

	public int DocID = 0;
	public int UserID = 0;
	public String DownTime = null;

	public int getDocID() {
		return DocID;
	}

	public void setDocID(int DocID) {
		this.DocID = DocID;
	}

	public int getUserID() {
		return UserID;
	}

	public void setUserID(int UserID) {
		this.UserID = UserID;
	}

	public String getDownTime() {
		return DownTime;
	}

	public void setDownTime(String DownTime) {
		this.DownTime = DownTime;
	}

	public String getTableName(){
		return "doc_userdown";
	}
	public String[] getFieldNames(){
		return new String[]{"DocID","UserID","DownTime"};
	}
	public String[] getFieldTypeNames(){
		return new String[]{"INT","INT","CHAR"};
	}
	public String getPrimaryKey(){
		return null;
	}
}
