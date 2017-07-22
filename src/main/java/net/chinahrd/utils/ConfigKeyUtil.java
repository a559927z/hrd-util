package net.chinahrd.utils;

/** 获取config.properties的Key
 * 
 * @author by jxzhang */
public class ConfigKeyUtil {

	// #基础配置
	/** 基础配置-客户自定义系统名称 */
	public static final String	XTSZ_SYSNAME			= "XTSZ.sysName";
	/** 基础配置-关闭服务后用户将无法登录系统，在系统更新或维护等时间段可选择关闭 */
	public static final String	XTSZ_ISOFFSERVICE		= "XTSZ.isOffService";
	/** 基础配置-用于接收系统异常的信息提醒(admin邮箱) */
	public static final String	XTSZ_ADMINMAIL			= "XTSZ.adminMail";
	/** 基础配置-设置系统管理员的密码(admin密码) */
	public static final String	XTSZ_ADMINPASSWORD		= "XTSZ.adminPassword";
	/** 基础配置-设置发送系统的邮箱(邮箱主机) */
	public static final String	XTSZ_EMAILHOST			= "XTSZ.eMailHost";
	/** 基础配置-设置发送系统的邮箱(邮箱端口) */
	public static final String	XTSZ_EMAILPORT			= "XTSZ.eMailPort";
	/** 基础配置-设置发送系统的邮箱(邮箱账号) */
	public static final String	XTSZ_EMAILACCOUNT		= "XTSZ.eMailAccount";
	/** 基础配置-设置发送系统的邮箱(邮箱密码) */
	public static final String	XTSZ_EMAILPASSWORD		= "XTSZ.eMailPassword";
	/** 基础配置-设置发送系统的短信(短信主机) */
	public static final String	XTSZ_MSGHOST			= "XTSZ.msgHost";
	/** 基础配置-基础配置-设置发送系统的短信(短信账号) */
	public static final String	XTSZ_MSGACCOUNT			= "XTSZ.msgAccount";
	/** 基础配置-基础配置-设置发送系统的短信(短信密码) */
	public static final String	XTSZ_MSGPASSWORD		= "XTSZ.msgPassword";

	// #首页
	/** 首页-考察加班周期(单位：周) */
	public static final String	SY_REVIEWOTWEEK			= "SY.reviewOTWeek";
	/** 首页-加班时长预警(单位：小时) */
	public static final String	SY_OTTIME				= "SY.OTTime";
	/** 首页-考察绩效次数(单位：次数) */
	public static final String	SY_REVIEWPERFMAN		= "SY.reviewPerfman";
	/** 首页-高绩效范围(单位：级别) */
	public static final String	SY_HEIGHTPERFMAN		= "SY.heightPerfman";
	/** 首页-低绩效范围(单位：级别) */
	public static final String	SY_LOWPERFMAN			= "SY.lowPerfman";
	/** 首页-入司周年(单位：年) */
	public static final String	SY_ANNUAL				= "SY.annual";

	// #人均效益
	/** 人均效益-计算公式，分母：1等效员工数，2正式员工数 */
	public static final String	RJXY_COMPUTATIONAL_FW	= "RJXY.computational-FW";
	/** 人均效益-计算公式，分子：1营业利润，2净利润，3销售额 */
	public static final String	RJXY_COMPUTATIONAL_FZ	= "RJXY.computational-FZ";
	/** 人均效益-低于行业多少时发邮件/手机信息通知。默认低于 10% */
	public static final String	RJXY_FORNOTIFY			= "RJXY.forNotify";
	/** 人均效益-当人均效益低于行业多少时通知的人员范围。1上级 2组织管理者，3HR管理者 */
	public static final String	RJXY_FORPERSON			= "RJXY.forPerson";
	/** 人均效益-当人均效益低于行业多少时通知的终端。默认低于 10% */
	public static final String	RJXY_FORTERMINAL		= "RJXY.forTerminal";

