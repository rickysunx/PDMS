package com.pxl.pkb.vo;
import com.pxl.pkb.framework.ValueObject;
public class ppm_probassist extends ValueObject {

	public int ProblemID = 0;
	public int MemberID = 0;

	public int getProblemID() {
		return ProblemID;
	}

	public void setProblemID(int ProblemID) {
		this.ProblemID = ProblemID;
	}

	public int getMemberID() {
		return MemberID;
	}

	public void setMemberID(int MemberID) {
		this.MemberID = MemberID;
	}

	public String getTableName(){
		return "ppm_probassist";
	}
	public String[] getFieldNames(){
		return new String[]{"ProblemID","MemberID"};
	}
	public String[] getFieldTypeNames(){
		return new String[]{"INT","INT"};
	}
	public String getPrimaryKey(){
		return null;
	}
}
