package game;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class ColdWarGame extends StateBasedGame {
	private static final ColdWarGame INSTANCE = new ColdWarGame();
	
	public ColdWarGame() {
		super(GameContext.defines.getField("name"));
	}
	
	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		addState(IntroState.get());
		addState(MenuState.get());
		addState(MapViewState.get());
		addState(PlayGameState.get());
		enterState(IntroState.get().getID());
	}
	
	public static ColdWarGame get() {
		return INSTANCE;
	}
}
