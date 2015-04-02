package map;

import game.GameContext;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;

public class ProvinceRenderer {
	public static void render(GameContainer gc, StateBasedGame sbg, Graphics graphics, Rectangle rectangle,
			float zoom) {
		Province[] provinces = GameContext.provinceScanner.getProvinces();
		float x = rectangle.getX();
		float y = rectangle.getY();
		
		for(Province prov: provinces) {
			if(rectangle.contains(prov.getMinX(), prov.getMinY())
					|| rectangle.contains(prov.getMaxX(), prov.getMaxY())) {
				prov.render(x, y, zoom);
			}
		}
	}
	
}
