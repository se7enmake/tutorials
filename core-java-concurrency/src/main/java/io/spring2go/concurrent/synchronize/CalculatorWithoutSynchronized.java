package io.spring2go.concurrent.synchronize;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class CalculatorWithoutSynchronized {
	
	private int sum = 0;
	
	public void calculate() {
		setSum(getSum() + 1);
	}
	
	public void setSum(int v) {
		this.sum = v;
	}
	
	public int getSum() {
		return sum;
	}
	
	public static void main(String[] args) throws InterruptedException {
		ExecutorService service = Executors.newFixedThreadPool(3);
		
		CalculatorWithoutSynchronized calculator = new CalculatorWithoutSynchronized();
		
		IntStream.range(0, 1000).forEach(counter -> service.submit(calculator::calculate));
		service.awaitTermination(1000, TimeUnit.MILLISECONDS);
		
		if (calculator.getSum() != 1000) {
			System.out.println("Wrong, Sum = " + calculator.getSum());
		} else {
			System.out.println("Right, Sum = 1000");
		}
	}
}
