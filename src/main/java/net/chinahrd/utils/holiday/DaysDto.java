package net.chinahrd.utils.holiday;

import java.io.Serializable;

/**
 * @author jxzhang on 2017年2月13日
 * @Verdion 1.0 版本
 */
public class DaysDto implements Serializable {
	private static final long serialVersionUID = -4651378683376163459L;

	private String days;
	/**
	 * 是否节假日
	 */
	private int isVacation = 0;
	/**
	 * 是否工作日
	 */
	private int isWorkFlag = 0;
	/**
	 * 是否休息日(周六,周日)
	 */
	private int isHoliday = 0;

	public String getDays() {
		return days;
	}

	public void setDays(String days) {
		this.days = days;
	}

	public int getIsVacation() {
		return isVacation;
	}

	public void setIsVacation(int isVacation) {
		this.isVacation = isVacation;
	}

	public int getIsWorkFlag() {
		return isWorkFlag;
	}

	public void setIsWorkFlag(int isWorkFlag) {
		this.isWorkFlag = isWorkFlag;
	}

	public int getIsHoliday() {
		return isHoliday;
	}

	public void setIsHoliday(int isHoliday) {
		this.isHoliday = isHoliday;
	}

}