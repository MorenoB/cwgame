package docs;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class SGMLReaderUtil {
	public static SGMLObject readFromPath(String path) {
		return readFromPath(new File(path));
	}
	
	public static SGMLObject readFromPath(File path) {
		SGMLObject object = new SGMLObject();
		
		try {
			SGMLReader reader = new SGMLReader(new FileInputStream(path), object);
			reader.read();
			reader.close();
		} catch(IOException exception) {
			exception.printStackTrace();
		}
		
		return object;
	}
}
