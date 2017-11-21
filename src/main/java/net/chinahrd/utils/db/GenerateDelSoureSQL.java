package net.chinahrd.utils.db;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.chinahrd.utils.FileUtil;

/**
 * 清空源表数据
 * 
 * @author jxzhang on 2017年3月28日
 * @Verdion 1.0 版本
 */
public class GenerateDelSoureSQL {

	/**
	 * 生成SQL
	 * 
	 * @param url
	 *            jdbc的URL
	 * @param user
	 *            数据连接用户
	 * @param password
	 *            数据库连接密码
	 * @param filePath
	 *            生成后写出来文件位置
	 * @throws SQLException
	 */
	public static void generateSQL(String url, String user, String password, String driverClassName, String filePath) throws SQLException {
		String dbName = url.split(":3369/")[1];
		StringBuffer sql = new StringBuffer();
//		 SELECT concat('DELETE FROM `', 'mup-large`.`', table_name, '`;') delSql FROM information_schema.`TABLES` WHERE TABLE_TYPE = 'BASE TABLE' AND table_name like 'soure_%' AND TABLE_SCHEMA='mup-large' 
		
		sql.delete(0, sql.length());
		sql.append(" SELECT  concat('DELETE FROM `', '").append(dbName).append("`.`', table_name, '`;') delSql FROM information_schema.`TABLES` ");
		sql.append(" WHERE TABLE_TYPE = 'BASE TABLE' ");
		sql.append(" AND table_name like 'soure_%' ");
		sql.append(" AND TABLE_SCHEMA = '").append(dbName).append("' ");
		DatabaseUtil databaseUtil=new DatabaseUtil(url, user, password, driverClassName);
		try {
			ResultSet rs = databaseUtil.query(sql.toString());
			String rsScriptSQL = "";
			while (rs.next()) {
				rsScriptSQL += rs.getString("delSql");
				rsScriptSQL += "\r\n";
			}
			System.out.println(rsScriptSQL);
			FileUtil.writeTxtFile(rsScriptSQL, new File(filePath));
		} catch (Exception e) {
			e.printStackTrace();
		}
		databaseUtil.close();
	}

	public static void main(String[] args) throws SQLException {
		String url = "jdbc:mysql://172.16.9.50:3369/mup-large";
		String user = "mup";
		String password = "1q2w3e123";
		String driverClassName = "com.mysql.jdbc.Driver";
		String filePath = "D:\\1.txt";
		GenerateDelSoureSQL.generateSQL(url, user, password,driverClassName, filePath);
	}

}
