package spu02_exemples;

import spu02_exemples.Fil_runnable_exemple1.elmeuRunnable;

public class Exemple_wait_notify_simple_incorrecte {
	static class elmeuRunnable implements Runnable {
		String nom;
		public elmeuRunnable(String nom) {
			this.nom=nom;
		}
		synchronized void posar_en_espera() {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		public void run() {
			posar_en_espera();
			
			System.out.println("El fill "+nom+" acaba de despertar-se"); 
		}
	}

	public static void main(String[] args) {
		Thread fil1=new Thread(new elmeuRunnable("Tofol"));
		fil1.start();
		Thread fil2=new Thread(new elmeuRunnable("Biel"));
		fil2.start();
		fil1.notify();
		fil2.notify();
	}
}
