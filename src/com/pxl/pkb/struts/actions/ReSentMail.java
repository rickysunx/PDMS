package com.pxl.pkb.struts.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.pxl.pkb.biz.Mail;
import com.pxl.pkb.framework.PkbAction;

public class ReSentMail extends PkbAction {

	@Override
	public ActionForward pkbExecute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String mailUserID=request.getParameter("mailUserID");
		Mail.sendMail(Integer.parseInt(mailUserID));
		return mapping.findForward("success");
	}
}
