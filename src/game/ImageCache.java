package game;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class ImageCache {
	public static Image terrainImage, provincesImage;
	
	static {
		try {
			terrainImage = new Image("res/map/terrain.bmp");
			provincesImage = new Image("res/map/provinces.bmp");
		} catch(SlickException exception) {
			exception.printStackTrace();
		}
	}
}
