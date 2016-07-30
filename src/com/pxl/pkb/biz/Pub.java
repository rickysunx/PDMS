/**
 * 
 */
package com.pxl.pkb.biz;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;

import sun.misc.BASE64Encoder;

import com.pxl.pkb.extendvo.ppm_taskon;
import com.pxl.pkb.framework.DataManagerObject;
import com.pxl.pkb.framework.Params;
import com.pxl.pkb.framework.ValueObject;
import com.pxl.pkb.myvo.NotifyInfo;
import com.pxl.pkb.vo.ask_cate;
import com.pxl.pkb.vo.bd_operanote;
import com.pxl.pkb.vo.bd_user;
import com.pxl.pkb.vo.doc_cate;
import com.pxl.pkb.vo.ppm_project;
import com.pxl.pkb.vo.sys_attach;
import com.pxl.ppm.framework.BeanFactory;
import com.pxl.ppm.itfs.IProject;
import com.pxl.winservice.common.Utils;


/**
 * @author Ricky
 *
 */
public class Pub {

	/**
	 * 
	 */
	public Pub() {
	}
	
	public static doc_cate docCate=null;
	
	public static Map extMap = null;
	
	public static boolean isSwfFileExists(int docid) throws Exception {
		Params p = Params.getInstance();
		DataManagerObject dmo = new DataManagerObject();
		Socket s = null;
		OutputStream sout = null;
		InputStream sin = null;
		try {
			ValueObject [] attachVOs = dmo.queryByWhere(sys_attach.class, 
					"ObjID=(select DocVerID from doc_ver where docid="+docid+" and IsLatest='1') and AttachCate='DOC'");
			sys_attach attach = null;
			if(attachVOs.length==1) {
				attach = (sys_attach)attachVOs[0];
			} else {
				throw new Exception("未找到文档的附件");
			}
			
			//向windows服务器发送获取SWF文件请求
			s = new Socket(p.getFlashPaperServer(),p.getFlashPaperServerPort());
			sout = s.getOutputStream();
			sin = s.getInputStream();
			
			DataOutputStream dout = new DataOutputStream(sout);
			DataInputStream din = new DataInputStream(sin);
			
			//服务名
			dout.write(Utils.getBytesByLen("SwfFileExists", 50));
			
			//文件名
			dout.write(Utils.getBytesByLen(""+attach.getAttachID(), 255));
			
			byte [] flagBytes = new byte[1];
			din.read(flagBytes);
			String flag = new String(flagBytes);
			
			if(flag.equals("1")) {
				return true;
			} else {
				return false;
			}
		} finally {
			try {if(sout!=null) sout.close();} catch (Exception e) {e.printStackTrace();}
			try {if(sin!=null) sin.close();} catch (Exception e) {e.printStackTrace();}
			try {if(s!=null) s.close();} catch (Exception e) {e.printStackTrace();}
		}
	}
	
