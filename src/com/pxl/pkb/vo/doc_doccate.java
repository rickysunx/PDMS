package com.pxl.pkb.vo;
import com.pxl.pkb.framework.ValueObject;
public class doc_doccate extends ValueObject {

	public int DocID = 0;
	public int CateID = 0;

	public int getDocID() {
		return DocID;
	}

	public void setDocID(int DocID) {
		this.DocID = DocID;
	}

	public int getCateID() {
		return CateID;
	}

	public void setCateID(int CateID) {
		this.CateID = CateID;
	}

	public String getTableName(){
		return "doc_doccate";
	}
	public String[] getFieldNames(){
		return new String[]{"DocID","CateID"};
	}
	public String[] getFieldTypeNames(){
		return new String[]{"INT","INT"};
	}
	public String getPrimaryKey(){
		return null;
	}
}
