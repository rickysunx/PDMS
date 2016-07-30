package com.pxl.pkb.vo;
import com.pxl.pkb.framework.ValueObject;
public class ppm_taskcomment extends ValueObject {

	public int CommentID = 0;
	public int TaskID = 0;
	public int CommentUserID = 0;
	public String CommentContent = null;
	public String CommentTime = null;

	public int getCommentID() {
		return CommentID;
	}

	public void setCommentID(int CommentID) {
		this.CommentID = CommentID;
	}

	public int getTaskID() {
		return TaskID;
	}

	public void setTaskID(int TaskID) {
		this.TaskID = TaskID;
	}

	public int getCommentUserID() {
		return CommentUserID;
	}

	public void setCommentUserID(int CommentUserID) {
		this.CommentUserID = CommentUserID;
	}

	public String getCommentContent() {
		return CommentContent;
	}

	public void setCommentContent(String CommentContent) {
		this.CommentContent = CommentContent;
	}

	public String getCommentTime() {
		return CommentTime;
	}

	public void setCommentTime(String CommentTime) {
		this.CommentTime = CommentTime;
	}

	public String getTableName(){
		return "ppm_taskcomment";
	}
	public String[] getFieldNames(){
		return new String[]{"CommentID","TaskID","CommentUserID","CommentContent","CommentTime"};
	}
	public String[] getFieldTypeNames(){
		return new String[]{"INT","INT","INT","VARCHAR","CHAR"};
	}
	public String getPrimaryKey(){
		return "CommentID";
	}
}
