package diplomacy;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import docs.SGMLObject;
import docs.SGMLReaderUtil;

public class Countries {
	private static Country[] array;
	private static final Map<String, Country> countries = new HashMap<>();
	private static final List<Country> countriesList = new ArrayList<>();
	
	public static Country[] getCountries() {
		if((array == null) || (array.length != countries.size())) {
			array = new Country[countries.size()];
		}
		
		return countriesList.toArray(array);
	}
	
	public static Country getCountry(String name) {
		return countries.get(name);
	}
	
	public static void init() {
		File countriesFolder = new File("res/common/countries/");
		for(File continentFolder: countriesFolder.listFiles()) {
			if(!continentFolder.isDirectory()) {
				continue;
			}
			
			for(File countryFile: continentFolder.listFiles()) {
				SGMLObject countrySGML = SGMLReaderUtil.readFromPath(countryFile);
				Country country = new Country(countrySGML);
				countries.put(country.getData().getName(), country);
				countriesList.add(country);
			}
		}
		
		Collections.sort(countriesList);
		
		for(Country c: getCountries()) {
			DiplomaticRelations.createRelations(c);
		}
	}
}
