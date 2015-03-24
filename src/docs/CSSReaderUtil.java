package docs;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class CSSReaderUtil {
	public static CSSObject readFromPath(String path) {
		return readFromPath(new File(path));
	}
	
	public static CSSObject readFromPath(File path) {
		CSSObject object = new CSSObject();
		
		try {
			CSSReader reader = new CSSReader(new FileInputStream(path), object);
			reader.read();
			reader.close();
		} catch(IOException exception) {
			exception.printStackTrace();
		}
		
		return object;
	}
}
