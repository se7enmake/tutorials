package io.spring2go.jmm.reorder;

public class ReorderSampleSynced {

	private int x = 0;
	private boolean bExit = false;

	public synchronized void methodA() {
		x = 1;
		bExit = true;
	}

	public synchronized void methodB() {
		if (bExit) {
			System.out.println("x = " + x);
		}
	}

	public static void main(String[] args) {
		for (int i = 0; i < 100; i++) {
			ReorderSample reorderSample = new ReorderSample();

			Thread thread1 = new Thread(() -> {
				reorderSample.methodA();
			});

			Thread thread2 = new Thread(() -> {
				reorderSample.methodB();
			});

			thread1.start();
			thread2.start();
		}
	}
}