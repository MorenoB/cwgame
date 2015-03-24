package gui.screens;

import game.Camera;
import game.GameContext;
import game.ImageCache;
import game.MapViewState;
import game.PlayGameState;
import game.ProvinceRenderer;
import gui.AbstractComponent;
import gui.Button;
import gui.Container;
import gui.views.AbstractView;
import gui.views.DiplomacyView;
import gui.views.ProvinceView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import map.Province;

import org.apache.commons.lang3.text.WordUtils;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;

import diplomacy.Countries;
import diplomacy.Country;

public class MapScreen implements Screen {
	private final Camera camera = new Camera(ImageCache.terrainImage.getWidth(),
			ImageCache.terrainImage.getHeight());
	private float clockCounter;
	private final Container container = new Container();
	private Province currentHoveredProvince;
	private final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
	private DiplomacyView diplomacyView;
	private boolean hasEnteredOnce = false;
	private final int lastMonth = 1;
	private ProvinceView provinceView;
	private final Map<CurrentScreen, Button> screenButtons = new HashMap<>();
	private final float zoom = 1f;
	
	@Override
	public void enter(GameContainer gc, StateBasedGame sbg) {
		if(!hasEnteredOnce) {
			camera.x = MapViewState.get().camera.x;
			camera.y = MapViewState.get().camera.y;
			hasEnteredOnce = true;
		}
		
		AbstractComponent.setRenderColor(GameContext.playerCountry.getData().getColor());
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) {
		int iter = 0;
		for(CurrentScreen screen: CurrentScreen.values()) {
			String name = WordUtils.capitalize(screen.name().toLowerCase());
			Button button = new Button(name, 128f * iter, 0f, 128f, 32f);
			button.addButtonListener((b) -> {
				PlayGameState.get().setCurrentScreen(gc, sbg, screen);
			});
			container.addComponent(button);
			iter++;
		}
		
		float y = gc.getHeight() - AbstractView.HEIGHT;
		provinceView = new ProvinceView(0, y);
		diplomacyView = new DiplomacyView(gc.getWidth() - AbstractView.WIDTH, y);
		container.addComponents(provinceView, diplomacyView);
		Input input = gc.getInput();
		input.addKeyListener(new PauseKeyListener());
	}
	
	@Override
	public void leave(GameContainer gc, StateBasedGame sbg) {}
	
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics graphics) {
		Rectangle rect = new Rectangle(camera.x, camera.y, camera.x + gc.getWidth(), camera.y
				+ gc.getHeight());
		ProvinceRenderer.render(gc, sbg, graphics, rect, zoom);
		if(currentHoveredProvince != null) {
			currentHoveredProvince.renderHighlight(camera.x, camera.y, zoom);
		}
		
		graphics.setColor(AbstractComponent.renderColor);
		graphics.fillRect(0, 0, gc.getWidth(), 32);
		graphics.setColor(Button.coverColor);
		graphics.fillRect(0, 0, gc.getWidth(), 32);
		container.render(gc, sbg, graphics);
		graphics.setColor(Color.white);
		graphics.drawString(dateFormat.format(GameContext.calendar.getTime()), 12, 40);
		if(GameContext.paused) {
			graphics.drawString("Paused", 12, 52);
		}
	}
	
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) {
		camera.update(gc, delta);
		container.update(gc, sbg, delta);
		Input input = gc.getInput();
		float mouseX = input.getMouseX();
		float mouseY = input.getMouseY();
		
		if(!provinceView.contains(mouseX, mouseY) && !container.contains(mouseX, mouseY)) {
			int provinceX = (int) (camera.x + mouseX);
			int provinceY = (int) (camera.y + mouseY);
			if((provinceX >= 0) && (provinceX < ImageCache.provincesImage.getWidth()) && (provinceY >= 0)
					&& (provinceY < ImageCache.provincesImage.getHeight())) {
				Province prov = GameContext.provinceScanner.getProvinceAt(provinceX, provinceY);
				currentHoveredProvince = prov;
				
				if(input.isMouseButtonDown(0)) {
					provinceView.setCurrentProvince(prov);
					diplomacyView.setCurrentProvince(prov);
					if(prov.isWater()) {
						provinceView.setVisible(false);
						diplomacyView.setVisible(false);
					} else {
						provinceView.setVisible(true);
						diplomacyView.setVisible(true);
					}
				}
			}
		}
		
		updateClock(delta);
	}
	
	public void updateClock(int delta) {
		int lastMonth = GameContext.calendar.get(Calendar.MONTH);
		if(!GameContext.paused) {
			clockCounter += delta;
			if(clockCounter > GameContext.thresholdOfClock) {
				GameContext.calendar.add(Calendar.DAY_OF_MONTH, 1);
				clockCounter -= GameContext.thresholdOfClock;
				if(lastMonth != GameContext.calendar.get(Calendar.MONTH)) {
					lastMonth = GameContext.calendar.get(Calendar.MONTH);
					
					for(Country country: Countries.getCountries()) {
						country.updateMonthly();
					}
				}
			}
		}
	}
	
	public class PauseKeyListener implements KeyListener {
		
		@Override
		public void inputEnded() {}
		
		@Override
		public void inputStarted() {}
		
		@Override
		public boolean isAcceptingInput() {
			return true;
		}
		
		@Override
		public void keyPressed(int arg0, char arg1) {}
		
		@Override
		public void keyReleased(int keycode, char arg1) {
			if(keycode == Input.KEY_SPACE) {
				GameContext.paused = !GameContext.paused;
			}
		}
		
		@Override
		public void setInput(Input arg0) {}
		
	}
}
