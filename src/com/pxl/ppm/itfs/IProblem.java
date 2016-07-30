package com.pxl.ppm.itfs;

import com.pxl.pkb.extendvo.ppm_problemon;
import com.pxl.pkb.vo.ppm_member;
import com.pxl.pkb.vo.ppm_probassist;
import com.pxl.pkb.vo.ppm_problem;

/**
 * 遗留问题相关接口
 * @author Ricky
 *
 */
public interface IProblem {
	/**
	 * 为工作日志查询遗留问题
	 * @param taskID
	 * @param userID
	 * @return
	 * @throws Exception
	 */
	public ppm_problemon [] queryForWorklog(int taskID) throws Exception;
	
	/**
	 * 新增遗留问题
	 * @param problem
	 * @return
	 * @throws Exception
	 */
	public int insert(ppm_problem problem) throws Exception;
	
	/**
	 * 删除遗留问题
	 * @param problemID
	 * @throws Exception
	 */
	public void delete(int problemID) throws Exception;
	
	/**
	 * 更新遗留问题
	 * @param problem
	 * @throws Exception
	 */
	public void update(ppm_problem problem) throws Exception;
	
	/**
	 * 获得ppm_probassist，问题表与问题配合人表
	 * @param problem
	 * @throws Exception
	 */
	public ppm_probassist[] getProbassist(int problemId) throws Exception;
	/**
	 * 写入关联
	 * @param problem
	 * @throws Exception
	 */
	public void insertProbassist(ppm_probassist probassist)throws Exception;
	/**
	 * 根据id查询表
	 * @param problem
	 * @throws Exception
	 */
	public ppm_problem queryByProblemId(int problemID)throws Exception;

	public ppm_problemon[] queryBySql(String sql)throws Exception;
	public void deteProbassist(int problemId);
	public ppm_probassist[] queryProbassist(int memberID) throws Exception;
}
