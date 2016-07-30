package com.pxl.ppm.itfs;

import com.pxl.pkb.vo.ppm_output;
import com.pxl.pkb.vo.sys_attach;

/**
 * 产出物相关接口
 * @author Ricky
 *
 */
public interface IOutput {
	/**
	 * 新增产出物
	 * @param output
	 * @return
	 * @throws Exception
	 */
	public int insert(ppm_output output) throws Exception;
	
	/**
	 * 删除产出物
	 * @param id
	 * @throws Exception
	 */
	public void delete(int id) throws Exception;
	
	/**
	 * 更新产出物
	 * @param output
	 * @throws Exception
	 */
	public void update(ppm_output output) throws Exception;
	/**
	 * 根据任务ID获取产出物
	 * @param output
	 * @throws Exception
	 */
	public ppm_output[] queryForWorklog(int taskID)throws Exception;
	/**
	 * 根据ID获取产出物
	 * @param output
	 * @throws Exception
	 */
	public ppm_output queryOutById(int id)throws Exception;
	
	public sys_attach[] queryByOutID(int outId)throws Exception;
}
