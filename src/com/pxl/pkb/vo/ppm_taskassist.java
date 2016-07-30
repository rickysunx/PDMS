package com.pxl.pkb.vo;
import com.pxl.pkb.framework.ValueObject;
public class ppm_taskassist extends ValueObject {

	public int TaskID = 0;
	public int MemberID = 0;

	public int getTaskID() {
		return TaskID;
	}

	public void setTaskID(int TaskID) {
		this.TaskID = TaskID;
	}

	public int getMemberID() {
		return MemberID;
	}

	public void setMemberID(int MemberID) {
		this.MemberID = MemberID;
	}

	public String getTableName(){
		return "ppm_taskassist";
	}
	public String[] getFieldNames(){
		return new String[]{"TaskID","MemberID"};
	}
	public String[] getFieldTypeNames(){
		return new String[]{"INT","INT"};
	}
	public String getPrimaryKey(){
		return null;
	}
}
