package map;

import docs.SGMLObject;
import economy.MarketActor;
import economy.ResourceTable;

public class SubPopulation extends MarketActor {
	public float literacy;
	public final String nationality;
	public final String religion;
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
	
	private void addProducedResources(Province homeProv) {
		ResourceTable production = type.getProduction();
		homeProv.getOwner().getPoliticalContext();
		for(int i = 0; i < production.getResourceAmounts().length; i++) {
			ResourceTable.getResource(i).getProductivitySimulation();
			double value = production.get(i);
			if(value == 0d) {
				continue;
			}
			
			production.set(i, value);
		}
		
		getResources().add(production.mul(size));
	}
	
	private void consumeEverydayNeeds() {
		
	}
	
	private void consumeLifeNeeds() {
		
	}
	
	private void consumeLuxuryNeeds() {
		
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
	
	public void update(double monthlyPopGrowth, Population superPop, Province homeProv) {
		grow(monthlyPopGrowth);
		type.update(this, superPop, homeProv);
	}
}
