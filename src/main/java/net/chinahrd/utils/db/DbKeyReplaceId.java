package net.chinahrd.utils.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * 用key字段值替换id值
 * 
 * 从已代替目前uuid值
 * 
 * @author jxzhang on 2017年1月2日
 * @Verdion 1.0 版本
 */
public class DbKeyReplaceId {
	
	private static String url = "jdbc:mysql://172.16.9.50:3369/mup-large";
	private static String user = "mup";
	private static String password = "1q2w3e123";
	private static String driverClassName = "com.mysql.jdbc.Driver";
	private static String filePath = "D:\\1.txt";


	/**
	 * key字段值替换id值
	 * @param tableName
	 * @param idColumnName	指定被替换的列id
	 * @param keyColumnName	指定使用列key值来替换
	 * @param rule
	 *            规则:创建Id 规则。(默认： rule + key)
	 * @throws SQLException
	 */
	public static void generateSQL(String tableName, String idColumnName, String keyColumnName,
			String rule) throws SQLException {
		
		String dbName = DatabaseUtil.url.split(":3369/")[1];
		StringBuffer sql = new StringBuffer();
		sql.delete(0, sql.length());
		// 需要修改表
		sql.append(" SELECT TABLE_NAME from information_schema.columns ");
		sql.append(" where column_name = '").append(idColumnName).append("'");
		sql.append(" and TABLE_NAME not like 'soure_%' and TABLE_NAME not like '%bak%' and TABLE_NAME not like '%copy%' and TABLE_NAME != 'dim_emp'");
		sql.append(" and TABLE_SCHEMA = '").append(dbName).append("' ");
		DatabaseUtil databaseUtil =new DatabaseUtil(url, user, password, driverClassName);
		try {
			ResultSet rs = databaseUtil.query(sql.toString() );
			List<String> needUpdateTableList = new ArrayList<String>();
			while (rs.next()) {
				needUpdateTableList.add(rs.getString("TABLE_NAME"));
			}

			sql.delete(0, sql.length());
			sql.append("SELECT * from ").append(tableName);
			ResultSet rs1 = databaseUtil.query(sql.toString());
			List<Map<String, String>> idAndKeyInfo = new ArrayList<Map<String, String>>();
			while (rs1.next()) {
				Map<String, String> idAndKeyInfoMap = new HashMap<String, String>();
				// 过滤特殊表
//				if(tableName.contentEquals("dim_ability") ||
//						tableName.contentEquals("dim_certificate_info")
//					){continue;}
				
				if(tableName.equals("dim_ability")){
					idAndKeyInfoMap.put(rs1.getString(idColumnName),  rs1.getString("type")+"_"+rs1.getString(keyColumnName));
				}else{
					idAndKeyInfoMap.put(rs1.getString(idColumnName),  rs1.getString(keyColumnName));
				}
				idAndKeyInfo.add(idAndKeyInfoMap);
			}
			String rsScriptSQL = "";
			for (String needUpdateTable : needUpdateTableList) {
				System.out.println("-- ========================== --");
				System.out.println("-- "+needUpdateTable+" --");
				System.out.println("-- ========================== --");
				for (Map<String, String> idAndKey : idAndKeyInfo) {
					StringBuffer updateSQL = new StringBuffer();
					Set<Entry<String, String>> entrySet = idAndKey.entrySet();
					String idVal = entrySet.iterator().next().getKey();
					String keyVal = entrySet.iterator().next().getValue();

					updateSQL.append(" UPDATE `").append(dbName).append("`.").append(needUpdateTable);
					updateSQL.append(" SET ").append(idColumnName).append(" = '").append(rule + keyVal).append("'");
					updateSQL.append(" WHERE ").append(idColumnName).append(" = '").append(idVal).append("'");
					updateSQL.append(" ; ");
					rsScriptSQL += updateSQL.toString() +"\r\n";
					System.out.println(updateSQL.toString());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
//		try {
//			FileOperation.writeTxtFile(rsScriptSQL, new File(filePath));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		// DatabaseUtil.close();
		databaseUtil.close();
	}

	/**
	 * 查出所有维度表
	 * @throws SQLException
	 */
	public static List<ResultModule> queryDimTableAll() throws SQLException{
		String dbName = DatabaseUtil.url.split(":3369/")[1];
		StringBuffer sql = new StringBuffer();
		sql.delete(0, sql.length());
		sql.append(" SELECT table_name, column_name FROM information_schema.`COLUMNS` t ");
		sql.append(" where TABLE_NAME like 'dim_%'");
		sql.append(" and TABLE_NAME not like '%copy%'");
		sql.append(" and TABLE_SCHEMA = '").append(dbName).append("' ");
		sql.append(" and t.column_key = 'PRI' ");
		DatabaseUtil databaseUtil =new DatabaseUtil(url, user, password, driverClassName);
		try{
			ResultSet rs = databaseUtil.query(sql.toString());
			List<ResultModule> dimTableList = new ArrayList<ResultModule>();
			while (rs.next()) {
				ResultModule rsDto = new ResultModule();
				rsDto.setTableName(rs.getString("table_name"));
				rsDto.setId(rs.getString("column_name"));
				dimTableList.add(rsDto);
			}
			databaseUtil.close();
			return dimTableList;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	

//	SELECT table_name, column_name FROM information_schema.`COLUMNS` t
//	where TABLE_NAME like 'dim_%'
//	and TABLE_NAME not in(
//				SELECT DISTINCT TABLE_NAME from information_schema.`COLUMNS`
//				where TABLE_NAME like 'dim_%'
//				and COLUMN_NAME = 'show_index'
//				and TABLE_SCHEMA = 'mup-large'
//			)
//	and TABLE_SCHEMA = 'mup-large'
//	and TABLE_NAME not in 
//		('dim_emp','dim_emp_month','dim_organization','dim_days','dim_function_item','dimission_risk','dimission_risk_item','dim_per_exam_content')
//	and t.column_key = 'PRI'
	public static List<ResultModule> queryNotShowIndexDimTableAll() throws SQLException{
		String dbName = DatabaseUtil.url.split(":3369/")[1];
		StringBuffer sql = new StringBuffer();
		sql.delete(0, sql.length());
		sql.append(" SELECT table_name, column_name FROM information_schema.`COLUMNS` t");
		sql.append(" where TABLE_NAME like 'dim_%'");
		sql.append(" and TABLE_NAME not in(");
		sql.append("	SELECT DISTINCT TABLE_NAME from information_schema.`COLUMNS`");
		sql.append("	where TABLE_NAME like 'dim_%'");
		sql.append("	and COLUMN_NAME = 'show_index'");
		sql.append("	and TABLE_SCHEMA = '").append(dbName).append("' ");
		sql.append(")");
		sql.append("and TABLE_SCHEMA = '").append(dbName).append("' ");
		sql.append("and TABLE_NAME not in ");
		sql.append("('dim_emp','dim_emp_month','dim_organization','dim_days','dim_function_item','dimission_risk','dimission_risk_item','dim_per_exam_content')");
		sql.append("and t.column_key = 'PRI'");
		DatabaseUtil databaseUtil =new DatabaseUtil(url, user, password, driverClassName);

		ResultSet rs;
		try {
			rs = databaseUtil.query(sql.toString());
			List<ResultModule> dimTableList = new ArrayList<ResultModule>();
			while (rs.next()) {
				ResultModule rsDto = new ResultModule();
				rsDto.setTableName(rs.getString("table_name"));
				rsDto.setId(rs.getString("column_name"));
				dimTableList.add(rsDto);
			}
			databaseUtil.close();
			return dimTableList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 添加show_index字段
	 * @param tableName
	 * @throws SQLException
	 */
	public static void generateShowIndexSQL(ResultModule rsDto) throws SQLException {
		String dbName = DatabaseUtil.url.split(":3369/")[1];
		String tableName = rsDto.getTableName();
		String id = rsDto.getId();
		
		StringBuffer sql = new StringBuffer();
		sql.delete(0, sql.length());
		sql.append(" ALTER TABLE `").append(dbName).append("`.").append(tableName).append(" ADD show_index INT(1); ");
		System.out.println(sql.toString());

		sql.delete(0, sql.length());
		sql.append("SELECT ").append(id).append(" as ID FROM ").append(tableName);
		DatabaseUtil databaseUtil =new DatabaseUtil(url, user, password, driverClassName);

		ResultSet rs1;
		try {
			rs1 = databaseUtil.query(sql.toString());
			int i = 0;
			while (rs1.next()) {
				i++;
				sql.delete(0, sql.length());
				sql.append(" UPDATE `").append(dbName).append("`.").append(tableName).append(" SET show_index = ").append(i);
				sql.append(" WHERE ").append(id).append(" = '").append(rs1.getString("ID")).append("';");
				System.out.println(sql.toString());
			}
			databaseUtil.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
