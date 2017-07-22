package net.chinahrd.utils.version.sql;

import java.util.HashMap;
import java.util.Map;

import net.chinahrd.utils.version.core.entity.EntityModel;


public class EntityModelTool {
	public EntityModelTool(){
		
	}
	public EntityModelTool(Map<String, EntityModel> map){
		this.map=map;
	}
	private static Map<String, EntityModel> map = new HashMap<String, EntityModel>();
	
	public EntityModel getEntityModel(String tabName) {
//		EntityModel entity = map.get(tabName.toLowerCase());
		EntityModel entity = map.get(tabName);
		if(entity==null){
			entity = map.get(tabName.toLowerCase());
		}
		if (null == entity) {
			//throw new BaseException("����" + tabName + "����ȷ�������δ��ʼ�����߻�ȡ��ݿ�����ʧ��");
		}
		return entity;
	}
}
