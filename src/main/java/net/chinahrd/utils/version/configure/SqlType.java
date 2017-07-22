package net.chinahrd.utils.version.configure;


import net.chinahrd.utils.version.core.entity.ColumnModel;
import net.chinahrd.utils.version.core.entity.IndexModel;
import net.chinahrd.utils.version.core.entity.TableModel;


public enum SqlType {
	COLUMN(ColumnModel.class),
	INDEX(IndexModel.class);

	private Class<? extends TableModel> clazz;
	SqlType(Class<? extends TableModel> clazz){
		
		this.clazz=clazz;
	}


	public TableModel creatTableModel(){
		TableModel obj=null;
		try {
			obj = this.clazz.newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return obj;
	}
}