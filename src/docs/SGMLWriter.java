package docs;

import java.io.IOException;
import java.io.OutputStream;

/**
 * An XML writer, that writes XML based on the callbacks of the {@link SGMLListener}
 * interface.
 * @author Silas Nordgren
 * @since 0.0.1
 * @see SGMLReader XMLEventListener
 */
public class SGMLWriter extends AbstractWriter implements
	SGMLListener {
	
	public SGMLWriter(OutputStream os) {
		super(os);
	}

	@Override
	public void elementEnd(String name) {
		try {
			this.writeTabs();
			getWriter().write('<');
			getWriter().write('/');
			getWriter().write(name);
			getWriter().write('>');
			decrementTabLevel();
			getWriter().println();
		} catch(IOException exception) {
			throw new RuntimeException(exception);
		}
	}
	
	@Override
	public void elementStart(String name) {
		try {
			incrementTabLevel();
			writeTabs();
			getWriter().write('<');
			getWriter().write(name);
			getWriter().write('>');
			getWriter().println();
		} catch(IOException exception) {
			throw new RuntimeException(exception);
		}
	}
	
	@Override
	public void textNode(String data) {
		try {
			incrementTabLevel();
			writeTabs();
			decrementTabLevel();
			getWriter().write(data);
			getWriter().println();
		} catch(IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	public void dataElement(String name, String data) {
		elementStart(name);
		textNode(data);
		elementEnd(name);
	}
}
