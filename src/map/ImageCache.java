package map;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageCache {
	public static BufferedImage climateMap;
	public static BufferedImage heightMap;
	public static BufferedImage provinces;
	public static BufferedImage rivers;
	public static BufferedImage terrain;
	public static BufferedImage winterMap;
	
	static {
		final String path = "res/map/";
		climateMap = getResource(path + "Climate_map.png");
		heightMap = getResource(path + "heightmap.bmp");
		provinces = getResource(path + "provinces.bmp");
		rivers = getResource(path + "rivers.bmp");
		terrain = getResource(path + "terrain.bmp");
		winterMap = getResource(path + "Winter_map.png");
	}
	
	public static BufferedImage getResource(String path) {
		try {
			return ImageIO.read(new File(path));
		} catch (IOException exception) {
			exception.printStackTrace();
			return null;
		}
	}
}
