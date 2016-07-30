/**
 * 
 */
package com.pxl.pkb.service;

import org.apache.ftpserver.ftplet.UserManager;
import org.apache.ftpserver.impl.DefaultFtpServerContext;

/**
 * @author Ricky
 *
 */
public class PkbFtpServerContext extends DefaultFtpServerContext {

	public UserManager m_userManger = null;

	/**
	 * 
	 */
	public PkbFtpServerContext() {
	}
	
	public UserManager getUserManager() {
		System.out.println("getUserManager=====");
		if(m_userManger==null) {
			m_userManger = new PkbFtpUserManager();
		}
		return m_userManger;
	}

	public void setUserManager(UserManager userManager) {
		m_userManger = userManager;
	}

}
