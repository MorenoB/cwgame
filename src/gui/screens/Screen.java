package gui.screens;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public interface Screen {
	public void enter(GameContainer gc, StateBasedGame sbg);
	public void init(GameContainer gc, StateBasedGame sbg);
	public void leave(GameContainer gc, StateBasedGame sbg);
	public void render(GameContainer gc, StateBasedGame sbg, Graphics graphics);
	public void update(GameContainer gc, StateBasedGame sbg, int delta);
}
