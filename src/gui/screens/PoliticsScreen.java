package gui.screens;

import gui.Button;
import gui.ButtonList;
import gui.ButtonListListener;
import gui.ButtonListener;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public class PoliticsScreen extends AbstractScreen {
	private String currentSelectedItem;
	private View currentView = View.POLICIES;
	private ButtonList policies;
	private Button policiesButton;
	private ButtonList simulations;
	private Button simulationsButton;
	
	@Override
	public void internalEnter(GameContainer gc, StateBasedGame sbg) {
		
	}
	
	@Override
	public void internalInit(GameContainer gc, StateBasedGame sbg) {
		
	}
	
	@Override
	protected void internalLeave(GameContainer gc, StateBasedGame sbg) {}
	
	@Override
	public void internalRender(GameContainer gc, StateBasedGame sbg, Graphics graphics) {
		
		switch(currentView) {
			case POLICIES:
				break;
			case SIMULATIONS:
				break;
		}
	}
	
	@Override
	public void internalUpdate(GameContainer gc, StateBasedGame sbg, int delta) {
		
	}
	
	public class SetItemListener implements ButtonListListener {
		
		@Override
		public void pressed(String text) {
			currentSelectedItem = text;
		}
		
	}
	
	public class SetListListener implements ButtonListener {
		private final ButtonList on, off;
		private final View view;
		
		public SetListListener(ButtonList on, ButtonList off, View view) {
			this.on = on;
			this.off = off;
			this.view = view;
		}
		
		@Override
		public void pressed(Button b) {
			off.setEnabled(false);
			off.setVisible(false);
			on.setEnabled(true);
			on.setVisible(true);
			currentView = view;
		}
	}
	
	public static enum View {
		POLICIES,
		SIMULATIONS
	}
}
