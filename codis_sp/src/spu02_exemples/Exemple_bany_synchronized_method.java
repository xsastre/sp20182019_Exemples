package spu02_exemples;


public class Exemple_bany_synchronized_method {
	static class Bany {
		
		synchronized void entrar_al_bany(String nom) {
			System.out.println(nom+" entra al bany");
			try {
				Thread.sleep((int)(Math.random()*3000+2000));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(nom+" surt del bany");

		}
	}
	static class Persona extends Thread {
		String nom;
		Bany bany;
		public Persona(String nom,Bany bany) {
			this.nom=nom;
			this.bany=bany;
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
			Espera(); // Espera abans d'anar al bany
			System.out.println(nom+" va cap al bany");
			bany.entrar_al_bany(nom);
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
