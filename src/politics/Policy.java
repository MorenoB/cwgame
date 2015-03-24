package politics;

import docs.SGMLObject;
import expr.Expr;
import expr.SyntaxException;
import expr.Variable;

public class Policy {
	private final Variable implementation;
	private final String internalName;
	private final String name;
	private final Expr price;
	
	public Policy(SGMLObject object, GlobalPoliticalContext context) {
		SGMLObject info = object.getChild("policy");
		name = info.getField("name");
		internalName = info.getField("internal");
		String priceExpr = info.getField("price");
		Expr priceExprObj = null;
		try {
			if(priceExpr != null) {
				priceExprObj = context.getParser().parseString(priceExpr);
			}
		} catch(SyntaxException e) {
			e.printStackTrace();
		}
		price = priceExprObj;
		implementation = null;
		// implementation = Variable.make(internalName);
	}
	
	public String getInternalName() {
		return internalName;
	}
	
	public String getName() {
		return name;
	}
}
