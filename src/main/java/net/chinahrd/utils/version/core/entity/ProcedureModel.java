/**
*net.chinahrd.utils.version.core.entity
*/
package net.chinahrd.utils.version.core.entity;

/**
 * @author htpeng
 *2016年9月20日下午12:58:10
 */
public class ProcedureModel {
	private String name;
	
	private String showSql;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShowSql() {
		return showSql;
	}

	public void setShowSql(String showSql) {
		this.showSql = showSql;
	}

	/**
	 * @return
	 */
	public String getDeleteSql() {
		// TODO Auto-generated method stub
		return "DROP PROCEDURE `"+name+"`;";
	}
	
}
