package diplomacy;


public class BilateralRelation {
	private final Country a;
	private final boolean alliance = false;
	private boolean atWar = false;
	private final Country b;
	private final boolean mutualDefense = false;
	private final boolean nonAggressionPact = false;
	
	public BilateralRelation(Country a, Country b) {
		this.a = a;
		this.b = b;
	}
	
	public void declareWar(Country declarer) {
		Country other = (declarer == a) ? b : a;
		if(declarer.getRelations().getUnilateral(other).canInteractYet()) {
			atWar = true;
		}
	}
	
	public boolean isAtWar() {
		return atWar;
	}
	
	public void proposePeace(Country proposer) {
		Country other = (proposer == a) ? b : a;
		if(proposer.getRelations().getUnilateral(other).canInteractYet()) {
			atWar = false;
		}
	}
}