package docs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class SGMLObject implements SGMLListener {
	private SGMLObject child = null;
	private final Map<String, SGMLObject> children = new HashMap<>(1);
	private String elementName = null;
	private String elementValue = null;
	private final Map<String, String> fields = new HashMap<>(4);
	private final Map<String, List<SGMLObject>> listsChildren = new HashMap<>(1);
	private final Map<String, List<String>> listsFields = new HashMap<>(1);
	private final String name;
	
	public SGMLObject() {
		this("");
	}
	
	public SGMLObject(String name) {
		this.name = name;
	}
	
	@Override
	public void elementEnd(String name) {
		if(child != null) {
			if(child.name.equals(name)) {
				// check if a list
				if(listsChildren.containsKey(name)) {
					listsChildren.get(name).add(child);
				} else
					if(children.containsKey(name)) {
						listsChildren.put(name, new ArrayList<SGMLObject>(2));
						listsChildren.get(name).add(child);
					} else {
						children.put(child.name, child);
					}
				child = null;
			} else {
				child.elementEnd(name);
			}
		} else
			if(name.equals(elementName)) {
				if(listsFields.containsKey(name)) {
					listsFields.get(name).add(elementValue);
				} else
					if(fields.containsKey(name)) {
						listsFields.put(name, new ArrayList<String>(2));
						listsFields.get(name).add(elementValue);
					} else {
						fields.put(elementName, elementValue);
					}
				elementName = null;
				elementValue = null;
			} else {
				System.out.println("Erroneous end, " + name);
			}
	}
	
	@Override
	public void elementStart(String name) {
		
		if(child != null) {
			child.elementStart(name);
		} else {
			if(elementName == null) {
				elementName = name;
			} else {
				SGMLObject child = new SGMLObject(elementName);
				if(children.containsKey(elementName)) {
					SGMLObject prevChild = children.get(elementName);
					children.remove(elementName);
					List<SGMLObject> list = new ArrayList<>();
					list.add(prevChild);
					// list.add(child);
					listsChildren.put(elementName, list);
				}
				
				this.child = child;
				this.child.elementStart(name);
				elementName = null;
			}
		}
	}
	
	public boolean getBoolean(String fieldName) {
		return Boolean.parseBoolean(getField(fieldName));
	}
	
	public SGMLObject getChild(String name) {
		return children.get(name);
	}
	
	public Map<String, SGMLObject> getChildren() {
		return children;
	}
	
	public List<SGMLObject> getChildren(String name) {
		return listsChildren.get(name);
	}
	
	public double getDouble(String fieldName) {
		return Double.parseDouble(getField(fieldName));
	}
	
	public String getField(String name) {
		return fields.get(name);
	}
	
	public Map<String, String> getFields() {
		return fields;
	}
	
	public float getFloat(String fieldName) {
		return Float.parseFloat(getField(fieldName));
	}
	
	public int getInt(String fieldName) {
		return Integer.valueOf(getField(fieldName));
	}
	
	public long getLong(String fieldName) {
		return Long.parseLong(getField(fieldName));
	}
	
	public short getShort(String fieldName) {
		return Short.parseShort(getField(fieldName));
	}
	
	public boolean hasChild(String name) {
		return children.containsKey(name);
	}
	
	public boolean hasChildren(String name) {
		return listsChildren.containsKey(name);
	}
	
	public boolean hasField(String name) {
		return fields.containsKey(name);
	}
	
	@Override
	public void textNode(String value) {
		if(child != null) {
			child.textNode(value);
		} else {
			if(elementName == null) {
				System.out.println("Erroneous node.");
			} else {
				elementValue = value;
			}
		}
	}
	
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		for(Entry<String, String> field: fields.entrySet()) {
			s.append("Field: " + field.getKey() + " : " + field.getValue() + "\n");
		}
		for(SGMLObject m: children.values()) {
			s.append("Child: ").append(m.name + " {\n").append(m.toString()).append("}\n");
		}
		
		return s.toString();
	}
}
