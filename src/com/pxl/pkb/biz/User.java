/**
 * 
 */
package com.pxl.pkb.biz;

import com.pxl.pkb.framework.DataManagerObject;
import com.pxl.pkb.framework.ValueObject;
import com.pxl.pkb.vo.bd_user;
import com.pxl.pkb.vo.bd_userscore;

/**
 * @author Ricky
 *
 */
public class User {

	/**
	 * 
	 */
	public User() {
	}
	
	public static bd_user findUser(int userid) throws Exception {
		bd_user user = null;
		DataManagerObject dmo = new DataManagerObject();
		ValueObject [] vos = dmo.queryByWhere(bd_user.class, " UserID="+userid);
		if(vos.length>0) {
			user = (bd_user)vos[0];
		}
		return user;
	}
	
	//用户积分
	public static void userScoreAdd(int userid,String content,int score) throws Exception {
		DataManagerObject dmo = new DataManagerObject();
		bd_userscore userScore = new bd_userscore();
		userScore.setUserID(userid);
		userScore.setScoreContent(content);
		userScore.setUserScore(score);
		userScore.setAddTime(Pub.getCurrTime());
		dmo.insert(userScore);
	}

}
