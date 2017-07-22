package net.chinahrd.utils.version.core.entity;

import java.util.HashMap;
import java.util.Map;
/**
 *  
 * @author mx__sword
 *
 */
public class MapEntity {
	private String key="";
	private Map<String,String> valueMap=new HashMap<String,String>();
	public MapEntity(String key){
		this.key=key;
	}
	public int hashCode(){
		return key.hashCode();
	}
	
	 public boolean equals(Object anObject) {
		 if(anObject instanceof MapEntity){
			 MapEntity e=(MapEntity)anObject;
			 return key.equals(e.key);
		 }
		 return false;
	 }
	 
	 public String toString( ) {
		 return key;
	 }
	
	public void put(String key,String value){
		if(null==valueMap.get(key)){
			valueMap.put(key,value);
		}else{
			if(valueMap.get(key).trim().length()==0){
				valueMap.put(key,value);
			}
		}
	}
	public String get(){
		return key;
	}
	public String get(String key){
		return valueMap.get(key);
	}
}
