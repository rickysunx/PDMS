/**
 * 
 */
package com.pxl.ppm.impls;

import com.pxl.pkb.framework.DataManagerObject;
import com.pxl.pkb.framework.ValueObject;
import com.pxl.pkb.vo.ppm_output;
import com.pxl.pkb.vo.ppm_task;
import com.pxl.pkb.vo.ppm_workdetail;
import com.pxl.pkb.vo.sys_attach;
import com.pxl.ppm.itfs.IOutput;

/**
 * @author Ricky
 *
 */
public class OutputImpl implements IOutput {

	/* (non-Javadoc)
	 * @see com.pxl.ppm.itfs.IOutput#delete(int)
	 */
	public void delete(int id) throws Exception {
		DataManagerObject dmo = new DataManagerObject();
		String sql = "delete from ppm_output where outputid="+id;
		dmo.updateBySQL(sql);
	}

	/* (non-Javadoc)
	 * @see com.pxl.ppm.itfs.IOutput#insert(com.pxl.pkb.vo.ppm_output)
	 */
	public int insert(ppm_output output) throws Exception {
		DataManagerObject dmo = new DataManagerObject();
		return dmo.insert(output);
	}

	/* (non-Javadoc)
	 * @see com.pxl.ppm.itfs.IOutput#update(com.pxl.pkb.vo.ppm_output)
	 */
	public void update(ppm_output output) throws Exception {
		DataManagerObject dmo = new DataManagerObject();
		dmo.update(output);
	}
	/*
	 * 
	 * */
     public ppm_output[] queryForWorklog(int taskID)
			throws Exception {
		DataManagerObject dmo = new DataManagerObject();
		ValueObject [] vos = dmo.queryByWhere(ppm_output.class, 
				"TaskID="+taskID+" order by SeqNum");
		ppm_output [] output = new ppm_output[vos.length];
		for (int i = 0; i < output.length; i++) {
			 output[i] = (ppm_output)vos[i];
		}
		return output;
	}
     
     public ppm_output queryOutById(int id)throws Exception{
    	DataManagerObject dmo = new DataManagerObject();
 		ValueObject [] vos = dmo.queryByWhere(ppm_output.class, "OutputID="+id);
 		if(vos.length<1) throw new Exception("根据ID查找项目失败");
 		return (ppm_output)vos[0];
     }

	public sys_attach[] queryByOutID(int outId) throws Exception {
		DataManagerObject dmo = new DataManagerObject();
 		ValueObject [] vos = dmo.queryByWhere(sys_attach.class, "ObjID="+outId);
 		sys_attach [] attach = new sys_attach[vos.length];
		for (int i = 0; i < attach.length; i++) {
			attach[i] = (sys_attach)vos[i];
		}
		return attach;
	}
}
