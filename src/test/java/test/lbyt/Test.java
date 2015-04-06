package test.lbyt;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
	
	public static void main(String[] args) {
		Pattern zhDatePattern = Pattern.compile("^(\\s*(\\d+)[/\\.年-]+)?\\s*(\\d+)\\s*[/\\.月-]+\\s*(\\d+)\\s*([日号])?\\s*$");
		String s = "2014年08月09 日 ";
		Matcher mathcer = zhDatePattern.matcher(s);
	}
}
