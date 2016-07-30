package com.pxl.pkb.struts.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.xmlbeans.impl.xb.ltgfmt.TestCase.Files;

import com.pxl.pkb.biz.Consts;
import com.pxl.pkb.biz.Pub;
import com.pxl.pkb.framework.DataManagerObject;
import com.pxl.pkb.framework.PkbAction;
import com.pxl.pkb.framework.PxlOutputStream;
import com.pxl.pkb.readers.MyReadLine;
import com.pxl.pkb.vo.bd_user;
import com.pxl.pkb.vo.sys_attach;

public class FlashUploadAction extends PkbAction {

    public ActionForward pkbExecute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
    	String cate = request.getParameter("cate");
    	String taskId = request.getParameter("taskId");
    	String number = request.getParameter("number");
		String contentType =request.getContentType();
		String objAction="";
		HttpSession session = request.getSession();
		String path =Pub.getUploadPath()+File.separator+cate; 
		DataManagerObject dmo = new DataManagerObject();
		bd_user user = (bd_user)session.getAttribute(Consts.PKB_USER_SESSION_NAME);
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		String header = "Content-Disposition: form-data; name=\"Filedata\";";
		String header_filename="filename=\"";
		
		sys_attach attach = new sys_attach();
		try{
			if(contentType.indexOf("multipart/form-data")>=0){
				InputStream in = request.getInputStream();
				MyReadLine myreader = new MyReadLine(in);
				String line = null;
				String filename=null;
				String boundary = myreader.readLine("UTF-8");
				while((line = myreader.readLine("UTF-8"))!=null){
					if(line.startsWith(header)){
						int index_start =line.indexOf(header_filename);
						int index_end = line.lastIndexOf("\"");
						filename =line.substring(index_start+header_filename.length(), index_end);
						myreader.readLine("UTF-8");
						myreader.readLine("UTF-8");
						break;
					}
				}
				
				attach.setAttachCate(cate);
				attach.setAddUser(user.getUserID());
				attach.setAddIP(request.getRemoteHost());
				attach.setAddTime(Pub.getCurrTime());
				String [] fileNameAndExt = Pub.getFileNameAndExt(filename);
				attach.setFileName(fileNameAndExt[0]);
				attach.setFileType(fileNameAndExt[1]);
				int attachID = dmo.insert(attach);
				attach.setAttachID(attachID);
				String filePath = path+File.separator+attach.getAddTime().substring(0,10);
				String fileFullPath = filePath+File.separator+attachID;
				request.setAttribute("attach", attach);
				String remoteName =Integer.toString(attachID);
				File file = new File(filePath);
				if(file.exists()){
			        file.delete();
				}else{
					file.mkdirs();
				}
				OutputStream outfile = new PxlOutputStream(fileFullPath);
				byte[] buff=null;
				byte[] oldbuff =null;
			
				while((buff=myreader.readLineBytes())!=null){
					String boundary1 = new String (buff,"GBK");
					if(boundary1.compareTo(boundary)==0){
						if(oldbuff!=null){
							outfile.write(oldbuff,0,oldbuff.length-2);
					       // UploadProperty.writeProperties(remoteName,Integer.toString(oldbuff.length));
						} 
						break;
						
					}else{
						if(oldbuff!=null){
							outfile.write(oldbuff);
							//UploadProperty.writeProperties(remoteName,Integer.toString(oldbuff.length));
						} 
						
					}
					oldbuff = buff;
				    
				}
				outfile.flush();
				outfile.close();
			}else{
				String content = request.getContentType();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		request.setAttribute("cate", cate);

		if(cate.equalsIgnoreCase("DOC")) {
			objAction ="update";
		}
		if(cate.equalsIgnoreCase("QST")){
			objAction ="add";
		}
		if(cate.equalsIgnoreCase("OUTPUT")){
			objAction="outadd";
		}
		String filename = java.net.URLDecoder.decode(attach.getFileName(),"UTF-8");
		if(attach!=null) {
			if(objAction==null) {
				out.println("addUploadFile("+attach.getAttachID()+",'"+
						Pub.getFullFileName(filename,attach.getFileType())+"');");
			} else {
				String action = (String)objAction;
				if(action.equalsIgnoreCase("add")) {
					out.print("addUploadFile("+attach.getAttachID()+",'"+
							Pub.getFullFileName(filename,attach.getFileType())+"');");
				} else if(action.equalsIgnoreCase("update")) {
					out.println("updateUploadFile("+attach.getAttachID()+",'"+
							Pub.getFullFileName(filename,attach.getFileType())+"');");
				}else if(action.equalsIgnoreCase("outadd")){
					if(null!=taskId&&null!=number&&!"".equals(taskId)&&!"".equals(number)){
						System.out.println(number+","+taskId);
						out.println("outaddUploadFile('"+attach.getAttachID()+"','"+Pub.getFullFileName(filename,attach.getFileType())+"','"+taskId+"','"+number+"');");
					}
					
				}
			}
		}
		return null;
	}

}
