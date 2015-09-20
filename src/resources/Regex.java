package resources;

public class Regex {
	
	public static final String ELLIPSIS = "\\.{2,}";
	
	public static final String EMAIL = "([\\w\\d_\\.-]+)@(([\\d\\w-]+)\\.)*([\\d\\w-]+)";
	
	public static final String FULL_DATE = "(0?[1-9]|[12][0-9]|3[01])(\\/|-|\\.)(0?[1-9]|1[012])((\\/|-|\\.)(19|20)\\d\\d)";
	
	public static final String MONTH = "(0?[1-9]|1[012])(\\/)(19|20)\\d\\d";
	
	public static final String DATE = "(0?[1-9]|[12][0-9]|3[01])(\\/)(0?[1-9]|1[012])";
	
	public static final String TIME = "(0?\\d|1\\d|2[0-3])(:|h)(0?\\d|[1-5]\\d)";
	
	public static final String MONEY = "\\p{Sc}\\d+([\\.,]\\d+)*|\\d+([\\.,]\\d+)*\\p{Sc}";
	
	public static final String PHONE_NUMBER = "(\\(?\\+\\d{1,2}\\)?[\\s\\.-]?)?\\d{2,}[\\s\\.-]?\\d{3,}[\\s\\.-]?\\d{3,}";
	
	public static final String URL = "(https?:\\/\\/)?([a-z][\\w-~]*(\\.[\\w-~]+)+|\\d{1,3}(\\.\\d{1,3}){3})(:\\d{1,5})?(\\/[\\w-~=\\?]*)*";
	
	public static final String NUMBER = "[-+]?\\d+([\\.,]\\d+)*(%)?";
	
	public static final String PUNCTUATION = ",|\\.|:|\\?|!|;|-|_|\"|'|\\||\\(|\\)|\\[|\\]|\\{|\\}|⟨|⟩|«|»|\\\\|\\/|\\‘|\\’|\\“|\\”|…";
	
	public static final String SPECIAL_CHAR = "\\~|\\@|\\#|\\^|\\&|\\*|\\+|\\-|\\–|<|>|\\|";
	
	public static final String EOS_PUNCTUATION = "(\\.+|\\?|!)";
	
}
