package com.josbertlonnee;

public class VERIFY
{
	public static void equals(String valueName, Object value, Object expectedValue) throws RuntimeException
	{
    	if (!value.equals(expectedValue))
    		throw new RuntimeException("Unexpected " + valueName + ": " + value + " (instead of " + expectedValue + ")");
	}
}