package diplomacy;

import game.GameContext;

import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.Months;

public class OpinionModifier {
	public final Date creationDate;
	public int modifier;
	public final int monthlyEffect;
	public final String name;
	private boolean terminable = false;
	public final int terminationValue;
	
	public OpinionModifier(String name, int modifier, int monthlyEffect, int terminationValue) {
		this.name = name;
		this.modifier = modifier;
		this.monthlyEffect = monthlyEffect;
		this.terminationValue = terminationValue;
		creationDate = GameContext.calendar.getTime();
	}
	
	public int getRemainingModifier() {
		Date currentDate = GameContext.calendar.getTime();
		int timeBetween = Months.monthsBetween(new DateTime(creationDate), new DateTime(currentDate))
				.getMonths();
		int monthlyEffectModifier = timeBetween * monthlyEffect;
		int value = modifier + monthlyEffectModifier;
		if(value == terminationValue) {
			terminable = true;
		}
		
		return value;
	}
	
	public boolean isTerminable() {
		return terminable;
	}
}