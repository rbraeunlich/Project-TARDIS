package kr.ac.kaist.se.tardis.web.tool;

public class SimpleCounter {
	private int count;
	
	public SimpleCounter(int init) {
		count = init;
	}
	
	public int increment() {
		return ++count;
	}
	
	public void reset() {
		count = 0;
	}
	
	public int getValue() {
		return count;
	}
}
