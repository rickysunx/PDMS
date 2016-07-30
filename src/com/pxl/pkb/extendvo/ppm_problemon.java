package com.pxl.pkb.extendvo;

import com.pxl.pkb.vo.ppm_problem;

public class ppm_problemon extends ppm_problem {
    public String[] useroper=null;//用于存储配合人,数据库中不必有
	public String chargerName=null;//用于存储分责任，数据库中不必有
    public String[] useroperid=null;//用于存储配合人Id,数据库中不必有
    public String taskName=null;//用与存储任务名称,数据库中不必有
    public String projectName=null;//用于存储项目名称，数据库中不必有
    public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String[] getUseroper() {
		return useroper;
	}

	public void setUseroper(String[] useroper) {
		this.useroper = useroper;
	}
	
	public String getChargerName() {
		return chargerName;
	}

	public void setChargerName(String chargerName) {
		this.chargerName = chargerName;
	}

	public String[] getUseroperid() {
		return useroperid;
	}

	public void setUseroperid(String[] useroperid) {
		this.useroperid = useroperid;
	}
}
