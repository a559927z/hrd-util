package net.chinahrd.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.*;
import org.springframework.beans.factory.annotation.Value;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * 时间工具类
 *
 * @author htpeng 2016年9月22日上午11:28:42
 */
@Slf4j
public class DateUtil {

    @Value("dbtest")
    private static boolean test; // 是否使用测试时间


    // 2015-12-18 09:45:22
    private static long deaultTimeLog = 1450403122691L;
    // 2016-06-01 00:00:00
    // private static long deaultTimeLog = 1464710400000L;
    private static Timestamp timestamp = new Timestamp(deaultTimeLog);

    /**
     * 默认日期格式
     */
    private static final String FORMATE_DATE = "yyyy-MM-dd";
    // private static final String FORMATE_SECONDS = "HH:mm:ss";
    private static final String FORMATE_SECONDS2 = "hh:mm:ss";
    private static final String FORMATE_DATEYMD = "yyyy年MM月dd日";

    public static InnerDateUtil init(int y, int m, int d) {
        return new InnerDateUtil(y, m, d);
    }

    /**
     * Title: InnerDateUtil <br/>
     * Description:
     *
     * @author jxzhang
     * @DATE 2017年12月27日 下午6:41:14
     * @Verdion 1.0 版本
     */
    @SuppressWarnings("unused")
    private static class InnerDateUtil {
        private int y;
        private int m;
        private int d;

        private String ys;
        private String ms;
        private String ds;

        public InnerDateUtil(int y, int m, int d) {
            super();
            this.y = y;
            this.m = m;
            this.d = d;
            this.ys = String.valueOf(y);
            this.ms = String.valueOf(m);
            this.ds = String.valueOf(d);
        }

        public int yearInt() {
            return y;
        }

        public int yearMonthInt() {
            return Integer.parseInt(ys + ms);
        }

        public int dayInt() {
            return d;
        }

        public String ymdLine() {
            System.out.println(y + "_" + m + "_" + d);
            if (m <= 9 && m >= 1) {
                this.ms = "0" + ms;
            }
            if (d <= 9 && d >= 1) {
                this.ds = "0" + ds;
            }
            return StringUtils.join(ys, "-", ms, "-", ds);
        }
    }

    /**
     * 获取服务器所需当前时间
     *
     * @return
     */
    public static String getServerDate(String pattern) {
        DateTime dt = new DateTime(System.currentTimeMillis());
        return dt.toString(pattern);
    }

    /**
     * 获取系统 前几年的时间
     *
     * @param pattern
     * @return
     */
    public static String getBeforeYear(String pattern, int num) {
        long t;
        if (test) {
            t = deaultTimeLog;
        } else {
            t = System.currentTimeMillis();
        }
        DateTime dt = new DateTime(t);
        return dt.minusYears(num).toString(pattern);
    }

    /**
     * 获取系统 前一天时间
     *
     * @param pattern
     * @return
     */
    public static String getBeforeDay(String pattern) {
        long t;
        if (test) {
            t = deaultTimeLog;
        } else {
            t = System.currentTimeMillis();
        }
        DateTime dt = new DateTime(t);
        return dt.minusDays(1).toString(pattern);
    }

    /**
     * 获取系统当前时间
     *
     * @param pattern
     * @return
     */
    public static String getDBTime(String pattern) {
        long t;
        if (test) {
            t = deaultTimeLog;
        } else {
            // 因为系统要看昨天数据，所以当前时间-1天
            DateTime dt = new DateTime(System.currentTimeMillis());
            // dt.minusDays(1);
            t = dt.toDate().getTime();
        }
        DateTime dt = new DateTime(t);
        return dt.toString(pattern);
    }

    /**
     * 获取系统当前时间 == CURDATE()
     *
     * @return
     */
    public static String getDBCurdate() {
        return getDBTime(FORMATE_DATE);
    }

    /**
     * 获取系统当前时间 == NOW()
     *
     * @return
     */
    public static String getDBNow() {
        return getDBTime(FORMATE_DATE + " " + FORMATE_SECONDS2);
    }

