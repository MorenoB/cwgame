package docs;

public interface CSSListener {
	public void elementStart(String name);
	public void elementEnd();
	public void textNode(String name, String data);
}
