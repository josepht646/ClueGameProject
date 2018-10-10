package clueGame;

import java.io.PrintWriter;

/**
 * BadConfigFormatException flags an error in file parsing.
 * @author Joseph Thurston
 * @author Thomas Depke
 *
 */
public class BadConfigFormatException extends Exception {
	/**
	 * Default constructor writes to log file.
	 */
	public BadConfigFormatException() {
		super("Configuration File is not in the correct format.");
		try {
			PrintWriter outFile = new PrintWriter("logfile.txt");
			outFile.println("Configuration File is not in the correct format.");
			outFile.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * Parameterized constructor writes to log file with a more descriptive message.
	 * @param name
	 */
	public BadConfigFormatException(String name) {
		super(name + " is not correctly formatted.");
		try {
			PrintWriter outFile = new PrintWriter("logfile.txt");
			outFile.println(name + " is not correctly formatted.");
			outFile.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
