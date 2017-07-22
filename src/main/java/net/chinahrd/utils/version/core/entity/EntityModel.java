package net.chinahrd.utils.version.core.entity;

import java.util.ArrayList;
import java.util.List;

import net.chinahrd.utils.version.util.Util;

public final class EntityModel  {
	private String user = ""; // 数据库用户角色
	private String tabName = ""; // 表名
	private String primaryKey = ""; // 主键
	private String database = ""; // 数据库名称
	private String comment ="";//表备注
	private String entityClassName = ""; // 实体类名称
	private boolean options = false; // 是否有表分区表分区

	private List<IndexModel> indexList = new ArrayList<IndexModel>();  //索引信息
	private List<ColumnModel> colList = new ArrayList<ColumnModel>();  //列信息
	private ColumnModel primaryCol = new ColumnModel(); // 主键对应的column
	
	private CreateTable createTable;
	private String engine = "";
	@Override
	public String toString(){
		return "tabName : "+tabName+",primaryKey : "+primaryKey+",database : "+database+",entityClassName:"+entityClassName;
		
	}
	
	public String getEntityClassName() {
		return entityClassName;
	}

	public void setEntityClassName(String entityClassName) {
		String[] arr =entityClassName.split("_");
		StringBuffer sb=new StringBuffer();
		for(String str:arr){
			if(str.length()>0){
				sb.append(Util.getFirstToCase(str));
			}
		}
		this.entityClassName = sb.toString();
	}



	public ColumnModel getPrimaryCol() {
		return primaryCol;
	}

	public void setPrimaryCol(ColumnModel primaryCol) {
		this.primaryCol = primaryCol;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getTabName() {
		return tabName;
	}


	public void setTabName(String tabName) {
		this.tabName = tabName;
	}

	public String getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(String primaryKey) {
		if (null == primaryKey)
			primaryKey = "";
	
		this.primaryKey = primaryKey;
	}

	public List<ColumnModel> getColList() {
		return colList;
	}


	

	public boolean isOptions() {
		return options;
	}

	public void setOptions(boolean options) {
		this.options = options;
	}

	public List<IndexModel> getIndexList() {
		return indexList;
	}



	/**
	 * @param column
	 */
	public void setTableModel(TableModel column) {
		if(column instanceof ColumnModel){
			this.colList.add((ColumnModel)column);
		}else if(column instanceof IndexModel){
			this.indexList.add((IndexModel)column);
		}
		
	}

	public List<String> compare(EntityModel entityModel){
		List<String> list =new ArrayList<String>();
		boolean exist=false;
		boolean addHeard=false;
		boolean tableOptions=false;
		if(!this.engine.equals(entityModel.engine)){
			if(!addHeard){
				addHeard=true;
				list.add("-- ----------------------------");
				list.add("-- Table structure for "+tabName);
				list.add("-- ----------------------------");
			}
			list.add("ALTER TABLE `"+this.tabName+"` ENGINE='"+this.engine+"';");
		}
		if(this.options!=entityModel.options){
			tableOptions=true;
			if(entityModel.options){
				//删除原有表分区
				if(!addHeard){
					addHeard=true;
					list.add("-- ----------------------------");
					list.add("-- Table structure for "+tabName);
					list.add("-- ----------------------------");
				}
				list.add(entityModel.createTable.deleteOptions());
			}
		}else if(this.options==entityModel.options&&this.options){
			tableOptions=true;
			//对比表分区
			if(this.createTable.getCreateOptions().equals(entityModel.createTable.getCreateOptions())){
				tableOptions=false;
			}else{
				if(!addHeard){
					addHeard=true;
					list.add("-- ----------------------------");
					list.add("-- Table structure for "+tabName);
					list.add("-- ----------------------------");
				}
				list.add(entityModel.createTable.deleteOptions());
			}
		}
		
		for (ColumnModel columnModel : this.getColList()) {
			exist=false;
			for (ColumnModel columnModel_o : entityModel.getColList()) {
				if(columnModel.getColname().equals(columnModel_o.getColname())){
					exist=true;
					String o=columnModel.compare(columnModel_o);
					if(o!=null){
						if(!addHeard){
							addHeard=true;
							list.add("-- ----------------------------");
							list.add("-- Table structure for "+tabName);
							list.add("-- ----------------------------");
						}
						list.add(o);
					}
				}
			}
			if(!exist){
				//添加该列
				if(!addHeard){
					addHeard=true;
					list.add("-- ----------------------------");
					list.add("-- Table structure for "+tabName);
					list.add("-- ----------------------------");
				}
				list.add(columnModel.add());
			}
		}
		for (IndexModel indexModel : this.getIndexList()) {
			exist=false;
			for (IndexModel indexModel_o : entityModel.getIndexList()) {
				if(indexModel.getIndexName().equals(indexModel_o.getIndexName())){
					exist=true;
					List<String> result=indexModel.compare(indexModel_o);
					if(!addHeard&&result.size()>0){
						addHeard=true;
						list.add("-- ----------------------------");
						list.add("-- Table structure for "+tabName);
						list.add("-- ----------------------------");
					}
					list.addAll(result);
				}
			}
			if(!exist){
				//添加该索引
				if(!addHeard){
					addHeard=true;
					list.add("-- ----------------------------");
					list.add("-- Table structure for "+tabName);
					list.add("-- ----------------------------");
				}
				list.add(indexModel.add());
			}
		}
		if(tableOptions){
			if(this.options){
				//修改表分区
				if(!addHeard){
					addHeard=true;
					list.add("-- ----------------------------");
					list.add("-- Table structure for "+tabName);
					list.add("-- ----------------------------");
				}
				list.add(this.createTable.alertOptions());
			}
		}
		
		if(list.size()>0){
			list.add("");
		}
		return list;
	}

	/**
	 * 获取建表sql
	 * @param createTable
	 */
	public void setCreateTable(CreateTable createTable) {
		this.createTable = createTable;
		createTable.setTableName(tabName);
		this.createTable.init();
	}

	public String getEngine() {
		return engine;
	}

	public void setEngine(String engine) {
		this.engine = engine;
	}

	/**
	 * @param conn 
	 * @return
	 */
	public String createTable() {
		return createTable.show();
	}

	/**
	 * @return
	 */
	public String deleteTable() {
		return createTable.deleteTable();
	}

	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}


}
