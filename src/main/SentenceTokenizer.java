package main;

import java.util.ArrayList;
import java.util.List;

import jmdn.base.util.string.StrUtil;

public class SentenceTokenizer {
	private static final String[] eos = {".", "?", "!", "EOS"};
	
	public static List<String> tokenize(List<String> words, String delimiter) {
		List<String> sentences = new ArrayList<>();
		
		List<String> sentence = new ArrayList<>();
		for (int i = 0; i < words.size(); i++) {
			String word = words.get(i);
			sentence.add(word);
			if (isEOS(word)) {
				if (word.equals("EOS")) sentence.remove(sentence.size() - 1);
				sentences.add(normalizeSentence(StrUtil.join(sentence, delimiter)));
				sentence.clear();
			} else if (words.get(i).equals("...") &&
					i + 1 < words.size() && Character.isUpperCase(words.get(i + 1).charAt(0))) {
				sentences.add(normalizeSentence(StrUtil.join(sentence, delimiter)));
				sentence.clear();
			}
		}
		
		return sentences;
	}
	
	private static String normalizeSentence(String sentence) {
		boolean removeRight = true;
		while(sentence.contains(" \" ")) {
			if (removeRight) {
				sentence = sentence.replaceFirst(" \" ", " \"");
				removeRight = false;
			} else {
				sentence = sentence.replaceFirst(" \" ", "\" ");
				removeRight = true;
			}
		}
		if (sentence.endsWith(" \"")) sentence = sentence.substring(0, sentence.length() - 2) + "\"";
		if (sentence.startsWith("\" ")) sentence = "\"" + sentence.substring(2);
		sentence = sentence.replace(" \",", "\",");
		
		sentence = sentence.replace("‘ ", "‘");
		sentence = sentence.replace(" ’", "’");
		sentence = sentence.replace("“ ", "“");
		sentence = sentence.replace(" ”", "”");
		
		sentence = sentence.replace("( ", "(");
		sentence = sentence.replace(" )", ")");
		sentence = sentence.replace(" ,", ",");
		sentence = sentence.replace(" :", ":");
		sentence = sentence.replace(" ?", "?");
		sentence = sentence.replace(" !", "!");
		sentence = sentence.replace(" / ", "/");
		
		if (sentence.endsWith(" .")) sentence = sentence.substring(0, sentence.length() - 2) + ".";
		
		return StrUtil.normalizeString(sentence);
	}
	
	private static boolean isEOS(String word) {
		for (String c : eos) {
			if (word.equals(c)) return true;
		}
		
		return false;
	}
}
