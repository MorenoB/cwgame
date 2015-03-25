package map;

import java.util.ArrayList;
import java.util.List;

import diplomacy.Countries;
import diplomacy.Country;
import docs.SGMLObject;
import economy.Factory;
import economy.Market;
import economy.MarketActor;
import economy.Resource;
import economy.ResourceTable;

/**
 * A <code>ProvinceData</code> class, extended to allow for all the features of a land
 * province.
 * 
 * @author nastyasalways
 *
 */
public class LandProvinceData extends ProvinceData {
	private static List<MarketActor> switchList = new ArrayList<>();
	
	private boolean coastal;
	private final List<Factory> factories = new ArrayList<>();
	private Infrastructure infrastructure;
	private Market market;
	private List<MarketActor> marketActors = new ArrayList<>();
	private String name;
	private final List<Organisation> organisations = new ArrayList<>();
	private Country owner;
	private Population pop;
	private Resource resource;
	private final Province self;
	
	public LandProvinceData(Province self) {
		coastal = false;
		infrastructure = new Infrastructure(self, null);
		name = "";
		pop = null;
		resource = ResourceTable.getResource("Grain");
		this.self = self;
	}
	
	public LandProvinceData(Province self, SGMLObject history) {
		
		this.self = self;
		reload(history);
	}
	
	public List<Factory> getFactories() {
		return factories;
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
	
	public Country getOwner() {
		return owner;
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
		
		owner = Countries.getCountry(history.getField("owner"));
		owner.getData().getProvinces().add(self);
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
