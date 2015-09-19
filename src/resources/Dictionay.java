package resources;

import java.util.List;

import jmdn.base.util.filesystem.FileLoader;

public class Dictionay {
	private static final String ENCODING = "UTF8";
	public static List<String> abbreviation;
	public static List<String> exception;
	
	static {
		abbreviation = FileLoader.readFile("../dictionary/abbreviation.dic", ENCODING);
		exception = FileLoader.readFile("../dictionary/exception.dic", ENCODING);
	}	
}
