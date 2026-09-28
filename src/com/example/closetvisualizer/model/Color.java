package com.example.closetvisualizer.model;

public enum Color {
	WHITE(1, "ホワイト"), BLACK(2, "ブラック"), GRAY(3, "グレー"), BEIGE(4, "ベージュ"), YELLOW(5, "イエロー"), ORANGE(6, "オレンジ"), BROWN(7,
			"ブラウン"), PINK(8, "ピンク"), RED(9, "レッド"), KHAKI(10, "カーキ"), GREEN(11,
					"グリーン"), LIGHT_BLUE(12, "ライトブルー"), BLUE(13, "ブルー"), NAVY(14, "ネイビー"), PURPLE(15, "パープル");

	private final int id;
	private final String name;

	private Color(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public static Color getById(int id) {
		for (Color color : values()) {
			if (color.getId() == id) {
				return color;
			}
		}
		return null;
	}

	public static String getOptionsString() {
		StringBuilder sb = new StringBuilder();
		for (Color color : values()) {
			sb.append(color.getId()).append(".").append(color.getName()).append(" ");
		}
		return sb.toString().trim();
	}

}
