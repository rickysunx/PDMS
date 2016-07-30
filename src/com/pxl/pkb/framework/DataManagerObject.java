package com.pxl.pkb.framework;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Hashtable;
import java.util.Vector;

import com.pxl.pkb.biz.Pub;

public class DataManagerObject {
	
	public static Hashtable htThreadConn = new Hashtable();
	public static ConnManageThread connManageThread = null;
	public DataManagerObject() {
	}
	
	protected static Connection getDBConn() throws Exception {
		Params param=Params.getInstance();		
		Class.forName("com.mysql.jdbc.Driver");
//		System.out.println("用户名："+param.getDb_user());
//		System.out.println("密码："+param.getDb_pass());
//		System.out.println("url："+param.getDb_url());
		Connection conn = DriverManager.getConnection(param.getDb_url(),param.getDb_user(),param.getDb_pass());
		return conn;
	}
		
	public static Connection getConnection() throws Exception {
		Object objConn = htThreadConn.get(Thread.currentThread());
		Connection conn = null;
		
		if(connManageThread==null) {
			connManageThread = new ConnManageThread();
			connManageThread.start();
		}
		
		if(objConn==null || ((Connection)objConn).isClosed()) {
			objConn = getDBConn();
			htThreadConn.put(Thread.currentThread(), objConn);
		} 
		
		conn = (Connection)objConn;
		return conn;
	}
	
	public int getTotalPage(Class c,String whereSQL,double pageCount)throws Exception{
		ValueObject[] vos= queryByWhere(c, whereSQL);
		int totalPage=(int)Math.ceil(vos.length/pageCount);
		return totalPage;
	}
	
	public ValueObject [] queryAll(Class c) throws Exception {
		return queryByWhere(c, null);
	}
	
	public ValueObject[] queryAllForPage(Class c,int page,int pageCount) throws Exception {
		return queryByWhere(c, " 1=1 limit "+(page-1)*pageCount+","+pageCount);
	}
	
	public ValueObject[] queryByWhereForPage(Class c,String whereSQL,int page,int pageCount) throws Exception {
		return queryByWhere(c, whereSQL+" limit "+(page-1)*pageCount+","+pageCount);
	}
	
public ValueObject[] queryByWhere0(Class c,String whereSQL) throws Exception {
		
		String sql = "select ";
		ValueObject vo = (ValueObject)c.newInstance();
		String [] fieldNames = vo.getFieldNames();
		String [] fieldTypes = vo.getFieldTypeNames();
		for (int i = 0; i < fieldNames.length; i++) {
			sql+=fieldNames[i];
			if(i<fieldNames.length-1) {
				sql+=",";
			}
		}
		sql+=" from "+vo.getTableName();
		if(whereSQL!=null) {
			sql+=" as c where "+whereSQL;
		}
		
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ValueObject [] vos = null;
		try {
			conn = getConnection();
			stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			Vector v = new Vector();
			while(rs.next()) {
				ValueObject vo1 = (ValueObject)c.newInstance();
				
				for (int i = 0; i < fieldNames.length; i++) {
					if(fieldTypes[i].equalsIgnoreCase("INT")){
						Method m = c.getMethod("set"+fieldNames[i], new Class[]{int.class});
						m.invoke(vo1, new Object[]{new Integer(rs.getInt(i+1))});
					} else {
						Method m = c.getMethod("set"+fieldNames[i], new Class[]{String.class});
						m.invoke(vo1, new Object[]{rs.getString(i+1)});
					}
				}
				
				v.add(vo1);
			}
			vos = new ValueObject[v.size()];
			v.copyInto(vos);
		} finally {
			try { if(stmt!=null) stmt.close(); } catch (Exception e) {}
			//try { if(conn!=null) conn.close(); } catch (Exception e) {}
		}
		return vos;
	}
	
