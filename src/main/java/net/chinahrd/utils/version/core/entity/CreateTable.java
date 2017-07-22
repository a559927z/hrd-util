/**
*net.chinahrd.utils.version.core
*/
package net.chinahrd.utils.version.core.entity;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import net.chinahrd.utils.version.core.JDBCHelper;

/**
 * @author htpeng
 *2016年9月20日上午11:55:45
 */
public class CreateTable {
	private Connection conn;
	private String tableName;
	private static final String sql="SHOW CREATE TABLE ";
	private static final String sqlcol="Table,Create Table";
	private String showSql;
	private String createTableSql;
	private String createTableOptions;
	public CreateTable(Connection conn){
		this.conn=conn;
	}
	
	public void setTableName(String tableName) {
		this.tableName = tableName;
		this.showSql=sql+"`"+tableName+"`";
	
	}

	/**
	 * 
	 */
	public String show() {
		// TODO Auto-generated method stub
		
		String sql=createTableSql+createTableOptions;
		return sql+";";
	}
	
	
	private String getCreateSql( String sql){
		
		int index=sql.indexOf("/*");
		if(index>0){
			return sql.substring(0,index);

		}else{
			return sql;
		}
	}
	
	private  String interceptCreateOptions(String sql){
		int index=sql.indexOf("/*");
		if(index>0){
			String options= sql.substring(index,sql.indexOf("*/"));
			 return	options.substring(options.indexOf(" "));
		}
		return "";
	}
	
	public String getCreateOptions(){

		return createTableOptions;
	}

	/**
	 * 
	 */
	public void init() {
		// TODO Auto-generated method stub
		List<Map<String,String>> list=JDBCHelper.getListMapNoSplit(showSql,
				sqlcol, conn);
		if(null==list||list.size()==0){
			return ;
		}
		String showCreateSql=list.get(0).get("Create Table");
		createTableSql=getCreateSql(showCreateSql);
//		showCreateSql.substring(0,index);
		createTableOptions=interceptCreateOptions(showCreateSql);
	}

	/**
	 * @return
	 */
	public String deleteOptions() {
		// TODO Auto-generated method stub
		return "ALTER TABLE "+this.tableName+" REMOVE PARTITIONING;";
	}

	/**
	 * @return
	 */
	public String alertOptions() {
		// TODO Auto-generated method stub
		return "ALTER TABLE "+this.tableName+" "+createTableOptions+";";


	}

	/**
	 * @return
	 */
	public String deleteTable() {
		// TODO Auto-generated method stub
		return "DROP TABLE "+this.tableName+";";
	}
}
