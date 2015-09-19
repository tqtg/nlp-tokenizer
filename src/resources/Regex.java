package resources;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {
	
	public static final String EMAIL = "([\\w\\d_\\.-]+)@(([\\d\\w-]+)\\.)*([\\d\\w-]+)";
	
	public static final String FULL_DATE = "(0?[1-9]|[12][0-9]|3[01])(\\/|-|\\.)(0?[1-9]|1[012])((\\/|-|\\.)(19|20)\\d\\d)";
	
	public static final String MONTH = "(0?[1-9]|1[012])(\\/)(19|20)\\d\\d";
	
	public static final String DATE = "(0?[1-9]|[12][0-9]|3[01])(\\/)(0?[1-9]|1[012])";
	
	public static final String TIME = "(0?\\d|1\\d|2[0-3])(:|h)(0?\\d|[1-5]\\d)";
	
	public static final String MONEY = "\\p{Sc}\\d+([\\.,]\\d+)*|\\d+([\\.,]\\d+)*\\p{Sc}";
	
	public static final String PHONE_NUMBER = "(\\(?\\+\\d{1,2}\\)?[\\s\\.-]?)?\\d{2,}[\\s\\.-]?\\d{3,}[\\s\\.-]?\\d{3,}";
	
	public static final String URL = "(https?:\\/\\/)?([\\da-z\\.-]+)\\.([a-z\\.]{2,6})([\\/\\w \\.-]*)*\\/?(\\?[\\w\\d=]+)?";
	
	public static final String NUMBER = "[-+]?\\d+([\\.,]\\d+)*(%)?";
	
	public static final String PUNCTUATION = ",|\\.+|:|\\?|!|;|-|_|\"|'|\\||\\(|\\)|\\[|\\]|\\{|\\}|⟨|⟩|«|»|\\\\|/|\\‘|\\’|\\“|\\”|…";
	
	public static final String NOT_EOS_PUNCTUATION = "(…|\\.{2,}|,|;|\\(|\\)|\\[|\\]|\\{|\\}|⟨|⟩|«|»|\"|'|:|\\||\\\\|/|\\‘|\\’|\\“|\\”)";
	
	public static final String EOS_PUNCTUATION = "(\\.+|\\?|!)";
	
	
	public static void testRegex(String s, String regex) {
		System.out.println("Test string: " + s);
		
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(s);
		if (matcher.find()) {
			System.out.println(s.substring(0, matcher.start()));
			System.out.println(s.substring(matcher.start(), matcher.end()));
			System.out.println(s.substring(matcher.end()));
		}
	}
}
