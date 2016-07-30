package com.pxl.pkb.biz;

public class Consts {
	public static final String DOC_TYPE_NORMAL = "0"; //知识文档
	public static final String DOC_TYPE_PRODUCT = "1"; //产品文档
	public static final String DOC_TYPE_PROJECT = "2"; //项目文档
	
	public static final String ASK_TYPE_NORMAL = "0";  //知识问答
	public static final String ASK_TYPE_PRODUCT = "1";  //产品问答
	public static final String ASK_TYPE_PROJECT = "2";  //项目问答
		
	public static final String [] ROLE_NAMES = 
		{"公司内用户","管理员","用友体系用户","待审核用户","其他用户"}; 
	
	public static final String ROLE_PXL = "0";        //公司内用户
	public static final String ROLE_ADMIN = "1";      //管理员
	public static final String ROLE_UFIDA = "2";      //用友体系用户
	public static final String ROLE_UNAPPROVED = "3"; //待审核用户
	public static final String ROLE_OTHER = "4";      //其他用户
	
	public static final String PKB_USER_SESSION_NAME = "PkbUser";
	
}
