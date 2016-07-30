package com.pxl.pkb.vo;
import com.pxl.pkb.framework.ValueObject;
public class ask_qstcate extends ValueObject {

	public int QstID = 0;
	public int CateID = 0;

	public int getQstID() {
		return QstID;
	}

	public void setQstID(int QstID) {
		this.QstID = QstID;
	}

	public int getCateID() {
		return CateID;
	}

	public void setCateID(int CateID) {
		this.CateID = CateID;
	}

	public String getTableName(){
		return "ask_qstcate";
	}
	public String[] getFieldNames(){
		return new String[]{"QstID","CateID"};
	}
	public String[] getFieldTypeNames(){
		return new String[]{"INT","INT"};
	}
	public String getPrimaryKey(){
		return null;
	}
}
