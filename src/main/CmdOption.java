package main;

import org.kohsuke.args4j.Option;

public class CmdOption {
	@Option(name="-filename", usage="Specify the input data file name")
	public String filename = "";
}
