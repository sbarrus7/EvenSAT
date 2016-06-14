package evensat;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public abstract class Operator extends Term {
	
	private LinkedHashSet<Term> terms;
	
	public Operator(Term... terms) {
		this.terms = new LinkedHashSet<Term>();
		for (Term t : terms) {
			add(t);
		}
	}
	
	protected void add(Term t) {
		if (sameType(t)) {
			terms.addAll(((Operator) t).terms);
			return;
		}
		terms.add(t);
	}
	
	public boolean absorbedBy(Term t) {
		if (t instanceof Operator) {
			return terms.containsAll(((Operator) t).terms);
		}
		return terms.contains(t);
	}
	
	private void absorbAll() {
		for (Iterator<Term> j = terms.iterator(); j.hasNext(); ) {
			Term t = j.next();
			if (absorbed(t)) {
				j.remove();
			}
		}
	}
	
	private boolean absorbed(Term t) {
		for (Iterator<Term> i = terms.iterator(); i.hasNext(); ) {
			Term a = i.next();
			if (a.size() < t.size()) {
				if (t.absorbedBy(a)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public abstract String opString();
	
	public Map<Literal, Integer> getPositives() {
		LinkedHashMap<Literal, Integer> s = new LinkedHashMap<Literal, Integer>();
		for (Term t : terms) {
			Map<Literal, Integer> m = t.getPositives();
			for (Entry<Literal, Integer> e : m.entrySet()) {
				Integer i = s.get(e.getKey());
				if (i != null) {
					s.put(e.getKey(), i + e.getValue());
				} else {
					s.put(e.getKey(), e.getValue());
				}
			}
		}
		return s;
	}
	
	protected abstract Const promote();
	
	protected abstract Const drop();
	
	protected abstract Operator create();
	
	public Term even(Literal l) {
		LinkedHashSet<Term> changes = new LinkedHashSet<Term>();
		Operator newOp1 = create();
		for (Iterator<Term> i = terms.iterator(); i.hasNext(); ) {
			Term t = i.next();
			Term rt = t.set(l, true);
			if (rt != t) {
				newOp1.add(rt);
				changes.add(t);
				i.remove();
			}
		}
		Operator newOp2 = create();
		for (Term t : changes) {
			Term rt = t.set(l, false);
			newOp2.add(rt);
		}
		
		Term even = new Or(newOp1.simplify(), newOp2.simplify());
		add(even.simplify());
		return simplify();
	}
	
	public Term set(Literal l, boolean val) {
		Const promote = promote();
		Const drop = drop();
		Operator newOp = create();
		boolean changed = false;
		
		for (Term t : terms) {
			Term rt = t.set(l, val);
			if (rt == promote) {
				return promote;
			}
			if (t != rt) {
				changed = true;
			}
			if (rt != drop) {
				newOp.add(rt);
			}
		}
		
		if (!changed) {
			return this;
		} else if (newOp.isEmpty()) {
			return drop;
		} else if (newOp.size() == 1) {
			return newOp.getFirst();
		}
		return newOp;
	}
	
	private boolean equalTerms(Operator op) {
		return (op == this) ? true : terms.equals(op.terms);
	}
	
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		} else if (o instanceof Operator) {
			return (sameType((Operator) o) && equalTerms((Operator) o));
		}
		return false;
	}
	
	public int hashCode() {
		return terms.hashCode();
	}
	
	public Term simplify() {
		if (terms.isEmpty()) {
			throw new IllegalArgumentException("Cannot simplify an empty operator.");
		}
		Const drop = drop();
		Const promote = promote();
		for (Iterator<Term> i = terms.iterator(); i.hasNext(); ) {
			Term t = i.next();
			if (t == promote) {
				return promote;
			} else if (t == drop) {
				i.remove();
			}
		}
		if (terms.isEmpty()) {
			return drop;
		} else if (terms.size() == 1) {
			return getFirst();
		}
		return this;
	}
	
	private Term getFirst() {
		for (Term t : terms) {
			return t;
		}
		return this;
	}
	
	public int treeSize() {
		int i = 1;
		for (Term t : terms) {
			i += t.treeSize();
		}
		return i;
	}
	
	public int size() {
		return terms.size();
	}
	
	public boolean isEmpty() {
		return terms.isEmpty();
	}
	
	@Override
	public int depth() {
		int depth = 0;
		for (Term t : terms) {
			int d = t.depth();
			if (d > depth) {
				depth = d;
			}
		}
		return (depth + 1);
	}
	
	public String toString() {
		String s = "";
		for (Iterator<Term> i = terms.iterator(); i.hasNext(); ) {
			s += i.next();
			if (i.hasNext()) {
				s += opString();
			}
		}
		return "(" + s + ")";
	}
	
}
