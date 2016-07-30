/**
 * 
 */
package com.pxl.ppm.impls;

import com.pxl.pkb.extendvo.ppm_problemon;
import com.pxl.pkb.framework.DataManagerObject;
import com.pxl.pkb.framework.ValueObject;
import com.pxl.pkb.vo.ppm_member;
import com.pxl.pkb.vo.ppm_probassist;
import com.pxl.pkb.vo.ppm_problem;
import com.pxl.pkb.vo.ppm_project;
import com.pxl.ppm.itfs.IProblem;

/**
 * @author Ricky
 *
 */
public class ProblemImpl implements IProblem {

	/* (non-Javadoc)
	 * @see com.pxl.ppm.itfs.IProblem#queryForWorklog(int, int)
	 */
	public ppm_problemon[] queryForWorklog(int taskID)
			throws Exception {
		DataManagerObject dmo = new DataManagerObject();
		ValueObject [] vos = dmo.queryByWhere(ppm_problemon.class, "TaskID="+taskID+" order by ProblemCode");
		ppm_problemon [] problems = new ppm_problemon[vos.length];
		for (int i = 0; i < problems.length; i++) {
			problems[i] = (ppm_problemon)vos[i];
		}
		return problems;
	}

	public void delete(int problemID) throws Exception {
		DataManagerObject dmo = new DataManagerObject();
		String sql = "delete from ppm_problem where ProblemID="+problemID;
		dmo.updateBySQL(sql);
		
	}

	public int insert(ppm_problem problem) throws Exception {
		DataManagerObject dmo = new DataManagerObject();
		return dmo.insert(problem);
	}

	public void update(ppm_problem problem) throws Exception {
		DataManagerObject dmo = new DataManagerObject();
		dmo.update(problem);
	}

	public ppm_probassist[] getProbassist(int problemId) throws Exception {
			DataManagerObject dmo = new DataManagerObject();
			ValueObject [] vos = dmo.queryByWhere(ppm_probassist.class, "ProblemID='"+problemId+")'");
			ppm_probassist [] probassist = new ppm_probassist[vos.length];
			for (int i = 0; i < probassist.length; i++) {
				probassist[i] = (ppm_probassist)vos[i];
			}
			return probassist;
	}

	public void insertProbassist(ppm_probassist probassist)throws Exception {
		DataManagerObject dmo = new DataManagerObject();
		dmo.insert(probassist);
	}

	public ppm_problem queryByProblemId(int problemID) throws Exception {
		DataManagerObject dmo = new DataManagerObject();
		ValueObject [] vos = dmo.queryByWhere(ppm_problem.class, "ProblemID="+problemID);
		if(vos.length<1) throw new Exception("根据ID查找项目失败");
		return (ppm_problem)vos[0];
	}

	public ppm_problemon[] queryBySql(String sql) throws Exception {
		DataManagerObject dmo = new DataManagerObject();
		ValueObject [] vos = dmo.queryByWhere(ppm_problemon.class,sql);
		ppm_problemon [] problems = new ppm_problemon[vos.length];
		for (int i = 0; i < vos.length; i++) {
			problems[i] = (ppm_problemon) vos[i];
		}
		return problems;
	}

	public void deteProbassist(int problemID){
		DataManagerObject dmo = new DataManagerObject();
		String sql = "delete from ppm_probassist where ProblemID ="+problemID;
		try {
			dmo.updateBySQL(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
 
	public ppm_probassist[] queryProbassist(int memberID) throws Exception{
		DataManagerObject dmo = new DataManagerObject();
		ValueObject [] vos = dmo.queryByWhere(ppm_probassist.class, "MemberID='"+memberID+")'");
		ppm_probassist [] probassist = new ppm_probassist[vos.length];
		for (int i = 0; i < probassist.length; i++) {
			probassist[i] = (ppm_probassist)vos[i];
		}
		return probassist;
	}
}
