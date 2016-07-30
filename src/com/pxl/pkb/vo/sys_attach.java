package com.pxl.pkb.vo;
import com.pxl.pkb.framework.ValueObject;
public class sys_attach extends ValueObject {

	public int AttachID = 0;
	public int ObjID = 0;
	public String AttachCate = null;
	public String FileName = null;
	public String FileType = null;
	public String AddTime = null;
	public int AddUser = 0;
	public String AddIP = null;
	public int getAttachID() {
		return AttachID;
	}

	public void setAttachID(int AttachID) {
		this.AttachID = AttachID;
	}

	public int getObjID() {
		return ObjID;
	}

	public void setObjID(int ObjID) {
		this.ObjID = ObjID;
	}

	public String getAttachCate() {
		return AttachCate;
	}

	public void setAttachCate(String AttachCate) {
		this.AttachCate = AttachCate;
	}

	public String getFileName() {
		return FileName;
	}

	public void setFileName(String FileName) {
		this.FileName = FileName;
	}

	public String getFileType() {
		return FileType;
	}

	public void setFileType(String FileType) {
		this.FileType = FileType;
	}

	public String getAddTime() {
		return AddTime;
	}

	public void setAddTime(String AddTime) {
		this.AddTime = AddTime;
	}

	public int getAddUser() {
		return AddUser;
	}

	public void setAddUser(int AddUser) {
		this.AddUser = AddUser;
	}

	public String getAddIP() {
		return AddIP;
	}

	public void setAddIP(String AddIP) {
		this.AddIP = AddIP;
	}

	public String getTableName(){
		return "sys_attach";
	}
	public String[] getFieldNames(){
		return new String[]{"AttachID","ObjID","AttachCate","FileName","FileType","AddTime","AddUser","AddIP"};
	}
	public String[] getFieldTypeNames(){
		return new String[]{"INT","INT","VARCHAR","VARCHAR","VARCHAR","CHAR","INT","VARCHAR"};
	}
	public String getPrimaryKey(){
		return "AttachID";
	}

	
}
