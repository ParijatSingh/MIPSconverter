/**
 * UTD CS 2336
 * Assignment 4 - Spring 2016
 * @author Parijat Singh
 * email: parijat.sigh@gmail.com * 
 */

package org.utd.cs2336.mips;

public class Runner{

	public static void main(String[] args)
	{
		Converter converter = new Converter();
		converter.readTable();
		MainFrame mFrame = new MainFrame(converter);
	}
	
}
