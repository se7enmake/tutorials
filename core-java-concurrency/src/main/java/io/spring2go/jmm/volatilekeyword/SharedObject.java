package io.spring2go.jmm.volatilekeyword;

public class SharedObject {
	
	//public int value = 0; // has visibility problem
	
	public volatile int value = 0; // use volatile to fix

}
