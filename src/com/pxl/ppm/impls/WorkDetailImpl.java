/**
 * 
 */
package com.pxl.ppm.impls;

import com.pxl.pkb.framework.DataManagerObject;
import com.pxl.pkb.framework.ValueObject;
import com.pxl.pkb.vo.ppm_member;
import com.pxl.pkb.vo.ppm_workdetail;
import com.pxl.ppm.itfs.IWorkDetail;

/**
 * @author Ricky
 *
 */
public class WorkDetailImpl implements IWorkDetail {

	/* (non-Javadoc)
	 * @see com.pxl.ppm.itfs.IWorkDetail#delete(int)
	 */
	public void delete(int detailid) throws Exception {
		DataManagerObject dmo = new DataManagerObject();
		String sql = "delete from ppm_workdetail where WorkDetailID="+detailid;
		dmo.updateBySQL(sql);

	}

	/* (non-Javadoc)
	 * @see com.pxl.ppm.itfs.IWorkDetail#insert(com.pxl.pkb.vo.ppm_workdetail)
	 */
	public int insert(ppm_workdetail detail) throws Exception {
		DataManagerObject dmo = new DataManagerObject();
		return dmo.insert(detail);
	}

	/* (non-Javadoc)
	 * @see com.pxl.ppm.itfs.IWorkDetail#queryForWorklog(int, int)
	 */
	public ppm_workdetail[] queryForWorklog(int taskID, int userID)
			throws Exception {
		DataManagerObject dmo = new DataManagerObject();
		ValueObject [] vos=null;
		if(userID!=0){
			vos = dmo.queryByWhere(ppm_workdetail.class, 
						"TaskID="+taskID+" and UserID="+userID+" order by UserID");
		}else{
			vos = dmo.queryByWhere(ppm_workdetail.class, 
					"TaskID="+taskID+" order by UserID ");
		}
	
		ppm_workdetail [] details = new ppm_workdetail[vos.length];
		for (int i = 0; i < details.length; i++) {
			details[i] = (ppm_workdetail)vos[i];
		}
		return details;
	}

	/* (non-Javadoc)
	 * @see com.pxl.ppm.itfs.IWorkDetail#update(com.pxl.pkb.vo.ppm_workdetail)
	 */
	public void update(ppm_workdetail detail) throws Exception {
		DataManagerObject dmo = new DataManagerObject();
		dmo.update(detail);
	}
   
    public  ppm_workdetail  queryByid(int id)throws Exception{
    	DataManagerObject dmo = new DataManagerObject();
		ValueObject [] vos = dmo.queryByWhere(ppm_workdetail.class, "WorkDetailID="+id);
		if(vos.length<1) throw new Exception("根据ID查找项目失败");
		return (ppm_workdetail)vos[0];
    }
}
