package economy;

import map.Province;

public class Factory {
	private final FactoryData data;
	private final Province province;
	
	public Factory(Province province, FactoryData data) {
		this.data = data;
		this.province = province;
	}
}
