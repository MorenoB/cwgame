package map;

import java.util.ArrayList;
import java.util.List;

import diplomacy.Country;
import docs.SGMLObject;

public class Population {
	private static List<SubPopulation> theOtherList = new ArrayList<>();
	private boolean newMarketActors = false;
	private List<SubPopulation> subPops = new ArrayList<>();
	private long totalSize;
	
	public Population(SGMLObject obj) {
		
		if(obj.hasChild("populations")) {
			SGMLObject populations = obj.getChild("populations");
			if(populations.hasChildren("population")) {
				for(SGMLObject subPopObj: populations.getChildren("population")) {
					SubPopulation subPop = new SubPopulation(subPopObj);
					subPops.add(subPop);
				}
			} else {
				SubPopulation subPop = new SubPopulation(populations.getChild("population"));
				subPops.add(subPop);
			}
			
			for(SubPopulation subPop: subPops) {
				totalSize += subPop.getSize();
			}
		} else {
			totalSize = obj.getInt("population");
		}
	}
	
	public void addSubPopulation(SubPopulation pop) {
		subPops.add(pop);
		newMarketActors = true;
	}
	
	public void buyNeeds(Country country, Province prov) {
		
	}
	
	public long getPopulationSize() {
		return totalSize;
	}
	
	public List<SubPopulation> getSubPopulations() {
		return subPops;
	}
	
	public String[] getSubPopulationString() {
		int length = subPops.size();
		String[] values = new String[length];
		for(int i = 0; i < length; i++) {
			SubPopulation subPop = subPops.get(i);
			values[i] = subPop.size + " " + subPop.nationality + " " + subPop.type + "s, "
					+ (subPop.literacy * 100f) + "% literate.";
		}
		
		return values;
	}
	
	public boolean hasNewMarketActors() {
		return newMarketActors;
	}
	
	public void update(Province homeProv, double monthlyPopGrowth) {
		long totalSize = 0;
		for(int i = 0; i < subPops.size(); i++) {
			SubPopulation subPop = subPops.get(i);
			subPop.update(monthlyPopGrowth, this, homeProv);
			totalSize += subPop.getSize();
		}
		
		if(totalSize != 0) {
			this.totalSize = totalSize;
		} else {
			this.totalSize *= monthlyPopGrowth;
		}
		
		for(int i = 0; i < subPops.size(); i++) {
			SubPopulation subPop = subPops.get(i);
			if(subPop.getSize() > 0) {
				theOtherList.add(subPop);
			}
		}
		
		List<SubPopulation> placeholder = subPops;
		subPops = theOtherList;
		theOtherList = placeholder;
		theOtherList.clear();
	}
}
