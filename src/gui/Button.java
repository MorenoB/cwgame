package gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;

public class Button extends AbstractComponent {
	
	public static Color coverColor = new Color(0, 0, 0, 31);
	private boolean determinedTextX = false;
	private boolean hovering, pressed;
	private final Image image;
	private ButtonListener listener;
	private boolean renderCover = true;
	private final String text;
	private Color textColor = Color.white;
	private float textX;
	private final float textY;
	private final float x, y, width, height;
	
	public Button(Image image, String text, float x, float y, float width, float height) {
		super(x, y, width, height);
		this.image = image;
		this.text = text;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		if(text.length() < 10) {
			textX = (x + (width / 2)) - (6 * (text.length() - 1));
		} else {
			textX = x;
		}
		
		textY = getTextY(y, height);
		setPriority(1);
	}
	
	public Button(String text, float x, float y, float width, float height) {
		this(null, text, x, y, width, height);
	}
	
	public void addButtonListener(ButtonListener value) {
		listener = value;
	}
	
	@Override
	public void internalRender(GameContainer container, StateBasedGame game, Graphics graphics) {
		if(!determinedTextX) {
			textX = getTextX(x, width, text, graphics);
			determinedTextX = true;
		}
		
		if(image != null) {
			image.startUse();
			image.drawEmbedded(x, y, width, height);
			image.endUse();
		} else {
			graphics.setColor(renderColor);
			graphics.fillRect(x, y, width, height);
			if(renderCover) {
				graphics.setColor(coverColor);
				graphics.fillRect(x, y, width, height);
			}
			
		}
		
		graphics.setColor(textColor);
		graphics.drawString(text, textX, textY);
		if(hovering) {
			graphics.setColor(hoverColor);
			graphics.fillRect(x, y, width, height);
		}
		
		graphics.setColor(Color.white);
	}
	
	@Override
	public void internalUpdate(GameContainer container, StateBasedGame game, int delta) {
		
		final Input input = container.getInput();
		final int mouseX = input.getMouseX();
		final int mouseY = input.getMouseY();
		
		if((mouseX >= x) && (mouseX <= (x + width)) && (mouseY >= y) && (mouseY <= (y + height))) {
			
			if(input.isMouseButtonDown(0)) {
				if(!pressed) {
					pressed = true;
					/*
					 * for(final ButtonListener listener: listeners) {
					 * listener.onPress(this, mouseX, mouseY); }
					 */
				}
			} else {
				if(pressed) {
					pressed = false;
					listener.pressed(this);
				} else {
					if(!hovering) {
						hovering = true;
						/*
						 * for(final ButtonListener listener: listeners) {
						 * listener.onHover(this, mouseX, mouseY); }
						 */
					}
				}
			}
		} else {
			hovering = false;
			pressed = false;
		}
	}
	
	public void setRenderCover(boolean value) {
		renderCover = value;
	}
	
	public void setTextColor(Color value) {
		textColor = value;
	}
	
	public void setVisibleAndEnabled(boolean value) {
		visible = value;
		enabled = value;
	}
	
	public boolean willRenderCover() {
		return renderCover;
	}
}
