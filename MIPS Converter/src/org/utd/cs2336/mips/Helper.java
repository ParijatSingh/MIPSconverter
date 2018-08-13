/**
 * UTD CS 2336
 * Assignment 4 - Spring 2016
 * @author Parijat Singh
 * email: parijat.sigh@gmail.com * 
 */

package org.utd.cs2336.mips;

public class Helper {
	
	public static String padFront(String txt, String padChar, int padLength) {
		if(txt.length() >= padLength) return txt;
		String res = String.format("%"+padLength+"s", txt).replace(" ", padChar);
		return res;
	}
	
	public String toHexString(String binaryString){
		int val = Integer.parseInt(binaryString, 2);
		return Integer.toHexString(val);
	}
	
	public String toBinaryString(String hexString){
		if(hexString.startsWith("0x")) hexString = hexString.replace("0x", "");
		//parsing with radix 16 (hex)
		int val = Integer.parseInt(hexString, 16);
		return Integer.toBinaryString(val);
	}
	
	/** Converts to binary format and pads with leading zeros 
	 * @param i
	 * @param paddedSize
	 * @return
	 */
	public String toBinaryString(int i, int paddedSize){
		String binaryString = Integer.toBinaryString(i);
		return padFront(binaryString, "0", paddedSize);
	}
	
	/** Converts to binary format and pads with leading zeros 
	 * @param i
	 * @param paddedSize
	 * @return
	 */
	public static String hexToBinary(String hex, int padLength) {
		if(hex.startsWith("0x")) {
			hex = hex.replace("0x", "");
		}
		long l = Long.parseLong(hex, 16);
		String binaryForm = Long.toBinaryString(l);
		binaryForm = Helper.padFront(binaryForm, "0", padLength);
		return binaryForm;
	}
	
	public static String binaryToHex(String binary, int padLength) {
		long l = Long.parseLong(binary, 2);
		String hexForm = Long.toHexString(l);
		hexForm = Helper.padFront(hexForm, "0", padLength);
		return "0x"+hexForm;
	}
	
	public static String intToBinary(int i, int padLength) {
		String binary = Integer.toBinaryString(i);
		return padFront(binary, "0", padLength);
	}
}
