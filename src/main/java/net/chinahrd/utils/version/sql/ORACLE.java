package net.chinahrd.utils.version.sql;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.chinahrd.utils.version.configure.SqlType;
import net.chinahrd.utils.version.core.entity.CreateTable;
import net.chinahrd.utils.version.core.entity.MapEntity;
import net.chinahrd.utils.version.core.entity.SqlEntity;

class ORACLE extends SqlSimple {

	public ORACLE() {
		
	}
	private String sqlcol = "tab.tab_name,tab.col_name,tab.type_name,tab.col_length,tab.type_length,tab.prec,tab.scale,tab.use_name,con.text";
	private String  sqlstr = "\n SELECT "
			+ sqlcol
			+ "\n from (SELECT tab.name tab_name,col.name col_name,t.name type_name,col.length col_length,t.length type_length,col.prec,col.scale,u.name use_name,col.cdefault "
			+ "\n FROM syscolumns col ,systypes t,sysobjects tab,sysusers u "
			+ "\n where col.usertype=t.usertype and col.id=tab.id and tab.type='U'and tab.uid=u.uid )tab "
			+ "\n left join syscomments con on tab.cdefault=con.id "
			+ "\n ORDER BY tab.tab_name";
	@Override
	protected List<SqlEntity> initSqlList() {
		List<SqlEntity> list=new ArrayList<SqlEntity>();
		SqlEntity sql=new SqlEntity(SqlType.COLUMN);
		list.add(sql);
		sql.setSql(sqlstr);
		sql.setSqlcol(sqlcol);
		sql.setGroupCol("TABLE_NAME");
		return list;
	}


	@Override
	public void constructorEntity(SqlType sqlType,
			Map<MapEntity, List<Map<String, String>>> mapData) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public String getUrl() {
		// TODO Auto-generated method stub
		return dBConfig.getUrl();
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

}
