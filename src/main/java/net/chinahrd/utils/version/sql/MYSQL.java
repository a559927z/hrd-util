package net.chinahrd.utils.version.sql;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.chinahrd.utils.version.configure.SqlType;
import net.chinahrd.utils.version.core.JDBCHelper;
import net.chinahrd.utils.version.core.entity.CreateTable;
import net.chinahrd.utils.version.core.entity.EntityModel;
import net.chinahrd.utils.version.core.entity.MapEntity;
import net.chinahrd.utils.version.core.entity.ProcedureModel;
import net.chinahrd.utils.version.core.entity.SqlEntity;
import net.chinahrd.utils.version.core.entity.TableModel;
import net.chinahrd.utils.version.util.Util;


public class MYSQL extends SqlSimple {

	//条件
	private String condition = null;
	@Override
	protected List<SqlEntity> initSqlList() {
		List<SqlEntity> list=new ArrayList<SqlEntity>();
		SqlEntity sql=new SqlEntity(SqlType.COLUMN);
		list.add(sql);
		sql.setSql(tablesql);
		sql.setSqlcol(tablecol);
		sql.setGroupCol("TABLE_NAME");
		SqlEntity index=new SqlEntity(SqlType.INDEX);
		list.add(index);
		index.setSql(indexsql);
		index.setSqlcol(indexcol);
		index.setGroupCol("TABLE_NAME");
		return list;
	}
	private String tablecol = 
			"c.TABLE_NAME,t.TABLE_COMMENT,c.COLUMN_NAME,c.ORDINAL_POSITION,c.COLUMN_DEFAULT,"
			+ "c.IS_NULLABLE,c.DATA_TYPE,c.CHARACTER_MAXIMUM_LENGTH,c.NUMERIC_PRECISION,"
			+ "c.NUMERIC_SCALE,c.COLUMN_KEY,c.COLUMN_COMMENT,t.ENGINE,t.CREATE_OPTIONS";
	
	private String tablesql =
			" SELECT " + tablecol + "\n FROM"
			+ "\n TABLES t "
			+ "\n INNER JOIN COLUMNS c "
			+ "\n ON t.TABLE_NAME=c.TABLE_NAME AND t.TABLE_SCHEMA=c.TABLE_SCHEMA"
			+ "\n WHERE c.TABLE_SCHEMA='?'  AND TABLE_TYPE='BASE TABLE' " 
			+ "\n ORDER BY c.TABLE_NAME,c.ORDINAL_POSITION";
	

	private String indexcol = "TABLE_NAME,INDEX_NAME,COLUMN_NAME";
	private String indexsql = 
			"SELECT  a.TABLE_NAME,a.INDEX_NAME,GROUP_CONCAT(a.COLUMN_NAME)    COLUMN_NAME "  
			+ "\n FROM (SELECT   "
	        + "\n TABLE_SCHEMA,   "
	        + "\n TABLE_NAME,   "
	        + "\n NON_UNIQUE,   "
	        + "\n INDEX_NAME,   "
	        + "\n SEQ_IN_INDEX,  " 
	        + "\n COLUMN_NAME,   "
	        + "\n INDEX_TYPE,   "
	        + "\n CONCAT(COMMENT,INDEX_COMMENT)    INDEX_COMMENT   "
	      + "\n FROM INFORMATION_SCHEMA.STATISTICS  " 
	      + "\n WHERE  TABLE_SCHEMA = '?'  "
	      + "\n ORDER BY TABLE_SCHEMA,TABLE_NAME,INDEX_NAME,SEQ_IN_INDEX) a   "
	+ "\n GROUP BY a.TABLE_SCHEMA,a.TABLE_NAME,a.INDEX_NAME;";

	 
	public MYSQL() {
	}
	public MYSQL(String condition) {
		this.condition = condition;
	}


	public void constructorEntity(
			SqlType sqlType,Map<MapEntity, List<Map<String, String>>> mapData) throws Exception {
		Set<MapEntity> set = mapData.keySet();
		for (MapEntity mapEntity : set) {
			String tabName = mapEntity.get();
			EntityModel entity = null;
			if(this.map.get(tabName)==null){
				entity = new EntityModel();
				entity.setTabName(tabName);
				entity.setComment(mapEntity.get(Sql.TABLE_COMMENT));
				entity.setEntityClassName(Util.getFirstToCase(tabName));
				entity.setEngine(mapEntity.get(Sql.ENGINE));
				entity.setOptions(mapEntity.get(Sql.CREATE_OPTIONS).length()>0);
				this.map.put(tabName.toLowerCase(), entity);
			}else{
				 entity = this.map.get(tabName);
			}
			List<Map<String, String>> listmap = mapData.get(mapEntity);
			for (int i = 0; i < listmap.size(); i++) {
				Map<String, String> map = listmap.get(i);
				TableModel column = sqlType.creatTableModel();
				column.setTableName(tabName);
				column.init(map);
				entity.setTableModel(column);
				if (null == map.get(Sql.ENGINE)) {

				}
			}
		}
	}



	@Override
	public String getUrl() {
		String url = dBConfig.getUrl();
		int len_ = url.indexOf("?");
		String head = url.substring(0, len_);
		String end = url.substring(len_, url.length());
		url = head.substring(0, head.lastIndexOf("/") + 1) + "information_schema" + end;
		return url;
	}

	@Override
	protected String getProcedureSql() {
		return "SHOW  PROCEDURE STATUS WHERE Db='"+this.dbname+"';";
	}

	@Override
	protected String getProcedureSqlCol() {
		return "Name";
	}

	@Override
	protected void initProcedureDetail() {
		for(ProcedureModel p:procedureList){
			String sql="SHOW CREATE PROCEDURE `"+p.getName()+"`";
			String sqlcol="Create Procedure";
			List<Map<String,String>> list=JDBCHelper.getListMapNoSplit(sql, sqlcol,scriptConnection);
			if(list.size()>0){
				p.setShowSql(list.get(0).get(sqlcol));
			}
		}
	}

	@Override
	protected CreateTable getCreateTable(Connection conn) {
		return new CreateTable(conn);
	}

}
