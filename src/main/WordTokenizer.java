package main;

import java.util.ArrayList;
import java.util.List;

import jmdn.base.util.string.StrUtil;

public class WordTokenizer {
	public static List<String> tokenize(String sentence) {
		List<String> words = new ArrayList<>();	
		words = StrUtil.tokenizeString(StrUtil.normalizeString(sentence), " ,;`'");
		
		return words;
	}
}
