package com.pxl.ppm.itfs;

import com.pxl.pkb.vo.ppm_workdetail;

/**
 * 工作内容详情
 * @author Ricky
 *
 */
public interface IWorkDetail {
	/**
	 * 插入工作详情
	 * @param detail
	 * @return
	 * @throws Exception
	 */
	public int insert(ppm_workdetail detail) throws Exception;
	
	/**
	 * 删除工作详情
	 * @param detailid
	 * @throws Exception
	 */
	public void delete(int detailid) throws Exception;
	
	/**
	 * 更新工作详情
	 * @param detail
	 * @throws Exception
	 */
	public void update(ppm_workdetail detail) throws Exception;
	
	/**
	 * 查询工作详情
	 * @param taskID
	 * @param userID
	 * @return
	 * @throws Exception
	 */
	public ppm_workdetail [] queryForWorklog(int taskID,int userID) throws Exception;
	
	public  ppm_workdetail  queryByid(int id)throws Exception;
	
}
