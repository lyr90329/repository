package repository.util;

import java.awt.Color;
import java.util.Random;

public class GenerateRandom {
	public static final char[] CHARS = { 'A', 'B', 'C', 'D', 'E', 'F', 'G',
			'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
			'U', 'V', 'W', 'X', 'Y', 'Z' };
	public static Random ran = new Random();

	public static String getRandomStr() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 4; i++) {
			int index = ran.nextInt(CHARS.length);
			sb.append(CHARS[index]);
		}
		return sb.toString();
	}

	public static Color getRandomCol() {
		return new Color(ran.nextInt(255), ran.nextInt(255), ran.nextInt(255));
	}

	public static Color getReverseCol(Color c) {
		return new Color(255 - c.getRed(), 255 - c.getGreen(),
				255 - c.getBlue());
	}
}