    /**
     * 获取系统当前时间
     *
     * @return
     */
    public static Date getDate() {
        if (test) {
            return timestamp;
        }
        return new Date(System.currentTimeMillis());
    }

    /**
     * 获取系统当前时间
     *
     * @return
     */
    public static Timestamp getTimestamp() {
        if (test) {
            return timestamp;
        }
        return new Timestamp(System.currentTimeMillis());
    }

    /**
     * 获取前几个月的yearMonth值，如：传入参数yearMonth为201506，offsetMonth为2，结果则返回201504;
     * 若offsetMonth为-2，则返回201508
     *
     * @param yearMonth   年月值
     * @param offsetMonth 往前推几个月（正数为以前年月，负数为将来年月）
     * @return
     */
    public static int getPreYearMonth(int yearMonth, int offsetMonth) {
        DateTime dt = new DateTime(yearMonth / 100, yearMonth % 100, 1, 0, 0, 0);
        String preYmStr = dt.minusMonths(offsetMonth).toString("yyyyMM");
        return Integer.parseInt(preYmStr);
    }

    /**
     * 获取前几年值
     *
     * @param yearMonth
     * @param offsetMonth
     * @return
     */
    public static int getPreYear(int yearMonth, int offsetMonth) {
        return yearMonth / 100 - offsetMonth;
    }

    /**
     * 转换时间为Int类
     *
     * @param obj new Date() 或者 new DateTime() 或者 '2015-09-06'
     * @return 201509
     * @author jxzhang on 2016-12-15
     */
    public static int convertToIntYearMonth(Object obj) {
        if (obj instanceof DateTime) {
            DateTime dt = (DateTime) obj;
            int year = dt.getYear();
            int monthOfYear = dt.getMonthOfYear();
            String monthOfYearStr = monthOfYear <= 9 ? "0" + monthOfYear : monthOfYear + "";
            String ymStr = year + monthOfYearStr;
            return Integer.valueOf(ymStr);
        } else if (obj instanceof String) {
            String str = (String) obj;
            String[] split = str.split("-");
            if (!(split.length >= 2)) {
                return 0;
            }
            String y = split[0];
            String m = split[1];
            if (m.startsWith("0")) {
                str = y + m;
            } else if (m.contains("10") || m.contains("11") || m.contains("12")) {
                // 1-9月可能没有0的处理
                str = y + m;
            } else {
                str = y + "0" + m;
            }
            int rs = Integer.parseInt(str);
            return rs;
        } else if (obj instanceof Date) {
            DateTime dt = new DateTime((Date) obj);
            int year = dt.getYear();
            int monthOfYear = dt.getMonthOfYear();
            String monthOfYearStr = monthOfYear <= 9 ? "0" + monthOfYear : monthOfYear + "";
            String ymStr = year + monthOfYearStr;
            return Integer.valueOf(ymStr);
        } else {
            try {
                throw new UtilsException("传入参数对像不在 DateTime、Date、String");
            } catch (UtilsException e) {
                log.debug(e.toString());
                return 0;
            }
        }
    }

    /**
     * 通过指定数字计算年月
     * <p>
     * 作用:获取上一个月: getCalculateInt(new Date(), -1)
     *
     * @param date      '2016-10-04'
     * @param monthsNum -3
     * @return DateTime
     * @author jxzhang on 2016-12-15
     */
    public static DateTime getCalculate(Date date, int monthsNum) {
        DateTime minusMonths = null;
        if (monthsNum > 0) {
            minusMonths = new DateTime(date).plusMonths(monthsNum);
        } else {
            minusMonths = new DateTime(date).minusMonths(Math.abs(monthsNum));
        }
        return minusMonths;
    }

    /**
     * 通过指定数字计算年月
     * <p>
     * 作用:获取上一个月: getCalculateInt(new Date(), -1)
     *
     * @param date      '2016-10-04'
     * @param monthsNum -3
     * @return 201607
     * @author jxzhang on 2016-12-15
     */
    public static int getCalculateInt(Date date, int monthsNum) {
        return convertToIntYearMonth(getCalculate(date, monthsNum));
    }

