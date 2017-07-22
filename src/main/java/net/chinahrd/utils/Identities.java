package net.chinahrd.utils;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 封装各种生成唯一性ID算法的工具类.
 * 
 */
public class Identities {
	private static final SnowflakeId snowflakeId = new SnowflakeId(0, 0);
	private static SecureRandom random = new SecureRandom();

	/**
	 * 封装JDK自带的UUID, 通过Random数字生成, 中间有-分割.
	 */
	public static String uuid() {
		return UUID.randomUUID().toString();
	}

	/**
	 * 封装JDK自带的UUID, 通过Random数字生成, 中间无-分割.
	 */
	public static String uuid3() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	/**
	 * 使用SnowflakeId生策略 纯数字递增
	 */
	public static String uuid2() {
		// return getOrderNo();
		return String.valueOf(snowflakeId.nextId());
	}

	/**
	 * 使用SecureRandom随机生成Long.
	 */
	public static long randomLong() {
		return Math.abs(random.nextLong());
	}

	/**
	 * 基于Base62编码的SecureRandom随机生成bytes.
	 */
	public static String randomBase62(int length) {
		byte[] randomBytes = new byte[length];
		random.nextBytes(randomBytes);
		return Encodes.encodeBase62(randomBytes);
	}

	// 生成19随机单号 纯数字
	public static String getOrderNo() {
		String orderNo = "";
		String trandNo = String.valueOf((Math.random() * 9 + 1) * 1000000);
		// System.out.println(trandNo);
		String sdf = new SimpleDateFormat("yyyyMMddHHMMSS").format(new Date());
		orderNo = trandNo.toString().substring(0, 4);
		// System.out.println(orderNo);

		orderNo = sdf + orderNo;
		return orderNo;
	}

	public static void main(String[] args) throws Exception {
		// List<String> days = CollectionKit.newList();
		// DateTime dt = new DateTime("2015-12-17");
		// String yestDay ="";
		// do {
		// yestDay = dt.minusDays(1).toString("yyyy-MM-dd");
		// dt = new DateTime(yestDay);
		// days.add(yestDay);
		//
		// System.out.println(yestDay);
		// } while (!(yestDay.equals("2010-01-01")));
		// System.out.println(days.size());

		// int a = DateUtil.getYearMonthDayInt("2010-01-01");
		// int b = DateUtil.getYearMonthDayInt("2010-11-01");
		// System.out.println(a >b);

		List<Integer> ls = CollectionKit.newList();
		ls.add(10);
		ls.add(2);
		ls.add(1);
		// System.out.println(ls.get(0));

		// String dt = new DateTime(2015,6,1,0,0).toString("yyyy-MM-dd",
		// Locale.CHINESE);
		//
		// DateTime dt1 =
		// DateTimeFormat.forPattern("yyyy-MM").parseDateTime("2015-06");
		// System.out.println(dt1.minusMonths(6));
		//
		// DateTime dt2 = DateTime.parse("2015-06");
		// System.out.println(dt2.minusMonths(1).toString("yyyy年MM月"));

		// String md5Pass = Base64.encodeToString("123456".getBytes());
		// String decPass = Base64.decodeToString(md5Pass);
		// System.out.println(md5Pass + " "+decPass);
		//
		// String substring = StringUtils.substring("123456", 1,3);
		// System.out.println(substring);
		//
		// //16进制编码
		// String hexEncoded = Hex.encodeToString("123456".getBytes());
		// //16进制解码
		// String str3 = new String(Hex.decode(hexEncoded.getBytes()));
		// System.out.println(hexEncoded + " "+str3);

		// List<String> a = CollectionKit.newList();
		// System.out.println(a.isEmpty());
		// System.out.println(a);
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		// Calendar cal=Calendar.getInstance();
		// System.out.println(sdf.parse("201506"));

		// Random r=new java.util.Random();
		for (int i = 1; i <= 1000; i++) {
			System.out.println(Identities.uuid2());
		}
		// System.out.println("-------------------------------");
		// long s = System.currentTimeMillis();
		// for (int i = 1; i <= 4; i++) {
		// for (int j = 1; j <= 2; j++) {
		// System.out.println(Identities.uuid2());
		// }
		// System.out.println(Identities.uuid2());
		// System.out.println("b5c9410dc7e4422c8e0189c7f8056b5f");

		// System.out.println("2015-12-"+i);
		// System.out.println("211院校");
		// System.out.println("bliang");
		// System.out.println(0);
		// System.out.println(r.nextInt(15)+1);
		// }

		// long e = System.currentTimeMillis();
		// DateTime dt = new DateTime(e-s);
		// System.out.println(dt.toString());
		// System.out.println(e-s);

		// System.out.println("abc".contains("b"));

		// List<Integer> list = new ArrayList<Integer>();
		// int sizes = 122; // sizes是一个动态变量 测试的时候先写死
		// for (int i = 1; i <= sizes; i++)
		// list.add(i);
		//
		// int count = list.size() / 10;
		// int yu = list.size() % 10;
		//
		// for (int i = 0; i < 10; i++) {
		// List<Integer> subList = new ArrayList<Integer>();
		// if (i == 9) {
		// subList = list.subList(i * count, count * (i + 1) + yu);
		// } else {
		// subList = list.subList(i * count, count * (i + 1));
		// }
		//
		// System.out.println(subList);
		// }
		// System.out.println(Math.random());
	}
}
