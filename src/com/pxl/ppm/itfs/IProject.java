package com.pxl.ppm.itfs;

import com.pxl.pkb.vo.ppm_project;
import com.pxl.pkb.vo.ppm_version;

/**
 * 有关项目的接口服务
 * @author Ricky
 *
 */
public interface IProject {
	
	/**
	 * 常量定义
	 */
	
	public static final int PROJECT_STATUS_EDITING = 0;    //编辑中状态
	public static final int PROJECT_STATUS_PUBLISHED = 1;  //已发布状态
	
	/**
	 * 查询所有的项目
	 * @return
	 * @throws Exception
	 */
	public ppm_project [] queryAll() throws Exception;
	
	/**
	 * 按照ID查询指定的项目
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public ppm_project queryByID(int id) throws Exception;
	
	/**
	 * 根据条件SQL查询指定项目
	 * @param whereSQL
	 * @return
	 * @throws Exception
	 */
	public ppm_project [] queryByWhere(String whereSQL) throws Exception;
	
	/**
	 * 根据项目的ID，对项目进行更新
	 * 后台服务中带有对项目信息进行校验的逻辑。
	 * 已经处于发布状态的项目，无法进行更新。
	 * @param project
	 * @throws Exception
	 */
	public void update(ppm_project project) throws Exception;
	
	/**
	 * 插入一条项目，返回项目的ID号
	 * 后台服务中带有对项目信息进行校验的逻辑
	 * @param project
	 * @return 项目ID号
	 * @throws Exception
	 */
	public int insert(ppm_project project) throws Exception;
	
	/**
	 * 发布项目，将项目改为发布状态
	 * @param projectID
	 * @throws Exception
	 */
	public void publish(int projectID) throws Exception;
	
	/**
	 * 修订项目，将产生项目新版本，将项目修改为编辑中状态。
	 * 
	 * @param projectID 不能为空
	 * @param verName 如果为空，则根据当前系统时间产生默认版本名称
	 * @return 项目新版本
	 * @throws Exception
	 */
	public ppm_version revise(int projectID,String verName,String first) throws Exception;
	
	/**
	 * 删除项目，后台服务中负责进行项目删除合法性校验
	 * @param projectID
	 * @throws Exception
	 */
	public void delete(int projectID) throws Exception;
	/**
	 * 根据sql查询项目版本
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public ppm_version[] queryBysql(String sql) throws Exception;
	
	/**
	 * 
	 * @param projectID
	 * @return
	 * @throws Exception
	 */
	public ppm_version queryLatestVersion(int projectID) throws Exception;
	
}
