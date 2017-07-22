package net.chinahrd.utils.version.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.chinahrd.utils.version.core.entity.DBConfig;
import net.chinahrd.utils.version.core.entity.EntityModel;
import net.chinahrd.utils.version.core.entity.ProcedureModel;
import net.chinahrd.utils.version.sql.AlloteSQL;
import net.chinahrd.utils.version.sql.Sql;


/**
 * 生成数据库sql
 * @author htpeng
 *2017年4月12日下午3:21:25
 */
public class ConstuctorDatabaseScript {

	private static String dbconfigOld= "default_database_old";
	private static String dbconfigNew= "default_database_new";
	public static void init() {
		init(dbconfigOld,dbconfigNew);
	}

	public static void init(String dbconfigOld,String dbconfigNew) {
		Sql n_sql=AlloteSQL.allotSQL(new DBConfig(dbconfigNew)).setScript(true).init();
		Sql o_sql=AlloteSQL.allotSQL(new DBConfig(dbconfigOld)).setScript(true).init();
		createEntity(
				o_sql.getEntityModel(),
				n_sql.getEntityModel(),
				o_sql.getProcedureModel(false),
				n_sql.getProcedureModel(true)
				);
	}

	
	public static void main(String[] ar) {
		init();
	}

	private static List<String> compareTable(Map<String, EntityModel> mapOld,Map<String, EntityModel> mapNew){
		List<String> list=new ArrayList<String>();
		for (String tableName_n : mapNew.keySet()) {
			EntityModel entityModel_n = mapNew.get(tableName_n);
			boolean exist=false;
			for (String tableName_o : mapOld.keySet()) {
				if(tableName_o.equals(tableName_n)){
					EntityModel entityModel_o = mapOld.get(tableName_o);
					exist=true; 
					list.addAll(entityModel_n.compare(entityModel_o));
					break;
				}
			}
			if(!exist){
				//查询show table
				list.add("-- ----------------------------");
				list.add("-- Table structure for "+tableName_n);
				list.add("-- ----------------------------");
				list.add(entityModel_n.createTable());
				list.add("");
			}
		}
		
		list.add("-- ----------------------------");
		list.add("-- 删除表 -----------------------");
		list.add("-- ----------------------------");
		
		for (String tableName_o : mapOld.keySet()) {
			boolean exist=false;
			EntityModel entityModel_o = mapOld.get(tableName_o);
			for (String tableName_n : mapNew.keySet()) {
				if(tableName_o.equals(tableName_n)){
					exist=true; 
					break;
				}
			}
			if(!exist){
				//删除表
				list.add("-- ----------------------------");
				list.add("-- Drop Table "+tableName_o);
				list.add("-- ----------------------------");
				list.add(entityModel_o.deleteTable());
				list.add("");
			}
		}
		return list;
	}
	/**
	 * @param conn_n
	 * @param conn_o
	 * @param mapOld
	 * @param mapNew
	 * @return
	 */
	private static List<String> compareProcedure(
			List<ProcedureModel> list_o,
			List<ProcedureModel> list_n) {
		// TODO Auto-generated method stub
		List<String> list=new ArrayList<String>();
		list.add("-- ----------------------------");
		list.add("-- 删除存储过程");
		list.add("-- ----------------------------");
		for(ProcedureModel p:list_o){
			list.add(p.getDeleteSql());
		}
		list.add("-- ----------------------------");
		list.add("-- 添加删除存储过程");
		list.add("-- ----------------------------");
		for(ProcedureModel p:list_n){
			list.add("-- ----------------------------");
			list.add("-- Procedure structure for "+p.getName());
			list.add("-- ----------------------------");
			list.add("DELIMITER //");
			list.add(p.getShowSql()+"//");
		    list.add("DELIMITER ;");
			list.add("");
		}
		return list;
	}

	private static void createEntity(
			Map<String, EntityModel> mapOld,
			Map<String, EntityModel> mapNew,
			List<ProcedureModel> list_o,
			List<ProcedureModel> list_n
			) {
		List<String> list=new ArrayList<String>();
		list.add("-- ----------------------------");
		list.add("-- 表结构同步");
		list.add("-- ----------------------------");
		list.addAll(compareTable(mapOld,mapNew));
		list.add("-- ----------------------------");
		list.add("-- 存储过程同步");
		list.add("-- ----------------------------");
		list.addAll(compareProcedure(list_o,list_n));
		try {
			FileWriter fw=new FileWriter(new File("D:\\SQL1.txt"));
			for(String str:list){
				System.out.println(str);
				str.replaceAll("\n", "\r\n");
				fw.write(str+"\r\n");
			}
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}



}
