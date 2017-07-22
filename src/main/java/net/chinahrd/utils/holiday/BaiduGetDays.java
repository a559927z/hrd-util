package net.chinahrd.utils.holiday;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.chinahrd.utils.CollectionKit;

/**
 * 获取节假日-百度API
 * 
 * @author jxzhang on 2017年2月13日
 * @Verdion 1.0 版本
 */
public class BaiduGetDays implements GetDays {

	private final static String jxzhangKey = "b757d63ed864dd413892557c7626a401";
	// 功能特点
	// 检查具体日期是否为节假日，工作日对应结果为 0, 休息日对应结果为 1, 节假日对应的结果为 2；
	// （对应支付工资比例分别为 100%, 200% 和 300%，以方便程序计算 计算方法: 基本工资* (1+结果数值)）
	// 获取具体月份下的节假日情况，只返回休息日或者节假日数据；
	// 可同时传递一个或者多个日期（月份）；
	// 支持 2009 年起至2016年
	// 中国法定节假日，以国务院发布的公告为准，随时调整及增加；http://www.gov.cn/zfwj/bgtfd.htm或http://www.gov.cn/zhengce/xxgkzl.htm
	// 参数可以以 GET 或 POST 方式传递，以 JSON 格式返回结果。
	//
	// 用法举例
	// 检查一个日期是否为节假日 ?d=20160101
	// 检查多个日期是否为节假日 ?d=20160101,20160103,20160105,20161201
	// 获取2016年10月份节假日 ?d=201610
	// 获取2016年所有节假日 ?d=2016
	// 获取2016年1/2月份节假日 ?d=201601,201602

	// 本地异常日志记录对象
	private static Logger logger = LoggerFactory.getLogger(GetDays.class);

	/**
	 * @param urlAll
	 *            :请求接口
	 * @param httpArg
	 *            :参数
	 * @return 返回结果
	 */
	public String calcEveryDays(String httpUrl, String httpArg) {
		if (null == httpUrl) {
			httpUrl = "http://apis.baidu.com/xiaogg/holiday/holiday";
		}
		BufferedReader reader = null;
		String result = null;
		StringBuffer sbf = new StringBuffer();
		httpUrl = httpUrl + "?" + httpArg;

		try {
			URL url = new URL(httpUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			// 填入apikey到HTTP header
			connection.setRequestProperty("apikey", jxzhangKey);
			connection.connect();
			InputStream is = connection.getInputStream();
			reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			String strRead = null;
			while ((strRead = reader.readLine()) != null) {
				sbf.append(strRead);
				sbf.append("\r\n");
			}
			reader.close();
			result = sbf.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 
	 * @param yearLast
	 *            2016-12-31
	 * @param yearFirst
	 *            2010-01-01
	 * @return
	 */
	private List<String> getEveryDays(String yearLast, String yearFirst) {
		List<String> days = CollectionKit.newList();
		DateTime dt = new DateTime(yearLast);
		String yestDay = "";
		do {
			yestDay = dt.minusDays(1).toString("yyyy-MM-dd");
			dt = new DateTime(yestDay);
			days.add(yestDay.replace("-", ""));

		} while (!(yestDay.equals(yearFirst)));
		// System.out.println(days.size());
		return days;
	}

	public List<DaysDto> calcDays(String endDay, String startDay) {
		List<DaysDto> dtos = CollectionKit.newList();

		List<String> calcDays = getEveryDays(endDay, startDay);
		for (String days : calcDays) {
			String jsonResult = calcEveryDays(null, "d=" + days);
			int key = Integer.parseInt(jsonResult.replace("\r\n", ""));
			DaysDto dto = new DaysDto();
			dto.setDays(days);
			if (key == 0) {
				dto.setIsWorkFlag(1);
			}
			if (key == 1) {
				dto.setIsHoliday(1);
			}
			if (key == 2) {
				dto.setIsVacation(1);
			}
			logger.info(dto.getDays());
			dtos.add(dto);
		}
		return dtos;
	}

	/**
	 * 这个只是算周末
	 * 
	 * @param whichYear
	 * @return
	 */
	public static List<String> getWeekend(int whichYear) {

		List<String> results = new ArrayList<String>();

		DateTime starTime = new DateTime().withYear(whichYear).withDayOfYear(1);

		while (!(starTime.getYear() > whichYear)) {
			if (starTime.getDayOfWeek() > 5) {
				String date = starTime.toString("yyyy-MM-dd");
				results.add(date);
			}
			starTime = starTime.plusDays(1);
		}
		return results;
	}

	private Connection getConnection() throws SQLException, ClassNotFoundException {
		String mysqlDriver = "com.mysql.jdbc.Driver";
		// 注册驱动
		Class.forName(mysqlDriver);
		// 连接
		String url = "jdbc:mysql://42.62.24.7:3369/mup-large";
		Connection con = DriverManager.getConnection(url, "mup", "1q2w3e123");
		return con;
	}

	private void writeDb() throws ClassNotFoundException, SQLException {
		Connection con = getConnection();
		List<String> calcDays = getEveryDays("2019-01-01", "2018-01-01");
		for (String days : calcDays) {
			String jsonResult = calcEveryDays(null, "d=" + days);
			int key = Integer.parseInt(jsonResult.replace("\r\n", ""));
			String v1 = "0", v2 = "0", v3 = "0";
			if (key == 0) {
				v1 = "1";
			}
			if (key == 1) {
				v2 = "1";
			}
			if (key == 2) {
				v3 = "1";
			}
			logger.info(days + " " + v1 + " " + v2 + " " + v3 + " ");
			// 执行SQL语句
			String sql = "insert into days(days,  is_work_flag, is_holiday, is_vacation) values (?,?,?,?)";

			// // 预编译SQL
			// PreparedStatement stmt = (PreparedStatement)
			// con.prepareStatement(sql);
			// // 传输数据
			// stmt.setString(1, days);
			// stmt.setString(2, v1);
			// stmt.setString(3, v2);
			// stmt.setString(4, v3);
			// stmt.executeUpdate();
			// // 关闭
			// stmt.close();
		}
		con.close();
	}

	public static void main(String[] args) throws Exception {
		GetDays gd = new BaiduGetDays();
		gd.calcDays("2019-01-01", "2018-01-01"); // 计算2018年节假日
		// gd.writeDb();
		// String httpUrl = "http://apis.baidu.com/xiaogg/holiday/holiday";
		// String httpArg = "d=20161001";
		// String jsonResult = GetDays.calsDays(httpUrl, httpArg);
		// System.out.println(jsonResult);

		// List<String> weekend = getWeekend(2015);
		// for (String string : weekend) {
		// System.out.println(string);
		// }
	}
}
