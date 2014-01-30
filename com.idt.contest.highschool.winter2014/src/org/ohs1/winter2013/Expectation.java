package org.ohs1.winter2013;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Work in progress expectation class, mainly just to experiment with how
 * the class will be structured. Not much to see here yet.
 * 
 * I can't really see any reason to make this class public, seeing as its only use is to
 * store information for BuiltInTester
 *	
 * @author Kostyantyn Proskuryakov, Ian Johnson
 * @version 0.2, 31 Dec 2013
 */
class Expectation {
	/**
	 * The name of the method the expectation is associated with
	 */
	private final String methodName;
	/**
	 * The log message the expectation is expecting
	 */
	private final String expectedLog;
	/**
	 * A list of all the parameter values the expectation was predicated on
	 */
	private final List<?> parameters;
	
	
	/**
	 * @param methodName the name of the method associated with the expectation
	 * @param expectedLog the expected log message
	 * @param parameters the parameter values associated with the expectation
	 */
	public Expectation(String methodName, String expectedLog, List<?> parameters) {
		this.methodName = methodName;
		this.expectedLog = expectedLog;
		this.parameters = new ArrayList<>(parameters);
	}
	
	/**
	 * Gets the method name associated with this expectation
	 * 
	 * @return the method name associated with this expectation
	 */
	public String getMethodName() {
		return methodName;
	}
	
	/**
	 * Gets the expected log message for this expectation
	 * 
	 * @return the expected log message for this expectation
	 */
	public String getExpectedLog() {
		return expectedLog;
	}
	
	/**
	 * Gets a string representing this expectation's parameters with the intention of using
	 * it in a readable output file. Currently, the format of the output will be (obj, obj, ...).
	 * 
	 * @return a string with this expectation's parameters
	 */
	public String getParameterString() {
		String params = "(";
		//Adds each parameter and a comma to the string, up to the last element (which has no comma)
		for (int i = 0; i < parameters.size() - 1; i++) {
			params += paramToString(parameters.get(i)) + ", ";
		}
		//Last parameter and final closing parenthesis
		params += paramToString(parameters.get(parameters.size() - 1)) + ")";
		
		return params;
	}
	
	//Quick and dirty toString for testing purposes
	public String toString() {
		return "Method name: " + methodName + 
			   "  expected log: " + expectedLog + 
			   "  params: " + getParameterString();
	}
	
	/**
	 * A method to convert the parameter into a string, taking into account the possibility
	 * of it being an array. Right now this only does int arrays because that's the minimum requirement,
	 * but there's no reason why it can't be extended.
	 * 
	 * @param param the parameter
	 * @return a string representing the parameter
	 */
	private String paramToString(Object param) {
		if (param instanceof int[])
			return Arrays.toString((int[])param);
		else
			return param.toString();
	}
}
