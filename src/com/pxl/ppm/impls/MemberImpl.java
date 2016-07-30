/**
 * 
 */
package com.pxl.ppm.impls;

import com.pxl.pkb.framework.DataManagerObject;
import com.pxl.pkb.framework.ValueObject;
import com.pxl.pkb.vo.bd_user;
import com.pxl.pkb.vo.ppm_member;
import com.pxl.pkb.vo.ppm_problem;
import com.pxl.ppm.itfs.IMember;

/**
 * @author Ricky
 *
 */
public class MemberImpl implements IMember {

	/* (non-Javadoc)
	 * @see com.pxl.ppm.itfs.IMember#delete(int)
	 */
	public void delete(int memberID) throws Exception {
		DataManagerObject dmo = new DataManagerObject();
		String sql = "delete from ppm_member where MemberID="+memberID;
		dmo.updateBySQL(sql);
	}

	/* (non-Javadoc)
	 * @see com.pxl.ppm.itfs.IMember#insert(com.pxl.pkb.vo.ppm_member)
	 */
	public int insert(ppm_member member) throws Exception {
		DataManagerObject dmo = new DataManagerObject();
		return dmo.insert(member);
	}

	/* (non-Javadoc)
	 * @see com.pxl.ppm.itfs.IMember#queryMemberByProject(int)
	 */
	public ppm_member[] queryMemberByProject(int projectID) throws Exception {
		DataManagerObject dmo = new DataManagerObject();
		ValueObject [] vos = dmo.queryByWhere(ppm_member.class, "ProjectID="+projectID);
		ppm_member [] members = new ppm_member[vos.length];
		for (int i = 0; i < members.length; i++) {
			members[i] = (ppm_member)vos[i];
		}
		return members;
	}

	/* (non-Javadoc)
	 * @see com.pxl.ppm.itfs.IMember#queryPxlUser()
	 */
	public ppm_member[] queryPxlUser() throws Exception {
		DataManagerObject dmo = new DataManagerObject();
		ValueObject [] vos = dmo.queryByWhere(bd_user.class, "IsAdmin in ('0','1')");
		bd_user [] users = new bd_user[vos.length];
		for (int i = 0; i < users.length; i++) {
			users[i] = (bd_user) vos[i];
		}
		
		ppm_member [] members = new ppm_member[users.length];
		
		for (int i = 0; i < members.length; i++) {
			ppm_member m = new ppm_member();
			bd_user u = users[i];
			m.setUserID(u.getUserID());
			m.setEMail(u.getEMail());
			m.setTel(u.getPhone());
			m.setUnit(u.getCompany());
			m.setUserName(u.getUserName());
			members[i] = m;
		}
		
		return members;
	}

	/* (non-Javadoc)
	 * @see com.pxl.ppm.itfs.IMember#update(com.pxl.pkb.vo.ppm_member)
	 */
	public void update(ppm_member member) throws Exception {
		DataManagerObject dmo = new DataManagerObject();
		dmo.update(member);

	}

	public ppm_member queryByID(int id) throws Exception {
		DataManagerObject dmo = new DataManagerObject();
		ValueObject [] vos = dmo.queryByWhere(ppm_member.class, "MemberID="+id);
		if(vos.length<1) throw new Exception("根据ID查找项目失败");
		return (ppm_member)vos[0];
	}

	public ppm_member[] queryMemberByProject(int projectID, boolean bPxlOnly) throws Exception {
		DataManagerObject dmo = new DataManagerObject();
		ValueObject [] vos = dmo.queryByWhere(ppm_member.class, "ProjectID="+projectID+(bPxlOnly?" and UserID<>0":""));
		ppm_member [] members = new ppm_member[vos.length];
		for (int i = 0; i < members.length; i++) {
			members[i] = (ppm_member)vos[i];
		}
		return members;
	}

	
	public ppm_member[] queryBySql(String sql) throws Exception {
		DataManagerObject dmo = new DataManagerObject();
		ValueObject [] vos = dmo.queryByWhere(ppm_member.class,sql);
		ppm_member [] members = new ppm_member[vos.length];
		for (int i = 0; i < vos.length; i++) {
			members[i] = (ppm_member) vos[i];
		}
		return members;
	}
	public bd_user queryUserByID(int id) throws Exception{
		DataManagerObject dmo = new DataManagerObject();
		ValueObject [] vos = dmo.queryByWhere(bd_user.class, "UserID="+id);
		if(vos.length<1) throw new Exception("根据ID查找项目失败");
		return (bd_user)vos[0];
		
	}
}
