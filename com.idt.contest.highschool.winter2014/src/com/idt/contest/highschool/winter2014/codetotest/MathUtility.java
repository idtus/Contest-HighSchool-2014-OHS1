package com.idt.contest.highschool.winter2014.codetotest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.ohs1.winter2013.BuiltInTester;

import com.idt.contest.highschool.winter2014.framework.FrameworkConstants;

/**
 * Class containing math related utility methods 
 */
public class MathUtility {
	
	
	/**
	 * Method that checks if a number is even
	 * @param numToCheck - the number to check 
	 * @return boolean - true if the number is even, false if the number is odd
	 */
	public boolean isEven(int numToCheck) {
		BuiltInTester.expecting("return true", numToCheck, 2);
		BuiltInTester.expecting("return false", numToCheck, 3);
		BuiltInTester.expecting("return true", numToCheck, 4);
		
		// divide the number by 2 and no remainder exists, the number is even
		if (numToCheck % 2 == 0) {
			BuiltInTester.log("return true");
			return true;
		} else {
			BuiltInTester.log("return false");
			return false;
		}
	}
	
	
	/**
	 * Method to compute the hypotenus of a triangle given two sides
	 * @param a - length of first side of triangle
	 * @param b - length of second side of triangle
	 * @return - integer value of the hypotenus of the triangle
	 */
	public double hypotenus(double a, double b) {
		BuiltInTester.expecting("return 5.0", a, 3.0, b, 4.0);
		BuiltInTester.expecting("return " + Math.sqrt(9.0*9.0 + 16.0*16.0), a, 9.0, b, 16.0);
		BuiltInTester.expecting("return " + Math.sqrt(100.0*100.0 + 200.0*200.0), a, 100.0, b, 200.0);
		
		double aSquared = a * a;
		double bSquared = b * b;
		double result = Math.sqrt(aSquared + bSquared); //added to prevent calculating result twice
		
		BuiltInTester.log("return " + result);
		return result;
	}
	
	
	/**
	 * Method that will return the prime factors of a given number
	 * @param n - number you wish to derive the prime factors of
	 * @return - array of ints that represent the prime factors
	 */
	public int[] primeFactor(int number) {
		BuiltInTester.expecting("[2, 2, 2]", number, 8);
		BuiltInTester.expecting("[2, 617]", number, 1234);
		BuiltInTester.expecting("[5, 7, 13, 29]", number, 13195);

		List<Integer> factors = new ArrayList<Integer>();
		int divider = 2;
		
		// determine the prime factors of the given number
		while (divider * divider <= number) {
			if (number % divider == 0) {
				factors.add(divider);
				number /= divider;
			} else {
				divider++;
			}
		}
		
		if (number > 1) {
			factors.add(number);
		}
		
		// convert Integer List to int array
		int[] returnArray = new int[factors.size()];
		for (int i = 0; i < factors.size(); i++) {
			returnArray[i] = factors.get(i);
		}
		
		// return int array of prime factors
		BuiltInTester.log(Arrays.toString(returnArray));
		return returnArray;
	}
	
	
	/**
	 * Method to multiple to simple binomials using the FOIL technique
	 * similar to (2x + 6) * (7x - 10)    
	 * @param x1, the multiplier of the first x (2 in the example above)
	 * @param ones1, the first ones value (6 in the example above)
	 * @param x2, the multiplier of the second x (7 in the example above)
	 * @param ones2, the second ones value (-10 in the example above)
	 * @return - String representation of binomial product
	 */
	public String multiplySimpleBinomials(short x1, short ones1, short x2, short ones2) {
		BuiltInTester.expecting("return x^2 + 5x + 6", x1, 1, ones1, 3, x2, 1, ones2, 2);
		BuiltInTester.expecting("return x^2 - 7x + 12", x1, 1, ones1, -4, x2, 1, ones2, -3);
		BuiltInTester.expecting("return -4x^2 + 4", x1, 2, ones1, 2, x2, -2, ones2, 2);
		BuiltInTester.expecting("return -12x^2 + 7x - 1", x1, -4, ones1, 1, x2, 3, ones2, -1);
		BuiltInTester.expecting("return 88x^2 + 49x + 6", x1, -11, ones1, -2, x2, -8, ones2, -3);
		
		boolean notTheFirst = false;
		String binomialResult = "";
		
		// follow foil procedure and multiply all combinations
		short first, outer, inner, last;
		first = (short) (x1 * x2);
		outer = (short) (x1 * ones2);
		inner = (short) (ones1 * x2);
		last = (short) (ones1 * ones2);
		
		// assemble binomial string
		if (first != 0) {
			if (Math.abs(first) != 1) {
				binomialResult += first;
			}
			binomialResult += "x^2 ";
			notTheFirst = true;
		}
		
		if (outer + inner != 0) {
			if (outer + inner > 0 && notTheFirst) {
				binomialResult += FrameworkConstants.POSITIVE_SIGN + " ";
			} else if (notTheFirst){
				binomialResult += FrameworkConstants.NEGATIVE_SIGN + " ";
			}
			
			if (Math.abs(outer + inner) != 1) {
				binomialResult += Math.abs(outer + inner);
			}
			binomialResult += "x ";
			notTheFirst = true;
		}
		
		if (last !=0) {
			//
			//
			//
			//
			//
			//
			// BUG below... the less than sign in the if statement should be a 
			// greater than sign. Because of this switch, a positive sign will 
			// show up in front of the ones place where a negative sign should 
			// show up, and vice versa. 
			//
			//
			//
			//
			//
			//
			if (last < 0 && notTheFirst) {
				binomialResult += FrameworkConstants.POSITIVE_SIGN + " ";
			} else if (notTheFirst){
				binomialResult += FrameworkConstants.NEGATIVE_SIGN + " ";
			}
			binomialResult += Math.abs(last);
		}
		
		BuiltInTester.log("return " + binomialResult);
		return binomialResult;
	}
	
	
	/**
	 * Method used to do simple currency conversions
	 * @param amount - float amount of money you start with
	 * @param rate - float exchange rate from start currency to finish currency
	 * @return - float amount of currency you finish with
	 */
	public float convertCurrency(float amount, float rate) {
		BuiltInTester.expecting("return 0.61", amount, 1.00, rate, 0.61);
		BuiltInTester.expecting("return 73.0", amount, 100.0, rate, 0.73);
		
		// if the amount is zero, the result will be zero
		// if the rate is zero, the result will be zero
		if (amount == 0 || rate == 0) {
			return 0;
		// if the rate is one, the amount will not be transformed
		} else if (rate == 1) {
			return amount;
		// otherwise we multiply the amount by the exchange rate
		} else {
			BuiltInTester.log("return " + amount*rate);
			return amount * rate;
		}
	}
	
}

