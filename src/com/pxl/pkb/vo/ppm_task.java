package com.pxl.pkb.vo;
import com.pxl.pkb.framework.ValueObject;
public class ppm_task extends ValueObject {

	public int TaskID = 0;
	public int VerID = 0;
	public int ProjectID = 0;
	public String SeqNum = null;
	public int ParentTaskID = 0;
	public int PreTaskID = 0;
	public String TaskTitle = null;
	public int TaskType = 0;
	public String StartTime = null;
	public String EndTime = null;
	public String TimeSpan = null;
	public int Charger =0;
	public int PxlCharger = 0;
	public int FinishedPercent = 0;
	public String FinishedDetail = null;
	public String UnfinishedReason = null;
	public String Notes = null;
   
	

	public int getTaskID() {
		return TaskID;
	}

	public void setTaskID(int TaskID) {
		this.TaskID = TaskID;
	}

	public int getVerID() {
		return VerID;
	}

	public void setVerID(int VerID) {
		this.VerID = VerID;
	}

	public int getProjectID() {
		return ProjectID;
	}

	public void setProjectID(int ProjectID) {
		this.ProjectID = ProjectID;
	}

	public String getSeqNum() {
		return SeqNum;
	}

	public void setSeqNum(String SeqNum) {
		this.SeqNum = SeqNum;
	}

	public int getParentTaskID() {
		return ParentTaskID;
	}

	public void setParentTaskID(int ParentTaskID) {
		this.ParentTaskID = ParentTaskID;
	}

	public int getPreTaskID() {
		return PreTaskID;
	}

	public void setPreTaskID(int PreTaskID) {
		this.PreTaskID = PreTaskID;
	}

	public String getTaskTitle() {
		return TaskTitle;
	}

	public void setTaskTitle(String TaskTitle) {
		this.TaskTitle = TaskTitle;
	}

	public int getTaskType() {
		return TaskType;
	}

	public void setTaskType(int TaskType) {
		this.TaskType = TaskType;
	}

	public String getStartTime() {
		return StartTime;
	}

	public void setStartTime(String StartTime) {
		this.StartTime = StartTime;
	}

	public String getEndTime() {
		return EndTime;
	}

	public void setEndTime(String EndTime) {
		this.EndTime = EndTime;
	}

	public String getTimeSpan() {
		return TimeSpan;
	}

	public void setTimeSpan(String TimeSpan) {
		this.TimeSpan = TimeSpan;
	}

	public int getCharger() {
		return Charger;
	}

	public void setCharger(int Charger) {
		this.Charger = Charger;
	}

	public int getPxlCharger() {
		return PxlCharger;
	}

	public void setPxlCharger(int PxlCharger) {
		this.PxlCharger = PxlCharger;
	}

	public int getFinishedPercent() {
		return FinishedPercent;
	}

	public void setFinishedPercent(int FinishedPercent) {
		this.FinishedPercent = FinishedPercent;
	}

	public String getFinishedDetail() {
		return FinishedDetail;
	}

	public void setFinishedDetail(String FinishedDetail) {
		this.FinishedDetail = FinishedDetail;
	}

	public String getUnfinishedReason() {
		return UnfinishedReason;
	}

	public void setUnfinishedReason(String UnfinishedReason) {
		this.UnfinishedReason = UnfinishedReason;
	}

	public String getNotes() {
		return Notes;
	}

	public void setNotes(String Notes) {
		this.Notes = Notes;
	}

	public String getTableName(){
		return "ppm_task";
	}
	public String[] getFieldNames(){
		return new String[]{"TaskID","VerID","ProjectID","SeqNum","ParentTaskID","PreTaskID","TaskTitle","TaskType","StartTime","EndTime","TimeSpan","Charger","PxlCharger","FinishedPercent","FinishedDetail","UnfinishedReason","Notes"};
	}
	public String[] getFieldTypeNames(){
		return new String[]{"INT","INT","INT","VARCHAR","INT","INT","VARCHAR","INT","CHAR","CHAR","DECIMAL","INT","INT","INT","VARCHAR","VARCHAR","VARCHAR"};
	}
	public String getPrimaryKey(){
		return null;
	}
}
