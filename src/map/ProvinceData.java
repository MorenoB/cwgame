package map;

import java.awt.Color;

public abstract class ProvinceData {
	
	public static final int CLIMATE_ARCTIC = new Color(107, 100, 255).getRGB();
	public static final int CLIMATE_ARID = new Color(255, 255, 108).getRGB();
	public static final int CLIMATE_TEMPERATE = new Color(180, 108, 108).getRGB();
	public static final int CLIMATE_TROPICAL = new Color(252, 37, 49).getRGB();
	public static final int CLIMATE_WASTELAND = new Color(38, 38, 38).getRGB();
	
	public static final int TERRAIN_INLAND_SEA = new Color(55, 90, 220).getRGB();
	public static final int TERRAIN_OCEAN = new Color(8, 31, 130).getRGB();
	
	public static final int WINTER_MILD = new Color(24, 183, 5).getRGB();
	public static final int WINTER_NONE = new Color(7, 49, 0).getRGB();
	public static final int WINTER_NORMAL = new Color(61, 255, 39).getRGB();
	public static final int WINTER_SEVERE = new Color(180, 255, 178).getRGB();
	public static final int WINTER_WASTELAND = new Color(38, 38, 38).getRGB();
}
