package com.pxl.pkb.struts.actions;

import java.io.File;
import java.util.HashMap;
import java.util.Vector;

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
import com.pxl.pkb.vo.bd_user;

public class InitBulkUploadAction extends PkbAction {

	
	public File [] getFileList(String userCode) throws Exception {
		Vector v = new Vector();
		File f = new File(Pub.getBatchUploadFilepath()+File.separator+userCode);
		scanFileList(userCode, f, v);
		File [] files = new File[v.size()];
		v.copyInto(files);
		return files;
	}
		
	public void scanFileList(String userCode,File f,Vector v) throws Exception {
		if(f.isDirectory()) {
			File [] files = f.listFiles();
			for (int i = 0; i < files.length; i++) {
				scanFileList(userCode, files[i], v);
			}
		} else {
			if(!f.getName().endsWith(BatchImportThread.UPLOADED_EXT_NAME)) {
				v.add(f);
			}
		}
	}
	
	public ActionForward pkbExecute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DataManagerObject dmo=new DataManagerObject();
		File directory = new File(Pub.getBatchUploadFilepath());
		if(!directory.exists())
			throw new Exception("Â·¾¶Î´ÕÒµ½");
		File[] filePaths = directory.listFiles();
		HashMap map = new HashMap();
		for (int i = 0; i < filePaths.length; i++) {
			ValueObject[] vos= dmo.queryByWhere(bd_user.class,"userCode='"+filePaths[i].getName()+"'");
			if(vos.length==1){
				bd_user user=(bd_user)vos[0];
				File [] files = getFileList(user.getUserCode());
				int count = files.length;
				map.put(user.getUserName(), count + "");
			}
		}
		Object result=request.getAttribute("result");
		if(result!=null){
			request.setAttribute("result", result);
		}
		request.setAttribute("userList", map);
		return mapping.findForward("success");
	}

}
