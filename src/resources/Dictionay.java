package resources;

import java.util.List;

import jmdn.base.util.filesystem.FileLoader;

public class Dictionay {
	public static List<String> exception = FileLoader.readFile("../dictionary/exception.dic", "UTF8");

	public static boolean isException(String str) {
		for (String value : Dictionay.exception) {
			if (str.toLowerCase().endsWith(value)) return true;
		}
		
		return false;
	}
}
