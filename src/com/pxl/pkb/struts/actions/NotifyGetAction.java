package com.pxl.pkb.struts.actions;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.pxl.pkb.biz.NotifyManager;
import com.pxl.pkb.biz.Pub;
import com.pxl.pkb.framework.PkbAction;
import com.pxl.pkb.myvo.NotifyInfo;

public class NotifyGetAction extends PkbAction {

	public ActionForward pkbExecute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		String strType = request.getParameter("type");
		String strTime = request.getParameter("time");
		int type = (strType==null||strType.trim().length()==0)?-1:Integer.parseInt(strType);
		
		StringBuffer sb = new StringBuffer();
		String currTime = null;
		currTime = Pub.getCurrTime();
		if(strTime!=null && strTime.trim().length()>0) {
			Vector vNotifyInfo = NotifyManager.getInstance().getNotifyInfoVector(); 
			synchronized (NotifyManager.getInstance()) {
				currTime = Pub.getCurrTime();
				for (int i = 0; i < vNotifyInfo.size(); i++) {
					NotifyInfo info = (NotifyInfo)vNotifyInfo.get(i);
					if(info.getType()==type && info.getInfoTime().compareTo(strTime)>=0 && info.getInfoTime().compareTo(currTime)<0) {
						sb.append(info.getInfo()+"###");
					}
				}
			}
		}
		
		request.setAttribute("time", currTime);
		request.setAttribute("text", sb.toString());
		
		
		return mapping.findForward("success");
	}

}
