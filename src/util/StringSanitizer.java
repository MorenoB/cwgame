package util;

public class StringSanitizer {
	public static String sanitize(String string) {
		while(string.contains("\t")) string = string.replaceAll("\t", " ");
		while(string.contains("\n")) string = string.replaceAll("\n", " ");
		while(string.contains("  ")) string = string.replaceAll("  ", " ");
		return string.trim();
	}
}
