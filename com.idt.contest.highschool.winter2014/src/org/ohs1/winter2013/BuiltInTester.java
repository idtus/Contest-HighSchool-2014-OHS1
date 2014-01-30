package org.ohs1.winter2013;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * The main tester class (very WIP, we may want to changed the name also)
 * 
 * @author Kostyantyn Proskuryakov, Ian Johnson
 * @version 0.2, 31 Dec 2013
 */
public class BuiltInTester {

	private static BuiltInTester instance;

	// Used as the file directory for all output logs
	private String programName;

	// Used as the template name for all output logs, will appear as
	// outputFileName000.html where 000 are incremented with each new log file
	private String outputFileName;

	// The expectations currently being expected
	private List<Expectation> expectations;

	// A queue storing the log entries in the order that they were created
	private Queue<LogEntry> logEntries;

	public static void enable(String programName, String outputFileName) {
		instance = new BuiltInTester();
		instance.programName = programName;
		instance.outputFileName = outputFileName;
	}

	private BuiltInTester() {
		this.expectations = new ArrayList<>();
		this.logEntries = new LinkedList<>();
	}

	public static void expecting(String logMessage, Object... parameters) {
		if (instance != null) {
			instance.expectingInner(logMessage, parameters);
		}
	}

	/**
	 * Logs an expectation. I needed to support multiple conditions (some of the
	 * classes we have to test contain methods with more than one parameter), so
	 * I used varargs and the condition that the parameters must be written in
	 * pairs. This makes the syntax essentially the same as that given in the
	 * example in the problem description, except the log message comes first
	 * (which makes more sense, actually).
	 * 
	 * @param logMessage
	 *            the message to be expecting
	 * @param parameters
	 *            the conditions, written in pairs of parameter then value, for
	 *            the expectation
	 */
	private void expectingInner(String logMessage, Object... parameters) {
		// Make sure testing is enabled
		if (parameters.length % 2 != 0)
			throw new IllegalArgumentException(
					"Parameters must be written in pairs");

		// Stops if the expectation should not be logged (if some parameter
		// condition is not met)
		// Checks parameters in pairs
		for (int i = 0; i < parameters.length - 1; i += 2) {
			// If parameters are not equal, exit the method before adding the
			// expectation
			if (!parameterEquals(parameters[i], parameters[i + 1]))
				return;
		}

		// Get method name from stack trace
		String methodName = Thread.currentThread().getStackTrace()[3]
				.getMethodName();

		// Construct a list of the parameter values
		List<Object> params = new ArrayList<>();
		for (int i = 0; i < parameters.length; i += 2)
			params.add(parameters[i]);

		// Since the parameters have their desired values, log the expectation
		expectations.add(new Expectation(methodName, logMessage, params));
	}
	
	public static void log(String message) {
		if (instance != null) {
			instance.logInner(message);
		}
	}

	/**
	 * Right now this does nothing but clear the list of expectations for
	 * testing purposes
	 * 
	 * @param message
	 */
	public void logInner(String message) {
		// Evaluate the correctness of all expectations and add the
		// appropriate log entries
		for (Expectation e : expectations) {
			logEntries.add(new LogEntry(message, e));
		}

		// Clear the list of expectations
		expectations.clear();
	}
	
	public static void outputLog() {
		if (instance != null) {
			instance.outputLogInner();
		}
	}

	/**
	 * This will eventually be the method that outputs the HTML formatted log,
	 * but for now it just prints out a normal, boring summary of the log
	 * entries
	 */

