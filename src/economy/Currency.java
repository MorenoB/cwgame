package economy;

import docs.SGMLObject;

public class Currency {
	private final int id;
	private final String officialName, acronym, colloquial;
	
	public Currency(SGMLObject info, int id) {
		this.id = id;
		SGMLObject names = info.getChild("names");
		officialName = names.getField("official");
		acronym = names.getField("acronym");
		colloquial = names.getField("colloquial");
	}
	
	public String getAcronym() {
		return acronym;
	}
	
	public String getColloquialName() {
		return colloquial;
	}
	
	public int getId() {
		return id;
	}
	
	public String getOfficialName() {
		return officialName;
	}
}
