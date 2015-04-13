package testing.politics;

import expr.Expr;
import expr.Variable;

public class Neuron {
	public final Expr expr;
	public final String name;
	public double value;
	public final Variable variable;
	
	public Neuron(String name, Expr expr) {
		this.expr = expr;
		this.name = name;
		variable = Variable.make(name);
	}
	
	public String toString() {
		return name + " " + value;
	}
}