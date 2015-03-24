package gui.views;

import map.Province;
import gui.AbstractComponent;

public abstract class AbstractView extends AbstractComponent {
	public static final int WIDTH = 512;
	public static final int HEIGHT = 256;

	protected static final int TEXTX = 16;
	protected static final int TEXTY = 16;

	private Province currentProvince;

	public AbstractView(float x, float y) {
		super(x, y, WIDTH, HEIGHT);
	}

	public Province getCurrentProvince() {
		return currentProvince;
	}

	public void setCurrentProvince(Province value) {
		currentProvince = value;
	}
}
