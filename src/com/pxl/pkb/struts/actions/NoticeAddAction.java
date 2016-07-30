package com.pxl.pkb.struts.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.pxl.pkb.biz.Consts;
import com.pxl.pkb.biz.Pub;
import com.pxl.pkb.framework.DataManagerObject;
import com.pxl.pkb.framework.PkbAction;
import com.pxl.pkb.struts.forms.NoticeForm;
import com.pxl.pkb.vo.bd_user;
import com.pxl.pkb.vo.nt_notice;

public class NoticeAddAction extends PkbAction {

	public ActionForward pkbExecute(ActionMapping mapping, ActionForm actionForm, 
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		NoticeForm noticeAddForm=(NoticeForm)actionForm;
		bd_user user=(bd_user)request.getSession().getAttribute(Consts.PKB_USER_SESSION_NAME);
		String currTime=Pub.getCurrTime();
		
		nt_notice notice=new nt_notice();
		notice.setNoticeTitle(noticeAddForm.getNoticeTitle());
		notice.setNoticeContent(noticeAddForm.getNoticeContent());
		notice.setNoticeType(noticeAddForm.getNoticeType());
		notice.setNoticeCate(noticeAddForm.getNoticeCate());
		notice.setAddUser(user.getUserID());
		notice.setAddTime(currTime);
		
		DataManagerObject dmo=new DataManagerObject();
		dmo.insert(notice);
		
		return mapping.findForward("success");
	}

	
}
