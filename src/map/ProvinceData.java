package map;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import docs.SGMLObject;
import economy.Market;
import economy.MarketActor;
import economy.Resource;
import economy.ResourceTable;

public class ProvinceData {
	public static final int CLIMATE_ARCTIC = new Color(107, 100, 255).getRGB();
	public static final int CLIMATE_ARID = new Color(255, 255, 108).getRGB();
	public static final int CLIMATE_TEMPERATE = new Color(180, 108, 108).getRGB();
	public static final int CLIMATE_TROPICAL = new Color(252, 37, 49).getRGB();
	public static final int CLIMATE_WASTELAND = new Color(38, 38, 38).getRGB();
	private static List<MarketActor> switchList = new ArrayList<>();
	
	public static final int TERRAIN_INLAND_SEA = new Color(55, 90, 220).getRGB();
	public static final int TERRAIN_OCEAN = new Color(8, 31, 130).getRGB();
	
	public static final int WINTER_MILD = new Color(24, 183, 5).getRGB();
	public static final int WINTER_NONE = new Color(7, 49, 0).getRGB();
	public static final int WINTER_NORMAL = new Color(61, 255, 39).getRGB();
	public static final int WINTER_SEVERE = new Color(180, 255, 178).getRGB();
	public static final int WINTER_WASTELAND = new Color(38, 38, 38).getRGB();
	
	private boolean coastal;
	private Infrastructure infrastructure;
	private Market market;
	private List<MarketActor> marketActors = new ArrayList<>();
	private String name;
	private final List<Organisation> organisations = new ArrayList<Organisation>();
	private Population pop;
	private Resource resource;
	
	private final Province self;
	
	public ProvinceData(Province self) {
		coastal = false;
		infrastructure = new Infrastructure(self, null);
		name = "";
		pop = null;
		resource = ResourceTable.getResource("Grain");
		this.self = self;
	}
	
	public ProvinceData(Province self, SGMLObject history) {
		reload(history);
		this.self = self;
	}
	
	public Infrastructure getInfrastructure() {
		return infrastructure;
	}
	
	public Market getMarket() {
		return market;
	}
	
	public List<MarketActor> getMarketActors() {
		return marketActors;
	}
	
	public String getName() {
		return name;
	}
	
	public List<Organisation> getOrganisations() {
		return organisations;
	}
	
	public Population getPopulation() {
		return pop;
	}
	
	public long getPopulationSize() {
		if(pop == null)
			return 1000;
		return pop.getPopulationSize();
	}
	
	public Province getProvince() {
		return self;
	}
	
	public Resource getResource() {
		return resource;
	}
	
	public boolean isCoastal() {
		return coastal;
	}
	
	public void reload(SGMLObject history) {
		if(history.hasField("coastal")) {
			coastal = history.getBoolean("coastal");
		} else {
			coastal = false;
		}
		
		infrastructure = new Infrastructure(self, history);
		name = history.getField("name");
		
		if(history.hasChild("organisations")) {
			
			SGMLObject organisations = history.getChild("organisations");
			List<SGMLObject> children;
			
			if(organisations.hasChildren("organisation")) {
				children = organisations.getChildren("organisation");
				for(int i = 0; i < children.size(); i++) {
					this.organisations.add(new Organisation(children.get(i)));
				}
			} else {
				this.organisations.add(new Organisation(organisations.getChild("organisation")));
			}
		}
		
		pop = new Population(history);
		
		if(history.hasField("resource")) {
			resource = ResourceTable.getResource(history.getField("resource"));
		} else {
			resource = ResourceTable.getResource("Grain");
		}
	}
	
	public void setMarket(Market value) {
		market = value;
	}
	
	public void updateMarketActors() {
		if(pop.hasNewMarketActors()) {
			for(SubPopulation subPop: pop.getSubPopulations()) {
				switchList.add(subPop);
			}
			
			for(Organisation org: organisations) {
				switchList.add(org);
			}
			
			List<MarketActor> tempList = marketActors;
			tempList.clear();
			marketActors = switchList;
			switchList = tempList;
		}
	}
}
