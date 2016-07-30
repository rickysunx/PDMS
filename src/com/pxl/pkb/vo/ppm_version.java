package com.pxl.pkb.vo;
import com.pxl.pkb.framework.ValueObject;
public class ppm_version extends ValueObject {

	public int VerID = 0;
	public int ProjectID = 0;
	public String VerName = null;
	public String VerTime = null;
	public String IsLatest = null;

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

	public String getVerName() {
		return VerName;
	}

	public void setVerName(String VerName) {
		this.VerName = VerName;
	}

	public String getVerTime() {
		return VerTime;
	}

	public void setVerTime(String VerTime) {
		this.VerTime = VerTime;
	}

	public String getIsLatest() {
		return IsLatest;
	}

	public void setIsLatest(String IsLatest) {
		this.IsLatest = IsLatest;
	}

	public String getTableName(){
		return "ppm_version";
	}
	public String[] getFieldNames(){
		return new String[]{"VerID","ProjectID","VerName","VerTime","IsLatest"};
	}
	public String[] getFieldTypeNames(){
		return new String[]{"INT","INT","VARCHAR","CHAR","CHAR"};
	}
	public String getPrimaryKey(){
		return "VerID";
	}
}
