package main;

import java.util.ArrayList;
import java.util.List;

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
		List<String> dataLines = FileLoader.readFile(cmdOption.filename, ENCODING);
		System.out.println("Total lines: " + dataLines.size());
		
		//	Sentences tokenization
		List<String> sentences = new ArrayList<>();
		for (String line : dataLines) {
			sentences.addAll(SentenceTokenizer.tokenize(line));
		}
		
		FileSaver.saveListString(sentences, "../data/sentences.txt", ENCODING);
		
		//	Words tokenization
		List<String> words = new ArrayList<>();
		for (String sentence : sentences) {
			words.addAll(WordTokenizer.tokenize(sentence));
		}
		
		FileSaver.saveListString(words, "../data/words.txt", ENCODING);
	}

	public static void showHelp(CmdLineParser parser) {
		parser.printUsage(System.out);
	}
}