package main;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import resources.Regex;

public class Util {
	public static void testFoundByRegex(String s, String regex) {
		System.out.println("Test string: " + s);
		
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(s);
		if (matcher.find()) {
			System.out.println(s.substring(0, matcher.start()));
			System.out.println(s.substring(matcher.start(), matcher.end()));
			System.out.println(s.substring(matcher.end()));
		}
	}
	
	public static void findMatchedRegex(String s) {
		Map<String, String> regexes = new HashMap<>();
		regexes.put("ELLIPSIS", Regex.ELLIPSIS);
		regexes.put("EMAIL", Regex.EMAIL);
		regexes.put("FULL_DATE", Regex.FULL_DATE);
		regexes.put("MONTH", Regex.MONTH);
		regexes.put("DATE", Regex.DATE);
		regexes.put("TIME", Regex.TIME);
		regexes.put("MONEY", Regex.MONEY);
		regexes.put("PHONE_NUMBER", Regex.PHONE_NUMBER);
		regexes.put("URL", Regex.URL);
		regexes.put("NUMBER", Regex.NUMBER);
		regexes.put("PUNCTUATION", Regex.PUNCTUATION);
		regexes.put("SPECIAL_CHAR", Regex.SPECIAL_CHAR);
		
		for (String key : regexes.keySet()) {
			if (s.matches(regexes.get(key)))
				System.out.println(key);
		}
	}
	
	public static String char2Hex(Character c) {
		return String.format("\\u%04x", (int)c);
	}
	
	public static Character hex2Char(String hex) {
		int hexToInt = Integer.parseInt(hex.substring(2), 16);
		return (char)hexToInt;
	}
}
