package main;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

import jmdn.base.util.filesystem.FileLoader;
import jmdn.base.util.filesystem.FileSaver;

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
		long beginTime = System.currentTimeMillis();
		
		System.out.println("\n=========");
		
		File outputFolder = new File("../output");
		if (!outputFolder.exists()) outputFolder.mkdirs();
		
		File file = new File(cmdOption.filename);
		if (file.isFile()) {
			tokenizeFile(cmdOption.filename);
		} else if (file.isDirectory()) {
			tokenizeDirectory(file);
		}
		
		long endTime = System.currentTimeMillis();
		System.out.println("\nTotal running time: " + String.valueOf(endTime - beginTime) + " ms");
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
		
		// Tokenization
		List<String> tokens = new ArrayList<>();
		List<String> sentences = new ArrayList<>();
				
		long beginTime = System.currentTimeMillis();
		for (String line : dataLines) {
			List<String> lineTokens = Tokenizer.tokenize(line);
			tokens.addAll(lineTokens);
			sentences.addAll(Tokenizer.joinSentences(lineTokens));
		}
		long endTime = System.currentTimeMillis();
		
		//	Count words frequency
		Map<String, Integer> wordFrequencyMap = new HashMap<>();
		for (String token : tokens) {
			if (!wordFrequencyMap.containsKey(token)) wordFrequencyMap.put(token, 1);
			else wordFrequencyMap.put(token, wordFrequencyMap.get(token) + 1);
		}
		
		Map<String, Integer> sortedMap = sortByComparator(wordFrequencyMap);
		List<String> wordFrequency = new ArrayList<>();
		for (Map.Entry<String, Integer> entry : sortedMap.entrySet()) {
			wordFrequency.add(entry.getKey() + " " + entry.getValue());
		}
		
		// Get filename
		int i = filename.lastIndexOf("/");
		if (i < 0) i = 0; 
		filename = filename.substring(i, filename.lastIndexOf("."));

		// Save result to files
		FileSaver.saveListString(tokens, "../output/" + filename + "_tokens.txt", ENCODING);
		System.out.println("Total tokens: " + tokens.size());
		
		FileSaver.saveListString(sentences, "../output/" + filename + "_sentences.txt", ENCODING);
		System.out.println("Total sentences: " + sentences.size());
		
		FileSaver.saveListString(wordFrequency, "../output/" + filename + "_frequency.txt", ENCODING);
		System.out.println("Total words: " + wordFrequency.size());
		
		//	Running time
		System.out.println("Tokenization time: " + String.valueOf(endTime - beginTime) + "ms");
		System.out.println("=========");
	}
	
	private static Map<String, Integer> sortByComparator(Map<String, Integer> unsortMap) {
		// Convert Map to List
		List<Map.Entry<String, Integer>> list = 
			new LinkedList<Map.Entry<String, Integer>>(unsortMap.entrySet());

		// Sort list with comparator, to compare the Map values
		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
			public int compare(Map.Entry<String, Integer> o1,
                                           Map.Entry<String, Integer> o2) {
				return (o2.getValue()).compareTo(o1.getValue());
			}
		});

		// Convert sorted map back to a Map
		Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
		for (Iterator<Map.Entry<String, Integer>> it = list.iterator(); it.hasNext();) {
			Map.Entry<String, Integer> entry = it.next();
			sortedMap.put(entry.getKey(), entry.getValue());
		}
		return sortedMap;
	}


	public static void showHelp(CmdLineParser parser) {
		parser.printUsage(System.out);
	}
}