package testing.politics;

public class DistributedNeuralNetworkTest {
	public static void main(String[] args) {
		DistributedNeuralNetwork dnn = new DistributedNeuralNetwork();
		dnn.newNeuron("GNP", "3000");
		int usDistr = dnn.addDistribution();
		int suDistr = dnn.addDistribution();
		dnn.setDistribution(usDistr);
		dnn.update();
		System.out.println("usDistr " + dnn.getValue("GNP"));
		dnn.setDistribution(suDistr);
		dnn.update();
		System.out.println("suDistr " + dnn.getValue("GNP"));
	}
}
