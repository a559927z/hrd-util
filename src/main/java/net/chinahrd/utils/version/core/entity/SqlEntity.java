/**
*net.chinahrd.utils.version.core.entity
*/
package net.chinahrd.utils.version.core.entity;

import net.chinahrd.utils.version.configure.SqlType;

/**
 * @author htpeng
 *2016年9月19日下午3:02:50
 */
public class SqlEntity {
	private String sql;
	private String sqlcol;
	private String groupCol;
	private SqlType sqlType; 
	public SqlEntity(SqlType sqlType) {
		super();
		this.sqlType=sqlType;
	}
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	public String getSqlcol() {
		return sqlcol;
	}
	public void setSqlcol(String sqlcol) {
		this.sqlcol = sqlcol;
	}
	public String getGroupCol() {
		return groupCol;
	}
	public void setGroupCol(String groupCol) {
		this.groupCol = groupCol;
	}
	public SqlType getSqlType() {
		return sqlType;
	}
	public void setSqlType(SqlType sqlType) {
		this.sqlType = sqlType;
	}
	
	
}
