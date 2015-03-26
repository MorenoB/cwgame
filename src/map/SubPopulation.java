package map;

import docs.SGMLObject;
import economy.MarketActor;
import economy.ResourceTable;

public class SubPopulation extends MarketActor {
	public float literacy;
	public final String nationality;
	public final String religion;
	private final ResourceTable remainingNeeds = new ResourceTable();
	public long size;
	
	public final PopulationType type;
	
	public SubPopulation(SGMLObject object) {
		nationality = object.getField("nationality");
		religion = object.getField("religion");
		size = object.getInt("size");
		type = PopulationType.getPopType(object.getField("type"));
		literacy = object.getFloat("literacy");
		initBuyRequests();
	}
	
	public SubPopulation(String nationality, String religion, PopulationType type, long size, float literacy) {
		this.nationality = nationality;
		this.religion = religion;
		this.type = type;
		this.size = size;
		this.literacy = literacy;
		initBuyRequests();
	}
	
	public void addProducedResources(Province homeProv) {
		ResourceTable production = type.getProduction();
		for(int i = 0; i < production.getResourceAmounts().length; i++) {
			double value = production.get(i);
			if(value == 0d) {
				continue;
			}
			
			production.set(i, value);
		}
		
		getResources().add(production.mul(size));
	}
	
	public void consumeOwnedResources() {
		for(int i = 0; i < ResourceTable.getResourceArrayLength(); i++) {
			double owned = getResources().get(i);
			double need = remainingNeeds.get(i);
			if(owned > need) {
				remainingNeeds.set(i, 0d);
				owned -= need;
			} else {
				owned = 0;
				remainingNeeds.set(i, need - owned);
			}
			
			getResources().set(i, owned);
		}
	}
	
	public float getLiteracy() {
		return literacy;
	}
	
	public String getNationality() {
		return nationality;
	}
	
	public String getReligion() {
		return religion;
	}
	
	public ResourceTable getRemainingNeeds() {
		return remainingNeeds;
	}
	
	public long getSize() {
		return size;
	}
	
	public PopulationType getType() {
		return type;
	}
	
	private void grow(double monthlyPopGrowth) {
		size *= monthlyPopGrowth;
	}
	
	// TODO maybe make this lazy
	private void initBuyRequests() {
		
	}
	
	public void resetRemainingNeeds() {
		for(int i = 0; i < ResourceTable.getResourceArrayLength(); i++) {
			double current = remainingNeeds.get(i);
			double staticNeed = 0d;
			staticNeed += type.getLifeNeeds().get(i);
			staticNeed += type.getEverydayNeeds().get(i);
			staticNeed += type.getLuxuryNeeds().get(i);
			staticNeed *= size;
			double newNeed = current + staticNeed;
			remainingNeeds.set(i, newNeed);
		}
	}
	
	public void update(double monthlyPopGrowth, Population superPop, Province homeProv) {
		grow(monthlyPopGrowth);
		type.update(this, superPop, homeProv);
	}
}
