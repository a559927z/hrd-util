/**
*net.chinahrd.utils.version.core.entity
*/
package net.chinahrd.utils.version.core.entity;

import java.util.Map;

/**
 * @author htpeng
 *2016年9月19日下午3:25:38
 */
public interface TableModel {
	void init(Map<String, String> map);
	void setTableName(String tableName);
}
