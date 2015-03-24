package docs;

import java.io.IOException;
import java.io.InputStream;

import util.StringSanitizer;

public class CSSReader extends AbstractReader implements AutoCloseable {
	private final CSSListener eventListener;
	
	public CSSReader(InputStream is, CSSListener listener) {
		super(is);
		eventListener = listener;
	}

	@Override
	public void read() throws IOException {
		while(!hasRead()) {
			int integer = getReader().read();
			if(integer == -1) {
				endReading();
				break;
			}
			
			char character = (char) integer;
			if(character == ' ') continue;
			else if(character == '\n') continue;
			else if(character == '\t') continue;
			else if(character == '\r') continue;
			else {
				// text character detected
				String element = character + readUntil('}');
				element = StringSanitizer.sanitize(element);
				String[] elementPortions = element.split(" ");
				if(elementPortions.length < 4) throw new RuntimeException();
				eventListener.elementStart(elementPortions[0]);
				for(int i = 2; i < elementPortions.length-1; i+=2) {
					String name = elementPortions[i];
					String value = elementPortions[i+1];
					eventListener.textNode(name.substring(0, name.length()-1), value.substring(0, value.length()-1));
				}
				
				eventListener.elementEnd();
			}
		}
	}
}
