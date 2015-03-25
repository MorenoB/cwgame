package map;

import docs.SGMLObject;
import economy.MarketActor;
import game.GameContext;

public class Organisation extends MarketActor {
	private final String name;
	private final OrganisationType type;
	
	public Organisation(SGMLObject obj) {
		super();
		name = obj.getField("name");
		String typeName = obj.getField("type").toUpperCase().replaceAll(" ", "_");
		type = OrganisationType.valueOf(typeName);
	}
	
	public void generateResources(LandProvinceData data) {
		if(getType() == OrganisationType.RESOURCE_EXTRACTOR) {
			long workerPops = 0;
			
			for(SubPopulation subPop: data.getPopulation().getSubPopulations()) {
				if(subPop.getType().canWorkInResourceOps) {
					workerPops += subPop.getSize();
				}
			}
			
			double productionModifier = GameContext.defines.getDouble("productionModifier");
			double generatedValue = workerPops * productionModifier;
			int resourceId = data.getResource().getId();
			double currentValue = getResources().get(resourceId);
			getResources().set(resourceId, currentValue + generatedValue);
		} else
			throw new RuntimeException("Non-resource extractor treated as that! AAAH!");
	}
	
	public String getName() {
		return name;
	}
	
	public OrganisationType getType() {
		return type;
	}
}
