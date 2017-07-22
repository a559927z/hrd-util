package net.chinahrd.utils.version.core.entity;

import net.chinahrd.utils.version.util.ResourceHelp;


public class DBConfig extends ResourceHelp{
	private static String CONFIG_urlConfigName="connection.url";
	private static String CONFIG_userConfigName="connection.username";
	private static String CONFIG_passwordConfigName="connection.password";
	private static String CONFIG_driveConfigName="connection.driver_class";
	private static String CONFIG_showsql="connection.showsql";
	private static String CONFIG_ID="connection.id";
	private static String MAPPER="mapper";
	
	private String username;
	private String password;
	private String url;
	private String driverClass;
	private String dbname;
	private String id;
	private boolean showSql = false;
	private String[] relSqlMapperConfig;
//
//	public DBConfig() {
//
//	}
	private void setDbname(){
	    dbname=url.substring(0,url.indexOf("?"));
	    dbname=dbname.substring(dbname.lastIndexOf("/")+1,dbname.length());
	}
	public DBConfig(String resourceName) {
		super(resourceName);
		url=get(CONFIG_urlConfigName);
		username=get(CONFIG_userConfigName);
	    password=get(CONFIG_passwordConfigName);
	    driverClass=get(CONFIG_driveConfigName);
	    id=get(CONFIG_ID);
	    showSql=Boolean.valueOf(get(CONFIG_showsql));
	    relSqlMapperConfig=get(MAPPER,"");

	    setDbname();
	}
	public DBConfig(String username, String password, String url,
			String driverClass) {
		this(username,password,url,driverClass,"",false);
	}

	public DBConfig(String username, String password, String url,
			String driverClass, boolean showSql) {
		this(username,password,url,driverClass,"",showSql);
	}

	public DBConfig(String username, String password, String url,
			String driverClass, String id) {
		this(username,password,url,driverClass,id,false);
	}

	public DBConfig(String username, String password, String url,
			String driverClass, String id, boolean showSql) {
		super();
		this.username = username;
		this.password = password;
		this.url = url;
		this.id = id;
		this.driverClass = driverClass;
		this.showSql = showSql;
		setDbname();
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDriverClass() {
		return driverClass;
	}

	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}

	public boolean isShowSql() {
		return showSql;
	}

	public void setShowSql(boolean showSql) {
		this.showSql = showSql;
	}
	public String getDbname() {
		return dbname;
	}
	public void setDbname(String dbname) {
		this.dbname = dbname;
	}
	public String[] getRelSqlMapperConfig() {
		return relSqlMapperConfig;
	}
	public void setRelSqlMapperConfig(String[] relSqlMapperConfig) {
		this.relSqlMapperConfig = relSqlMapperConfig;
	}

	
	
}
