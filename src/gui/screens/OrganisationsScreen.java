package gui.screens;

import gui.Button;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public class OrganisationsScreen extends AbstractScreen {
	private final Button newOrganisationButton = new Button("New Organisation", 256, 128, 256, 32);
	
	@Override
	protected void internalEnter(GameContainer gc, StateBasedGame sbg) {}
	
	@Override
	protected void internalInit(GameContainer gc, StateBasedGame sbg) {
		container.addComponent(newOrganisationButton);
	}
	
	@Override
	protected void internalLeave(GameContainer gc, StateBasedGame sbg) {}
	
	@Override
	protected void internalRender(GameContainer gc, StateBasedGame sbg, Graphics graphics) {}
	
	@Override
	protected void internalUpdate(GameContainer gc, StateBasedGame sbg, int delta) {}
	
}
