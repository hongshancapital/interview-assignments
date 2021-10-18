package com.jerry.domainservice.api.service.util;

/**
 * Provides the utility function for number.
 * @author jerry
 *
 */
public final class NumberUtils {
	
	private NumberUtils() {}
	
	private static char[] array = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
	
	public static int MAX_RADIX = 62;
	
	public static int MIN_RADIX = 2;
	
	static class InvalidRadixException extends RuntimeException{

		/**
		 * 
		 */
		private static final long serialVersionUID = -3450431798764995166L;
		
	}

	/**
	 * Returns a string representation of the first argument in the
     * radix specified by the second argument.
	 * @param number
	 * @param radix
	 * @return
	 */
	public static String convert(long number, int radix) {
		
		if(radix<MIN_RADIX || radix>MAX_RADIX) {
			throw new InvalidRadixException();
		}
		
		StringBuilder result = new StringBuilder();
		while (number > 0) {
			result.insert(0, array[(int) (number % radix)]);
			number /= radix;
		}
		
		return result.toString();
	}
}
