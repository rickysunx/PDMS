package com.pxl.pkb.vo;
import com.pxl.pkb.framework.ValueObject;
public class ppm_workdetail extends ValueObject {

	public int WorkDetailID = 0;
	public int TaskID = 0;
	public int UserID = 0;
	public int SeqNum = 0;
	public String DetailContent = null;
	public String WorkAddress = null;
	public int getWorkDetailID() {
		return WorkDetailID;
	}

	public void setWorkDetailID(int WorkDetailID) {
		this.WorkDetailID = WorkDetailID;
	}

	public int getTaskID() {
		return TaskID;
	}

	public void setTaskID(int TaskID) {
		this.TaskID = TaskID;
	}

	public int getUserID() {
		return UserID;
	}

	public void setUserID(int UserID) {
		this.UserID = UserID;
	}

	public int getSeqNum() {
		return SeqNum;
	}

	public void setSeqNum(int SeqNum) {
		this.SeqNum = SeqNum;
	}

	public String getDetailContent() {
		return DetailContent;
	}

	public void setDetailContent(String DetailContent) {
		this.DetailContent = DetailContent;
	}

	public String getWorkAddress() {
		return WorkAddress;
	}

	public void setWorkAddress(String WorkAddress) {
		this.WorkAddress = WorkAddress;
	}

	public String getTableName(){
		return "ppm_workdetail";
	}
	public String[] getFieldNames(){
		return new String[]{"WorkDetailID","TaskID","UserID","SeqNum","DetailContent","WorkAddress"};
	}
	public String[] getFieldTypeNames(){
		return new String[]{"INT","INT","INT","INT","VARCHAR","VARCHAR"};
	}
	public String getPrimaryKey(){
		return "WorkDetailID";
	}

}
