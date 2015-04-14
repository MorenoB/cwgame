package politics;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import diplomacy.Countries;
import diplomacy.Country;
import docs.SGMLReaderUtil;

/**
 * The PoliticsEngine drives the politics system, key features are found here.
 * 
 * @author nastyasalways
 *
 */
public class PoliticsEngine {
	private final DistributedNeuralNetwork2 dnn2 = new DistributedNeuralNetwork2();
	private final Map<String, Simulation> simulations = new HashMap<>();
	
	public PoliticsEngine() {
		File simulationsDir = new File("res/politics/simulations/");
		for(File simFile: simulationsDir.listFiles()) {
			Simulation newSim = new Simulation(SGMLReaderUtil.readFromPath(simFile));
			simulations.put(newSim.getName(), newSim);
			dnn2.newNeuron(newSim.getName(), newSim.getExpression());
		}
		
		dnn2.newVariable("__POPULATION__", 0d);
		
		dnn2.finalizeStructures();
		for(Country country: Countries.getCountries()) {
			dnn2.newDistribution(country.getData().getName());
		}
	}
	
	public void setCurrentCountry(String countryName) {
		dnn2.setDistribution(countryName);
	}
}
