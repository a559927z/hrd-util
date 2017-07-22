package net.chinahrd.utils;

import java.util.List;
import java.util.UUID;


/**
 * 构造树结构的数据集
 * @author zhiwei 
 *
 */
public class TreeStruDataUtil<T> {
	/**
	 * 将包含树形结构的数据集按照书本目录排序<p>
	 * 如：[{A},{A-1},{A-2},...{A-n},{B},{B-1},{B-2},...{B-n},...{XX-1},{XX-2},...{XX-n}]<p>
	 * 其中<p>
	 * 树的层级无限<p>
	 * 只支持单级父亲的数<p>
	 * 最终的数据结构可以包含多个单独的树
	 * @param data 源数据集合
	 * @param nodeIdName	单个节点的主键id名,默认"id"
	 * @param rootMark	判断记录为树根的字符串标记,默认"-1"
	 * @param nodeParentName 对象中获取父级id的字段名,默认"parent"
	 * @param isleaf	表示是否有叶子的字段名,若有值，则该方法会根据节点是否有子树进行判断
	 * @param level		表示节点在树中的深度，若有值，计算节点在数中的深度,0,1,2...n
	 * @param setleafrootId	[true|false]是否为单节点的树根设置ID值，是则设置UUID
	 * @param expanded
	 * @param expandedValue
	 * @return
	 */
	public List<T> constructOrderTree(List<T> data, String nodeIdName, String rootMark, 
			String nodeParentName, String isleaf, String level, boolean setleafrootId, String expanded, String expandedValue){
		List<T> tem= CollectionKit.newList();
		for (T d:data){
			//从树根出发
			if((Str.IsEmpty(rootMark)?"-1":rootMark).equals(
							Reflections.invokeGetter(d, 
									Str.IsEmpty(nodeParentName)?"parent".intern():nodeParentName))
									)
			{
				//树根无parent
				Reflections.invokeSetter(d, 
						Str.IsEmpty(nodeParentName)?"parent".intern():nodeParentName, null);
				//root深度为0
				if(!Str.IsEmpty(level))
					Reflections.invokeSetter(d, level, "0".intern());
				if(!Str.IsEmpty(expanded))
					Reflections.invokeSetter(d, expanded, expandedValue);
				//记录是否为叶子节点
				boolean leaf = false;
				if(!Str.IsEmpty(isleaf)){
					leaf = Boolean.valueOf(hasLeaf(data, Reflections.invokeGetter(d, nodeIdName).toString(), nodeParentName));
					Reflections.invokeSetter(d, isleaf, String.valueOf(leaf));
				}
				//是叶子节点重写id，使用uuid
				if(setleafrootId && leaf ){
					Reflections.invokeSetter(d, nodeIdName, UUID.randomUUID().toString());
				}
				tem.add(d);
				setLevel(tem, data, String.valueOf(Reflections.invokeGetter(d, nodeIdName)), 0,nodeIdName, nodeParentName, isleaf, level, setleafrootId, expanded, expandedValue);
			}
		}
		return tem;
		
	}
	private  String hasLeaf(List<T> data, String id, String nodeParentName){
		boolean b=true;
		for(T d:data){
			if(id.equals(Reflections.invokeGetter(d, Str.IsEmpty(nodeParentName)?"parent".intern():nodeParentName))){
				b=false;
				break;
			}
		}
		return String.valueOf(b).intern();
	}
	private  void setLevel(List<T> tem,List<T> data, String id, Integer levelCount, String nodeIdName, String nodeParentName, String isleaf, String level, boolean setleafrootId, String expanded, String expandedValue){
		levelCount++;
		for (T d:data){
			if(id.equals(Reflections.invokeGetter(d, nodeParentName))){
				if(!Str.IsEmpty(expanded))
					Reflections.invokeSetter(d, expanded, expandedValue);

				if(!Str.IsEmpty(level))
					Reflections.invokeSetter(d, level, String.valueOf(levelCount));
				boolean leaf = false;

				if(!Str.IsEmpty(isleaf)){
					leaf = Boolean.valueOf(hasLeaf(data, Reflections.invokeGetter(d, nodeIdName).toString(), nodeParentName));
					Reflections.invokeSetter(d, isleaf, String.valueOf(leaf));
				}
				//是叶子节点重写id，使用uuid
				if(setleafrootId && leaf ){
					Reflections.invokeSetter(d, nodeIdName, UUID.randomUUID().toString());
				}
				tem.add(d);
				setLevel(tem, data, String.valueOf(Reflections.invokeGetter(d, nodeIdName)), levelCount,nodeIdName, nodeParentName, isleaf, level, setleafrootId, expanded, expandedValue);
			}
		}
	}
}
