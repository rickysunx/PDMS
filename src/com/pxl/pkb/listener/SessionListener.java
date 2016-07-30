package com.pxl.pkb.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionListener implements HttpSessionListener {

	public void sessionCreated(HttpSessionEvent httpsessionevent) {
//		System.out.println("=========Session Created===========");
//		HttpSession session = httpsessionevent.getSession();
//		Vector vNotifyInfo = new Vector();
//		session.setAttribute(NotifyManager.INFO_SESSION_NAME, vNotifyInfo);
//		NotifyManager.getInstance().addSession(session);
	}

	public void sessionDestroyed(HttpSessionEvent httpsessionevent) {
//		System.out.println("=========Session Destroyed=========");
//		HttpSession session = httpsessionevent.getSession();
//		NotifyManager.getInstance().removeSession(session);
	}

}
