package com.pxl.pkb.struts.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.pxl.pkb.framework.DataManagerObject;
import com.pxl.pkb.framework.PkbAction;

public class AskCateDeleteAction extends PkbAction {

	public ActionForward pkbExecute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String strAskCateID = request.getParameter("askcateid");
		int askCateID = Integer.parseInt(strAskCateID);
		
		DataManagerObject dmo = new DataManagerObject();
		dmo.updateBySQL("delete from ask_cate where AskCateID="+askCateID);
		
		return mapping.findForward("success");
	}

}
