package evensat;

import java.util.Map;

public abstract class Term {
	
	public abstract int size();
	
	public abstract int treeSize();
	
	public abstract Map<Literal, Integer> getPositives();
	
	public abstract Term set(Literal l, boolean val);
	
	public abstract Term simplify();
	
	public abstract Term even(Literal l);
	
	public abstract boolean sameType(Term t);
	
	public abstract boolean absorbedBy(Term t);
	
	public abstract int depth();
	
}
