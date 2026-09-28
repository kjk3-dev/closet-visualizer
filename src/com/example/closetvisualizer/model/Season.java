package com.example.closetvisualizer.model;

public enum Season {
	SPRING_SUMMER(1, "春夏"), AUTUMN_WINTER(2, "秋冬");

	private final int id;
	private final String name;

	private Season(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public static Season getById(int id) {
		for (Season season : values()) {
			if (season.getId() == id) {
				return season;
			}
		}
		return null;
	}

	public static String getOptionsString() {
		StringBuilder sb = new StringBuilder();
		for (Season season : values()) {
			sb.append(season.getId()).append(".").append(season.getName()).append(" ");
		}
		return sb.toString().trim();
	}
}