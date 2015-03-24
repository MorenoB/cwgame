package docs;

import java.util.HashMap;
import java.util.Map;

public class CSSObject implements CSSListener {
	private final Map<String, Map<String, String>> values = new HashMap<>();
	private String currentValue = null;
	
	@Override
	public void elementStart(String name) {
		values.put(name, new HashMap<String, String>());
		currentValue = name;
	}

	@Override
	public void elementEnd() {
		currentValue = null;
	}
	
	public String[] getKeys() {
		return (String[]) values.keySet().toArray();
	}
	
	public Map<String, String> getValues(String element) {
		return values.get(element);
	}
	
	public String getValue(String element, String attribute) {
		return getValues(element).get(attribute);
	}

	@Override
	public void textNode(String name, String data) {
		values.get(currentValue).put(name, data);
	}
}
