package com.pxl.pkb.struts.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.pxl.pkb.biz.Pub;
import com.pxl.pkb.framework.DataManagerObject;
import com.pxl.pkb.framework.PkbAction;
import com.pxl.pkb.myvo.SearchItem;

public class SearchAction extends PkbAction {

	public ActionForward pkbExecute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		
		String keyword=request.getParameter("keyword");
		
		String searchType=request.getParameter("searchType");
		if(keyword!=null && keyword.trim().length()>0) {
			DataManagerObject dmo = new DataManagerObject();
			
			String sql = "";
			searchType=searchType==null?"0":searchType;
			
			if(searchType.equals("1")){
				sql+="select distinct q.QstID,'QST',q.QstTitle from ";
				sql+="ask_qst q,ask_answer a where q.QstID=a.QstID and                                        ";
				sql+="concat(q.QstTitle,q.QstContent,q.QstAddition,a.AnswerContent) like '%"+keyword+"%'      ";
				sql+="union                                                                              ";
				sql+="select distinct q.QstID,'QST',q.QstTitle from ask_qst q    where                       ";
				sql+="concat(q.QstTitle,q.QstContent,q.QstAddition) like '%"+keyword+"%'                      ";
			}else if(searchType.equals("2")){
				sql+="select distinct d.DocID,'DOC',d.DocTitle from                       ";
				sql+="doc_ver v,doc_doc d                                                                     ";
				sql+="where v.DocID=d.DocID and v.IsLatest='1'                                                ";
				sql+="and concat(v.DocText,d.DocTitle,d.DocIntro) like '%"+keyword+"%'";
			}else{
				sql+="select distinct q.QstID,'QST',q.QstTitle from ";
				sql+="ask_qst q,ask_answer a where q.QstID=a.QstID and                                        ";
				sql+="concat(q.QstTitle,q.QstContent,q.QstAddition,a.AnswerContent) like '%"+keyword+"%'      ";
				sql+="union                                                                              ";
				sql+="select distinct q.QstID,'QST',q.QstTitle from ask_qst q    where                       ";
				sql+="concat(q.QstTitle,q.QstContent,q.QstAddition) like '%"+keyword+"%'                      ";
				sql+="union                                                                         ";
				sql+="select distinct d.DocID,'DOC',d.DocTitle from                       ";
				sql+="doc_ver v,doc_doc d                                                                     ";
				sql+="where v.DocID=d.DocID and v.IsLatest='1'                                                ";
				sql+="and concat(v.DocText,d.DocTitle,d.DocIntro) like '%"+keyword+"%'";
			}
			Object [][] dataTotal = dmo.querySQL(sql);
			
			String strCurrentPage=request.getParameter("currentPage");
			int currentPage =strCurrentPage==null?1:Integer.parseInt(strCurrentPage);
			int totalPage=(int)Math.ceil(dataTotal.length/20.0);
			
			int strartLine=(currentPage-1)*20;
			Object [][] data = dmo.querySQL(sql+" limit "+strartLine+",20");
			
			request.setAttribute("currentPage",currentPage+"");
			request.setAttribute("totalPage", totalPage+"");
			request.setAttribute("keyword", keyword);
			
			SearchItem [] items = new SearchItem[data.length];
			for (int i = 0; i < items.length; i++) {
				SearchItem item = new SearchItem();
				items[i] = item;
				
				item.setTitle((String)data[i][2]);
				String type = (String)data[i][1];
				int id = ((Integer)data[i][0]).intValue();
				String content = "";
				if(type.equalsIgnoreCase("DOC")) {
					item.setUrl("doc_detail.jsp?docid="+id);
					
					String sqlDoc = "select concat(v.DocText,d.DocTitle,d.DocIntro) from doc_ver v,doc_doc d " +
							"where v.DocID=d.DocID and v.IsLatest='1' and d.DocID="+id;
					
					Object [][] docData = dmo.querySQL(sqlDoc);
					content = (String)docData[0][0];
					
				}
				if(type.equalsIgnoreCase("QST")) {
					item.setUrl("ask_qst_view.jsp?qstid="+id);
					
					String sqlQst = "select concat(q.QstTitle,q.QstContent,q.QstAddition,a.AnswerContent) from ask_qst q,ask_answer a " +
						"where q.QstID=a.QstID and q.QstID="+id;
					
					Object [][] qstData = dmo.querySQL(sqlQst);
					
					if(qstData.length==0) {
					
						String sqlQst1 = "select concat(q.QstTitle,q.QstContent,q.QstAddition) from ask_qst q " +
							"where q.QstID="+id;
						
						qstData = dmo.querySQL(sqlQst1);
					}
					
					
					content = (String)qstData[0][0];
				}
				
				item.setContent(Pub.showKeyword(content, keyword));
			}
			
			request.setAttribute("items", items);
			request.setAttribute("searchType", searchType);
			
		} else {
			request.setAttribute("currentPage",1+"");
			request.setAttribute("totalPage", 0+"");
			request.setAttribute("keyword", keyword);
			request.setAttribute("items", new SearchItem[0]);
			request.setAttribute("searchType", searchType);
		}
		
		return mapping.findForward("success");
	}

}
