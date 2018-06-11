package io.spring2go.jmm.compositeaction;

public class CompositeActionWithSynchronized {
	static int value;

	// 原子排它方法
	public synchronized static void increment() {
		value++;
	}

	public static void main(String[] args) throws InterruptedException {

		for (int t = 0; t < 10; t++) {
			value = 0;

			Thread thread1 = new Thread(() -> {
				for (int i = 0; i < 1000; i++) {
					increment();
				}
			});

			Thread thread2 = new Thread(() -> {
				for (int i = 0; i < 1000; i++) {
					increment();
				}
			});

			thread1.start();
			thread2.start();

			thread1.join();
			thread2.join();

			System.out.println("shared value = " + value);
		}

	}
}
