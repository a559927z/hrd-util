package net.chinahrd.utils.version.core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.chinahrd.utils.version.core.entity.MapEntity;


public class JDBCHelper {
	 
public static Map<MapEntity,List<Map<String, String>>> findRowtoColBySql(String sql,
				String sqlcol, Connection conn,String compareColById,String...cols) throws Exception {
			List<Map<String, String>> listmap =getListMap(sql, sqlcol,conn);
			Map<MapEntity,List<Map<String, String>>> map=getRowTOArraylistMap(listmap, compareColById,cols);
			return map;
		}
public static Map<MapEntity,List<Map<String,String>>> getRowTOArraylistMap(List<Map<String,String>> listMap,String compareColById,String...cols){
	Map<MapEntity,List<Map<String,String>>> resultMap= new HashMap<MapEntity,List<Map<String,String>>>();
	String name=new String();
	int len=listMap.size();
	for(int i=0;i<len;i++){
		Map<String,String> map=listMap.get(i);
		String col=map.get(compareColById);
		MapEntity mapEntity=new MapEntity(col); 
		if(null!=cols){
			for(int j=0;j<cols.length;j++){
				mapEntity.put(cols[j],map.get(cols[j]));
			}
		}
		if(!name.equals(col)){
			List<Map<String,String>> objList=new ArrayList<Map<String,String>>(); 
			objList.add(map);
			resultMap.put(mapEntity, objList);
			name=col;
		}else{
			List<Map<String,String>> objList=resultMap.get(mapEntity); 
			objList.add(map);
		}
	}
	return resultMap;
}

/**
 * 根据sql,sqlcol和conn查询sql 封装成List<Map<String,String>>返回
 * @param sql
 * @param sqlcol
 * @param conn
 * @return
 * @throws Exception
 */
public static List<Map<String, String>> getListMap(String sql,
		String sqlcol, Connection conn) throws Exception {
	 List<Map<String, String>> listmap = new ArrayList<Map<String, String>>();

	 PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			printSql(conn,sql);
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			listmap = getlistMap(rs, sqlcol);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(conn,rs, ps);
		}
	 
	return listmap;
}


public static List<Map<String, String>> getlistMap(ResultSet rs,
			String liemcstr) throws Exception {
		ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();
		while (rs != null && rs.next()) {
			String[] liearr = liemcstr.split(",");
			Map<String, String> map = new LinkedHashMap<String, String>();
			for (int dd = 0; dd < liearr.length; dd++) {
				String liemc = liearr[dd].trim();
				liemc = liemc.substring(liemc.indexOf(".") + 1, liemc.length());
				liemc = liemc.substring(liemc.indexOf(" ") + 1, liemc.length());
				// 为null的时候设置为""
				if (rs.getString(liemc) == null) {
					map.put(liemc.trim(), "");
				} else {
					map.put(liemc.trim(), rs.getString(liemc).trim());
				}
			}
			list.add(map);
		}
		return list;
	}
	
public static void printSql(Connection conn ,String sql){

	 System.out.println("JDBC:  "+sql);
 }
public static void close(Connection conn, ResultSet rs, PreparedStatement ps) {
	 
	 if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (ps != null) {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
/**
 * 根据sql,sqlcol和conn查询sql 封装成List<Map<String,String>>返回
 * @param sql
 * @param sqlcol
 * @param conn
 * @return
 * @throws Exception
 */
public static List<Map<String, String>> getListMapNoSplit(String sql,
		String sqlcol, Connection conn)  {
	 List<Map<String, String>> listmap = new ArrayList<Map<String, String>>();

	 PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			printSql(conn,sql);
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			listmap = getlistMapNoSplit(rs, sqlcol);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(null,rs, ps);
		}
	 
	return listmap;
}


public static List<Map<String, String>> getlistMapNoSplit(ResultSet rs,
			String liemcstr) throws Exception {
		ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();
		while (rs != null && rs.next()) {
			String[] liearr = liemcstr.split(",");
			Map<String, String> map = new LinkedHashMap<String, String>();
			for (int dd = 0; dd < liearr.length; dd++) {
				String liemc = liearr[dd].trim();
				// 为null的时候设置为""
				if (rs.getString(liemc) == null) {
					map.put(liemc.trim(), "");
				} else {
					map.put(liemc.trim(), rs.getString(liemc).trim());
				}
			}
			list.add(map);
		}
		return list;
	}
}
