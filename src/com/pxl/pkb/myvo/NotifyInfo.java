/**
 * 
 */
package com.pxl.pkb.myvo;

import com.pxl.pkb.biz.Pub;

/**
 * @author Ricky
 *
 */
public class NotifyInfo {

	public static final int TYPE_MSG = 0;
	public static final int TYPE_ASK_REFRESH = 1;
	
	public int type = 0;
	public String info = null;
	public int objID = 0;
	public String infoTime = null;
	
	/**
	 * 
	 */
	public NotifyInfo() {
		super();
		try {
			infoTime = Pub.getCurrTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public int getObjID() {
		return objID;
	}

	public void setObjID(int objID) {
		this.objID = objID;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getInfoTime() {
		return infoTime;
	}

	public void setInfoTime(String infoTime) {
		this.infoTime = infoTime;
	}

}
