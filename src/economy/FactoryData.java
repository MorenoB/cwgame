package economy;

import java.util.HashMap;
import java.util.Map;

import docs.SGMLObject;
import docs.SGMLReaderUtil;

public class FactoryData {
	private static final Map<String, FactoryData> factoryTypes = new HashMap<>();
	private final String name;
	private final ResourceTable inputs;
	private final ResourceTable outputs;
	private final int manpower;
	
	public FactoryData(SGMLObject object) {
		this.name = object.getField("name");
		this.inputs = new ResourceTable();
		this.outputs = new ResourceTable();
		this.manpower = object.getInt("manpower");
		
		SGMLObject inputsData = object.getChild("inputs");
		if(inputsData.hasChildren("input")) {
			for(SGMLObject input: inputsData.getChildren("input")) {
				createInput(input);
			}
		} else {
			createInput(inputsData.getChild("input"));
		}
		
		SGMLObject outputsData = object.getChild("outputs");
		if(outputsData.hasChildren("output")) {
			for(SGMLObject output: outputsData.getChildren("output")) {
				createOutput(output);
			}
		} else {
			createOutput(outputsData.getChild("output"));
		}
	}
	
	public ResourceTable getInputs() {
		return inputs;
	}
	
	public int getManpower() {
		return manpower;
	}
	
	public String getName() {
		return name;
	}
	
	public ResourceTable getOutputs() {
		return outputs;
	}
	
	private void createInput(SGMLObject input) {
		String name = input.getField("name");
		int amount = input.getInt("amount");
		int resourceId = ResourceTable.getResource(name).getId();
		inputs.set(resourceId, amount);
	}
	
	private void createOutput(SGMLObject output) {
		String name = output.getField("name");
		int amount = output.getInt("amount");
		int resourceId = ResourceTable.getResource(name).getId();
		outputs.set(resourceId, amount);
	}
	
	public static void loadFactories() {
		SGMLObject factories = SGMLReaderUtil.readFromPath("res/common/Factories.xml");
		for(SGMLObject factoryObject: factories.getChildren("factory")) {
			FactoryData data = new FactoryData(factoryObject);
			factoryTypes.put(data.getName(), data);
		}
	}
	
	public static FactoryData getFactoryData(String name) {
		return factoryTypes.get(name);
	}
}
