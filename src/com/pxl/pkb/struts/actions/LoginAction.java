package com.pxl.pkb.struts.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.pxl.pkb.biz.Consts;
import com.pxl.pkb.biz.Pub;
import com.pxl.pkb.biz.User;
import com.pxl.pkb.framework.DataManagerObject;
import com.pxl.pkb.framework.PkbAction;
import com.pxl.pkb.framework.ValueObject;
import com.pxl.pkb.struts.forms.LoginForm;
import com.pxl.pkb.vo.bd_user;

public class LoginAction extends PkbAction {

	public LoginAction() {
	}

	
	public ActionForward pkbExecute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		LoginForm loginForm = (LoginForm) form;
		String userCode = loginForm.getUserCode();
		String passWord = loginForm.getPassWord();
		passWord = Pub.encodeByMD5(passWord);
		
		HttpSession session = request.getSession();
		DataManagerObject dmo = new DataManagerObject();
		ValueObject [] vos = dmo.queryByWhere(bd_user.class, " UserCode='"+userCode+"'");
		if(vos.length==1) {
			bd_user user = (bd_user)vos[0];
			if(user.getPassWord().equals(passWord)) {
				if(user.getErrorCount()<5){
					user.setErrorCount(0);
					if(user.getIsAdmin().equalsIgnoreCase("3")) throw new Exception("未审核的用户不能登录！");
					String currTime = Pub.getCurrTime();
					String currDate = currTime.substring(0,10);
					if(user.getLastLogTime()==null || (!user.getLastLogTime().startsWith(currDate))) {
						User.userScoreAdd(user.getUserID(), "当日首次登录", 10);
					}
					user.setLastLogTime(currTime);
					dmo.update(user);
					session.setAttribute(Consts.PKB_USER_SESSION_NAME, user);
					Pub.addOperaNote(request, "用户登录", user.getUserID());
					
				}else{
					request.setAttribute("errinfo", "对不起，您的账户已被锁定，请联系管理员");
					return mapping.findForward("failed");
				}
			} else {
				user.setErrorCount(user.getErrorCount()+1);
				dmo.update(user);
				request.setAttribute("errinfo", "密码错误,密码连续5次错误该用户将被锁定,您当前还剩"+(5-user.getErrorCount())+"次机会");
				return mapping.findForward("failed");
			}
		} else {
			request.setAttribute("errinfo", "查找用户失败");
			return mapping.findForward("failed");
		}
		
		if(request.getSession().getAttribute("oldURL")!=null){
			response.sendRedirect(request.getSession().getAttribute("oldURL").toString());
			request.getSession().removeAttribute("oldURL");
			return null;
		}
		return mapping.findForward("success");
	}

}
