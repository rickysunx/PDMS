package com.pxl.pkb.vo;
import com.pxl.pkb.framework.ValueObject;
public class bd_role extends ValueObject {

	public int RoleID = 0;
	public String RoleName = null;

	public int getRoleID() {
		return RoleID;
	}

	public void setRoleID(int RoleID) {
		this.RoleID = RoleID;
	}

	public String getRoleName() {
		return RoleName;
	}

	public void setRoleName(String RoleName) {
		this.RoleName = RoleName;
	}

	public String getTableName(){
		return "bd_role";
	}
	public String[] getFieldNames(){
		return new String[]{"RoleID","RoleName"};
	}
	public String[] getFieldTypeNames(){
		return new String[]{"INT","VARCHAR"};
	}
	public String getPrimaryKey(){
		return "RoleID";
	}
}
