package economy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import docs.SGMLObject;
import docs.SGMLReaderUtil;

public class ResourceTable {
	private static final String PATH = "res/common/Resources.xml";
	private static Resource[] resourceIds;
	private static Map<String, Resource> resourceMap = new HashMap<>();
	private static String[] resourceNames;
	private final double[] resourceAmounts;
	
	public ResourceTable() {
		resourceAmounts = new double[resourceIds.length];
	}
	
	public ResourceTable(SGMLObject info) {
		this();
		if(info.hasChildren("resource")) {
			for(final SGMLObject resourceObj: info.getChildren("resource")) {
				addResource(resourceObj);
			}
		} else
			if(info.hasChild("resource")) {
				final SGMLObject resource = info.getChild("resource");
				addResource(resource);
			}
	}
	
	public void add(ResourceTable other) {
		for(int i = 0; i < resourceAmounts.length; i++) {
			resourceAmounts[i] += other.resourceAmounts[i];
		}
	}
	
	private void addResource(SGMLObject resourceObj) {
		final String name = resourceObj.getField("name");
		final Resource resource = getResource(name);
		if(resource == null) {
			System.err.println("No such rseource as " + name);
		}
		final int resourceId = getResource(name).getId();
		final double amount = resourceObj.getDouble("amount");
		resourceAmounts[resourceId] = amount;
	}
	
	public void clear() {
		for(int i = 0; i < getResourceArrayLength(); i++) {
			resourceAmounts[i] = 0d;
		}
	}
	
	public double get(int resourceId) {
		return resourceAmounts[resourceId];
	}
	
	public double[] getResourceAmounts() {
		return resourceAmounts;
	}
	
	public ResourceTable mul(double times) {
		final ResourceTable newTable = new ResourceTable();
		for(int i = 0; i < resourceAmounts.length; i++) {
			newTable.resourceAmounts[i] = times * resourceAmounts[i];
		}
		
		return newTable;
	}
	
	public ResourceTable mul(double times, ResourceTable table) {
		for(int i = 0; i < resourceAmounts.length; i++) {
			table.resourceAmounts[i] = times * resourceAmounts[i];
		}
		
		return table;
	}
	
	public void mulThis(double times) {
		for(int i = 0; i < resourceAmounts.length; i++) {
			resourceAmounts[i] = times * resourceAmounts[i];
		}
	}
	
	public void set(int resourceId, double value) {
		resourceAmounts[resourceId] = value;
	}
	
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		for(int i = 0; i < getResourceArrayLength(); i++) {
			if(resourceAmounts[i] == 0d) {
				continue;
			}
			builder.append(resourceNames[i]);
			builder.append(" ");
			builder.append(resourceAmounts[i]);
			builder.append("\n");
		}
		
		return builder.toString();
	}
	
	public static Resource getResource(int id) {
		return resourceIds[id];
	}
	
	public static Resource getResource(String name) {
		return resourceMap.get(name);
	}
	
	public static int getResourceArrayLength() {
		return resourceIds.length;
	}
	
	public static String getResourceName(int id) {
		return resourceNames[id];
	}
	
	public static void init() {
		SGMLObject resourceObj = SGMLReaderUtil.readFromPath(PATH);
		resourceObj = resourceObj.getChild("resources");
		final List<SGMLObject> resourceObjects = resourceObj.getChildren("resource");
		resourceIds = new Resource[resourceObjects.size()];
		resourceNames = new String[resourceObjects.size()];
		
		int id = 0;
		for(final SGMLObject object: resourceObjects) {
			final Resource newResource = new Resource(object, id);
			id++;
			resourceMap.put(newResource.getName(), newResource);
			resourceIds[newResource.getId()] = newResource;
			resourceNames[newResource.getId()] = newResource.getName();
		}
	}
}
