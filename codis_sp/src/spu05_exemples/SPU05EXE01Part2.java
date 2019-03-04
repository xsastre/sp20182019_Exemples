package spu05_exemples;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
public class SPU05EXE01Part2 {
	public static void main(String[] args) {
		SPU05Utils utilitats=new SPU05Utils();
		try {
			File fitxer=utilitats.obtenirFitxer("spu05_exemples/clau");
			FileInputStream cis = new FileInputStream(fitxer);
			byte[] clave = new byte[(int) fitxer.length()];
			cis.read(clave);
			DESKeySpec keyspec = new DESKeySpec(clave);
			SecretKeyFactory keyfac = SecretKeyFactory.getInstance("DES");
			SecretKey key = keyfac.generateSecret(keyspec);
			Cipher desCipher = Cipher.getInstance("DES");
			desCipher.init(Cipher.DECRYPT_MODE, key);
			fitxer=utilitats.obtenirFitxer("spu05_exemples/fitxer_xifrat");
			FileInputStream is = new FileInputStream(fitxer);
			fitxer=utilitats.obtenirFitxer("spu05_exemples/fitxer_desxifrat.txt");
			FileOutputStream os = new FileOutputStream(fitxer);
			byte[] buffer = new byte[16];
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