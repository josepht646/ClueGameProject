package clueGame;

import java.io.PrintWriter;

/**
 * BadConfigFormatException flags an error in file parsing.
 * @author Joseph Thurston
 * @author Thomas Depke
 *
 */
public class BadConfigFormatException extends Exception {
	private static final long serialVersionUID = 1L;    // Exception serial version.
	
	/**
	 * Default constructor writes to log file.
	 */
	public BadConfigFormatException() {
		super("Configuration File is not in the correct format.");
		try {
			PrintWriter logFile = new PrintWriter("logfile.txt");    // Write the error to log file.
			logFile.println("Configuration File is not in the correct format.");
			logFile.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Parameterized constructor writes to log file with a more descriptive message.
	 * @param filename - Filename that triggers the exception
	 */
	public BadConfigFormatException(String filename) {
		super(filename + " is not correctly formatted.");
		try {
			PrintWriter logFile = new PrintWriter("logfile.txt");    // Write the error with filename to log file.
			logFile.println(filename + " is not correctly formatted.");
			logFile.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
