package economy;

import java.util.HashMap;
import java.util.Map;

import map.Province;
import map.SubPopulation;

public class ProvinceMarket {
	private final ResourceTable demand = new ResourceTable();
	/**
	 * The excess resources that are to be traded among POPs in exchange for money.
	 */
	private final Map<SubPopulation, ResourceTable> excessResources = new HashMap<>();
	private final Map<SubPopulation, ResourceTable> needs = new HashMap<>();
	private final Province province;
	private final ResourceTable supply = new ResourceTable();
	
	public ProvinceMarket(Province province) {
		this.province = province;
	}
	
	public void addExcessResourcesToSupply() {
		for(SubPopulation subPop: province.getData().getPopulation().getSubPopulations()) {
			excessResources.put(subPop, subPop.getResources());
		}
	}
	
	public void addNeeds() {
		for(SubPopulation subPop: province.getData().getPopulation().getSubPopulations()) {
			ResourceTable remainingNeeds = subPop.getRemainingNeeds();
			needs.put(subPop, remainingNeeds);
		}
	}
	
	public void addProducedResources() {
		for(SubPopulation subPop: province.getData().getPopulation().getSubPopulations()) {
			subPop.addProducedResources(province);
		}
	}
	
	public void calculatePrices() {
		for(ResourceTable table: needs.values()) {
			for(int i = 0; i < ResourceTable.getResourceArrayLength(); i++) {
				double oldValue = demand.get(i);
				double addValue = table.get(i);
				double newValue = oldValue + addValue;
				demand.set(i, newValue);
			}
		}
		
		for(ResourceTable table: excessResources.values()) {
			for(int i = 0; i < ResourceTable.getResourceArrayLength(); i++) {
				double oldValue = supply.get(i);
				double addValue = table.get(i);
				double newValue = oldValue + addValue;
				supply.set(i, newValue);
			}
		}
	}
	
	public void consumeOwnedResources() {
		for(SubPopulation subPop: province.getData().getPopulation().getSubPopulations()) {
			subPop.resetRemainingNeeds();
			subPop.consumeOwnedResources();
		}
	}
	
	public Province getProvince() {
		return province;
	}
	
	public void resetData() {
		demand.clear();
		supply.clear();
		needs.clear();
		excessResources.clear();
	}
}
