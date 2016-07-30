package com.pxl.pkb.struts.actions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.pxl.pkb.biz.Consts;
import com.pxl.pkb.biz.Pub;
import com.pxl.pkb.biz.TextExtracter;
import com.pxl.pkb.framework.DataManagerObject;
import com.pxl.pkb.framework.PkbAction;
import com.pxl.pkb.struts.forms.DocUpdateForm;
import com.pxl.pkb.vo.bd_user;
import com.pxl.pkb.vo.doc_ver;
import com.pxl.pkb.vo.sys_attach;

public class DocUpdateAction extends PkbAction {
	public ActionForward pkbExecute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		DocUpdateForm theForm = (DocUpdateForm) form;
		
		int docid = Integer.parseInt(theForm.getDocid());
		
		FormFile file = theForm.getMyfile();
		String fileName = file.getFileName();
		HttpSession session = request.getSession();
		//ServletContext application = session.getServletContext();
		String path = Pub.getUploadPath()+File.separator+"DOC"; //application.getRealPath("META-INF/upload/DOC");
		DataManagerObject dmo = new DataManagerObject();
		bd_user user = (bd_user)session.getAttribute(Consts.PKB_USER_SESSION_NAME);
		String currTime = Pub.getCurrTime();
		
		dmo.updateBySQL("update doc_doc set UpdateTime='"+currTime+"' where DocID="+docid);
		
		//将原有版本最新标志置为0
		dmo.updateBySQL("update doc_ver set IsLatest=0 where DocID="+docid);
		
		
		sys_attach attach = new sys_attach();
		attach.setAttachCate("DOC");
		attach.setAddUser(user.getUserID());
		attach.setAddIP(request.getRemoteHost());
		attach.setAddTime(Pub.getCurrTime());
		String [] fileNameAndExt = Pub.getFileNameAndExt(fileName);
		attach.setFileName(fileNameAndExt[0]);
		attach.setFileType(fileNameAndExt[1]);
		int attachID = dmo.insert(attach);
		attach.setAttachID(attachID);
		
		String filePath = path+File.separator+attach.getAddTime().substring(0,10);
		String fileFullPath = filePath+File.separator+attachID;
		
		File fPath = new File(filePath);
		fPath.mkdirs();
		
		FileOutputStream fout = new FileOutputStream(fileFullPath);
		InputStream fin = file.getInputStream();
		byte [] buff = new byte[512];
		int len;
		while((len=fin.read(buff))!=-1) {
			fout.write(buff, 0, len);
		}
		fout.flush();
		fout.close();
		file.destroy();
		
		
		//创建一个新版本
		doc_ver docVer = new doc_ver();
		docVer.setAddIP(request.getRemoteHost());
		docVer.setAddTime(currTime);
		docVer.setAddUser(user.getUserID());
		docVer.setDocID(docid);
		docVer.setDocVerName("版本["+currTime+"]");
		docVer.setIsLatest("1");
		
		String text = TextExtracter.getTextFromFile(fileFullPath, attach.getFileType());
		docVer.setDocText(text);
		
		int docVerID = dmo.insert(docVer);
		attach.setObjID(docVerID);
		dmo.update(attach);
		
		
		
		response.sendRedirect("doc_detail.jsp?docid="+docid);
		
		return null;
	}

}
