package com.pxl.pkb.vo;
import com.pxl.pkb.framework.ValueObject;
public class doc_cate extends ValueObject {

	public int DocCateID = 0;
	public String DocCateName = null;
	public int ParentDocCate = 0;
	public String CanPost = null;
	public int DocCount = 0;
	public String DocType = null;

	public int getDocCateID() {
		return DocCateID;
	}

	public void setDocCateID(int DocCateID) {
		this.DocCateID = DocCateID;
	}

	public String getDocCateName() {
		return DocCateName;
	}

	public void setDocCateName(String DocCateName) {
		this.DocCateName = DocCateName;
	}

	public int getParentDocCate() {
		return ParentDocCate;
	}

	public void setParentDocCate(int ParentDocCate) {
		this.ParentDocCate = ParentDocCate;
	}

	public String getCanPost() {
		return CanPost;
	}

	public void setCanPost(String CanPost) {
		this.CanPost = CanPost;
	}

	public int getDocCount() {
		return DocCount;
	}

	public void setDocCount(int DocCount) {
		this.DocCount = DocCount;
	}

	public String getDocType() {
		return DocType;
	}

	public void setDocType(String DocType) {
		this.DocType = DocType;
	}

	public String getTableName(){
		return "doc_cate";
	}
	public String[] getFieldNames(){
		return new String[]{"DocCateID","DocCateName","ParentDocCate","CanPost","DocCount","DocType"};
	}
	public String[] getFieldTypeNames(){
		return new String[]{"INT","VARCHAR","INT","CHAR","INT","CHAR"};
	}
	public String getPrimaryKey(){
		return "DocCateID";
	}
}
