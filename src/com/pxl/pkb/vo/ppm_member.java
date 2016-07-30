package com.pxl.pkb.vo;
import com.pxl.pkb.framework.ValueObject;
public class ppm_member extends ValueObject {

	public int MemberID = 0;
	public int UserID = 0;
	public String UserName = null;
	public String Unit = null;
	public String Position = null;
	public String CustRole = null;
	public String UfidaRole = null;
	public String Tel = null;
	public String EMail = null;
	public String Notes = null;
	public int ProjectID = 0;

	public int getMemberID() {
		return MemberID;
	}

	public void setMemberID(int MemberID) {
		this.MemberID = MemberID;
	}

	public int getUserID() {
		return UserID;
	}

	public void setUserID(int UserID) {
		this.UserID = UserID;
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String UserName) {
		this.UserName = UserName;
	}

	public String getUnit() {
		return Unit;
	}

	public void setUnit(String Unit) {
		this.Unit = Unit;
	}

	public String getPosition() {
		return Position;
	}

	public void setPosition(String Position) {
		this.Position = Position;
	}

	public String getCustRole() {
		return CustRole;
	}

	public void setCustRole(String CustRole) {
		this.CustRole = CustRole;
	}

	public String getUfidaRole() {
		return UfidaRole;
	}

	public void setUfidaRole(String UfidaRole) {
		this.UfidaRole = UfidaRole;
	}

	public String getTel() {
		return Tel;
	}

	public void setTel(String Tel) {
		this.Tel = Tel;
	}

	public String getEMail() {
		return EMail;
	}

	public void setEMail(String EMail) {
		this.EMail = EMail;
	}

	public String getNotes() {
		return Notes;
	}

	public void setNotes(String Notes) {
		this.Notes = Notes;
	}

	public int getProjectID() {
		return ProjectID;
	}

	public void setProjectID(int ProjectID) {
		this.ProjectID = ProjectID;
	}

	public String getTableName(){
		return "ppm_member";
	}
	public String[] getFieldNames(){
		return new String[]{"MemberID","UserID","UserName","Unit","Position","CustRole","UfidaRole","Tel","EMail","Notes","ProjectID"};
	}
	public String[] getFieldTypeNames(){
		return new String[]{"INT","INT","VARCHAR","VARCHAR","VARCHAR","VARCHAR","VARCHAR","VARCHAR","VARCHAR","VARCHAR","INT"};
	}
	public String getPrimaryKey(){
		return "MemberID";
	}
}
