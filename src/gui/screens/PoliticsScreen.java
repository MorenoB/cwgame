package gui.screens;

import game.GameContext;
import gui.Button;
import gui.ButtonList;
import gui.ButtonListListener;
import gui.ButtonListener;

import java.util.Arrays;
import java.util.Set;

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
		Set<String> policyNameSet = GameContext.globalContext.getPolicies().keySet();
		String[] policyNameArray = new String[policyNameSet.size()];
		String[] policyNames = policyNameSet.toArray(policyNameArray);
		Arrays.sort(policyNames);
		policies = new ButtonList(64, 64 + 32, 128 * 3, 5, policyNames);
		policies.addListener(new SetItemListener());
		container.addComponent(policies);
		
		Set<String> simNameSet = GameContext.globalContext.getSimulations().keySet();
		String[] simNameArray = new String[simNameSet.size()];
		String[] simulationNames = simNameSet.toArray(simNameArray);
		Arrays.sort(simulationNames);
		simulations = new ButtonList(policies.getX(), policies.getY(), policies.getWidth(),
				policies.getDisplayRows(), simulationNames);
		simulations.addListener(new SetItemListener());
		simulations.setVisible(false);
		simulations.setEnabled(false);
		container.addComponent(simulations);
		
		policiesButton = new Button("Policies", 128, 64, 128, 32);
		policiesButton.addButtonListener(new SetListListener(policies, simulations, View.POLICIES));
		container.addComponent(policiesButton);
		
		simulationsButton = new Button("Simulations", 256, 64, 128, 32);
		simulationsButton.addButtonListener(new SetListListener(simulations, policies, View.SIMULATIONS));
		container.addComponent(simulationsButton);
	}
	
	@Override
	protected void internalLeave(GameContainer gc, StateBasedGame sbg) {}
	
	@Override
	public void internalRender(GameContainer gc, StateBasedGame sbg, Graphics graphics) {
		GameContext.playerCountry.getPoliticalContext();
		graphics.getFont().getLineHeight();
		
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
