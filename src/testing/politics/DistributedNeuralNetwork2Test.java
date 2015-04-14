package testing.politics;

import politics.DistributedNeuralNetwork2;

public class DistributedNeuralNetwork2Test {
	public static void main(String[] args) {
		DistributedNeuralNetwork2 dnn2 = new DistributedNeuralNetwork2();
		dnn2.newVariable("THREE");
		dnn2.newVariable("SIX");
		dnn2.newNeuron("NINE", "THREE+SIX");
		dnn2.newNeuron("SIXTIMESSIX", "SIX * SIX");
		
		dnn2.finalizeStructures();
		
		dnn2.newDistribution("TRUTH");
		dnn2.setDistribution("TRUTH");
		dnn2.setVariableValue("THREE", 3d);
		dnn2.setVariableValue("SIX", 6d);
		
		dnn2.newDistribution("WRONG");
		dnn2.setDistribution("WRONG");
		dnn2.setVariableValue("THREE", 2d);
		dnn2.setVariableValue("SIX", 5d);
		
		dnn2.setDistribution("TRUTH");
		dnn2.update();
		dnn2.print();
		
		System.out.println();
		
		dnn2.setDistribution("WRONG");
		dnn2.update();
		dnn2.print();
	}
}
