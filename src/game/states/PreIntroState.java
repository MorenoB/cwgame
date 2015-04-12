package game.states;

import economy.CurrencyTable;
import economy.ResourceTable;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class PreIntroState extends BasicGameState {
	private static final PreIntroState INSTANCE = new PreIntroState();
	private boolean renderedOnce = false;
	
	@Override
	public int getID() {
		return 5;
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		
	}
	
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics gfx) throws SlickException {
		float textWidth = gfx.getFont().getWidth("Loading...");
		float textHeight = gfx.getFont().getHeight("Loading...");
		float textX = gc.getWidth() / 2 - textWidth / 2;
		float textY = gc.getHeight() / 2 - textHeight / 2;
		gfx.drawString("Loading...", textX, textY);
		renderedOnce = true;
	}
	
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		if(renderedOnce) {
			sbg.enterState(IntroState.get().getID());
		}
	}
	
	public static PreIntroState get() {
		return INSTANCE;
	}
}
