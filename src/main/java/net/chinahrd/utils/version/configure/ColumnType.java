package net.chinahrd.utils.version.configure;




public enum ColumnType {
	NULL("","","NULL","","",""),
	
	CHAR("String","char","CHAR","","getString","setString"),
	VARCHAR("String","varchar","VARCHAR","","getString","setString"),
	NCHAR("String","nchar","NCHAR","","getString","setString"),
	NVARCHAR("String","nvarchar","NVARCHAR","","getString","setString"),
	BINARY("String","binary","BINARY","","getString","setString"),
	VARBINARY("String","varbinary","VARBINARY","","getString","setString"),
	TIMESTAMP("String","timestamp","TIMESTAMP","","getString","setString"),
	DATETIME("String","datetime","DATETIME","","getString","setString"),
	SMALLDATETIME("String","smalldatetime","SMALLDATETIME","","getString","setString"),
	DATE("String","date","DATE","","getString","setString"),
	
	INT("int","int","INT","","getInt","setInt"),
	BIGINT("int","bigint","BIGINT","","getInt","setInt"),
	SMALLINT("int","smallint","SMALLINT","","getInt","setInt"),
	TINYINT("int","tinyint","TINYINT","","getInt","setInt"),

	FLOAT("Float","float","FLOAT","","getFloat","setFloat"),
	DOUBLE("Double","double","DOUBLE","","getDouble","setDouble"),
	DECIMAL("Double","decimal","DECIMAL","","getDouble","setDouble"),
	NUMBER("Double","number","NUMBER","","getDouble","setDouble"),
	MONEY("BigDecimal","money","MONEY","","getBigDecimal","setBigDecimal"),
	SMALLMONEY("BigDecimal","smallmoney","SMALLMONEY","","getBigDecimal","setBigDecimal"),
	TEXT("String","text","TEXT","","getString","setString"),
	IMAGE("String","image","IMAGE","","getString","setString"),
	BIT("Boolean","bit","BIT","","getBoolean","setBoolean"),
	BLOB("String","blob","BLOB","","getString","setString"),
	LONGBLOB("String","longblob","LONGBLOB","","getString","setString"),
	MEDIUMTEXT("String","mediumtext","MEDIUMTEXT","","getString","setString");
	
	private String value;
	/**
	 * @return the setMethodName
	 */
	public String getSetMethodName() {
		return setMethodName;
	}

	private String dataBaseType;
	private String typeName;
	private String importName;
	/**
	 * ResultSet  获取该类型的方法名
	 */
	private String resultSetMethodName;
	
	
	/**
	 * ResultSet  设置该类型的方法名
	 */
	private String setMethodName;
	ColumnType(String value,String dataBaseType,String typeName,String importName,String resultSetMethodName,String setMethodName ){
		this.value=value;
		this.dataBaseType=dataBaseType;
		this.typeName=typeName;
		this.importName=importName;
		this.resultSetMethodName=resultSetMethodName;
		this.setMethodName=setMethodName;
	}
	

	public String getImportName(){
		return this.importName;
	}	
	public String getResultSetMethodName(){
		return this.resultSetMethodName;
	}	
	public String getType(){
		return this.value;
	}

	
	public String getTypeName(){
		
		return this.typeName;
	}
	
	public static ColumnType getColumnTypeByDataBaseType(String dataBaseType){
		for(ColumnType c:ColumnType.values()){
			if(c.dataBaseType.equalsIgnoreCase(dataBaseType)){
				return c;
			}
		}
		return null;
	}
	
	public static ColumnType getColumnTypeByValue(String value){
		for(ColumnType c:ColumnType.values()){
			if(c.value.equalsIgnoreCase(value)){
				return c;
			}
		}
		return null;
	}
	
	public static boolean checkType(String value){
		for(ColumnType c:ColumnType.values()){
			if(c.getType().equals(value)){
				return true;
			}
		}
		return false;
	}

}
