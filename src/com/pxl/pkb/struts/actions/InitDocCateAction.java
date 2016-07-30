package com.pxl.pkb.struts.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.pxl.pkb.framework.DataManagerObject;
import com.pxl.pkb.framework.PkbAction;
import com.pxl.pkb.framework.ValueObject;
import com.pxl.pkb.vo.doc_cate;

public class InitDocCateAction extends PkbAction {

	public ActionForward pkbExecute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String docCateID=request.getParameter("docCateID");
		DataManagerObject dmo=new DataManagerObject();
		ValueObject[] docCate=dmo.queryByWhere(doc_cate.class, "DocCateID="+docCateID);
		request.setAttribute("docCate", docCate[0]);
		return mapping.findForward("success");
	}
}
