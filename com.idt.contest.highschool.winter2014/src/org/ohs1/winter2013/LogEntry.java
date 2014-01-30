package org.ohs1.winter2013;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Constitutes everything that each log entry in the html output file is.
 * 
 * @author Kostyantyn Proskuryakov, Ian Johnson
 * @version 1.1, 30 Jan 2014
 */
class LogEntry {

	// The date format to be used in formatting the timestamp
	private static final DateFormat dateFormat = new SimpleDateFormat(
			"MMM dd, yyyy h:mm a", Locale.US);

	// The expectation associated with the log entry
	private final Expectation expectation;

	// The actual message that was logged (as opposed to the expected message)
	private final String actualMessage;

	// The time this log entry was created, the time the method returned in the
	// main program
	private final Date logDate;

	/* 
	 * Message is the actual message that was logged and the expectation
	 * includes within it the expected message and the parameters. Records the
	 * time this instance was created for use later
	 */
	LogEntry(String message, Expectation expectation) {
		this.actualMessage = message;
		this.expectation = expectation;
		this.logDate = new Date();
	}

	// Returns whether the method's logic passed or failed
	boolean didPass() {
		return actualMessage.equals(expectation.getExpectedLog());
	}

	/*
	 * Returns a string containing the log data in the format of an HTML table
	 * row. omitPassFail returns a string omitting the pass/fail table entry for
	 * the fail only table
	 */
	String toTRString(boolean omitPassFail) {
		String trString = "<tr>";

		// Add date
		trString += "<td>" + dateFormat.format(logDate) + "</td>";
		// Add method name
		trString += "<td>" + expectation.getMethodName() + "</td>";
		// Add parameters
		trString += "<td>" + expectation.getParameterString() + "</td>";
		// Add expected log
		trString += "<td>" + expectation.getExpectedLog() + "</td>";
		// Add received log
		trString += "<td>" + actualMessage + "</td>";
		// Add pass/fail (if requested)
		if (!omitPassFail) {
			if (didPass())
				trString += "<td>Pass</td>";
			else
				trString += "<td>Fail</td>";
		}
		// Add closing tag
		trString += "</tr>";

		return trString;
	}
}
