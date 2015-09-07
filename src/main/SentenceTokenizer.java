package main;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jmdn.base.util.string.StrUtil;
import resources.Regex;

public class SentenceTokenizer {
	private static Pattern pattern;
	private static Matcher matcher;
	
	public static List<String> tokenize(String line) {
		List<String> sentences = new ArrayList<>();
		String tempSentence = "";
		
		for (int i = 0; i < line.length(); i++) {
			Character character = line.charAt(i);
			tempSentence += character;
			
			if (character.equals('?') || character.equals('!')) {
				sentences.add(StrUtil.normalizeString(tempSentence));
				tempSentence = "";
			} else if (character.equals('.')) {
				i++;
				if (i == line.length()) {
					sentences.add(StrUtil.normalizeString(tempSentence));
					tempSentence = "";
					break;
				} else {
					Character nextChar = line.charAt(i);
					if (nextChar.equals(' ') && !tempSentence.endsWith("...")) {
						sentences.add(StrUtil.normalizeString(tempSentence));
						tempSentence = "";
						continue;
					}
					
					String sentence = tempSentence;
					
					while (!nextChar.equals(' ')) {
						tempSentence += nextChar;
						i++;
						if (i == line.length()) break;
						nextChar = line.charAt(i);
					}
					
					String lastPhrase = tempSentence;
					int index = tempSentence.lastIndexOf(' ');
					if (index != -1) {
						lastPhrase = lastPhrase.substring(index);	
					}
					
					i--;
					
					//	Catch ... exception
					if (lastPhrase.endsWith("...")) continue;
					
					//	Catch number exception
					pattern = Pattern.compile(Regex.NUMBER);
					matcher = pattern.matcher(lastPhrase);
					if (matcher.find()) continue;
					
					//	Catch url exception
					pattern = Pattern.compile(Regex.URL);
					matcher = pattern.matcher(lastPhrase);
					if (matcher.find()) continue;
					
					//	Catch email exception
					pattern = Pattern.compile(Regex.EMAIL);
					matcher = pattern.matcher(lastPhrase);
					if (matcher.find()) continue;
					
					sentences.add(StrUtil.normalizeString(sentence));
					tempSentence = "";
				}
			}
		}
		
		if (tempSentence.length() > 0) sentences.add(StrUtil.normalizeString(tempSentence));
		
		return sentences;
	}
}
