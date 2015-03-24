package diplomacy;

import logger.Logger;
import map.Province;
import politics.LocalPoliticalContext;
import docs.SGMLObject;
import economy.MarketActor;

public class Country extends MarketActor implements Comparable<Country> {
	private final CountryData data;
	private boolean playerControlled = false;
	private final LocalPoliticalContext politicalContext;
	private DiplomaticRelations relations;
	
	public Country(SGMLObject obj) {
		obj = obj.getChild("country");
		Logger.get().debug("Loading country: " + obj.getField("name"));
		data = new CountryData(obj);
		politicalContext = new LocalPoliticalContext(this);
		getPopulation(); // init the stored population value.
	}
	
	@Override
	public int compareTo(Country other) {
		return getData().getName().compareTo(other.getData().getName());
	}
	
	public CountryData getData() {
		return data;
	}
	
	public LocalPoliticalContext getPoliticalContext() {
		return politicalContext;
	}
	
	public long getPopulation() {
		if(data.getPopulation() == 0) {
			long popCalc = 0;
			for(Province prov: data.getProvinces()) {
				popCalc += prov.getData().getPopulationSize();
			}
			data.setPopulation(popCalc);
		}
		
		return data.getPopulation();
	}
	
	public DiplomaticRelations getRelations() {
		return relations;
	}
	
	public boolean isPlayerControlled() {
		return playerControlled;
	}
	
	public void setPlayerControlled(boolean value) {
		playerControlled = value;
	}
	
	public void setRelations(DiplomaticRelations value) {
		if(relations != null)
			throw new RuntimeException("Trying to overwrite relations.");
		
		relations = value;
	}
	
	public String toString() {
		return getData().getName();
	}
	
	public void updateMonthly() {
		long population = 0;
		
		for(Province prov: data.getProvinces()) {
			population += prov.getData().getPopulationSize();
		}
		
		data.setPopulation(population);
		
		for(Province prov: data.getProvinces()) {
			
			prov.updatePopulation(data.getMonthlyPopGrowth());
		}
		
		for(Province prov: data.getProvinces()) {
			prov.getData().updateMarketActors();
			prov.updateResources();
		}
		
		for(Province prov: data.getProvinces()) {
			prov.getData().getPopulation().buyNeeds(this, prov);
		}
	}
}
