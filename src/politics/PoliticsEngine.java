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
		dnn2.newVariable("__POP_GROWTH__", 0d);
		
		dnn2.finalizeStructures();
		for(Country country: Countries.getCountries()) {
			String countryName = country.getData().getName();
			dnn2.newDistribution(countryName);
			dnn2.setDistribution(countryName);
			
			// initialize variables to their first values
			// some variables will keep this value for the rest of their lifetime
			
			dnn2.setVariableValue("__POP_GROWTH__", country.getData().getMonthlyPopGrowth());
			dnn2.setVariableValue("__POPULATION__", country.getData().getPopulation());
			/*
			 * TODO Update the neural network system so that __POP_GROWTH__ can be a
			 * simple variable, and put everything else that affects population growth
			 * into connections.
			 */
		}
	}
	
	public void setCurrentCountry(String countryName) {
		dnn2.setDistribution(countryName);
	}
}
