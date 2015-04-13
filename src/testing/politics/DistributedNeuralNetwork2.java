package testing.politics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import expr.Expr;
import expr.Parser;
import expr.SyntaxException;
import expr.Variable;

public class DistributedNeuralNetwork2 implements NeuralNetwork {
	private Distribution currentDistribution;
	private final Map<String, Distribution> distributions = new HashMap<>();
	private boolean finalized = false;
	private final Map<String, Neuron> neurons = new HashMap<>();
	private final List<Neuron> orderedNeuronList = new ArrayList<>();
	private final List<WrappedVariable> orderedVariableList = new ArrayList<>();
	private final Parser parser = new Parser();
	private final Map<String, WrappedVariable> variables = new HashMap<>();
	
	public void finalizeStructures() {
		finalized = true;
	}
	
	public String getDistribution() {
		return currentDistribution.name;
	}
	
	@Override
	public double getValue(String name) {
		Neuron neuron = neurons.get(name);
		return currentDistribution.neuronValues[(int) neuron.value];
	}
	
	public void newDistribution(String name) {
		if(!finalized)
			throw new RuntimeException("Finalize before creating distributions.");
		
		int neuronsLength = neurons.values().size();
		int variablesLength = variables.values().size();
		Distribution newDistribution = new Distribution(name, neuronsLength, variablesLength);
		distributions.put(name, newDistribution);
	}
	
	public void newNeuron(String name, String expr) {
		if(finalized)
			throw new RuntimeException("Can't instantiate neurons after finalizing.");
		
		try {
			Expr parsedExpr = parser.parseString(expr);
			Neuron newNeuron = new Neuron(name, parsedExpr);
			neurons.put(name, newNeuron);
			orderedNeuronList.add(newNeuron);
			newNeuron.value = orderedNeuronList.size() - 1;
			parser.allow(newNeuron.variable);
		} catch(SyntaxException e) {
			System.err.println(e.explain());
		}
	}
	
	public void newVariable(String name) {
		newVariable(name, 0d);
	}
	
	public void newVariable(String name, double baseValue) {
		Variable var = Variable.make(name);
		var.setValue(baseValue);
		parser.allow(var);
		WrappedVariable wrapped = new WrappedVariable(name, var, orderedVariableList.size());
		variables.put(name, wrapped);
		orderedVariableList.add(wrapped);
	}
	
	public void print() {
		System.out.println("Printing distribution " + currentDistribution.name + ", starting with neurons");
		for(int i = 0; i < orderedNeuronList.size(); i++) {
			Neuron n = orderedNeuronList.get(i);
			System.out.println(n.name + " " + currentDistribution.neuronValues[(int) n.value]);
		}
		
		System.out.println("Printing variables");
		for(int i = 0; i < orderedVariableList.size(); i++) {
			WrappedVariable w = orderedVariableList.get(i);
			System.out.println(w.name + " " + currentDistribution.variableValues[w.index]);
		}
	}
	
	public void setDistribution(String name) {
		currentDistribution = distributions.get(name);
		for(int i = 0; i < orderedNeuronList.size(); i++) {
			orderedNeuronList.get(i).variable.setValue(currentDistribution.neuronValues[i]);
		}
		
		for(int i = 0; i < orderedVariableList.size(); i++) {
			orderedVariableList.get(i).variable.setValue(currentDistribution.variableValues[i]);
		}
	}
	
	public void setVariableValue(String name, double value) {
		WrappedVariable actualVariable = variables.get(name);
		currentDistribution.variableValues[actualVariable.index] = value;
		actualVariable.variable.setValue(value);
	}
	
	public void update() {
		for(int i = 0; i < orderedNeuronList.size(); i++) {
			Neuron neuron = orderedNeuronList.get(i);
			neuron.variable.setValue(currentDistribution.neuronValues[i]);
		}
		
		for(int i = 0; i < orderedNeuronList.size(); i++) {
			Neuron neuron = orderedNeuronList.get(i);
			double exprValue = neuron.expr.value();
			neuron.variable.setValue(exprValue);
			currentDistribution.neuronValues[i] = exprValue;
		}
	}
	
	/**
	 * A distribution represents the data.
	 * 
	 * @author nastyasalways
	 *
	 */
	public class Distribution {
		public final String name;
		public final double[] neuronValues;
		public final double[] variableValues;
		
		public Distribution(String name, int neuronsLength, int variableLength) {
			this.name = name;
			neuronValues = new double[neuronsLength];
			variableValues = new double[variableLength];
		}
	}
	
	/**
	 * A wrapper class with pointers to relevant data regarding this variable.
	 * 
	 * @author nastyasalways
	 *
	 */
	public class WrappedVariable {
		public int index;
		public final String name;
		public final Variable variable;
		
		public WrappedVariable(String name, Variable variable, int index) {
			this.name = name;
			this.variable = variable;
			this.index = index;
		}
	}
}