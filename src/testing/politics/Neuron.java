package testing.politics;

import expr.Expr;
import expr.Parser;
import expr.SyntaxException;
import expr.Variable;

public class Neuron {
	private final Expr expression;
	private final String exprStr;
	private final String name;
	private final Variable variable;
	
	public Neuron(Parser parser, String name, String exprStr) {
		this.name = name;
		this.exprStr = exprStr;
		Expr tempExpr = null;
		
		try {
			tempExpr = parser.parseString(exprStr);
		} catch(SyntaxException e) {
			e.printStackTrace();
		}
		
		expression = tempExpr;
		variable = Variable.make(name);
		parser.allow(variable);
	}
	
	public String getExpression() {
		return exprStr;
	}
	
	public String getName() {
		return name;
	}
	
	public double getValue() {
		return variable.value();
	}
	
	public Variable getVariable() {
		return variable;
	}
	
	public void updateValue() {
		double newValue = expression.value();
		variable.setValue(newValue);
	}
}
