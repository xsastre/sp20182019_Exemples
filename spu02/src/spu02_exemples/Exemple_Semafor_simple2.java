package spu02_exemples;

import java.util.concurrent.Semaphore;

public class Exemple_Semafor_simple2 {

	static Semaphore semafor = new Semaphore(2);

	static class ElmeuThread extends Thread {

		String nom = "";

		ElmeuThread(String name) {
			this.nom = name;
		}

		public void run() {


			System.out.println(nom + " : agafant el bloqueig...");
			System.out.println(nom + " : semafor, llocs disponibles: " 
					+ semafor.availablePermits());
			try {
					semafor.acquire();
					System.out.println(nom + " : ha agafat un lloc!");

					sleep((int)(Math.random()*3+2)*1000);

			} catch (InterruptedException e) {

				e.printStackTrace();

			}
			System.out.println(nom + " : amollant bloqueig...");
			semafor.release();
			System.out.println(nom + " : semafor, ara llocs disponibles: " 
					+ semafor.availablePermits());
			
		}


		public static void main(String[] args) {
			System.out.println("Total permisos de semafor disponibles : " 
					+ semafor.availablePermits());

			ElmeuThread t1 = new ElmeuThread("Fil 1");
			t1.start();
			ElmeuThread t2 = new ElmeuThread("Fil 2");
			t2.start();
			ElmeuThread t3 = new ElmeuThread("Fil 3");
			t3.start();
			ElmeuThread t4 = new ElmeuThread("Fil 4");
			t4.start();

		
		}

	}
}