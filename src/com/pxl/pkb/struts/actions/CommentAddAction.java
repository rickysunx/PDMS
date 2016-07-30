package com.pxl.pkb.struts.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.pxl.pkb.biz.Consts;
import com.pxl.pkb.biz.Pub;
import com.pxl.pkb.framework.DataManagerObject;
import com.pxl.pkb.framework.PkbAction;
import com.pxl.pkb.struts.forms.CommentAddForm;
import com.pxl.pkb.vo.bd_comment;
import com.pxl.pkb.vo.bd_user;

public class CommentAddAction extends PkbAction {

	public CommentAddAction() {

	}


	public ActionForward pkbExecute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		CommentAddForm theForm = (CommentAddForm) form;
		HttpSession session = request.getSession();
		if(Pub.isUnCheckUser(session)) {
			throw new Exception("未审核用户不能进行评论");
		}
		if(theForm.getCommentContent()==null||theForm.getCommentContent().trim().length()==0) {
			throw new Exception("评论不能为空");
		}
		
		
		DataManagerObject dmo = new DataManagerObject();
		bd_comment comment = new bd_comment();
		bd_user user = (bd_user)session.getAttribute(Consts.PKB_USER_SESSION_NAME);
		comment.setObjID(Integer.parseInt(theForm.getObjID()));
		comment.setCommentType(Integer.parseInt(theForm.getCommentType()));
		comment.setCommentContent(theForm.getCommentContent());
		comment.setAddIP(request.getRemoteHost());
		comment.setAddTime(Pub.getCurrTime());
		comment.setAddUser(user.getUserID());
		
		dmo.insert(comment);
		
		response.sendRedirect(theForm.getReturnPage());
		
		return null;
	}

}
