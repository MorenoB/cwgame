package diplomacy;

/**
 * A <code>BilateralRelation</code> represents the bilateral relations between two
 * countries, such as military alliances and non-aggression pacts. It contains utility
 * functions to easily manipulate the state of bilateral diplomatic relations between
 * the two countries.
 * @author nastyasalways
 *
 */
public class BilateralRelation {
	private final Country a;
	private final boolean alliance = false;
	private boolean atWar = false;
	private final Country b;
	private final boolean mutualDefense = false;
	private final boolean nonAggressionPact = false;
	
	/**
	 * Creates a new object representing bilateral relations between two countries. Which
	 * country is <code>a</code> and which is <code>b</code> doesn't matter.
	 * @param a The first country to represent.
	 * @param b The second country to represent.
	 */
	public BilateralRelation(Country a, Country b) {
		this.a = a;
		this.b = b;
	}
	
	/**
	 * Creates a state of war between the two countries.
	 * @param declarer The country that declares the war.
	 */
	public void declareWar(Country declarer) {
		Country other = (declarer == a) ? b : a;
		if(declarer.getRelations().getUnilateral(other).canInteractYet()) {
			atWar = true;
		}
	}
	
	/**
	 * 
	 * @return True if these two countries are at war.
	 */
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