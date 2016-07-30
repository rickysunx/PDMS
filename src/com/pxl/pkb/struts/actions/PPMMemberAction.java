package com.pxl.pkb.struts.actions;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.pxl.pkb.framework.PkbAction;
import com.pxl.pkb.struts.forms.PPMMemberForm;
import com.pxl.pkb.vo.ppm_member;
import com.pxl.pkb.vo.ppm_project;
import com.pxl.pkb.vo.ppm_task;
import com.pxl.pkb.vo.ppm_taskassist;
import com.pxl.ppm.framework.BeanFactory;
import com.pxl.ppm.impls.TaskImpl;
import com.pxl.ppm.itfs.IMember;
import com.pxl.ppm.itfs.IProject;
import com.pxl.ppm.itfs.ITask;

public class PPMMemberAction extends PkbAction {
    
	IMember dom=null;
	IProject pdom = null;
	ITask tdom=null;
	public PPMMemberAction(){
		try {
			if(null==dom){
				dom = (IMember) BeanFactory.getBean("Member");
			}
			if(pdom==null){
			    pdom = (IProject)BeanFactory.getBean("Project");
			}
			if(tdom==null){
				tdom = (ITask)BeanFactory.getBean("Task");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public ActionForward pkbExecute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		   PPMMemberForm memberform =(PPMMemberForm) form;
		   String method =memberform.getMethod();
		   if(null!=method&&!"".equals(method)){
			   if(method.equals("add")){
				   return addExecute(mapping, memberform, request, response);
				}else if(method.equals("select")){
					return selExecute(mapping,memberform,request,response);
				}else if(method.equals("update")){
					return updaExecute(mapping,memberform,request,response);
				}else if(method.equals("delete")){
					return delExcute(mapping,memberform,request,response);
				}else if(method.equals("modify")){
					return modExcute(mapping,memberform,request,response);
				}else if(method.equals("list")){
					return listExcute(mapping,memberform,request,response);
				}else if(method.equals("choseUser")){
					return choseExcute(mapping,memberform,request,response);
				}else{
					return null;
				}
		   }
		return null;
	}

	//列表
	private ActionForward listExcute(ActionMapping mapping, PPMMemberForm memberform, HttpServletRequest request, HttpServletResponse response) {
		String projectId = request.getParameter("projectId").trim();
		if(null!=projectId&&!"".equals(projectId)){
			try {
				request.setAttribute("projectId", projectId);
				ppm_project project = pdom.queryByID(Integer.parseInt(projectId));
				ppm_member[] ppmmember = dom.queryMemberByProject(Integer.parseInt(projectId));
				request.setAttribute("project",project);
				if(ppmmember.length!=0){
					request.setAttribute("ppmmembers", ppmmember);
				    return mapping.findForward("list");
				}else{
					return mapping.findForward("list");
				}
			} catch (Exception e) {
				System.out.println("项目成员列表异常");
				e.printStackTrace();
				return mapping.findForward("errors");
			} 
		}else{
			return mapping.findForward("errors");
		}
		
	}


   //编辑时详细信息
	private ActionForward modExcute(ActionMapping mapping, PPMMemberForm memberform, HttpServletRequest request, HttpServletResponse response) {
		String memberID =request.getParameter("memberId").trim();
		if(null==memberID||"".equals(memberID)){
			return mapping.findForward("errors");
		}
		int memberId = Integer.parseInt(memberID);
		if(0!=memberId){
			try {
				ppm_member ppmmember = dom.queryByID(memberId);
				if(null!=ppmmember){
					request.setAttribute("ppm_member", ppmmember);
					return mapping.findForward("modify");
				}else{
					return mapping.findForward("errors");
				}
			} catch (Exception e) {
				System.out.println("编辑详细信息时发生异常");
				e.printStackTrace();
				return mapping.findForward("errors");
			}
		}else{
			return mapping.findForward("errros");
		}
	}


   //删除信息
	private ActionForward delExcute(ActionMapping mapping, PPMMemberForm memberform, HttpServletRequest request, HttpServletResponse response) {
		String memberId =request.getParameter("memberId").trim();
		if(null!=memberId&&!"".equals(memberId)){
			int memberID =Integer.parseInt(memberId);
			if(0!=memberID){
				try {
					response.setContentType("text/html;charset=utf-8");
				   PrintWriter out = response.getWriter();   
				  String sql ="Charger="+memberID+" or PxlCharger="+memberID;
				  String sqltaskass ="MemberID="+memberID;
				  ppm_task[] task =tdom.queryBywhere(sql);
				  if(task!=null&&task.length!=0){
					  out.print("<script type=\"text/javaScript\"> alert(\"此项目成员已经在任务中，无法删除\");history.go(-1);</script>");
					  return null;
				
					
				  }else if(task==null){
					  ppm_taskassist[] taskass = tdom.queryByWhere(sqltaskass);
					  if(null!=taskass){
						  for(ppm_taskassist assist:taskass){
							  if(assist.getTaskID()!=0&&null!=assist){
								  out.print("<script type=\"text/javaScript\"> alert(\"此项目成员已经在任务中，无法删除\"); history.go(-1); </script>");
								  return null;
							  }
						  }
					  }
				  }

					ppm_member ppmmember =dom.queryByID(memberID);
					dom.delete(memberID);
					if(0!=ppmmember.getProjectID()&&!"".equals(ppmmember.getProjectID())){
						ppm_project project = pdom.queryByID(ppmmember.getProjectID());
                    	request.setAttribute("project",project);
            			request.setAttribute("projectId", Integer.toString(ppmmember.getProjectID()));
            			try {
            				ppm_member[] ppmmembers = dom.queryMemberByProject(ppmmember.getProjectID());
            				if(ppmmembers.length!=0){
            					request.setAttribute("ppmmembers", ppmmembers);
            				    return mapping.findForward("list");
            				}else{
            					return mapping.findForward("list");
            				}
            			} catch (Exception e) {
            				System.out.println("项目成员列表异常");
            				e.printStackTrace();
            				return mapping.findForward("errors");
            			} 
            		}else{
            			return mapping.findForward("errors");
            		}
				} catch (Exception e) {
				     System.out.print("删除项目成员异常");
					e.printStackTrace();
					return mapping.findForward("errors");
				}
			}else{
				return mapping.findForward("errors");
			}
			
		}else{
			return mapping.findForward("errors");
		}
		
		
	}


   //修改保存的信息
	private ActionForward updaExecute(ActionMapping mapping, PPMMemberForm memberform, HttpServletRequest request, HttpServletResponse response) {
		ActionErrors errors =memberform.validate(mapping, request);
	    if(0!=errors.size()){
	    	System.out.println(new Exception("输入有空值"));
	    	System.out.println("异常："+errors);
	       return mapping.findForward("errors");
	    }else{
	    	   String memberId = request.getParameter("memberId").trim();
	    	   if(null!=memberId&&!"".equals(memberId)){
	    		   try {
	    			     int memberID = Integer.parseInt(memberId);
		    			if(0!=memberID){
		    				ppm_member ppmmember =dom.queryByID(memberID);
		    				ppmmember.setUserName(memberform.getUserName());
		    				ppmmember.setUserID(memberform.getUserID());
		    				ppmmember.setUnit(memberform.getUnit());
		    				ppmmember.setPosition(memberform.getPosition());
		    				ppmmember.setCustRole(memberform.getCustRole());
		    				ppmmember.setUfidaRole(memberform.getUfidaRole());
		    				ppmmember.setTel(memberform.getTel());
		    				ppmmember.setEMail(memberform.getEmail());
		    				ppmmember.setNotes(memberform.getNotes());
							dom.update(ppmmember);
							if(0!=ppmmember.getProjectID()&&!"".equals(ppmmember.getProjectID())){
								ppm_project project = pdom.queryByID(ppmmember.getProjectID());
		                    	request.setAttribute("project",project);
	                			request.setAttribute("projectId", Integer.toString(ppmmember.getProjectID()));
	                			try {
	                				ppm_member[] ppmmembers = dom.queryMemberByProject(ppmmember.getProjectID());
	                				if(ppmmembers.length!=0){
	                					request.setAttribute("ppmmembers", ppmmembers);
	                				    return mapping.findForward("list");
	                				}else{
	                					return mapping.findForward("list");
	                				}
	                			} catch (Exception e) {
	                				System.out.println("项目成员列表异常");
	                				e.printStackTrace();
	                				return mapping.findForward("errors");
	                			} 
	                		}else{
	                			return mapping.findForward("errors");
	                		}
		    			}else{
		    				return mapping.findForward("errors");
		    			}
		    			
					} catch (Exception e) {
						System.out.println("项目成员添加异常");
						e.printStackTrace();
						return mapping.findForward("errors");
					}
	    	   }else{
	    		   return mapping.findForward("errors");
	    	   }
	    }
		
	}


	//添加
	private ActionForward addExecute(ActionMapping mapping, PPMMemberForm memberform, HttpServletRequest request, HttpServletResponse response) {
	    ActionErrors errors =memberform.validate(mapping, request);
	    if(0!=errors.size()){
	    	System.out.println(new Exception("输入有空值"));
	    	System.out.println("异常："+errors);
	       return mapping.findForward("errors");
	    }else{
	    		try {
	    			ppm_member ppmmember = new ppm_member();
					ppmmember.setUserID(memberform.getUserID());
					ppmmember.setProjectID(memberform.getProjectID());
					ppmmember.setUserName(memberform.getUserName());
					ppmmember.setUnit(memberform.getUnit());
					ppmmember.setPosition(memberform.getPosition());
					ppmmember.setCustRole(memberform.getCustRole());
					ppmmember.setUfidaRole(memberform.getUfidaRole());
					ppmmember.setTel(memberform.getTel());
					ppmmember.setEMail(memberform.getEmail());
					ppmmember.setNotes(memberform.getNotes());
					int id =dom.insert(ppmmember);
                    if(id!=0){
                    	ppm_project project = pdom.queryByID(memberform.getProjectID());
                    	request.setAttribute("project",project);
                    	if(0!=memberform.getProjectID()&&!"".equals(memberform.getProjectID())){
                			request.setAttribute("projectId", Integer.toString(memberform.getProjectID()));
                			
                			try {
                				ppm_member[] ppmmembers = dom.queryMemberByProject(memberform.getProjectID());
                				if(ppmmembers.length!=0){
                					request.setAttribute("ppmmembers", ppmmembers);
                				    return mapping.findForward("list");
                				}else{
                					return mapping.findForward("list");
                				}
                			} catch (Exception e) {
                				System.out.println("项目成员列表异常");
                				e.printStackTrace();
                				return mapping.findForward("errors");
                			} 
                		}else{
                			return mapping.findForward("errors");
                		}
                    	
                    }else{
              			return mapping.findForward("errors");
                    }

				} catch (Exception e) {
					System.out.println("项目成员添加异常");
					e.printStackTrace();
					 return mapping.findForward("errors");
				}
			   
	    }
		
	}

	//显示详细信息
	private ActionForward selExecute(ActionMapping mapping, PPMMemberForm memberform, HttpServletRequest request, HttpServletResponse response) {
		String memberID =request.getParameter("memberId");
        if(null==memberID||"".equals(memberID)){
        	return mapping.findForward("errors");
        }else{
        	int memberId =Integer.parseInt(memberID);
       		 if(0!=memberId){
       	        try {
       				ppm_member ppmmember =dom.queryByID(memberId);
       				ppm_project ppmproject =pdom.queryByID(ppmmember.getProjectID());
       				if(null!=ppmproject&&!"".equals(ppmproject)){
       				 request.setAttribute("ppmprojects", ppmproject);
       				}
       				if(null!=ppmmember){
       				   request.setAttribute("ppm_member", ppmmember);
       				   return mapping.findForward("select");
       				}else{
       			       return mapping.findForward("errors");
       				}
       			} catch (Exception e) {
       				System.out.println("显示详细项目成员异常");
       				e.printStackTrace();
       				return mapping.findForward("errors");
       			}
       		 }else{
       			 return mapping.findForward("errors");
       		 }
        }		
		
	  
	}
	//查找用户
    private ActionForward choseExcute(ActionMapping mapping, PPMMemberForm memberform, HttpServletRequest request, HttpServletResponse response) {  	
 		  try {
 			 String tag = request.getParameter("tag").trim();
 			 if(tag!=null&&!tag.equals("")){
 				ppm_member[] ppmmembers= dom.queryPxlUser();
 				request.setAttribute("ppm_members", ppmmembers);
 				if(tag.equals("member")){
 					return mapping.findForward("chose");
 				 }else if(tag.equals("worklogs")){
 					return mapping.findForward("worklogchose");
 				 }else{
 					return mapping.findForward("errors");
 				 }
 			 }else{
 				return mapping.findForward("errors");
 			 }
 		  } catch (Exception e) {
			System.out.println("查找用户失败");
			e.printStackTrace();
			return mapping.findForward("errors");
 		  }

	}
}
