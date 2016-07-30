package com.pxl.pkb.struts.actions;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.pxl.pkb.biz.Consts;
import com.pxl.pkb.biz.Pub;
import com.pxl.pkb.extendvo.ppm_problemon;
import com.pxl.pkb.extendvo.ppm_taskon;
import com.pxl.pkb.framework.PkbAction;
import com.pxl.pkb.struts.forms.PPMMemberForm;
import com.pxl.pkb.struts.forms.PPMTaskForm;
import com.pxl.pkb.vo.bd_user;
import com.pxl.pkb.vo.ppm_member;
import com.pxl.pkb.vo.ppm_output;
import com.pxl.pkb.vo.ppm_probassist;
import com.pxl.pkb.vo.ppm_problem;
import com.pxl.pkb.vo.ppm_project;
import com.pxl.pkb.vo.ppm_task;
import com.pxl.pkb.vo.ppm_taskassist;
import com.pxl.pkb.vo.ppm_workdetail;
import com.pxl.ppm.framework.BeanFactory;
import com.pxl.ppm.itfs.IMember;
import com.pxl.ppm.itfs.IOutput;
import com.pxl.ppm.itfs.IProblem;
import com.pxl.ppm.itfs.IProject;
import com.pxl.ppm.itfs.ITask;
import com.pxl.ppm.itfs.IWorkDetail;

public class PPMTaskAction extends PkbAction {
    
