package net.chinahrd.utils.version.sql;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import net.chinahrd.utils.version.configure.SqlType;
import net.chinahrd.utils.version.core.entity.CreateTable;
import net.chinahrd.utils.version.core.entity.MapEntity;
import net.chinahrd.utils.version.core.entity.SqlEntity;

public class SQL_SERVER extends SqlSimple {
	public SQL_SERVER(){
//		sqlcol="tab.tab_name,tab.col_name,tab.type_name,tab.col_length,tab.type_length,tab.prec,tab.scale,tab.use_name,con.text";
//		sql="\n SELECT " +sqlcol+
//		 		"\n from (SELECT tab.name tab_name,col.name col_name,t.name type_name,col.length col_length,t.length type_length,col.prec,col.scale,u.name use_name,col.cdefault " +
//			 	"\n FROM syscolumns col ,systypes t,sysobjects tab,sysusers u " +
//			 	"\n where col.usertype=t.usertype and col.id=tab.id and tab.type='U'and tab.uid=u.uid )tab " +
//			 	"\n left join syscomments con on tab.cdefault=con.id " +
//			 	"\n ORDER BY tab.tab_name";	
	}



	@Override
	public String getUrl() {
		// TODO Auto-generated method stub
		return dBConfig.getUrl();
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
}
