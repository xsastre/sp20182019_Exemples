package spu02_exemples;
public class Fil_runnable_exemple2 {
	public static class ElmeuRunnable implements Runnable {
		@Override
		public void run() {
			String nom_thread;
			Thread unaltrethread=Thread.currentThread();
			nom_thread=unaltrethread.getName();
			for (int i=0;i<10;i++) {
				System.out.println(nom_thread+" "+i);
			}
		}
	}
	public static void main(String[] args) {
		String nom_thread;
		Thread fil1=new Thread(new ElmeuRunnable(), "Tofol");
		Thread thread_actual=Thread.currentThread();
		nom_thread=thread_actual.getName();
		fil1.start();
		for (int i=0;i<10;i++) {
			System.out.println(nom_thread+" "+i);
		}
	}
}
