package main;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jmdn.base.util.string.StrUtil;
import resources.Dictionay;
import resources.Regex;

public class Tokenizer {
	
	public static List<String> tokenize(String s) {
		List<String> tokens = new ArrayList<>();
		
		List<String> tempTokens = StrUtil.tokenizeString(s);
		for (String token : tempTokens) {
			if (Dictionay.abbreviation.contains(token)) {
				tokens.add(token);
				continue;
			}
			
			if (Dictionay.exception.contains(token)) {
				tokens.add(token);
				continue;
			}

			if (token.matches(Regex.EMAIL)) {
				tokens.add(token);
				continue;
			}

			if (token.matches(Regex.FULL_DATE)) {
				tokens.add(token);
				continue;
			}
			
			if (token.matches(Regex.MONTH)) {
				tokens.add(token);
				continue;
			}
			
			if (token.matches(Regex.DATE)) {
				tokens.add(token);
				continue;
			}

			if (token.matches(Regex.TIME)) {
				tokens.add(token);
				continue;
			}
			
			if (token.matches(Regex.MONEY)) {
				tokens.add(token);
				continue;
			}

			if (token.matches(Regex.PHONE_NUMBER)) {
				tokens.add(token);
				continue;
			}
			
			if (token.matches(Regex.URL)) {
				tokens.add(token);
				continue;
			}

			if (token.matches(Regex.NUMBER)) {
				tokens.add(token);
				continue;
			}
			
			if (token.matches(Regex.PUNCTUATION)) {
				tokens.add(token);
				continue;
			}
			
			boolean tokenContainsExp = false;
			for (String e : Dictionay.exception) {
				int i = token.indexOf(e);
				if (i < 0) continue;
				
				tokens = recursive(tokens, token, i, i + e.length());
				tokenContainsExp = true;
				break;
			}
			if (tokenContainsExp) continue;

			Pattern pattern = Pattern.compile(Regex.EMAIL);
			Matcher matcher = pattern.matcher(token);
			if (matcher.find()) {
				tokens = recursive(tokens, token, matcher.start(), matcher.end());
				continue;
			}
			
			pattern = Pattern.compile(Regex.FULL_DATE);
			matcher = pattern.matcher(token);
			if (matcher.find()) {
				tokens = recursive(tokens, token, matcher.start(), matcher.end());
				continue;
			}
			
			pattern = Pattern.compile(Regex.MONTH);
			matcher = pattern.matcher(token);
			if (matcher.find()) {
				tokens = recursive(tokens, token, matcher.start(), matcher.end());
				continue;
			}
			
			pattern = Pattern.compile(Regex.DATE);
			matcher = pattern.matcher(token);
			if (matcher.find()) {
				tokens = recursive(tokens, token, matcher.start(), matcher.end());
				continue;
			}
			
			pattern = Pattern.compile(Regex.TIME);
			matcher = pattern.matcher(token);
			if (matcher.find()) {
				tokens = recursive(tokens, token, matcher.start(), matcher.end());
				continue;
			}
			
			
			pattern = Pattern.compile(Regex.MONEY);
			matcher = pattern.matcher(token);
			if (matcher.find()) {
				tokens = recursive(tokens, token, matcher.start(), matcher.end());
				continue;
			}
			
			pattern = Pattern.compile(Regex.PHONE_NUMBER);
			matcher = pattern.matcher(token);
			if (matcher.find()) {
				tokens = recursive(tokens, token, matcher.start(), matcher.end());
				continue;
			}
			
			pattern = Pattern.compile(Regex.URL);
			matcher = pattern.matcher(token);
			if (matcher.find()) {
				tokens = recursive(tokens, token, matcher.start(), matcher.end());
				continue;
			}
			
			pattern = Pattern.compile(Regex.NUMBER);
			matcher = pattern.matcher(token);
			if (matcher.find()) {
				String[] replaceChar = {"-", "+"};
				tokens = recursive(tokens, token, matcher.start(), matcher.end(), replaceChar);
				continue;
			}
			
			if (hasPunctuation(token)) {
				pattern = Pattern.compile(Regex.PUNCTUATION);
				matcher = pattern.matcher(token);
				if (matcher.find()) {
					tokens = recursive(tokens, token, matcher.start(), matcher.end());
					continue;
				}
			}

			tokens.add(token);
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
