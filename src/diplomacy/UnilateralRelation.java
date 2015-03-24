package diplomacy;

import game.GameContext;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Months;

public class UnilateralRelation {
	private static List<OpinionModifier> switchList = new ArrayList<>();
	
	private final Country a;
	private final Country b;
	private Date lastInteractionDate = null;
	private boolean militaryAccess;
	private boolean navalBaseAccess;
	private List<OpinionModifier> opinionModifiers = new ArrayList<>();
	
	public UnilateralRelation(Country a, Country b) {
		this.a = a;
		this.b = b;
	}
	
	public void addOpinionModifier(String name, int modifier, int monthlyEffect, int terminationValue) {
		boolean done = false;
		
		for(OpinionModifier mod: opinionModifiers) {
			
			if(mod.name.equalsIgnoreCase(name)) {
				mod.modifier += modifier;
				done = true;
				break;
			}
		}
		
		if(!done) {
			opinionModifiers.add(new OpinionModifier(name, modifier, monthlyEffect, terminationValue));
		}
	}
	
	public void askForMilitaryAccess() {
		militaryAccess = true;
	}
	
	public boolean canInteractYet() {
		Date currentDate = GameContext.calendar.getTime();
		
		if(lastInteractionDate != null) {
			int timeBetween = Months.monthsBetween(new DateTime(lastInteractionDate),
					new DateTime(currentDate)).getMonths();
			
			if(timeBetween < 2)
				return false;
		}
		
		lastInteractionDate = currentDate;
		return true;
	}
	
	public int getOpinion() {
		if(opinionModifiers.size() == 0)
			return 0;
		
		int value = 0;
		for(OpinionModifier modifier: opinionModifiers) {
			value += modifier.getRemainingModifier();
			if(!modifier.isTerminable()) {
				switchList.add(modifier);
			}
		}
		
		List<OpinionModifier> placeholder = opinionModifiers;
		opinionModifiers = switchList;
		placeholder.clear();
		switchList = placeholder;
		
		return value;
	}
	
	public boolean hasMilitaryAccess() {
		return militaryAccess;
	}
	
	public void lowerRelations() {
		if(canInteractYet()) {
			addOpinionModifier("Lower relations", -15, 1, 0);
		}
	}
	
	public void offerMilitaryAccess(Country offerer) {
		Country other = (offerer == a) ? b : a;
		offerer.getRelations().getUnilateral(other).militaryAccess = true;
	}
	
	public void raiseRelations() {
		if(canInteractYet()) {
			addOpinionModifier("Raise relatiosn", 15, -1, 0);
		}
	}
}