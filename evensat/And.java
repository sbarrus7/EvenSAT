package evensat;

public class And extends Operator {
	
	public And(Term... terms) {
		super(terms);
	}

	@Override
	public String opString() {
		return ".";
	}

	@Override
	protected Operator create() {
		return new And();
	}

	@Override
	protected Const drop() {
		return Const.TRUE;
	}

	@Override
	protected Const promote() {
		return Const.FALSE;
	}

	@Override
	public boolean sameType(Term t) {
		return (t instanceof And);
	}

}
