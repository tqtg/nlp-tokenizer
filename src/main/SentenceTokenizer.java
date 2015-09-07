package main;

import java.util.ArrayList;
import java.util.List;

import jmdn.base.util.string.StrUtil;

public class SentenceTokenizer {
	private static final String[] eos = {".", "?", "!", "EOS"};
	
	public static List<String> tokenize(List<String> words) {
		List<String> sentences = new ArrayList<>();
		
		List<String> sentence = new ArrayList<>();
		for (String word : words) {
			sentence.add(word);
			if (isEOS(word)) {
				if (word.equals("EOS")) sentence.remove(sentence.size() - 1);
				sentences.add(normalizeSentence(StrUtil.normalizeString(StrUtil.join(sentence))));
				sentence.clear();
			}
		}
		
		return sentences;
	}
	
	private static String normalizeSentence(String sentence) {
		sentence = sentence.replace("( ", "(");
		sentence = sentence.replace(" )", ")");
		sentence = sentence.replace(" ,", ",");
		sentence = sentence.replace(" :", ":");
		sentence = sentence.replace(" ?", "?");
		sentence = sentence.replace(" !", "!");
		if (sentence.endsWith(" .")) sentence = sentence.substring(0, sentence.length() - 2) + ".";
		
		return sentence;
	}
	
	private static boolean isEOS(String word) {
		for (String c : eos) {
			if (word.equals(c)) return true;
		}
		
		return false;
	}
}
