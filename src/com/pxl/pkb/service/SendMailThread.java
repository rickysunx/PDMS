package com.pxl.pkb.service;

import com.pxl.pkb.biz.Mail;
import com.pxl.pkb.framework.DataManagerObject;
import com.pxl.pkb.framework.ValueObject;
import com.pxl.pkb.vo.bd_mailuser;

public class SendMailThread extends Thread {

	private SendMailThread(){
		super();
	}
	private static SendMailThread sendMail=null;
	public static SendMailThread getInstence(){
		if(sendMail==null){
			sendMail=new SendMailThread();
		}
		return sendMail;
	}
	
	@Override
	public void run() {
		while(true){
			try {
				Thread.sleep(30000);
				sendMail();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void sendMail() throws Exception{
		DataManagerObject dmo=new DataManagerObject();
		ValueObject[] mailUserVos=dmo.queryByWhere0(bd_mailuser.class, " IsSuccess=0 and FailedCount<5");
		for(int i=0;i<mailUserVos.length;i++){
			bd_mailuser mailUser=(bd_mailuser)mailUserVos[i];
			Mail.sendMail(mailUser.getMailUserID());
		}
	}
}
