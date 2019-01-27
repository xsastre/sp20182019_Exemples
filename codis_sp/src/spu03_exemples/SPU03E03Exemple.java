package spu03_exemples;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


class Zona {
	private int entradesPerVendre;
	private String name;

	public Zona(String nom,int n) {
		entradesPerVendre=n;
		name=nom;
	}
	public int getEntradesPerVendre() {
		return entradesPerVendre;
	}
	public String getName() {
		return name;
	}

	public String vendre(int n) {
		String resultat="";
		if (this.entradesPerVendre == 0) {
			resultat= "Ho sentim, les entrades per la zona "+name+" estan exhaurides.";
		} else if (this.entradesPerVendre <n) {
			resultat= "Nomes queden "+this.entradesPerVendre+" entrades per la zona "+this.name+".";
		}
		if(this.entradesPerVendre >= n) {
			entradesPerVendre -=n;
			resultat= "Aquí té les seves "+n+" entrades per la zona "+name+", gràcies.";
		}
		return resultat;
	}
}

public class SPU03E03Exemple {

	static Zona platea=new Zona("Platea",1000);
	static Zona amfiteatre=new Zona("Amfiteatre",250);
	static Zona VIP=new Zona("VIP",25);
	public static final int PORT=44014;

	public static void mostrarMenuPrincipal(PrintWriter stream_de_sortida){
		stream_de_sortida.println("Benvingut al sistema TOFOL de venda d'entrades:");
		stream_de_sortida.println("1.- Veure entrades disponibles");
		stream_de_sortida.println("2.- Comprar entrades");
		stream_de_sortida.println("3.- Sortir");
	}
	public static void mostrarMenuTipusEntrada(PrintWriter stream_de_sortida){
		stream_de_sortida.println("Quin tipus d'entrada vol comprar?");
		stream_de_sortida.println("1.- Amfiteatre");
		stream_de_sortida.println("2.- Platea");
		stream_de_sortida.println("3.- Vip");
	}
	public static void mostrarQuantesEntrades(PrintWriter stream_de_sortida){
		stream_de_sortida.println("Quantes entrades vols?");
	}
	public static void mostrarEntradesDisponibles(PrintWriter stream_de_sortida) {
		stream_de_sortida.println();
		stream_de_sortida.println("=======================");
		stream_de_sortida.println("La disponibilidad a "+platea.getName()+" es de: "+platea.getEntradesPerVendre());
		stream_de_sortida.println("La disponibilidad a "+amfiteatre.getName()+" es de: "+amfiteatre.getEntradesPerVendre());
		stream_de_sortida.println("La disponibilidad a "+VIP.getName()+" es de: "+VIP.getEntradesPerVendre());    	
		stream_de_sortida.println("=======================");
		stream_de_sortida.println();
	}
	public static String obtenirRespostaUsuari(BufferedReader entrada) {
		String resposta="";
		try {
			resposta = entrada.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (entrada == null) {
			return "";
		}
		return resposta;
	}
	synchronized public static String gestionarCompraEntrades(String zona,int num_entrades) throws InterruptedException {
		String resultat_operacio="";
		// Determinar zona triada per usuari
		switch(zona) {
		case "1":
			resultat_operacio=platea.vendre(num_entrades);
			Thread.sleep(10000);
			break;
		case "2":
			resultat_operacio=amfiteatre.vendre(num_entrades);
			Thread.sleep(10000);
			break;
		case "3":
			resultat_operacio=VIP.vendre(num_entrades);
			Thread.sleep(10000);
			break;
		}
		return resultat_operacio;
	}

	private static class ManejadorConnexions extends Thread {
		private String name;
		private Socket socket;
		private BufferedReader entrada;
		private PrintWriter sortida;

		/**
		 * En el constructor nomes posam el socket, la resta se farà al run()
		 */
		public ManejadorConnexions(Socket socket) {
			this.socket = socket;
		}


		public void run() {
			String resposta_usuari_menu_principal="";
			String resposta_usuari_zona_compra="";
			int resposta_usuari_num_entrades=0;
			try {

				// Cream character streams pel socket.
				entrada = new BufferedReader(new InputStreamReader(
						socket.getInputStream()));
				sortida = new PrintWriter(socket.getOutputStream(), true);


				// Mostram el menu i començam a tractar respostes
				while (!resposta_usuari_menu_principal.equals("3")) {
					mostrarMenuPrincipal(sortida);
					resposta_usuari_menu_principal=obtenirRespostaUsuari(entrada);
					switch (resposta_usuari_menu_principal) {
					case "1":
						mostrarEntradesDisponibles(sortida);
						break;
					case "2":
						mostrarMenuTipusEntrada(sortida);
						resposta_usuari_zona_compra=obtenirRespostaUsuari(entrada);
						if ((Integer.parseInt(resposta_usuari_zona_compra)<1) || (Integer.parseInt(resposta_usuari_zona_compra)>3)) {
							sortida.println();	
							sortida.println("===> ATENCIO! La zona triada no existeix. Torna-ho provar");
							sortida.println();
							break;
						}
						else
							mostrarQuantesEntrades(sortida);
						resposta_usuari_num_entrades=Integer.parseInt(obtenirRespostaUsuari(entrada));
						// Si el num entrades es dolent cancela i torna menu
						if (resposta_usuari_num_entrades<0) {
							sortida.println();	
							sortida.println("===> ATENCIO! El numero d'entrades es incorrecte. Torna-ho provar");
							sortida.println();
							break;
						}
						else {
							sortida.println("Processant compra ... Per favor, espera");
							sortida.println(gestionarCompraEntrades(resposta_usuari_zona_compra,resposta_usuari_num_entrades));	
						}
						break;
					}
				}
				sortida.println("Gracies per utilitzar aquest servei");

			} catch (IOException | InterruptedException e) {
				System.out.println(e);
			} finally {
				// Si el client cau, tancam el socket.
				try {
					socket.close();
				} catch (IOException e) {
					System.out.println("S'ha produit un error tancant el socket"+e.getMessage());
				}
			}
		}
	}


	public static void main(String args[]) {

		ServerSocket listener;
		try {
			listener = new ServerSocket(PORT);
			try {
				while (true) {
					new ManejadorConnexions(listener.accept()).start();
				}
			} finally {
				listener.close();
			}
		} catch (IOException e) {
			System.out.println("S'ha produit un error "+e.getMessage());
		}

	}
}
