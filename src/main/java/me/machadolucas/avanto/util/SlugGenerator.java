package me.machadolucas.avanto.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.Normalizer;
import java.text.Normalizer.Form;

public class SlugGenerator {

	public static String slugify(String input) {
		if (input == null || input.length() == 0)
			return "";
		String toReturn = normalize(input);
		toReturn = toReturn.replace(" ", "-");
		toReturn = toReturn.replaceAll("(?!-)\\p{Punct}", "");
		toReturn = toReturn.toLowerCase();
		try {
			toReturn = URLEncoder.encode(toReturn, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return toReturn;
	}

	private static String normalize(String input) {
		if (input == null || input.length() == 0)
			return "";
		return Normalizer.normalize(input, Form.NFD).replaceAll(
				"[^\\p{ASCII}]", "");
	}
}
