package io.spring2go.concurrent.synchronize;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class CalculatorWithSynchronizedBlocks {
	
	private int syncSum = 0;
    private static int staticSum = 0;

    // synchronized instance block
    void syncCalculate() {
        synchronized (this) {
        	setSyncSum(getSyncSum() + 1);
        }
    }

	// synchronized static block
    static void staticCalculate() {
        synchronized (CalculatorWithSynchronizedBlocks.class) {
        	setStaticSum(getStaticSum() + 1);
        }
    }
    
    public int getSyncSum() {
    	return syncSum;
    }
    
    public void setSyncSum(int v) {
    	this.syncSum = v;
    }
    
    public static int getStaticSum() {
    	return staticSum;
    }
    
    private static void setStaticSum(int v) {
    	CalculatorWithSynchronizedBlocks.staticSum = v;
    }
    
	public static void main(String[] args) throws InterruptedException {
		ExecutorService service = Executors.newFixedThreadPool(3);
		
		CalculatorWithSynchronizedBlocks calculator = new CalculatorWithSynchronizedBlocks();
		
		IntStream.range(0, 1000).forEach(counter -> service.submit(calculator::syncCalculate));
		service.awaitTermination(1000, TimeUnit.MILLISECONDS);
		
		if (calculator.getSyncSum() != 1000) {
			System.out.println("Wrong, syncSum = " + calculator.getSyncSum());
		} else {
			System.out.println("Right, syncSum = 1000");
		}
		
//		IntStream.range(0, 1000)
//	      .forEach(count -> 
//	        service.submit(CalculatorWithSynchronizedBlocks::staticCalculate));
//	    service.awaitTermination(100, TimeUnit.MILLISECONDS);
//		
//		if (staticSum!= 1000) {
//			System.out.println("Wrong, staticSum = " + CalculatorWithSynchronizedBlocks.getStaticSum());
//		} else {
//			System.out.println("Right, staticSum = 1000");
//		}
	}
}
