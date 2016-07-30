package com.pxl.pkb.struts.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.pxl.pkb.framework.PkbAction;

public class OpenJSPAction extends PkbAction {

	@Override
	public ActionForward pkbExecute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String forword = request.getParameter("forword");
		if(forword.equals("openProject")){
			return mapping.findForward("openProject");
		}
		if(forword.equals("openMember")){
			String projectId =request.getParameter("projectId");
			if(null!=projectId&&!"".equals(projectId)){
				request.setAttribute("projectId", projectId);
				return mapping.findForward("openMember");
			}
		
		}
		return null;
	}

}
