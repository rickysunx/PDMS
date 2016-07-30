package com.pxl.pkb.struts.actions;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.pxl.pkb.biz.Consts;
import com.pxl.pkb.biz.Pub;
import com.pxl.pkb.extendvo.ppm_problemon;
import com.pxl.pkb.framework.DataManagerObject;
import com.pxl.pkb.framework.PkbAction;
import com.pxl.pkb.vo.bd_mail;
import com.pxl.pkb.vo.bd_mailuser;
import com.pxl.pkb.vo.bd_user;
import com.pxl.pkb.vo.ppm_member;
import com.pxl.pkb.vo.ppm_output;
import com.pxl.pkb.vo.ppm_probassist;
import com.pxl.pkb.vo.ppm_problem;
import com.pxl.pkb.vo.ppm_project;
import com.pxl.pkb.vo.ppm_task;
import com.pxl.pkb.vo.ppm_taskassist;
import com.pxl.pkb.vo.ppm_workdetail;
import com.pxl.pkb.vo.sys_attach;
import com.pxl.ppm.framework.BeanFactory;
import com.pxl.ppm.itfs.IMember;
import com.pxl.ppm.itfs.IOutput;
import com.pxl.ppm.itfs.IProblem;
import com.pxl.ppm.itfs.IProject;
import com.pxl.ppm.itfs.ITask;
import com.pxl.ppm.itfs.IWorkDetail;

public class AJAXAction extends PkbAction {
	IProject dom=null;
	IWorkDetail wdom = null;
	IOutput odom =null;
	IProblem pdom=null;
	IMember mdom=null;
	ITask tdom = null;
	public AJAXAction(){
		try {
			if(null==dom){
				dom = (IProject) BeanFactory.getBean("Project");
			}
			if(null==wdom){
				wdom =(IWorkDetail) BeanFactory.getBean("WorkDetail");
			}
			if(null==odom){
				odom = (IOutput) BeanFactory.getBean("Output");
			}
			if(null==pdom){
				pdom = (IProblem) BeanFactory.getBean("Problem");
			}
			if(null==mdom){
				mdom=(IMember) BeanFactory.getBean("Member");
			}
			if(null==tdom){
				tdom =(ITask)BeanFactory.getBean("Task");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public ActionForward pkbExecute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String method = request.getParameter("method").trim();
		if(null!=method&&!"".equals(method)){
			if(method.equals("checkonly")){
				return this.checkonlyExecute(mapping, form, request, response);
			}else if(method.equals("statu")){
				return this.changeExecute(mapping, form, request, response);
			}else if(method.equals("detelWorkdetatil")){
				return this.detelworkExecute(mapping,form,request,response);
			}else if(method.equals("modifyStatu")){
				return this.modifyStatus(mapping,form,request,response);
			}else if(method.equals("detelProblem")){
				return this.deteproblemExecute(mapping,form,request,response);
			}else if(method.equals("deleoutput")){
				return this.deleteoutputExecute(mapping,form,request,response);
			}else if(method.equals("savepask")){
				return this.savepaskExecute(mapping,form,request,response);
			}else if(method.equals("checkMember")){
				return this.checkMemberExecute(mapping,form,request,response);
			}else{
				return null;
			}
		}else{
			return mapping.findForward("errors");
		}
	}
	public ActionForward checkonlyExecute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter writer = response.getWriter();
		String projectCode = request.getParameter("projectCode").trim();
		if(null!=projectCode&&!"".equals(projectCode)){
			String sql ="ProjectCode='"+projectCode+"'";
			ppm_project[] ppmproject = dom.queryByWhere(sql);
		    if(ppmproject.length!=0){
		    	writer.println("1");
		    }else{
		    	writer.println("0");
		    }
		}
		return null;
	}
	
	public ActionForward changeExecute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		  PrintWriter printwriter = response.getWriter();
		  String projectId = request.getParameter("projectId").trim();
		   DataManagerObject dmo = new DataManagerObject();
		  if(null!=projectId&&!"".equals(projectId)){
                 dom.publish(Integer.parseInt(projectId));
                 bd_user user = (bd_user)request.getSession().getAttribute(Consts.PKB_USER_SESSION_NAME);
                 ppm_project project =dom.queryByID(Integer.parseInt(projectId));
                 String date=Pub.getCurrTime();
 	    		String subject="知识库系统提示："+project.getProjectName()+" 项目发布通知";
 	    		String message="尊敬的\""+project.getProjectName()+"\"项目成员：<br/>&nbsp;&nbsp;您好！项目\""+project.getProjectName()+"\"于"+date+"在知识库系统中被\""+user.getUserName()+"\"用户重新进行了发布，请您登陆知识库系统注意查看具体信息。<br>知识库系统：<a target='_blank' href='http://www.pushingline.com'>http://www.pushingline.com</a>";
 	    		bd_mail mail=new bd_mail();
 	    		mail.setMailContext(message);
 	    		mail.setMailSubject(subject);
 	    		int mailId=dmo.insert(mail);
 				ppm_member[] members = mdom.queryMemberByProject(Integer.parseInt(projectId));
 				if(members!=null&&members.length!=0){
 					for(ppm_member member:members){
 						bd_mailuser mailuser=new bd_mailuser();
 						mailuser.setFailedCount(0);
 						mailuser.setIsSuccess("0");
 						mailuser.setMailID(mailId);
 						mailuser.setUserID(member.getUserID());
 						dmo.insert(mailuser);
 					}
 				}
				 printwriter.print("1,"+projectId+"");
		  }
				return null;	     
	}

	
	public List getWrokdetailByTaskId(String[] strs)throws Exception {
		List<ppm_workdetail> list = new ArrayList<ppm_workdetail>();
		if(null!=strs&&strs.length!=0){
			String taskId = strs[0];
			String userId = strs[1];
			if(null!=taskId&&!"0".equals(taskId)&&null!=userId&&!"".equals(userId)){
		        ppm_workdetail[] workDetail = wdom.queryForWorklog(Integer.parseInt(taskId),Integer.parseInt(userId));
		        for(ppm_workdetail work:workDetail){
		        	list.add(work);
		        }
			}
		}
		return list;
	}
	
