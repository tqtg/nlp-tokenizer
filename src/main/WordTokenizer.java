
package main;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jmdn.base.util.string.StrUtil;
import resources.Dictionay;
import resources.Regex;

public class WordTokenizer {
	private static final String[] specialChar = {";", "…", "...", "&", "?", "!", "_", "=", "\"", "'", "`", "\\", "<", ">", "[", "]", "{", "}", "‘", "’", "“", "”", "*", "~", "^", "|"};
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
		
		List<String> tokens = StrUtil.tokenizeString(line, "  \t");
		for (String token : tokens) {
			if (Dictionay.isException(token)) {
				words.add(token);
				continue;
			}
			
			if (token.matches(Regex.DATE)) {
				words.add(token);
				continue;
			}
			
			if (token.matches(Regex.URL)) {
				words.add(token);
				continue;
			} else {
				token = token.replace("/", " / ");
				token = token.replace(":", " : ");
			}
			
			if (token.matches(Regex.PHONE_NUMBER)) {
				words.add(token);
				continue;
			} else {
				token = token.replace("(", " ( ");
				token = token.replace(")", " ) ");
			}
			
			Pattern pattern = Pattern.compile(Regex.EMAIL);
			Matcher matcher = pattern.matcher(token);
			if (matcher.find()) {
				token = token.replace(matcher.group(0), " " + matcher.group(0) + " ");
			} else {
				pattern = Pattern.compile(Regex.NUMBER);
				matcher = pattern.matcher(token);
				if (matcher.find()) {
					token = token.replace(matcher.group(0), " " + matcher.group(0) + " ");
					
					if (!matcher.group(0).contains(",")) {
						token = token.replace(",", " , ");
					}
				} else {
					token = token.replace(",", " , ");
					token = token.replace("+", " + ");
					token = token.replace("-", " - ");
					
					pattern = Pattern.compile(Regex.URL);
					matcher = pattern.matcher(token);
					if (matcher.find()) {
						token = token.replace(matcher.group(0), " " + matcher.group(0) + " ");
					} else {
						
					}
				}
			}
			
			if (!token.contains(" ")) {
				token = token.replace(",", " , ");
				token = token.replace(".", " . ");
			}
			
			if (token.startsWith(".") && !token.equals(".")) token = ". " + token.substring(1);
			else if (token.startsWith(",") && !token.equals(",")) token = ", " + token.substring(1);
			
			if (token.endsWith(".") && !token.endsWith("..") && token.length() > 1) token = token.substring(0, token.length() - 1) + " .";
			else if (token.endsWith(",") && token.length() > 1) token = token.substring(0, token.length() - 1) + " ,";
			
			words.addAll(StrUtil.tokenizeString(token, " "));
		}
		
		return words;
	}
	
	
}
