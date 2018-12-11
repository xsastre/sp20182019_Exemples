package spu03_exemples;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class ExempleHTTPClient {
  public static void main(String[] args) throws Exception {
    InetAddress addr = InetAddress.getByName("www.google.com");
    Socket socket = new Socket(addr, 80);
    boolean autoflush = true;
    PrintWriter out = new PrintWriter(socket.getOutputStream(), autoflush);
    BufferedReader in = new BufferedReader(

    new InputStreamReader(socket.getInputStream()));
    // Enviar una peticio HTTP al servidor web
    out.println("GET /search?q=mrsalvab HTTP/1.1");
    out.println("Host: www.google.com:80");
    out.println("Connection: Close");
    out.println();

    // llegeix la resposta
    boolean loop = true;
    String resposta="";
    while (loop) {
    	if (in.ready()) {
    		int i = 0;
    		while (i != -1) {
    			i = in.read();
    			resposta=resposta+((char) i);
    		}
    		loop = false;
    	}
    }
    /* Aquí teniu una altra manera de fer-ho utilitzant StringBuilder
     * StringBuilder sb = new StringBuilder(8096);
    while (loop) {
      if (in.ready()) {
        int i = 0;
        while (i != -1) {
          i = in.read();
          sb.append((char) i);
        }
        loop = false;
      }
    }
    System.out.println(sb.toString());*/

    socket.close();
    System.out.println(resposta);
  }
}