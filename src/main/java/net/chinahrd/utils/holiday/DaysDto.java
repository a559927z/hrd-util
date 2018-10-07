package net.chinahrd.utils.holiday;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author jxzhang on 2017年2月13日
 * @Verdion 1.0 版本
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
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

}