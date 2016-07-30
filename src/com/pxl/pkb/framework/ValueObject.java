/**
 * 
 */
package com.pxl.pkb.framework;

/**
 * @author Ricky
 *
 */
public abstract class ValueObject {

	/**
	 * 
	 */
	public ValueObject() {
	}
	
	public abstract String getTableName();
	public abstract String [] getFieldNames();
	public abstract String [] getFieldTypeNames();
	public abstract String getPrimaryKey();
}
