package com.pxl.pkb.extendvo;

import com.pxl.pkb.vo.ppm_task;

public class ppm_taskon extends ppm_task{
    
	 public String pxlUsername;//用于保存普信联负责人，数据库中不必有
	    public String chargename;//用于保存负责人，数据库中不必有
	    public String projectName;//用于存储任务所在项目，数据库中不必有
	    
	    public String getProjectName() {
			return projectName;
		}

		public void setProjectName(String projectName) {
			this.projectName = projectName;
		}

		public String getChargename() {
			return chargename;
		}

		public void setChargename(String chargename) {
			this.chargename = chargename;
		}

		public String getPxlUsername() {
			return pxlUsername;
		}

		public void setPxlUsername(String pxlUsername) {
			this.pxlUsername = pxlUsername;
		}
}
