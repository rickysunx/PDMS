package com.pxl.pkb.servlets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;

import com.pxl.pkb.framework.DataManagerObject;
import com.pxl.pkb.framework.Params;
import com.pxl.pkb.framework.ValueObject;
import com.pxl.pkb.vo.sys_attach;
import com.pxl.winservice.common.Utils;

public class GetReader extends HttpServlet {

	public void service(ServletRequest request, ServletResponse response) 
		throws ServletException, IOException {
		
		Params p = Params.getInstance();
		String docid = request.getParameter("docid");
		response.setContentType("application/x-shockwave-flash");
		OutputStream out = response.getOutputStream();
		DataManagerObject dmo = new DataManagerObject();
		Socket s = null;
		OutputStream sout = null;
		InputStream sin = null;
		
		//根据docid查询出attachid
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
			dout.write(Utils.getBytesByLen("GetSwfFile", 50));
			
			//文件名
			dout.write(Utils.getBytesByLen(""+attach.getAttachID(), 255));
			
			byte [] flagBytes = new byte[1];
			din.read(flagBytes);
			String flag = new String(flagBytes);
			if(!flag.equals("1")) {
				out.flush();
				out.close();
				throw new Exception("文件不存在");
			}
			
			//读取文件长度
			long fileSize = 0;
			fileSize = din.readLong();
			response.setContentLength((int)fileSize);
			
			byte [] buff = new byte[512];
			int len = -1;
			while(true) {
				len = din.read(buff);
				if(len<=0) break;
				out.write(buff,0,len);
			}
			out.flush();
			out.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			out.flush();
			out.close();
			throw new ServletException(e.getMessage());
		}
		
		
//		String flashPaperPath = p.getFlashPaperPath();
//		String flashPaperFileName = flashPaperPath + File.separator + docid + ".swf";
//		
//		File f = new File(flashPaperFileName);
//		if(!f.exists()) {
//			out.flush();
//			out.close();
//			throw new ServletException("文件不存在");
//		}
//		response.setContentLength((int)f.length());
//		
//		FileInputStream fin = new FileInputStream(f);
//		byte [] buff = new byte[512];
//		int len = -1;
//		
//		while((len=fin.read(buff))!=-1) {
//			out.write(buff,0,len);
//		}
//		out.flush();
//		out.close();
		
	}

}
