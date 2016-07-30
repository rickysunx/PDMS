package com.pxl.pkb.framework;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

public abstract class PkbAction extends Action {
	public PkbAction() {
	}
	
	public abstract ActionForward pkbExecute(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ActionForward forward = null;
		Connection conn = null;
		try {
			conn = DataManagerObject.getConnection();
			conn.setAutoCommit(false);
			forward = pkbExecute(mapping, form, request, response);
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if(conn!=null) conn.rollback();
			request.setAttribute("errmsg", e.getMessage());
			request.getRequestDispatcher("/errmsg.jsp").forward(request, response);
		} finally {
			conn.setAutoCommit(true);
		}
		
		return forward;
	}

}
