package gui;

import util.Builder;

public class MutableLabelBuilder implements Builder<MutableLabel> {
	private String text;
	private float x, y, width, height;
	
	public float getHeight() {
		return height;
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
	
	public MutableLabelBuilder height(float value) {
		height = value;
		return this;
	}
	
	public MutableLabel instantiate() {
		return new MutableLabel(text, x, y, width, height);
	}
	
	public MutableLabelBuilder text(String value) {
		text = value;
		return this;
	}
	
	public MutableLabelBuilder width(float value) {
		width = value;
		return this;
	}
	
	public MutableLabelBuilder x(float value) {
		x = value;
		return this;
	}
	
	public MutableLabelBuilder y(float value) {
		y = value;
		return this;
	}
}
