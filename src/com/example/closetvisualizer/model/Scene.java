package com.example.closetvisualizer.model;

public enum Scene {
	PRIVATE(1, "プライベート"), OFFICE(2, "オフィス"), FORMAL(3, "フォーマル"), ROOMWEAR(4, "ルームウェア");

	private final int id;
	private final String name;

	private Scene(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public static Scene getById(int id) {
		for (Scene scene : values()) {
			if (scene.getId() == id) {
				return scene;
			}
		}
		return null;
	}

	public static String getOptionsString() {
		StringBuilder sb = new StringBuilder();
		for (Scene scene : values()) {
			sb.append(scene.getId()).append(".").append(scene.getName()).append(" ");
		}
		return sb.toString().trim();
	}

}
