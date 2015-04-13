package testing.politics;

public interface NeuralNetwork {
	public double getValue(String name);
	
	public void newNeuron(String name, String expr);
	
	public void update();
}
