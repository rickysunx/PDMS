package com.pxl.pkb.vo;
import com.pxl.pkb.framework.ValueObject;
public class ppm_project extends ValueObject {

	public int ProjectID = 0;
	public String ProjectCode = null;
	public String ProjectName = null;
	public int ProjectStatus = 0;
	public String ProjectValue =null;
	public int getProjectID() {
		return ProjectID;
	}

	public void setProjectID(int ProjectID) {
		this.ProjectID = ProjectID;
	}

	public String getProjectCode() {
		return ProjectCode;
	}

	public void setProjectCode(String ProjectCode) {
		this.ProjectCode = ProjectCode;
	}

	public String getProjectName() {
		return ProjectName;
	}

	public void setProjectName(String ProjectName) {
		this.ProjectName = ProjectName;
	}

	public int getProjectStatus() {
		return ProjectStatus;
	}

	public void setProjectStatus(int ProjectStatus) {
		this.ProjectStatus = ProjectStatus;
	}

	public String getTableName(){
		return "ppm_project";
	}
	public String[] getFieldNames(){
		return new String[]{"ProjectID","ProjectCode","ProjectName","ProjectStatus","ProjectValue"};
	}
	public String[] getFieldTypeNames(){
		return new String[]{"INT","VARCHAR","VARCHAR","INT","VARCHAR"};
	}
	public String getPrimaryKey(){
		return "ProjectID";
	}

	public String getProjectValue() {
		return ProjectValue;
	}

	public void setProjectValue(String projectValue) {
		ProjectValue = projectValue;
	}

	
}