	public void outputLogInner() {
			//Normal output code
			/*String output = "*****Testing results for " + programName + "*****\n\n";
			
			//Add all log entries, in order, to the output string
			while (!logEntries.isEmpty())
				output += logEntries.poll() + "\n";
			
			System.out.println(output);*/
			
			try {
				File f = createFile();
				System.out.println(f.getAbsolutePath());
				//Somewhere in here we need to copy the files that I've added myself to the folder
				BufferedWriter bw = new BufferedWriter(new FileWriter(f));
				writeHeader(bw);
				writeBody(bw);
				writeFooter(bw);
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	/**
	 * Only for testing purposes, returns all expectations logged in the system
	 * 
	 * @return
	 */
	static String testingString() {
		if (instance != null) {
			String s = "";
			for (Expectation e : instance.expectations)
				s += e.toString() + "\n";
			return s;
		} else {
			return "Not enabled\n";
		}
	}

	/**
	 * Checks if the two parameters are equal, taking into account the
	 * possibility of being an array
	 * 
	 * @param param1
	 * @param param2
	 * @return
	 */
	private boolean parameterEquals(Object param1, Object param2) {
		if (param1 == null || param2 == null)
			return false;
		
		//Take into account arrays (only does int[] for now)
		if (param1 instanceof int[] && param2 instanceof int[])
			return Arrays.equals((int[]) param1, (int[]) param2);
		else {
			//Allow for comparison of different numerical types
			if (param1 instanceof Number && param2 instanceof Number) {
				//Compare Long, Integer, Short, and Byte
				if ((param1 instanceof Long || param1 instanceof Integer ||
					 param1 instanceof Short || param1 instanceof Byte) &&
					(param2 instanceof Long || param2 instanceof Integer ||
					 param2 instanceof Short || param2 instanceof Byte))
					return ((Number)param1).longValue() == ((Number)param2).longValue();
				//Compare Double and Float
				else if ((param1 instanceof Double || param1 instanceof Float) &&
						 (param2 instanceof Double || param2 instanceof Float))
					return ((Number)param1).doubleValue() == ((Number)param2).doubleValue();
				//Any other subclass of Number
				else
					return param1.equals(param2);
			} else
				return param1.equals(param2);
		}
	}

	/**
	 * Creates the html file to store log data.
	 * 
	 * @return The created empty log file in File object format
	 * @throws IOException
	 */
	private File createFile() throws IOException {
		File f;
		String path = programName + File.separator + outputFileName;
		DecimalFormat format = new DecimalFormat("000");
		int fileNum = 0;
		do {
			f = new File(path.substring(0, path.length() - 5)
					+ format.format(fileNum)
					+ path.substring(path.length() - 5));
			fileNum++;
		} while (f.exists());
		f.getParentFile().mkdirs();
		f.createNewFile();
		return f;
	}

	private void writeHeader(BufferedWriter bw) throws IOException {
		// First part of file
		bw.write("<html>\n<head>\n");
		//Stylesheet
		bw.write("<link rel=\"stylesheet\" href=\"../scripts/style.css\" type=\"text/css\" />\n");
		//External scripts
		bw.write("<script type=\"text/javascript\" src=\"../scripts/jquery-latest.js\"></script>\n");
		bw.write("<script type=\"text/javascript\" src=\"../scripts/jquery.tablesorter.min.js\"></script>\n");
		//Script for page load
		bw.write("<script type=\"text/javascript\">\n");
		bw.write("window.onload = function() {\n");
		bw.write("$(document).ready(function()\n"); 
    	bw.write("{$(\"#all\").tablesorter();$(\"#failed\").tablesorter();}\n"); 
    	bw.write(");}\n");
    	bw.write("</script>\n");
    	//End of header
    	bw.write("</head>\n");
	}
	
	//Date, method name, input, expected, received, pass/fail
	private void writeBody(BufferedWriter bw) throws IOException {
		//Body tag
		bw.write("<body>\n");
		//Header for file (program name)
		bw.write("<h1>Test results for " + programName + "</h1>\n");
		
		//Header for all tests
		bw.write("<h2>All tests</h2>\n");
		//Beginning of table
		bw.write("<table id=\"all\" class=\"tablesorter\">\n");
		//Table header with names of columns
		bw.write("<thead>\n<tr>\n");
		for (String header : new String[]{"Date", "Method name", "Input", "Expected log", "Received log", "Pass/fail"})
			bw.write("    <th>" + header + "</th>\n");
		bw.write("</tr>\n</thead>\n");
		
		//Body of first (all entries) table
		//This is a queue of all the failed log entries so they can be added to the other table as well
		Queue<LogEntry> failedEntries = new LinkedList<>();
		
		bw.write("<tbody>\n");
		while (!logEntries.isEmpty()) {
			LogEntry entry = logEntries.poll();
			//Add entry to failed queue if it failed
			if (!entry.didPass())
				failedEntries.add(entry);
			
			bw.write("    " + entry.toTRString(false) + "\n");
		}
		bw.write("</tbody>\n</table>\n");
		
		//Header for second table (failed tests)
		bw.write("<h2>Failed tests</h2>\n");
		//Beginning of table
		bw.write("<table id=\"failed\" class=\"tablesorter\">\n");
		//Table header with names of columns
		bw.write("<thead>\n<tr>\n");
		for (String header : new String[]{"Date", "Method name", "Input", "Expected log", "Received log"})
			bw.write("    <th>" + header + "</th>\n");
		bw.write("</tr>\n</thead>\n");
		//Body of second (failed entries) table
		bw.write("<tbody>\n");
		while (!failedEntries.isEmpty())
			bw.write("    " + failedEntries.poll().toTRString(true) + "\n");
		bw.write("</tbody>\n</table>\n");
		
		//End of body
		bw.write("</body>\n");
	}

	private void writeFooter(BufferedWriter bw) throws IOException {
		//This is sort of a pathetic method
		bw.write("</html>");
	}
}
