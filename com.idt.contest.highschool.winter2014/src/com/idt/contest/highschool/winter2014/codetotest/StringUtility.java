package com.idt.contest.highschool.winter2014.codetotest;

import org.ohs1.winter2013.BuiltInTester;

import com.idt.contest.highschool.winter2014.framework.FrameworkConstants;

/**
 * Class containing String related utility methods
 */
public class StringUtility {

	
	/**
	 * Method that counts the number of vowels in a String
	 * @param stringToCheck - String to count vowels in
	 * @return - int number of vowels in supplied String
	 */
	public int countVowels(String stringToCheck) {
		BuiltInTester.expecting("return 0", stringToCheck, "bcd fgh jkl mnp qrs tvw xyz");
		BuiltInTester.expecting("return 3", stringToCheck, "PROGRAMMING");
		BuiltInTester.expecting("return 13", stringToCheck, "This is a test of the emergency broadcast system");
		
		int vowelCount = 0;
		char currentChar;
		//
		//
		//
		//
		//
		//
		//BUG below... the vowelArray contains zero instead of capital O.
		//
		//
		//
		//
		//
		//
		//
		char[] vowelArray = {'a','A','e','E','i','I','o','0','u','U'};
		
		// must check string for null before processing 
		if (stringToCheck == null) {
			return vowelCount;
		}
		
		// iterate through each character of the string
		for (int i = 0; i < stringToCheck.length(); i++) {
			currentChar = stringToCheck.charAt(i);
			
			// check if the current character is in the vowelArray
			for (char vowel: vowelArray) {
				if (currentChar == vowel) {
					vowelCount++;
				}
			}
		}
		
		BuiltInTester.log("return " + vowelCount);
		return vowelCount;
	}
	
	
	/**
	 * Method to return the 2's compliment of a binary String
	 * @param binaryString - binary string to convert to 2's compliment
	 * 						 binary string must be positive and only contain 1s and 0s
	 * @return - String representation 2's compliment of binary string
	 */
	public String binaryByteTwosCompliment(String binaryByteString) {
		BuiltInTester.expecting("return 11001101", binaryByteString, "00110011");
		BuiltInTester.expecting("return 00000000", binaryByteString, "00000000");
		BuiltInTester.expecting("return 10000001", binaryByteString, "01111111");
	
		String binaryRepresentation = "";
		char currentChar;
		char tempDigit;
		int carryOver = 0;
		
		// if the binary byte string is null or empty, return an error string
		if (binaryByteString == null || binaryByteString.isEmpty()) {
			BuiltInTester.log("Binary representation error");
			return FrameworkConstants.BINARY_REPRESENTATION_ERROR;
		}
		
		// pad the binary string to be 8 characters long
		while (binaryByteString.length() < FrameworkConstants.BITS_IN_BYTE) {
			binaryByteString = FrameworkConstants.ZERO_STRING + binaryByteString;
		}
		
		// process binary string from back to front
		for (int i = binaryByteString.length()-1; i >= 0; i--) {
			currentChar = binaryByteString.charAt(i);
			
			// only handle strings that contain 1s or 0s
			if (currentChar == '0' || currentChar == '1') {
				
				// flip the digit
				if (currentChar == '0') {
					tempDigit = '1';
				} else {
					tempDigit = '0';
				}
				
				// if this is the first iteration or if carry over exists, add 1
				if (i == binaryByteString.length() - 1 || carryOver == 1) {
					if (tempDigit == '0') {
						tempDigit = '1';
						carryOver = 0;
					} else {
						tempDigit = '0';
						carryOver = 1;
					}
				}
				
				// add the determined bit to the binary representation of 2's compliment
				binaryRepresentation = tempDigit + binaryRepresentation;
				
			} else {
				// binary byte string contained a non 1 or 0 character, return an error string
				return FrameworkConstants.BINARY_REPRESENTATION_ERROR;
			}
		}
		
		BuiltInTester.log("return " + formatBinaryByteString(binaryRepresentation));
		return formatBinaryByteString(binaryRepresentation);
	}
	
	
	/**
	 * Method to format a binary byte string to 8 characters
	 * @param binaryByteString - binary byte string to format
	 * @return - String version of binary byte string with 8 characters
	 */
	public String formatBinaryByteString(String binaryByteString) {
		BuiltInTester.expecting("return 00000101", binaryByteString, "0101");
		BuiltInTester.expecting("return 00000101", binaryByteString, "000000000000000101");
		BuiltInTester.expecting("return 00000001", binaryByteString, "1");
		
		//  handle null pointers by returning an error string
		if (binaryByteString == null) {
			BuiltInTester.log("Binary representation error");
			return FrameworkConstants.BINARY_REPRESENTATION_ERROR;
		}
		
		if (binaryByteString != null && binaryByteString.length() < FrameworkConstants.BITS_IN_BYTE) {
			// pad the binary string to be 8 characters long
			while (binaryByteString.length() < FrameworkConstants.BITS_IN_BYTE) {
				binaryByteString = FrameworkConstants.ZERO_STRING + binaryByteString;
			}
		} else {
			// cut the binary string to be 8 characters long
			binaryByteString = binaryByteString.substring(binaryByteString.length() - FrameworkConstants.BITS_IN_BYTE);
		}
		
		BuiltInTester.log("return " + binaryByteString);
		return binaryByteString;
	}
	
	
	/**
	 * Method to find the index of the first location a specific character appears in a String
	 * @param stringToCheck - String to check for specific character
	 * @param charToLookFor - Specific character to look for inside of stringToCheck
	 * @return - int index of first instance of char found in string, -1 if char is never found
	 */
	public int indexOfFirstSpecificChar(String stringToCheck, char charToLookFor) {
		BuiltInTester.expecting("return 2", stringToCheck, "hello", charToLookFor, 'l');
		BuiltInTester.expecting("return 0", stringToCheck, "hello", charToLookFor, 'h');
		BuiltInTester.expecting("Invalid value", stringToCheck, "hello", charToLookFor, 'z');
		
		if (stringToCheck == null) {
			BuiltInTester.log("Invalid value");
			return FrameworkConstants.INVALID_VALUE;
		}
		
		for (int i = 0; i < stringToCheck.length(); i++) {
			if (stringToCheck.charAt(i) == charToLookFor) {
				BuiltInTester.log("return " + i);
				return i;
			}
		}
		
		BuiltInTester.log("Invalid value");
		return FrameworkConstants.INVALID_VALUE;
	}
	
	
	/**
	 * Method to find the index of the last location a specific character appears in a String
	 * @param stringToCheck - String to check for specific character
	 * @param charToLookFor - Specific character to look for inside of stringToCheck
	 * @return - int index of last instance of char found in string, -1 if char is never found
	 */
	public int indexOfLastSpecificChar(String stringToCheck, char charToLookFor) {
		BuiltInTester.expecting("return 3", stringToCheck, "hello", charToLookFor, 'l');
		BuiltInTester.expecting("return 0", stringToCheck, "hello", charToLookFor, 'h');
		BuiltInTester.expecting("Invalid value", stringToCheck, "hello", charToLookFor, 'z');
		
		if (stringToCheck == null) {
			BuiltInTester.log("Invalid value");
			return FrameworkConstants.INVALID_VALUE;
		}
		
		for (int i = stringToCheck.length() - 1; i >= 0; i--) {
			if (stringToCheck.charAt(i) == charToLookFor) {
				BuiltInTester.log("return " + i);
				return i;
			}
		}
		
		BuiltInTester.log("Invalid value");
		return FrameworkConstants.INVALID_VALUE;
	}
}
