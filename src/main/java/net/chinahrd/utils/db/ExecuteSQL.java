package net.chinahrd.utils.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.chinahrd.utils.CollectionKit;
import net.chinahrd.utils.db.strategy.GenerateSqlContext;

public class ExecuteSQL {

	private static final Logger logger = LoggerFactory.getLogger(GenerateDelSoureSQL.class);

	/**
	 * 执行SQL
	 * 
	 * @param jdbcDto
	 *            数据源对象
	 * @param fetchColName
	 *            抓取有这列名的表
	 * @param whereValueStr
	 *            最终执行的条件
	 * @param optType
	 *            策略模式选取操作
	 * @throws SQLException
	 */
	public static void execute(JdbcDto jdbcDto, String fetchColName, String whereValueStr, String optType)
			throws SQLException {
		String dbName = jdbcDto.getDbName();
		String allTbNameSql = getAllTableNameSql(dbName, fetchColName);
		DatabaseUtil databaseUtil = new DatabaseUtil(jdbcDto);

		// 需要执行sqlMap
		Map<String, String> needGrenerateSqlMap = CollectionKit.newMap();
		try {
			ResultSet rs = databaseUtil.query(allTbNameSql);
			while (rs.next()) {
				String tbName = rs.getString("tbName");
				// 策略模式
				needGrenerateSqlMap.put(tbName, new GenerateSqlContext(optType).getNeedGenerateSql(tbName, dbName));
			}

			List<String> warpGenerateSqls = warpGenerateSql(needGrenerateSqlMap, whereValueStr);

			for (String warpGenerateSql : warpGenerateSqls) {
				int isSuccess = databaseUtil.delete(warpGenerateSql);
				logger.debug(String.valueOf(isSuccess));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		databaseUtil.close();
	}

	/**
	 * 对需要执行的SQL，追加where条件
	 * 
	 * @param needGrenerateSqlMap
	 * @param whereValueStr
	 * @return
	 */
	private static List<String> warpGenerateSql(Map<String, String> needGrenerateSqlMap, String whereValueStr) {
		List<String> rs = CollectionKit.newList();
		for (Map.Entry<String, String> entry : needGrenerateSqlMap.entrySet()) {
			rs.add(entry.getValue() + whereValueStr);
			logger.debug(entry.getValue() + whereValueStr);
		}
		return rs;
	}

	/**
	 * 组装字段在哪些表里的SQL
	 * 
	 * @param dbName
	 *            指定库
	 * @param colName
	 *            列字段名
	 * @return
	 */
	private static String getAllTableNameSql(String dbName, String colName) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT table_name AS tbName FROM information_schema.columns");
		sql.append(" WHERE column_name = '").append(colName).append("' ");
		sql.append(" AND TABLE_SCHEMA = '").append(dbName).append("' ");
		return sql.toString();
	}

	public static void main(String[] args) throws SQLException {
		// 数据源
		String dbName = "mup-zrw";
		String url = "jdbc:mysql://120.236.148.37:3369/mup-zrw";
		String user = "mup";
		String password = "1z2x3c123";
		String driverClassName = "com.mysql.jdbc.Driver";

		// 抓取有这列名的表
		String fetchColName = "emp_id";
		// where的字符串
		// SELECT emp_id from dim_user t where t.user_name_ch = '张景星'
		String whereValueStr = " WHERE emp_id = '352819843713466368'";

		// 策略模式操作类型
		String optType = "delete";

		ExecuteSQL.execute(new JdbcDto(url, user, password, driverClassName, dbName), fetchColName, whereValueStr,
				optType);
	}
}
