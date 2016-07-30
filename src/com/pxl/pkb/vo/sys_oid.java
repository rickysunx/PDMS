package com.pxl.pkb.vo;
import com.pxl.pkb.framework.ValueObject;
public class sys_oid extends ValueObject {

	public int SysID = 0;

	public int getSysID() {
		return SysID;
	}

	public void setSysID(int SysID) {
		this.SysID = SysID;
	}

	public String getTableName(){
		return "sys_oid";
	}
	public String[] getFieldNames(){
		return new String[]{"SysID"};
	}
	public String[] getFieldTypeNames(){
		return new String[]{"INT"};
	}
	public String getPrimaryKey(){
		return null;
	}
}
