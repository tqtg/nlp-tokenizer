package main;

import java.util.ArrayList;
import java.util.List;

import jmdn.base.util.string.StrUtil;

public class SentenceTokenizer {
	public static List<String> tokenize(String line) {
		List<String> sentences = new ArrayList<>();	
		sentences = StrUtil.tokenizeString(line, ".");
		
		return sentences;
	}
}
