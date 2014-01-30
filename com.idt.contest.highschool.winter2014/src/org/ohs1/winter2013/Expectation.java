package org.ohs1.winter2013;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Constitutes the expected log message given the parameters.
 * 
 * @author Kostyantyn Proskuryakov, Ian Johnson
 * @version 1.1, 30 Jan 2014
 */
class Expectation {

	// The name of the method the expectation is associated with
	private final String methodName;

	// The log message the expectation is expecting
	private final String expectedLog;

	// A list of all the parameter values the expectation was predicated on
	private final List<?> parameters;

	/*
	 * Parameters: 
	 * methodName  - the name of the method associated with the expectation
	 * expectedLog - the expected log message
	 * parameters  - the parameter values associated with the expectation
	 */
	Expectation(String methodName, String expectedLog, List<?> parameters) {
		this.methodName = methodName;
		this.expectedLog = expectedLog;
		this.parameters = new ArrayList<>(parameters);
	}

	// Gets the method name associated with this expectation
	String getMethodName() {
		return methodName;
	}

	// Gets the expected log message for this expectation
	String getExpectedLog() {
		return expectedLog;
	}

	/* Gets a string representing this expectation's parameters with the
	* intention of using it in a readable output file. Currently, the format of
	* the output will be (obj, obj, ...)
	*/
	String getParameterString() {
		String params = "(";
		// Adds each parameter and a comma to the string, up to the last element
		// (which has no comma).
		for (int i = 0; i < parameters.size() - 1; i++) {
			params += paramToString(parameters.get(i)) + ", ";
		}
		// Last parameter and final closing parenthesis
		params += paramToString(parameters.get(parameters.size() - 1)) + ")";

		return params;
	}

	/* A method to convert the parameter into a string, taking into account the
     * possibility of it being an array
     */
	private String paramToString(Object param) {
		if (param instanceof int[])
			return Arrays.toString((int[]) param);
		else
			return param.toString();
	}
}
