package evensat;

import java.util.Collections;
import java.util.Map;

public class Const extends Term {

	public static final Const TRUE = new Const();
	public static final Const FALSE = new Const();
	
	private Const() { }
	
	@Override
	public int size() {
		return 0;
	}

	@Override
	public Map<Literal, Integer> getPositives() {
		return Collections.emptyMap();
	}

	@Override
	public Term set(Literal l, boolean val) {
		return this;
	}
	
	@Override
	public String toString() {
		if (this == TRUE) {
			return "TRUE";
		} else if (this == FALSE) {
			return "FALSE";
		}
		return "UNKNOWN";
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
	public Term even(Literal l) {
		return this;
	}

	@Override
	public boolean sameType(Term t) {
		return (t instanceof Const);
	}

	@Override
	public boolean absorbedBy(Term t) {
		return (t == this);
	}

	@Override
	public int depth() {
		return 1;
	}

}
