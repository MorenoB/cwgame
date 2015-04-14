package map;

import static map.ProvinceData.TERRAIN_INLAND_SEA;
import static map.ProvinceData.TERRAIN_OCEAN;
import game.GameContext;
import gnu.trove.list.TIntList;
import gnu.trove.list.array.TIntArrayList;

import java.io.File;

import org.newdawn.slick.Image;
import org.newdawn.slick.ImageBuffer;

import diplomacy.Countries;
import diplomacy.Country;
import diplomacy.CountryData;
import docs.SGMLObject;
import docs.SGMLReaderUtil;
import economy.Currency;
import economy.Market;
import economy.ProvinceMarket;
import economy.ResourceTable;

/**
 * The base class for a province.
 * 
 * @author nastyasalways
 *
 */
public class Province {
	private static int idGen = 0;
	private static int maxPoints = 0;
	private static final int SEA_B = GameContext.defines.getChild("seaColor").getInt("blue");
	private static final int SEA_G = GameContext.defines.getChild("seaColor").getInt("green");
	private static final int SEA_R = GameContext.defines.getChild("seaColor").getInt("red");
	private final int climateType;
	private final int color;
	private float currentRed, currentGreen, currentBlue;
	private final LandProvinceData data;
	private SGMLObject history;
	private final File historyFile;
	private final int id;
	private Image image;
	public final int initialX;
	public final int initialY;
	private int minX, minY, maxX, maxY;
	
	private final ProvinceMarket provMarket;
	private int terrainType;
	private final int winterType;
	private final TIntList xPoints = new TIntArrayList();
	private final TIntList yPoints = new TIntArrayList();
	
	public Province(int x, int y, int color) {
		this.color = color;
		id = idGen;
		idGen++;
		addPoint(x, y);
		climateType = ImageCache.climateMap.getRGB(x, y);
		terrainType = ImageCache.terrain.getRGB(x, y);
		winterType = ImageCache.winterMap.getRGB(x, y);
		final int idSubFile = id / 100;
		historyFile = getHistoryFile(idSubFile, id);
		initialX = x;
		initialY = y;
		
		if(historyFile.exists()) {
			history = SGMLReaderUtil.readFromPath(historyFile);
			data = new LandProvinceData(this, history);
			provMarket = new ProvinceMarket(this);
		} else {
			if(isWater()) {
				data = null;
				history = null;
				provMarket = null;
			} else {
				data = new LandProvinceData(this);
				history = null;
				provMarket = new ProvinceMarket(this);
			}
		}
	}
	
	public void addPoint(int x, int y) {
		xPoints.add(x);
		yPoints.add(y);
		if(xPoints.size() > maxPoints) {
			maxPoints = xPoints.size();
		}
	}
	
	public void createMarket() {
		LandProvinceData data = getData();
		if(data != null) {
			Country country = data.getOwner();
			if(country != null) {
				CountryData countryData = country.getData();
				Currency currency = countryData.getCurrency();
				Market market = new Market(currency);
				getData().setMarket(market);
			}
		}
	}
	
	public void determineTerrain() {
		final int takeX = minX + ((maxX - minX) / 2);
		final int takeY = minY + ((maxY - minY) / 2);
		for(int i = 0; i < xPoints.size(); i++) {
			final int x = xPoints.get(i);
			final int y = yPoints.get(i);
			if((takeX == x) && (takeY == y)) {
				terrainType = ImageCache.terrain.getRGB(takeX, takeY);
			}
		}
		
	}
	
	public int getClimateType() {
		return climateType;
	}
	
	public int getColor() {
		return color;
	}
	
	public LandProvinceData getData() {
		return data;
	}
	
	public SGMLObject getHistory() {
		return history;
	}
	
	private File getHistoryFile(int idSubFile, int id) {
		File genFolderFile = new File("res/history/provinces/gen/" + idSubFile + "/" + id + ".xml");
		
		File gen2FolderFile = new File("res/history/provinces/gen2/" + idSubFile + "/" + id + ".xml");
		
		if(gen2FolderFile.exists())
			return gen2FolderFile;
		if(genFolderFile.exists())
			return genFolderFile;
		File baseFolderFile = new File("res/history/provinces/" + idSubFile + "/" + id + ".xml");
		
		return baseFolderFile;
	}
	
	public int getId() {
		return id;
	}
	
	public ResourceTable getLocalResources() {
		ResourceTable table = new ResourceTable();
		
		for(SubPopulation subPop: getData().getPopulation().getSubPopulations()) {
			table.add(subPop.getResources());
		}
		
		for(Organisation org: getData().getOrganisations()) {
			table.add(org.getResources());
		}
		
		return table;
	}
	
	public ProvinceMarket getMarket() {
		return provMarket;
	}
	
	public int getMaxX() {
		return maxX;
	}
	
