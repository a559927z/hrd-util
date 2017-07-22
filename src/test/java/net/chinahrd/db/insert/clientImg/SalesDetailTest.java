//package net.chinahrd.db.insert.clientImg;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//
//import org.junit.Before;
//import org.junit.Ignore;
//import org.junit.Test;
//
//import net.chinahrd.utils.db.DatabaseUtil;
//
//public class SalesDetailTest {
//	
//	public static String url = "jdbc:mysql://172.16.9.50:3369/mup-zrw-test";
//	private static String user = "mup";
//	private static String password = "1q2w3e123";
//	private static String driverClassName = "com.mysql.jdbc.Driver";
////	public static String url = "jdbc:mysql://42.62.24.7:3369/mup";
////	private static String user = "mup";
////	private static String password = "1a2s3d123";
//
//	@Before
//	public void initParam() {
//	}
//
//	 @Ignore
//	@Test
//	public void insertSalesDetail() throws SQLException {
//		String dbName = DatabaseUtil.url.split(":3369/")[1];
//		List<String> orderIds = queryOrderIds();
//		List<ProductEntry> products = queryProducts();
//		
//		int maxProduct = products.size();
//		
//		for (String orderId : orderIds) {
//			Random r1 = new Random();
//			int len = r1.nextInt(maxProduct);
//			
//			for (int i = 0; i < len; i++) {
//				int indexProduct = new Random().nextInt(maxProduct);
//				ProductEntry entry = products.get(indexProduct);
//				String productId = entry.getProduct_id();
//				Double salesMoney = Double.parseDouble(entry.getProduct_price());
//				Double salesProfit = salesMoney- Double.parseDouble(entry.getProduct_cost());
//				StringBuffer sql = new StringBuffer();
//				sql.append("insert into `").append(dbName).append("`.sales_detail"); 
//				sql.append("(customer_id, product_id, sales_order_id, product_number, sales_money, sales_profit)");
//				sql.append("values(");
//				sql.append("1").append(",")
//					.append(productId).append(",")
//					.append(orderId).append(",")
//					.append(1).append(",")
//					.append(salesMoney).append(",")
//					.append(salesProfit).append(");");
//				
//				System.out.println(sql.toString());
//			}
//		}
//		
//	}
//	
//	private List<String> queryOrderIds() throws SQLException {
//		String salesOrderSQL = "select sales_order_id from sales_order";
//		ResultSet rs = DatabaseUtil.query(salesOrderSQL, url, user, password, driverClassName);
//		List<String> orderIds = new ArrayList<String>();
//		while (rs.next()) {
//			orderIds.add(rs.getString("sales_order_id"));
//		}
//		return orderIds;
//	};
//	
//	private List<ProductEntry> queryProducts() throws SQLException {
//		String salesProductSQL = 
//				"select product_id, product_price, product_cost from dim_sales_product where product_price is not null AND product_cost is not null";
//		ResultSet rs = DatabaseUtil.query(salesProductSQL, url, user, password, driverClassName);
//		List<ProductEntry> orderIds = new ArrayList<ProductEntry>();
//		while (rs.next()) {
//			ProductEntry p = new ProductEntry();
//			p.setProduct_id(rs.getString("product_id"));
//			p.setProduct_price(rs.getString("product_price"));
//			p.setProduct_cost(rs.getString("product_cost"));
//			orderIds.add(p);
//		}
//		return orderIds;
//	};
//	
//	
//	
//	
//	
//}
