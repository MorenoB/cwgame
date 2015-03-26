package map;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

import java.awt.image.BufferedImage;

import org.lwjgl.opengl.GL11;

public class ProvinceScanner {
	private static final boolean CHECK_SYSTEM = false;
	
	private final boolean dataOnlyMode;
	private final Province[][] provincePixels;
	private final TIntObjectMap<Province> provinces = new TIntObjectHashMap<Province>();
	
	/**
	 * Creates a new province scanner that generates OpenGL-based rendering information
	 * for it's provinces.
	 */
	public ProvinceScanner() {
		this(false);
	}
	
	public ProvinceScanner(boolean dataOnlyMode) {
		provincePixels = new Province[ImageCache.provinces.getWidth()][ImageCache.provinces.getHeight()];
		this.dataOnlyMode = dataOnlyMode;
	}
	
	public void addProvince(int color, int x, int y) {
		provinces.put(color, new Province(color, x, y));
	}
	
	public Province getProvinceAt(int x, int y) {
		final Province returnValue = provincePixels[x][y];
		if(returnValue == null) {
			final int provColor = ImageCache.provinces.getRGB(x, y);
			if(provinces.containsKey(provColor)) {
				final Province prov = provinces.get(provColor);
				prov.addPoint(x, y);
				return prov;
			} else {
				final Province prov = new Province(x, y, provColor);
				provinces.put(provColor, prov);
				return prov;
			}
		}
		return provincePixels[x][y];
	}
	
	public Province[] getProvinces() {
		Province[] array = new Province[provinces.values().length];
		array = provinces.values(array);
		return array;
	}
	
	public void scan() {
		final BufferedImage provinces = ImageCache.provinces;
		for(int x = 0; x < provinces.getWidth(); x++) {
			for(int y = 0; y < provinces.getHeight(); y++) {
				final int color = provinces.getRGB(x, y);
				Province prov;
				if(this.provinces.containsKey(color)) {
					prov = this.provinces.get(color);
					prov.addPoint(x, y);
					provincePixels[x][y] = prov;
				} else {
					prov = new Province(x, y, color);
					this.provinces.put(color, prov);
				}
			}
		}
		
		for(final Object obj: this.provinces.values()) {
			final Province prov = (Province) obj;
			if(!dataOnlyMode) {
				prov.testGenTexture();
			}
			prov.determineTerrain();
			prov.nullifyHistory();
			prov.createMarket();
		}
		
		if(CHECK_SYSTEM) {
			System.out.println(System.getProperty("os.name"));
			System.out.println(GL11.glGetString(GL11.GL_RENDERER));
			System.out.println(GL11.glGetString(GL11.GL_VERSION));
			System.out.println(GL11.glGetString(GL11.GL_VENDOR));
		}
		
	}
}
