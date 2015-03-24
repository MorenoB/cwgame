package politics;

import docs.SGMLObject;

public class Simulation {
	private final String internalName;
	private double maxDecimalValue;
	private long maxIntegerValue;
	private double minDecimalValue;
	private long minIntegerValue;
	private final String name;
	private final ValueType type;
	
	public Simulation(SGMLObject object) {
		final SGMLObject info = object.getChild("simulation");
		name = info.getField("name");
		internalName = info.getField("internal");
		
		SGMLObject rep = info.getChild("representation");
		type = ValueType.valueOf(rep.getField("type"));
		switch(type) {
			case BOUNDED_PERCENTAGE:
			case BOUNDED_DECIMAL:
				initializeValues(rep.getDouble("upperBound"), rep.getDouble("lowerBound"), 0l, 0l);
				break;
			case BOUNDED_PERCENTAGE_0_AND_UP:
			case BOUNDED_DECIMAL_0_AND_UP:
				initializeValues(rep.getDouble("upperBound"), 0d, 0l, 0l);
				break;
			case BOUNDED_DECIMAL_0_TO_MAX:
				initializeValues(Double.MAX_VALUE, 0d, 0l, 0l);
				break;
			case BOUNDED_INTEGER:
				initializeValues(0d, 0d, rep.getLong("upperBound"), rep.getLong("lowerBound"));
				break;
			case BOUNDED_INTEGER_0_AND_UP:
				initializeValues(0d, 0d, rep.getLong("upperBound"), 0l);
				break;
			case BOUNDED_INTEGER_0_TO_MAX:
				initializeValues(0d, 0d, Long.MAX_VALUE, 0l);
				break;
			case REGULAR_PERCENTAGE:
				initializeValues(1f, 0f, 0l, 0l);
				break;
			case UNBOUNDED_PERCENTAGE:
			case UNBOUNDED_DECIMAL:
				initializeValues(Double.MAX_VALUE, Double.MIN_VALUE, 0l, 0l);
				break;
			case UNBOUNDED_INTEGER:
				initializeValues(0d, 0d, Long.MAX_VALUE, Long.MIN_VALUE);
				break;
		}
	}
	
	public String getInternalName() {
		return internalName;
	}
	
	public double getMaxDecimalValue() {
		return maxDecimalValue;
	}
	
	public String getMaximumValueString() {
		switch(type) {
			case BOUNDED_DECIMAL:
			case BOUNDED_DECIMAL_0_AND_UP:
			case BOUNDED_DECIMAL_0_TO_MAX:
			case UNBOUNDED_DECIMAL:
			case UNBOUNDED_PERCENTAGE:
				return "" + maxDecimalValue;
			case BOUNDED_INTEGER:
			case BOUNDED_INTEGER_0_AND_UP:
			case BOUNDED_INTEGER_0_TO_MAX:
			case UNBOUNDED_INTEGER:
				return "" + maxIntegerValue;
			case BOUNDED_PERCENTAGE:
			case BOUNDED_PERCENTAGE_0_AND_UP:
			case REGULAR_PERCENTAGE:
				return (maxDecimalValue * 100d) + "%";
			default:
				return "HEY!";
		}
	}
	
	public long getMaxIntegerValue() {
		return maxIntegerValue;
	}
	
	public double getMinDecimalValue() {
		return minDecimalValue;
	}
	
	public String getMinimumValueString() {
		switch(type) {
			case BOUNDED_DECIMAL:
			case BOUNDED_DECIMAL_0_AND_UP:
			case BOUNDED_DECIMAL_0_TO_MAX:
			case UNBOUNDED_DECIMAL:
			case UNBOUNDED_PERCENTAGE:
				return "" + minDecimalValue;
			case BOUNDED_INTEGER:
			case BOUNDED_INTEGER_0_AND_UP:
			case BOUNDED_INTEGER_0_TO_MAX:
			case UNBOUNDED_INTEGER:
				return "" + minIntegerValue;
			case BOUNDED_PERCENTAGE:
			case BOUNDED_PERCENTAGE_0_AND_UP:
			case REGULAR_PERCENTAGE:
				return (minDecimalValue * 100d) + "%";
		}
		
		return null;
	}
	
	public long getMinIntegerValue() {
		return minIntegerValue;
	}
	
	public String getName() {
		return name;
	}
	
	public ValueType getType() {
		return type;
	}
	
	private void initializeValues(double maxDec, double minDec, long maxInt, long minInt) {
		maxDecimalValue = maxDec;
		minDecimalValue = minDec;
		maxIntegerValue = maxInt;
		minIntegerValue = minInt;
	}
	
	public static enum GenerationMethodType {
		INSTANCE,
		SINGLETON,
		STATIC
	}
	
	public static enum ValueType {
		BOUNDED_DECIMAL,
		BOUNDED_DECIMAL_0_AND_UP,
		BOUNDED_DECIMAL_0_TO_MAX,
		BOUNDED_INTEGER,
		BOUNDED_INTEGER_0_AND_UP,
		BOUNDED_INTEGER_0_TO_MAX,
		BOUNDED_PERCENTAGE,
		BOUNDED_PERCENTAGE_0_AND_UP,
		REGULAR_PERCENTAGE,
		UNBOUNDED_DECIMAL,
		UNBOUNDED_INTEGER,
		UNBOUNDED_PERCENTAGE,
	}
}
