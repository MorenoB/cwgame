package map;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import docs.SGMLObject;
import docs.SGMLReaderUtil;
import economy.ResourceTable;

public class PopulationType {
	private static final float CONVERSION_FACTOR = 0.995f;
	private static final String PATH = "res/common/poptypes/";
	private static final Map<String, PopulationType> popTypes = new HashMap<>();
	private static final Random random = new Random();
	static {
		File popTypeFolder = new File(PATH);
		for(File file: popTypeFolder.listFiles()) {
			SGMLObject popType = SGMLReaderUtil.readFromPath(file);
			PopulationType newPopType = new PopulationType(popType);
			popTypes.put(newPopType.getName(), newPopType);
		}
	}
	public final boolean canBeUnemployed;
	public final boolean canBuildFactories;
	public final boolean canExpandFactories;
	public final boolean canWorkInFactories;
	public final boolean canWorkInResourceOps;
	private final Conversion[] conversions;
	private final ResourceTable everydayNeeds;
	private final ResourceTable lifeNeeds;
	private final ResourceTable luxuryNeeds;
	private final String name;
	private final String plural;
	private final ProductionType prodType;
	private final ResourceTable production = new ResourceTable();
	
	public PopulationType(SGMLObject info) {
		info = info.getChild("populationType");
		name = info.getField("name");
		plural = info.getField("plural");
		canBeUnemployed = info.getBoolean("unemployed");
		
		SGMLObject actions = info.getChild("actions");
		SGMLObject factories = actions.getChild("factories");
		canBuildFactories = factories.getBoolean("build");
		canExpandFactories = factories.getBoolean("expand");
		canWorkInFactories = factories.getBoolean("workIn");
		
		SGMLObject resourceOps = actions.getChild("resourceOps");
		canWorkInResourceOps = resourceOps.getBoolean("workIn");
		
		SGMLObject conversionsObj = info.getChild("conversions");
		List<SGMLObject> conversionList;
		if(conversionsObj.hasChildren("conversion")) {
			conversionList = conversionsObj.getChildren("conversion");
		} else {
			conversionList = new ArrayList<>();
			conversionList.add(conversionsObj.getChild("conversion"));
		}
		
		conversions = new Conversion[conversionList.size()];
		int iter = 0;
		for(SGMLObject iterObj: conversionList) {
			conversions[iter] = new Conversion(iterObj);
			iter++;
		}
		
		SGMLObject production = info.getChild("production");
		prodType = ProductionType.valueOf(production.getField("type"));
		if(prodType != ProductionType.NONE) {
			if(production.hasChildren("resource")) {
				for(SGMLObject obj: production.getChildren("resource")) {
					addResourceProduction(obj);
				}
			} else {
				if(production.hasChild("resource")) {
					addResourceProduction(production.getChild("resource"));
				}
			}
		}
		
		SGMLObject needs = info.getChild("needs");
		
		if(needs.hasChild("life")) {
			lifeNeeds = new ResourceTable(needs.getChild("life"));
		} else {
			lifeNeeds = new ResourceTable();
		}
		
		if(needs.hasChild("everyday")) {
			everydayNeeds = new ResourceTable(needs.getChild("everyday"));
		} else {
			everydayNeeds = new ResourceTable();
		}
		
		if(needs.hasChild("luxury")) {
			luxuryNeeds = new ResourceTable(needs.getChild("luxury"));
		} else {
			luxuryNeeds = new ResourceTable();
		}
	}
	
	private void addResourceProduction(SGMLObject obj) {
		double base = obj.getDouble("base");
		String name = obj.getField("name");
		int id = ResourceTable.getResource(name).getId();
		production.set(id, (float) base);
	}
	
	public ResourceTable getEveryDayNeeds() {
		return everydayNeeds;
	}
	
	public ResourceTable getLifeNeeds() {
		return lifeNeeds;
	}
	
	public ResourceTable getLuxuryNeeds() {
		return luxuryNeeds;
	}
	
	public String getName() {
		return name;
	}
	
	public ResourceTable getProduction() {
		return production;
	}
	
	public String toString() {
		return getName();
	}
	
	public void update(SubPopulation sub, Population pop, Province prov) {
		if(sub.getType() == this) {
			for(Conversion conversion: conversions) {
				float factor = conversion.getFactor(sub, pop, prov);
				float chance = random.nextFloat();
				if((factor > 0.0f) && (chance < factor)) {
					String name = conversion.name;
					PopulationType type = getPopType(name);
					long newSubSize = (long) (sub.size * CONVERSION_FACTOR);
					long convertedSize = sub.size - newSubSize;
					
					boolean doneConverting = false;
					for(SubPopulation subPopType: pop.getSubPopulations()) {
						if((subPopType.type == type)
								&& subPopType.getNationality().equals(sub.getNationality())
								&& subPopType.getReligion().equals(sub.getReligion())) {
							
							sub.size = newSubSize;
							
							subPopType.size += convertedSize;
							doneConverting = true;
						}
						
						if(doneConverting) {
							break;
						}
					}
					
					if(!doneConverting) {
						SubPopulation newPopToAdd = new SubPopulation(sub.nationality, sub.religion, type,
								convertedSize, sub.literacy);
						sub.size = newSubSize;
						pop.addSubPopulation(newPopToAdd);
						doneConverting = true;
					}
				}
			}
		}
	}
	
	public static PopulationType getPopType(String name) {
		return popTypes.get(name);
	}
	
	public static class Conversion {
		private final float baseFactor;
		private final String name;
		private final float provinceHasFactoriesFactor;
		private final float provinceHasNoFactoriesFactor;
		private final float provinceHasNoResourcesFactor;
		private final float provinceHasResourcesFactor;
		private final float unemployedFactor;
		
		public Conversion(SGMLObject info) {
			name = info.getField("name");
			SGMLObject factors = info.getChild("factors");
			baseFactor = factors.getFloat("base");
			unemployedFactor = factors.getFloat("unemployed");
			
			SGMLObject provinceFactors = factors.getChild("province");
			SGMLObject factoryFactors = provinceFactors.getChild("factories");
			provinceHasFactoriesFactor = factoryFactors.getFloat("has");
			provinceHasNoFactoriesFactor = factoryFactors.getFloat("hasNone");
			
			SGMLObject resourceFactors = provinceFactors.getChild("resources");
			provinceHasResourcesFactor = resourceFactors.getFloat("has");
			provinceHasNoResourcesFactor = resourceFactors.getFloat("hasNone");
		}
		
		public float getFactor(SubPopulation sub, Population pop, Province prov) {
			float factor = baseFactor;
			
			boolean hasResourceOp = false;
			for(Organisation org: prov.getData().getOrganisations()) {
				if(org.getType() == OrganisationType.RESOURCE_EXTRACTOR) {
					hasResourceOp = true;
				}
			}
			
			if(hasResourceOp) {
				factor += provinceHasResourcesFactor;
			} else {
				factor += provinceHasNoResourcesFactor;
			}
			
			return factor;
		}
	}
	
	public static enum ProductionType {
		ALL,
		NONE,
		RANDOM_EACH_TIME,
		RANDOM_PER_POP
	}
}
