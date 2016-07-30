package com.pxl.pkb.struts.forms;

import org.apache.struts.action.ActionForm;

public class PPMTaskForm extends ActionForm {
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
	public int Charger = 0;
	public int PxlCharger = 0;
	public int FinishedPercent = 0;
	public String FinishedDetail = null;
	public String UnfinishedReason = null;
	public String Notes = null;
	public String method =null;
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public int getCharger() {
		return Charger;
	}
	public void setCharger(int charger) {
		Charger = charger;
	}
	public String getEndTime() {
		return EndTime;
	}
	public void setEndTime(String endTime) {
		EndTime = endTime;
	}
	public String getFinishedDetail() {
		return FinishedDetail;
	}
	public void setFinishedDetail(String finishedDetail) {
		FinishedDetail = finishedDetail;
	}
	public int getFinishedPercent() {
		return FinishedPercent;
	}
	public void setFinishedPercent(int finishedPercent) {
		FinishedPercent = finishedPercent;
	}
	public String getNotes() {
		return Notes;
	}
	public void setNotes(String notes) {
		Notes = notes;
	}
	public int getParentTaskID() {
		return ParentTaskID;
	}
	public void setParentTaskID(int parentTaskID) {
		ParentTaskID = parentTaskID;
	}
	public int getPreTaskID() {
		return PreTaskID;
	}
	public void setPreTaskID(int preTaskID) {
		PreTaskID = preTaskID;
	}
	public int getProjectID() {
		return ProjectID;
	}
	public void setProjectID(int projectID) {
		ProjectID = projectID;
	}
	public int getPxlCharger() {
		return PxlCharger;
	}
	public void setPxlCharger(int pxlCharger) {
		PxlCharger = pxlCharger;
	}
	public String getSeqNum() {
		return SeqNum;
	}
	public void setSeqNum(String seqNum) {
		SeqNum = seqNum;
	}
	public String getStartTime() {
		return StartTime;
	}
	public void setStartTime(String startTime) {
		StartTime = startTime;
	}
	public int getTaskID() {
		return TaskID;
	}
	public void setTaskID(int taskID) {
		TaskID = taskID;
	}
	public String getTaskTitle() {
		return TaskTitle;
	}
	public void setTaskTitle(String taskTitle) {
		TaskTitle = taskTitle;
	}
	public int getTaskType() {
		return TaskType;
	}
	public void setTaskType(int taskType) {
		TaskType = taskType;
	}
	public String getTimeSpan() {
		return TimeSpan;
	}
	public void setTimeSpan(String timeSpan) {
		TimeSpan = timeSpan;
	}
	public String getUnfinishedReason() {
		return UnfinishedReason;
	}
	public void setUnfinishedReason(String unfinishedReason) {
		UnfinishedReason = unfinishedReason;
	}
	public int getVerID() {
		return VerID;
	}
	public void setVerID(int verID) {
		VerID = verID;
	}
}
