package com.rfid.test;

public class ThreadTest implements Runnable{
	
	
	int i = 1;
	
	public void run() {
		System.out.println(Thread.currentThread());
		System.out.println(this);
		while(1==1) {
			i++;
		}
		
	}

	
	
	public static void main(String[] args) {
		ThreadTest t = new ThreadTest();
		new Thread(t).start();
		while(1==1) {
			System.out.println(t.i);
		}
	}
}
