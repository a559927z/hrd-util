package net.chinahrd.utils;

import java.lang.instrument.Instrumentation;

/**
 * http://www.tuicool.com/articles/vYrARj
 * 
 * @author jxzhang on 2017年5月12日
 * @Verdion 1.0 版本
 */
public class Agent {

	private static volatile Instrumentation globalInstr;

	public static void premain(String args, Instrumentation inst) {
		System.out.println(inst);
		globalInstr = inst;
	}

	public static long getObjectSize(Object obj) {
		if (globalInstr == null)
			throw new IllegalStateException("Agent not initted");
		return globalInstr.getObjectSize(obj);
	}
}