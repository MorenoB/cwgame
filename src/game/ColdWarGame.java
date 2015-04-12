package game;

import game.states.IntroState;
import game.states.MapViewState;
import game.states.MenuState;
import game.states.PlayGameState;
import game.states.PreIntroState;

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
		addState(PreIntroState.get());
		enterState(PreIntroState.get().getID());
	}
	
	public static ColdWarGame get() {
		return INSTANCE;
	}
}
