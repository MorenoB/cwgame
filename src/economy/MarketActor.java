package economy;

public class MarketActor {
	private final CurrencyTable funds = new CurrencyTable();
	private final ResourceTable monthlyBuyRequests = new ResourceTable();
	private final ResourceTable monthlySellRequests = new ResourceTable();
	private final ResourceTable resources = new ResourceTable();
	
	public CurrencyTable getFunds() {
		return funds;
	}
	
	public ResourceTable getMonthlyBuyRequests() {
		return monthlyBuyRequests;
	}
	
	public ResourceTable getMonthlySellRequests() {
		return monthlySellRequests;
	}
	
	public ResourceTable getResources() {
		return resources;
	}
}
