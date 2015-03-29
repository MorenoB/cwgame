package game;

import gui.Button;
import gui.ButtonBuilder;
import gui.Container;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class MenuState extends BasicGameState {
	private static final MenuState INSTANCE = new MenuState();
	private final Color backgroundColor = new Color(43, 28, 107);
	private final Container container = new Container();
	// private Image sovietFlag, usFlag;
	private Image titleScreenImage;
	
	public int getID() {
		return 1;
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		ButtonBuilder bb = new ButtonBuilder();
		float buttonX = (gc.getWidth() / 2) - 64;
		float buttonY = (gc.getHeight() / 2) - 16;
		bb.x(buttonX).image(new Image("res/gfx/interface/BlueButton128_32.png")).width(128).height(32);
		
		Button exitButton = bb.y(buttonY + 16).text("Exit").instantiate();
		exitButton.addButtonListener((button) -> {
			gc.exit();
		});
		container.addComponent(exitButton);
		
		Button playButton = bb.y(buttonY - 16).text("Play").instantiate();
		playButton.addButtonListener((button) -> {
			sbg.enterState(MapViewState.get().getID());
		});
		container.addComponent(playButton);
		
		/*
		 * sovietFlag = new Image("res/gfx/flags/Flag of the Soviet Union.png"); usFlag =
		 * new Image("res/gfx/flags/Flag of the United States.png");
		 */
		String titleScreenImagePath = GameContext.defines.getField("titleScreenImage");
		titleScreenImage = new Image(titleScreenImagePath);
	}
	
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics gfx) throws SlickException {
		/*
		 * gfx.setColor(backgroundColor); gfx.fillRect(0, 0, gc.getWidth(),
		 * gc.getHeight());
		 * 
		 * /*sovietFlag.startUse(); sovietFlag.drawEmbedded(gc.getWidth() / 2,
		 * (gc.getHeight() / 2) - 96 - 16, 160, 80); sovietFlag.endUse();
		 * usFlag.startUse(); usFlag.drawEmbedded((gc.getWidth() / 2) - 160,
		 * (gc.getHeight() / 2) - 96 - 16, 160, 80); usFlag.endUse();
		 */
		
		titleScreenImage.startUse();
		titleScreenImage.drawEmbedded(0, 0, gc.getWidth(), gc.getHeight());
		titleScreenImage.endUse();
		container.render(gc, sbg, gfx);
		ScreenshotUtility.update(gc, sbg, gfx);
		
	}
	
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		EscapeExitUtility.update(gc);
		container.update(gc, sbg, delta);
	}
	
	public static MenuState get() {
		return INSTANCE;
	}
}
