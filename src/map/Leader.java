package map;

import docs.SGMLObject;

public class Leader {
	public final String name;
	public final String referral;
	public final int yearOfBirth;
	public final String nationality;
	public final String religion;
	public final String ideology;
	
	public Leader(SGMLObject data) {
		data = data.getChild("leader");
		name = data.getField("name");
		referral = data.getField("referral");
		yearOfBirth = data.getInt("born");
		nationality = data.getField("nationality");
		religion = data.getField("religion");
		ideology = data.getField("ideology");
	}
}
