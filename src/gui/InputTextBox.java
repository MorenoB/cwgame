package gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public class InputTextBox extends AbstractComponent {
	private final StringBuilder text = new StringBuilder();
	
	public InputTextBox(float x, float y, float width, float height) {
		super(x, y, width, height);
	}
	
	@Override
	protected void internalRender(GameContainer gc, StateBasedGame sbg, Graphics graphics) {
		graphics.setColor(AbstractComponent.renderColor.darker());
		graphics.fillRect(x, y, width, height);
		graphics.setColor(Color.white);
		graphics.drawString(text.toString(), x, y);
	}
	
	@Override
	protected void internalUpdate(GameContainer gc, StateBasedGame sbg, int delta) {
		
	}
}
