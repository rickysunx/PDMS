package com.pxl.pkb.biz;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Vector;

import com.pxl.pkb.framework.DataManagerObject;
import com.pxl.pkb.framework.PxlOutputStream;
import com.pxl.pkb.framework.ValueObject;
import com.pxl.pkb.vo.bd_user;
import com.pxl.pkb.vo.doc_cate;
import com.pxl.pkb.vo.doc_doc;
import com.pxl.pkb.vo.doc_ver;
import com.pxl.pkb.vo.sys_attach;

public class BatchImportThread extends Thread {

	public static boolean importing = false;
	public static String currUserCode = null;
	public static int userIndex = 0;
	public static int userCount = 0;
	public static int fileIndex = 0;
	public static int fileCount = 0;
	public static String currInfo = "";
	public static StringBuffer strLog = new StringBuffer();
	public static final String UPLOADED_EXT_NAME = ".uploaded";
	
	public String [] userCodes = null;
	public doc_cate[] docCates = null;
	public String batchUploadPath = null;
	public String ip = null;
	
	public BatchImportThread(String [] userCodes,doc_cate[] docCates,String batchUploadPath,String ip) {
		super();
		this.userCodes = userCodes;
		this.docCates = docCates;
		this.batchUploadPath = batchUploadPath;
		this.ip = ip;
	}
	
	public void log(String str) throws Exception {
		currInfo = str;
		String mystr = Pub.getCurrTime()+" "+str;
		strLog.append(mystr+"\r\n");
		System.out.println(mystr);
	}
	
