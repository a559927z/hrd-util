package net.chinahrd.utils.version.core.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.chinahrd.utils.version.sql.Sql;




public class IndexModel implements TableModel{
	private String tableName = ""; // 实体类对应的名字
	
	private String indexName="";
	private String colName="";

	private String _colName="";
	public String getIndexName() {
		return indexName;
	}

	@Override
	public String toString() {
		return "IndexModel [tableName=" + tableName + ", indexName="
				+ indexName + ", colName=" + colName + "]";
	}

	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}

	public String getColName() {
		return colName;
	}

	public void setColName(String colName) {
		this.colName=colName;
		String[] arr=colName.split(",");
		List<String> list=new ArrayList<String>();
		for(String str:arr){
			String trim=str.trim();
			if(trim.length()>0){
				list.add("`"+trim+"`");
			}
		}
		String _colName="";
		for(String str:list){
			_colName+=","+str;
		}
		this._colName = _colName.replaceFirst(",", "");
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}


	/* (non-Javadoc)
	 * @see net.chinahrd.utils.version.core.entity.TableModel#init(java.util.Map, net.chinahrd.utils.version.sql.Sql)
	 */
	@Override
	public void init(Map<String, String> map) {
		// TODO Auto-generated method stub
		this.indexName =map.get(Sql.INDEX_NAME);
		this.setColName(map.get(Sql.INDEX_COL));
	}

	/**
	 * @param indexModel_o
	 */
	public List<String> compare(IndexModel indexModel_o) {
		List<String> list =new ArrayList<String>();
		String[] _n=this.colName.split(",");
		String[] _o=indexModel_o.colName.split(",");
		boolean isAlert=true;
		if(_n.length==_o.length){
			int count=0;
			for(int i=0;i<_n.length;i++){
				for(int j=0;j<_o.length;j++){
					if(_n[i].equals(_o[j])){
						count++;
						break;
					}
				}
			}
			if(count==_n.length){
				isAlert=false;
			}
		}
		if(isAlert){
			if(indexName.equals("PRIMARY")){
				list.add("ALTER TABLE `"+tableName+"` DROP PRIMARY KEY;");
			}else{
				list.add("ALTER TABLE `"+tableName+"` DROP INDEX `"+indexName+"`;");
			}
			
			list.add(add());
		}
		return list;
		
	}

	/**
	 * @return
	 */
	public String add() {
		if(indexName.equals("PRIMARY")){
			return "ALTER TABLE `"+tableName+"` ADD PRIMARY KEY ("+_colName+");";
		}
		return "CREATE INDEX "+indexName+" ON `"+tableName+"` ("+_colName+");";
	}

	
}