	ITask dom=null;
	IMember mdom=null;
	IWorkDetail wdom =null;
	IProject pdom = null;
	IProblem prodom=null;
	IOutput odom =null;
	public PPMTaskAction(){
		try {
			if(null==dom){
				dom = (ITask) BeanFactory.getBean("Task");
			}
			if(null==mdom){
				mdom = (IMember) BeanFactory.getBean("Member");
			}
			if(null==wdom){
			    wdom = (IWorkDetail)BeanFactory.getBean("WorkDetail");
			}
			if(null==pdom){
				pdom =(IProject)BeanFactory.getBean("Project");
			}
			if(null==prodom){
				prodom =(IProblem)BeanFactory.getBean("Problem");
			}
			if(null==odom){
				odom =(IOutput)BeanFactory.getBean("Output");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public ActionForward pkbExecute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PPMTaskForm taskform =(PPMTaskForm) form;
		   String method =taskform.getMethod();
		   if(null!=method&&!"".equals(method)){
			   if(method.equals("add")){
				   return addExecute(mapping, taskform, request, response);
				}else if(method.equals("select")){
					return selExecute(mapping,taskform,request,response);
				}else if(method.equals("update")){
					return updaExecute(mapping,taskform,request,response);
				}else if(method.equals("delete")){
					return delExcute(mapping,taskform,request,response);
				}else if(method.equals("modify")){
					return modExcute(mapping,taskform,request,response);
				}else if(method.equals("list")){
					return listExcute(mapping,taskform,request,response);
				}else if(method.equals("choseCharge")){
					return choseChargeExcute(mapping,taskform,request,response);
				}else if(method.equals("querylist")){
					return querylistExcute(mapping,taskform,request,response);
				}else if(method.equals("queryworklog")){
					return queryworklogExcute(mapping,taskform,request,response);
				}
		   }
		return null;
	}

	//列表
	private ActionForward listExcute(ActionMapping mapping, PPMTaskForm taskform, HttpServletRequest request, HttpServletResponse response) {
		bd_user user = (bd_user)request.getSession().getAttribute(Consts.PKB_USER_SESSION_NAME);
		if(null!=user&&!"".equals(user)){
			try {
				ppm_taskon[] ppmtask = dom.queryTaskForWorklog(user.getUserID(),Pub.getCurrTime(),"nowtask");
				if(null!=ppmtask&&ppmtask.length!=0){
			     List<ppm_taskon> list =Pub.getTaskon(ppmtask);
			     ppm_taskon[] ppmtaskons = new ppm_taskon[list.size()];
			     for(int i=0;i<list.size();i++){
			    	 ppmtaskons[i]=list.get(i);
			     }
			    String[][] usernames = this.getUsernames(ppmtaskons);
			    if(null!=usernames){
				   request.setAttribute("usernames", usernames);
			     }
			   }
				ppm_member[] members =mdom.queryBySql("UserID="+user.getUserID());
				  List<ppm_problemon> list = new ArrayList<ppm_problemon>();
	              if(null!=members){
	            	  for(int i=0;i<members.length;i++){
	            		  ppm_problemon[] problems = prodom.queryBySql("ProblemStatus='1' and Charger="+members[i].getMemberID());
	            		  if(null!=problems){
	            			  for(ppm_problemon problem:problems){
	            				  ppm_task task =dom.queryTaskbyID(problem.getTaskID());
     							 ppm_project project =pdom.queryByID(task.getProjectID());
     							 problem.setTaskName(task.getTaskTitle());
     							 if(project!=null){
     								 problem.setProjectName(project.getProjectName());
     							 }else{
     								 problem.setProjectName(null);
     							 }
     							   list.add(problem);
	            			  }
	            		  }
	            		  ppm_probassist[] probass = prodom.queryProbassist(members[i].getMemberID());
	            		  if(probass!=null){
	            			  for(ppm_probassist probas:probass){
	            				 if(probas.getProblemID()!=0){
	            					 ppm_problemon[] problem = prodom.queryBySql("ProblemStatus='1' and ProblemID="+probas.getProblemID());
	            					 if(problem!=null&&problem.length!=0){
	            						 for(ppm_problemon pro:problem){
	            							 if(pro!=null&&pro.getTaskID()!=0){
	            								 ppm_task task =dom.queryTaskbyID(pro.getTaskID());
		            							 ppm_project project =pdom.queryByID(task.getProjectID());
		            							 pro.setTaskName(task.getTaskTitle());
		            							 if(project!=null){
		            								 pro.setProjectName(project.getProjectName());
		            							 }else{
		            								 pro.setProjectName(null);
		            							 }
		            						
		            							 list.add(pro);
	            							 }
	            							
	            						 }
	            					 }
	            					  
	            				 }
	            				
	            			  }
	            		  }
	            	  }
	              }
				  if(null!=list){
						 for(int i=0;i<list.size();i++){
							  for( int j = list.size()-1;j >i; j -- ){
								 if(list.get(i).getProblemID()==list.get(j).getProblemID()){
									 list.remove(j);
								 }
					    }
				  }
				 ppm_problemon[] problem = new ppm_problemon[list.size()];
				 for(int i=0;i<list.size();i++){
					 problem[i]=list.get(i);		 
				 }
				 
			  if(null!=problem){
				  String[][] useroper = new String[problem.length][10];
					for(int i=0;i<problem.length;i++){
						ppm_probassist[] probassist= prodom.getProbassist(problem[i].getProblemID());
						ppm_member ppmmember =mdom.queryByID(problem[i].getCharger());
					    if(null!=probassist){
					    	for(int j=0;j<probassist.length;j++){
					    		ppm_member member = mdom.queryByID(probassist[j].getMemberID());
					    	    useroper[i][j]=member.getUserName();
					    	}
					    	useroper[i][probassist.length+1]=ppmmember.getUserName();
					       }
						}
					request.setAttribute("useroper",useroper);
					request.setAttribute("problem", problem);
				}
		 }
			  ppm_taskon[] lastFivetask = dom.queryTaskForWorklog(user.getUserID(),Pub.getCurrTime(),"fivetask");
			  if(lastFivetask!=null&&lastFivetask.length!=0){
				  String[][] usernames = this.getUsernames(lastFivetask);
				    if(null!=usernames){
					   request.setAttribute("lastusernames", usernames);
				     }
			  }
			  request.setAttribute("lastFivetask", lastFivetask);
			  request.setAttribute("ppmtask", ppmtask);
				return mapping.findForward("list");
			} catch (Exception e) {
				e.printStackTrace();
				return mapping.findForward("errors");
			}
		}else{
			return mapping.findForward("errors");
		}
			
			
		
	}


   //编辑时详细信息
	private ActionForward modExcute(ActionMapping mapping, PPMTaskForm taskform, HttpServletRequest request, HttpServletResponse response) {
	
			return mapping.findForward("errros");
	}


   //删除信息
	private ActionForward delExcute(ActionMapping mapping, PPMTaskForm taskform, HttpServletRequest request, HttpServletResponse response) {
			return mapping.findForward("errors");

		
	}


   //修改保存的信息
	private ActionForward updaExecute(ActionMapping mapping, PPMTaskForm taskform, HttpServletRequest request, HttpServletResponse response) {
		
	 return mapping.findForward("errors");
	}


	//添加
	private ActionForward addExecute(ActionMapping mapping, PPMTaskForm taskform, HttpServletRequest request, HttpServletResponse response) {
	    
	         return mapping.findForward("errors");
		
		
	}

	//显示详细信息
	private ActionForward selExecute(ActionMapping mapping, PPMTaskForm taskform, HttpServletRequest request, HttpServletResponse response) {
		
        return mapping.findForward("errors");
 
	}
	//选取负责人
	private ActionForward choseChargeExcute(ActionMapping mapping, PPMTaskForm taskform, HttpServletRequest request, HttpServletResponse response) {
        try {
        	String taskId = request.getParameter("taskId").trim();
        	String addrownumber = request.getParameter("addrownumber").trim();
        	String opercharg = request.getParameter("opercharg").trim();
        	String addmody = request.getParameter("addmody");
        	if(null!=taskId&&!"".equals(taskId)){
            	ppm_task task =dom.queryTaskbyID(Integer.parseInt(taskId));
            	if(task!=null){
            		ppm_project ppmproject =pdom.queryByID(task.getProjectID());
            		if(null!=ppmproject){
            			ppm_member[] ppmember =mdom.queryMemberByProject(ppmproject.getProjectID());
            			if(null!=addrownumber){
            				request.setAttribute("addrownumber", addrownumber);
            				request.setAttribute("taskId", taskId);
            				request.setAttribute("addmody", addmody);
            			}
            			request.setAttribute("ppm_members", ppmember);
            			if(opercharg!=null){
            				if(opercharg.equals("charg")){
            					return mapping.findForward("choseCharge");
            				}else{
            					return mapping.findForward("choseOperuser");
            				}
            			}else{
            				return mapping.findForward("errors");
            			}
            			
            		}else{
            			return mapping.findForward("errors");
            		}
            	}else{
            		return mapping.findForward("errors");
            	}
            	
            }else{
            	return mapping.findForward("errors");
            }
		} catch (Exception e) {
			e.printStackTrace();
			return mapping.findForward("errors");
		  }
	  
	}
	
	//获取任务配合人的类的私有方法
	private String[][] getUsernames(ppm_taskon[] ppmtask) throws Exception{
		String[][] usernames= new String[ppmtask.length][10];
		for(int i=0;i<ppmtask.length;i++){
	        ppm_taskon task = ppmtask[i];
	        ppm_member pxlmember = mdom.queryByID(task.getPxlCharger());
	        ppm_member charge = mdom.queryByID(task.getCharger());
	        ppm_project project = pdom.queryByID(task.getProjectID());
	        
	        
	         task.setPxlUsername(pxlmember.getUserName());
	         task.setChargename(charge.getUserName());
	         task.setProjectName(project.getProjectName());
	        String sql="TaskID='"+task.getTaskID()+"'";
	     
	        ppm_taskassist[] taskassit= dom.queryByWhere(sql);
	        for(int j=0;j<taskassit.length;j++){
	            int memberId = taskassit[j].getMemberID();
	            ppm_member member = mdom.queryByID(memberId);
	            usernames[i][j]=member.getUserName();
	        }
		}
		return usernames;
	}
	
	//查询日志
	
	private ActionForward querylistExcute(ActionMapping mapping, PPMTaskForm taskform, HttpServletRequest request, HttpServletResponse response) throws Exception {
			String projectselid = request.getParameter("projectselid").trim();
			String memberselid = request.getParameter("memberselid").trim();
			String startdate = request.getParameter("startdate").trim();
			String enddate = request.getParameter("enddate").trim();
			List<ppm_taskon> list=null;
			ppm_taskon[] taskons=null;
			if(((projectselid==null||projectselid.equals(""))
	        		&&(startdate==null||startdate.equals(""))
	        		&&(enddate==null||enddate.equals(""))
	        		&&(memberselid==null||memberselid.equals("")))
	        		&&((startdate==null||startdate.equals(""))&&(enddate==null||enddate.equals("")))
	        		
			){
				taskons= dom.queryworklog(0,startdate, enddate,0);
			}else if((projectselid==null||projectselid.equals(""))&&(memberselid!=null&&!memberselid.equals(""))){
				if(startdate!=null&&!startdate.equals("")&&enddate!=null&&!enddate.equals("")){
					startdate = startdate+" 00:00";
					enddate = enddate+" 24:00";
				}
				taskons= dom.queryworklog(Integer.parseInt(memberselid), startdate, enddate, 0);
			}else if((projectselid!=null&&!projectselid.equals(""))&&(memberselid==null||memberselid.equals(""))){
				if(startdate!=null&&!startdate.equals("")&&enddate!=null&&!enddate.equals("")){
					startdate = startdate+" 00:00";
					enddate = enddate+" 24:00";
				}
				taskons = dom.queryTaskonByProject(Integer.parseInt(projectselid),startdate,enddate);
				
			}else if((projectselid!=null&&!projectselid.equals(""))&&(memberselid!=null&&!memberselid.equals(""))){
				if(startdate!=null&&!startdate.equals("")&&enddate!=null&&!enddate.equals("")){
					startdate = startdate+" 00:00";
					enddate = enddate+" 24:00";
				}
				taskons= dom.queryworklog(Integer.parseInt(memberselid), startdate, enddate, Integer.parseInt(projectselid));
			}else if((projectselid==null||projectselid.equals(""))&&(memberselid==null||memberselid.equals(""))){
				if(startdate!=null&&!startdate.equals("")&&enddate!=null&&!enddate.equals("")){
					startdate = startdate+" 00:00";
					enddate = enddate+" 24:00";
				}
				taskons= dom.queryworklog(0,startdate, enddate,0);
			}
			if(null!=taskons&&taskons.length!=0){
				 List<ppm_taskon> listtaskon =Pub.getTaskon5(taskons);
			     ppm_taskon[] ppmtaskons = new ppm_taskon[listtaskon.size()];
			     for(int i=0;i<listtaskon.size();i++){
			    	 ppmtaskons[i]=listtaskon.get(i);
			     }
            String[][] usernames = this.getUsernames(ppmtaskons);
			    if(null!=usernames){
				   request.setAttribute("usernames", usernames);
			     }
			}
			list=Pub.getTaskon5(taskons);
			request.setAttribute("userId",memberselid);
			request.setAttribute("taskonlist", list);
			request.setAttribute("taskons", taskons);
		return mapping.findForward("querylist");
	}
	//查询详细工作内容
	private ActionForward queryworklogExcute(ActionMapping mapping, PPMTaskForm taskform, HttpServletRequest request, HttpServletResponse response) {
           String userId = request.getParameter("userId").trim();
           String taskId = request.getParameter("taskId").trim();
        	   ppm_workdetail[] workdetail=null;
        	   try {
        		   if(userId!=null&&!userId.equals("")&&taskId!=null&&!taskId.equals("")){
        			  if(Integer.parseInt(userId)!=0){
        				  workdetail= wdom.queryForWorklog(Integer.parseInt(taskId), Integer.parseInt(userId));
        			  }
        		   }else if(taskId!=null&&!taskId.equals("")){
        			      workdetail = wdom.queryForWorklog(Integer.parseInt(taskId),0);
        		   }
        		   List<ppm_problemon> problemlist = this.problemonlist(taskId);
        		   List<ppm_output> outputlist = this.getOutputlist(taskId);
                   request.setAttribute("workdetail", workdetail);
        		   request.setAttribute("problemlist", problemlist);
        		   request.setAttribute("outputlist", outputlist);
        		   return mapping.findForward("queryworklog");
        	   } catch (Exception e) {
        		   e.printStackTrace();
        		   return mapping.findForward("errors");
        	   } 
	
	}
	//获得遗留问题
	private List<ppm_problemon> problemonlist(String taskId)throws Exception{
            ppm_problemon[] problem = prodom.queryForWorklog(Integer.parseInt(taskId));
        	List<ppm_problemon> list = new ArrayList<ppm_problemon>();
		if(null!=problem){
		   for(ppm_problemon ppmproblem:problem){
				ppm_probassist[] probassist= prodom.getProbassist(ppmproblem.getProblemID());
				ppm_member ppmmember =mdom.queryByID(ppmproblem.getCharger());
				if(null!=probassist){
					String[] useroper = new String[probassist.length];
					String[] useroperid = new String[probassist.length];
					for(int j=0;j<probassist.length;j++){
						ppm_member member = mdom.queryByID(probassist[j].getMemberID());
						useroper[j]=member.getUserName();
						useroperid[j]=Integer.toString(member.getMemberID());
					}
			    	if(ppmmember!=null){
			    		ppmproblem.setChargerName(ppmmember.getUserName());
			    		
			    	}
			    	ppmproblem.setUseroper(useroper);
			    	ppmproblem.setUseroperid(useroperid);
				}
				list.add(ppmproblem);
			}
		}
		return list;
	}
	//获得产出物
	public List<ppm_output> getOutputlist(String taskId){
		List<ppm_output> list = new ArrayList<ppm_output>();
		 if(null!=taskId&&!"0".equals(taskId)){
			 try {
				ppm_output[] output = odom.queryForWorklog(Integer.parseInt(taskId));
				for(ppm_output ppmoutput:output){
					list.add(ppmoutput);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} 
		 }
		
		return list;
	}
}
