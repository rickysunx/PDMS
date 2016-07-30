/**
 * 
 */
package com.pxl.pkb.biz;

import java.util.Stack;
import java.util.Vector;

import com.pxl.pkb.framework.DataManagerObject;
import com.pxl.pkb.framework.ValueObject;
import com.pxl.pkb.vo.doc_cate;
import com.pxl.pkb.vo.sys_attach;

/**
 * @author Ricky
 *
 */
public class Doc {

	/**
	 * 
	 */
	public Doc() {
	}
	
	public static void scanCatesForTreeOrder(Vector v,Stack st,int pid,doc_cate[] cates) throws Exception {
		for(int i=0;i<cates.length;i++) {
			if(cates[i].getParentDocCate()==pid) {
				cates[i].setDocCateName(Pub.getSpace(st.size()*2)+"¡¤"+cates[i].getDocCateName());
				st.push(cates[i]);
				v.add(cates[i]);
				scanCatesForTreeOrder(v, st, cates[i].getDocCateID(), cates);
				st.pop();
			}
		}
	}
	
	public static synchronized void convertDocToSwf(int docid) throws Exception {
		DataManagerObject dmo = new DataManagerObject();
		ValueObject [] vos = dmo.queryByWhere(sys_attach.class, 
				"ObjID=(select DocVerID from doc_ver where DocID="+docid
				+" and IsLatest='1') and AttachCate='DOC'");
		
		if(vos.length>0) {
			sys_attach attach = (sys_attach)vos[0];
			FlashPaperUtil.convertFileToSwf(attach.getAddTime().substring(0, 10), 
					attach.getAttachID(), attach.getFileType());
		} else {
			throw new Exception("Î´ÕÒµ½¸½¼þ");
		}
	}
	
	public static doc_cate [] getCatesByTreeOrder(doc_cate[] cates) throws Exception {
		
		Vector v = new Vector();
		
		Stack st = new Stack();
		
		scanCatesForTreeOrder(v, st, 0, cates);
		
		doc_cate [] result = new doc_cate[v.size()];
		v.copyInto(result);
		
		return result;
	}
	
	public static doc_cate [] getDocCatePath(int docCateID) throws Exception {
		doc_cate[] cates = null;
		Stack st = new Stack();
		DataManagerObject dmo = new DataManagerObject();
		ValueObject [] vos = dmo.queryByWhere(doc_cate.class, " DocCateID="+docCateID);
		if(vos.length>0) {
			getDocCateRecursive((doc_cate)vos[0], st);
		}
		cates = new doc_cate[st.size()];
		for (int i = 0; i < cates.length; i++) {
			cates[i] = (doc_cate)st.pop();
		}
		return cates;
	}
	
	public static void getDocCateRecursive(doc_cate cate,Stack st) throws Exception {
		st.push(cate);
		DataManagerObject dmo = new DataManagerObject();
		int parentDocCateID = cate.getParentDocCate();
		if(parentDocCateID==0) return;
		ValueObject [] vos = dmo.queryByWhere(doc_cate.class, " DocCateID="+parentDocCateID);
		if(vos.length>0) {
			getDocCateRecursive((doc_cate)vos[0], st);
		}
		
	}
}
