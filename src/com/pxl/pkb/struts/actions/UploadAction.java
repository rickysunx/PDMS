package com.pxl.pkb.struts.actions;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.pxl.pkb.biz.Consts;
import com.pxl.pkb.biz.Pub;
import com.pxl.pkb.framework.DataManagerObject;
import com.pxl.pkb.framework.PkbAction;
import com.pxl.pkb.framework.PxlOutputStream;
import com.pxl.pkb.struts.forms.UploadForm;
import com.pxl.pkb.vo.bd_user;
import com.pxl.pkb.vo.sys_attach;

public class UploadAction extends PkbAction {


	public ActionForward pkbExecute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UploadForm theForm = (UploadForm) form;
		
		FormFile file = theForm.getMyfile();
		String cate = request.getParameter("cate");
		if(file!=null) {
			String fileName = file.getFileName();
			
			HttpSession session = request.getSession();
			String path = Pub.getUploadPath()+File.separator+cate; 
			DataManagerObject dmo = new DataManagerObject();
			bd_user user = (bd_user)session.getAttribute(Consts.PKB_USER_SESSION_NAME);
			
			
			sys_attach attach = new sys_attach();
			attach.setAttachCate(cate);
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
			
			OutputStream output = new PxlOutputStream(fileFullPath);
			InputStream fin = file.getInputStream();
			byte [] buff = new byte[512];
			int len;
			while((len=fin.read(buff))!=-1) {
				output.write(buff, 0, len);
			}
			output.flush();
			output.close();
			file.destroy();
			request.setAttribute("attach", attach);
		} else {
			request.setAttribute("error", "文件大小超过系统限制");
		}
		request.setAttribute("cate", cate);
		if(cate.equalsIgnoreCase("DOC")) {
			request.setAttribute("action", "update");
		}
		return mapping.findForward("success");
	}

}
