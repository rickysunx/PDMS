package com.pxl.pkb.vo;
import com.pxl.pkb.framework.ValueObject;
public class bd_user extends ValueObject {

	public int UserID = 0;
	public String UserCode = null;
	public String UserName = null;
	public String PassWord = null;
	public String Sex = null;
	public String EMail = null;
	public String Phone = null;
	public String QQ = null;
	public String MSN = null;
	public int DeptID = 0;
	public int RoleID = 0;
	public String IsAdmin = null;
	public int ErrorCount = 0;
	public String AddTime = null;
	public String AddIP = null;
	public String LastLogTime = null;
	public String Company = null;
	public String Province = null;
	public String City = null;
	public String Job = null;

	public int getUserID() {
		return UserID;
	}

	public void setUserID(int UserID) {
		this.UserID = UserID;
	}

	public String getUserCode() {
		return UserCode;
	}

	public void setUserCode(String UserCode) {
		this.UserCode = UserCode;
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String UserName) {
		this.UserName = UserName;
	}

	public String getPassWord() {
		return PassWord;
	}

	public void setPassWord(String PassWord) {
		this.PassWord = PassWord;
	}

	public String getSex() {
		return Sex;
	}

	public void setSex(String Sex) {
		this.Sex = Sex;
	}

	public String getEMail() {
		return EMail;
	}

	public void setEMail(String EMail) {
		this.EMail = EMail;
	}

	public String getPhone() {
		return Phone;
	}

	public void setPhone(String Phone) {
		this.Phone = Phone;
	}

	public String getQQ() {
		return QQ;
	}

	public void setQQ(String QQ) {
		this.QQ = QQ;
	}

	public String getMSN() {
		return MSN;
	}

	public void setMSN(String MSN) {
		this.MSN = MSN;
	}

	public int getDeptID() {
		return DeptID;
	}

	public void setDeptID(int DeptID) {
		this.DeptID = DeptID;
	}

	public int getRoleID() {
		return RoleID;
	}

	public void setRoleID(int RoleID) {
		this.RoleID = RoleID;
	}

	public String getIsAdmin() {
		return IsAdmin;
	}

	public void setIsAdmin(String IsAdmin) {
		this.IsAdmin = IsAdmin;
	}

	public int getErrorCount() {
		return ErrorCount;
	}

	public void setErrorCount(int ErrorCount) {
		this.ErrorCount = ErrorCount;
	}

	public String getAddTime() {
		return AddTime;
	}

	public void setAddTime(String AddTime) {
		this.AddTime = AddTime;
	}

	public String getAddIP() {
		return AddIP;
	}

	public void setAddIP(String AddIP) {
		this.AddIP = AddIP;
	}

	public String getLastLogTime() {
		return LastLogTime;
	}

	public void setLastLogTime(String LastLogTime) {
		this.LastLogTime = LastLogTime;
	}

	public String getCompany() {
		return Company;
	}

	public void setCompany(String Company) {
		this.Company = Company;
	}

	public String getProvince() {
		return Province;
	}

	public void setProvince(String Province) {
		this.Province = Province;
	}

	public String getCity() {
		return City;
	}

	public void setCity(String City) {
		this.City = City;
	}

	public String getJob() {
		return Job;
	}

	public void setJob(String Job) {
		this.Job = Job;
	}

	public String getTableName(){
		return "bd_user";
	}
	public String[] getFieldNames(){
		return new String[]{"UserID","UserCode","UserName","PassWord","Sex","EMail","Phone","QQ","MSN","DeptID","RoleID","IsAdmin","ErrorCount","AddTime","AddIP","LastLogTime","Company","Province","City","Job"};
	}
	public String[] getFieldTypeNames(){
		return new String[]{"INT","VARCHAR","VARCHAR","VARCHAR","CHAR","VARCHAR","VARCHAR","VARCHAR","VARCHAR","INT","INT","CHAR","INT","CHAR","VARCHAR","CHAR","VARCHAR","VARCHAR","VARCHAR","VARCHAR"};
	}
	public String getPrimaryKey(){
		return "UserID";
	}
}
