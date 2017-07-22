package net.chinahrd.thread;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 赛跑时，等待所有人都准备好时，才起跑<br>
 * 使用场景： 需要所有的子任务都完成时，才执行主任务，这个时候就可以选择使用CyclicBarrier。<br>
 * http://blog.csdn.net/shihuacai/article/details/8856407
 * 
 * @author jxzhang on 2017年7月7日
 * @Verdion 1.0 版本
 */
public class CyclicBarrierTest {

	public static void main(String[] args) throws IOException, InterruptedException {
		// 如果将参数改为4，但是下面只加入了3个选手，这永远等待下去
		// Waits until all parties have invoked await on this barrier.
		CyclicBarrier barrier = new CyclicBarrier(3);

		ExecutorService executor = Executors.newFixedThreadPool(3);
		executor.submit(new Thread(new Runner(barrier, "1号选手")));
		executor.submit(new Thread(new Runner(barrier, "2号选手")));
		executor.submit(new Thread(new Runner(barrier, "3号选手")));

		executor.shutdown();
	}
}

class Runner implements Runnable {
	// 一个同步辅助类，它允许一组线程互相等待，直到到达某个公共屏障点 (common barrier point)
	private CyclicBarrier barrier;

	private String name;

	public Runner(CyclicBarrier barrier, String name) {
		super();
		this.barrier = barrier;
		this.name = name;
	}

	@Override
	public void run() {
		try {
			Thread.sleep(1000 * (new Random()).nextInt(8));
			System.out.println(name + " 准备好了...");
			// barrier的await方法，在所有参与者都已经在此 barrier 上调用 await 方法之前，将一直等待。
			barrier.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (BrokenBarrierException e) {
			e.printStackTrace();
		}
		System.out.println(name + " 起跑！");
	}
}