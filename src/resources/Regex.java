package resources;

public class Regex {
	public static final String URL = "(https?://)?[\\w-~]+(\\.[\\w-~]+)+(:\\d{1,5})?(/[\\w-~]*)*";
	public static final String EMAIL = "([\\w\\d_\\.-]+)@(([\\d\\w-]+)\\.)*([\\d\\w-]+)";
	public static final String PHONE_NUMBER = "(\\(+\\d+\\))?[\\d\\.\\-]+";
	public static final String NUMBER = "[-+]?\\d+([\\.,]\\d+)*(%)?";
	public static final String DATE = "(0?[1-9]|[12]\\d|3[01])(\\/|-|.)(0?[1-9]|1[0-2])((\\/|-|.)((19|20)\\d\\d))?";
}
