package com.pxl.pkb.struts.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.pxl.pkb.framework.DataManagerObject;
import com.pxl.pkb.framework.PkbAction;
import com.pxl.pkb.framework.ValueObject;
import com.pxl.pkb.vo.ask_cate;

public class InitAskCateAction extends PkbAction {

	public ActionForward pkbExecute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String askCateID=request.getParameter("askCateID");
		DataManagerObject dmo=new DataManagerObject();
		ValueObject[] askCate=dmo.queryByWhere(ask_cate.class, "askCateID="+askCateID);
		request.setAttribute("askCate", askCate[0]);
		return mapping.findForward("success");
	}
}
