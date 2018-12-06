package spu02_exemples;

public class Exemple_Semafor_simple {
	private boolean signal = false;

	public synchronized void take() {
		this.signal = true;
		this.notify();
	}

	public synchronized void release() throws InterruptedException{
		while(!this.signal) wait();
		this.signal = false;
	}

}