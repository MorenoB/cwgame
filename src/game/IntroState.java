package game;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import economy.CurrencyTable;
import economy.ResourceTable;

public class IntroState extends BasicGameState {
	private static final IntroState INSTANCE = new IntroState();
	
	public IntroState() {}
	
	@Override
	public int getID() {
		return 0;
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		ResourceTable.init();
		CurrencyTable.init();
		GameContext.init();
		gc.setUpdateOnlyWhenVisible(false);
	}
	
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics gfx) throws SlickException {}
	
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		sbg.enterState(MenuState.get().getID());
	}
	
	public static IntroState get() {
		return INSTANCE;
	}
}
