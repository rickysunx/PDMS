package com.pxl.ppm.itfs;

import com.pxl.pkb.extendvo.ppm_taskon;
import com.pxl.pkb.vo.ppm_task;
import com.pxl.pkb.vo.ppm_taskassist;

/**
 * 任务相关操作的接口
 * @author Ricky
 *
 */
public interface ITask {
	/**
	 * 根据项目ID查询任务
	 * @param projectID
	 * @return
	 * @throws Exception
	 */
	public ppm_task [] queryTaskByProject(int projectID) throws Exception;
	
	/**
	 * 为工作日志查询任务
	 * @param userID
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public ppm_taskon [] queryTaskForWorklog(int userID,String date,String lastFivetask) throws Exception;
	
	/**
	 * 新增任务
	 * @param task
	 * @return
	 * @throws Exception
	 */
	public int insert(ppm_task task) throws Exception;
	
	/**
	 * 删除任务
	 * @param taskid
	 * @throws Exception
	 */
	public void delete(int taskid) throws Exception;
	
	/**
	 * 更新任务
	 * @param task
	 * @throws Exception
	 */
	public void update(ppm_task task) throws Exception;
	/**
	 * 查询任务配合人
	 * @param task
	 * @throws Exception
	 */
	public ppm_taskassist [] queryByWhere(String whereSQL) throws Exception;

	public ppm_task  queryTaskbyID (int id) throws Exception;
	
	/**
	 * 保存任务
	 * @param tasks
	 * @throws Exception
	 */
	public void saveTask(int projectID,ppm_task [] tasks,String [] assists) throws Exception;
	
	public ppm_task[] queryBywhere(String sql) throws Exception;
	
	/**
	 * 查询任务配合人
	 * @param taskid
	 * @return 返回数组，str[0]-以逗号分隔的ID，str[1]-以逗号分隔的名称
	 * @throws Exception
	 */
	public String [] queryAssistsByTaskID(int taskid) throws Exception;
    /**更新任务
     * @throws Exception 
     * 
     * 
     * */
	public void updateTask(int projectID, ppm_task taskcopy) throws Exception;
	/**
	 * 
	 */
	
	public ppm_taskon[] queryworklog(int userID, String startdate, String enddate,int projectID) throws Exception;
	
	public ppm_taskon [] queryTaskonByProject(int projectID,String startdate,String enddate) throws Exception;
}
