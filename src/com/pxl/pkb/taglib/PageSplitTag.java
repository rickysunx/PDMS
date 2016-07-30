package com.pxl.pkb.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

public class PageSplitTag extends TagSupport {

	private int totalPage;

	private int currentPage;

	private String url;
	
	private String symbl;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int doStartTag() throws JspException {
		JspWriter out=pageContext.getOut();
		//判断传入路径是否含有参数
		if(url.indexOf("?")==-1){
			symbl="?";
		}else{
			symbl="&";
		}

		if (currentPage <= 1) {

		} else {
			try {
				out.println("<a href='"+url+symbl+"currentPage=1'>首页</a>");
				out.println("<a href='"+url+symbl+"currentPage="+(currentPage-1)+"'>上一页</a>");
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
			// <a href="notice.jsp?currentPage=1">首页</a>&nbsp;&nbsp;
			// <a href="notice.jsp?currentPage=<%=currentPage-1%>">上一页</a>
		}

		if (currentPage - 5 >= 1) {
			for (int i = currentPage - 5; i < currentPage; i++) {
				try {
					out.println("<a href='"+url+symbl+"currentPage="+i+"'>"+i+"</a>");
				} catch (IOException e) {
					System.out.println(e.getMessage());
				}
				// <a href="notice.jsp?currentPage=<%=i%>">i</a>
			}
		} else {
			for (int i = 1; i < currentPage; i++) {
				try {
					out.println("<a href='"+url+symbl+"currentPage="+i+"'>"+i+"</a>");
				} catch (IOException e) {
					System.out.println(e.getMessage());
				}
				// <a href="notice.jsp?currentPage=<%=i%>">i</a>
			}
		}
		int count=0;
		for (int i = currentPage; i <= totalPage; i++) {
			int j = 1;
			if (j <= 4) {
				if (i == currentPage) {
					try {
						out.println("<a href='"+url+symbl+"currentPage="+i+"'><span style='background-color:#E6F4D0;color:red;'>"+i+"</span></a>");
					} catch (IOException e) {
						System.out.println(e.getMessage());
					}
					// <a href="notice.jsp?currentPage=<%=i%>"><span style="background-color:#E6F4D0;color:red;">=i</span></a>
				} else {
					try {
						out.println("<a href='"+url+symbl+"currentPage="+i+"'>"+i+"</a>");
					} catch (IOException e) {
						System.out.println(e.getMessage());
					}
					// <a href="notice.jsp?currentPage=<%=i%>"><%=i%></a>
				}
			}
			if(count>4)
				break;
			else
				count++;
			
		}
		if (currentPage >= totalPage) {

		} else {
			try {
				out.println("<a href='"+url+symbl+"currentPage="+(currentPage+1)+"'>下一页</a>");
				out.println("<a href='"+url+symbl+"currentPage="+totalPage+"'>尾页</a>&nbsp;");
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
			// <a href="notice.jsp?currentPage=<%=currentPage+1%>">下一页</a>
			// <a href="notice.jsp?currentPage=<%=totalPage%>">尾页</a>
		}
		
		try {
			out.println("当前第"+currentPage+"页/共"+totalPage+"页");
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return Tag.SKIP_BODY;
	}

	public int doEndTag() throws JspException {
		return Tag.EVAL_PAGE;
	}
}