    /**
     * 通过指定数字计算年月
     * <p>
     * 作用:获取上一个月: getCalculateInt(new Date(), -1)
     *
     * @param date      '2016-10-04'
     * @param monthsNum -3
     * @return '2016-07-04'
     * @author jxzhang on 2016-12-15
     */
    public static Date getCalculateDate(Date date, int monthsNum) {
        return getCalculate(date, monthsNum).toDate();
    }

    /**
     * 转换时间为Int类
     *
     * @param obj new Date() 或者 new DateTime() 或者 '2015-09-06'
     * @return 906
     * @author jxzhang on 2016-12-15
     */
    public static int convertToIntMonthDay(Object obj) {
        if (obj instanceof DateTime) {
            DateTime dt = (DateTime) obj;
            int year = dt.getYear();
            int monthOfYear = dt.getMonthOfYear();
            String monthOfYearStr = monthOfYear <= 9 ? "0" + monthOfYear : monthOfYear + "";
            String ymStr = year + monthOfYearStr;
            return Integer.valueOf(ymStr);
        } else if (obj instanceof String) {
            String str = (String) obj; // TODO 可能越界
            str = str.split("-")[1] + str.split("-")[2];
            int rs = Integer.parseInt(str);
            return rs;
        } else if (obj instanceof Date) {
            DateTime dt = new DateTime((Date) obj);
            int monthOfYear = dt.getMonthOfYear();
            int dayOfMonth = dt.getDayOfMonth();
            String dayOfMonthStr = dayOfMonth <= 9 ? "0" + dayOfMonth : dayOfMonth + "";
            String mdStr = monthOfYear + dayOfMonthStr;
            return Integer.valueOf(mdStr);
        } else {
            try {
                throw new UtilsException("传入参数对像不在 DateTime、Date、String");
            } catch (UtilsException e) {
                log.debug(e.toString());
                return 0;
            }
        }
    }

    /**
     * 获取当前季度 by jxzhang
     *
     * @param date
     * @return
     */
    public static int getQuarter(Date date) {
        DateTime dt = new DateTime(date);
        int rs = dt.getMonthOfYear();
        if (rs >= 1 && rs <= 3) {
            return 1;
        } else if (rs >= 4 && rs <= 6) {
            return 2;
        } else if (rs >= 7 && rs <= 9) {
            return 3;
        } else {
            return 4;
        }
    }

    /**
     * 计算两个日期间隔的
     *
     * @param start
     * @param end
     * @param type  1:日、2:月、3秒
     * @return 两个日期间隔的(天数 、 月数 、 秒数)
     * @author jxzhang
     */
    public static int getBetweenDiff(Date start, Date end, int type) {
        LocalDate startLd = new LocalDate(start);
        LocalDate endLd = new LocalDate(end);
        switch (type) {
            case 1:
                return Days.daysBetween(startLd, endLd).getDays();
            case 2:
                return Months.monthsBetween(startLd, endLd).getMonths();
            case 3:
                return Seconds.secondsBetween(startLd, endLd).getSeconds();
            default:
                return 0;
        }
    }

    /**
     * 计算时间相差多少 天，小时，分钟，秒 by jxzhang
     *
     * @param start 开始时间
     * @param end   结束时间
     * @return 时间相差：多少 天，小时，分钟，秒
     */
    public static String getInterval(Date start, Date end) {
        Period p = getPeriod(start, end);
        return "时间相差：" + p.getDays() + " 天 " + p.getHours() + " 小时 " + p.getMinutes() + " 分钟" + p.getSeconds() + " 秒";
    }

    /**
     * 一个不可变的时间指定一组持续时间字段值。
     *
     * @param start 开始时间
     * @param end   结束时间
     * @return
     */
    private static Period getPeriod(Date start, Date end) {
        Interval interval = new Interval(start.getTime(), end.getTime());
        return interval.toPeriod();
    }

