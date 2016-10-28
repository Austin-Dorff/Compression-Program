package core.engine.utilities;

public class StringUtils
{
	
	
	public static String removeAllFromString(String orig, String rem)
	{
		String newString = orig;
		int index = newString.indexOf(rem);
		while (index != -1)
		{
			String tempString = new String();
			tempString += newString.substring(0, index);
			tempString += newString.substring(index + rem.length());
			newString = tempString;
			index = newString.indexOf(rem);
		}
		return newString;
	}
	
	public static String replaceAllFromStringWith(String orig, String rem, char replacer)
	{
		String newString = orig;
		int index = newString.indexOf(rem);
		while (index != -1)
		{
			String tempString = new String();
			tempString += newString.substring(0, index);
			for (int i = 0; i < rem.length(); i++)
			{
				tempString += replacer;
			}
			tempString += newString.substring(index + rem.length());
			newString = tempString;
			index = newString.indexOf(rem);
		}
		return newString;
	}
	
	public static String replaceFirstFromStringWith(String orig, String rem, char replacer)
	{
		String newString = orig;
		int index = newString.indexOf(rem);
		if (index != -1)
		{
			String tempString = new String();
			tempString += newString.substring(0, index);
			for (int i = 0; i < rem.length(); i++)
			{
				tempString += replacer;
			}
			tempString += newString.substring(index + rem.length());
			newString = tempString;
			index = newString.indexOf(rem);
		}
		return newString;
	}
	
	public static String removeFirstFromString(String orig, String rem)
	{
		String newString = orig;
		int index = newString.indexOf(rem);
		if (index != -1)
		{
			String tempString = new String();
			tempString += newString.substring(0, index);
			tempString += newString.substring(index + rem.length());
			newString = tempString;
			index = newString.indexOf(rem);
		}
		return newString;
	}

}
