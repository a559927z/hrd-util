package net.chinahrd.utils.version.core;

import java.util.List;
import java.util.ResourceBundle;


import net.chinahrd.utils.version.core.entity.DBConfig;
import net.chinahrd.utils.version.sql.Sql;



/**��ʼ����ݿ�
 * @author mx__sword
 *
 */
 public class Initialize  {
	public static final String SYBASE5="com.sybase.jdbc3.jdbc.SybDriver";
	public static final String SYBASE_IQ="com.sybase.jdbc3.jdbc.SybDriver";
	
	public static final String ORACLE="com.sybase.jdbc3.jdbc.SybDriver";
	public static final String MYSQL="com.mysql.jdbc.Driver";
	public static final String SQL_SERVER="com.sybase.jdbc3.jdbc.SybDriver";
	
	
	public static String[] getRelSqlMapperConfigFile(String fileName){
		DBConfig dbConfig = new DBConfig(fileName);
		return dbConfig.getRelSqlMapperConfig();
	}


}
