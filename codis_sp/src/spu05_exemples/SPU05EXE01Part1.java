package spu05_exemples;
/*
 * Utilitzant una clau secrete i un objecte
 * de la classe Cipher, xifrarem el fitxer de prova,
 * en blocs de 8 bytes. Posteriorment obtenim la versio
 * transparent de la clau i la emmagatzemem en un altre
 * fitxer. 
 */
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
import java.io.File;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
public class SPU05EXE01Part1 {
	public static void main(String[] args) {
		SPU05Utils utilitats=new SPU05Utils();
		try {
			System.out.println("Obtenint generador de claus amb xifrat DES");
			KeyGenerator keygen = KeyGenerator.getInstance("DES");
			System.out.println("Generant clau");
			SecretKey key = keygen.generateKey();
			System.out.println("Obtenint objecte Cipher amb xifrat DES");
			Cipher desCipher = Cipher.getInstance("DES");
			System.out.println("Configurant Cipher per encriptar");
			desCipher.init(Cipher.ENCRYPT_MODE, key);
			System.out.println("Obrint el fitxer");
			Properties properties = new Properties();
			File fitxer=utilitats.obtenirFitxer("spu05_exemples/fitxer_proves.txt");
			FileInputStream is = new FileInputStream(fitxer);
			System.out.println("Obrint el fitxer xifrat");
			fitxer=utilitats.obtenirFitxer("spu05_exemples/fitxer_xifrat");
			FileOutputStream os = new FileOutputStream(fitxer);
			System.out.println("Xifrant el fitxer...");
			byte[] buffer = new byte[8];
			int bytes_llegits = is.read(buffer);
			while (bytes_llegits != -1) {
				os.write(desCipher.doFinal(buffer, 0, bytes_llegits));
				bytes_llegits = is.read(buffer);
			}
			os.close();
			System.out.println("Obtenint factoria de claus amb xifrat DES");
			SecretKeyFactory keyfac = SecretKeyFactory.getInstance("DES");
			System.out.println("Generant keyspec");
			DESKeySpec keyspec = (DESKeySpec) keyfac.getKeySpec(key, DESKeySpec.class);
			System.out.println("Emmagatzemant la clau en un fitxer");
			fitxer=utilitats.obtenirFitxer("spu05_exemples/clau");
			FileOutputStream cos = new FileOutputStream(fitxer);
			cos.write(keyspec.getKey());
			cos.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}