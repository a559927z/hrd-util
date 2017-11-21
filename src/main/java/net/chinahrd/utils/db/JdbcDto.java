package net.chinahrd.utils.db;

import java.io.Serializable;

/**
 * Title: JdbcDto <br/>
 * Description: 
 * 
 * @author jxzhang
 * @DATE 2017年11月20日 下午8:50:49
 * @Verdion 1.0 版本
 */
public class JdbcDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String url;
	private String username;
	private String password;
	private String driverClassName;
	
	private String dbName;

	public JdbcDto() {
		super();
	}

	public JdbcDto(String url, String username, String password, String driverClassName) {
		super();
		this.url = url;
		this.username = username;
		this.password = password;
		this.driverClassName = driverClassName;
	}

	
	public JdbcDto(String url, String username, String password, String driverClassName, String dbName) {
		super();
		this.url = url;
		this.username = username;
		this.password = password;
		this.driverClassName = driverClassName;
		this.dbName = dbName;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDriverClassName() {
		return driverClassName;
	}

	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}

}
