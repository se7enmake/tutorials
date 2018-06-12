package io.spring2go.jmm.volatileref;

public class VolatileRefSample {
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

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }

    public static void main(String[] args) throws InterruptedException {

        for (int i = 0; i < 20000; i++) {
            int m = i;
            int n = i;
            //writer
            Thread writerThread = new Thread(() -> {setValue(m, n);});
            //reader
            Thread readerThread = new Thread(() -> {
                while (vo == null) {}
                int x = vo.getX();
                int y = vo.getY();
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