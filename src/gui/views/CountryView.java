package gui.views;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import diplomacy.Country;
import diplomacy.CountryData;
import gui.Component;

public class CountryView extends AbstractView implements Component {
	public static final int HEIGHT = 256;
	private static final int TEXTX = 16;
	
	private static final int TEXTY = 16;
	public static final int WIDTH = 512;
	
	private Country currentCountry;
	
	public CountryView(float x, float y) {
		super(x, y);
	}
	
	@Override
	public int compareTo(Component o) {
		return 0;
	}
	
	public Country getCurrentCountry() {
		return currentCountry;
	}
	
	@Override
	public void internalRender(GameContainer container, StateBasedGame game, Graphics graphics) {
		if(visible && (currentCountry != null)) {
			CountryData data = currentCountry.getData();
			graphics.setColor(data.getColor());
			graphics.fillRect(x, y, WIDTH, HEIGHT);
			graphics.setColor(Color.white);
			graphics.drawString(data.getName(), TEXTX, y + TEXTY);
			graphics.drawString(data.getOfficialName(), TEXTX * 2, y + (TEXTY * 2));
			graphics.drawString("(" + data.getAcronym() + ")", TEXTX * 2, y + (TEXTY * 3));
			graphics.drawString(data.getGovernment(), TEXTX, y + (TEXTY * 5));
			graphics.drawString(data.getEconomy(), TEXTX, y + (TEXTY * 6));
			graphics.drawString("Population: " + currentCountry.getPopulation(), TEXTX, y + (TEXTY * 8));
		}
	}
	
	@Override
	public void internalUpdate(GameContainer container, StateBasedGame game, int delta) {}
	
	public void setCurrentCountry(Country value) {
		currentCountry = value;
	}
}
