package gui;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

public class MutableImageIcon extends AbstractComponent {
	private Image image;
	
	public MutableImageIcon(Image image, float x, float y, float width, float height) {
		super(x, y, width, height);
	}
	
	public Image getImage() {
		return image;
	}
	
	@Override
	public void internalRender(GameContainer container, StateBasedGame game, Graphics graphics) {
		if(image != null) {
			image.startUse();
			image.drawEmbedded(x, y, width, height);
			image.endUse();
		}
	}
	
	@Override
	public void internalUpdate(GameContainer container, StateBasedGame game, int delta) {}
	
	public void setImage(Image value) {
		image = value;
	}
	
}
