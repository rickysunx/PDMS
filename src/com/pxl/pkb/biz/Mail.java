package com.pxl.pkb.biz;

import java.net.Socket;
import java.text.DateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Vector;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

import com.pxl.pkb.framework.DataManagerObject;
import com.pxl.pkb.framework.Params;
import com.pxl.pkb.framework.ValueObject;
import com.pxl.pkb.vo.bd_mail;
import com.pxl.pkb.vo.bd_mailuser;
import com.pxl.pkb.vo.bd_user;
public class Mail {

	
	
	public static String [] getHostMxRecords(String host) throws Exception {
		Hashtable env = new Hashtable();
		env.put("java.naming.factory.initial","com.sun.jndi.dns.DnsContextFactory");
		env.put("java.naming.provider.url","dns://8.8.8.8");
		DirContext ictx = new InitialDirContext(env);
		
		Attributes attrs = ictx.getAttributes(host, new String[]{"MX"});
		Attribute attr = attrs.get("MX");
		
		if(attr==null) {
			return new String[]{host};
		}
		
		Vector v = new Vector();
		NamingEnumeration en = attr.getAll();
		while(en.hasMoreElements()) {
			String x = (String) en.next();
			String [] f = x.split(" ");
			if(f[1].endsWith(".")) {
				f[1] = f[1].substring(0,f[1].length()-1);
			}
			v.add(f[1]);
		}
		String [] records = new String[v.size()];
		v.copyInto(records);
		return records;
	}
	
	public static String getEmailHost(String emailAddress) throws Exception {
		if(emailAddress!=null) {
			int index = emailAddress.indexOf("@");
			if(index==-1) throw new Exception("非法的Email地址");
			return emailAddress.substring(index+1);
		} else {
			throw new Exception("email地址不能为空");
		}
	}
	
