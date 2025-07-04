package com.limatisoft.util;

public class StringUtils {
	public static boolean isBlank(String text) {
		return text == null || text.trim().length() == 0;
	}
}
