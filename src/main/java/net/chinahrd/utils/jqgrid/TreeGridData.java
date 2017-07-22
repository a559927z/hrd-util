package net.chinahrd.utils.jqgrid;

import java.io.Serializable;
import java.util.List;

/**
 * treegrid数据
 * @author guanjian
 *
 */
public class TreeGridData implements Serializable{
	
	/**  */
	private static final long serialVersionUID = -8579381258190394554L;
	private int page;
	private int records;
	private int total;
	private List<TreeGridRow> rows;
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getRecords() {
		return records;
	}
	public void setRecords(int records) {
		this.records = records;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public List<TreeGridRow> getRows() {
		return rows;
	}
	public void setRows(List<TreeGridRow> rows) {
		this.rows = rows;
	}
	
	
}
