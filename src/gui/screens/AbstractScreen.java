package gui.screens;

import gui.AbstractComponent;
import gui.Button;
import gui.Container;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public abstract class AbstractScreen implements Screen {
	protected static Color backgroundColor;
	protected final Button backButton = new Button("Back", 0, 0, 64, 32);
	protected final Container container = new Container();
	
	@Override
	public final void enter(GameContainer gc, StateBasedGame sbg) {
		backgroundColor = AbstractComponent.getRenderColor().darker();
		container.setVisible(true);
		container.setEnabled(true);
		internalEnter(gc, sbg);
	}
	
	@Override
	public final void init(GameContainer gc, StateBasedGame sbg) {
		backButton.addButtonListener(new SetScreenListener(CurrentScreen.MAP));
		backButton.setRenderCover(false);
		container.addComponent(backButton);
		internalInit(gc, sbg);
	}
	
	protected abstract void internalEnter(GameContainer gc, StateBasedGame sbg);
	
	protected abstract void internalInit(GameContainer gc, StateBasedGame sbg);
	
	protected abstract void internalLeave(GameContainer gc, StateBasedGame sbg);
	
	protected abstract void internalRender(GameContainer gc, StateBasedGame sbg, Graphics graphics);
	
	protected abstract void internalUpdate(GameContainer gc, StateBasedGame sbg, int delta);
	
	@Override
	public final void leave(GameContainer gc, StateBasedGame sbg) {
		internalLeave(gc, sbg);
		container.setVisible(false);
		container.setEnabled(false);
	}
	
	@Override
	public final void render(GameContainer gc, StateBasedGame sbg, Graphics graphics) {
		graphics.setColor(backgroundColor);
		graphics.fillRect(0, 0, gc.getWidth(), gc.getHeight());
		internalRender(gc, sbg, graphics);
		container.render(gc, sbg, graphics);
	}
	
	@Override
	public final void update(GameContainer gc, StateBasedGame sbg, int delta) {
		container.update(gc, sbg, delta);
		internalUpdate(gc, sbg, delta);
	}
}
