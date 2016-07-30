package com.pxl.pkb.struts.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.pxl.pkb.framework.DataManagerObject;
import com.pxl.pkb.framework.PkbAction;

public class NoticeDeteleAction extends PkbAction {

	public ActionForward pkbExecute(ActionMapping mapping, ActionForm actionForm, 
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String strNoticeID=request.getParameter("noticeID");
		int noticeID=Integer.parseInt(strNoticeID);
		
		DataManagerObject dmo=new DataManagerObject();
		dmo.updateBySQL("delete from nt_notice where NoticeID="+noticeID);
		
		return mapping.findForward("success");
	}
}
