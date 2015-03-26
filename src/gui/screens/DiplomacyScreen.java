package gui.screens;

import game.GameContext;
import gui.AbstractComponent;
import gui.Button;
import gui.ButtonBuilder;
import gui.ButtonList;
import gui.ColorRectangle;
import gui.Component;
import gui.MutableImageIcon;
import gui.MutableLabel;
import gui.MutableLabelBuilder;

import java.util.ArrayList;
import java.util.List;

import logger.Logger;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import diplomacy.BilateralRelation;
import diplomacy.Countries;
import diplomacy.Country;
import diplomacy.DiplomaticRelations;
import diplomacy.UnilateralRelation;

public class DiplomacyScreen extends AbstractScreen {
	private static final String LABEL_TEXT_PEACE = "We are at peace.";
	private static final String LABEL_TEXT_WAR = "We are at war.";
	protected static final int TEXTX = 16;
	protected static final int TEXTY = 16;
	
	private Button buttonAskMilitaryAccess;
	private Button buttonDeclareWar;
	private ButtonList buttonListCountries;
	private Button buttonLowerRelations;
	private Button buttonOfferMilitaryAccess;
	private Button buttonProposePeace;
	private Button buttonRaiseRelations;
	private ColorRectangle colorRectangleBackground;
	private ColorRectangle colorRectFlagOutline;
	private Country currentCountry;
	private MutableImageIcon imageCountryFlag;
	private MutableLabel labelCountryName;
	private MutableLabel labelOurMilitaryAccess;
	private MutableLabel labelOurOpinionOfThem;
	private MutableLabel labelPeaceOrWar;
	private MutableLabel labelTheirMilitaryAccess;
	private MutableLabel labelTheirOpinionOfUs;
	private Country playerCountry;
	private final List<Component> playerInvisible = new ArrayList<>();
	
	private void initButtons(GameContainer gc) {
		final int buttonX = gc.getWidth() - 192 - TEXTX - 256;
		final int width = 256;
		final int height = 32;
		final float startY = 64 + TEXTY + 4;
		ButtonBuilder bb = new ButtonBuilder();
		bb.width(width).height(height).x(buttonX);
		
		bb.text("Declare war").y(startY).listener((button) -> {
			final DiplomaticRelations relations = currentCountry.getRelations();
			relations.getBilateral(playerCountry).declareWar(playerCountry);
		});
		buttonDeclareWar = bb.instantiate();
		
		bb.text("Propose peace").listener((button) -> {
			DiplomaticRelations relations = currentCountry.getRelations();
			relations.getBilateral(playerCountry).proposePeace(playerCountry);
		});
		buttonProposePeace = bb.instantiate();
		buttonProposePeace.setVisibleAndEnabled(false);
		
		bb.text("Raise Relations").y(startY + height).listener((button) -> {
			final DiplomaticRelations relations = currentCountry.getRelations();
			relations.getUnilateral(playerCountry).raiseRelations();
		});
		buttonRaiseRelations = bb.instantiate();
		
		bb.text("Lower Relations").y(startY + (height * 2)).listener((button) -> {
			final DiplomaticRelations relations = currentCountry.getRelations();
			relations.getUnilateral(playerCountry).lowerRelations();
		});
		buttonLowerRelations = bb.instantiate();
		
		bb.text("Ask for Military Access").y(startY + (height * 3)).listener((button) -> {
			DiplomaticRelations relations = currentCountry.getRelations();
			relations.getUnilateral(playerCountry).askForMilitaryAccess();
		});
		buttonAskMilitaryAccess = bb.instantiate();
		
		bb.text("Offer Military Access").y(startY + (height * 4)).listener((button) -> {
			DiplomaticRelations relations = currentCountry.getRelations();
			relations.getUnilateral(playerCountry).offerMilitaryAccess(playerCountry);
		});
		buttonOfferMilitaryAccess = bb.instantiate();
		
		container.addComponent(buttonDeclareWar);
		container.addComponent(buttonProposePeace);
		container.addComponent(buttonRaiseRelations);
		container.addComponent(buttonLowerRelations);
		container.addComponent(buttonAskMilitaryAccess);
		container.addComponent(buttonOfferMilitaryAccess);
		
		playerInvisible.add(buttonDeclareWar);
		playerInvisible.add(buttonProposePeace);
		playerInvisible.add(buttonRaiseRelations);
		playerInvisible.add(buttonLowerRelations);
		playerInvisible.add(buttonAskMilitaryAccess);
		playerInvisible.add(buttonOfferMilitaryAccess);
	}
	
