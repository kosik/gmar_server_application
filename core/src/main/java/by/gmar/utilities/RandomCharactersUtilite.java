package by.gmar.utilities;

import java.util.Random;

/**
 *
 * @author s.kosik
 */
public class RandomCharactersUtilite {
    	private String base = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabsdefghijklmnopqrstuvwxyz";
	private Random random = new Random();

	public String randomString(int length) {
		StringBuilder b = new StringBuilder();
		for (int i = 0; i < length; i++) {
			b.append(base.charAt(random.nextInt(base.length())));
		}
		return b.toString();
	}
}
