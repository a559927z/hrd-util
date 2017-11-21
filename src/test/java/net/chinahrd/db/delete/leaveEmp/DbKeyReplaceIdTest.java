package net.chinahrd.db.delete.leaveEmp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import net.chinahrd.utils.db.DatabaseUtil;
import net.chinahrd.utils.db.DbKeyReplaceId;
import net.chinahrd.utils.db.ResultModule;

public class DbKeyReplaceIdTest {

	@Before
	public void initParam() {
	}

	@Ignore
	@Test
	public void generateSQL() throws SQLException {
		String tableName = "dim_sales_client";
		String idColumnName = "client_id";
		String keyColumnName = "client_Key";
		String rule = "SALES_CLIENT_"; // 规则
		DbKeyReplaceId.generateSQL(tableName, idColumnName, keyColumnName, rule);
	}

	@Ignore
	@Test
	public void generateSQLAllDegbut() throws SQLException {
		List<ResultModule> dimTableAll = DbKeyReplaceId.queryDimTableAll();
		System.out.println(dimTableAll.size());
		int index = 43;
		String tableName = dimTableAll.get(index).getTableName();
		if (hasFilterDimTable(tableName)) {
			String delDimStr = tableName.replace("dim_", "");
			String idColumnName = dimTableAll.get(index).getId();
			String keyColumnName = getKeyColumnName(tableName, delDimStr);
			String rule = delDimStr.toUpperCase() + "_";
			DbKeyReplaceId.generateSQL(tableName, idColumnName, keyColumnName, rule);
		}
	}
	
	
	@Ignore
	@Test
	public void generateSQLAll() throws SQLException {
		List<ResultModule> dimTableAll = DbKeyReplaceId.queryDimTableAll();
		for (int i = 0; i<dimTableAll.size(); i++) {
//		for (ResultModule rsDto : dimTableAll) {
			String tableName = dimTableAll.get(i).getTableName();
			
			System.out.println("");
			System.out.println("-- 第"+i+"张维度表: "+tableName);
			if (hasFilterDimTable(tableName)) {
				String delDimStr = tableName.replace("dim_", "");
				String idColumnName = dimTableAll.get(i).getId();
				String keyColumnName = getKeyColumnName(tableName, delDimStr);
				String rule = delDimStr.toUpperCase() + "_";
//				System.out.println(tableName + "=======" + idColumnName + "=====" + keyColumnName);
				DbKeyReplaceId.generateSQL(tableName, idColumnName, keyColumnName, rule);
			}
		}
	}
	private String getKeyColumnName(String tableName, String delDimStr) {
		List<String> notKeyTableList = notKeyTableList();
		if (notKeyTableList.contains(tableName)) {
			return "show_index";
		} else {
			return delDimStr + "_key";
		}
	}

	private List<String> notKeyTableList(){
		return Arrays.asList(
		"dim_certificate_info",
		"dim_change_type",
		"dim_checkwork_type",
		"dim_dedicat_genre",
		"dim_dismission_week",
		"dim_project_input_type",
		"dim_project_type",
		"dim_quality",
		"dim_sales_client_dimension",
		"dim_sales_product",
		"dim_sales_team",
		"dim_satfac_genre",
		"dim_structure",
		"dim_z_info"
				);
	}
	
	private boolean hasFilterDimTable(String tableName) {
		List<String> filterDimTableList = Arrays.asList(
				"dim_emp", "dim_emp_month", "dim_days", // 实时表
				"dimission_risk", "dimission_risk_item", "dim_per_exam_content",
				"dim_sales_client", "dim_customer","dim_function_item", // 待修改
				"dim_function", "dim_organization","dim_separation_risk", "dim_dedicat_genre","dim_satfac_genre"); // 自关联
		if (filterDimTableList.contains(tableName)) {
			return false;
		}
		return true;
	}
	
	@Ignore
	@Test
	public void generateShowIndexSQL() throws SQLException {
		List<ResultModule> dimTableAll = DbKeyReplaceId.queryNotShowIndexDimTableAll();
		if (dimTableAll.size() > 0) {
			for (ResultModule tableName : dimTableAll) {
				// 过滤特殊表
				// if (tableName.contentEquals("dim_ability")
				// || tableName.contentEquals("dim_certificate_info")
				// || tableName.contentEquals("dim_days")) {
				// continue;
				// }
				 DbKeyReplaceId.generateShowIndexSQL(tableName);
//				System.out.println(tableName);
			}
		}
	}
	
	@Ignore
	@Test
	public void main() throws SQLException {
		String dbName = DatabaseUtil.url.split(":3369/")[1];
		System.out.println(dbName);
	}
	

}
