package org.ohs1.winter2013;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Basic log entry class, we'll still need to implement methods here to export data into proper
 * HTML format for whatever sort of table we use
 * 
 * @author Kostyantyn Proskuryakov, Ian Johnson
 * @version 0.1, 31 Dec 2013
 */
class LogEntry {
	/**
	 * The date format to be used in formatting the timestamp
	 */
	private static final DateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy h:mm a", Locale.US);
	
	private final Expectation expectation;
	/**
	 * The actual message that was logged (as opposed to the expected message)
	 */
	private final String actualMessage;
	private final Date logDate;
	
	public LogEntry(String message, Expectation expectation) {
		this.actualMessage = message;
		this.expectation = expectation;
		this.logDate = new Date();
	}
	
	/**
	 * Returns whether the log entry passed or failed
	 * 
	 * @return whether the log entry represents a passed test
	 */
	public boolean didPass() {
		return actualMessage.equals(expectation.getExpectedLog());
	}
	
	/**
	 * 
	 * @param omitPassFail whether to omit the pass/fail column (for the failed-only table)
	 * @return a string containing the log data in the format of an HTML table row
	 */
	public String toTRString(boolean omitPassFail) {
		String trString = "<tr>";
		
		//Add date
		trString += "<td>" + dateFormat.format(logDate) + "</td>";
		//Add method name
		trString += "<td>" + expectation.getMethodName() + "</td>";
		//Add parameters
		trString += "<td>" + expectation.getParameterString() + "</td>";
		//Add expected log
		trString += "<td>" + expectation.getExpectedLog() + "</td>";
		//Add received log
		trString += "<td>" + actualMessage + "</td>";
		//Add pass/fail (if requested)
		if (!omitPassFail) {
			if (didPass())
				trString += "<td>Pass</td>";
			else
				trString += "<td>Fail</td>";
		}
		//Add closing tag
		trString += "</tr>";
		
		return trString;
	}
	
	//Not really going to be used in the final product, but it looks like the example in the
	//problem description so it's good for testing
	public String toString() {
		//If the test passed, output the sort of result from the problem description
		if (didPass()) {
			return dateFormat.format(logDate) +
				   expectation.getMethodName() +
				   " with input " + expectation.getParameterString() +
				   " PASSED with \"" + actualMessage + "\"";
		} else {
			return dateFormat.format(logDate) +
					   expectation.getMethodName() +
					   " with input " + expectation.getParameterString() +
					   " FAILED with \"" + actualMessage + "\"," +
					   " expected message was \"" + expectation.getExpectedLog() + "\"";
		}
	}
}
