package main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import resources.Regex;

public class Utils {
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
	
	public static void appendFile(List<String> lines, String pathname) {
		try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(pathname, true)))) {
		    for (String line : lines)
		    	out.println(line);
		}catch (IOException e) {
		    //exception handling left as an exercise for the reader 
		} 
	}
}
