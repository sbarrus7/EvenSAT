package evensat;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

public class EvenSat {
	
	private And cnf;
	
	public EvenSat(And cnf) {
		this.cnf = cnf;
	}
	
	private class EntVal implements Comparator<Entry<Literal, Integer>> {
		public int compare(Entry<Literal, Integer> o1, Entry<Literal, Integer> o2) {
			return o1.getValue().compareTo(o2.getValue());
		}
	}
	
	public boolean isSat() {
		LinkedList<Entry<Literal, Integer>> pos = new LinkedList<Entry<Literal, Integer>>(cnf.getPositives().entrySet());
		Term eq = cnf;
		System.out.printf("%d %d %d\n", pos.size(), eq.size(), eq.treeSize());
		int count = 0;
		Collections.sort(pos, new EntVal());
		for (Entry<Literal, Integer> e : pos) {
			count++;
			eq = eq.even(e.getKey());
			System.out.printf("%4d %4d %10d\n", count, eq.depth(), eq.treeSize());
			if (eq == Const.TRUE) {
				return true;
			} else if (eq == Const.FALSE) {
				return false;
			}
		}
		throw new IllegalStateException("Unable to solve given CNF.");
	}
	
	@Override
	public String toString() {
		return cnf.toString();
	}

}
