package map;

import java.awt.Color;

public enum ClimateType {
	POLAR (new Color(107, 100, 255)),
	TEMPERATE (new Color(108, 108, 108)),
	DESERT (new Color(255, 255, 108)),
	WASTELAND (new Color(38, 38, 38));
	
	private final int color;
	private ClimateType(Color color) {
		this.color = color.getRGB();
	}
	
	public int getColor() {
		return color;
	}
	
	public static ClimateType getClimate(int color) {
		if(color == POLAR.getColor()) return POLAR;
		else if(color == TEMPERATE.getColor()) return TEMPERATE;
		else if(color == DESERT.getColor()) return DESERT;
		else if(color == WASTELAND.getColor()) return WASTELAND;
		else throw new RuntimeException("Illegal climate color.");
	}
}
