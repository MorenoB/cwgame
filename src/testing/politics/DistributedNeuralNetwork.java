package testing.politics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import expr.Expr;
import expr.Parser;
import expr.SyntaxException;
import expr.Variable;
import gnu.trove.list.TDoubleList;
import gnu.trove.list.array.TDoubleArrayList;

/**
 * A distributed neural network is a neural network that keeps the same structure, but has
 * different values in different places. It allows "infrastructure" to be shared among the
 * multiple instances of the same structure, instead of copying it.
 * 
 * @author nastyasalways
 *
 */
public class DistributedNeuralNetwork implements NeuralNetwork {
	private int currentDistribution = 0;
	private int distributions = 0;
	private final Map<String, DistributedNeuron> neurons = new HashMap<>();
	private final Parser parser = new Parser();
	private final List<Variable> variableObjs = new ArrayList<>();
	private final List<TDoubleList> variableValues = new ArrayList<>();
	
	public DistributedNeuralNetwork() {
		
	}
	
	public int addDistribution() {
		int toReturn = distributions;
		distributions += 1;
		for(DistributedNeuron neuron: neurons.values()) {
			neuron.values.add(0d);
		}
		return toReturn;
	}
	
	@Override
	public double getValue(String name) {
		final int distr = currentDistribution;
		DistributedNeuron neuron = neurons.get(name);
		return neuron.values.get(distr);
	}
	
	public void newNeuron(String name, String expr) {
		try {
			Expr parsedExpr = parser.parseString(expr);
			DistributedNeuron newNeuron = new DistributedNeuron(name, parsedExpr);
			neurons.put(name, newNeuron);
			parser.allow(newNeuron.variable);
		} catch(SyntaxException e) {
			System.err.println(e.explain());
		}
	}
	
	public void setDistribution(int value) {
		currentDistribution = value;
	}
	
	public void update() {
		final int distr = currentDistribution;
		Collection<DistributedNeuron> neuronVals = neurons.values();
		
		for(DistributedNeuron neuron: neuronVals) {
			neuron.variable.setValue(neuron.values.get(distr));
		}
		
		for(DistributedNeuron neuron: neuronVals) {
			double exprValue = neuron.expr.value();
			neuron.values.set(distr, exprValue);
			neuron.variable.setValue(exprValue);
		}
	}
	
	public class DistributedNeuron {
		public final Expr expr;
		public final String name;
		public final TDoubleList values;
		public final Variable variable;
		
		public DistributedNeuron(String name, Expr expr) {
			variable = Variable.make(name);
			parser.allow(variable);
			this.name = name;
			this.expr = expr;
			values = new TDoubleArrayList(distributions);
		}
	}
	
}
