package docs;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

public class AbstractWriter implements AutoCloseable {
	private final PrintWriter writer;
	private int tabLevel = -1;

	/**
	 * Constructor.
	 * 
	 * @param os
	 * @since 0.0.1
	 */
	public AbstractWriter(OutputStream os) {
		this.writer = new PrintWriter(os);
	}

	protected void decrementTabLevel() {
		tabLevel--;
	}

	protected void incrementTabLevel() {
		tabLevel++;
	}

	protected PrintWriter getWriter() {
		return writer;
	}

	protected int getTabLevel() {
		return tabLevel;
	}

	@Override
	public void close() {
		getWriter().flush();
		getWriter().close();
	}

	protected void writeTabs() throws IOException {
		for (int iter = 0; iter < getTabLevel(); iter++)
			getWriter().write('\t');
	}
}
