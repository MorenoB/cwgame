package testing.politics;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import expr.Expr;
import expr.Parser;
import expr.SyntaxException;

public class NeuralNetworkImpl implements NeuralNetwork {
	public static boolean updateOnNewNeuron = true;
	private final Map<String, Neuron> neurons = new HashMap<>();
	private final Parser parser;
	
	public NeuralNetworkImpl() {
		parser = new Parser();
	}
	
	@Override
	public double getValue(String name) {
		return neurons.get(name).value;
	}
	
	public void newNeuron(String name, String expr) {
		try {
			Expr parsedExpr = parser.parseString(expr);
			Neuron newNeuron = new Neuron(name, parsedExpr);
			neurons.put(name, newNeuron);
			parser.allow(newNeuron.variable);
		} catch(SyntaxException e) {
			System.err.println(e.explain());
		}
		
		if(updateOnNewNeuron) {
			update();
		}
	}
	
	public void print() {
		for(Neuron neuron: neurons.values()) {
			System.out.println(neuron.toString());
		}
	}
	
	public void update() {
		Collection<Neuron> neuronVals = neurons.values();
		for(Neuron neuron: neuronVals) {
			neuron.value = neuron.expr.value();
			neuron.variable.setValue(neuron.value);
		}
	}
}