    /**
     * 获得某个月有多少天
     *
     * @param dataObj
     * @return
     */
    public static int getMonthMaxDay(Object dataObj) {
        // TODO string 要做验证格式
        if (dataObj instanceof DateTime || dataObj instanceof String || dataObj instanceof Date) {
            return new DateTime(dataObj).dayOfMonth().getMaximumValue();
        }
        return 0;
    }

    /**
     * 是否月的第一天 by jxzhang on 2016-03-07
     *
     * @param d
     * @return
     */
    public static boolean isBeginMonth(Date d) {
        return isBeginMonth(new DateTime(d));
    }

    /**
     * 是否月的最后一天 by jxzhang on 2016-4-22
     *
     * @param d
     * @return
     */
    public static boolean isLastMonth(Date d) {
        return isLastMonth(new DateTime(d));
    }

    /**
     * 是否月的第一天 by jxzhang on 2016-03-07
     *
     * @param dt
     * @return
     */
    public static boolean isBeginMonth(DateTime dt) {
        int dayOfMonth = dt.getDayOfMonth();
        if (1 == dayOfMonth) {
            return true;
        }
        return false;
    }

    /**
     * 是否月的最后一天 by jxzhang on 2016-4-22
     *
     * @param dt
     * @return
     */
    public static boolean isLastMonth(DateTime dt) {
        LocalDate d = LocalDate.now();
        String lastDayOfPreviousMonth = d.dayOfMonth().withMaximumValue().toString(FORMATE_DATE, Locale.CHINESE);
        String now = DateTime.now().toString(FORMATE_DATE, Locale.CHINESE);

        if (lastDayOfPreviousMonth.equals(now)) {
            return true;
        }
        return false;
    }

    /**
     * date to string
     *
     * @param date
     * @param pattern yyyy-MM-dd
     * @return <br>
     * @author jxzhang on 2016-03-18
     */
    public static String dateToStr(Date date, String pattern) {
        return new DateTime(date).toString(pattern);
    }

    /**
     * string to date
     *
     * @param dateStr
     * @return <br>
     * @author jxzhang on 2016-03-18
     */
    public static Date strToDate(String dateStr) {
        return new DateTime(dateStr).toDate();
    }

    /**
     * date to timestamp
     *
     * @param date
     * @return <br>
     * @author jxzhang on 2016-03-18
     */
    public static Timestamp dateToTimestamp(Date date) {
        return new Timestamp(date.getTime());
    }

    public static int getYearHead() {
        String y = new DateTime(DateUtil.getDBCurdate()).getYear() + "";
        return Integer.parseInt(y + "01");
    }

    public static int getYearLast() {
        String y = new DateTime(DateUtil.getDBCurdate()).getYear() + "";
        return Integer.parseInt(y + "12");
    }

    /**
     * 年月转上半年下半年;例如 201504 --》2015上半年 ； 201507--》2015下半年
     *
     * @param list
     * @return
     */
    public static List<String> getYearMonthTohHalfs(List<String> list) {
        List<String> result = CollectionKit.newList();
        for (String str : list) {
            result.add(getYearMonthTohHalf(str));
        }
        return result;
    }

    /**
     * 年月转上半年下半年;例如 201504 --》2015上半年 ； 201507--》2015下半年
     *
     * @param date
     * @return
     */
    public static String getYearMonthTohHalf(String date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateUtil.strToDate(date));
        String year = date.substring(0, 4);
        int month = -1;
        if (date.charAt(4) == '-') {
            month = Integer.parseInt(date.substring(5, 7));
        } else {
            month = Integer.parseInt(date.substring(4, 6));
        }

