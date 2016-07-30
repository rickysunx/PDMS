package com.pxl.pkb.biz;

import java.util.Vector;

import com.pxl.pkb.myvo.NotifyInfo;

public class NotifyManager {
	protected static NotifyManager m_instance = null;
	
	public static final String INFO_SESSION_NAME = "NotifyInfoVector";
	
	
	public Vector notifyInfoVector = null;
	protected NotifyManager() {
		super();
		notifyInfoVector = new Vector();
	}
	
	public static NotifyManager getInstance() {
		if(m_instance==null) {
			m_instance = new NotifyManager();
		}
		return m_instance;
	}
	
	//增加通知消息
	public void addNotifyMsg(String str,String url,String currTime) throws Exception {
		synchronized (this) {
			NotifyInfo info = new NotifyInfo();
			info.setInfo(currTime+"$$$"+url+"$$$"+str);
			info.setType(NotifyInfo.TYPE_MSG);
			addNotify(info);
		}
	}
	 
	public void addNotify(NotifyInfo info) throws Exception {
		synchronized (this) {
			notifyInfoVector.add(info);
		}
	}

	public Vector getNotifyInfoVector() {
		return notifyInfoVector;
	}

	public void setNotifyInfoVector(Vector notifyInfoVector) {
		this.notifyInfoVector = notifyInfoVector;
	}
	
}
