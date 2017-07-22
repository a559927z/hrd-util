package net.chinahrd.thread.pool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author jxzhang on 2017年5月6日
 * @Verdion 1.0 版本
 */
public class ThreadPoolTest {
	public static void main(String[] args) {

		ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 5, 200, TimeUnit.MILLISECONDS,
				new ArrayBlockingQueue<Runnable>(5));

		for (int i = 0; i < 18; i++) {
			MyTask myTask = new MyTask(i);
			executor.execute(myTask);
			System.out.print("线程池中线程数目：" + executor.getPoolSize());
			System.out.print("，队列中等待执行的任务数目：" + executor.getQueue().size());
			System.out.println("，已执行完别的任务数目：" + executor.getCompletedTaskCount());
			System.out.println("，!：" + executor.getRejectedExecutionHandler().getClass().getName());
		}
		executor.shutdown();
	}
}

class MyTask implements Runnable {
	private int taskNum;

	public MyTask(int num) {
		this.taskNum = num;
	}

	@Override
	public void run() {
		System.out.println("正在执行task " + taskNum);
		try {
			Thread.currentThread().sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("task " + taskNum + "执行完毕");
	}
}