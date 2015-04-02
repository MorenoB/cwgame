package gui.views;

import game.GameContext;
import gui.Component;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import map.Province;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import diplomacy.Country;
import economy.Resource;
import economy.ResourceTable;

public class DiplomacyView extends AbstractView implements Component {
	private Color color;
	private Country country;
	private final NumberFormat format = new DecimalFormat("#0");

	public DiplomacyView(float x, float y) {
		super(x, y);
		setVisible(false);
	}

	@Override
	public int compareTo(Component o) {
		return 0;
	}

	@Override
	public void internalRender(GameContainer container, StateBasedGame game,
			Graphics graphics) {
		if (!isVisible())
			return;

		graphics.setColor(color);
		graphics.fillRect(getX(), getY(), WIDTH, HEIGHT);
		graphics.setColor(Color.white);
		if (country != null) {

			graphics.drawString(country.getData().getName(), getX() + TEXTX,
					getY() + TEXTY);
			graphics.drawString("Population: " + country.getPopulation(),
					getX() + (TEXTX * 2), getY() + (TEXTY * 2));

			if (country == GameContext.playerCountry) {

				ResourceTable resources = country.getData().getResources();
				double[] resourceAmounts = resources.getResourceAmounts();
				int ymod = 4;
				for (int i = 0; i < resourceAmounts.length; i++) {
					Resource actualResource = ResourceTable.getResource(i);
					String shortenedValue = format.format(resourceAmounts[i]);
					graphics.drawString(actualResource.getName() + ": "
							+ shortenedValue, getX() + (TEXTX * 2), getY()
							+ (TEXTY * ymod));
					ymod++;
				}
			} else {

			}
		}
	}

	@Override
	public void internalUpdate(GameContainer container, StateBasedGame game,
			int delta) {
		Province prov = getCurrentProvince();
		if (prov == null)
			return;

		if (!prov.isWater()) {
			Country owner = prov.getData().getOwner();
			if (owner != null) {
				color = owner.getData().getColor();
			} else {
				color = GameContext.playerCountry.getData().getColor();
			}
		}
	}

	@Override
	public void setCurrentProvince(Province value) {
		super.setCurrentProvince(value);
		if (value.getData() != null && value.getData().getOwner() != null) {
			country = value.getData().getOwner();
		}
	}
}
