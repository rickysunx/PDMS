package com.pxl.pkb.vo;
import com.pxl.pkb.framework.ValueObject;
public class ppm_problem extends ValueObject {

	public int ProblemID = 0;
	public int TaskID = 0;
	public String ProblemCode = null;
	public String Description = null;
	public String Plan = null;
	public int Charger = 0;
	public String PlanTime = null;
	public int ProblemStatus = 0;

	

	public int getProblemID() {
		return ProblemID;
	}

	public void setProblemID(int ProblemID) {
		this.ProblemID = ProblemID;
	}

	public int getTaskID() {
		return TaskID;
	}

	public void setTaskID(int TaskID) {
		this.TaskID = TaskID;
	}

	public String getProblemCode() {
		return ProblemCode;
	}

	public void setProblemCode(String ProblemCode) {
		this.ProblemCode = ProblemCode;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String Description) {
		this.Description = Description;
	}

	public String getPlan() {
		return Plan;
	}

	public void setPlan(String Plan) {
		this.Plan = Plan;
	}

	public int getCharger() {
		return Charger;
	}

	public void setCharger(int Charger) {
		this.Charger = Charger;
	}

	public String getPlanTime() {
		return PlanTime;
	}

	public void setPlanTime(String PlanTime) {
		this.PlanTime = PlanTime;
	}

	public int getProblemStatus() {
		return ProblemStatus;
	}

	public void setProblemStatus(int ProblemStatus) {
		this.ProblemStatus = ProblemStatus;
	}

	public String getTableName(){
		return "ppm_problem";
	}
	public String[] getFieldNames(){
		return new String[]{"ProblemID","TaskID","ProblemCode","Description","Plan","Charger","PlanTime","ProblemStatus"};
	}
	public String[] getFieldTypeNames(){
		return new String[]{"INT","INT","VARCHAR","VARCHAR","VARCHAR","INT","CHAR","INT"};
	}
	public String getPrimaryKey(){
		return "ProblemID";
	}
}
