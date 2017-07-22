package net.chinahrd.utils.version.sql;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.chinahrd.utils.version.configure.SqlType;
import net.chinahrd.utils.version.core.JDBCHelper;
import net.chinahrd.utils.version.core.entity.CreateTable;
import net.chinahrd.utils.version.core.entity.DBConfig;
import net.chinahrd.utils.version.core.entity.EntityModel;
import net.chinahrd.utils.version.core.entity.MapEntity;
import net.chinahrd.utils.version.core.entity.ProcedureModel;
import net.chinahrd.utils.version.core.entity.SqlEntity;
import net.chinahrd.utils.version.util.ConnectionTool;


/**
 * 
 * @author mx__sword
 * 
 */
public abstract class SqlSimple extends JDBCHelper implements Sql {

	protected Map<String, EntityModel> map = new HashMap<String, EntityModel>();
	protected List<ProcedureModel> procedureList = new ArrayList<ProcedureModel>();

	protected String dbname = null;
	protected DBConfig dBConfig;
	private  List<SqlEntity> list;
	private  boolean  initProcedureDetail=false;
//	protected abstract String getSql();
	protected abstract List<SqlEntity> initSqlList();
	protected abstract String getProcedureSql();
	protected abstract String getProcedureSqlCol();
//	protected abstract String getSqlcol();
//	protected abstract void setSql(String sql);
	//创建实体
	protected abstract void constructorEntity(SqlType sqlType,Map<MapEntity, List<Map<String, String>>> mapData)
			throws Exception;
	//初始化过程
	protected abstract void initProcedureDetail();
	//初始化表分区
	protected abstract CreateTable getCreateTable(Connection conn);
	/**
	 * 脚本sql连接
	 */
	protected Connection scriptConnection;
	/**
	 * 是否查询sql脚本
	 */
	private boolean script=false;
	

	public SqlSimple(){
		
	}
	
	public SqlSimple(DBConfig dBConfig){
		this.dBConfig=dBConfig;
	}
	
	@Override
	public void setDBConfig(DBConfig dBConfig){
		this.dBConfig=dBConfig;
	}
	
	public Sql setScript(boolean script){
		this.script=script;
		return this;
	}
	/**
	 *
	 * 
	 * @throws Exception
	 */
	@Override
	public Sql init() {
		list=initSqlList();
		this.dbname = dBConfig.getDbname();
		if(null!=list){
			for(SqlEntity sql:list){
				sql.setSql(sql.getSql().replace("?", dbname));
				
				DBConfig clone=new DBConfig(dBConfig.getUsername(),dBConfig.getPassword(),getUrl(),dBConfig.getDriverClass(),"",false);
				try {
					constructorEntity(sql.getSqlType(),JDBCHelper.findRowtoColBySql(sql.getSql(),
							sql.getSqlcol(), ConnectionTool.getConn(clone), sql.getGroupCol(), Sql.ENGINE, Sql.CREATE_OPTIONS,Sql.TABLE_COMMENT));
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if(script){
				scriptConnection=ConnectionTool.getConn(dBConfig);
				for(String tab:map.keySet()){
					map.get(tab).setCreateTable(getCreateTable(scriptConnection));
				}
				try {
					constructorProcedure(JDBCHelper.getListMapNoSplit(getProcedureSql(),
							getProcedureSqlCol(),scriptConnection));
				} catch (Exception e) {
					e.printStackTrace();
				}finally{
					try {
						scriptConnection.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return this;
	}
	
	public Map<String, EntityModel> getDataModel(){
		return map;
	}
	public abstract String getUrl();

	protected void constructorProcedure(List<Map<String, String>> listmap)
			throws Exception {
		for(Map<String,String> map:listmap){
			ProcedureModel procedureModel=new ProcedureModel();
			procedureModel.setName(map.get(getProcedureSqlCol()));
			this.procedureList.add(procedureModel);
		}
	}





//	public int getScale(Map<String, String> map) {
//		return Convert.filterInteger(map.get(scale));
//	}


	
	@Override
	public Map<String, EntityModel> getEntityModel() {
		// TODO Auto-generated method stub
		return map;
	}
	@Override
	public List<ProcedureModel> getProcedureModel(boolean detail) {
		// TODO Auto-generated method stub
		if(!initProcedureDetail&&detail){
			initProcedureDetail=true;
			initProcedureDetail();
		}
		return procedureList;
	}

}