	// #人才流失风险
	/** 人才流失风险-当有人离职风险更新为红灯或者黄灯时发信息通知。1红，2黄，3红黄 */
	public static final String	RCLSFX_FORNOTIFY		= "RCLSFX.forNotify";
	/** 人才流失风险-当有人离职风险更新为红灯或者黄灯时通知的人员类型。1上级 2组织管理者，3HR管理者 */
	public static final String	RCLSFX_FORPERSON		= "RCLSFX.forPerson";
	/** 人才流失风险-当有人离职风险更新为红灯或者黄灯时通知的终端。1邮件 2短信 */
	public static final String	RCLSFX_FORTERMINAL		= "RCLSFX.forTerminal";
	/** 人才流失风险-定义“有预警提示”这个名词。1一年、2半年、3季度 */
	public static final String	RCLSFX_HASMESSAGE		= "RCLSFX.hasMessage";

	// #主动流失率
	/** 主动流失率-当流失率为红灯或者黄灯时发信息通知。 */
	public static final String	ZDLSL_FORNOTIFY			= "ZDLSL.forNotify";
	/** 主动流失率-当流失率为红灯或者黄灯时通知的人员类型。 */
	public static final String	ZDLSL_FORPERSON			= "ZDLSL.forPerson";
	/** 主动流失率-当流失率为红灯或者黄灯时通知的终端。 1邮件 2短信 */
	public static final String	ZDLSL_FORTERMINAL		= "ZDLSL.forTerminal";
	/** 主动流失率-用户输入正常值主动流失率值。 */
	public static final String	ZDLSL_NORMAL			= "ZDLSL.normal";
	/** 主动流失率-用户输入风险值主动流失率值。 */
	public static final String	ZDLSL_RISK				= "ZDLSL.risk";
	/** 主动流失率-高绩效范围。 */
	public static final String	ZDLSL_HEIGHTPERFMAN		= "ZDLSL.heightPerfman";

	// #过往绩效评估
	/** 过往绩效-高绩效范围(单位：级别) */
	public static final String	GRJXJBH_HEIGHTPERFMAN	= "GRJXJBH.heightPerfman";
	/** 过往绩效-低绩效范围(单位：级别) */
	public static final String	GRJXJBH_LOWPERFMAN		= "GRJXJBH.lowPerfman";
	/** 过往绩效-配置绩效周期。1一年、2半年、3季度 */
	public static final String	GRJXJBH_PERFMANWEEK		= "GRJXJBH.perfmanWeek";
	public static final String	GRJXJBH_V1		= "GRJXJBH.v1";
	public static final String	GRJXJBH_V2		= "GRJXJBH.v2";
	public static final String	GRJXJBH_V3		= "GRJXJBH.v3";
	public static final String	GRJXJBH_V4		= "GRJXJBH.v4";

	// #人才成本
	/** 人才成本-人类：配置计算人均成本人员范围。1全职（不包离职）、2正职、3兼职、4外包 */
	public static final String	RLCB_personType			= "RLCB.personType";

	// #关键人才库
	/** 关键人才库-用户输入登录企业资料库系统频率相比上月增高**倍次数； */
	public static final String	GJRCK_DATABASE			= "GJRCK.database";
	/** 关键人才库-请假天数相比上月增高**倍。默认为5； */
	public static final String	GJRCK_DAYOFF			= "GJRCK.dayOff";
	/** 关键人才库-当有人离职风险更新为红灯或者黄灯时发信息通知。1红，2黄，3红黄 */
	public static final String	GJRCK_FORNOTIFY			= "GJRCK.forNotify";
	/** 关键人才库-当有人离职风险更新为红灯或者黄灯时通知的人员类型。1上级 2组织管理者，3HR管理者 */
	public static final String	GJRCK_FORPERSON			= "GJRCK.forPerson";
	/** 关键人才库-当有人离职风险更新为红灯或者黄灯时通知的终端。1邮件 2短信 */
	public static final String	GJRCK_FORTERMINAL		= "GJRCK.forTerminal";
	/** 关键人才库-高职级范围。默认为4.x~~~~5.x */
	public static final String	GJRCK_HEIGHTAB			= "GJRCK.heightAB";
	/** 关键人才库-高绩效范围。默认为5 星； */
	public static final String	GJRCK_HEIGHTPERFMAN		= "GJRCK.heightPerfman";
	/** 关键人才库-人才类别是否可以添加，0不，1是。默认为1； */
	public static final String	GJRCK_ISADD				= "GJRCK.isAdd";
	/** 关键人才库-每周工作超过**小时。默认为50小时； */
	public static final String	GJRCK_WORKING			= "GJRCK.working";

