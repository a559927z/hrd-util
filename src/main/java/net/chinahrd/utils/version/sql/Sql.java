package net.chinahrd.utils.version.sql;

import java.util.List;
import java.util.Map;

import net.chinahrd.utils.version.core.entity.DBConfig;
import net.chinahrd.utils.version.core.entity.EntityModel;
import net.chinahrd.utils.version.core.entity.ProcedureModel;


/**
 * 实例化数据库接口
 * @author mx__sword
 * 
 */
public interface Sql {
	
	/**
	 * 表名
	 */
	String TABLE_NAME = "TABLE_NAME";  
	/**
	 * 表配注
	 */
	String TABLE_COMMENT = "TABLE_COMMENT";
	/**
	 * 列名
	 */
	String COLUMN_NAME = "COLUMN_NAME";
	/**
	 * 列的默认值
	 */
	String COLUMN_DEFAULT = "COLUMN_DEFAULT";
	/**
	 * 是否允许为空
	 */
	String COLUMN_IS_NULL = "IS_NULLABLE";
	/**
	 * 列字段类型
	 */
	String COLUMN_TYPE = "DATA_TYPE";
	/**
	 * 最大长度
	 */
	String LENGTH = "CHARACTER_MAXIMUM_LENGTH";
	/**
	 * 整数位长度
	 */
	String PREC = "NUMERIC_PRECISION";
	/**
	 * 小数位长度
	 */
	String SCALE = "NUMERIC_SCALE";
	/**
	 * 
	 */
	String PRIMARYKEY = "COLUMN_KEY";
	/**
	 * 列 备注
	 */
	String COLUMN_COMMENT = "COLUMN_COMMENT";
	/**
	 * 表引擎
	 */
	String ENGINE = "ENGINE";
	/**
	 * 表分区
	 */
	String CREATE_OPTIONS = "CREATE_OPTIONS";
	
	
	/**
	 * 索引名称
	 */
	String INDEX_NAME = "INDEX_NAME";
	/**
	 * 索引列
	 */
	String INDEX_COL = "COLUMN_NAME";
	
	
	
	void setDBConfig(DBConfig dBConfig);

	Sql init();

	
	//EntityModel getEntityModel(String tabName);

	

	
	Map<String, EntityModel> getEntityModel();
	List<ProcedureModel> getProcedureModel(boolean detail);

	/**
	 * 设置是否查询sql脚本
	 * @param script
	 */
	Sql setScript(boolean script);
}
