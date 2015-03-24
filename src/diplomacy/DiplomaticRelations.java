package diplomacy;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import docs.SGMLObject;
import docs.SGMLReaderUtil;
import docs.SGMLWriter;

public class DiplomaticRelations {
	private static Map<Country, DiplomaticRelations> dipRelations = new HashMap<>();
	private final Map<Country, BilateralRelation> biRelations = new HashMap<>();
	private final Country self;
	private final Map<Country, UnilateralRelation> uniRelations = new HashMap<>();
	
	private DiplomaticRelations(Country self) {
		this.self = self;
		
		for(Country country: Countries.getCountries()) {
			if(dipRelations.containsKey(country) && dipRelations.get(country).biRelations.containsKey(self)) {
				biRelations.put(country, dipRelations.get(country).biRelations.get(self));
			} else {
				BilateralRelation biRelation = new BilateralRelation(self, country);
				biRelations.put(country, biRelation);
			}
			
			uniRelations.put(country, new UnilateralRelation(self, country));
		}
	}
	
	public BilateralRelation getBilateral(Country c) {
		return getBilateralRelationsWith(c);
	}
	
	public BilateralRelation getBilateralRelationsWith(Country c) {
		return biRelations.get(c);
	}
	
	public Country getCountry() {
		return self;
	}
	
	public UnilateralRelation getUnilateral(Country c) {
		return getUnilateralRelationsWith(c);
	}
	
	public UnilateralRelation getUnilateralRelationsWith(Country c) {
		return uniRelations.get(c);
	}
	
	public void readRelations() {
		String selfName = self.getData().getName();
		File relationsFile = new File("res/history/diplomacy/" + selfName + ".xml");
		if(relationsFile.exists()) {
			SGMLObject relations = SGMLReaderUtil.readFromPath(relationsFile).getChild("relations");
			for(SGMLObject relation: relations.getChildren("relation")) {
				Country otherCountry = Countries.getCountry(relation.getField("country"));
				int value = relation.getInt("value");
				int monthlyEffect = relation.getInt("monthlyEffect");
				uniRelations.get(otherCountry).addOpinionModifier("Pre-1949", value, monthlyEffect, 0);
			}
		} else {
			try {
				relationsFile.createNewFile();
				FileOutputStream fos = new FileOutputStream(relationsFile);
				SGMLWriter writer = new SGMLWriter(fos);
				writer.elementStart("relations");
				writer.elementStart("relation");
				writer.elementStart("country");
				writer.textNode("Soviet Union");
				writer.elementEnd("country");
				writer.elementStart("value");
				writer.textNode("0");
				writer.elementEnd("value");
				writer.elementStart("monthlyEffect");
				writer.textNode("0");
				writer.elementEnd("monthlyEffect");
				writer.elementEnd("relation");
				writer.elementStart("relation");
				writer.elementStart("country");
				writer.textNode("United States");
				writer.elementEnd("country");
				writer.elementStart("value");
				writer.textNode("0");
				writer.elementEnd("value");
				writer.elementStart("monthlyEffect");
				writer.textNode("0");
				writer.elementEnd("monthlyEffect");
				writer.elementEnd("relation");
				writer.elementEnd("relations");
				writer.close();
				fos.close();
			} catch(IOException exception) {
				exception.printStackTrace();
			}
		}
	}
	
	public static void createRelations(Country c) {
		DiplomaticRelations newDipRelations = new DiplomaticRelations(c);
		dipRelations.put(c, newDipRelations);
		c.setRelations(newDipRelations);
		newDipRelations.readRelations();
	}
}