        if (month < 7) {
            return year + "上半年";
        } else {
            return year + "下半年";
        }
    }

    /**
     * 获取中文的日期描述
     *
     * @param date
     * @param format:年，年月，月日
     * @return
     */
    public static String getDate4CN(String date, String format) {
        if (!Str.IsEmpty(format)) {
            Date d = DateUtil.strToDate(date);
            Calendar cl = Calendar.getInstance();
            cl.setTime(d);
            if (format.equals("年")) {
                return date.substring(0, 4) + "年";
            }
            if (format.equals("年月")) {
                return date.substring(0, 4) + "年" + date.substring(5, 7) + "月";
            }
            if (format.equals("月日")) {
                return date.substring(5, 7) + "月" + date.substring(8, 10) + "日";
            }
            if (format.equals("月日星期")) {
                return date.substring(5, 7) + "月" + date.substring(8, 10) + "日星期"
                        + Str.dayofWeek2Cn(cl.get(Calendar.DAY_OF_WEEK) + "");
            }
            return date;
        }
        return date;
    }

    // 以下Calendar类，将会用DataTime类取代========================================================

    /**
     * 获取年月日
     *
     * @return
     */
    public static String getDBNowYMD() {
        return getDBTime(FORMATE_DATEYMD);
    }

    /**
     * 格式化日期
     *
     * @param date 日期对象
     * @return String 日期字符串
     */
    public static String formatDate(Date date) {
        SimpleDateFormat f = new SimpleDateFormat(FORMATE_DATE);
        String sDate = f.format(date);
        return sDate;
    }

    /**
     * 格式化日期
     *
     * @param date 日期 yyyyMMdd
     * @return String 日期字符串 yyyy-MM-dd
     */
    public static String formatDate(Integer date) {
        String dateStr = date.toString();
        if (dateStr.length() != 8) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        sb.append(dateStr.substring(0, 4));
        sb.append("-");
        sb.append(dateStr.substring(4, 6));
        sb.append("-");
        sb.append(dateStr.substring(6, 8));
        return sb.toString();
    }

    /**
     * 获取当年的第一天
     *
     * @return
     */
    public static Date getCurrYearFirst() {
        Calendar currCal = Calendar.getInstance();
        int currentYear = currCal.get(Calendar.YEAR);
        return getYearFirst(currentYear);
    }

    /**
     * 获取当年的最后一天
     *
     * @return
     */
    public static Date getCurrYearLast() {
        Calendar currCal = Calendar.getInstance();
        int currentYear = currCal.get(Calendar.YEAR);
        return getYearLast(currentYear);
    }

    /**
     * 获取某年第一天日期
     *
     * @param year 年份
     * @return Date
     */
    public static Date getYearFirst(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        Date currYearFirst = calendar.getTime();
        return currYearFirst;
    }

    /**
     * 获取某年最后一天日期
     *
     * @param year 年份
     * @return Date
     */
    public static Date getYearLast(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        Date currYearLast = calendar.getTime();
        return currYearLast;
    }

    /**
     * 获取某月第一天日期
     *
     * @param date
     * @return
     */
    public static String getMonthFirst(Date date) {
        Calendar c = Calendar.getInstance();
        c.clear();
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH, 1);
        return new SimpleDateFormat(FORMATE_DATE).format(c.getTime());
    }

    /**
     * 获取某月最后一天日期
     *
     * @param date
     * @return
     */
    public static String getMonthLast(Date date) {
        Calendar c = Calendar.getInstance();
        c.clear();
        c.setTime(date);
        c.add(Calendar.MONTH, 1);
        c.set(Calendar.DAY_OF_MONTH, 0);
        return new SimpleDateFormat(FORMATE_DATE).format(c.getTime());
    }

    /**
     * 时间戳转换为时间str（天,时:分:秒.毫秒）
     *
     * @param timeMillis
     * @return
     */
    public static String formatDateTime(long timeMillis) {
        long day = timeMillis / (24 * 60 * 60 * 1000);
        long hour = (timeMillis / (60 * 60 * 1000) - day * 24);
        long min = ((timeMillis / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (timeMillis / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        long sss = (timeMillis - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000 - min * 60 * 1000 - s * 1000);
        return (day > 0 ? day + "," : "") + hour + ":" + min + ":" + s + "." + sss;
    }

    public static void main(String[] args) {
        String ymdLine = DateUtil.init(2017, 11, 2).ymdLine();
        System.out.println(ymdLine);
    }

}
