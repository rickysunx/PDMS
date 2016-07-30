/**
 * 
 */
package com.pxl.pkb.service;

import org.apache.ftpserver.impl.DefaultFtpServer;
import org.apache.ftpserver.impl.FtpServerContext;

/**
 * @author Ricky
 *
 */
public class PkbFtpServer extends DefaultFtpServer {

	protected static PkbFtpServer instance = null;
	/**
	 * 
	 */
	protected PkbFtpServer(final FtpServerContext serverContext) {
        super(serverContext);
    }

	public static PkbFtpServer getInstance() {
		if(instance==null) {
			FtpServerContext context = new PkbFtpServerContext();
			instance = new PkbFtpServer(context);
		}
		return instance;
	}
	
}
