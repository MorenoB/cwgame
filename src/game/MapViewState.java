package game;

import gui.AbstractComponent;
import gui.Button;
import gui.ButtonBuilder;
import gui.Container;
import gui.views.AbstractView;
import gui.views.CountryView;
import map.LandProvinceData;
import map.Province;
import map.ProvinceRenderer;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import diplomacy.Countries;
import diplomacy.Country;

public class MapViewState extends BasicGameState {
	private static final MapViewState INSTANCE = new MapViewState();
	public final Camera camera = new Camera(ImageCache.terrainImage.getWidth(),
			ImageCache.terrainImage.getHeight());
	private final Container container = new Container();
	private CountryView countryView;
	private Image currentImage = ImageCache.terrainImage;
	private Province mousedOverProvince;
	private float mouseX, mouseY;
	private boolean politicalMode = true;
	private Country selectedCountry;
	
	@Override
	public int getID() {
		return 2;
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame arg1) throws SlickException {
		selectedCountry = Countries.getCountry(GameContext.defines.getField("initialCountry"));
		
		float buttonY = gc.getHeight() - CountryView.HEIGHT;
		ButtonBuilder bb = new ButtonBuilder();
		bb.x(512).y(buttonY).width(128).height(32);
		
		Button terrainButton = bb.text("Terrain").instantiate();
		terrainButton.addButtonListener((button) -> {
			politicalMode = false;
			currentImage = ImageCache.terrainImage;
			countryView.setVisible(false);
		});
		container.addComponent(terrainButton);
		
		Button provincesButton = bb.text("Provinces").y(buttonY + 32).instantiate();
		provincesButton.addButtonListener((button) -> {
			politicalMode = false;
			currentImage = ImageCache.provincesImage;
			countryView.setVisible(false);
		});
		container.addComponent(provincesButton);
		
		Button politicalButton = bb.text("Political").y(buttonY + 64).instantiate();
		politicalButton.addButtonListener((button) -> {
			politicalMode = true;
			countryView.setVisible(true);
		});
		container.addComponent(politicalButton);
		
		Button playButton = bb.text("Play Game").y(buttonY + 96).instantiate();
		playButton.addButtonListener((button) -> {
			GameContext.playerCountry = selectedCountry;
			GameContext.playerCountry.setPlayerControlled(true);
			ColdWarGame.get().enterState(PlayGameState.get().getID());
		});
		container.addComponent(playButton);
		
		countryView = new CountryView(0, gc.getHeight() - AbstractView.HEIGHT);
		container.addComponent(countryView);
		countryView.setVisible(true);
	}
	
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics graphics) throws SlickException {
		AbstractComponent.setRenderColor(selectedCountry.getData().getColor());
		graphics.setColor(Color.black);
		graphics.fillRect(0, 0, gc.getWidth(), gc.getHeight());
		graphics.setColor(Color.white);
		
		if(politicalMode) {
			Rectangle rect = new Rectangle(camera.x, camera.y, camera.x + gc.getWidth(), camera.y
					+ gc.getHeight());
			ProvinceRenderer.render(gc, sbg, graphics, rect, 1f);
		} else {
			currentImage.startUse();
			currentImage.drawEmbedded(0, 0, gc.getWidth(), gc.getHeight(), camera.x, camera.y,
					camera.x + gc.getWidth(), camera.y + gc.getHeight());
			currentImage.endUse();
		}
		
		container.render(gc, sbg, graphics);
		ScreenshotUtility.update(gc, sbg, graphics);
	}
	
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		EscapeExitUtility.update(gc);
		container.update(gc, sbg, delta);
		
		Input input = gc.getInput();
		float mouseX = input.getMouseX();
		float mouseY = input.getMouseY();
		
		camera.update(gc, delta);
		
		int provinceX = (int) (camera.x + mouseX);
		int provinceY = (int) (camera.y + mouseY);
		if((provinceX >= 0) && (provinceX < ImageCache.provincesImage.getWidth()) && (provinceY >= 0)
				&& (provinceY < ImageCache.provincesImage.getHeight())) {
			Province prov = GameContext.provinceScanner.getProvinceAt(provinceX, provinceY);
			if(prov != null) {
				mousedOverProvince = prov;
			}
		}
		
		if(politicalMode) {
			if(input.isMouseButtonDown(0)) {
				LandProvinceData data = mousedOverProvince.getData();
				if((data != null) && (data.getOwner() != null)) {
					selectedCountry = mousedOverProvince.getData().getOwner();
					countryView.setCurrentCountry(selectedCountry);
				}
			}
		}
	}
	
	public static MapViewState get() {
		return INSTANCE;
	}
}
