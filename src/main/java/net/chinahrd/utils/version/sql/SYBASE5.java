package net.chinahrd.utils.version.sql;


import java.sql.Connection;
import java.util.List;
import java.util.Map;

import net.chinahrd.utils.version.configure.SqlType;
import net.chinahrd.utils.version.core.entity.CreateTable;
import net.chinahrd.utils.version.core.entity.MapEntity;
import net.chinahrd.utils.version.core.entity.SqlEntity;


class SYBASE5 extends SqlSimple{
	protected String table_name="tab_name";
	protected String column_name="col_name";
	protected String column_default="text";
	protected String column_is_null="";
	protected String column_type="type_name";
	protected String length="col_length";
	protected String prec="prec";
	protected String scale="scale";
	protected String primaryKey="";
	private String sqlcol="tab.tab_name,tab.col_name,tab.type_name,tab.col_length,tab.type_length,tab.prec,tab.scale,tab.use_name,con.text";
	private String sql="\n SELECT " +sqlcol+
	 		"\n from (SELECT tab.name tab_name,col.name col_name,t.name type_name,col.length col_length,t.length type_length,col.prec,col.scale,u.name use_name,col.cdefault " +
		 	"\n FROM syscolumns col ,systypes t,sysobjects tab,sysusers u " +
		 	"\n where col.usertype=t.usertype and col.id=tab.id and tab.type='U'and tab.uid=u.uid )tab " +
		 	"\n left join syscomments con on tab.cdefault=con.id " +
		 	"\n ORDER BY tab.tab_name";	


	public String getSqlcol() {
		return sqlcol;
	}

	public void setSqlcol(String sqlcol) {
		this.sqlcol = sqlcol;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql=sql;
	}

	public String getColumn_default(Map<String, String> map) {
		String defaultValue=map.get(column_default);
		int indexof=defaultValue.indexOf("DEFAULT");
		if(indexof>-1){
			defaultValue=defaultValue.substring(indexof+7,defaultValue.length()).replaceAll("'", "");
		}
		return defaultValue.trim();
	}


//	public Map<MapEntity, List<Map<String, String>>> getDateBaseTable() throws Exception {
//		JDBCHelper.findRowtoColBySql(sql, sqlcol,conn,table_name);
//		return null;
//	}
	
	/**
	 * ����tabʵ����
	 * @param map
	 * @throws Exception 
	 */
	public void constructorEntity(Map<MapEntity,List<Map<String, String>>> mapData) throws Exception {
//		Set<MapEntity> set=mapData.keySet();
//		for(MapEntity mapEntity:set){
//			EntityModel entity=new EntityModel();
//			String tabName=mapEntity.get();
//			entity.setTabName(tabName);
//			entity.setPrimaryKey(getPrimaryByTabName(tabName));
//			List<Map<String,String>> listmap=mapData.get(mapEntity);
//			for(int i=0;i<listmap.size();i++){
//				ColumnModel column=new ColumnModel(listmap.get(i),this);
//				entity.setColList(column);
//			}
//			this.map.put(tabName.toLowerCase(), entity);
//		}
	}
	
	private String getPrimaryByTabName(String tabname) throws Exception{
//		initDataBaseConntion();
//		String sql="SELECT index_col( '"+tabname+"',a.indid,b.colid) PK "+
//			"\n FROM sysindexes a ,syscolumns b "+
//			"\n WHERE a.id=object_id( '"+tabname+"')  and  a.status & 2048=2048  "+
//			"\n and b.id=object_id( '"+tabname+"')  "+
//			"\n and isnull(index_col( '"+tabname+"',a.indid,b.colid),'')<>''";
//		List<Map<String,String>> listmap=JDBCHelper.getListMap(sql,"PK",conn);
//		if(listmap.size()==1){
//			return listmap.get(0).get("PK");
//		}else{
//			return "";
//		}
		return "";
	}

 
	@Override
	public String getUrl() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see net.chinahrd.utils.version.sql.SqlSimple#initSqlList()
	 */
	@Override
	protected List<SqlEntity> initSqlList() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see net.chinahrd.utils.version.sql.SqlSimple#getProcedureSql()
	 */
	@Override
	protected String getProcedureSql() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see net.chinahrd.utils.version.sql.SqlSimple#getProcedureSqlCol()
	 */
	@Override
	protected String getProcedureSqlCol() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see net.chinahrd.utils.version.sql.SqlSimple#constructorEntity(net.chinahrd.utils.version.configure.SqlType, java.util.Map)
	 */
	@Override
	protected void constructorEntity(SqlType sqlType,
			Map<MapEntity, List<Map<String, String>>> mapData) throws Exception {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see net.chinahrd.utils.version.sql.SqlSimple#initProcedureDetail()
	 */
	@Override
	protected void initProcedureDetail() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see net.chinahrd.utils.version.sql.SqlSimple#getCreateTable(java.sql.Connection)
	 */
	@Override
	protected CreateTable getCreateTable(Connection conn) {
		// TODO Auto-generated method stub
		return null;
	}

//	@Override
//	public List<Map<String, String>> pageFind(String sql, String sqlcol,
//			Page page, Connection conn) throws Exception {
//		// TODO Auto-generated method stub
//		return null;
//	}

}
