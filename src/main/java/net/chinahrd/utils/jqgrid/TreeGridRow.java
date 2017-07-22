package net.chinahrd.utils.jqgrid;

import java.io.Serializable;

/**
 * treegrid一行的数据
 * @author guanjian
 *
 */
public class TreeGridRow implements Serializable{
	/**  */
	private static final long serialVersionUID = -7529051830044448555L;
	private String id;
	private Object[] cell;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Object[] getCell() {
		return cell;
	}
	public void setCell(Object[] cell) {
		this.cell = cell;
	}
	
	

}
