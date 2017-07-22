package net.chinahrd.utils.holiday;

import java.util.List;

/**
 * 获取节假日
 * 
 * @author jxzhang on 2016年11月30日
 * @Verdion 1.0 版本
 */
public interface GetDays {

	/**
	 * @param endDay
	 *            结束计算日期 如 2018-01-01
	 * @param startDay
	 *            开始计算日期 如 2017-01-01 
	 *            
	 * @return	计算2017年节假日
	 */
	public List<DaysDto> calcDays(String endDay, String startDay);

}
