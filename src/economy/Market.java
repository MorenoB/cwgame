package economy;

public class Market {
	private final double[] demand;
	private double[] lastPrices;
	private final Currency localCurrency;
	private final double[] prices;
	private final double[] supply;
	
	public Market(Currency localCurrency) {
		this.localCurrency = localCurrency;
		int length = ResourceTable.getResourceArrayLength();
		demand = new double[length];
		prices = new double[length];
		supply = new double[length];
	}
	
	public Currency getLocalCurrency() {
		return localCurrency;
	}
	
	public void updatePrices() {
		lastPrices = prices;
	}
}