	public int getMaxY() {
		return maxY;
	}
	
	public int getMinX() {
		return minX;
	}
	
	public int getMinY() {
		return minY;
	}
	
	public int getTerrainType() {
		return terrainType;
	}
	
	public int getWinterType() {
		return winterType;
	}
	
	public TIntList getXPoints() {
		return xPoints;
	}
	
	public TIntList getYPoints() {
		return yPoints;
	}
	
	public boolean isWater() {
		return (terrainType == TERRAIN_INLAND_SEA) || (terrainType == TERRAIN_OCEAN);
	}
	
	public void nullifyHistory() {
		history = null;
	}
	
	/**
	 * 
	 * @param xOffset
	 *            This is the x offset of the camera.
	 * @param yOffset
	 *            This is the y offset of the camera.
	 */
	public void render(float xOffset, float yOffset, float zoom) {
		final float x = (minX - xOffset) * zoom;
		final float y = (minY - yOffset) * zoom;
		if((x < 0f) && (y < 0f))
			return;
		// image.setImageColor(r, g, b);
		image.startUse();
		image.drawEmbedded(x, y, image.getWidth() * zoom, image.getHeight() * zoom);
		image.endUse();
	}
	
	public void renderHighlight(float xOffset, float yOffset, float zoom) {
		if(image == null)
			return;
		final float x = (minX - xOffset) * zoom;
		final float y = (minY - yOffset) * zoom;
		if((x < 0f) && (y < 0f))
			return;
		
		image.setImageColor(1f, 1f, 1f, 0.5f);
		image.startUse();
		image.drawEmbedded(x, y, image.getWidth() * zoom, image.getHeight() * zoom);
		image.endUse();
		image.setImageColor(currentRed, currentGreen, currentBlue, 1f);
	}
	
	public void setProvinceColor(float r, float g, float b) {
		if(image != null) {
			image.setImageColor(r, g, b);
			currentRed = r;
			currentGreen = g;
			currentBlue = b;
		}
	}
	
	public void testGenTexture() {
		/*
		 * if ((terrainType == TERRAIN_INLAND_SEA) || (terrainType == TERRAIN_OCEAN) ||
		 * (history != null) || history == null) {
		 */
		
		assert (xPoints.size() == yPoints.size());
		int minX = xPoints.get(0);
		int minY = yPoints.get(0);
		int maxX = xPoints.get(0);
		int maxY = yPoints.get(0);
		
		for(int i = 1; i < xPoints.size(); i++) {
			final int xval = yPoints.get(i);
			if(xval < minY) {
				minY = xval;
			}
			if(xval > maxY) {
				maxY = xval;
			}
			final int yval = xPoints.get(i);
			if(yval < minX) {
				minX = yval;
			}
			if(yval > maxX) {
				maxX = yval;
			}
		}
		
		final ImageBuffer buf = new ImageBuffer((maxX - minX) + 1, (maxY - minY) + 1);
		
		// TODO If the buffer turns out not to be zeroed then manually zero
		// it.
		for(int i = 0; i < xPoints.size(); i++) {
			final int xval = xPoints.get(i) - minX;
			final int yval = yPoints.get(i) - minY;
			buf.setRGBA(xval, yval, 255, 255, 255, 255);
		}
		
		image = buf.getImage();
		this.minX = minX;
		this.minY = minY;
		this.maxX = maxX;
		this.maxY = maxY;
		
		if(history != null) {
			final String owner = history.getField("owner");
			final Country country = Countries.getCountry(owner);
			CountryData data = country.getData();
			setProvinceColor(data.getRed(), data.getGreen(), data.getBlue());
		} else {
			if((terrainType == TERRAIN_INLAND_SEA) || (terrainType == TERRAIN_OCEAN)) {
				// setProvinceColor(55 / 255f, 90 / 255f, 220 / 255f);
				// always use the shallow sea color.
				// setProvinceColor(8 / 255f, 31 / 255f, 130 / 255f);
				setProvinceColor(SEA_R / 255f, SEA_G / 255f, SEA_B / 255f);
			} else {
				setProvinceColor(.3f, .3f, .3f);
			}
		}
		/* } */
	}
	
	public void updateHistory() {
		history = SGMLReaderUtil.readFromPath(getHistoryFile(id / 100, id));
		data.reload(history);
	}
	
	public void updatePopulation(double monthlyPopGrowth) {
		LandProvinceData data = getData();
		assert (data != null);
		Population pop = data.getPopulation();
		assert (pop != null);
		pop.update(this, monthlyPopGrowth);
	}
	
	public void updateResources() {
		for(final Organisation org: data.getOrganisations()) {
			if(org.getType() == OrganisationType.RESOURCE_EXTRACTOR) {
				org.generateResources(data);
			} else {
				// check whether there is demand for this resource
			}
		}
	}
}
