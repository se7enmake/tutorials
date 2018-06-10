package io.spring2go.jmm.compositeaction;

import java.util.concurrent.atomic.AtomicInteger;

public class CompositeActionWithAtomicVariable {
    static AtomicInteger atomicInt = new AtomicInteger();

    public static void main (String[] args) throws InterruptedException {

        for (int t = 0; t < 10; t++) {
        	atomicInt.set(0);

            Thread thread1 = new Thread(() -> {
                for (int i = 0; i < 1000; i++) {
                	atomicInt.incrementAndGet();
                }
            });

            Thread thread2 = new Thread(() -> {
                for (int i = 0; i < 1000; i++) {
                	atomicInt.incrementAndGet();
                }
            });

            thread1.start();
            thread2.start();

            thread1.join();
            thread2.join();

            System.out.println("shared value = " + atomicInt.get());
        }
    }
}