	//删除
	private ActionForward detelworkExecute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
	    
		try {
			PrintWriter writer = response.getWriter();
			String workdetailID = request.getParameter("workdetailID");
		    String obj = request.getParameter("obj");
		    if(null!=workdetailID&&!"".equals(workdetailID)&&null!=obj&&!"".equals(obj)){
		    	int workdetailId = Integer.parseInt(workdetailID);
		    	ppm_workdetail workdetail = wdom.queryByid(workdetailId);
		    	int taskId = workdetail.getTaskID();
		        wdom.delete(workdetailId);
		        String str =obj+","+Integer.toString(taskId);
		        if(str!=null&&!str.equals("")){
		        	 writer.print(str);
		        }
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	//保存详细工作内容

	public String[] saveWorkdetatil(String[][] args){
		try {
	         if(null!=args&&!"".equals(args)&&args.length!=0){
	        	 String[] seqnumlist = new String[args[0].length];;
	        	 String[] detailContentlist = new String[args[1].length];;
	        	 String[]  workAddresslist = new String[args[2].length];;
	        	 String[] seqnumadd = new String[args[3].length];
	             String[] detailContentadd = new String[args[4].length];
	        	 String[] workAddressadd = new String[args[5].length];
	        	 String[] city = new String[args[8].length];
	        	 String[] workdetailIdlist =new String[args[7].length];
	        	 String userId = null;
	        	 String taskId = null;
	        	 String number=null;
	        	 String[] data=null;
	        	if(args[0].length!=0){
	        	  for(int i=0;i<seqnumlist.length;i++){
	        		  seqnumlist[i]=args[0][i];
	        		  detailContentlist[i]=args[1][i];
	        		  if(!args[2][i].equals(args[9][i]+args[10][i])&&!args[10][i].equals("")&&null!=args[10][i]){
	        			  workAddresslist[i]=args[9][i]+args[10][i];
	        		  }else{
	        			  workAddresslist[i]=args[2][i];
	        		  }
	        		  workdetailIdlist[i]=args[7][i];
	        	  }
	        	}
	        	if(args[4].length!=0){
	           	  for(int i=0;i<detailContentadd.length;i++){
	           		seqnumadd[i]=args[3][i];
	           		detailContentadd[i]=args[4][i];
	           		workAddressadd[i]=args[5][i];
	           		city[i]=args[8][i];
	           	  }
	        	}
	        	if(null!=args[6][0]&&!"".equals(args[6][0])&&args[6][0].length()!=0){
	        		taskId = args[6][0];
	        		
	        	}
	        	if(null!=args[6][1]&&!"".equals(args[6][1])&&args[6][1].length()!=0){
	        		userId = args[6][1];
	        		
	        	}
	        	if(null!=args[6][2]&&!"".equals(args[6][2])&&args[6][2].length()!=0){
	        		number = args[6][2];
	        		
	        	}
	        	if(null!=workdetailIdlist&&workdetailIdlist.length!=0){
					for(int i=0;i<workdetailIdlist.length;i++){
						if(null!=workdetailIdlist[i].trim()&&!"".equals(workdetailIdlist[i].trim())){
							int workdetailId = Integer.parseInt(workdetailIdlist[i]);
							ppm_workdetail workdetail = wdom.queryByid(workdetailId);
						    workdetail.setSeqNum(Integer.parseInt(seqnumlist[i].trim()));
						    workdetail.setDetailContent(detailContentlist[i].trim());
						    workdetail.setWorkAddress(workAddresslist[i].trim());
						    wdom.update(workdetail);
						}
					}
				}
				if(null!=detailContentadd&&detailContentadd.length!=0){
					for(int i=0;i<detailContentadd.length;i++){
						if(null!=detailContentadd[i].trim()&&!"".equals(detailContentadd[i].trim())){
							ppm_workdetail workdetail = new ppm_workdetail();
							workdetail.setSeqNum(Integer.parseInt(seqnumadd[i].trim()));
							workdetail.setDetailContent(detailContentadd[i].trim());
							workdetail.setWorkAddress(workAddressadd[i].trim()+city[i].trim());
							if(null!=taskId&&!"".equals(taskId)){
								workdetail.setTaskID(Integer.parseInt(taskId));
							}
							if(null!=userId&&!"".equals(userId)){
								workdetail.setUserID(Integer.parseInt(userId));
							}
							int workdetailId = wdom.insert(workdetail);
							
						}
					}
				}
				data = new String[]{number,taskId}; 
				if(null!=data&&!"".equals(data)&&data.length!=0){
		        	 return data;
		        	 
		         }else{
		        	 return null;
		         }
	         }else{
	        	 return null;
	         }
	         
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	} 
	

	private ActionForward modifyStatus(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException {
	    String projectId = request.getParameter("projectId").trim();
	    PrintWriter writer =response.getWriter();
	    DataManagerObject dmo = new DataManagerObject();
	    if(null!=projectId&&!"".equals(projectId)){
	    	try {
	    		bd_user user = (bd_user)request.getSession().getAttribute(Consts.PKB_USER_SESSION_NAME);
				dom.revise(Integer.parseInt(projectId), null,null);
				ppm_project project =dom.queryByID(Integer.parseInt(projectId));
				String date=Pub.getCurrTime();
	    		String subject="知识库系统提示："+project.getProjectName()+" 项目修订通知";
	    		String message="尊敬的\""+project.getProjectName()+"\"项目成员：<br/>&nbsp;&nbsp;您好！项目\""+project.getProjectName()+"\"于"+date+"在知识库系统中被\""+user.getUserName()+"\"用户重新进行了修订，请您登陆知识库系统注意查看具体信息。<br>知识库系统：<a target='_blank' href='http://www.pushingline.com'>http://www.pushingline.com</a>";
	    		bd_mail mail=new bd_mail();
	    		mail.setMailContext(message);
	    		mail.setMailSubject(subject);
	    		int mailId=dmo.insert(mail);
				ppm_member[] members = mdom.queryMemberByProject(Integer.parseInt(projectId));
				if(members!=null&&members.length!=0){
					for(ppm_member member:members){
						bd_mailuser mailuser=new bd_mailuser();
						mailuser.setFailedCount(0);
						mailuser.setIsSuccess("0");
						mailuser.setMailID(mailId);
						 if(member.getUserID()!=0){
							 mailuser.setUserID(member.getUserID());
							 dmo.insert(mailuser);
						 }
					}
				}
	    
				writer.print("0,"+projectId+"");
	    	} catch (Exception e) {
				e.printStackTrace();
				writer.print("2");
			} 
	    }
		return null;
	}
	
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
	public List<ppm_problemon> getProblemlist(String taskId){
		List<ppm_problemon> list = new ArrayList<ppm_problemon>();
		 if(null!=taskId&&!"0".equals(taskId)){
			 try {
				ppm_problemon[] problem = pdom.queryForWorklog(Integer.parseInt(taskId));
				if(null!=problem){
					for(ppm_problemon ppmproblem:problem){
						ppm_probassist[] probassist= pdom.getProbassist(ppmproblem.getProblemID());
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
				
			} catch (Exception e) {
				e.printStackTrace();
			} 
		 }
		
		return list;
	}
	
	public String[] savaProblem(String[][] args){
		if(null!=args&&!"".equals(args)){
			 String[] problemCodeadd = new String[args[0].length];;
        	 String[] descriptionadd = new String[args[1].length];;
        	 String[]  planaddadd = new String[args[2].length];;
        	 String[] memberIdadd = new String[args[3].length];
             String[] useroperadd = new String[args[4].length];
        	 String[] planTimeadd = new String[args[5].length];
        	 String[] statu =new String[args[6].length];
        	 String[] problemIDlist = new String[args[8].length];
        	 String[] problemCodelist = new String[args[9].length];
        	 String[] descriptionlist = new String[args[10].length];
        	 String[] planlist = new String[args[11].length];
        	 String[] memberIdlist = new String[args[12].length];
        	 String[] useroperlist = new String[args[13].length];
        	 String[] planTimelist = new String[args[14].length];
        	 String[] problemsta = new String[args[16].length];
        	 String taskId = null;
        	 String number=null;
        	 String[] data=null;
        	 if(args[1]!=null&&args[1].length!=0){
	        	  for(int i=0;i<descriptionadd.length;i++){
	        		  problemCodeadd[i]=args[0][i];
	        		  descriptionadd[i]=args[1][i];
	        		  planaddadd[i]=args[2][i];
	        		  memberIdadd[i]=args[3][i];
	        		  useroperadd[i]=args[4][i];
	        		  planTimeadd[i]=args[5][i];
	        		  statu[i]=args[6][i];

	        	  }
	        }
        	 if(args[8]!=null&&args[8].length!=0){
        		 for(int i=0;i<problemIDlist.length;i++){
        			 problemIDlist[i] =args[8][i];
        			  problemCodelist[i]=args[9][i];
        			  descriptionlist[i]=args[10][i];
        			  planlist[i]=args[11][i];
        			  memberIdlist[i]=args[12][i];
        			  useroperlist[i]=args[13][i];
        			  planTimelist[i]=args[14][i];
        			  if(!args[15][i].trim().equals(args[16][i].trim())&&!args[15][i].trim().equals("")){
        				  problemsta[i]=args[15][i];
        			  }else{
        				  problemsta[i]=args[16][i];
        			  }
        		 }
        	 }
        	 if(null!=problemIDlist&&problemIDlist.length!=0){
        		 if(null!=args[7][0]&&!"".equals(args[7][0])){
						taskId = args[7][0];
        		 }
        		 if(null!=args[7][1]&&!"".equals(args[7][1])){
			        	number = args[7][1];
			        }
        		 for(int i=0;i<problemIDlist.length;i++){
        			 if(null!=problemIDlist[i].trim()&&!"".equals(problemIDlist[i].trim())){
        				 try {
							ppm_problem problem = pdom.queryByProblemId(Integer.parseInt(problemIDlist[i]));
							pdom.deteProbassist(Integer.parseInt(problemIDlist[i]));
							String[] str = useroperlist[i].split(",");
							if(str.length!=0&&null!=str){
								for(int j=0;j<str.length;j++){
							    	 ppm_probassist probassist = new ppm_probassist(); 
							    	  probassist.setProblemID(Integer.parseInt(problemIDlist[i]));
									   probassist.setMemberID(Integer.parseInt(str[j]));
									   pdom.insertProbassist(probassist);
								   }
							}
							problem.setProblemCode(problemCodelist[i].trim());
							problem.setDescription(descriptionlist[i].trim());
							problem.setPlan(planlist[i].trim());
							problem.setCharger(Integer.parseInt(memberIdlist[i].trim()));
							problem.setPlanTime(planTimelist[i].trim());
							problem.setProblemStatus(Integer.parseInt(problemsta[i].trim()));
							pdom.update(problem);
							ppm_probassist probass = new ppm_probassist();
							
							pdom.insertProbassist(probass);
						} catch (Exception e) {
							e.printStackTrace();
						}
        				 
        			 }
        		 }
        	 }
        	 if(null!=descriptionadd&&descriptionadd.length!=0){
					for(int i=0;i<descriptionadd.length;i++){
						if(null!=descriptionadd[i].trim()&&!"".equals(descriptionadd[i].trim())){
							ppm_problem problem = new ppm_problem();
							problem.setProblemCode(problemCodeadd[i].trim());
							problem.setDescription(descriptionadd[i].trim());
							problem.setPlan(planaddadd[i].trim());
							problem.setCharger(Integer.parseInt(memberIdadd[i].trim()));
							problem.setPlanTime(planTimeadd[i].trim());
							problem.setProblemStatus(Integer.parseInt(statu[i].trim()));
							if(null!=args[7][0]&&!"".equals(args[7][0])){
								taskId = args[7][0];
								problem.setTaskID(Integer.parseInt(taskId));
								try {
									int problemId = pdom.insert(problem);
									if(problemId!=0){
									String[] str = useroperadd[i].split(",");
									if(str.length!=0&&null!=str){
										for(int j=0;j<str.length;j++){
									    	 ppm_probassist probassist = new ppm_probassist(); 
											   probassist.setProblemID(problemId);
											   probassist.setMemberID(Integer.parseInt(str[j]));
											   pdom.insertProbassist(probassist);
										   }
									}
									}
								} catch (Exception e) {
								   e.printStackTrace();
								}
							}
					        if(null!=args[7][1]&&"".equals(args[7][1])){
					        	number = args[7][1];
					        }
					        
							
						}
					}
					
				}
        	 data = new String[]{number,taskId}; 
				if(null!=data&&!"".equals(data)&&data.length!=0){
		        	 return data;
		        	 
		         }else{
		        	 return null;
		         }
		}
		return null;
	}
	
	private ActionForward deteproblemExecute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

		try {
			PrintWriter writer = response.getWriter();
			String problemID = request.getParameter("problemID");
		    String obj = request.getParameter("obj");
		    if(null!=problemID&&!"".equals(problemID)&&null!=obj&&!"".equals(obj)){
		    	int problemId = Integer.parseInt(problemID);
		    	ppm_problem problem = pdom.queryByProblemId(problemId);
		    	int taskId = problem.getTaskID();
		        pdom.delete(problemId);
		        String str =obj+","+Integer.toString(taskId);
		        if(str!=null&&!str.equals("")){
		        	 writer.print(str);
		        }
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
   public String[] saveOutput(String[][]args){
	   if(null!=args&&!"".equals(args)){
		   String[] seqnumadd = new String[args[0].length];
		   String[] outputadd = new String[args[1].length];
		   String[] outputIdlist = new String[args[2].length];
		   String[] outputNamelist = new String[args[3].length];
		   String[] seqnumslist = new String[args[4].length];
		   String taskId = null;
      	   String number=null;
      	   String[] data=null;
      	   String[] attachCount =new String[args[6].length];
      	   String[] attachCountADD = new String[args[7].length];
      	  DataManagerObject dmo = new DataManagerObject();
	     if(null!=args[2]&&args[2].length!=0){
			   for(int i=0;i<outputIdlist.length;i++){
				   outputIdlist[i]=args[2][i].trim();
				   outputNamelist[i]=args[3][i].trim();
				   seqnumslist[i]=args[4][i].trim();
				   attachCount[i]= args[6][i].trim();
			   }
		   }
		   if(null!=args[1]&&args[1].length!=0){
			   for(int i=0;i<outputadd.length;i++){
				   seqnumadd[i]=args[0][i].trim();
				   outputadd[i]=args[1][i].trim();
				   attachCountADD[i]=args[7][i].trim();
			   }
		   }
		   if(null!=args[5][0]&&args[5][0].length()!=0){
			   taskId = args[5][0].trim();
		   }
		   if(null!=args[5][1]&&args[5][1].length()!=0){
			   number = args[5][1].trim();
		   }
		   for(int i=0;i<outputIdlist.length;i++){
  			 if(null!=outputIdlist[i].trim()&&!"".equals(outputIdlist[i].trim())){
  				 try {
						ppm_output output = odom.queryOutById(Integer.parseInt(outputIdlist[i]));
                        output.setSeqNum(Integer.parseInt(seqnumslist[i]));
                        output.setOutputName(outputNamelist[i]);
                        String attachArray =attachCount[i];
                        if(null!=attachArray&&!"".equals(attachArray)){
                        	String[] attach = attachArray.split(",");
                        	StringBuffer stringbuffer = new StringBuffer();
                        	if(attach!=null&&attach.length>1){
                        		for(int j=1;j<attach.length;j++){
                            		stringbuffer.append(attach[j]);
                            		if(j!=attach.length-1){
                            			stringbuffer.append(",");
                            		}
                            	}
                       String attachUpdateSQL = "update sys_attach set ObjID="+ outputIdlist[i].trim() + " where AttachID in ("+stringbuffer+")";
                       dmo.updateBySQL(attachUpdateSQL);
                    			
                        	}
                           
                        }
                      odom.update(output);
					} catch (Exception e) {
						e.printStackTrace();
					}
  				 
  			 }
  		 }
		 for(int i=0;i<outputadd.length;i++){
			 if(outputadd[i].trim()!=null&&!outputadd[i].trim().equals("")){
				 ppm_output output =new ppm_output();
				 if(null!=seqnumadd[i]){
					 output.setSeqNum(Integer.parseInt(seqnumadd[i]));
					 output.setOutputName(outputadd[i].trim());
					 output.setTaskID(Integer.parseInt(taskId));
					 try {
						int id =odom.insert(output);
						String attachArray =attachCountADD[i];
                        if(null!=attachArray&&!"".equals(attachArray)){
                        	String[] attach = attachArray.split(",");
                        	StringBuffer stringbuffer = new StringBuffer();
                        	if(attach!=null&&attach.length>1){
                        		for(int j=1;j<attach.length;j++){
                            		stringbuffer.append(attach[j]);
                            		if(j!=attach.length-1){
                            			stringbuffer.append(",");
                            		}
                            	}
                       String attachUpdateSQL = "update sys_attach set ObjID="+ id + " where AttachID in ("+stringbuffer+")";
                       dmo.updateBySQL(attachUpdateSQL);
                    			
                        	}
                           
                        }
                      odom.update(output);
					} catch (Exception e) {
						e.printStackTrace();
					}
                }
				
				 
				 
			 }
			 
		 }
    	 data = new String[]{number,taskId}; 
			if(null!=data&&!"".equals(data)&&data.length!=0){
	        	 return data;
	        	 
	         }else{
	        	 return null;
	         }
		   
		   
	   }
	   
	   
	   return null;
   }
   
   
   
	
	private ActionForward deleteoutputExecute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
	   try {
		PrintWriter print = response.getWriter();
		String outputID = request.getParameter("outputID");
		String obj = request.getParameter("obj");
		if(outputID!=null&&!"".equals(outputID)&&!"".equals(obj)&&null!=obj){
			int outputId = Integer.parseInt(outputID);
            ppm_output out = odom.queryOutById(outputId);
            odom.delete(outputId);
            String str =obj+","+Integer.toString(out.getTaskID());
	        if(str!=null&&!str.equals("")){
	        	 print.print(str);
	        }
           
			
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	   
		return null;
	}
	
	private ActionForward savepaskExecute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try {
			PrintWriter out = response.getWriter();
			String taskId = request.getParameter("taskId").trim();
			String finished = request.getParameter("finished").trim();
			String number = request.getParameter("number").trim();
			if(taskId!=null&&!"0".equals(taskId)&&finished!=null&&!"".equals(finished)&&number!=null&&!"".equals(number)){
				ppm_task[] tasks =tdom.queryBywhere("TaskID='"+taskId+"' and c.VerID=(select VerID from ppm_version as p where p.ProjectID=c.ProjectID and p.IsLatest='1')");
				ppm_task task = tasks[0];
				ppm_task taskcopy = new ppm_task();
				BeanUtils.copyProperties(taskcopy, task);
				taskcopy.setFinishedPercent(Integer.parseInt(finished));
				tdom.updateTask(taskcopy.getProjectID(), taskcopy);
				String str =number+","+taskId+","+finished;
				if(null!=str&&!str.equals("")){
					out.print(str);
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private ActionForward checkMemberExecute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try {
			PrintWriter writer =response.getWriter();
		    String memberID =request.getParameter("memberID").trim();
		    if(null!=memberID&&!"".equals(memberID)){
		       String sql ="Charger="+Integer.parseInt(memberID)+" or PxlCharger="+Integer.parseInt(memberID);
		       String sqltaskass ="MemberID="+Integer.parseInt(memberID);
		       ppm_task[] task =tdom.queryBywhere(sql);
		       if(task!=null&&task.length!=0){
		    	   writer.print("1");
		    	   return null;
		       }else{
		    	   ppm_taskassist[] taskass = tdom.queryByWhere(sqltaskass);
			       if(null!=taskass){
			    	   for(ppm_taskassist assist:taskass){
			    		   if(assist.getTaskID()!=0&&null!=assist){
			    			   writer.print("1");
			    			   return null;
			    		   }else{
			    			   writer.print("0");
			    		   }
			    	   }
			       }else{
			    	   writer.print("0");
			       }
		       }
		       

		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
