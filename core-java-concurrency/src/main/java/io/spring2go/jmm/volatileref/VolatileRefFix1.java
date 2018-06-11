package io.spring2go.jmm.volatileref;

public class VolatileRefFix1 {
	private static volatile ValueObject vo;

	public static void setValue(int a, int b) {
		vo = new ValueObject(a, b);
	}

	private static class ValueObject {
		private int x;
		private int y;

		public ValueObject(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public int[] getValues() {
			return new int[] { x, y };
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
				while (vo == null) {
				}
				int[] values = vo.getValues();
				int x = values[0];
				int y = values[1];
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