	public static void sendmail(String emailAddress,String subject,String content) throws Exception {
		if(!Params.getInstance().isSendMail()) return;
		
		String [] mxRecords = getHostMxRecords(getEmailHost(emailAddress));
		
//		for (int i = 0; i < mxRecords.length; i++) {
//			try {
//				s = new Socket(mxRecords[i],25);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
		
		//if(s==null) throw new Exception("连接邮件服务器失败");
		
		Properties props=System.getProperties();
		props.put("mail.smtp.auth","false");//同时通过验证
		props.put("mail.smtp.connectiontimeout", "100000"); //登录超时时间
		props.put("mail.smtp.timeout", "100000"); //设置断开时间
		props.put("mail.mime.charset", "gbk");//设置邮件的编码方式
		
		Session s=Session.getInstance(props,null);
		Message message=new MimeMessage(s);
		
		BodyPart bodyPart=new MimeBodyPart();
		bodyPart.setContent(content,"text/html;charset=gbk");
		MimeMultipart mm=new MimeMultipart();
		mm.addBodyPart(bodyPart);
		message.setContent(mm);
		
		Address from=new InternetAddress("pkb@pushingline.com","知识库系统");
		Address to=new InternetAddress(emailAddress);
		
		message.setFrom(from);//设置发件人
		message.setRecipient(Message.RecipientType.TO,to);// 设置收件人,并设置其接收类型为TO,还有3种预定义类型
		message.setSubject(subject);//设置主题
		message.setSentDate(new Date());//设置发信时间
		message.saveChanges();//存储邮件信息
		
		for (int i = 0; i < mxRecords.length; i++) {
			try {
				Transport transport=s.getTransport("smtp");
				transport.connect(mxRecords[i],null,null);
				transport.sendMessage(message, message.getAllRecipients());
				transport.close();
				break;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public static void main(String [] args) {
		try {
			sendmail("yangbo524@126.com","测试问题","请访问系统来修改");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void sendMail(int mailUserId) throws Exception {
		DateFormat format=DateFormat.getDateInstance();
		Date date=new Date();
		DataManagerObject dmo=new DataManagerObject();
		
		ValueObject[] mailUservos=dmo.queryByWhere(bd_mailuser.class, "mailuserid="+mailUserId);
		
		if(mailUservos.length!=1){
			throw new Exception("获取邮件信息出错");
		}
		bd_mailuser mailUser=(bd_mailuser)mailUservos[0];
		
		ValueObject[] mailvos=dmo.queryByWhere(bd_mail.class, "mailid="+mailUser.getMailID());
		if(mailvos.length!=1){
			throw new Exception("查找邮件信息出错");
		}
		bd_mail mail=(bd_mail)mailvos[0];
		String msg=mail.getMailContext()+"<br/><table align='right' width='90' border='0' style='color:gray;'><tr><td>"+format.format(date)+"</td></tr><tr><td><hr/></td></tr><tr><td>知识库系统</td></tr></talbe>";
		
		ValueObject[] uservos=dmo.queryByWhere(bd_user.class, "userid="+mailUser.getUserID());
		if(uservos.length!=1){
			throw new Exception("查找邮件地址失败");
		}
		bd_user user=(bd_user)uservos[0];
		try{
//			Properties props=System.getProperties();
//			//JavaMail需要Properties来创建一个session对象。它将寻找字符串"mail.smtp.host"，属性值就是发送邮件的主机.
//			//Properties对象获取诸如邮件服务器、用户名、密码等信息，以及其他可在整个应用程序中 共享的信息。
//	
//	//		Properties props=new Properties();//也可用Properties props = System.getProperties();
//	//		props.put("mail.smtp.host","smtp.126.com");// 存储发送邮件服务器的信息
//			props.put("mail.smtp.auth","true");//同时通过验证
//			props.put("mail.smtp.connectiontimeout", "10000"); //登录超时时间
//			props.put("mail.smtp.timeout", "10000"); //设置断开时间
//			props.put("mail.mime.charset", "gb2312");//设置邮件的编码方式
//			
//			//方法二： (如果是在weblogin配置JavaMail：则需指定JNDI名检索
//			//Context ctx=new InitialContext();
//			//Session s=(Session)ctx.lookup("MailSession");
//			//Message msg=new MimeMessage(s);
//	
//	
//			//这个Session类代表JavaMail 中的一个邮件session. 每一个基于 JavaMail的应用程序至少有一个session但是可以有任意多的session。
//			//Session类定义全局和每个用户的与邮件相关的属性。这此属性说明了客房机和服务器如何交流信息。
//	
//			Session s=Session.getInstance(props,null);//根据属性新建一个邮件会话，null参数是一种 Authenticator(验证程序) 对象
//	//		s.setDebug(true);//设置调试标志,要查看经过邮件服务器邮件命令，可以用该方法
//	
//			//  一旦创建了自己的Session对象，就是该去创建要发送的消息的 时候了。这时就要用到消息类型(MimeMessage是其中一种类型)。
//			//　Message对象将存储我们实际发送的电子邮件信息，Message对象被作为一个MimeMessage对象来创建并且需要知道应当选择哪一个JavaMail session。
//			//  Message类表示单个邮件消息，它的属性包括类型，地址信息和所定义的目录结构。
//	
//			Message message=new MimeMessage(s);//由邮件会话新建一个消息对象
//			/**
//			 * 发送text邮件
//			 */
//			//message.setText(msg);//设置信件内容
//	
//			/**
//			 * 发送HTML邮件
//			 */
//			BodyPart bodyPart=new MimeBodyPart();//新建一個存放信件內容的BodyPart對像
//			bodyPart.setContent(msg,"text/html;charset=gb2312");//給BodyPart對像設置內容和格式/編碼方式
//			MimeMultipart mm=new MimeMultipart();//新建一個MimeMultipart對像用來存放BodyPart對
//			mm.addBodyPart(bodyPart);//將BodyPart加入到MimeMultipart對像中(可以加入多個BodyPart)
//			message.setContent(mm);//设置消息的内容类型,如果发送的格式有HTML格式就必须设置，
//			
//			//设置邮件,一旦您创建了 Session 和 Message，并将内容填入消息后，就可以用Address确定信件地址了。
//			//如果想让一个名字出现在电子邮件地址后，也可以将其传递给构造器：
//			Address from=new InternetAddress("xiong3102598@126.com","知识库系统");//发件人的邮件地址
//			Address to=new InternetAddress(user.getEMail());//收件人的邮件地址
//			
//			message.setFrom(from);//设置发件人
//			message.setRecipient(Message.RecipientType.TO,to);// 设置收件人,并设置其接收类型为TO,还有3种预定义类型
//			message.setSubject(mail.getMailSubject());//设置主题
//			message.setSentDate(date);//设置发信时间
//			message.saveChanges();//存储邮件信息
//	
//			// Transport 是用来发送信息的，
//			// 用于邮件的收发打操作。
//			Transport transport=s.getTransport("smtp");
//			transport.connect("smtp.126.com","xiong3102598","3102598");//以smtp方式登录邮箱
//			transport.sendMessage(message,message.getAllRecipients());// 发送邮件,其中第二个参数是所有已设好的收件人地址
//			transport.close();
			sendmail(user.getEMail(), mail.getMailSubject(), msg);
			mailUser.setSendDate(Pub.getCurrTime());
			mailUser.setFailedReason("");
			mailUser.setIsSuccess("1");
			dmo.update(mailUser);
		}catch(Exception e){
			mailUser.setFailedCount(mailUser.getFailedCount()+1);
			mailUser.setIsSuccess("0");
		}
	}
//	public static void main(String[] args) throws Exception {
//		Mail.sendMail("xiongay1@ufida.com.cn", "测试", "测试测试测试测试测试测试");
//	}
	
}
