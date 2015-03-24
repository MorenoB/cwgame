package game;

import gui.screens.CurrentScreen;
import gui.screens.DiplomacyScreen;
import gui.screens.EconomyScreen;
import gui.screens.EspionageScreen;
import gui.screens.MapScreen;
import gui.screens.MilitaryScreen;
import gui.screens.OrganisationsScreen;
import gui.screens.PoliticsScreen;
import gui.screens.PopulationScreen;
import gui.screens.Screen;
import gui.screens.TechnologyScreen;
import gui.screens.TradeScreen;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class PlayGameState extends BasicGameState {
	private static final PlayGameState INSTANCE = new PlayGameState();
	
	private CurrentScreen currentScreen = CurrentScreen.MAP;
	public GameContainer lastGC;
	public StateBasedGame lastSBG;
	private final Map<CurrentScreen, Screen> screens = new HashMap<>();
	{
		screens.put(CurrentScreen.DIPLOMACY, new DiplomacyScreen());
		screens.put(CurrentScreen.ECONOMY, new EconomyScreen());
		screens.put(CurrentScreen.ESPIONAGE, new EspionageScreen());
		screens.put(CurrentScreen.MILITARY, new MilitaryScreen());
		screens.put(CurrentScreen.MAP, new MapScreen());
		screens.put(CurrentScreen.POLITICS, new PoliticsScreen());
		screens.put(CurrentScreen.POPULATION, new PopulationScreen());
		screens.put(CurrentScreen.TECHNOLOGY, new TechnologyScreen());
		screens.put(CurrentScreen.TRADE, new TradeScreen());
		screens.put(CurrentScreen.ORGANISATIONS, new OrganisationsScreen());
	}
	
	@Override
	public void enter(GameContainer gc, StateBasedGame sbg) {
		screens.get(currentScreen).enter(gc, sbg);
	}
	
	public int getID() {
		return 4;
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		for(Screen screen: screens.values()) {
			screen.init(gc, sbg);
		}
	}
	
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics gfx) throws SlickException {
		gfx.setColor(Color.black);
		gfx.fillRect(0, 0, gc.getWidth(), gc.getHeight());
		gfx.setColor(Color.white);
		screens.get(currentScreen).render(gc, sbg, gfx);
		ScreenshotUtility.update(gc, sbg, gfx);
	}
	
	public void setCurrentScreen(GameContainer gc, StateBasedGame sbg, CurrentScreen value) {
		screens.get(currentScreen).leave(gc, sbg);
		currentScreen = value;
		screens.get(value).enter(gc, sbg);
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		EscapeExitUtility.update(gc);
		screens.get(currentScreen).update(gc, sbg, delta);
		if(!GameContext.paused && (currentScreen != CurrentScreen.MAP)) {
			screens.get(CurrentScreen.MAP).update(gc, sbg, delta);
			
		}
		lastGC = gc;
		lastSBG = sbg;
	}
	
	public static PlayGameState get() {
		return INSTANCE;
	}
}
