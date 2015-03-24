package economy;

import game.GameContext;
import politics.Simulation;
import docs.SGMLObject;

public class Resource {
	private final int id;
	private final String name;
	private final Simulation productivitySim;
	
	public Resource(SGMLObject object, int id) {
		this.id = id;
		name = object.getField("name");
		if(object.hasField("links")) {
			SGMLObject links = object.getChild("links");
			String productivitySimName = links.getField("productivity");
			productivitySim = GameContext.globalContext.getSimulations().get(productivitySimName);
		} else {
			productivitySim = null;
		}
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public Simulation getProductivitySimulation() {
		return productivitySim;
	}
}
