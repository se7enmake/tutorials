package io.spring2go.jmm.compositeaction;

public class CompositeActionWithVolatile {
	
	static volatile int value;

    public static void main (String[] args) throws InterruptedException {
    	
        for (int t = 0; t < 10; t++) {
            value = 0;

            Thread thread1 = new Thread(() -> {
                for (int i = 0; i < 1000; i++) {
                	value++;
                }
            });

            Thread thread2 = new Thread(() -> {
                for (int i = 0; i < 1000; i++) {
                	value++;
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