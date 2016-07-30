/**
 *
 * Created on 2009-4-9
 * @author sunrui
 *
 */
package com.pxl.pkb.filters;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.pxl.pkb.biz.Consts;


/**
 * @author sunrui
 *
 */
public class CheckSessionFilter implements Filter {

	protected static HashMap hmExPage = null;
	protected static Object objMutex = new Object();
	protected FilterConfig config = null;
	/**
	 * 
	 */
	
	public CheckSessionFilter() {
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	public void destroy() {

	}
	
	public void loadExPageMap() throws Exception {
		synchronized (objMutex) {
			if(hmExPage==null) {
				hmExPage = new HashMap();
				String strExPageCount = config.getInitParameter("ExPageCount");
				if(strExPageCount!=null) {
					int exPageCount = Integer.parseInt(strExPageCount);
					for (int i = 0; i < exPageCount; i++) {
						String exPageUrl = config.getInitParameter("ExPage"+i);
						hmExPage.put(exPageUrl, null);
					}
				}
			}
		}
	}
	
	public boolean isExcludePath(String path) throws Exception {
		loadExPageMap();
		return hmExPage.containsKey(path);
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		//long t1 = System.currentTimeMillis();
		boolean allowed = false;
		
		try {
			if(request instanceof HttpServletRequest) {
				HttpServletRequest httpRequest = (HttpServletRequest) request;
				String path = httpRequest.getServletPath();
				if(!isExcludePath(path)) {
					HttpSession session = httpRequest.getSession();
					if(session!=null) {
						Object objUser = session.getAttribute(Consts.PKB_USER_SESSION_NAME);
						if(objUser!=null) {
							allowed = true;
						}
					}
				} else {
					allowed=true;
				}
				
				if(path.equalsIgnoreCase("/upload.do")) {
					
					if(request.getContentLength()>300*1024*1024) {
						String cate = request.getParameter("cate");
						request.setAttribute("cate", cate);
						request.setAttribute("error", "文件大小超过系统限制，大小限制300M！");
						request.getInputStream().close();
						request.getRequestDispatcher("/upload.jsp").forward(request, response);
					}
				}
				
			}
			
			if(allowed) {
				chain.doFilter(request, response);
			} else {
				HttpServletRequest httpRequest=(HttpServletRequest)request;
				String oldURL=httpRequest.getRequestURL().toString();
				String uri = httpRequest.getRequestURI();
				if(uri.equalsIgnoreCase("/pkb/") ||
					uri.equalsIgnoreCase("/")) {
					
				} else {
					Map param=httpRequest.getParameterMap();
					if(param!=null&&param.size()!=0){
						oldURL=oldURL+"?";
						for(Iterator iter=param.keySet().iterator();iter.hasNext();){
							String key=iter.next().toString();
							String value=httpRequest.getParameter(key);
							oldURL=oldURL+key+"="+value;
							if(iter.hasNext()){
								oldURL=oldURL+"&";
							}
						}
					}
					httpRequest.getSession().setAttribute("oldURL", oldURL);
				}
				
				request.getRequestDispatcher("/timeout.jsp").forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException(e.getMessage());
		}
		//long t2 = System.currentTimeMillis();
		//HttpServletRequest httpRequest = (HttpServletRequest) request;
		//String path = httpRequest.getServletPath();
		//System.out.println("处理时间："+(t2-t1)+"毫秒 线程["+Thread.currentThread().getName()+"] 页面："+path);
		
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	public void init(FilterConfig filterConfig) throws ServletException {
		config = filterConfig;
	}

}
