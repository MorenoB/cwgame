package gui;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

public class Background extends AbstractComponent {
	private final Image image;
	
	public Background(Image image, int x, int y, int width, int height) {
		super(x, y, width, height);
		this.image = image;
	}
	
	@Override
	public void internalRender(GameContainer container, StateBasedGame game, Graphics graphics) {
		image.startUse();
		image.drawEmbedded(x, y, width, height);
		image.endUse();
	}
	
	@Override
	public void internalUpdate(GameContainer container, StateBasedGame game, int delta) {
		
	}
	
}
