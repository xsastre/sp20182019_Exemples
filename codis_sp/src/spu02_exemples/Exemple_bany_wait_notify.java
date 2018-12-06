package spu02_exemples;

public class Exemple_bany_wait_notify {
	static class Bany {
		Boolean ocupat=false;
		synchronized void posar_en_espera() {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		synchronized void deixar_pasar_al_seguent() {
			notify();
		}
	}
	static class Persona extends Thread {
		String nom;
		Bany bany;
		public Persona(String nom,Bany objecte) {
			this.nom=nom;
			this.bany=objecte;
		}
		public void Espera() {
			try {
				sleep((int)(Math.random()*3000+2000));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public void run() {
			Espera(); // Espera un moment abans d'anar cap al bany
			System.out.println(nom+" va cap al bany");
			while (bany.ocupat) {
				System.out.println(nom+" esperarà per entrar al bany");
				bany.posar_en_espera();
			}
			bany.ocupat=true;
			System.out.println(nom+" entra al bany");
			Espera();
			bany.ocupat=false;
			System.out.println(nom+" acaba de sortir del bany");
			bany.deixar_pasar_al_seguent(); 
		}
	}

	public static void main(String[] args) throws InterruptedException{
		Bany bany=new Bany();
		Persona Tofol=new Persona("Tofol",bany);
		Persona Biel=new Persona("Biel",bany);
		Persona Andreu=new Persona("Andreu",bany);
		Thread fil1=new Thread(Tofol);
		fil1.start();
		Thread fil2=new Thread(Biel);
		fil2.start();
		Thread fil3=new Thread(Andreu);
		fil3.start();		
	}
}
