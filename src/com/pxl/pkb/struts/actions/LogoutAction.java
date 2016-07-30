/**
 * 
 */
package com.pxl.pkb.struts.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.pxl.pkb.biz.Consts;
import com.pxl.pkb.framework.PkbAction;

/**
 * @author Ricky
 *
 */
public class LogoutAction extends PkbAction {

	/**
	 * 
	 */
	public LogoutAction() {
	}

	/* (non-Javadoc)
	 * @see com.pxl.pkb.framework.PkbAction#pkbExecute(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public ActionForward pkbExecute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		session.setAttribute(Consts.PKB_USER_SESSION_NAME, null);
		return mapping.findForward("success");
	}

}
