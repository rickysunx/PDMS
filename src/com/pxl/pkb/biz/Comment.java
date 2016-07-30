package com.pxl.pkb.biz;

import com.pxl.pkb.framework.DataManagerObject;

public class Comment {
	public static String getCommentViewLink(int commentType,int objID,String linkClass) throws Exception {
		String link = "<a target='_blank' "+
				(linkClass!=null?("class='"+linkClass+"'"):"")+
				" href='comment.jsp?commenttype="+
			commentType+"&objid="+objID+"'>查看评论(";
		
		//查询评论数量
		DataManagerObject dmo = new DataManagerObject();
		String sql = "select count(1) from bd_comment where CommentType="+
			commentType+" and ObjID = "+objID;
		Object [][] data = dmo.querySQL(sql);
		int count = ((Long)data[0][0]).intValue();
		link+=count+")</a>";
		return link;
	}
}
