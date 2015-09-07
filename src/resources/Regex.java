package resources;

public class Regex {
	public static final String URL = "(https?:\\/\\/)?([\\da-z\\.-]+)\\.([a-z\\.]{2,6})([\\/\\w \\.-]*)*\\/?$";
	public static final String EMAIL = "([\\w\\d_\\.-]+)@(([\\d\\w-]+)\\.)*([\\d\\w-]+)";
	public static final String PHONE_NUMBER = "(\\(+\\d+\\))?[\\d\\.\\-]+";
	public static final String NUMBER = "[-+]?\\d+([\\.,]\\d+)*";
}
