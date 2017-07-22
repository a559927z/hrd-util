package net.chinahrd.utils.version.sql;

import net.chinahrd.utils.version.core.Initialize;
import net.chinahrd.utils.version.core.entity.DBConfig;

public class AlloteSQL {
	public static Sql allotSQL(DBConfig dBConfig) {
		Sql sql = null;
		String driverClass = dBConfig.getDriverClass();
		if (driverClass.trim().equals(Initialize.SYBASE5)) {
			sql = new SYBASE5();
		} else if (driverClass.trim().equals(Initialize.ORACLE)) {
			sql = new ORACLE();
		} else if (driverClass.trim().equals(Initialize.MYSQL)) {
			sql = new MYSQL();
		} else if (driverClass.trim().equals(Initialize.SQL_SERVER)) {
			sql = new SQL_SERVER();
		}
		sql.setDBConfig(dBConfig);
		return sql;
	}

	public static Sql allotSQL(DBConfig dBConfig, String condition) {
		Sql sql = null;
		String driverClass = dBConfig.getDriverClass();
		if (driverClass.trim().equals(Initialize.SYBASE5)) {
			sql = new SYBASE5();
		} else if (driverClass.trim().equals(Initialize.ORACLE)) {
			sql = new ORACLE();
		} else if (driverClass.trim().equals(Initialize.MYSQL)) {
			sql = new MYSQL(condition);
		} else if (driverClass.trim().equals(Initialize.SQL_SERVER)) {
			sql = new SQL_SERVER();
		}
		sql.setDBConfig(dBConfig);
		return sql;
	}
}
