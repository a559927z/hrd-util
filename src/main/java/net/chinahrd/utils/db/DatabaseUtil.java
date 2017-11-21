package net.chinahrd.utils.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 连接数据操作工具类
 * 
 * @author jxzhang on 2017年1月2日
 * @Verdion 1.0 版本
 */
public class DatabaseUtil {

	public static final Logger log = LoggerFactory.getLogger(DatabaseUtil.class);

	private Connection conn;
	private Statement st;
	private ResultSet rs;

	private String driver = "";

	public DatabaseUtil(JdbcDto dto) {
		conn = DatabaseUtil.getConnectionJDBC(dto.getUrl(), dto.getUsername(), dto.getPassword(),
				dto.getDriverClassName());
		driver = driverClassName;
	}

	public DatabaseUtil(String url, String user, String password, String driverClassName) {
		conn = DatabaseUtil.getConnectionJDBC(url, user, password, driverClassName);
		driver = driverClassName;
	}

	public String getDriver() {
		return driver;

	}

	/**
	 * 所有连接参数
	 */
	// local
	// public static String url = "jdbc:mysql://127.0.0.1:3369/mup";
	// private static String user = "root";
	// private static String password = "root";
	// private static String driverClassName = "com.mysql.jdbc.Driver";

	// gz
	// public static String url = "jdbc:mysql://172.16.9.50:3369/mup-large";
	// private static String user = "mup";
	// private static String password = "1q2w3e123";
	// private static String driverClassName = "com.mysql.jdbc.Driver";

	// bj
	public static String url = "jdbc:mysql://42.62.24.7:3369/mup";
	private static String user = "mup";
	private static String password = "1a2s3d123";
	private static String driverClassName = "com.mysql.jdbc.Driver";

	/**
	 * 通过 tomcat pool来连接数据库
	 * 
	 * @param args
	 * @return
	 */
	private static DataSource getDatasoureceTomcat(String... args) {
		if (args.length > 0) {
			url = args[0];
			user = args[1];
			password = args[2];
			driverClassName = args[3];
		}

		PoolProperties p = new PoolProperties();
		p.setUrl(url);
		p.setDriverClassName(driverClassName);
		p.setUsername(user);
		p.setPassword(password);
		p.setJmxEnabled(true);
		p.setTestWhileIdle(false);
		p.setTestOnBorrow(true);
		p.setValidationQuery("SELECT 1");
		p.setTestOnReturn(false);
		p.setValidationInterval(30000);
		p.setTimeBetweenEvictionRunsMillis(30000);
		p.setMaxActive(100);
		p.setInitialSize(10);
		p.setMaxWait(10000);
		p.setRemoveAbandonedTimeout(60);
		p.setMinEvictableIdleTimeMillis(30000);
		p.setMinIdle(10);
		p.setLogAbandoned(true);
		p.setRemoveAbandoned(true);
		p.setJdbcInterceptors("org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;"
				+ "org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer");
		DataSource datasource = new DataSource();
		datasource.setPoolProperties(p);
		return datasource;
	}

	/**
	 * 创建数据库的连接
	 * 
	 * @param args
	 * @return
	 */
	private static Connection getConnectionJDBC(String... args) {
		if (args.length > 0) {
			url = args[0];
			user = args[1];
			password = args[2];
			driverClassName = args[3];
		}
		try {
			// 加载驱动类
			Class.forName(driverClassName);
		} catch (ClassNotFoundException e) {
			System.out.println("找不到驱动程序类 ，加载驱动失败！");
			e.printStackTrace();
		}
		Connection conn = null;
		try {
			return DriverManager.getConnection(url, user, password);
		} catch (SQLException se) {
			System.out.println("数据库连接失败！");
			se.printStackTrace();
		}
		return conn;
	}

	/**
	 * 获得连接
	 * 
	 * @param args
	 *            url,user,password,driverClassName
	 * @return
	 * @throws SQLException
	 */
	public static Connection getConnection(String... args) throws SQLException {
		Connection conn = null;
		conn = getConnectionJDBC(args[0], args[1], args[2], args[3]);
		return conn;
	}

	/**
	 * 关闭连接
	 * 
	 * @param conn
	 */
	public void close() {
		try {
			if (conn != null) {
				conn.close();
			}

			if (st != null) {
				st.close();
			}

			if (rs != null) {
				rs.close();
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			rs = null;
			st = null;
			conn = null;
		}
	}

	/**
	 * 发送查询SQL
	 * 
	 * @param sql
	 * @param url
	 * @param user
	 * @param password
	 * @return
	 */
	public ResultSet query(String sql) throws Exception {
		System.out.println(sql);
		st = conn.createStatement();
		rs = st.executeQuery(sql);
		return rs;
	}

	/**
	 * 发送更新SQL
	 * 
	 * @param sql
	 * @param url
	 * @param user
	 * @param password
	 * @return
	 * @throws SQLException
	 */
	public void saveOrUpdate(String sql) throws SQLException {
		// log.debug(sql);
		Statement st = null;
		try {
			st = conn.createStatement();
			st.executeUpdate(sql);
		} catch (SQLException e) {
			throw new SQLException(e);
		} finally {
			if (null != st) {
				try {
					st.close();
				} catch (SQLException e) {

				}
			}
		}
	}

	/**
	 * 删除
	 * 
	 * @param sql
	 * @return
	 */
	public int delete(String sql) {
		
		int result = 0;
		try {
			st = conn.createStatement();
			result = st.executeUpdate(sql);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
		}
		return result;
	}

	public void main(String[] args) throws SQLException {
		Connection connection = getConnectionJDBC();
		System.out.println(connection);
	}
}