	public ValueObject[] queryByWhere(Class c,String whereSQL) throws Exception {
		
		String sql = "select ";
		ValueObject vo = (ValueObject)c.newInstance();
		String [] fieldNames = vo.getFieldNames();
		String [] fieldTypes = vo.getFieldTypeNames();
		for (int i = 0; i < fieldNames.length; i++) {
			sql+=fieldNames[i];
			if(i<fieldNames.length-1) {
				sql+=",";
			}
		}
		sql+=" from "+vo.getTableName();
		if(whereSQL!=null) {
			sql+=" as c where "+whereSQL;
		}
		
		System.out.println("SQL["+Pub.getCurrTime()+"] "+sql);
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ValueObject [] vos = null;
		try {
			conn = getConnection();
			stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			Vector v = new Vector();
			while(rs.next()) {
				ValueObject vo1 = (ValueObject)c.newInstance();
				
				for (int i = 0; i < fieldNames.length; i++) {
					if(fieldTypes[i].equalsIgnoreCase("INT")){
						Method m = c.getMethod("set"+fieldNames[i], new Class[]{int.class});
						m.invoke(vo1, new Object[]{new Integer(rs.getInt(i+1))});
					} else {
						Method m = c.getMethod("set"+fieldNames[i], new Class[]{String.class});
						m.invoke(vo1, new Object[]{rs.getString(i+1)});
					}
				}
				
				v.add(vo1);
			}
			vos = new ValueObject[v.size()];
			v.copyInto(vos);
		} finally {
			try { if(stmt!=null) stmt.close(); } catch (Exception e) {}
			//try { if(conn!=null) conn.close(); } catch (Exception e) {}
		}
		return vos;
	}
	
