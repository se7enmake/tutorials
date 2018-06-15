package io.spring2go.concurrent.synchronize;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class CalculatorWithSynchronizedMethods {
	
	private int syncSum = 0;
	
	static int staticSum = 0;
	
    // synchronized instance method
	public synchronized void calculate() {
		setSyncSum(getSyncSum() + 1);
	}
	
    // synchronized static method
	public static synchronized void syncStaticCalculate() {
		staticSum = staticSum + 1;
	}
	
	public void setSyncSum(int syncSum) {
		this.syncSum = syncSum;
	}

	public int getSyncSum() {
		return syncSum;
	}
	
	public static void main(String[] args) throws InterruptedException {
		ExecutorService service = Executors.newFixedThreadPool(3);
		
		CalculatorWithSynchronizedMethods calculator = new CalculatorWithSynchronizedMethods();
		
		IntStream.range(0, 1000).forEach(counter -> service.submit(calculator::calculate));
		service.awaitTermination(1000, TimeUnit.MILLISECONDS);
		
		if (calculator.getSyncSum() != 1000) {
			System.out.println("Wrong, syncSum = " + calculator.getSyncSum());
		} else {
			System.out.println("Right, syncSum = 1000");
		}
		
//		IntStream.range(0, 1000)
//	      .forEach(count -> 
//	        service.submit(CalculatorWithSynchronizedMethods::syncStaticCalculate));
//	    service.awaitTermination(100, TimeUnit.MILLISECONDS);
//		
//		if (staticSum!= 1000) {
//			System.out.println("Wrong, staticSum = " + staticSum);
//		} else {
//			System.out.println("Right, staticSum = 1000");
//		}
	}
}
