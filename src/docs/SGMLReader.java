package docs;

import java.io.IOException;
import java.io.InputStream;

/**
 * An XML reader, that calls the callbacks in the {@link SGMLListener} interface.
 * @author Silas Nordgren
 * @since 0.0.1
 * @see SGMLListener XMLWriter
 */
public class SGMLReader extends AbstractReader {
	private final SGMLListener eventListener;
	private boolean doubleQuote = false;
	private boolean singleQuote = false;
	
	/**
	 * Constructor.
	 * @param is The <code>InputStream</code> of data to read from. This is internally
	 * wrapped in a <code>DataInputStream</code> if it isn't already, to enable reading.
	 * @param eventListener The callbacks.
	 * @since 0.0.1
	 */
	public SGMLReader(InputStream is, SGMLListener eventListener) {
		super(is);
		this.eventListener = eventListener;
	}
	
	protected void fireElementEvent()
		throws IOException {
		String name = readUntil('>');
		if(name.charAt(0) == '/') this.eventListener.elementEnd(name.substring(1));
		else this.eventListener.elementStart(name);
	}
	
	/**
	 * Reads data and calls callbacks appropriately.
	 * @throws IOException Should something go wrong while reading.
	 * @since 0.0.1
	 */
	public void read()
		throws IOException {
		while(!hasRead()) {
			int integer = getReader().read();
			if(integer == -1) {
				// end of stream
				endReading();
				break;
			}
			
			char character = (char) (integer);
			if(character == ' ') continue;
			if(character == '\n') continue;
			if(character == '\t') continue;
			if(character == '\r') continue;
			if(character == '"') if(this.doubleQuote = !this.doubleQuote) continue;
			if(character == '\'') if(this.singleQuote = !this.singleQuote) continue;
			
			if(character == '<' && !this.doubleQuote && !this.singleQuote) this
				.fireElementEvent();
			else {
				this.eventListener.textNode(character + readUntil('<'));
				this.fireElementEvent();
			}
		}
		
		endReading();
		getReader().close();
	}
}
