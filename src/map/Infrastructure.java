package map;

import docs.SGMLObject;

public class Infrastructure {
	private final Province prov;
	private int railroad;
	private int road;
	private int port;
	
	public Infrastructure(Province prov, SGMLObject history) {
		this.prov = prov;
		if(history != null && history.hasChild("infrastructure")) {
			SGMLObject infrastructure = history.getChild("infrastructure");
			railroad = infrastructure.getInt("railroad");
			road = infrastructure.getInt("road");
			port = infrastructure.getInt("port");
		} else {
			railroad = 0;
			road = 0;
			port = 0;
		}
	}
	
	public int getPortLevel() {
		return port;
	}
	
	public Province getProvince() {
		return prov;
	}
	
	public int getRailroadLevel() {
		return railroad;
	}
	
	public int getRoadLevel() {
		return road;
	}
}
