package net.chinahrd.utils.db.strategy;

/**
 * 策略模式
 * 
 * @author jxzhang on 2017年9月13日
 * @Verdion 1.0 版本
 */
public class GenerateSqlContext {

	private GenerateSql generateSql;

	public GenerateSqlContext(String i) {
		switch (i) {
		case "select":
			generateSql = new GenerateDelSql();
			break;
		case "delete":
			generateSql = new GenerateDelSql();
			break;
		case "InnoDB":
			generateSql = new GenerateDelSql();
			break;
		case "MyISAM":
			generateSql = new GenerateDelSql();
			break;
		}
	}

	public String getNeedGenerateSql(String tbName, String dbName) {
		return this.generateSql.generate(tbName, dbName);
	}

}
