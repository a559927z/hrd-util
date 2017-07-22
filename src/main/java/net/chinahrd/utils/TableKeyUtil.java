package net.chinahrd.utils;

/**
 * 获取config.properties的Key
 *
 * @author by jxzhang on 2016-02-23
 */
public class TableKeyUtil {

    // #字典表
    /**
     * 主序列-管理序列
     * @see 根据客户真实数据来修改config.properties
     */
    public static final String DIM_SEQUENCE_KEY_MGRSEQ = PropertiesUtil.getProperty("DIM_SEQUENCE_KEY_MGRSEQ");
    /**
     * 学历分组查询条件
     */
    public static final String CODE_GROUP_DEGREE = "degree";
    /**
     * 员工性格查询条件
     */
    public static final String CODE_GROUP_PERSONALITY = "personality";

    //todo 中人网系统ID 角色“员工”ID
    public static final String ZRW_CUSTOMER_ID = "1";
    public static final String YG_ROLE_ID = "6951df2d7c444cd08e0dd3fe47ed104b";
    /**
     * 深圳同事提醒
     **/
    public static final String SHENZHEN = "深圳";
    /**
     * 广州分公司下员工
     */
    public static final String ORGAN_ID = "d7fd6b524e5111e6999590b11c32f63c";

}
