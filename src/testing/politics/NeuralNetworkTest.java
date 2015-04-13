package testing.politics;

public class NeuralNetworkTest {
	public static void main(String[] args) {
		NeuralNetworkImpl nn = new NeuralNetworkImpl();
		nn.newNeuron("A", "2");
		nn.newNeuron("B", "A + 2");
		nn.newNeuron("C", "A ^ 2");
		nn.newNeuron("D", "A * B ^ C");
		nn.newNeuron("Sum", "A + B + C + D");
		nn.newNeuron("SumDivTwo", "A + B + C + D / 2");
		nn.newNeuron("RulesTest", "8 + 5 + 2 / 2");
		nn.newNeuron("RulesTest2", "8 * 3 ^ 4");
		nn.update();
		nn.print();
	}
}
