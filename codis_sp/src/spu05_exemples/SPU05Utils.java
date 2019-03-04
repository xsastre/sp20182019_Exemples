package spu05_exemples;

import java.io.File;
import java.net.URL;

public class SPU05Utils {
	public File obtenirFitxer(String path) {
		URL fitxer=ClassLoader.getSystemResource(path);
		File file = new File(fitxer.getPath());
		return file;
	}
}
