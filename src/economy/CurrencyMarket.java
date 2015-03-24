package economy;

public class CurrencyMarket {
	private final double[] demand;
	private final double[] price;
	private final double[] supply;
	
	public CurrencyMarket() {
		int length = CurrencyTable.getCurrencyArrayLength();
		price = new double[length];
		demand = new double[length];
		supply = new double[length];
	}
}
