package com.pxl.pkb.struts.actions;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.pxl.pkb.biz.BatchImportThread;
import com.pxl.pkb.biz.Pub;
import com.pxl.pkb.framework.DataManagerObject;
import com.pxl.pkb.framework.PkbAction;
import com.pxl.pkb.framework.ValueObject;
import com.pxl.pkb.vo.doc_cate;

public class BulkUploadAction extends PkbAction {

	public ActionForward pkbExecute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String method = request.getParameter("method");
		String[] userCodes = request.getParameterValues("users");
		DataManagerObject dmo = new DataManagerObject();
		ValueObject[] catevos = dmo.queryAll(doc_cate.class);
		String batchUploadPath = Pub.getBatchUploadFilepath()+File.separator;
		
		doc_cate [] docCates = new doc_cate[catevos.length];
		for (int i = 0; i < docCates.length; i++) {
			docCates[i] = (doc_cate)catevos[i];
		}
		
		if (method.equalsIgnoreCase("createDirectory")) {
			//创建上传目录
			for (int i = 0; i < userCodes.length; i++) {
				createDir(userCodes[i], null, docCates,batchUploadPath+userCodes[i]);
			}
		} else {
			//导入文档
			if(BatchImportThread.importing) {
				throw new Exception("正在导入，<a href='admin_importing.jsp'>点击进入导入监控页面</a>");
			}
			BatchImportThread.importing=true;
			(new BatchImportThread(userCodes,docCates,batchUploadPath,request.getRemoteHost())).start();
			response.sendRedirect("admin_importing.jsp");
			return null;
		}
		
		return mapping.findForward("success");
	}
	
	public void createDir(String userCode,doc_cate cate,doc_cate [] cates,String parentPath) throws Exception {
		for (int i = 0; i < cates.length; i++) {
			int pid = (cate==null)?0:cate.getDocCateID();
			if(cates[i].getParentDocCate()==pid) {
				File f = new File(parentPath+File.separator+cates[i].getDocCateName());
				f.mkdirs();
				createDir(userCode, cates[i], cates, parentPath+File.separator+cates[i].getDocCateName());
			}
		}
	}
	
	public void delDir(File f) throws Exception {
		File [] files = f.listFiles();
		for (int i = 0; i < files.length; i++) {
			if(files[i].isDirectory()) {
				delDir(files[i]);
			} else {
				files[i].delete();
			}
		}
		f.delete();
	}
	
}
