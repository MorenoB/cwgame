package gui;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public interface Component extends Comparable<Component> {
	public boolean contains(float x, float y);
	public boolean isEnabled();
	public boolean isVisible();
	public void update(GameContainer container, StateBasedGame game, int delta);
	public void render(GameContainer container, StateBasedGame game, Graphics graphics);
	public void setEnabled(boolean value);
	public void setVisible(boolean value);
}
