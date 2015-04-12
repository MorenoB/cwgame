package gui.screens;

import game.states.PlayGameState;
import gui.Button;
import gui.ButtonListener;

public class SetScreenListener implements ButtonListener {
	private final CurrentScreen value;
	
	public SetScreenListener(CurrentScreen screen) {
		value = screen;
	}
	
	@Override
	public void pressed(Button b) {
		PlayGameState.get().setCurrentScreen(PlayGameState.get().lastGC, PlayGameState.get().lastSBG, value);
	}
	
}
