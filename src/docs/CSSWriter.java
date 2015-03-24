package docs;

import java.io.IOException;
import java.io.OutputStream;

public class CSSWriter extends AbstractWriter implements CSSListener {

	public CSSWriter(OutputStream os) {
		super(os);
	}

	@Override
	public void elementStart(String name) {
		System.out.println("element start: " + name);
		getWriter().write(name);
		getWriter().write(" ");
		getWriter().write("{");
		getWriter().println();
		incrementTabLevel();
		incrementTabLevel();
	}

	@Override
	public void elementEnd() {
		System.out.println("element end");
		getWriter().write("}");
		getWriter().println();
		decrementTabLevel();
		decrementTabLevel();
	}

	@Override
	public void textNode(String name, String data) {
		System.out.println("text node: " + name + ":" + data);
		try {
			writeTabs();
			getWriter().write(name + ": " + data + ";");
			getWriter().println();
		} catch(IOException exception) {
			throw new RuntimeException(exception);
		}
	}
}
