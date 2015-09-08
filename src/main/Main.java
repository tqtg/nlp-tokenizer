package main;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

import jmdn.base.util.filesystem.FileLoader;
import jmdn.base.util.filesystem.FileSaver;
import jmdn.base.util.string.StrUtil;

public class Main {
	private static final String ENCODING = "UTF8";
	
	public static void main(String[] args) {
		CmdOption cmdOption = new CmdOption();
		CmdLineParser parser = new CmdLineParser(cmdOption);

		try {
			if (args.length == 0) {
				showHelp(parser);
				return;
			}

			parser.parseArgument(args);

			perform(cmdOption);

		} catch (CmdLineException cle) {
			System.out.println("Command line error: " + cle.getMessage());
			showHelp(parser);

		} catch (Exception ex) {
			System.out.println("Error in main: " + ex.getMessage());
		}
	}
	
	public static void perform(CmdOption cmdOption) {
		System.out.println("\n=========");
		
		File outputFolder = new File("../output");
		if (!outputFolder.exists()) outputFolder.mkdirs();
		
		File file = new File(cmdOption.filename);
		if (file.isFile()) {
			tokenizeFile(cmdOption.filename);
		} else if (file.isDirectory()) {
			tokenizeDirectory(file);
		}
		
	}
	
	public static void tokenizeDirectory(File dir) {
		System.out.println("Folder: " + dir.getName());
		File[] listOfFiles = dir.listFiles();
		 
	    for (int i = 0; i < listOfFiles.length; i++) {
	    	if (listOfFiles[i].isFile()) {
	    		System.out.println("File: " + listOfFiles[i].getName());
	    		tokenizeFile(listOfFiles[i].getPath());
	    	} else if (listOfFiles[i].isDirectory()) {
	    		System.out.println("Folder: " + listOfFiles[i].getName());
	    		tokenizeDirectory(new File(listOfFiles[i].getPath()));
	    	} 
	    } 
	}
	
	public static void tokenizeFile(String filename) {
		
		List<String> dataLines = FileLoader.readFile(filename, ENCODING);
		System.out.println("Total lines: " + dataLines.size());
		
		//	Words tokenization
		List<String> tokens = new ArrayList<>();
		for (String line : dataLines) {
			tokens.addAll(WordTokenizer.tokenize(StrUtil.normalizeString(line)));
		}
		
		//	Sentences tokenization
		List<String> sentences = SentenceTokenizer.tokenize(tokens);
		
		filename = filename.substring(filename.lastIndexOf("/"), filename.lastIndexOf("."));
		
		FileSaver.saveListString(sentences, "../output/" + filename + "_sentences.txt", ENCODING);
		System.out.println("Total sentences: " + sentences.size());
		
		while (tokens.contains("EOS")) tokens.remove("EOS");
		FileSaver.saveListString(tokens, "../output/" + filename + "_tokens.txt", ENCODING);
		System.out.println("Total tokens: " + tokens.size());
		
		List<String> words = new ArrayList<>();
		for (String token : tokens) {
			if (!words.contains(token)) words.add(token);
		}
		FileSaver.saveListString(tokens, "../output/" + filename + "_words.txt", ENCODING);
		System.out.println("Total words: " + words.size());
		
		System.out.println("=========");
	}

	public static void showHelp(CmdLineParser parser) {
		parser.printUsage(System.out);
	}
}