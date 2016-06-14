/**
 * 
 */
package evensat;

/**
 * Main class
 */
public class Main {
	
	/**
	 * @param args File names of CNF files
	 */
	public static void main(String[] args) {
		if (args.length < 1) {
			System.out.println("No cnf file specified.");
			System.exit(1);
		}
		
		for (String filename : args) {
			And cnf = new CnfParser(filename).getCnf();
			if (cnf == null) {
				System.err.println("Failed to load file: " + filename);
				System.exit(2);
			}
			System.out.printf("%s: %d\n", filename, cnf.size());
			//System.out.println(cnf);
			EvenSat evenSat = new EvenSat(cnf);
			System.out.println(filename + ": " + (evenSat.isSat() ? "sat" : "NOT sat"));
		}
	}

}
