/**
 * 
 */
package com.pxl.ppm.impls;

import java.util.Arrays;

import com.pxl.pkb.biz.Pub;
import com.pxl.pkb.framework.DataManagerObject;
import com.pxl.pkb.framework.ValueObject;
import com.pxl.pkb.vo.ppm_project;
import com.pxl.pkb.vo.ppm_task;
import com.pxl.pkb.vo.ppm_version;
import com.pxl.ppm.itfs.IProject;

/**
 * @author Ricky
 *
 */
public class ProjectImpl implements IProject {

	/* (non-Javadoc)
	 * @see com.pxl.ppm.itfs.IProject#delete(int)
	 */
	public void delete(int projectID) throws Exception {
		DataManagerObject dmo = new DataManagerObject();
		//合法性 校验
		dmo.updateBySQL("delete from ppm_project where projectid="+projectID);
	}

	/* (non-Javadoc)
	 * @see com.pxl.ppm.itfs.IProject#insert(com.pxl.pkb.vo.ppm_project)
	 */
	public int insert(ppm_project project) throws Exception {
		DataManagerObject dmo = new DataManagerObject();
		int id = dmo.insert(project);
		revise(id, "初始版本","first");
		return id;
	}

	/* (non-Javadoc)
	 * @see com.pxl.ppm.itfs.IProject#publish(int)
	 */
	public void publish(int projectID) throws Exception {
		DataManagerObject dmo = new DataManagerObject();
		String sql = "update ppm_project set ProjectStatus=1 where ProjectID="+projectID;
		dmo.updateBySQL(sql);		
	}

	/* (non-Javadoc)
	 * @see com.pxl.ppm.itfs.IProject#queryAll()
	 */
	public ppm_project[] queryAll() throws Exception {
		DataManagerObject dmo = new DataManagerObject();
		ValueObject [] vos = dmo.queryAll(ppm_project.class);
		ppm_project [] projects = new ppm_project[vos.length];
		for (int i = 0; i < vos.length; i++) {
			projects[i] = (ppm_project) vos[i];
		}
		return projects;
	}

	/* (non-Javadoc)
	 * @see com.pxl.ppm.itfs.IProject#queryByID(int)
	 */
	public ppm_project queryByID(int id) throws Exception {
		DataManagerObject dmo = new DataManagerObject();
		ValueObject [] vos = dmo.queryByWhere(ppm_project.class, "ProjectID="+id);
		if(vos.length<1) throw new Exception("根据ID查找项目失败");
		return (ppm_project)vos[0];
	}

	/* (non-Javadoc)
	 * @see com.pxl.ppm.itfs.IProject#queryByWhere(java.lang.String)
	 */
	public ppm_project [] queryByWhere(String whereSQL) throws Exception {
		DataManagerObject dmo = new DataManagerObject();
		ValueObject [] vos = dmo.queryByWhere(ppm_project.class,whereSQL);
		ppm_project [] projects = new ppm_project[vos.length];
		for (int i = 0; i < vos.length; i++) {
			projects[i] = (ppm_project) vos[i];
		}
		return projects;
	}

	/* (non-Javadoc)
	 * @see com.pxl.ppm.itfs.IProject#revise(int, java.lang.String)
	 */
	public ppm_version revise(int projectID, String verName,String first) throws Exception {
		DataManagerObject dmo = new DataManagerObject();
		
		if(first==null||"".equals(first)){
			
		
		//复制任务数据
		ppm_version lastestVersion = queryLatestVersion(projectID);
		ValueObject [] oldTaskVOs = dmo.queryByWhere(ppm_task.class, 
				" VerID="+lastestVersion.getVerID()+" and ProjectID="+projectID);
		
		ppm_task [] oldTasks = new ppm_task[oldTaskVOs.length];
		for (int i = 0; i < oldTasks.length; i++) {
			oldTasks[i] = (ppm_task)oldTaskVOs[i];
		}
		
		//将原有版本更新为非最新
		String sql = "update ppm_version set IsLatest=0 where ProjectID="+projectID;
		dmo.updateBySQL(sql);
	

		//创建新版本
		ppm_version ver = new ppm_version();
		ver.setIsLatest("1");
		ver.setProjectID(projectID);
		ver.setVerName(verName==null?("版本["+Pub.getCurrTime()+"]"):verName);
		ver.setVerTime(Pub.getCurrTime());
		int verid = dmo.insert(ver);
		ver.setVerID(verid);
		
		for (int i = 0; i < oldTasks.length; i++) {
			oldTasks[i].setVerID(verid);
		}
		
		dmo.insert(oldTasks);
		
		String sql1 = "update ppm_project set ProjectStatus=0 where ProjectID="+projectID;
		dmo.updateBySQL(sql1);
		return ver;
		}else{
			ppm_version ver = new ppm_version();
			ver.setIsLatest("1");
			ver.setProjectID(projectID);
			ver.setVerName(verName==null?("版本["+Pub.getCurrTime()+"]"):verName);
			ver.setVerTime(Pub.getCurrTime());
			int verid = dmo.insert(ver);
			return ver;
		}
	}

	/* (non-Javadoc)
	 * @see com.pxl.ppm.itfs.IProject#update(com.pxl.pkb.vo.ppm_project)
	 */
	public void update(ppm_project project) throws Exception {
		//查询当前状态
		DataManagerObject dmo = new DataManagerObject();
		int projectStatus = queryProjectStatus(project.getProjectID());
		if(projectStatus==PROJECT_STATUS_PUBLISHED) throw new Exception("项目处于发布状态，无法更新！");
				
		dmo.update(project);
	}
	
	/**
	 * 根据项目id查询项目状态
	 * @param id
	 * @return
	 * @throws Exception
	 */
	protected int queryProjectStatus(int id) throws Exception {
		DataManagerObject dmo = new DataManagerObject();
		String sql = "select ProjectStatus from ppm_project where ProjectID="+id;
		Object [][] data = dmo.querySQL(sql);
		int projectStatus = 0;
		if(data.length==1) {
			projectStatus = ((Integer)data[0][0]).intValue();
		} else {
			throw new Exception();
		}
		return projectStatus;
	}
	/**
	 * 根据sql查询项目版本
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	
	public ppm_version[] queryBysql(String sql) throws Exception{
		DataManagerObject dmo = new DataManagerObject();
		ValueObject [] vos = dmo.queryByWhere(ppm_version.class,sql);
		ppm_version [] versions = new ppm_version[vos.length];
		for (int i = 0; i < vos.length; i++) {
			versions[i] = (ppm_version) vos[i];
		}
		return versions;
	}

	public ppm_version queryLatestVersion(int projectID) throws Exception {
		DataManagerObject dmo = new DataManagerObject();
		ValueObject [] vos = dmo.queryByWhere(ppm_version.class, "ProjectID="+projectID+" and IsLatest='1'");
		if(vos.length>0) {
			return (ppm_version)vos[0];
		} else {
			throw new Exception("未找到最新版本");
		}
	}
}
