/**
 * 
 */
package com.pxl.ppm.impls;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.pxl.pkb.extendvo.ppm_taskon;
import com.pxl.pkb.framework.DataManagerObject;
import com.pxl.pkb.framework.ValueObject;
import com.pxl.pkb.vo.ppm_member;
import com.pxl.pkb.vo.ppm_problem;
import com.pxl.pkb.vo.ppm_project;
import com.pxl.pkb.vo.ppm_task;
import com.pxl.pkb.vo.ppm_taskassist;
import com.pxl.pkb.vo.ppm_version;
import com.pxl.ppm.framework.BeanFactory;
import com.pxl.ppm.itfs.IProblem;
import com.pxl.ppm.itfs.IProject;
import com.pxl.ppm.itfs.ITask;

/**
 * @author Ricky
 * 
 */
public class TaskImpl implements ITask {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pxl.ppm.itfs.ITask#delete(int)
	 */
	public void delete(int taskid) throws Exception {
		DataManagerObject dmo = new DataManagerObject();
		// dmo.updateBySQL("delete from ppm_task where taskid="+taskid);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pxl.ppm.itfs.ITask#insert(com.pxl.pkb.vo.ppm_task)
	 */
	public int insert(ppm_task task) throws Exception {
		DataManagerObject dmo = new DataManagerObject();
		return dmo.insert(task);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pxl.ppm.itfs.ITask#queryTaskByProject(int)
	 */
	public ppm_task[] queryTaskByProject(int projectID) throws Exception {
		DataManagerObject dmo = new DataManagerObject();
		IProject projectService = (IProject) BeanFactory.getBean("Project");
		ppm_version ver = projectService.queryLatestVersion(projectID);
		ValueObject[] vos = dmo.queryByWhere(ppm_task.class, "ProjectID="
				+ projectID + " and VerID=" + ver.getVerID()
				+ " order by CAST(SeqNum as Signed)");
		ppm_task[] tasks = new ppm_task[vos.length];
		for (int i = 0; i < tasks.length; i++) {
			tasks[i] = (ppm_task) vos[i];
		}
		return tasks;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pxl.ppm.itfs.ITask#queryTaskForWorklog(int, java.lang.String)
	 */
	public ppm_taskon[] queryTaskForWorklog(int userID, String date,
			String lastFivetask) throws Exception {
		DataManagerObject dmo = new DataManagerObject();
		ValueObject[] voss = dmo.queryByWhere(ppm_member.class, "UserID='"
				+ userID + "'");
		List<ppm_taskon> list = new ArrayList<ppm_taskon>();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		ValueObject[] voproject = dmo.queryByWhere(ppm_project.class,
				"ProjectStatus=1");
		StringBuffer sb = new StringBuffer();
		if (null != voproject && voproject.length != 0) {
			for (int i = 0; i < voproject.length; i++) {
				ppm_project project = (ppm_project) voproject[i];
				sb.append(project.getProjectID());
				if (i != voproject.length - 1) {
					sb.append(",");
				}
			}
		}
		if (null != voss && voss.length != 0) {
			ppm_member[] members = new ppm_member[voss.length];
			if (lastFivetask.equals("fivetask")) {
				Date datenow = format.parse(date);
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(datenow);
				int inputDayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
				calendar.set(Calendar.DAY_OF_YEAR, inputDayOfYear + 5);
				String lastdate = format.format(calendar.getTime());
				date = lastdate;
			}

			for (int i = 0; i < members.length; i++) {
				members[i] = (ppm_member) voss[i];
				int memberId = members[i].getMemberID();
				String sql = "";
                String sql2= "";
				if (sb != null && !"".equals(sb) && sb.length() != 0) {
					sql = "(PxlCharger='"+ memberId+ "' or Charger='"+ memberId+ "') and c.VerID=(select VerID from ppm_version as p where p.ProjectID=c.ProjectID and p.IsLatest='1') and '"+ date+ "' between (select StartTime from ppm_task as a where (a.PxlCharger='"+ memberId+ "' or a.Charger='"+ memberId+ "' ) and a.TaskID =c.TaskID and a.VerID=(select VerID from ppm_version as p where p.ProjectID=a.ProjectID and p.IsLatest='1') ) and (select EndTime from ppm_task as b where (PxlCharger='"+ memberId+ "' or Charger='"+ memberId+ "' )  and  b.TaskID =c.TaskID and b.VerID=(select VerID from ppm_version as p where p.ProjectID=b.ProjectID and p.IsLatest='1') ) and ProjectID in("+ sb + ")   order by c.SeqNum ";
				    sql2= "(PxlCharger='"+ memberId+ "' or Charger='"+ memberId+ "') and c.VerID=(select VerID from ppm_version as p where p.ProjectID=c.ProjectID and p.IsLatest='1') and '"+ date+ "' > (select EndTime from ppm_task as b where (PxlCharger='"+ memberId+ "' or Charger='"+ memberId+ "' )  and  b.TaskID =c.TaskID and b.VerID=(select VerID from ppm_version as p where p.ProjectID=b.ProjectID and p.IsLatest='1') ) and ProjectID in("+ sb + ") and c.FinishedPercent<100  order by c.SeqNum ";
				} else {
					return null;
				}
				if(lastFivetask.equals("nowtask")){
					this.listAddTaskCharge(list, sql2, dmo);
				}
				this.listAddTaskCharge(list, sql, dmo);
				ValueObject[] voass = dmo.queryByWhere(ppm_taskassist.class,
						"MemberID='" + memberId + "'");
				if (null != voass && voass.length != 0) {
					ppm_taskassist[] ppmtasks = new ppm_taskassist[voass.length];
					ppm_taskon[] taskarray = new ppm_taskon[ppmtasks.length];
					int strslength = 0;
					for (int j = 0; j < ppmtasks.length; j++) {
						ppmtasks[j] = (ppm_taskassist) voass[j];
						int taskId = ppmtasks[j].getTaskID();
						String sqlpass = "";
						String sqlpass2="";
						if (sb != null && !"".equals(sb) && sb.length() != 0) {
							sqlpass = "TaskID='"
									+ taskId
									+ "' and c.VerID=(select VerID from ppm_version as p where p.ProjectID=c.ProjectID and p.IsLatest='1') and ProjectID in("
									+ sb + ") order by c.SeqNum";
							sqlpass2 = "TaskID='"
								+ taskId
								+ "' and c.VerID=(select VerID from ppm_version as p where p.ProjectID=c.ProjectID and p.IsLatest='1') and '"+ date+ "' > (select EndTime from ppm_task as b where  b.TaskID =c.TaskID and b.VerID=(select VerID from ppm_version as p where p.ProjectID=b.ProjectID and p.IsLatest='1') ) and c.FinishedPercent<100 and ProjectID in("
								+ sb + ") order by c.SeqNum";
						} else {
							return null;
						}
						this.listAddCharpass(dmo, format, sqlpass, date, strslength, taskarray, j);
						if(lastFivetask.equals("nowtask")){
							ValueObject[] votaskmesk = dmo.queryByWhere(ppm_taskon.class, sqlpass2);
							if (null != votaskmesk) {
								for (int x = 0; x < votaskmesk.length; x++) {
										ppm_taskon task = (ppm_taskon) votaskmesk[x];
										taskarray[j] = task;
										strslength++;

								}

							}	
						}
					}
					for (int n = 0; n < taskarray.length; n++) {
						if (taskarray[n] != null && !"".equals(taskarray[n])) {
							list.add(taskarray[n]);
						}
					}

				}
			}

		}

		if (null != list) {
			for (int i = 0; i < list.size(); i++) {
				for (int j = list.size() - 1; j > i; j--) {
					if (list.get(i).getTaskID() == list.get(j).getTaskID()) {
						list.remove(j);
					}
				}
			}
			ppm_taskon[] ppmtask = new ppm_taskon[list.size()];
			for (int i = 0; i < list.size(); i++) {
				ppmtask[i] = list.get(i);
			}
			return ppmtask;
		} else {
			return null;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pxl.ppm.itfs.ITask#update(com.pxl.pkb.vo.ppm_task)
	 */
	public void update(ppm_task task) throws Exception {
		DataManagerObject dmo = new DataManagerObject();
		dmo.update(task);
	}

	public ppm_taskassist[] queryByWhere(String whereSQL) throws Exception {
		DataManagerObject dmo = new DataManagerObject();
		ValueObject[] vos = dmo.queryByWhere(ppm_taskassist.class, whereSQL);
		ppm_taskassist[] taskassist = new ppm_taskassist[vos.length];
		for (int i = 0; i < vos.length; i++) {
			taskassist[i] = (ppm_taskassist) vos[i];
		}
		return taskassist;
	}

	public ppm_task queryTaskbyID(int id) throws Exception {
		DataManagerObject dmo = new DataManagerObject();
		ValueObject[] vos = dmo.queryByWhere(ppm_task.class, "TaskID=" + id);
		if (vos.length < 1)
			throw new Exception("根据ID查找项目失败");
		return (ppm_task) vos[0];
	}

	protected void checkNull(String content, int seqNum, String fieldName)
			throws Exception {
		if (content == null || content.trim().length() == 0) {
			throw new Exception("第" + (seqNum + 1) + "行[" + fieldName
					+ "]不能为空！");
		}
	}

	public void saveTask(int projectID, ppm_task[] tasks, String[] assists)
			throws Exception {

		IProject projectService = (IProject) BeanFactory.getBean("Project");
		ppm_version ver = projectService.queryLatestVersion(projectID);
		DataManagerObject dmo = new DataManagerObject();

		// 删除原有任务
		String sql = "delete from ppm_task where ProjectID=" + projectID
				+ " and VerID=" + ver.getVerID();
		dmo.updateBySQL(sql);

		for (int i = 0; i < tasks.length; i++) {
			ppm_task t = tasks[i];
			if (t.getTaskID() <= 0) {
				// 如果任务ID小于0，则代表是新建任务，赋予新ID，并且需对父任务和前置任务更新。
				int old_id = t.getTaskID();
				int new_id = DataManagerObject.getID();
				t.setTaskID(new_id);

				for (int j = 0; j < tasks.length; j++) {
					ppm_task t1 = tasks[j];
					// 更新父ID
					if (t1.getParentTaskID() == old_id) {
						t1.setParentTaskID(new_id);
					}
					// 更新前置任务
					if (t1.getPreTaskID() == old_id) {
						t1.setPreTaskID(new_id);
					}
				}

			}
		}

		for (int i = 0; i < tasks.length; i++) {
			ppm_task t = tasks[i];
			t.setVerID(ver.getVerID());
			int seqNum = Integer.parseInt(t.SeqNum);
			checkNull(t.getTaskTitle(), seqNum, "工作任务");
			checkNull(t.getStartTime(), seqNum, "开始时间");
			checkNull(t.getEndTime(), seqNum, "结束时间");
			checkNull(t.getTimeSpan(), seqNum, "工期");
			if (t.getCharger() == 0) {
				throw new Exception("第" + (seqNum + 1) + "行，负责人不能为空");
			}

			if (t.getPxlCharger() == 0) {
				throw new Exception("第" + (seqNum + 1) + "行，内部负责人不能为空");
			}

			dmo.insert(t);

			String sql1 = "delete from ppm_taskassist where TaskID="
					+ t.getTaskID();
			dmo.updateBySQL(sql1);

			if (assists[i] != null && assists[i].length() > 0) {
				String[] memberids = assists[i].split(",");
				if (memberids != null && memberids.length > 0) {
					ppm_taskassist[] tas = new ppm_taskassist[memberids.length];
					for (int j = 0; j < tas.length; j++) {
						tas[j] = new ppm_taskassist();
						tas[j].setTaskID(t.getTaskID());
						tas[j].setMemberID(Integer.parseInt(memberids[j]));
					}
					dmo.insert(tas);
				}
			}
		}
	}

	public String[] queryAssistsByTaskID(int taskid) throws Exception {
		String[] result = null;
		String ids = "";
		String names = "";
		String sql = "select m.memberid,m.username from ppm_taskassist ta,ppm_member m where ta.memberid=m.memberid and ta.taskid="
				+ taskid;

		DataManagerObject dmo = new DataManagerObject();

		Object[][] data = dmo.querySQL(sql);
		for (int i = 0; i < data.length; i++) {
			ids += data[i][0];
			names += data[i][1];

			if (i < data.length - 1) {
				ids += ",";
				names += ",";
			}
		}
		result = new String[] { ids, names };

		return result;
	}

	public ppm_task[] queryBywhere(String sql) throws Exception {
		DataManagerObject dmo = new DataManagerObject();
		ValueObject[] vos = dmo.queryByWhere(ppm_task.class, sql);
		ppm_task[] task = new ppm_task[vos.length];
		for (int i = 0; i < vos.length; i++) {
			task[i] = (ppm_task) vos[i];
		}
		return task;
	}

	public void updateTask(int projectID, ppm_task taskcopy) throws Exception {
		IProject projectService = (IProject) BeanFactory.getBean("Project");
		ppm_version ver = projectService.queryLatestVersion(projectID);
		DataManagerObject dmo = new DataManagerObject();

		// 删除原有任务
		String sql = "delete from ppm_task where TaskID="
				+ taskcopy.getTaskID() + " and VerID=" + ver.getVerID();
		dmo.updateBySQL(sql);
		dmo.insert(taskcopy);
	}
	
	private void listAddTaskCharge(List list,String sql,DataManagerObject dmo){
		
		try {
			ValueObject[] vos = dmo.queryByWhere(ppm_taskon.class, sql);
			if (null != vos && vos.length != 0) {
				ppm_taskon[] ppmtask = new ppm_taskon[vos.length];
				for (int j = 0; j < vos.length; j++) {
					ppmtask[j] = (ppm_taskon) vos[j];
					list.add(ppmtask[j]);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	private void listAddCharpass(DataManagerObject dmo,SimpleDateFormat format,String sqlpass,String date,int strslength,ppm_taskon[] taskarray,int j){
		try {
			ValueObject[] votaskmesk = dmo.queryByWhere(ppm_taskon.class, sqlpass);
			if (null != votaskmesk) {
				for (int x = 0; x < votaskmesk.length; x++) {
					ppm_taskon task = (ppm_taskon) votaskmesk[x];
					Date startDate = format.parse(task.getStartTime());
					Date stopDate = format.parse(task.getEndTime());
					Date dateDate = format.parse(date);
					if (dateDate.before(stopDate)&& dateDate.after(startDate)) {
						taskarray[j] = task;
						strslength++;
					}

				}

			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
   }
	
	public ppm_taskon[] queryworklog(int userID, String startdate, String enddate,int projectID) throws Exception {
		DataManagerObject dmo = new DataManagerObject();
		List<ppm_taskon> list = new ArrayList<ppm_taskon>();
			    if(userID==0&&projectID==0){
			    	String sql="";
			    	 if(projectID==0&&userID==0){
						if(startdate!=null&&!startdate.equals("")&&!enddate.equals("")&&enddate!=null){
							sql = " c.VerID=(select VerID from ppm_version as p where p.ProjectID=c.ProjectID and  p.IsLatest='1') and (c.StartTime >='"+startdate+"') and (c.EndTime <='"+enddate+"' ) order by c.ProjectID ";
						}else{
							sql = " c.VerID=(select VerID from ppm_version as p where p.ProjectID=c.ProjectID and  p.IsLatest='1') order by c.ProjectID ";
						}
					
					}
			    	
			    	 ValueObject[] vos = dmo.queryByWhere(ppm_taskon.class, sql);
						
						if (null != vos && vos.length != 0) {
							ppm_taskon[] ppmtask = new ppm_taskon[vos.length];
							for (int j = 0; j < vos.length; j++) {
								ppmtask[j] = (ppm_taskon) vos[j];
								list.add(ppmtask[j]);
							}
						}
				        ValueObject[] voass = dmo.queryAll(ppm_taskassist.class);
				        if (null != voass && voass.length != 0) {
							ppm_taskassist[] ppmtasks = new ppm_taskassist[voass.length];
							ppm_taskon[] taskarray = new ppm_taskon[ppmtasks.length];
							for (int j = 0; j < ppmtasks.length; j++) {
								ppmtasks[j] = (ppm_taskassist) voass[j];
								int taskId = ppmtasks[j].getTaskID();
								String sqlpass = "";
								if (projectID== 0) {
									sqlpass = "TaskID='"+ taskId+"' and c.VerID=(select VerID from ppm_version as p where p.ProjectID=c.ProjectID and p.IsLatest='1') and (c.StartTime >='"+startdate+"') and (c.EndTime <='"+enddate+"' ) order by c.SeqNum";
								}else if(projectID!=0&&userID!=0){
									sqlpass = "TaskID='"+ taskId+"' and c.VerID=(select VerID from ppm_version as p where p.ProjectID=c.ProjectID and p.IsLatest='1') and c.ProjectID="+projectID+" and (c.StartTime >='"+startdate+"') and (c.EndTime <='"+enddate+"' ) order by c.SeqNum";
								}else if(projectID==0&&userID==0){
									sqlpass = "c.VerID=(select VerID from ppm_version as p where p.ProjectID=c.ProjectID and p.IsLatest='1') and (c.StartTime >='"+startdate+"') and (c.EndTime <='"+enddate+"' ) order by c.SeqNum";
								}
								
								ValueObject[] votaskmesk = dmo.queryByWhere(ppm_taskon.class, sqlpass);
								if (null != votaskmesk) {
									for (int x = 0; x < votaskmesk.length; x++) {
										ppm_taskon task = (ppm_taskon) votaskmesk[x];
										taskarray[j] = task;

									}

								}	
							}
							for (int n = 0; n < taskarray.length; n++) {
								if (taskarray[n] != null && !"".equals(taskarray[n])) {
									list.add(taskarray[n]);
								}
							}

						}
			    }else{
			    	ValueObject[] voss = dmo.queryByWhere(ppm_member.class, "UserID='"
							+ userID + "'");
			        ppm_member[] members = new ppm_member[voss.length];
			        for (int i = 0; i < members.length; i++) {
						members[i] = (ppm_member) voss[i];
						int memberId = members[i].getMemberID();
						String sql ="";
						if(projectID==0&&userID!=0){
							if(startdate!=null&&!startdate.equals("")&&!enddate.equals("")&&enddate!=null){
								sql = "(PxlCharger='"+ memberId+ "' or Charger='"+ memberId+ "') and c.VerID=(select VerID from ppm_version as p where p.ProjectID=c.ProjectID and p.IsLatest='1') and (c.StartTime >='"+startdate+"') and (c.EndTime <='"+enddate+"' ) order by c.ProjectID ";
							}else{
								sql = "(PxlCharger='"+ memberId+ "' or Charger='"+ memberId+ "') and c.VerID=(select VerID from ppm_version as p where p.ProjectID=c.ProjectID and p.IsLatest='1') order by c.ProjectID ";
							}
							 
						}else if(projectID!=0&&userID!=0){
							if(startdate!=null&&!startdate.equals("")&&!enddate.equals("")&&enddate!=null){
								sql = "(PxlCharger='"+ memberId+ "' or Charger='"+ memberId+ "') and c.VerID=(select VerID from ppm_version as p where p.ProjectID=c.ProjectID and  p.IsLatest='1') and (c.StartTime >='"+startdate+"') and (c.EndTime <='"+enddate+"' ) and c.ProjectID="+projectID+" order by c.ProjectID ";
							}else{
								sql = "(PxlCharger='"+ memberId+ "' or Charger='"+ memberId+ "') and c.VerID=(select VerID from ppm_version as p where p.ProjectID=c.ProjectID and  p.IsLatest='1') and c.ProjectID="+projectID+" order by c.ProjectID ";
							}
							
						}
						ValueObject[] vos = dmo.queryByWhere(ppm_taskon.class, sql);
					
						if (null != vos && vos.length != 0) {
							ppm_taskon[] ppmtask = new ppm_taskon[vos.length];
							for (int j = 0; j < vos.length; j++) {
								ppmtask[j] = (ppm_taskon) vos[j];
								list.add(ppmtask[j]);
							}
						}
				        ValueObject[] voass = dmo.queryByWhere(ppm_taskassist.class,
								"MemberID='" + memberId + "'");
						if (null != voass && voass.length != 0) {
							ppm_taskassist[] ppmtasks = new ppm_taskassist[voass.length];
							ppm_taskon[] taskarray = new ppm_taskon[ppmtasks.length];
							for (int j = 0; j < ppmtasks.length; j++) {
								ppmtasks[j] = (ppm_taskassist) voass[j];
								int taskId = ppmtasks[j].getTaskID();
								String sqlpass = "";
								if (projectID== 0) {
									sqlpass = "TaskID='"+ taskId+"' and c.VerID=(select VerID from ppm_version as p where p.ProjectID=c.ProjectID and p.IsLatest='1') and (c.StartTime >='"+startdate+"') and (c.EndTime <='"+enddate+"' ) order by c.SeqNum";
								}else if(projectID!=0&&userID!=0){
									sqlpass = "TaskID='"+ taskId+"' and c.VerID=(select VerID from ppm_version as p where p.ProjectID=c.ProjectID and p.IsLatest='1') and c.ProjectID="+projectID+" and (c.StartTime >='"+startdate+"') and (c.EndTime <='"+enddate+"' ) order by c.SeqNum";
								}else if(projectID==0&&userID==0){
									sqlpass = "c.VerID=(select VerID from ppm_version as p where p.ProjectID=c.ProjectID and p.IsLatest='1') and (c.StartTime >='"+startdate+"') and (c.EndTime <='"+enddate+"' ) order by c.SeqNum";
								}
								
								ValueObject[] votaskmesk = dmo.queryByWhere(ppm_taskon.class, sqlpass);
								if (null != votaskmesk) {
									for (int x = 0; x < votaskmesk.length; x++) {
										ppm_taskon task = (ppm_taskon) votaskmesk[x];
										taskarray[j] = task;

									}

								}	
							}
							for (int n = 0; n < taskarray.length; n++) {
								if (taskarray[n] != null && !"".equals(taskarray[n])) {
									list.add(taskarray[n]);
								}
							}

						}
					}
			    }
				

		if (null != list) {
			for (int i = 0; i < list.size(); i++) {
				for (int j = list.size() - 1; j > i; j--) {
					if (list.get(i).getTaskID() == list.get(j).getTaskID()) {
						list.remove(j);
					}
				}
			}
			ppm_taskon[] ppmtask = new ppm_taskon[list.size()];
			for (int i = 0; i < list.size(); i++) {
				ppmtask[i] = list.get(i);
			}
			return ppmtask;
		} else {
			return null;
		}

	}

	public ppm_taskon[] queryTaskonByProject(int projectID,String startdate,String enddate) throws Exception {
		DataManagerObject dmo = new DataManagerObject();
		IProject projectService = (IProject) BeanFactory.getBean("Project");
		ppm_version ver = projectService.queryLatestVersion(projectID);
		ValueObject[] vos =null;
		if(startdate!=null&&!startdate.equals("")&&enddate!=null&&!enddate.equals("")){
			vos = dmo.queryByWhere(ppm_taskon.class, "ProjectID="
					+ projectID + " and VerID=" + ver.getVerID()
					+ " and (StartTime >='"+startdate+"') and (EndTime <='"+enddate+"' ) order by CAST(SeqNum as Signed)");
		}else{
			vos = dmo.queryByWhere(ppm_taskon.class, "ProjectID="
					+ projectID + " and VerID=" + ver.getVerID()
					+ " order by CAST(SeqNum as Signed)");
		}
			
			
		ppm_taskon[] tasks = new ppm_taskon[vos.length];
		for (int i = 0; i < tasks.length; i++) {
			tasks[i] = (ppm_taskon) vos[i];
		}
		return tasks;
	}
}
