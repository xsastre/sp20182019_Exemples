package spu05_exemples;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.spec.RSAPublicKeySpec;
import javax.crypto.Cipher;

public class SPU05EXE02Part1 {

	public static void main(String[] args) {
		SPU05Utils utilitats=new SPU05Utils();
		try {
			KeyPairGenerator keygen = KeyPairGenerator.getInstance("RSA");
			System.out.println("Generant parell de claus");
			KeyPair keypair = keygen.generateKeyPair();
			System.out.println("Obtenint objecte Cipher amb xifrat RSA");
			Cipher desCipher = Cipher.getInstance("RSA/ECB/PKCS1PADDING");
			System.out.println("Configurant Cipher per xifrar");
			desCipher.init(Cipher.ENCRYPT_MODE, keypair.getPrivate());
			System.out.println("Obrint el fitxer");
			File inf = utilitats.obtenirFitxer("spu05_exemples/fitxer_prova_exercici2.txt");
			FileInputStream is = new FileInputStream(inf);
			System.out.println("Obrint el fitxer xifrat");
			File fitxer_xifrat = utilitats.obtenirFitxer("spu05_exemples/fitxer_xifrat_amb_RSA");
			FileOutputStream os = new FileOutputStream(fitxer_xifrat);
			System.out.println("Xifrant el fitxer...");
			byte[] buffer = new byte[64];
			int bytes_llegits = is.read(buffer);
			while (bytes_llegits != -1) {
				byte[] cbuf = desCipher.doFinal(buffer, 0, bytes_llegits);
				//byte[] cbuf = desCipher.doFinal(buffer);
				os.write(cbuf);
				bytes_llegits = is.read(buffer);
			}
			os.close();
			System.out.println("Obtenint factoria de claus amb xifrat RSA");
			KeyFactory keyfac = KeyFactory.getInstance("RSA");
			System.out.println("Generant keyspec");
			RSAPublicKeySpec publicKeySpec = keyfac.getKeySpec(keypair.getPublic(),
					RSAPublicKeySpec.class);
			System.out.println("Emmagatzemant la clau public en un fitxer");
			File clau=utilitats.obtenirFitxer("spu05_exemples/clau_publica");
			FileOutputStream cos = new FileOutputStream(clau);
			PrintWriter pw = new PrintWriter(cos);
			pw.println(publicKeySpec.getModulus());
			pw.println(publicKeySpec.getPublicExponent());
			pw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}