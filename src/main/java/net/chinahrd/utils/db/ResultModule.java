package net.chinahrd.utils.db;

import java.io.Serializable;

/**
 * 结果集DTO
 * 
 * @author jxzhang on 2017年3月28日
 * @Verdion 1.0 版本
 */
public class ResultModule implements Serializable {

	private static final long serialVersionUID = 1431767194428491300L;
	private String tableName;
	private String id;
	private String key;
	private String showIndex;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getShowIndex() {
		return showIndex;
	}

	public void setShowIndex(String showIndex) {
		this.showIndex = showIndex;
	}

}
