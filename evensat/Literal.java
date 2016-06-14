package evensat;

import java.util.Collections;
import java.util.Map;

public class Literal extends Term {

	private int var;
	
	public Literal (int var) {
		this.var = var;
		if (var == 0) {
			throw new IllegalArgumentException("Literal cannot be zero.");
		}
	}

	@Override
	public int size() {
		return 1;
	}
	
	@Override
	public boolean equals(Object l) {
		if (l instanceof Literal) {
			return (var == ((Literal) l).var);
		}
		return false;
	}
	
	public boolean isOpposite(Literal l) {
		return (-var == l.var);
	}
	
	public Literal abs() {
		return (var < 0) ? new Literal(-var) : this;
	}
	
	public int hashCode() {
		return new Integer(var).hashCode();
	}
	
	@Override
	public Term even(Literal l) {
		if (equals(l) || isOpposite(l)) {
			return Const.TRUE;
		}
		return this;
	}
	
	@Override
	public Term set(Literal l, boolean val) {
		if (equals(l)) {
			return (val ? Const.TRUE : Const.FALSE);
		} else if (isOpposite(l)) {
			return (val ? Const.FALSE : Const.TRUE);
		}
		return this;
	}
	
	@Override
	public String toString() {
		return String.valueOf(var);
	}

	@Override
	public Map<Literal, Integer> getPositives() {
		return Collections.singletonMap(abs(), 1);
	}

	@Override
	public Term simplify() {
		return this;
	}

	@Override
	public int treeSize() {
		return 1;
	}

	@Override
	public boolean sameType(Term t) {
		return (t instanceof Literal);
	}

	@Override
	public boolean absorbedBy(Term t) {
		return equals(t);
	}

	@Override
	public int depth() {
		return 1;
	}
	
}
