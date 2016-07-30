/**
 * 
 */
package com.pxl.ppm.actions;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.pxl.pkb.framework.DataManagerObject;
import com.pxl.pkb.framework.PkbAction;
import com.pxl.pkb.vo.ppm_task;
import com.pxl.ppm.framework.BeanFactory;
import com.pxl.ppm.itfs.ITask;

/**
 * @author Ricky
 *
 */
public class TaskSaveAction extends PkbAction {

	/**
	 * 
	 */
	public TaskSaveAction() {
		super();
	}

	/* (non-Javadoc)
	 * @see com.pxl.pkb.framework.PkbAction#pkbExecute(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public ActionForward pkbExecute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		try {
			String strOidCount = request.getParameter("oidCount");
			String strProjectID = request.getParameter("projectID");
			String strLineDRs = request.getParameter("lineDRs");
			int projectID = Integer.parseInt(strProjectID);
			int oidCount = Integer.parseInt(strOidCount);
			
			String [] arrDR = strLineDRs.split(",");
			
			Vector<ppm_task> v = new Vector<ppm_task>();
			Vector<String> vAssist = new Vector<String>();
			for (int i = 0; i < arrDR.length; i++) {
				if(arrDR[i].equals("0")) {
					ppm_task t = new ppm_task();
					t.setSeqNum(request.getParameter("seqNum"+i));
					int seqNum = Integer.parseInt(t.getSeqNum());
					
					String charger = request.getParameter("charger"+i);
					if(charger==null || charger.trim().length()==0) throw new Exception("第"+(seqNum+1)+"行，负责人不能为空");
					
					String pxlCharger = request.getParameter("pxlCharger"+i);
					if(pxlCharger==null || pxlCharger.trim().length()==0) throw new Exception("第"+(seqNum+1)+"行，内部负责人不能为空");
					t.setParentTaskID(Integer.parseInt(request.getParameter("parentTaskID"+i)));
					t.setTaskID(Integer.parseInt(request.getParameter("taskID"+i)));
					t.setProjectID(Integer.parseInt(request.getParameter("projectID")));
					t.setPreTaskID(Integer.parseInt(request.getParameter("preTaskID"+i)));
					t.setTaskTitle(request.getParameter("taskTitle"+i));
					t.setTaskType(Integer.parseInt(request.getParameter("taskType"+i)));
					t.setStartTime(request.getParameter("startTime"+i));
					t.setEndTime(request.getParameter("endTime"+i));
					t.setTimeSpan(request.getParameter("timeSpan"+i));
					t.setCharger(Integer.parseInt(request.getParameter("charger"+i)));
					t.setPxlCharger(Integer.parseInt(request.getParameter("pxlCharger"+i)));
					t.setNotes(request.getParameter("notes"+i));
					vAssist.add(request.getParameter("assist"+i));
					v.add(t);
				}
			}
			ppm_task [] tasks = new ppm_task[v.size()];
			v.copyInto(tasks);
			
			String [] assists = new String[vAssist.size()];
			vAssist.copyInto(assists);
			
			ITask taskService = (ITask)BeanFactory.getBean("Task");
			taskService.saveTask(projectID,tasks,assists);
			
			request.setAttribute("ok", Boolean.TRUE);
			
		} catch (Exception e) {
			e.printStackTrace();
			DataManagerObject.getConnection().rollback();
			request.setAttribute("ok", Boolean.FALSE);
			request.setAttribute("errorinfo", e.getMessage());
		}
		
		return mapping.findForward("success");
	}

}
