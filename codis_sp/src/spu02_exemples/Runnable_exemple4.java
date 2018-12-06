package spu02_exemples;

import spu02_exemples.Fil_runnable_exemple2.ElmeuRunnable;

public class Runnable_exemple4 {
	public static class ElmeuRunnable implements Runnable {
		String nom;
		public ElmeuRunnable(String nom_thread) {
			nom=nom_thread;
		}
		@Override
		public void run() {
			System.out.println("Thread: " + this.nom + " començant");
			try {
				Thread.sleep((int)(Math.random()*10000));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Thread: " + this.nom + " acabant");
		}
	}
	
	public static void main(String[] args) {
		String nom_thread;
		Thread[] fil=new Thread[10];
		for (int i=0;i<10;i++) {
			fil[i]=new Thread(new ElmeuRunnable(String.valueOf(i)));
			fil[i].start();
			try {
				fil[i].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