	public synchronized static int getID() throws Exception {
		Connection conn = null;
		PreparedStatement stmt = null;
		String sql = "select SysID from sys_oid";
		int key = 1;
		try {
			conn = getDBConn();
			stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				key = rs.getInt(1);
				key++;
				stmt.close();
				stmt = conn.prepareStatement("update sys_oid set SysID="+key);
				stmt.executeUpdate();
			} else {
				stmt.close();
				stmt = conn.prepareStatement("insert into sys_oid(SysID) values (1)");
				stmt.executeUpdate();
			}
		} finally {
			try { if(stmt!=null) stmt.close(); } catch (Exception e) {}
			try { if(conn!=null) conn.close(); } catch (Exception e) {}
		}
		return key;
	}
	
	public void update(ValueObject vo) throws Exception {
		update(new ValueObject[]{vo});
	}
	
	public void update(ValueObject[] vos) throws Exception {
		if(vos==null || vos.length==0) return;
		
		ValueObject vo = vos[0];
		if(vo.getPrimaryKey()==null) throw new Exception("表没有主键字段，无法进行更新");
		String [] fieldNames = vo.getFieldNames();
		String [] fieldTypeNames = vo.getFieldTypeNames();
		String sql = "update "+vo.getTableName()+" set ";
		for (int i = 0; i < fieldNames.length; i++) {
			sql+=fieldNames[i]+"=?";
			if(i<fieldNames.length-1) {
				sql+=",";
			}
		}
		sql+=" where "+vo.getPrimaryKey()+"=?";
		sql+="";
		
		System.out.println("SQL["+Pub.getCurrTime()+"] "+sql);
		
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = getConnection();
			stmt = conn.prepareStatement(sql);
			System.out.println("SQL["+Pub.getCurrTime()+"] "+sql);
			for (int i = 0; i < vos.length; i++) {
				for (int j = 0; j < fieldNames.length; j++) {
					Method m = vos[i].getClass().getMethod("get"+fieldNames[j], new Class[0]);
					Object obj = m.invoke(vos[i], new Object[0]);
					if(fieldTypeNames[j].equalsIgnoreCase("INT")) {
						stmt.setInt(j+1, ((Integer)obj).intValue());
					} else {
						stmt.setString(j+1, (String)obj);
					}
				}
				Method m1 = vos[i].getClass().getMethod("get"+vos[i].getPrimaryKey(), new Class[0]);
				Object obj1 = m1.invoke(vos[i], new Object[0]);
				stmt.setInt(fieldNames.length+1, ((Integer)obj1).intValue());
				stmt.addBatch();
			}
			
			stmt.executeBatch();
			
		} finally {
			try { if(stmt!=null) stmt.close(); } catch (Exception e) {}
			//try { if(conn!=null) conn.close(); } catch (Exception e) {}
		}
	}
	
	public void updateBySQL(String sql) throws Exception {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = getConnection();
			System.out.println("SQL["+Pub.getCurrTime()+"] "+sql);
			stmt = conn.prepareStatement(sql);
			stmt.executeUpdate();
		} finally {
			try { if(stmt!=null) stmt.close(); } catch (Exception e) {}
			//try { if(conn!=null) conn.close(); } catch (Exception e) {}
		}
	}
	
	public int insert(ValueObject vo) throws Exception {
		return insert(new ValueObject[]{vo})[0];
	}
	
	public Object [][] querySQL(String sql) throws Exception {
		Object [][] data = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = getConnection();
			stmt = conn.prepareStatement(sql);
			System.out.println("SQL["+Pub.getCurrTime()+"] "+sql);
			ResultSet rs = stmt.executeQuery();
			Vector v = new Vector();
			ResultSetMetaData md = rs.getMetaData();
			while(rs.next()) {
				Object [] row = new Object[md.getColumnCount()];
				for (int i = 0; i < row.length; i++) {
					row[i] = rs.getObject(i+1);
				}
				v.add(row);
			}
			data = new Object[v.size()][];
			v.copyInto(data);
		} finally {
			try { if(stmt!=null) stmt.close(); } catch (Exception e) {}
			//try { if(conn!=null) conn.close(); } catch (Exception e) {}
		}
		return data;
	}
	
	public int [] insert(ValueObject [] vos) throws Exception {
		
		if(vos==null || vos.length==0) return new int[0];
		ValueObject vo = vos[0];
		String [] fieldNames = vo.getFieldNames();
		String [] fieldTypeNames = vo.getFieldTypeNames();
		String sql = "insert into "+vo.getTableName()+" (";
		for (int i = 0; i < fieldNames.length; i++) {
			sql+=fieldNames[i];
			if(i<fieldNames.length-1) {
				sql+=",";
			}
		}
		sql+=") values (";
		for (int i = 0; i < fieldNames.length; i++) {
			sql+="?";
			if(i<fieldNames.length-1) {
				sql+=",";
			}
		}
		sql+=")";
		
		System.out.println("SQL["+Pub.getCurrTime()+"] "+sql);
		
		Connection conn = null;
		PreparedStatement stmt = null;
		int [] keys = new int[vos.length];
		try {
			conn = getConnection();
			stmt = conn.prepareStatement(sql);
			
			for (int i = 0; i < vos.length; i++) {
				for (int j = 0; j < fieldNames.length; j++) {
					Method m = vos[i].getClass().getMethod("get"+fieldNames[j], new Class[0]);
					Object obj = m.invoke(vos[i], new Object[0]);
					System.out.println(obj);
					if(vos[i].getPrimaryKey()!=null && vos[i].getPrimaryKey().equals(fieldNames[j])) {
						keys[i] = getID();
						stmt.setInt(j+1, keys[i]);
						Method m1 = vos[i].getClass().getMethod("set"+fieldNames[j], new Class[]{int.class});
						m1.invoke(vos[i], new Object[]{new Integer(keys[i])});
					} else {
						if(fieldTypeNames[j].equalsIgnoreCase("INT")) {
							stmt.setInt(j+1, ((Integer)obj).intValue());
						} else {
							stmt.setString(j+1, (String)obj);
						}
					}
				}
				stmt.addBatch();
			}
			stmt.executeBatch();
			
		} finally {
			try { if(stmt!=null) stmt.close(); } catch (Exception e) {}
		}
		
		return keys;
	}
}
