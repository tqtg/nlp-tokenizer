package main;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jmdn.base.util.string.StrUtil;
import resources.Dictionay;
import resources.Regex;

public class WordTokenizer {
	private static final String[] specialChar = {";", ":", "?", "!", "_", "\"", "'","\\", "[", "]", "{", "}"};
	private static final String[] eos = {".", "?", "!"};
	
	public static List<String> tokenize(String line) {
		boolean hasEOSChar = false;
		for (String c : eos) {
			if (line.endsWith(c)) {
				hasEOSChar = true;
				break;
			}
		}
		if (!hasEOSChar) line += " EOS";
		
		for (String character : specialChar) {
			line = line.replace(character, " " + character + " ");
		}
		
		List<String> words = new ArrayList<>();	
		
		List<String> tokens = StrUtil.tokenizeString(line, " ");
		for (String token : tokens) {
			if (Dictionay.isException(token)) {
				words.add(token);
				continue;
			} 
			
			if (token.matches(Regex.NUMBER)) {
				words.add(token);
				continue;
			} else {
				token = token.replace(",", " , ");
			}
			
			if (token.matches(Regex.PHONE_NUMBER)) {
				words.add(token);
				continue;
			} else {
				token = token.replace("(", " ( ");
				token = token.replace(")", " ) ");
				token = token.replace("+", " + ");
				token = token.replace("-", " - ");
			}
			
			Pattern pattern = Pattern.compile(Regex.EMAIL);
			Matcher matcher = pattern.matcher(token);
			if (matcher.find()) {
				token = token.replace(matcher.group(), " " + matcher.group() + " ");
			} else {
				pattern = Pattern.compile(Regex.URL);
				matcher = pattern.matcher(token);
				if (matcher.find()) {
					token = token.replace(matcher.group(), " " + matcher.group() + " ");
				} else {
					token = token.replace("/", " / ");
				}
			}
			
			if (token.endsWith("...") && token.length() > 3) token = token.substring(0, token.length() - 3) + " ...";
			else if (token.endsWith(".") && token.length() > 1) token = token.substring(0, token.length() - 1) + " .";
			
			words.addAll(StrUtil.tokenizeString(token, " "));
		}
		
		return words;
	}
	
	
}
