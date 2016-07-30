package com.pxl.pkb.struts.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.pxl.pkb.framework.DataManagerObject;
import com.pxl.pkb.framework.PkbAction;

public class UserDeleteAction extends PkbAction {

	public ActionForward pkbExecute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		String strUserID = request.getParameter("userid");
		int userid = Integer.parseInt(strUserID);
		
		DataManagerObject dmo = new DataManagerObject();
		dmo.updateBySQL("delete from bd_user where userid="+userid);
		
		return mapping.findForward("success");
	}

}
