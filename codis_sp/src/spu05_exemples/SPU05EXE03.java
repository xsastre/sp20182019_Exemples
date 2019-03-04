
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.MessageDigest;

public class SPU05EXE03 {

	public static void main(String[] args) {
		try {
			if (args.length != 1) {
				System.out.println("arguments: <algorisme de resum>");
				System.exit(1);
			}
			String algorisme = args[0];
			MessageDigest md = MessageDigest.getInstance(algorisme);
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String linia = br.readLine();
			while (linia != null) {
				md.update(linia.getBytes());
				linia = br.readLine();
			}
			byte[] resumen = md.digest();
			System.out.println("Resumen " + algorisme + ":\n" + new String(resumen));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}