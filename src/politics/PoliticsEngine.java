package politics;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

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
		}
	}
}
