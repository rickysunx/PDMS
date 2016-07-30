/**
 * 
 */
package com.pxl.pkb.struts.actions;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.pxl.pkb.biz.Attach;
import com.pxl.pkb.biz.Consts;
import com.pxl.pkb.biz.NotifyManager;
import com.pxl.pkb.biz.Pub;
import com.pxl.pkb.biz.TextExtracter;
import com.pxl.pkb.biz.User;
import com.pxl.pkb.framework.DataManagerObject;
import com.pxl.pkb.framework.PkbAction;
import com.pxl.pkb.framework.ValueObject;
import com.pxl.pkb.struts.forms.DocAddForm;
import com.pxl.pkb.vo.bd_user;
import com.pxl.pkb.vo.doc_cate;
import com.pxl.pkb.vo.doc_doc;
import com.pxl.pkb.vo.doc_doccate;
import com.pxl.pkb.vo.doc_ver;
import com.pxl.pkb.vo.sys_attach;

/**
 * @author Ricky
 *
 */
public class DocAddAction extends PkbAction {

	/**
	 * 
	 */
	public DocAddAction() {
	}

	/* (non-Javadoc)
	 * @see com.pxl.pkb.framework.PkbAction#pkbExecute(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public ActionForward pkbExecute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		DocAddForm theForm = (DocAddForm) form;
		DataManagerObject dmo = new DataManagerObject();
		
		HttpSession session = request.getSession();
		if(Pub.isUnCheckUser(session)) {
			throw new Exception("您的用户尚未通过审核，不能进行回答！");
		}
		String uploadPath = Pub.getUploadPath();//session.getServletContext().getRealPath("META-INF/upload/");
		bd_user user = (bd_user)session.getAttribute(Consts.PKB_USER_SESSION_NAME);
		
		String strDocCateID=theForm.getDocCate();
		String[] strDocCateIDs=strDocCateID.split("&");
	
		ValueObject[] doc_catevos=null;
		if(null!=strDocCateIDs&&!"".equals(strDocCateIDs)){
		       doc_catevos= dmo.queryByWhere(doc_cate.class, "DocCateID="+strDocCateIDs[0]);
		}
		int doc_cateslength=0;
			doc_cateslength=doc_catevos.length;
			
		if(doc_cateslength==1){
			doc_cate docCate=(doc_cate)doc_catevos[0];
			if(docCate.getCanPost().equals("0")){
				throw new Exception("该类别下不能上传文档");
			}else{
				doc_doc doc = new doc_doc();
				doc.setDocCateID(Integer.parseInt(strDocCateIDs[0]));
				doc.setDocTitle(theForm.getDocTitle());
				doc.setDocIntro(theForm.getDocIntro());
				doc.setDocKeyword(theForm.getDocKeyword());
				String currTime = Pub.getCurrTime();
				doc.setAddTime(currTime);
				doc.setAddUser(user.getUserID());
				doc.setUpdateTime(currTime);
				doc.setDownloadCount(0);
				doc.setClickCount(0);
				
				String strAttachID = request.getParameter("attach0");
				int attachID = Integer.parseInt(strAttachID);
				sys_attach attach = Attach.findAttach(attachID);
				doc.setDocType(attach.getFileType());
				
				int docid = dmo.insert(doc);
				
				//创建版本
				doc_ver docVer = new doc_ver();
				docVer.setAddIP(request.getRemoteHost());
				docVer.setAddTime(currTime);
				docVer.setAddUser(user.getUserID());
				docVer.setDocID(docid);
				docVer.setDocVerName("初始版本");
				docVer.setDocText("");
				docVer.setIsLatest("1");
				
				int docVerID = dmo.insert(docVer);
				
				
				//增加关联级别
				if(strDocCateIDs.length>1) {
					for (int i = 1; i < strDocCateIDs.length; i++) {
						int otherCateID = Integer.parseInt(strDocCateIDs[i]);
						if(otherCateID>0) {
							doc_doccate doccate = new doc_doccate();
							doccate.setCateID(otherCateID);
							doccate.setDocID(docid);
							dmo.insert(doccate);
						}
					}
				}

				//设置附件对象
				attach.setObjID(docVerID);
				dmo.update(attach);
				//判断是否项目文档
				Object[][] IsProjectCates=dmo.querySQL("select docType from doc_doccate,doc_cate where doc_doccate.cateid=doc_cate.doccateid and docid="+docid);
				Boolean isProjectCate=false;
				for(int i=0;i<IsProjectCates.length;i++){
					if(Integer.parseInt(IsProjectCates[i][0].toString())==1){
						isProjectCate=true;
					}
				}
				//不是项目文档就加20分
				if(!isProjectCate){
					User.userScoreAdd(user.getUserID(), "上传文档:"+docid, 20);
				}
				//提取文档文本
				String filePath = uploadPath+File.separator+"DOC"+File.separator+attach.getAddTime().substring(0,10)+File.separator+attachID;
				String text = TextExtracter.getTextFromFile(filePath, attach.getFileType());
				docVer.setDocText(text);
				dmo.update(docVer);
				
				NotifyManager.getInstance().addNotifyMsg(
						"["+user.getUserName()+"]发布文档："+doc.getDocTitle(), 
						"doc_detail.jsp?docid="+doc.getDocID(), currTime);
				
				response.sendRedirect("doc_list.jsp?doccateid="+doc.getDocCateID());
			}
		}else{
			throw new Exception("系统异常");
		}
		return null;
	}

}