	public File [] getFileList(String userCode) throws Exception {
		Vector v = new Vector();
		File f = new File(batchUploadPath+userCode);
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
			if(!f.getName().endsWith(UPLOADED_EXT_NAME)) {
				v.add(f);
			}
		}
	}
	
	public void createCates(String userCode,Vector vCates) throws Exception {
		File f = new File(batchUploadPath+userCode);
		HashMap map = getCatePathMap(vCates);
		scanForCreateCates(f, userCode, vCates, map);
	}
	
	public void scanForCreateCates(File f,String userCode,Vector vCates,HashMap map) throws Exception {
		DataManagerObject dmo = new DataManagerObject();
		String userPath = batchUploadPath+userCode;
		if(f.isDirectory()) {
			String aPath = f.getAbsolutePath();
			String catePath = aPath.substring(userPath.length());
			if(catePath.length()>0) {
				if(!map.containsKey(catePath)) {
					int index = catePath.lastIndexOf(File.separator);
					String parentPath = catePath.substring(0,index);
					int pid = 0;
					doc_cate pCate = null;
					if(parentPath.length()>0) {
						Object obj = map.get(parentPath);
						if(obj==null) throw new Exception("未找到上级目录");
						pCate = (doc_cate) obj;
						pid = pCate.getDocCateID();
					}
					log("创建类别："+catePath);
					
					doc_cate newCate = new doc_cate();
					newCate.setDocCateName(f.getName());
					newCate.setDocCount(0);
					newCate.setCanPost("1");
					newCate.setParentDocCate(pid);
					newCate.setDocType(pCate!=null?pCate.getDocType():"0");
					int cateid = dmo.insert(newCate);
					newCate.setDocCateID(cateid);
					vCates.add(newCate);
					map.put(catePath, newCate);
					
				}
			}
			
			File [] files = f.listFiles();
			for (int i = 0; i < files.length; i++) {
				scanForCreateCates(files[i], userCode, vCates, map);
			}
		}
	}
	
	//获取类别全路径的HashMap
	public HashMap getCatePathMap(Vector vCates) throws Exception {
		String catePath = "";
		HashMap map = new HashMap();
		scanCatePath(null, catePath, vCates, map);
		return map;
	}
	
	public void scanCatePath(doc_cate pCate,String catePath,Vector vCates,HashMap map) throws Exception {
		int pid = (pCate==null)?0:pCate.getDocCateID();
		for (int i = 0; i < vCates.size(); i++) {
			doc_cate cate = (doc_cate)vCates.get(i);
			if(cate.getParentDocCate()==pid) {
				scanCatePath(cate, catePath+File.separator+cate.getDocCateName(), vCates, map);
			}
		}
		if(pCate!=null) map.put(catePath, pCate);
	}
	
	
	public void impFile(File f,HashMap map,Connection conn,int userid,String userCode,String ip) throws Exception {
		DataManagerObject dmo = new DataManagerObject();
		
		String currTime = Pub.getCurrTime();
		
		//获取对应类别
		String userPath = batchUploadPath+userCode;
		String aPath = f.getParent();
		String catePath = aPath.substring(userPath.length());
		
		Object objCate = map.get(catePath);
		if(objCate==null) throw new Exception("未找到类别："+catePath);
		doc_cate cate = (doc_cate)objCate;
		
		
		//插入附件信息
		sys_attach attach = new sys_attach();
		attach.setAttachCate("DOC");
		attach.setAddUser(userid);
		attach.setAddIP(ip);
		attach.setAddTime(currTime);
		String [] fileNameAndExt = Pub.getFileNameAndExt(f.getName());
		attach.setFileName(fileNameAndExt[0]);
		attach.setFileType(fileNameAndExt[1]);
		int attachID = dmo.insert(attach);
		attach.setAttachID(attachID);
		
		//导入附件
		String path = Pub.getUploadPath()+File.separator+"DOC"; 
		String filePath = path+File.separator+attach.getAddTime().substring(0,10);
		String fileFullPath = filePath+File.separator+attachID;
		File fPath = new File(filePath);
		fPath.mkdirs();
		OutputStream output = null;
		InputStream fin = null;
		try {
			output = new PxlOutputStream(fileFullPath);
			fin = new FileInputStream(f);
			byte [] buff = new byte[512];
			int len;
			while((len=fin.read(buff))>=0) {
				output.write(buff, 0, len);
			}
		} finally {
			if(output!=null) {
				try{output.flush();} catch(Exception e){}
				try{output.close();} catch(Exception e){}
			}
			if(fin!=null) {
				try{fin.close();} catch(Exception e){}
			}
		}
		
		//生成文档
		doc_doc doc = new doc_doc();
		doc.setDocCateID(cate.getDocCateID());
		doc.setDocTitle(f.getName());
		doc.setDocIntro("");
		doc.setDocKeyword("");
		doc.setAddTime(currTime);
		doc.setAddUser(userid);
		doc.setUpdateTime(currTime);
		doc.setDownloadCount(0);
		doc.setClickCount(0);
		doc.setDocType(fileNameAndExt[1]);
		int docid = dmo.insert(doc);
		doc.setDocID(docid);
		
		//创建版本
		doc_ver docVer = new doc_ver();
		docVer.setAddIP(ip);
		docVer.setAddTime(currTime);
		docVer.setAddUser(userid);
		docVer.setDocID(docid);
		docVer.setDocVerName("初始版本");
		docVer.setDocText("");
		docVer.setIsLatest("1");
		int docVerID = dmo.insert(docVer);
		docVer.setDocVerID(docVerID);
		
		//设置文档和附件关联
		attach.setObjID(docVerID);
		dmo.update(attach);
		
		if(cate.getDocType()!=null && cate.getDocType().equals(Consts.DOC_TYPE_PROJECT)){
			User.userScoreAdd(userid,"上传项目文档:"+docid, 100);
		} else {
			User.userScoreAdd(userid,"上传文档:"+docid,20);
		}
		
		//提取文本
		String text = TextExtracter.getTextFromFile(fileFullPath, attach.getFileType());
		docVer.setDocText(text);
		dmo.update(docVer);
		
		//修改后缀名
		f.renameTo(new File(f.getAbsoluteFile()+UPLOADED_EXT_NAME));
		
	}
	
	public void run() {
		Connection conn = null;
		strLog = new StringBuffer();
		try {
			userIndex = -1;
			userCount = userCodes.length;
			fileIndex = -1;
			fileCount = 0;
			DataManagerObject dmo = new DataManagerObject();
			conn = DataManagerObject.getConnection();
			conn.setAutoCommit(false);
			
			Vector vCates = new Vector();
			for (int i = 0; i < docCates.length; i++) {
				vCates.add(docCates[i]);
			}
			
			
			HashMap hmUser = new HashMap();
			ValueObject [] uservos = dmo.queryAll(bd_user.class);
			for (int i = 0; i < uservos.length; i++) {
				bd_user user = (bd_user)uservos[i];
				hmUser.put(user.getUserCode(), user);
			}
			
			
			for (int i = 0; i < userCodes.length; i++) {
				userIndex = i;
				currUserCode = userCodes[i];
				log("开始导入用户："+userCodes[i]);
				Object objUser = hmUser.get(userCodes[i]);
				if(objUser==null) throw new Exception("未找到用户["+userCodes[i]+"]相对应的记录");
				bd_user user = (bd_user)objUser;
				
				//创建类别
				createCates(userCodes[i], vCates);
				conn.commit();
				
				//查找要导入文件列表
				File [] files = getFileList(userCodes[i]);
				fileIndex = -1;
				fileCount = files.length;
				
				HashMap map = getCatePathMap(vCates);
				
				//导入文件
				for (int j = 0; j < files.length; j++) {
					try {
						log("导入文件："+files[j].getName());
						fileIndex = j;
						impFile(files[j], map, conn,user.getUserID(),userCodes[i] ,ip);
						conn.commit();
					} catch (Exception e) {
						e.printStackTrace();
						log(e.getMessage());
						conn.rollback();
					}
				}
				
			}
			
			log("批量导入成功完成");
		} catch (Throwable th) {
			th.printStackTrace();
		} finally {
			importing = false;
			try {
				if(conn!=null) {
					try {conn.commit();} catch (Exception e) {}
					try {conn.close();} catch (Exception e) {}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}

	public doc_cate[] getDocCates() {
		return docCates;
	}

	public void setDocCates(doc_cate[] docCates) {
		this.docCates = docCates;
	}

	public String[] getUserCodes() {
		return userCodes;
	}

	public void setUserCodes(String[] userCodes) {
		this.userCodes = userCodes;
	}

}
