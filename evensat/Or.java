package evensat;

public class Or extends Operator {
	
	public Or(Term...terms) {
		super(terms);
	}

	@Override
	public String opString() {
		return "|";
	}

	@Override
	protected Operator create() {
		return new Or();
	}

	@Override
	protected Const drop() {
		return Const.FALSE;
	}

	@Override
	protected Const promote() {
		return Const.TRUE;
	}

	@Override
	public boolean sameType(Term t) {
		return (t instanceof Or);
	}

}
