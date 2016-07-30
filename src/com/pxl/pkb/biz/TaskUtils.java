package com.pxl.pkb.biz;

public class TaskUtils {
	
	public static final String [] TASK_TYPE_NAMES = {"常规任务","重要任务","里程碑任务","其他"};
	
	public static String getTaskTypeNameByID(int id) {
		return TASK_TYPE_NAMES[id];
	}
}
