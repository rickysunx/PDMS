/**
 * 
 */
package com.pxl.pkb.framework;

/**
 * @author Ricky
 *
 */
public final class Params {

	protected String backupTime = null;
	protected String uploadPath = null;
	protected String batchUploadPath = null;
	protected String backupPath = null;
	protected String flashPaperPath = null;
	protected String flashPaperServer = null;
	protected String db_url = null;
	protected String db_user = null;
	protected String db_pass = null;
	protected String mysqldumpPath = null;
	protected boolean systemRunning = false;
	protected boolean sendMail = false;
	
	protected static Params m_instance = null;
	/**
	 * 
	 */
	protected Params() {
		super();
	}
	
	public static Params getInstance() {
		if(m_instance==null) {
			m_instance = new Params();
			m_instance.setSystemRunning(true);
		}
		return m_instance;
	}

	public String getBackupTime() {
		return backupTime;
	}

	public void setBackupTime(String backupTime) {
		this.backupTime = backupTime;
	}

	public String getBatchUploadPath() {
		return batchUploadPath;
	}

	public void setBatchUploadPath(String batchUploadPath) {
		this.batchUploadPath = batchUploadPath;
	}

	public String getDb_pass() {
		return db_pass;
	}

	public void setDb_pass(String db_pass) {
		this.db_pass = db_pass;
	}

	public String getDb_url() {
		return db_url;
	}

	public void setDb_url(String db_url) {
		this.db_url = db_url;
	}

	public String getDb_user() {
		return db_user;
	}

	public void setDb_user(String db_user) {
		this.db_user = db_user;
	}

	public String getUploadPath() {
		return uploadPath;
	}

	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
	}

	public String getBackupPath() {
		return backupPath;
	}

	public void setBackupPath(String backupPath) {
		this.backupPath = backupPath;
	}


	public String getMysqldumpPath() {
		return mysqldumpPath;
	}

	public void setMysqldumpPath(String mysqldumpPath) {
		this.mysqldumpPath = mysqldumpPath;
	}

	public boolean isSystemRunning() {
		return systemRunning;
	}

	public void setSystemRunning(boolean systemRunning) {
		this.systemRunning = systemRunning;
	}

	public boolean isSendMail() {
		return sendMail;
	}

	public void setSendMail(boolean sendMail) {
		this.sendMail = sendMail;
	}

	public String getFlashPaperPath() {
		return flashPaperPath;
	}

	public void setFlashPaperPath(String flashPaperPath) {
		this.flashPaperPath = flashPaperPath;
	}

	public String getFlashPaperServer() {
		return flashPaperServer;
	}

	public void setFlashPaperServer(String flashPaperServer) {
		this.flashPaperServer = flashPaperServer;
	}
	
	public int getFlashPaperServerPort() {
		return 9500;
	}

}
