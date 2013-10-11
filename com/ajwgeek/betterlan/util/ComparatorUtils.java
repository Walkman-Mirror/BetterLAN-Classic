package com.ajwgeek.betterlan.util;

public class ComparatorUtils
{
	public int compareStringLength(String par1String, String par2String)
	{
		if (par1String.length() > par2String.length())
		{
			return 1;
		}
		else if (par1String.length() < par2String.length())
		{
			return -1;
		}
		return par1String.compareTo(par2String);
	}
}