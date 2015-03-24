package politics;

public class LocalPolicy {
	private double currentImplPercent;
	private final double effectivenessPercent = 1d;
	private final Policy policy;
	private double targetImplPercent;
	
	public LocalPolicy(Policy policy) {
		this.policy = policy;
	}
	
	public boolean isImplemented() {
		return targetImplPercent == 0d;
	}
}
