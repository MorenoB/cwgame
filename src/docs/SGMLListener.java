package docs;

/**
 * A simple interface for an object that listens to events fired by an {@link SGMLReader}
 * object.
 * @author Silas Nordgren
 * @since 0.0.1
 */
public interface SGMLListener {
	
	/**
	 * Ends the current element.
	 * @param name The name of the ended element.
	 * @since 0.0.1
	 */
	public void elementEnd(String name);
	
	/**
	 * Begins a new element.
	 * @param name The name of the new element.
	 * @since 0.0.1
	 */
	public void elementStart(String name);
	
	/**
	 * Callback for text nodes. There can be only one text node per element, and so a
	 * {@link #elementEnd(String)} call is almost certain after a call to this method.
	 * @param data The data of the text node.
	 * @since 0.0.1
	 */
	public void textNode(String data);
}
