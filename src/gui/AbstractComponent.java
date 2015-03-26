package gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public abstract class AbstractComponent implements Component {
	private static final String INVALID_PRIORITY_EXCEPTION = "Invalid priority value. Override compareTo for custom behavior.";
	
	private static Color renderColor;
	protected final Color hoverColor = new Color(255, 255, 255, 127);
	private int priority = 0;
	protected boolean visible = true, enabled = true;
	protected final float x, y, width, height;
	
	public AbstractComponent(float x, float y) {
		this(x, y, 0, 0);
	}
	
	public AbstractComponent(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	@Override
	public int compareTo(Component o) {
		return priority;
	}
	
	public boolean contains(float x, float y) {
		return (x >= this.x) && (x < (this.x + width)) && (y >= this.y) && (y < (this.y + height));
	}
	
	public float getHeight() {
		return height;
	}
	
	public int getPriority() {
		return priority;
	}
	
	public float getWidth() {
		return width;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	//@formatter:off
	protected abstract void internalRender(GameContainer gc, StateBasedGame sbg, Graphics graphics);
	
	protected abstract void internalUpdate(GameContainer gc, StateBasedGame sbg, int delta);
	//@formatter:on
	public boolean isEnabled() {
		return enabled;
	}
	
	public boolean isVisible() {
		return visible;
	}
	
	public final void render(GameContainer gc, StateBasedGame sbg, Graphics graphics) {
		if(visible) {
			internalRender(gc, sbg, graphics);
		}
	}
	
	public void setEnabled(boolean value) {
		enabled = value;
	}
	
	public void setPriority(int value) {
		if((value > 1) || (value < -1))
			throw new RuntimeException(INVALID_PRIORITY_EXCEPTION);
		
		priority = value;
	}
	
	public void setVisible(boolean value) {
		visible = value;
	}
	
	public final void update(GameContainer gc, StateBasedGame sbg, int delta) {
		if(enabled) {
			internalUpdate(gc, sbg, delta);
		}
	}
	
	public static Color getRenderColor() {
		return renderColor;
	}
	
	public static float getTextX(float x, float width, String text, Graphics graphics) {
		float textX = x;
		textX += width / 2;
		textX -= graphics.getFont().getWidth(text) / 2;
		return textX;
	}
	
	public static float getTextY(float y, float height) {
		return (y + (height / 2)) - 8;
	}
	
	public static void setRenderColor(Color value) {
		renderColor = value;
	}
}
