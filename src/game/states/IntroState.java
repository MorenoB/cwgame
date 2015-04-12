package game.states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import economy.CurrencyTable;
import economy.ResourceTable;
import game.GameContext;

public class IntroState extends BasicGameState {
	private static final IntroState INSTANCE = new IntroState();
	private boolean renderedOnce = false;
	
	public IntroState() {}
	
	@Override
	public int getID() {
		return 0;
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		gc.setUpdateOnlyWhenVisible(false);
		ResourceTable.init();
		CurrencyTable.init();
		GameContext.init();
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
			sbg.enterState(MenuState.get().getID());
		}
	}
	
	public static IntroState get() {
		return INSTANCE;
	}
}
