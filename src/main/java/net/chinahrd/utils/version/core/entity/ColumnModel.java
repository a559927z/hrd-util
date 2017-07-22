package net.chinahrd.utils.version.core.entity;

import java.util.Map;


import net.chinahrd.utils.version.sql.Sql;
import net.chinahrd.utils.version.util.Convert;
import net.chinahrd.utils.version.util.Util;


public class ColumnModel implements TableModel{
	private String tableName = ""; // 表名
	private String colname = ""; // 列名
	private String dbtype; // 数据类型  (数据库中)
	private long length = -1; // 长度
	private int prec = 0; // 整数位
	private int scale = 0; // 小数位
	private String defaultValue ; // 默认值
	private String value = ""; // 值
	private String isNULLABLE = "";
	private String comment = "";
	private String getMethodName = "";
	private String setMethodName = "";
	// private ForeignKeyModel foreignKey=null;

	@Override
	public String toString() {
		return "ColumnModel [ colname=" + colname
				+ ", dbtype=" + dbtype + ", length="
				+ length + ", prec=" + prec + ", scale=" + scale
				+ ", defaultValue=" + defaultValue + ", value=" + value
				+ "]";
	}


	public String getEntityname() {
////		String[] arr=colname.split("_");
////		StringBuffer sb=new StringBuffer();
////		for(String str :arr){
////			if(sb.length()>0){
////				sb.append(str.toUpperCase());
////			}else{
////				sb.append(str);
////			}
////		}
//		return sb.toString();
		return colname;
	}

	public String getGetMethodName() {
		return getMethodName;
	}

	public String getSetMethodName() {
		return setMethodName;
	}

	public String isNULLABLE() {
		return isNULLABLE.equals("YES")?" NULL":" NOT NULL";
	}





	public ColumnModel() {

	}


	public String getDbtype() {
		return dbtype;
	}


	public void getUpdateVlalue(StringBuffer str) {
		str.append("," + colname + "=?");
	}

	public String getColname() {
		return colname;
	}

	public void setColname(String colname) {
		this.colname = colname;
		getMethodName();
	}

	private void getMethodName(){
		this.getMethodName = Util.getGMethodNameByName(colname);
		this.setMethodName = Util.getSMethodNameByName(colname);
	}

	public long getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getPrec() {
		return prec;
	}

	public void setPrec(int prec) {
		this.prec = prec;
	}

	public int getScale() {
		return scale;
	}


	public String getDefaultValue() {
		return  defaultValue!=null&&defaultValue.length()>0?
				" DEFAULT "+(this.dbtype.equals("double")
							||this.dbtype.equals("decimal")
							||this.dbtype.equals("float")
							||this.dbtype.equals("int")
							||this.dbtype.equals("tinyint")
							?defaultValue:"'"+defaultValue+"'")
				:"";
	}


	public String getComment() {
		return  comment!=null&&comment.length()>0?(" COMMENT '"+comment+"'"):"";
	}


	public boolean equals(Object anObject) {
		if (anObject instanceof ColumnModel) {
			ColumnModel e = (ColumnModel) anObject;
			return this.colname.equals(e.colname) && this.value.equals(e.value);
		}
		return false;
	}

	
	private String getDateType(){
		if(this.dbtype.equals("double")
				||this.dbtype.equals("decimal")
				||this.dbtype.equals("float")
				){
			return dbtype+"("+prec+","+scale+")";
		}else if(this.dbtype.equals("int")
				||this.dbtype.equals("tinyint")
				){
			return dbtype+"("+prec+")";
			
		}else if(this.dbtype.equals("varchar")
				||this.dbtype.equals("char")
				){
			return dbtype+"("+length+")";
		}else if(this.dbtype.equals("datetime")){
			return dbtype;
		}
		return dbtype;
		
	}
	
	/* (non-Javadoc)
	 * @see net.chinahrd.utils.version.core.entity.TableModel#init(java.util.Map, net.chinahrd.utils.version.sql.Sql)
	 */
	@Override
	public void init(Map<String, String> map) {
		this.colname =map.get(Sql.COLUMN_NAME);
		getMethodName();
		this.dbtype=map.get(Sql.COLUMN_TYPE);
		this.length =Convert.filterLog( map.get(Sql.LENGTH));
		this.prec = Convert.filterInteger(map.get(Sql.PREC));
		this.scale =  Convert.filterInteger(map.get(Sql.SCALE));
		this.defaultValue =  map.get(Sql.COLUMN_DEFAULT);
		this.isNULLABLE =  map.get(Sql.COLUMN_IS_NULL);
		this.comment=  map.get(Sql.COLUMN_COMMENT);
	}



	/**
	 * 
	 */
	public String compare(ColumnModel columnModel) {
		if(this.dbtype.equals(columnModel.dbtype)&&
				this.length==columnModel.length&&
				this.isNULLABLE.equals(columnModel.isNULLABLE)&&
				this.prec==columnModel.prec&&
				this.scale==columnModel.scale&&
				this.defaultValue.equals(columnModel.defaultValue)&&
				this.comment.equals(columnModel.comment)
				){
			return null;
		}
		return "ALTER TABLE `"+tableName+"` MODIFY `"+colname+"` "+getDateType()+isNULLABLE()+getDefaultValue()+getComment()+";";
		
	}
	public String add() {
	
		return "ALTER TABLE `"+tableName+"` ADD COLUMN `"+colname+"` "+getDateType()+isNULLABLE()+getDefaultValue()+getComment()+";";
	}



	/* (non-Javadoc)
	 * @see net.chinahrd.utils.version.core.entity.TableModel#setTableName(java.lang.String)
	 */
	@Override
	public void setTableName(String tableName) {
		// TODO Auto-generated method stub
		this.tableName=tableName;
	}
}
