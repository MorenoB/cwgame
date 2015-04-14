package politics;

import docs.SGMLObject;

public class Simulation {
	private final String expression;
	private final String guiname;
	private final String name;
	
	public Simulation(SGMLObject obj) {
		obj = obj.getChild("simulation");
		name = obj.getField("name");
		guiname = obj.getField("guiname");
		expression = obj.getField("expression");
	}
	
	public String getName() {
		return name;
	}
}