	// #组织架构（编制和空缺）
	/** #组织架构（编制和空缺） - 架构超编编制(单位：百分比) */
	public static final String	ZZJGBZ_MAXVAL			= "ZZJGBZ.maxVal";

	// #人力结构
	/** 人力结构-人员统计范围，1正式/2实习/3兼职/4外包，默认：正式 */
	public static final String	RLJG_PERSONTYPE			= "RLJG.personType";
	/** 人力结构-当编制处于什么灯时预警，1黄灯/3黄灯及红灯/2红灯，默认：黄灯 */
	public static final String	RLJG_FORNOTIFY			= "RLJG.forNotify";
	/** 人力结构-当编制达到通知值时通知谁，1架构负责人/2BP，默认：BP */
	public static final String	RLJG_FORPERSON			= "RLJG.forPerson";
	/** 人力结构-通知形式，1邮件/2短信，默认：邮件 */
	public static final String	RLJG_FORTERMINAL		= "RLJG.forTerminal";
	/** 人力结构-用户输入正常值编制使用率值。默认:95% */
	public static final String	RLJG_NORMAL				= "RLJG.normal";
	/** 人力结构-用户输入风险值编制使用率值。默认:100% */
	public static final String	RLJG_RISK				= "RLJG.risk";
	
	//	#培训看板
	/** 培训看板-实际花费。1月，2季。默认:月 */
	public static final String	PXKB_outlay				= "PXKB.outlay";
	
	//	#项目人力盘点
	/** 项目人力盘点-盘点时间段。1月，2季。默认：月 */
	public static final String	XMRLPD_inventoryDate	= "XMRLPD.inventoryDate";
	
	//	#培训看板
	/** 招聘看板-招聘画像推荐人员范围，1：根据登陆人所选架构范围.2：整个公司:（不带机构条件） */
	public static final String	ZPKB_recommend				= "ZPKB.recommend";
	/** 招聘看板-最大离职周期范围类型。默认5半年  */
	public static final String	ZPKB_rodWeek				= "ZPKB.rodWeek";
	
	//	#人才损益
	/** 人才损益-管理 */
	public static final String RCSY_MGR = "RCSY.mgr";
	/** 人才损益-非管理 */
	public static final String RCSY_EMP = "RCSY.emp";
	/** 人才损益-流入 */
	public static final String RCSY_INPUT = "RCSY.input";
	/** 人才损益-流出 */
	public static final String RCSY_OUTPUT = "RCSY.output";
	
	//	#劳动力效能
	/** 劳动力效能-出勤类型 */
	public static final String LDLXN_CHECK = "LDLXN.check";
	
	// #岗位胜任度
	/** 岗位胜任度  用户输入胜任度低范围。默认:0%-60% */
	public static final String GWSRD_LOW = "GWSRD.LOW";
	/** 岗位胜任度  用户输入胜任度中范围。默认:60%-80% */
	public static final String GWSRD_MIDDLE = "GWSRD.MIDDLE";
	/** 岗位胜任度  用户输入胜任度中范围。默认:80%-100% */
	public static final String GWSRD_HIGH = "GWSRD.HIGH";
	
	//	#销售看板
	/** 销售看板-黄色预警默认值 */
	public static final String XSKB_YELLOW = "XSKB.yellow";
	/** 销售看板-红色预警默认值 */
	public static final String XSKB_RED = "XSKB.red";
	/** 销售看板-业务能力考核黄色预警默认值 */
	public static final String XSKB_YELLOW_YWNLKH = "XSKB.yellow-ywnlkh";
	/** 销售看板-业务能力考核红色预警默认值 */
	public static final String XSKB_RED_YWNLKH = "XSKB.red-ywnlkh";
	/** 销售看板-颜色配置 */
	public static final String XSKB_PZ = "XSPM.pz";
	/** 销售看板-流入 */
	public static final String XSKB_INPUT = "XSKB.input";
	/** 销售看板-流出 */
	public static final String XSKB_OUTPUT = "XSKB.output";
	
}
