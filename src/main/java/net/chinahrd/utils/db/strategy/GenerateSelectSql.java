package net.chinahrd.utils.db.strategy;

/**
 * 策略模式
 * 
 * @author jxzhang on 2017年9月13日
 * @Verdion 1.0 版本
 */
public class GenerateSelectSql implements GenerateSql {

	@Override
	public String generate(String tbName, String dbName) {
		return "SELECT FROM `" + dbName + "`.`" + tbName + "`";
	}

}
