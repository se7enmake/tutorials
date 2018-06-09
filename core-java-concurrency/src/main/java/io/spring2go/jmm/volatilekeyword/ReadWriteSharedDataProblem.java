package io.spring2go.jmm.volatilekeyword;

public class ReadWriteSharedDataProblem {

	static int value; // shared data
	//static volatile int value; // volatile to fix

	public static void main(String[] args) {
		Thread thread1 = new Thread(() -> {
			int temp = 0;
			while (true) {
				if (temp != value) {
					temp = value;
					System.out.println("reader: value is = " + value);
				}
			}
		});

		Thread thread2 = new Thread(() -> {
			for (int i = 0; i < 5; i++) {
				value++;
				System.out.println("writer: changed value to = " + value);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			// sleep for reader to read pending changes
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			// exit the program otherwise other threads
			// will be keep waiting on value to change
			System.exit(0);
		});

		thread1.start();
		thread2.start();
	}

}
