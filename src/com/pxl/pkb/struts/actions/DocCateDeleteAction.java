package com.pxl.pkb.struts.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.pxl.pkb.framework.DataManagerObject;
import com.pxl.pkb.framework.PkbAction;

public class DocCateDeleteAction extends PkbAction {

	public ActionForward pkbExecute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String strDocCateID = request.getParameter("doccateid");
		int docCateID = Integer.parseInt(strDocCateID);
		
		DataManagerObject dmo = new DataManagerObject();
		dmo.updateBySQL("delete from doc_cate where docCateID="+docCateID);
		
		return mapping.findForward("success");
	}
}
