package economy;

import docs.SGMLObject;

public class Resource {
	private final int id;
	private final String name;
	
	public Resource(SGMLObject object, int id) {
		this.id = id;
		name = object.getField("name");
		if(object.hasField("links")) {
			SGMLObject links = object.getChild("links");
			links.getField("productivity");
		}
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
}