	public static void fixdb() throws Exception {
		
		System.out.println("===========================开始转换=========================");
		
		String [] exts = new String[] {
			"doc","xls","ppt","docx","xlsx","pptx","pdf","txt","mpp","vsd","htm","html"
		};
		
		extMap = new HashMap();
		for (int i = 0; i < exts.length; i++) {
			extMap.put(exts[i], exts[i]);
		}
		
		
		new Thread() {
			public void run() {
				try {
					convertSwf();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.start();
		
		
		
//		int project_docid = 4025;
//		
//		DataManagerObject dmo = new DataManagerObject();
//		ValueObject [] vos = dmo.queryAll(doc_cate.class);
//		doc_cate [] cates = new doc_cate[vos.length];
//		for (int i = 0; i < cates.length; i++) {
//			cates[i] = (doc_cate)vos[i];
//		}
//		
//		fixProject(project_docid, cates);
		
	}
	
	public static void convertSwf() throws Exception {
		//一次性转换SWF文件
		DataManagerObject dmo = new DataManagerObject();
		ValueObject [] vos = dmo.queryAll(sys_attach.class);
		final sys_attach [] attachs = new sys_attach[vos.length];
		for (int i = 0; i < attachs.length; i++) {
			attachs[i] = (sys_attach)vos[i];
		}
		
		int threadCount = 1; //同时进行转换的线程数
		
		
		for (int i = 0; i < threadCount; i++) {
			final int index = i;
			new Thread() {
				public void run() {
					try {
						convertSwf0(index,attachs);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}
			}.start();
		}
		
	}
	
	public static void convertSwf0(int index,sys_attach [] attachs) throws Exception {
		for (int i = 0; i < attachs.length; i++) {
			if(i%(index+1)==0) {
				System.out.println("==============转换"+(i+1)+"/"+attachs.length+"个文件================");
				try {
					String date = attachs[i].getAddTime().substring(0,10);
					int id = attachs[i].getAttachID();
					String fileExt = attachs[i].getFileType();
					String lowerFileExt = fileExt.toLowerCase();
					if(extMap.containsKey(lowerFileExt)) {
						FlashPaperUtil.convertFileToSwf(date, id, fileExt);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void fixProject(int id,doc_cate [] cates) throws Exception {
		DataManagerObject dmo = new DataManagerObject();
		dmo.updateBySQL("update doc_cate set DocType=2 where DocCateID="+id);
		for (int i = 0; i < cates.length; i++) {
			if(cates[i].getParentDocCate()==id) {
				fixProject(cates[i].getDocCateID(), cates);
			}
		}
		
	}
	
	public static void getCateTree(ValueObject[] vos,JspWriter out,String type) throws IOException{
		out.println("<script type='text/javascript'>");
		out.println("var cates=new Array();");
		out.println("var index=0;");
		out.println("var tree = new WebFXTree('请选择类别');");
		for(int i=0;i<vos.length;i++) {
			if(type.equals("doc_cate")){
				doc_cate cate=(doc_cate)vos[i];
				if(cate.getParentDocCate()==0){
					if(cate.getCanPost().equals("0")){
						out.println("var cate"+cate.getDocCateID()+"=new WebFXTreeItem('"+cate.getDocCateName()+"');");
					}else{
						out.println("var cate"+cate.getDocCateID()+"=new WebFXCheckBoxTreeItem('"+cate.getDocCateName()+"');");
						out.println("cate"+cate.getDocCateID()+".cateId='"+cate.getDocCateID()+"';");
						out.println("cates[index]=cate"+cate.getDocCateID()+";");
						out.println("index++;");
					}
					//out.println("cate"+cate.getDocCateID()+".expand();");
					getTree(vos,out,cate.getDocCateID(),type);
					out.println("tree.add(cate"+cate.getDocCateID()+");");
				}
			}
			 if(type.equals("ask_cate")){
				ask_cate cate=(ask_cate)vos[i];
				if(cate.getParentAskCate()==0){
					if(cate.getCanPost().equals("0")){
						out.println("var cate"+cate.getAskCateID()+"=new WebFXTreeItem('"+cate.getAskCateName()+"');");
					}else{
						out.println("var cate"+cate.getAskCateID()+"=new WebFXCheckBoxTreeItem('"+cate.getAskCateName()+"');");
						out.println("cate"+cate.getAskCateID()+".cateId='"+cate.getAskCateID()+"';");
						out.println("cates[index]=cate"+cate.getAskCateID()+";");
						out.println("index++;");
					}
					//out.println("cate"+cate.getAskCateID()+".expand();");
					getTree(vos,out,cate.getAskCateID(),type);
					out.println("tree.add(cate"+cate.getAskCateID()+");");
				}
			}
			
		}
		out.println("document.write(tree);");
		out.println("tree.expand();");
		out.println("</script>");
	}
	
	public static void getTree(ValueObject[] vos,JspWriter out,int parentNote,String type) throws IOException{
		for(int i=0;i<vos.length;i++) {
			if(type.equals("doc_cate")){
				doc_cate cate=(doc_cate)vos[i];
				if(cate.getParentDocCate()==parentNote){
					if(cate.getCanPost().equals("0")){
						out.println("var cate"+cate.getDocCateID()+"=new WebFXTreeItem('"+cate.getDocCateName()+"');");
					}else{
						out.println("var cate"+cate.getDocCateID()+"=new WebFXCheckBoxTreeItem('"+cate.getDocCateName()+"');");
						out.println("cate"+cate.getDocCateID()+".cateId='"+cate.getDocCateID()+"';");
						out.println("cates[index]=cate"+cate.getDocCateID()+";");
						out.println("index++;");
					}
					//out.println("cate"+cate.getDocCateID()+".expand();");
					getTree(vos,out,cate.getDocCateID(),type);
					out.println("cate"+parentNote+".add(cate"+cate.getDocCateID()+");");
				}
			}
            if(type.equals("ask_cate")){
				ask_cate cate=(ask_cate)vos[i];
				if(cate.getParentAskCate()==parentNote){
					if(cate.getCanPost().equals("0")){
						out.println("var cate"+cate.getAskCateID()+"=new WebFXTreeItem('"+cate.getAskCateName()+"');");
					}else{
						out.println("var cate"+cate.getAskCateID()+"=new WebFXCheckBoxTreeItem('"+cate.getAskCateName()+"');");
						out.println("cate"+cate.getAskCateID()+".cateId='"+cate.getAskCateID()+"';");
						out.println("cates[index]=cate"+cate.getAskCateID()+";");
						out.println("index++;");
					}
					//out.println("cate"+cate.getAskCateID()+".expand();");
					getTree(vos,out,cate.getAskCateID(),type);
					out.println("cate"+parentNote+".add(cate"+cate.getAskCateID()+");");
				}
			}
            if(type.equals("workdetail")){
				ppm_taskon taskon=(ppm_taskon)vos[i];
				if(taskon.getParentTaskID()==parentNote){
                    out.println("var cate"+taskon.getTaskID()+"=new WebFXTreeItem('"+taskon.getTaskTitle()+"');");
			        getTree(vos,out,taskon.getTaskID(),type);
					out.println("cate"+parentNote+".add(cate"+taskon.getTaskID()+");");
				}
			}
		}
	}
	
	public static String getCurrDate() throws Exception {
		return getCurrTime().substring(0, 10);
	}
	
	public static String getEncodedHTML(String str) throws Exception {
		StringBuffer encoded = new StringBuffer();
		boolean inTag = false;
		String [] specialStr = new String[]{
				"&nbsp;","&ldquo;","&rdquo;","&rarr;","&mdash;",
				"&iexcl;",
				"&Aacute;",
				"&aacute;",
				"&cent;",
				"&circ;",
				"&acirc;",
				"&pound;",
				"&Atilde;",
				"&atilde;",
				"&curren;",
				"&Auml;",
				"&auml;",
				"&yen;",
				"&ring;",
				"&aring;",
				"&brvbar;",
				"&AElig;",
				"&aelig; ",
				"&sect;",
				"&Ccedil;",
				"&ccedil;",
				"&uml;",
				"&Egrave;",
				"&egrave;",
				"&copy;",
				"&Eacute;",
				"&eacute;",
				"&ordf;",
				"&Ecirc;",
				"&ecirc;",
				"&laquo;",
				"&Euml;",
				"&euml;",
				"&not;",
				"&Igrave;",
				"&igrave;",
				"&shy;",
				"&Iacute;",
				"&iacute;",
				"&reg;",
				"&Icirc;",
				"&icirc;",
				"&macr;",
				"&Iuml;",
				"&iuml;",
				"&deg;",
				"&ETH;",
				"&ieth;",
				"&plusmn;",
				"&Ntilde;",
				"&ntilde;",
				"&sup2;",
				"&Ograve;",
				"&ograve;",
				"&sup3;",
				"&Oacute;",
				"&oacute;",
				"&acute;",
				"&Ocirc;",
				"&ocirc;",
				"&micro;",
				"&Otilde;",
				"&otilde;",
				"&para;",
				"&Ouml;",
				"&ouml;",
				"&middot;",
				"&times;",
				"&times;",
				"&divide;",
				"&cedil;",
				"&Oslash;",
				"&oslash;",
				"&sup1;",
				"&Ugrave;",
				"&ugrave;",
				"&ordm;",
				"&Uacute;",
				"&uacute;",
				"&raquo;",
				"&Ucirc;",
				"&ucirc;",
				"&frac14;",
				"&Uuml;",
				"&uuml;",
				"&frac12;",
				"&Yacute;",
				"&yacute;",
				"&frac34;",
				"&THORN;",
				"&thorn;",
				"&iquest;",
				"&szlig;",
				"&yuml;",
				"&Agrave;",
				"&agrave;"
		};
		
		for (int i = 0; i < str.length(); i++) {
			
			boolean replaced = false;
			for (int j = 0; j < specialStr.length; j++) {
				if(i+specialStr[j].length()<str.length()) {
					if(str.substring(i, i+specialStr[j].length()).endsWith(specialStr[j])) {
						i+=specialStr[j].length()-1;
						encoded.append(specialStr[j]);
						replaced = true;
					}
				}
			}
			
			if(!replaced) {
				if(str.charAt(i)=='<') inTag = true;
				if(inTag) {
					encoded.append(str.charAt(i));
				} else {
					encoded.append("&#"+(int)str.charAt(i));
				}
				if(str.charAt(i)=='>') inTag = false;
			}
		}
		return encoded.toString();
	}
	
	public static void refreshQst(int qstID) throws Exception {
		NotifyInfo info = new NotifyInfo();
		info.setInfo("doQstRefresh("+qstID+");");
		info.setType(NotifyInfo.TYPE_ASK_REFRESH);
		NotifyManager.getInstance().addNotify(info);
	}
	
    public static String encodeByMD5(String str) throws Exception{
        //确定计算方法
        MessageDigest md5=MessageDigest.getInstance("MD5");
        BASE64Encoder base64en = new BASE64Encoder();
        //加密后的字符串
        String newstr=base64en.encode(md5.digest(str.getBytes("utf-8")));
        return newstr;
    }
	
	public static String getUploadPath() throws Exception {
		return Params.getInstance().getUploadPath();
	}
	
	public static String getBatchUploadFilepath(){
		return Params.getInstance().getBatchUploadPath();
	}
	
	public static String showKeyword(String content,String keyword) throws Exception {
		String text = "";
		int fromIndex = 0;
		
		while(true) {
			int index = content.indexOf(keyword, fromIndex);
			if(index==-1) break;
			
			int start = index-30;
			start = (start>=0)?start:0;
			int end = index+30;
			end = (end<content.length())?end:content.length();
			
			text+=content.substring(start, end)+"...";
			
			fromIndex = index+1;
		}
		
		text = text.replaceAll(keyword, "<font color='#FF0000'>"+keyword+"</font>");
		
		if(text.length()>300) {
			text = text.substring(0, 300);
		}
		return text;
	}
	
	public static String getFullFileName(String fileName,String ext) throws Exception {
		return fileName+((ext!=null)?("."+ext):"");
	}
	
	public static String getCurrTime() throws Exception {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = format.format(cal.getTime());
		
		return str;
	}
	
	
	
	public static void main(String [] args) {
		try {
//			String [] str = getFileNameAndExt("123.22.bmp");
//			System.out.println(str[0]);
//			System.out.println(str[1]);
			
			System.out.println(encodeByMD5("1"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String [] getFileNameAndExt(String fileName) throws Exception {
		String [] fileNameAndExt = new String[2];
		int dotindex = fileName.lastIndexOf(".");
		if(dotindex==-1) {
			fileNameAndExt[0] = fileName;
			fileNameAndExt[1] = "";
		} else {
			fileNameAndExt[0] = fileName.substring(0, dotindex);
			fileNameAndExt[1] = fileName.substring(dotindex+1);
		}
		return fileNameAndExt;
	}
	
	public static String getSpace(int n) throws Exception {
		String space = "";
		for(int i=0;i<n;i++) {
			space+="　";
		}
		return space;
	}
	
	public static String getCateOption(String table,String id,String disp,String parent,int selected) throws Exception {
		StringBuffer sb = new StringBuffer();
		DataManagerObject dmo = new DataManagerObject();
		String sql = "select "+id+","+parent+","+disp+" from "+table;
		Object [][] data = dmo.querySQL(sql);
		int [] depth = new int[]{0};
		
		scanCateOption(sb, data, 0, depth, selected);
		
		return sb.toString();
	}
	
	protected static void scanCateOption(StringBuffer sb,Object[][] data,int parentid,int[] depth,int selected) throws Exception {
		for (int i = 0; i < data.length; i++) {
			if( ((Integer)data[i][1]).intValue()==parentid) {
				int id = ((Integer)data[i][0]).intValue();
				sb.append("<option value='"+id+"'"+((selected==id)?" selected":"")+">"+getSpace(depth[0])+data[i][2]+"</option>");
				depth[0]++;
				scanCateOption(sb, data, ((Integer)data[i][0]).intValue(), depth, selected);
				depth[0]--;
			}
		}
	}
	
	public static String text2html(String text) throws Exception {
		if(text==null) return null;
		String html = text;
		html = html.replaceAll("&", "&amp;");
		html = html.replaceAll("<", "&lt;");
		html = html.replaceAll(">", "&gt;");
		html = html.replaceAll("\"", "&quot;");
		html = html.replaceAll("\r\n", "<br>\r\n");
		html = html.replaceAll(" ", "&nbsp;");
		return html;
	}
	
	public static String text2htmlNoBR(String text) throws Exception {
		if(text==null) return null;
		String html = text;
		html = html.replaceAll("&", "&amp;");
		html = html.replaceAll("<", "&lt;");
		html = html.replaceAll(">", "&gt;");
		html = html.replaceAll("\"", "&quot;");
		html = html.replaceAll(" ", "&nbsp;");
		return html;
	}

	public static void addOperaNote(HttpServletRequest request,String operaType,int userID) throws Exception{
		bd_operanote operaNote = new bd_operanote();
		operaNote.setIPAddress(request.getRemoteHost());
		operaNote.setOperaType(operaType);
		operaNote.setUserID(userID);
		operaNote.setOperaTime(getCurrTime());
		
		DataManagerObject dmo = new DataManagerObject();
		dmo.insert(operaNote);
	}
	
	public static boolean isUnCheckUser(HttpSession session) throws Exception {
		bd_user user = (bd_user)session.getAttribute(Consts.PKB_USER_SESSION_NAME);
		if(user.getIsAdmin().equals(Consts.ROLE_UNAPPROVED)) return true;
		return false;
	}
	
	public static boolean isAdminUser(HttpSession session) throws Exception {
		bd_user user = (bd_user)session.getAttribute(Consts.PKB_USER_SESSION_NAME);
		if(user.getIsAdmin().equals(Consts.ROLE_ADMIN)) return true;
		return false;
	}
	
	/**
	 * 获取工作日志的文件
	 * 以树状结构显示的
	 */
   public  static List<ppm_taskon> getTaskon(ppm_taskon[] taskon){
	   
	   if(taskon!=null){
		   List<ppm_taskon> list = new ArrayList<ppm_taskon>();
		   for(int i=0;i<taskon.length;i++){
			   if(taskon[i].getParentTaskID()==0&&taskon[i].getFinishedPercent()!=100){
				    list.add(taskon[i]);
				    getTaskon2(taskon,taskon[i].getTaskID(),list);
			   }
		   }
	      for(int i=0;i<list.size();i++){
	    	  ppm_taskon taskons = list.get(i);
	    	  System.out.println(taskons.getTaskTitle());
	      }
		   return list;
	   }else{
		   return null;
	   }
   }
   public static void getTaskon2(ppm_taskon[] taskon,int index,List<ppm_taskon> list){
	   if(taskon!=null){
		   for(int i=0;i<taskon.length;i++){
			   if(taskon[i].getParentTaskID()==index){
				   list.add(taskon[i]);
				   getTaskon2(taskon,taskon[i].getTaskID(),list);
			   }
		   }
	   }
   }
  public static boolean getTaskon3(ppm_taskon[] taskon,int index){
	  List<ppm_taskon> list = new ArrayList<ppm_taskon>();
	  if(taskon!=null){
		  for(int i=0;i<taskon.length;i++){
			   if(taskon[i].getParentTaskID()==index){
				   list.add(taskon[i]);
			   }
		   }
	  }
	  if(list!=null&&list.size()==0){
		  return true;
	  }else{
		  return false;
	  }
  }
  public static boolean getTaskon4(ppm_taskon[] taskon,int taskId){
	  int count=0;
	  if(taskon!=null){
		  for(int i=0;i<taskon.length;i++){
			  if((taskon[i].getParentTaskID()==taskId)&&taskon[i].getFinishedPercent()!=100){
				 count++;
			  }
		  }
	  }
	  if(count==0){
		  return false;
	  }else{
		  return true;
	  }
  }
  
  
  public  static List<ppm_taskon> getTaskon5(ppm_taskon[] taskon) throws Exception{
	   if(taskon!=null){
		   List<ppm_taskon> list = new ArrayList<ppm_taskon>();
		   for(int i=0;i<taskon.length;i++){
			    		if(taskon[i].getParentTaskID()==0){
			    			list.add(taskon[i]);
			    			getTaskon2(taskon,taskon[i].getTaskID(),list); 
			    }
		   }
	      for(int i=0;i<list.size();i++){
	    	  ppm_taskon taskons = list.get(i);
	    	  System.out.println(taskons.getTaskTitle());
	      }
		   return list;
	   }else{
		   return null;
	   }
  }
  
}
