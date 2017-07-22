/**
*net.chinahrd.utils.version.util
*/
package net.chinahrd.utils.version.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import net.chinahrd.utils.version.core.entity.DBConfig;

/**
 * @author htpeng
 *2016年9月20日下午1:24:13
 */
public class ConnectionTool {
	public static Connection getConn(DBConfig dBConfig){
		Connection conn=null;
		try {
			Class.forName(dBConfig.getDriverClass()).newInstance();
			conn =  DriverManager.getConnection(dBConfig.getUrl(), dBConfig.getUsername(), dBConfig
					.getPassword());
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
}
