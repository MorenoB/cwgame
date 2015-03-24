package gui;

import org.newdawn.slick.Image;

import util.Builder;

public class ButtonBuilder implements Builder<Button> {
	private Image image;
	private ButtonListener listener;
	private String text;
	private float x, y, width, height;
	
	public float getHeight() {
		return height;
	}
	
	public Image getImage() {
		return image;
	}
	
	public ButtonListener getListener() {
		return listener;
	}
	
	public String getText() {
		return text;
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
	
	public ButtonBuilder height(float value) {
		height = value;
		return this;
	}
	
	public ButtonBuilder image(Image value) {
		image = value;
		return this;
	}
	
	public Button instantiate() {
		Button newButton = new Button(image, text, x, y, width, height);
		newButton.addButtonListener(listener);
		return newButton;
	}
	
	public ButtonBuilder listener(ButtonListener value) {
		listener = value;
		return this;
	}
	
	public ButtonBuilder text(String value) {
		text = value;
		return this;
	}
	
	public ButtonBuilder width(float value) {
		width = value;
		return this;
	}
	
	public ButtonBuilder x(float value) {
		x = value;
		return this;
	}
	
	public ButtonBuilder y(float value) {
		y = value;
		return this;
	}
}
