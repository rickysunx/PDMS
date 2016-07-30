package com.pxl.pkb.struts.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.pxl.pkb.biz.Consts;
import com.pxl.pkb.biz.NotifyManager;
import com.pxl.pkb.biz.Pub;
import com.pxl.pkb.biz.User;
import com.pxl.pkb.framework.DataManagerObject;
import com.pxl.pkb.framework.PkbAction;
import com.pxl.pkb.framework.ValueObject;
import com.pxl.pkb.struts.forms.AnswerAddForm;
import com.pxl.pkb.vo.ask_answer;
import com.pxl.pkb.vo.ask_qst;
import com.pxl.pkb.vo.bd_user;

public class AnswerAddAction extends PkbAction {

	public AnswerAddAction() {
	}

	public ActionForward pkbExecute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		AnswerAddForm myForm = (AnswerAddForm)form;
		HttpSession session = request.getSession();
		bd_user user = (bd_user)session.getAttribute(Consts.PKB_USER_SESSION_NAME);
		DataManagerObject dmo = new DataManagerObject();
		String strQstID = request.getParameter("qstid");
		int qstid = Integer.parseInt(strQstID);
		
		if(Pub.isUnCheckUser(session)) {
			throw new Exception("您的用户尚未通过审核，不能进行回答！");
		}
		
		ask_answer answer = new ask_answer();
		answer.setQstID(qstid);
		answer.setAnswerContent(myForm.getAnswerContent());
		answer.setAddIP(request.getRemoteHost());
		String currTime = Pub.getCurrTime();
		answer.setAddTime(currTime);
		answer.setUpdateTime(currTime);
		answer.setAddUser(user.getUserID());
		dmo.insert(answer);
		
		dmo.updateBySQL("update ask_qst set ReplyCount="+"(select count(1) from ask_answer where QstID="
				+qstid+") where qstid=" + qstid);
		Object[][] userScore= dmo.querySQL("select sum(UserScore) from bd_userscore where ScoreContent like '回答问题:%' and AddTime like '"+Pub.getCurrDate()+"%'");
		
		ValueObject [] qstvos = dmo.queryByWhere(ask_qst.class, " qstid="+qstid );
		ask_qst qst = new ask_qst();
		if(qstvos.length>0) {
			qst = (ask_qst)qstvos[0];
		}
		
		if(userScore[0][0]==null||Integer.parseInt(userScore[0][0].toString())<100){
			User.userScoreAdd(user.getUserID(), "回答问题:"+qstid, 20);
		}
		Pub.refreshQst(qstid);
		
		NotifyManager.getInstance().addNotifyMsg(
				"["+user.getUserName()+"]回答了问题："+qst.getQstTitle(), 
				"ask_qst_view.jsp?qstid="+qstid, currTime);
		
		response.sendRedirect("ask_qst_view.jsp?qstid="+qstid);
		
		return null;
	}

}
