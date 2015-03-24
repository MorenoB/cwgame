package docs;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public abstract class AbstractReader implements AutoCloseable {
	private static final String DEFAULT_RETURN = "";
	private final InputStreamReader is;
	private boolean read = false;

	public AbstractReader(InputStream is) {
		this.is = new InputStreamReader(is);
	}

	public void close() throws IOException {
		getReader().close();
	}

	protected InputStreamReader getReader() {
		return is;
	}

	/**
	 * True if the <code>InputStream</code> assigned to this reader has been
	 * read fully.
	 * 
	 * @return If the stream has been read.
	 * @since 0.0.1
	 */
	public boolean hasRead() {
		return this.read;
	}
	
	protected boolean isReading(int value) {
		if(value == -1) return false;
		return true;
	}

	protected String readUntil(char delimiter) throws IOException {
		int integer;
		boolean reading = this.isReading(integer = getReader().read());
		if (!reading) {
			// end of stream
			this.read = true;
			return DEFAULT_RETURN;
		}

		char character;
		StringBuilder stringBuilder = new StringBuilder();
		while (reading) {
			character = (char) (integer);
			if (character == delimiter)
				break;
			stringBuilder.append(character);
			reading = this.isReading(integer = getReader().read());
		}

		return stringBuilder.toString().trim();
	}
	
	protected void endReading() {
		read = true;
	}
	
	public abstract void read() throws IOException;
}
