package gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public class MutableLabel extends AbstractComponent {
	private static Color color = Color.white;
	private boolean centerText = true;
	private String text;
	
	public MutableLabel(String text, float x, float y, float width, float height) {
		super(x, y, width, height);
		this.text = text;
	}
	
	public String getText() {
		return text;
	}
	
	@Override
	public void internalRender(GameContainer container, StateBasedGame game, Graphics graphics) {
		float textX, textY;
		
		if(!((width == 0) || (height == 0))) {
			graphics.setColor(AbstractComponent.getRenderColor());
			graphics.fillRect(x, y, width, height);
			graphics.setColor(color);
			textX = getTextX(x, width, text, graphics);
			textY = getTextY(y, height);
		} else {
			textX = x;
			textY = y;
		}
		
		graphics.setColor(color);
		graphics.drawString(text, textX, textY);
		graphics.setColor(Color.white);
	}
	
	@Override
	public void internalUpdate(GameContainer container, StateBasedGame game, int delta) {}
	
	public boolean isTextCentered() {
		return centerText;
	}
	
	public void setText(String value) {
		text = value;
	}
	
	public void setTextCentered(boolean value) {
		centerText = value;
	}
	
	public static Color getTextColor() {
		return color;
	}
	
	public static void setTextColor(Color color) {
		MutableLabel.color = color;
	}
}
