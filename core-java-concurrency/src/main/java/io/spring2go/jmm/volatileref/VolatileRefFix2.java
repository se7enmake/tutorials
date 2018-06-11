package io.spring2go.jmm.volatileref;

import java.util.concurrent.atomic.AtomicReference;

public class VolatileRefFix2 {
	private static AtomicReference<ValueObject> vo = new AtomicReference<>();

	public static void setValue(int x, int y) {
		vo.compareAndSet(null, new ValueObject(x, y));
	}

	private static class ValueObject {
		private int x;
		private int y;

		public ValueObject(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}
	}

	public static void main(String[] args) throws InterruptedException {
		for (int i = 0; i < 10000; i++) {
			int m = i;
			int n = i;
			// writer
			Thread writerThread = new Thread(() -> {
				setValue(m, n);
			});

			// reader
			Thread readerThread = new Thread(() -> {
				while (vo.get() == null) {
				}
				int x = vo.get().getX();
				int y = vo.get().getY();
				if (x != y) {
					System.out.printf("x = %s, y = %s%n", x, y);
				}
			});

			writerThread.start();
			readerThread.start();
			writerThread.join();
			readerThread.join();
		}
		System.out.println("finished");
	}
}