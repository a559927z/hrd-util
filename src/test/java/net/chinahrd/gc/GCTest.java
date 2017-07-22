package net.chinahrd.gc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 运行时间对vm加上：-XX:+PrintGC （告诉虚拟机在发生垃圾回收时打印内存回收日志。）
 * 
 * @author jxzhang on 2017年7月5日
 * @Verdion 1.0 版本
 */
public class GCTest {

	private static Logger log = LoggerFactory.getLogger(GCTest.class);
	
	private Object instance = null;
	private static final int _10M = 10 * 1 << 20;
	// 一个对象占10M，方便在GC日志中看出是否被回收
	private byte[] bigSize = new byte[_10M];

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		log.info("123");
		GCTest objA = new GCTest();
		GCTest objB = new GCTest();

		objA.instance = objB;
		objB.instance = objA;

		objA = null;
		objB = null;

		System.gc();
	}
}
