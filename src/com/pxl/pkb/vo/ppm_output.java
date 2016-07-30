package com.pxl.pkb.vo;
import com.pxl.pkb.framework.ValueObject;
public class ppm_output extends ValueObject {

	public int OutputID = 0;
	public int TaskID = 0;
	public int SeqNum = 0;
	public String OutputName = null;


	public int getOutputID() {
		return OutputID;
	}

	public void setOutputID(int OutputID) {
		this.OutputID = OutputID;
	}

	public int getTaskID() {
		return TaskID;
	}

	public void setTaskID(int TaskID) {
		this.TaskID = TaskID;
	}

	public int getSeqNum() {
		return SeqNum;
	}

	public void setSeqNum(int SeqNum) {
		this.SeqNum = SeqNum;
	}

	public String getOutputName() {
		return OutputName;
	}

	public void setOutputName(String OutputName) {
		this.OutputName = OutputName;
	}

	public String getTableName(){
		return "ppm_output";
	}
	public String[] getFieldNames(){
		return new String[]{"OutputID","TaskID","SeqNum","OutputName"};
	}
	public String[] getFieldTypeNames(){
		return new String[]{"INT","INT","INT","VARCHAR"};
	}
	public String getPrimaryKey(){
		return "OutputID";
	}
}
