package politics;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import docs.SGMLObject;
import docs.SGMLReaderUtil;
import expr.Parser;

public class GlobalPoliticalContext {
	private final Parser exprParser = new Parser();
	private final Map<String, Policy> policies = new HashMap<>();
	private final Map<String, Simulation> simulations = new HashMap<>();
	
	public GlobalPoliticalContext(String path) {
		File policiesFolder = new File(path + "policies/");
		if(!policiesFolder.exists()) {
			policiesFolder.mkdirs();
		}
		
		File simulationsFolder = new File(path + "simulations/");
		if(!simulationsFolder.exists()) {
			simulationsFolder.mkdirs();
		}
		
		for(File policyFile: policiesFolder.listFiles()) {
			SGMLObject policyFileXML = SGMLReaderUtil.readFromPath(policyFile);
			Policy newPolicy = new Policy(policyFileXML, this);
			policies.put(newPolicy.getName(), newPolicy);
		}
		
		for(File simFile: simulationsFolder.listFiles()) {
			SGMLObject simFileXML = SGMLReaderUtil.readFromPath(simFile);
			Simulation newSimulation = new Simulation(simFileXML);
			simulations.put(newSimulation.getName(), newSimulation);
		}
	}
	
	public Parser getParser() {
		return exprParser;
	}
	
	public Map<String, Policy> getPolicies() {
		return policies;
	}
	
	public Map<String, Simulation> getSimulations() {
		return simulations;
	}
}
