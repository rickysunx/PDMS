package com.pxl.pkb.struts.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.pxl.pkb.biz.Consts;
import com.pxl.pkb.biz.NotifyManager;
import com.pxl.pkb.biz.Pub;
import com.pxl.pkb.framework.DataManagerObject;
import com.pxl.pkb.framework.PkbAction;
import com.pxl.pkb.framework.ValueObject;
import com.pxl.pkb.struts.forms.QstAddForm;
import com.pxl.pkb.vo.ask_cate;
import com.pxl.pkb.vo.ask_qst;
import com.pxl.pkb.vo.ask_qstcate;
import com.pxl.pkb.vo.bd_user;

public class QstAddAction extends PkbAction {

	public QstAddAction() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pxl.pkb.framework.PkbAction#pkbExecute(org.apache.struts.action.ActionMapping,
	 *      org.apache.struts.action.ActionForm,
	 *      javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public ActionForward pkbExecute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		QstAddForm myForm = (QstAddForm) form;
		String strAttachCount = request.getParameter("attachCount");
		int attachCount=0;
		if(strAttachCount!=null&&!"".equals(strAttachCount)){
			 attachCount= Integer.parseInt(strAttachCount);
		}
		
		HttpSession session = request.getSession();
		DataManagerObject dmo = new DataManagerObject();
		if (Pub.isUnCheckUser(session)) {
			throw new Exception("您的用户尚未通过审核，不能进行回答！");
		}
		String strQstCateID = myForm.getAskCate();
		String[] strQstCateIDs = strQstCateID.split("&");
		ValueObject[] ask_catevos = dmo.queryByWhere(ask_cate.class,"AskCateID="+strQstCateIDs[0]);
		if (ask_catevos.length == 1) {
			ask_cate ask_cate = (ask_cate) ask_catevos[0];
			if (ask_cate.getCanPost().equals("0")) {
				throw new Exception("该目录不允许发表文档");
			} else {
				ask_qst qst = new ask_qst();
				qst.setAskCateID(Integer.parseInt(strQstCateIDs[0]));
				qst.setQstTitle(myForm.getQstTitle());
				qst.setQstContent(myForm.getQstContent());
				qst.setAddIP(request.getRemoteHost());
				String currTime = Pub.getCurrTime();
				qst.setAddTime(currTime);
				qst.setUpdateTime(currTime);
				bd_user user = (bd_user) session.getAttribute(Consts.PKB_USER_SESSION_NAME);
				qst.setAddUser(user.getUserID());
				qst.setReplyCount(0);
				qst.setBestAnswerID(0);
				qst.setQstStatus("0");
				qst.setQstKeyword(myForm.getQstKeyword());
				qst.setQstAddition("");
				int qstID = dmo.insert(qst);

				// 设置其他类别
				if (strQstCateIDs.length>1) {
					for (int i = 1; i < strQstCateIDs.length; i++) {
						int otherCateID = Integer.parseInt(strQstCateIDs[i]);
						if (otherCateID > 0) {
							ask_qstcate qstcate = new ask_qstcate();
							qstcate.setCateID(Integer.parseInt(strQstCateIDs[i]));
							qstcate.setQstID(qstID);
							dmo.insert(qstcate);
						}
					}
				}

				// 设置上传附件
				if (attachCount > 0) {
					String attachUpdateSQL = "update sys_attach set ObjID="+ qstID + " where AttachID in (";
					for (int i = 0; i < attachCount; i++) {
						attachUpdateSQL += request.getParameter("attach"+ i);
						if (i < attachCount - 1) {
							attachUpdateSQL += ",";
						}
					}
					attachUpdateSQL += ")";
					dmo.updateBySQL(attachUpdateSQL);
				}
				
				NotifyManager.getInstance().addNotifyMsg(
						"["+user.getUserName()+"]提出问题："+qst.getQstTitle(), 
						"ask_qst_view.jsp?qstid="+qst.getQstID(), currTime);
				
				response.sendRedirect("ask_list.jsp?askcateid=" + qst.getAskCateID());
			}
		} else {
			throw new Exception("系统异常");
		}
		return null;

	}

}
