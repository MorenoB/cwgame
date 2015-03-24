package gui;

public abstract class AbstractList extends AbstractComponent {
	protected String[] data;
	protected int displayRows;
	protected boolean hasHover;
	protected final int offset;
	
	public AbstractList(float x, float y, float width, float height, int rows, String[] data) {
		super(x, y, width, height);
		this.data = data;
		displayRows = rows;
		offset = 0;
	}
	
}
