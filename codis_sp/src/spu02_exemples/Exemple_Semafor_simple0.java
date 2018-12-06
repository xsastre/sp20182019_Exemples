package spu02_exemples;

import java.util.concurrent.Semaphore ;
class Exemple_Semafor_simple0 extends Thread {
	int id;
	static Semaphore semaphore = new Semaphore (1); 
	/*must be static so multiple threads have
	 * only one semaphore The
	 * created semaphore here allows only one
	 * thread at a time execute the critical
	 * section*/
	public Exemple_Semafor_simple0(int id){
		this.id= id;
	}
	public void run(){ /*subclasses of Thread must override the method run()*/
		try {
			semaphore.acquire(); /*thread stops here until it gets permit to go on*/
		}catch (InterruptedException e){} /*exception must be caught or thrown*/
		//CRITICAL SECTION
		semaphore.release();
	}
	public static void main(String[] args){
		Exemple_Semafor_simple0 esim1= new Exemple_Semafor_simple0(1);
		Exemple_Semafor_simple0 esim2= new Exemple_Semafor_simple0(2);
		esim1.start(); 
		esim2.start(); 
	}
}
