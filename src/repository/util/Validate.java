package repository.util;

public class Validate {
	public boolean isString(String str) {
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) != ' ' && str.charAt(i) != '\n'
					&& str.charAt(i) != '\t') {
				return true;
			}
		}
		return false;
	}
}
