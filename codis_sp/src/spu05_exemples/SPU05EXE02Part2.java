package spu05_exemples;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.spec.RSAPublicKeySpec;
import javax.crypto.Cipher;
public class SPU05EXE02Part2 {
	public static void main(String[] args) {
		SPU05Utils utilitats=new SPU05Utils();
		try {
			BufferedReader br = new BufferedReader(new FileReader(utilitats.obtenirFitxer("spu05_exemples/clau_publica")));
			BigInteger modulus = new BigInteger(br.readLine());
			BigInteger exponente = new BigInteger(br.readLine());
			RSAPublicKeySpec keyspec = new RSAPublicKeySpec(modulus, exponente);
			KeyFactory keyfac = KeyFactory.getInstance("RSA");
			Key public_key = keyfac.generatePublic(keyspec);
			Cipher desCipher = Cipher.getInstance("RSA/ECB/PKCS1PADDING");
			desCipher.init(Cipher.DECRYPT_MODE, public_key);
			File inf = utilitats.obtenirFitxer("spu05_exemples/fitxer_xifrat_amb_RSA");
			FileInputStream is = new FileInputStream(inf);
			File fitxer_desxifrat=utilitats.obtenirFitxer("spu05_exemples/fitxer_desxifrat_amb_RSA.txt");
			FileOutputStream os = new FileOutputStream(fitxer_desxifrat);
			byte[] buffer = new byte[128];
			int bytes_llegits = is.read(buffer);
			while (bytes_llegits != -1) {
				os.write(desCipher.doFinal(buffer, 0, bytes_llegits));
				bytes_llegits = is.read(buffer);
			}
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}