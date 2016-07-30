package com.pxl.ppm.itfs;

import com.pxl.pkb.vo.bd_user;
import com.pxl.pkb.vo.ppm_member;
import com.pxl.pkb.vo.ppm_project;

/**
 * 项目成员相关接口
 * @author Ricky
 *
 */
public interface IMember {
	
	/**
	 * 从bd_user，获取普信联员工列表。
	 * 该服务已经根据字段做相应过滤，并将转换为项目成员表的数据
	 * @return
	 * @throws Exception
	 */
	public ppm_member [] queryPxlUser() throws Exception;
	
	/**
	 * 根据项目ID查询相关项目成员
	 * @param projectID
	 * @return
	 * @throws Exception
	 */
	public ppm_member [] queryMemberByProject(int projectID) throws Exception;
	
	
	/**
	 * 根据项目ID查询相关项目成员，并根据是否为普信联用户进行筛选
	 * @param projectID
	 * @param bPxlOnly
	 * @return
	 * @throws Exception
	 */
	public ppm_member [] queryMemberByProject(int projectID,boolean bPxlOnly) throws Exception;
	
	/**
	 * 删除项目成员
	 * 后台对删除逻辑进行合法性校验，如果不允许删除则会抛出异常
	 * @param memberID
	 * @throws Exception
	 */
	public void delete(int memberID) throws Exception;
	
	/**
	 * 增加项目成员，返回项目成员ID
	 * @param member
	 * @return
	 * @throws Exception
	 */
	public int insert(ppm_member member) throws Exception;
	
	/**
	 * 更新项目成员
	 * @param member
	 * @throws Exception
	 */
	public void update(ppm_member member) throws Exception;
	/**
	 * 显示项目成员
	 * @param memberId
	 * @throws Exception
	 */
	public ppm_member queryByID(int id) throws Exception;
	/**
	 * sql查询出项目成员
	 * @param memberId
	 * @throws Exception
	 */
	public ppm_member[] queryBySql(String sql) throws Exception;
	/*
	 *查询用户 
	 * */
	public bd_user queryUserByID(int id) throws Exception;
}
