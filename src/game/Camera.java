package game;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;

public class Camera {
	private final float width, height;
	public float x, y;
	
	public Camera(float width, float height) {
		this.width = width;
		this.height = height;
	}
	
	public void update(GameContainer gc, int delta) {
		Input input = gc.getInput();
		float mouseX = input.getMouseX();
		float mouseY = input.getMouseY();
		
		if(mouseX < (gc.getWidth() * 0.1)) {
			x -= 1 * delta;
		}
		if(mouseY < (gc.getHeight() * 0.1)) {
			y -= 1 * delta;
		}
		if(mouseX > (gc.getWidth() * 0.9)) {
			x += 1 * delta;
		}
		if(mouseY > (gc.getHeight() * 0.9)) {
			y += 1 * delta;
		}
		
		if(x > (width - gc.getWidth())) {
			x = width - gc.getWidth();
		}
		if(y > (height - gc.getHeight())) {
			y = height - gc.getHeight();
		}
		if(x < 0) {
			x = 0;
		}
		if(y < 0) {
			y = 0;
		}
	}
}
