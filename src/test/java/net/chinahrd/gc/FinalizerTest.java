package net.chinahrd.gc;

/**
 * 重写finalize方法，当第一次gc时不被回收，但finalize只会调用一次
 * 
 * @author jxzhang on 2017年7月5日
 * @Verdion 1.0 版本
 */
public class FinalizerTest {
	public static FinalizerTest object;

	public void isAlive() {
		System.out.println("I'm alive");
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		System.out.println("method finalize is running");
		object = this;
	}

	public static void main(String[] args) throws Exception {
		object = new FinalizerTest();

		// 第一次执行，finalize方法会自救
		object = null;
		System.gc();

		Thread.sleep(500);
		if (object != null) {
			object.isAlive();
		} else {
			System.out.println("I'm dead");
		}

		// 第二次执行，finalize方法已经执行过
		object = null;
		System.gc();

		Thread.sleep(500);
		if (object != null) {
			object.isAlive();
		} else {
			System.out.println("I'm dead");
		}
	}
}
