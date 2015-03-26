package gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;

public class ButtonList extends AbstractList {
	private int height;
	private int hoverRow;
	private int lineHeight;
	private ButtonListListener listener;
	private int offset;
	private boolean pressed = false;
	
	public ButtonList(float x, float y, float width, int rows, String[] data) {
		super(x, y, width, 0, rows, data);
		this.data = data;
		displayRows = rows;
		offset = 0;
	}
	
	public void addListener(ButtonListListener value) {
		listener = value;
	}
	
	@Override
	public int compareTo(Component o) {
		return 0;
	}
	
	@Override
	public boolean contains(float x, float y) {
		return (x >= this.x) && (x < (this.x + width)) && (y >= this.y) && (y < (this.y + height));
	}
	
	public String[] getData() {
		return data;
	}
	
	public int getDisplayRows() {
		return displayRows;
	}
	
	@Override
	public void internalRender(GameContainer container, StateBasedGame game, Graphics graphics) {
		if(lineHeight == 0) {
			lineHeight = graphics.getFont().getLineHeight();
			height = lineHeight * (displayRows + 2);
		}
		
		graphics.setColor(getRenderColor());
		graphics.fillRect(x, y, width, height);
		graphics.setColor(Color.white);
		graphics.drawString("/\\", x, y);
		int i = 0;
		for(; i < displayRows; i++) {
			int index = i + offset;
			if(data == null) {
				break;
			}
			if(index >= data.length) {
				height = (i + 2) * lineHeight;
				displayRows = i + 2;
				break;
			}
			
			if((index < 0) || (index >= data.length)) {
			} else {
				String toRender = data[index];
				graphics.drawString(toRender, x, y + ((i + 1) * graphics.getFont().getLineHeight()));
			}
			
		}
		i++;
		graphics.drawString("\\/", x, y + (i * graphics.getFont().getLineHeight()));
		
		if(hasHover) {
			graphics.setColor(hoverColor);
			int hoverY = (int) (y + ((hoverRow) * lineHeight));
			graphics.fillRect(x, hoverY, width, lineHeight);
		}
	}
	
	@Override
	public void internalUpdate(GameContainer container, StateBasedGame game, int delta) {
		Input input = container.getInput();
		int mouseX = input.getMouseX();
		int mouseY = input.getMouseY();
		hasHover = contains(mouseX, mouseY);
		if(hasHover) {
			hoverRow = (int) (mouseY - y) / lineHeight;
			int pressedRow = (hoverRow + offset) - 1;
			if(input.isMousePressed(0)) {
				if((hoverRow != 0) && (hoverRow != (displayRows + 1))) {
					// listener.onPress(pressedRow, data[pressedRow], mouseX, mouseY);
				}
				
				pressed = true;
				
			} else {
				if(pressed) {
					if(hoverRow == 0) {
						offset--;
					} else
						if(hoverRow == (displayRows + 1)) {
							offset++;
						} else {
							// listener.pressed(pressedRow, data[pressedRow], mouseX,
							// mouseY);
							if((pressedRow < 0) || (pressedRow >= data.length)) {
							} else {
								listener.pressed(data[pressedRow]);
							}
						}
					pressed = false;
				}
			}
		}
		
		if(offset < 0) {
			offset = 0;
		}
		
		if(offset > (data.length - displayRows)) {
			offset = data.length - displayRows;
		}
	}
	
	public void setData(String[] value) {
		data = value;
	}
}
