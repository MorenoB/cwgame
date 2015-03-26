package gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public class ColorRectangle extends AbstractComponent {
	private Color color;
	
	public ColorRectangle(Color color, float x, float y, float width, float height) {
		super(x, y, width, height);
		this.color = color;
		setPriority(-1);
	}
	
	public ColorRectangle(float x, float y, float width, float height) {
		this(null, x, y, width, height);
	}
	
	public Color getColor() {
		return color;
	}
	
	@Override
	public void internalRender(GameContainer container, StateBasedGame game, Graphics graphics) {
		if(color == null) {
			graphics.setColor(AbstractComponent.getRenderColor());
		} else {
			graphics.setColor(color);
		}
		
		graphics.fillRect(x, y, width, height);
		graphics.setColor(Color.white);
	}
	
	@Override
	public void internalUpdate(GameContainer container, StateBasedGame game, int delta) {}
	
	public void setColor(Color value) {
		color = value;
	}
	
}
