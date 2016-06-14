package evensat;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CnfParser {
	
	private String filename;
	private LineNumberReader reader;
	private And cnf;
	
	public CnfParser(String filename) {
		this.filename = filename;
		this.cnf = null;
		loadCnf();
	}
	
	public And getCnf() {
		return cnf;
	}
	
	private static final Pattern LINE_P_CNF = 
		Pattern.compile("\\s*p\\s+cnf\\s+(\\d+)\\s+(\\d+)\\s*");
	private static final Pattern LINE_CLS = 
		Pattern.compile("\\s*((-?\\d+\\s+)+)0\\s*");
	//private static final Pattern LINE_EOF =
	//	Pattern.compile("\\s*0\\s*");
	
	private void loadCnf() {
		try {
			reader = new LineNumberReader(new FileReader(filename));
		} catch (FileNotFoundException e) {
			printError("Unable to open cnf file: " + filename);
			return;
		}
		
		String line;
		Matcher matcher;
		do {
			line = getLine();
			if (line == null) {
				printError("Invalid cnf file: no 'p cnf' line");
				return;
			}
			matcher = LINE_P_CNF.matcher(line);
		} while (!matcher.matches());
		
		//int vars = Integer.valueOf(matcher.group(1));
		int cls = Integer.valueOf(matcher.group(2));
		
		cnf = new And();
		
		while ((line = getLine()) != null) {
			matcher = LINE_CLS.matcher(line);
			if (!matcher.matches()) {
				continue;
			}
			
			Or orOp = new Or();
			StringTokenizer st = new StringTokenizer(matcher.group(1));
			while (st.hasMoreTokens()) {
				orOp.add(new Literal(Integer.parseInt(st.nextToken())));
			}
			cnf.add(orOp);
		}
		
		if (cnf.size() != cls) {
			printError("Incorrect number of clauses.");
		}
		
		try {
			reader.close();
		} catch (IOException e) {}
	}
	
	private String getLine() {
		try {
			return reader.readLine();
		} catch (IOException e) {
			printError("Unable to read file: " + e.toString());
			return null;
		}
	}
	
	private void printError(String msg) {
		if (reader != null) {
			msg = (filename + ":" + reader.getLineNumber() + ": " + msg);
		}
		System.err.println(msg);
	}
}
