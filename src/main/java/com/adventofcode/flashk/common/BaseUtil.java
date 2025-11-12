package com.adventofcode.flashk.common;

import module java.base;

public final class BaseUtil {

	private BaseUtil() {}
	
	public static Integer binaryToDecInteger(String bin) {
	    return Integer.parseInt(bin, 2);
	}
	
	public static Long binaryToDec(String bin) {
	    return Long.parseLong(bin, 2);
	}

	/// Converts from hexadecimal to binary.
	///
	/// Any hexadecimal number can be represented with 4 binary digits (`6 hex = 0110 bin`).
	///
	/// Leading zero digits won't be included in the converted number.
	/// Use [hexToBinaryPadLeft][#hexToBinaryPadLeft(String)] if they need to be included.
	///
	/// **Examples:**
	///
	/// ```java
	/// 	BaseUtil.hexToBinary("6") // returns "110"
	/// ```
	/// @param hex the hexadecimal code
	/// @return the translated binary code
	/// @see #hexToBinaryPadLeft(String)
	public static String hexToBinary(String hex) {
	    return new BigInteger(hex, 16).toString(2);
	}

	/// Converts from hexadecimal to binary and adds zero padding at the leading.
	///
	/// Any hexadecimal number can be represented with 4 binary digits (`6 hex = 0110 bin`).
	///
	/// Leading zero digits will be included in the converted number.
	/// Use [hexToBinary][#hexToBinary(String)] if they need to be removed from the result.
	///
	/// **Examples:**
	///
	/// ```java
	/// BaseUtil.hexToBinaryPadLeft("6"); // returns "0110"
	/// ```
	/// @param hex the hexadecimal code
	/// @return the translated binary code, it will always have a multiple of 4 digits.
	/// @see #hexToBinary(String)
	public static String hexToBinaryPadLeft(String hex) {
	    String unpaddedBinary = BaseUtil.hexToBinary(hex);
	    int size = hex.length() * 4;
		String formatSize = "%" + size + "s";
	    return String.format(formatSize, unpaddedBinary).replace(" ", "0");
	}
}
