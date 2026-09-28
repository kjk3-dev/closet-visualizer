package com.example.closetvisualizer.util;

public class StringUtil {
	public static String padRight(String text, int width) {
		if (text == null) {
			text = "";
		}
		int currentWidth = getDisplayWidth(text);
		int paddingSize = Math.max(0, width - currentWidth);

		StringBuilder sb = new StringBuilder(text);
		for (int i = 0; i < paddingSize; i++) {
			sb.append(" ");
		}
		return sb.toString();
	}

	private static int getDisplayWidth(String text) {
		int width = 0;
		for (char c : text.toCharArray()) {
			width += isFullWidth(c) ? 2 : 1;
		}
		return width;
	}

	private static boolean isFullWidth(char c) {
		return (c >= 0x3000 && c <= 0x303F) // 句読点・中点・〜・「」等
				|| (c >= 0x3040 && c <= 0x309F) // ひらがな
				|| (c >= 0x30A0 && c <= 0x30FF) // カタカナ
				|| (c >= 0x4E00 && c <= 0x9FFF) // 漢字
				|| (c >= 0xFF00 && c <= 0xFFEF); // 全角記号・全角英数字
	}
}
