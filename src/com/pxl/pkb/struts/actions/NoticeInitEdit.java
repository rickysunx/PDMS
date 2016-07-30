package com.pxl.pkb.struts.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.pxl.pkb.framework.DataManagerObject;
import com.pxl.pkb.framework.PkbAction;
import com.pxl.pkb.framework.ValueObject;
import com.pxl.pkb.vo.nt_notice;

public class NoticeInitEdit extends PkbAction {

	public ActionForward pkbExecute(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String strNoticeID=request.getParameter("noticeID");
		int noticeID=Integer.parseInt(strNoticeID);
		
		DataManagerObject dmo=new DataManagerObject();
		ValueObject[] vo=dmo.queryByWhere(nt_notice.class, "NoticeID="+noticeID);
		
		nt_notice oldNotice=(nt_notice)vo[0];
		request.setAttribute("notice", oldNotice);
		
		return mapping.findForward("success");
	}

	
}
