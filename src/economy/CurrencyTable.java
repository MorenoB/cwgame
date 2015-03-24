package economy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import docs.SGMLObject;
import docs.SGMLReaderUtil;

public class CurrencyTable {
	private static Currency[] currencyIds;
	private static Map<String, Currency> currencyMap = new HashMap<>();
	private static String[] currencyNames;
	private static final String PATH = "res/common/Currencies.xml";
	private final double[] currencyAmounts;
	
	public CurrencyTable() {
		currencyAmounts = new double[currencyIds.length];
		
	}
	
	public double get(int index) {
		return currencyAmounts[index];
	}
	
	public void set(int index, double value) {
		currencyAmounts[index] = value;
	}
	
	public static Currency getCurrency(String name) {
		return currencyMap.get(name);
	}
	
	public static int getCurrencyArrayLength() {
		return currencyNames.length;
	}
	
	public static void init() {
		SGMLObject currencyObj = SGMLReaderUtil.readFromPath(PATH);
		currencyObj = currencyObj.getChild("currencies");
		List<SGMLObject> currencyObjects = currencyObj.getChildren("currency");
		currencyIds = new Currency[currencyObjects.size()];
		currencyNames = new String[currencyObjects.size()];
		
		int id = 0;
		for(SGMLObject object: currencyObjects) {
			Currency newCurrency = new Currency(object, id);
			id++;
			currencyMap.put(newCurrency.getAcronym(), newCurrency);
			currencyIds[newCurrency.getId()] = newCurrency;
			currencyNames[newCurrency.getId()] = newCurrency.getOfficialName();
		}
	}
}
