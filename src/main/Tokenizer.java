package main;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jmdn.base.util.string.StrUtil;
import resources.Dictionay;
import resources.Regex;

public class Tokenizer {
	// whitespace and tab character
	private static final String[] delimiterChars = {"\\u0020", "\\u00a0", "\\u0009"};
	private static String delimiter;
	
	static {
		delimiter = "";
		for (String hex : delimiterChars) {
			delimiter += Util.hex2Char(hex);
		}
	}
	
	public static List<String> tokenize(String s) {
		List<String> tokens = new ArrayList<>();
		List<String> tempTokens = StrUtil.tokenizeString(s, delimiter);
		
		for (String token : tempTokens) {	
			if (token.length() == 1 || !hasPunctuation(token)) {
				tokens.add(token);
				continue;
			}
			
			if (token.endsWith(",")) {
				tokens.addAll(tokenize(token.substring(0, token.length() - 1)));
				tokens.add(",");
				continue;
			}
			
			if (Dictionay.abbreviation.contains(token)) {
				tokens.add(token);
				continue;
			}
			
			if (token.endsWith(".") && Character.isAlphabetic(token.charAt(token.length() - 2))) {
				tokens.addAll(tokenize(token.substring(0, token.length() - 1)));
				tokens.add(".");
				continue;
			}
			
			if (Dictionay.exception.contains(token)) {
				tokens.add(token);
				continue;
			}
			
			List<String> regexes = new ArrayList<>();
			regexes.add(Regex.ELLIPSIS);
			regexes.add(Regex.EMAIL);
			regexes.add(Regex.URL);
			regexes.add(Regex.FULL_DATE);
			regexes.add(Regex.MONTH);
			regexes.add(Regex.DATE);
			regexes.add(Regex.TIME);
			regexes.add(Regex.MONEY);
			regexes.add(Regex.PHONE_NUMBER);
			regexes.add(Regex.NUMBER);
			regexes.add(Regex.PUNCTUATION);
			regexes.add(Regex.SPECIAL_CHAR);
			
			boolean matching = false;
			for (String regex : regexes) {
				if (token.matches(regex)) {
					tokens.add(token);
					matching = true;
					break;
				}
			}
			if (matching) continue;
			
			boolean tokenContainsExp = false;
			for (String e : Dictionay.exception) {
				int i = token.indexOf(e);
				if (i < 0) continue;
				
				tokenContainsExp = true;
				tokens = recursive(tokens, token, i, i + e.length());
				break;
			}
			if (tokenContainsExp) continue;

			for (int i = 0; i < regexes.size(); i++) {
				Pattern pattern = Pattern.compile(regexes.get(i));
				Matcher matcher = pattern.matcher(token);
				
				if (matcher.find()) {
					if (i == 9) // index of number regex
					{
						String[] replaceChar = {"-", "+"};
						tokens = recursive(tokens, token, matcher.start(), matcher.end(), replaceChar);
					} else {
						tokens = recursive(tokens, token, matcher.start(), matcher.end());
					}
					
					matching = true;
					break;
				}
			}
			
			if (matching) continue;
			else tokens.add(token);
		}
		
		return tokens;
	}
	
	private static List<String> recursive(List<String> tokens, String token, int beginMatch, int endMatch) {
		tokens.addAll(tokenize(token.substring(0, beginMatch)));
		tokens.addAll(tokenize(token.substring(beginMatch, endMatch)));
		tokens.addAll(tokenize(token.substring(endMatch)));
		
		return tokens;
	}
	
	private static List<String> recursive(List<String> tokens, String token, int beginMatch, int endMatch, String[] replaceChar) {
		String beforeMatch = token.substring(0, beginMatch);
		String afterMatch = token.substring(endMatch);
		
		for (String c : replaceChar) {
			beforeMatch = beforeMatch.replace(c, " " + c + " ");
			afterMatch = afterMatch.replace(c, " " + c + " ");
		}
		
		tokens.addAll(tokenize(beforeMatch));
		tokens.addAll(tokenize(token.substring(beginMatch, endMatch)));
		tokens.addAll(tokenize(afterMatch));
		
		return tokens;
	}
	
	private static boolean hasPunctuation(String s) {
		for (int i = 0; i < s.length(); i++) {
			if (!Character.isAlphabetic(s.charAt(i)) && !Character.isDigit(s.charAt(i)))
				return true;
		}
		
		return false;
	}
	
	public static List<String> joinSentences(List<String> tokens) {
		List<String> sentences = new ArrayList<>();
		
		List<String> sentence = new ArrayList<>();
		for (int i = 0; i < tokens.size(); i++) {
			sentence.add(tokens.get(i));
			
			if (i == tokens.size() - 1) {
				sentences.add(StrUtil.join(sentence));
				return sentences;
			}
			
			if (tokens.get(i).matches(Regex.EOS_PUNCTUATION)) {
				sentences.add(StrUtil.join(sentence));
				sentence.clear();
			}
		}
		
		return sentences;
	}
}
