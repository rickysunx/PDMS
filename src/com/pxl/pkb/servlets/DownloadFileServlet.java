/**
 * 
 */
package com.pxl.pkb.servlets;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.pxl.pkb.biz.Consts;
import com.pxl.pkb.biz.Pub;
import com.pxl.pkb.framework.DataManagerObject;
import com.pxl.pkb.framework.PxlInputStream;
import com.pxl.pkb.framework.ValueObject;
import com.pxl.pkb.vo.bd_user;
import com.pxl.pkb.vo.sys_attach;
import com.pxl.ppm.framework.BeanFactory;
import com.pxl.ppm.itfs.IOutput;

/**
 * @author Ricky
 *
 */
public class DownloadFileServlet extends HttpServlet {
	IOutput odom =null;
	public DownloadFileServlet(){
		if(null==odom){
			try {
				odom = (IOutput) BeanFactory.getBean("Output");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 */

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			ValueObject [] attachs=null;
			String outputid = request.getParameter("outputid").trim();
			if(null!=outputid&&!"".equals(outputid)){
			     attachs =odom.queryByOutID(Integer.parseInt(outputid));
			     HttpSession session = request.getSession();
					bd_user user = (bd_user)session.getAttribute(Consts.PKB_USER_SESSION_NAME);
					if(user.getIsAdmin().equals("4")) {
						throw new Exception("其他用户无法下载附件");
					}
			}else{
				String strAttachID = request.getParameter("attachid");
				int attachID = Integer.parseInt(strAttachID);
				DataManagerObject dmo = new DataManagerObject();
				HttpSession session = request.getSession();
				bd_user user = (bd_user)session.getAttribute(Consts.PKB_USER_SESSION_NAME);
				if(user.getIsAdmin().equals("4")) {
					throw new Exception("其他用户无法下载附件");
				}
				 attachs = dmo.queryByWhere(sys_attach.class, " AttachID="+attachID);
			}
			System.out.println(attachs+"-----------------搞定-----------------");
			if(attachs!=null&&!attachs.equals("")&&attachs.length!=0){
			for(int i=0;i<attachs.length;i++) {
				sys_attach attach = (sys_attach)attachs[i];
				//HttpSession session = ((HttpServletRequest)request).getSession();
				//ServletContext application = session.getServletContext();
				String uploadPath = Pub.getUploadPath();//application.getRealPath("META-INF/upload/");
				String filePath = uploadPath+File.separator+attach.getAttachCate()+File.separator+
					attach.getAddTime().substring(0,10)+File.separator+attach.getAttachID();
				File f = new File(filePath);
				
				if(f.exists()) {
					
					PxlInputStream input1 = null;
					long fileSize = 0;
					int len;
					byte [] buff = new byte[1024];
					
					try {
						input1 = new PxlInputStream(filePath);
						fileSize = input1.getFileSize();
						input1.read(buff);
					} catch(Exception e) {
						if(e.getMessage()!=null && e.getMessage().equalsIgnoreCase("Given final block not properly padded")) {
							throw new Exception("文档校验出错，文档可能已经损坏");
						}
					} finally {
						try {
							if(input1!=null)
								input1.close();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					
					response.setContentType("application/x-download");
					String fileName = Pub.getFullFileName(attach.getFileName(), attach.getFileType());
					String fileDisplay = URLEncoder.encode(fileName,"UTF-8");
					response.addHeader("Content-Disposition","attachment;filename=" + fileDisplay);
					response.setContentLength((int)fileSize);
					
					OutputStream output = response.getOutputStream();
					InputStream input = new PxlInputStream(filePath);
					
					
					while((len=input.read(buff))>0) {
						try {
							output.write(buff, 0, len);
						} catch (Exception e) {
							break;
						}
					}
					
					try {
						output.flush();
					} catch (Exception e) {
					}
					
					try {
						input.close();
					} catch (Exception e) {
					}
					
				} else {
					throw new Exception("未找到要下载的附件");
				}
				
			} 
			}else {
				throw new Exception("未找到要下载的附件");
				
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errmsg", e.getMessage());
			request.getRequestDispatcher("/errmsg.jsp").forward(request, response);
		}
		
	}


}
