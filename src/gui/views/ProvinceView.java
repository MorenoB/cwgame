package gui.views;

import game.GameContext;
import gui.Button;
import gui.ButtonBuilder;
import gui.ButtonList;
import gui.Component;
import gui.Container;

import java.util.List;

import map.Infrastructure;
import map.Organisation;
import map.Province;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;

import diplomacy.Countries;
import diplomacy.Country;
import docs.SGMLObject;

// TODO Keep a configuration and store the data required to show the province view. That
// way it doesn't have to be requested at every render but can instead be stored when a
// new province is assigned.
public class ProvinceView extends AbstractView implements Component {
	private final Container container = new Container();
	private ProvinceDataView currentView = ProvinceDataView.DEMOGRAPHICS;
	private final ButtonList listDemographics;
	private final Button viewDemographics;
	private final Button viewInfrastructure;
	private final Button viewOrganisations;
	private final Button viewResources;
	
	public ProvinceView(float x, float y) {
		super(x, y);
		
		ButtonBuilder bb = new ButtonBuilder();
		bb.y((y + HEIGHT) - TEXTY - 32).width(128).height(32);
		viewDemographics = bb.text("Demographics").x(x + TEXTX).instantiate();
		viewDemographics.addButtonListener((button) -> {
			currentView = ProvinceDataView.DEMOGRAPHICS;
		});
		
		viewInfrastructure = bb.text("Infrastructure").x((x + (WIDTH / 2)) - 64).instantiate();
		viewInfrastructure.addButtonListener((button) -> {
			currentView = ProvinceDataView.INFRASTRUCTURE;
		});
		
		viewOrganisations = bb.text("Organisations").x((x + WIDTH) - TEXTX - 128).instantiate();
		viewOrganisations.addButtonListener((button) -> {
			currentView = ProvinceDataView.ORGANISATIONS;
		});
		
		bb.y((y + height) - TEXTY - 64);
		viewResources = bb.text("Resources").x(x + TEXTX).instantiate();
		
		listDemographics = new ButtonList(x + (TEXTX * 2), y + (TEXTY * 5), 128, 3, null);
		
		container.addComponent(viewDemographics);
		container.addComponent(viewInfrastructure);
		container.addComponent(viewOrganisations);
		container.addComponent(viewResources);
	}
	
	@Override
	public int compareTo(Component o) {
		return 0;
	}
	
	public boolean contains(float x, float y) {
		return ((x >= getX()) && (x <= (getX() + WIDTH)) && (y >= getY()) && (y < (getY() + HEIGHT)));
	}
	
	@Override
	public void internalRender(GameContainer container, StateBasedGame game, Graphics graphics) {
		
		if(!isVisible())
			return;
		
		Province prov = getCurrentProvince();
		float x = getX();
		float y = getY();
		
		if(prov == null)
			return;
		
		prov.getData().getOwner();
		if(renderColor != null) {
			graphics.setColor(renderColor);
			graphics.fillRect(x, getY(), WIDTH, HEIGHT);
		}
		Button.setRenderColor(renderColor);
		graphics.setColor(Color.white);
		graphics.drawString(prov.getData().getName() + " (" + prov.getId() + ")", x + TEXTX, y + TEXTY);
		graphics.drawString("Population: " + prov.getData().getPopulationSize(), x + (TEXTX * 2), y
				+ (TEXTY * 2));
		
		String resourceName = prov.getData().getResource().getName();
		graphics.drawString("Resource: " + resourceName, x + (TEXTX * 2), y + (TEXTY * 3));
		graphics.drawString("Terrain: " + prov.getTerrainType(), x + (TEXTX * 2), y + (TEXTY * 4));
		
		if(prov.getData().getPopulation() != null) {
			int counter = 5;
			
			switch(currentView) {
				case DEMOGRAPHICS:
					listDemographics.render(container, game, graphics);
					break;
				
				case INFRASTRUCTURE:
					Infrastructure infra = prov.getData().getInfrastructure();
					graphics.drawString("Railroad: " + infra.getRailroadLevel(), TEXTX * 2, y
							+ (TEXTY * counter));
					counter += 1;
					graphics.drawString("Roadways: " + infra.getRoadLevel(), TEXTX * 2, y + (TEXTY * counter));
					counter += 1;
					graphics.drawString("Sea Port: " + infra.getPortLevel(), TEXTX * 2, y + (TEXTY * counter));
					break;
				
				case ORGANISATIONS:
					List<Organisation> organisations = prov.getData().getOrganisations();
					if(organisations != null) {
						for(int i = 0; i < organisations.size(); i++) {
							Organisation item = organisations.get(i);
							graphics.drawString(item.getName(), TEXTX * 2, y + (TEXTY * counter));
							counter += 1;
						}
					}
					break;
			}
		}
		
		this.container.render(container, game, graphics);
	}
	
	@Override
	public void internalUpdate(GameContainer container, StateBasedGame game, int delta) {
		
		if(getCurrentProvince() != null) {
			SGMLObject history = getCurrentProvince().getHistory();
			Country owner = (history == null) ? null : Countries.getCountry(history.getField("owner"));
			renderColor = (owner == null) ? GameContext.playerCountry.getData().getColor() : owner.getData()
					.getColor();
			
			Input input = container.getInput();
			if(input.isKeyDown(Input.KEY_F5)) {
				getCurrentProvince().updateHistory();
			}
		}
		
		if(currentView == ProvinceDataView.DEMOGRAPHICS) {
			Province prov = getCurrentProvince();
			if((prov != null) && (prov.getData().getPopulation() != null)) {
				listDemographics.setData(getCurrentProvince().getData().getPopulation()
						.getSubPopulationString());
				listDemographics.update(container, game, delta);
			}
		}
		this.container.update(container, game, delta);
	}
	
	public static enum ProvinceDataView {
		DEMOGRAPHICS,
		INFRASTRUCTURE,
		ORGANISATIONS
	}
}