	private void initLabels(int lh) {
		final float startY = 64 + (lh * 2) + 116;
		MutableLabelBuilder mbl = new MutableLabelBuilder();
		mbl.text("").x(192 + TEXTX).width(0f).height(0f);
		
		labelCountryName = mbl.y(startY).instantiate();
		labelPeaceOrWar = mbl.y(startY + (lh * 2)).instantiate();
		labelTheirOpinionOfUs = mbl.y(startY + (lh * 3)).instantiate();
		labelOurOpinionOfThem = mbl.y(startY + (lh * 4)).instantiate();
		labelTheirMilitaryAccess = mbl.y(startY + (lh * 5)).instantiate();
		labelOurMilitaryAccess = mbl.y(startY + (lh * 6)).instantiate();
		
		container.addComponent(labelCountryName);
		container.addComponent(labelPeaceOrWar);
		container.addComponent(labelTheirOpinionOfUs);
		container.addComponent(labelOurOpinionOfThem);
		container.addComponent(labelTheirMilitaryAccess);
		container.addComponent(labelOurMilitaryAccess);
		
		playerInvisible.add(labelPeaceOrWar);
		playerInvisible.add(labelTheirOpinionOfUs);
		playerInvisible.add(labelOurOpinionOfThem);
		playerInvisible.add(labelTheirMilitaryAccess);
		playerInvisible.add(labelOurMilitaryAccess);
	}
	
	@Override
	public void internalEnter(GameContainer gc, StateBasedGame sbg) {
		if(currentCountry == null) {
			currentCountry = GameContext.playerCountry;
		}
		
		playerCountry = GameContext.playerCountry;
	}
	
	@Override
	public void internalInit(GameContainer gc, StateBasedGame sbg) {
		final Country[] countries = Countries.getCountries();
		final String[] countryNames = new String[countries.length];
		for(int i = 0; i < countries.length; i++) {
			countryNames[i] = countries[i].getData().getName();
		}
		
		final int lh = gc.getGraphics().getFont().getLineHeight();
		
		int buttonListHeight = ((gc.getHeight() - 128) / lh) - 2;
		Logger.get().debug("Using " + buttonListHeight + " country name buttons in the Diplomacy screen.");
		buttonListCountries = new ButtonList(0, 64, 192, buttonListHeight, countryNames);
		buttonListCountries.addListener((text) -> {
			currentCountry = Countries.getCountry(text);
		});
		container.addComponent(buttonListCountries);
		
		colorRectangleBackground = new ColorRectangle(192, 64, gc.getWidth() - 256 - 128,
				gc.getHeight() - 128);
		container.addComponent(colorRectangleBackground);
		
		colorRectFlagOutline = new ColorRectangle(192 + TEXTX, 64 + lh, 216, 116);
		container.addComponent(colorRectFlagOutline);
		
		imageCountryFlag = new MutableImageIcon(null, colorRectFlagOutline.getX() + 8,
				colorRectFlagOutline.getY() + 8, 200, 100);
		container.addComponent(imageCountryFlag);
		
		initButtons(gc);
		initLabels(lh);
	}
	
	@Override
	public void internalLeave(GameContainer gc, StateBasedGame sbg) {
		
	}
	
	@Override
	public void internalRender(GameContainer gc, StateBasedGame sbg, Graphics graphics) {
		colorRectFlagOutline.setColor(AbstractComponent.getRenderColor().darker());
		
		final DiplomaticRelations countryRelations = currentCountry.getRelations();
		final BilateralRelation biRelations = countryRelations.getBilateral(playerCountry);
		final UnilateralRelation theirRelationToUs = countryRelations.getUnilateral(playerCountry);
		final DiplomaticRelations playerRelations = playerCountry.getRelations();
		final UnilateralRelation ourRelationToThem = playerRelations.getUnilateral(currentCountry);
		
		if(currentCountry != playerCountry) {
			final int theirOpinion = theirRelationToUs.getOpinion();
			labelTheirOpinionOfUs.setText("Their opinion of us: " + theirOpinion);
			final int ourOpinion = ourRelationToThem.getOpinion();
			labelOurOpinionOfThem.setText("Our opinion of them: " + ourOpinion);
			boolean ourMilitaryAccess = theirRelationToUs.hasMilitaryAccess();
			if(ourMilitaryAccess) {
				labelOurMilitaryAccess.setText("We have military access.");
			} else {
				labelOurMilitaryAccess.setText("We don't have military access.");
			}
			
			boolean theirMilitaryAccess = ourRelationToThem.hasMilitaryAccess();
			if(theirMilitaryAccess) {
				labelTheirMilitaryAccess.setText("They have military access.");
			} else {
				labelTheirMilitaryAccess.setText("They don't have military access.");
			}
			
			if(biRelations.isAtWar()) {
				labelPeaceOrWar.setText(LABEL_TEXT_WAR);
				buttonDeclareWar.setVisibleAndEnabled(false);
				buttonProposePeace.setVisibleAndEnabled(true);
			} else {
				labelPeaceOrWar.setText(LABEL_TEXT_PEACE);
				buttonDeclareWar.setVisibleAndEnabled(true);
				buttonProposePeace.setVisibleAndEnabled(false);
			}
		}
	}
	
	@Override
	public void internalUpdate(GameContainer gc, StateBasedGame sbg, int delta) {
		boolean currentIsNotPlayer = playerCountry != currentCountry;
		
		for(Component c: playerInvisible) {
			c.setEnabled(currentIsNotPlayer);
			c.setVisible(currentIsNotPlayer);
		}
		
		labelCountryName.setText(currentCountry.getData().getOfficialName());
		imageCountryFlag.setImage(currentCountry.getData().getFlag());
	}
}
