package com.example.closetvisualizer;

import java.sql.SQLException;
import java.text.Normalizer;
import java.util.List;
import java.util.Scanner;

import com.example.closetvisualizer.dao.CategoryDao;
import com.example.closetvisualizer.dao.ClothesDao;
import com.example.closetvisualizer.model.Category;
import com.example.closetvisualizer.model.Clothes;
import com.example.closetvisualizer.model.Color;
import com.example.closetvisualizer.model.Scene;
import com.example.closetvisualizer.model.Season;
import com.example.closetvisualizer.util.StringUtil;

public class Main {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		boolean isRunning = true;

		while (isRunning) {
			System.out.println();
			System.out.println("=== TOPメニュー ===");
			System.out.println("1. 新しく持ち物を登録する");
			System.out.println("2. 持ち物を見る");
			System.out.println("3. ごみ箱を見る");
			System.out.println("4. 終了");
			System.out.print("ご希望の番号を数字のみで入力してください: ");

			String input = scanner.nextLine();
			input = Normalizer.normalize(input, Normalizer.Form.NFKC);

			switch (input) {
			case "1":
				try {
					registerClothes(scanner);
				} catch (SQLException e) {
					System.out.println("\n処理中にエラーが発生しました。");
					e.printStackTrace();
				}
				break;
			case "2":
				try {
					showClothesList(scanner);
				} catch (SQLException e) {
					System.out.println("\n処理中にエラーが発生しました。");
					e.printStackTrace();
				}
				break;
			case "3":
				try {
					showTrashList(scanner);
				} catch (SQLException e) {
					System.out.println("\n処理中にエラーが発生しました。");
					e.printStackTrace();
				}
				break;
			case "4":
				System.out.println("\n終了します");
				isRunning = false;
				break;
			default:
				System.out.println("\nこの入力は無効です。");
			}
		}

		scanner.close();
	}

	private static void registerClothes(Scanner scanner) throws SQLException {
		CategoryDao categoryDao = new CategoryDao();
		List<Category> categories = categoryDao.findAll();

		System.out.println();
		System.out.println("=== 新規登録 ===");

		String name = null;
		while (true) {
			System.out.print("アイテム名を入力してください（30文字以内）: ");
			String nameInput = scanner.nextLine();
			if (nameInput.isEmpty()) {
				System.out.println("\nこの項目の入力は必須です。");
				continue;
			}
			if (nameInput.length() <= 30) {
				name = nameInput;
				break;
			} else {
				System.out.println("\nこの入力は無効です。");
			}
		}

		System.out.println();
		int categoryId = 0;
		while (true) {
			printCategoryOptions(categories);
			System.out.print("カテゴリーの番号を数字のみで入力してください: ");
			String categoryIdInput = scanner.nextLine();
			categoryIdInput = Normalizer.normalize(categoryIdInput, Normalizer.Form.NFKC);

			if (categoryIdInput.isEmpty()) {
				System.out.println("\nこの項目の入力は必須です。");
				continue;
			}
			try {
				int intCategoryID = Integer.parseInt(categoryIdInput);
				Category selectedCategory = findCategoryById(intCategoryID, categories);
				if (selectedCategory != null) {
					categoryId = selectedCategory.getId();
					break;
				} else {
					System.out.println("\nこの入力は無効です。");
				}
			} catch (NumberFormatException e) {
				System.out.println("\nこの入力は無効です。");
			}
		}

		System.out.println();
		String color = null;
		while (true) {
			System.out.println(Color.getOptionsString());
			System.out.print("色の番号を数字のみで入力してください。登録しない場合はそのままEnterを押下してください: ");
			String colorInput = scanner.nextLine();
			colorInput = Normalizer.normalize(colorInput, Normalizer.Form.NFKC);

			if (colorInput.isEmpty()) {
				break;
			}
			Color selectedColor = parseColor(colorInput);
			if (selectedColor != null) {
				color = selectedColor.getName();
				break;
			} else {
				System.out.println("\nこの入力は無効です。");
			}
		}

		System.out.println();

		String scene = null;
		while (true) {
			System.out.println(Scene.getOptionsString());
			System.out.print("シーンの番号を数字のみで入力してください。登録しない場合はそのままEnterを押下してください: ");
			String sceneInput = scanner.nextLine();
			sceneInput = Normalizer.normalize(sceneInput, Normalizer.Form.NFKC);

			if (sceneInput.isEmpty()) {
				break;
			}
			Scene selectedScene = parseScene(sceneInput);
			if (selectedScene != null) {
				scene = selectedScene.getName();
				break;
			} else {
				System.out.println("\nこの入力は無効です。");
			}
		}

		System.out.println();
		String season = null;
		while (true) {
			System.out.println(Season.getOptionsString());
			System.out.print("季節の番号を数字のみで入力してください。登録しない場合はそのままEnterを押下してください: ");
			String seasonInput = scanner.nextLine();
			seasonInput = Normalizer.normalize(seasonInput, Normalizer.Form.NFKC);

			if (seasonInput.isEmpty()) {
				break;
			}
			Season selectedSeason = parseSeason(seasonInput);
			if (selectedSeason != null) {
				season = selectedSeason.getName();
				break;
			} else {
				System.out.println("\nこの入力は無効です。");
			}
		}

		System.out.println();
		String size = null;
		while (true) {
			System.out.print("サイズを入力してください（5文字以内）。登録しない場合はそのままEnterを押下してください: ");
			String sizeInput = scanner.nextLine();
			if (sizeInput.isEmpty()) {
				break;
			}
			if (sizeInput.length() <= 5) {
				size = sizeInput;
				break;
			} else {
				System.out.println("\nこの入力は無効です。");
			}
		}

		System.out.println();
		Integer price = null;
		while (true) {
			System.out.print("価格を数字のみで入力してください。登録しない場合はそのままEnterを押下してください: ");
			String priceInput = scanner.nextLine();
			priceInput = Normalizer.normalize(priceInput, Normalizer.Form.NFKC);

			if (priceInput.isEmpty()) {
				break;
			}
			try {
				int intPrice = Integer.parseInt(priceInput);
				price = intPrice;
				break;
			} catch (NumberFormatException e) {
				System.out.println("\nこの入力は無効です。");
			}
		}

		System.out.println();
		Integer rating = null;
		while (true) {
			System.out.print("お気に入り度を1〜5の数字のみで入力してください。登録しない場合はそのままEnterを押下してください: ");
			String ratingInput = scanner.nextLine();
			ratingInput = Normalizer.normalize(ratingInput, Normalizer.Form.NFKC);

			if (ratingInput.isEmpty()) {
				break;
			}
			try {
				int intRating = Integer.parseInt(ratingInput);
				if (intRating >= 1 && intRating <= 5) {
					rating = intRating;
					break;
				} else {
					System.out.println("\nこの入力は無効です。");
				}
			} catch (NumberFormatException e) {
				System.out.println("\nこの入力は無効です。");
			}
		}

		System.out.println();
		String memo = null;
		while (true) {
			System.out.print("メモを入力してください（400文字以内）。登録しない場合はそのままEnterを押下してください: ");
			String memoInput = scanner.nextLine();
			if (memoInput.length() <= 400) {
				memo = memoInput;
				break;
			} else {
				System.out.println("\nこの入力は無効です。");
			}
		}

		System.out.println();
		System.out.println("=== 新規登録確認 ===");
		System.out.println("アイテム名：" + name);
		System.out.println("カテゴリー：" +
				findCategoryById(categoryId, categories).getName());
		System.out.println("色：" + (color != null ? color : ""));
		System.out.println("シーン：" + (scene != null ? scene : ""));
		System.out.println("季節：" + (season != null ? season : ""));
		System.out.println("サイズ：" + (size != null ? size : ""));
		System.out.println("価格：" + (price != null ? price : ""));
		System.out.println("お気に入り度：" + (rating != null ? rating : ""));
		System.out.println("メモ：" + (memo != null ? memo : ""));
		while (true) {
			System.out.print("このアイテムを持ち物に登録しますか？(y/n): ");
			String confirm = scanner.nextLine();
			confirm = Normalizer.normalize(confirm, Normalizer.Form.NFKC);
			if (confirm.equalsIgnoreCase("y")) {
				Clothes clothes = new Clothes();
				clothes.setName(name);
				clothes.setCategoryId(categoryId);
				clothes.setColor(color);
				clothes.setScene(scene);
				clothes.setSeason(season);
				clothes.setSize(size);
				clothes.setPrice(price);
				clothes.setRating(rating);
				clothes.setMemo(memo);

				ClothesDao clothesDao = new ClothesDao();
				clothesDao.insert(clothes);
				System.out.println("\nアイテム名：「" + name + "」の登録が完了しました。");
				break; // TOPメニューに遷移
			} else if (confirm.equalsIgnoreCase("n")) {
				System.out.println("\n新規登録をキャンセルしました。");
				break;
			} else {
				System.out.println("\nこの入力は無効です。");
			}
		}
	}

	private static void showClothesList(Scanner scanner) throws SQLException {
		ClothesDao clothesDao = new ClothesDao();
		CategoryDao categoryDao = new CategoryDao();

		List<Clothes> clothesList = clothesDao.findAll();
		List<Category> categories = categoryDao.findAll();

		while (true) {
			System.out.println();
			System.out.println("=== アイテム一覧[持ち物] ===");
			int noWidth = 5;
			int nameWidth = 60;
			int categoryWidth = 30;
			int colorWidth = 12;
			int sceneWidth = 12;
			int seasonWidth = 12;
			int priceWidth = 12;
			int ratingWidth = 12;
			String header = String.join("｜",
					StringUtil.padRight("No.", noWidth),
					StringUtil.padRight("アイテム名", nameWidth),
					StringUtil.padRight("カテゴリー", categoryWidth),
					StringUtil.padRight("色", colorWidth),
					StringUtil.padRight("シーン", sceneWidth),
					StringUtil.padRight("季節", seasonWidth),
					StringUtil.padRight("価格", priceWidth),
					StringUtil.padRight("お気に入り度", ratingWidth));
			System.out.println(header);

			int no = 1;
			for (Clothes clothes : clothesList) {
				String categoryName = getCategoryName(clothes.getCategoryId(), categories);
				String colorName = getColorName(clothes.getColor());
				String sceneName = getSceneName(clothes.getScene());
				String seasonName = getSeasonName(clothes.getSeason());
				String priceStr = (clothes.getPrice() != null) ? String.valueOf(clothes.getPrice()) : "";
				String ratingStr = (clothes.getRating() != null) ? String.valueOf(clothes.getRating()) : "";

				String row = String.join("｜",
						StringUtil.padRight(String.valueOf(no), noWidth),
						StringUtil.padRight(clothes.getName(), nameWidth),
						StringUtil.padRight(categoryName, categoryWidth),
						StringUtil.padRight(colorName, colorWidth),
						StringUtil.padRight(sceneName, sceneWidth),
						StringUtil.padRight(seasonName, seasonWidth),
						StringUtil.padRight(priceStr, priceWidth),
						StringUtil.padRight(ratingStr, ratingWidth));
				System.out.println(row);
				no++;
			}

			if (clothesList.isEmpty()) {
				System.out.println("（登録されている持ち物はありません）");
			}

			System.out.print("詳細表示したいアイテムのNo.を数字のみで入力してください（TOPメニューに戻りたい場合はqを入力してください）: ");
			String input = scanner.nextLine();
			input = Normalizer.normalize(input, Normalizer.Form.NFKC);

			if (input.equalsIgnoreCase("q")) {
				return;
			}

			try {
				int selectedNo = Integer.parseInt(input);
				if (selectedNo >= 1 && selectedNo <= clothesList.size()) {
					Clothes selected = clothesList.get(selectedNo - 1);
					boolean backToList = showClothesDetail(scanner, selected, selectedNo, categories);
					if (backToList) {
						clothesList = clothesDao.findAll();
					}
				} else {
					System.out.println("\nこの入力は無効です。");
				}
			} catch (NumberFormatException e) {
				System.out.println("\nこの入力は無効です。");
			}
		}
	}

	private static void showTrashList(Scanner scanner) throws SQLException {
		ClothesDao clothesDao = new ClothesDao();
		CategoryDao categoryDao = new CategoryDao();

		List<Clothes> trashList = clothesDao.findTrashed();
		List<Category> categories = categoryDao.findAll();

		while (true) {
			System.out.println();
			System.out.println("=== アイテム一覧[ごみ箱] ===");
			int noWidth = 5;
			int nameWidth = 60;
			int categoryWidth = 30;
			int colorWidth = 12;
			int sceneWidth = 12;
			int seasonWidth = 12;
			int priceWidth = 12;
			int ratingWidth = 12;
			String header = String.join("｜",
					StringUtil.padRight("No.", noWidth),
					StringUtil.padRight("アイテム名", nameWidth),
					StringUtil.padRight("カテゴリー", categoryWidth),
					StringUtil.padRight("色", colorWidth),
					StringUtil.padRight("シーン", sceneWidth),
					StringUtil.padRight("季節", seasonWidth),
					StringUtil.padRight("価格", priceWidth),
					StringUtil.padRight("お気に入り度", ratingWidth));
			System.out.println(header);

			int no = 1;
			for (Clothes clothes : trashList) {
				String categoryName = getCategoryName(clothes.getCategoryId(), categories);
				String colorName = getColorName(clothes.getColor());
				String sceneName = getSceneName(clothes.getScene());
				String seasonName = getSeasonName(clothes.getSeason());
				String priceStr = (clothes.getPrice() != null) ? String.valueOf(clothes.getPrice()) : "";
				String ratingStr = (clothes.getRating() != null) ? String.valueOf(clothes.getRating()) : "";

				String row = String.join("｜",
						StringUtil.padRight(String.valueOf(no), noWidth),
						StringUtil.padRight(clothes.getName(), nameWidth),
						StringUtil.padRight(categoryName, categoryWidth),
						StringUtil.padRight(colorName, colorWidth),
						StringUtil.padRight(sceneName, sceneWidth),
						StringUtil.padRight(seasonName, seasonWidth),
						StringUtil.padRight(priceStr, priceWidth),
						StringUtil.padRight(ratingStr, ratingWidth));
				System.out.println(row);
				no++;
			}

			if (trashList.isEmpty()) {
				System.out.println("（ごみ箱は空です）");
			}

			System.out.print("詳細表示したいアイテムのNo.を数字のみで入力してください（TOPメニューに戻りたい場合はqを入力してください）: ");
			String input = scanner.nextLine();
			input = Normalizer.normalize(input, Normalizer.Form.NFKC);

			if (input.equalsIgnoreCase("q")) {
				return;
			}

			try {
				int selectedNo = Integer.parseInt(input);
				if (selectedNo >= 1 && selectedNo <= trashList.size()) {
					Clothes selected = trashList.get(selectedNo - 1);
					boolean listChanged = showTrashDetail(scanner, selected, selectedNo, categories);
					if (listChanged) {
						trashList = clothesDao.findTrashed();
					}
				} else {
					System.out.println("\nこの入力は無効です。");
				}
			} catch (NumberFormatException e) {
				System.out.println("\nこの入力は無効です。");
			}
		}
	}

	private static boolean showClothesDetail(Scanner scanner, Clothes clothes, int no, List<Category> categories)
			throws SQLException {
		ClothesDao clothesDao = new ClothesDao();

		while (true) {
			String categoryName = getCategoryName(clothes.getCategoryId(), categories);
			String priceStr = (clothes.getPrice() != null) ? String.valueOf(clothes.getPrice()) : "";
			String ratingStr = (clothes.getRating() != null) ? String.valueOf(clothes.getRating()) : "";
			System.out.println();
			System.out.println("=== アイテム詳細[持ち物] ===");
			System.out.println("No.：" + no);
			System.out.println("アイテム名：" + clothes.getName());
			System.out.println("カテゴリー：" + categoryName);
			System.out.println("色：" + (clothes.getColor() != null ? clothes.getColor() : ""));
			System.out.println("シーン：" + (clothes.getScene() != null ? clothes.getScene() : ""));
			System.out.println("季節：" + (clothes.getSeason() != null ? clothes.getSeason() : ""));
			System.out.println("サイズ：" + (clothes.getSize() != null ? clothes.getSize() : ""));
			System.out.println("価格：" + priceStr);
			System.out.println("お気に入り度：" + ratingStr);
			System.out.println("メモ：" + (clothes.getMemo() != null ? clothes.getMemo() : ""));
			System.out.println();
			System.out.println("1. 登録情報を編集する");
			System.out.println("2. 持ち物をごみ箱に移動する");
			System.out.println("3. 持ち物一覧に戻る");
			System.out.print("番号を入力してください: ");

			String input = scanner.nextLine();
			input = Normalizer.normalize(input, Normalizer.Form.NFKC);

			switch (input) {
			case "1":
				editClothes(scanner, clothes, categories);
				break;
			case "2":
				boolean isYes = false;
				while (true) {
					System.out.println();
					System.out.println("=== 削除確認 ===");
					System.out.print("アイテム名：「" + clothes.getName() + "」" + "をごみ箱に移動しますか？(y/n): ");
					String confirm = scanner.nextLine();
					confirm = Normalizer.normalize(confirm, Normalizer.Form.NFKC);
					if (confirm.equalsIgnoreCase("y")) {
						isYes = true;
						break;
					} else if (confirm.equalsIgnoreCase("n")) {
						isYes = false;
						break;
					} else {
						System.out.println("\nこの入力は無効です。");

					}
				}
				if (isYes) {
					clothesDao.moveToTrash(clothes.getId());
					System.out.println("\nアイテムをごみ箱に移動しました。");
					return true; // ごみ箱一覧画面に遷移
				} else {
					System.out.println("\nごみ箱への移動をキャンセルしました。");
					break; // switch抜けで詳細画面に遷移
				}
			case "3":
				return false;
			default:
				System.out.println("\nこの入力は無効です。");
			}
		}
	}

	private static void editClothes(Scanner scanner, Clothes clothes, List<Category> categories) throws SQLException {
		System.out.println();
		System.out.println("=== 登録内容編集 ===");
		System.out.println("1.アイテム名 2.カテゴリー 3.色 4.シーン 5.季節 6.サイズ 7.価格 8.お気に入り度 9.メモ");
		System.out.print("編集する項目の番号を入力してください: ");

		String input = scanner.nextLine();
		input = Normalizer.normalize(input, Normalizer.Form.NFKC);

		boolean updated = false;

		switch (input) {
		case "1":
			updated = editName(scanner, clothes);
			break;
		case "2":
			updated = editCategory(scanner, clothes, categories);
			break;
		case "3":
			updated = editColor(scanner, clothes);
			break;
		case "4":
			updated = editScene(scanner, clothes);
			break;
		case "5":
			updated = editSeason(scanner, clothes);
			break;
		case "6":
			updated = editSize(scanner, clothes);
			break;
		case "7":
			updated = editPrice(scanner, clothes);
			break;
		case "8":
			updated = editRating(scanner, clothes);
			break;
		case "9":
			updated = editMemo(scanner, clothes);
			break;
		default:
			System.out.println("\nこの入力は無効です。");
			return;
		}

		if (updated) {
			ClothesDao clothesDao = new ClothesDao();
			clothesDao.update(clothes);
			System.out.println("\n更新が完了しました。");
		}
	}

	private static boolean editName(Scanner scanner, Clothes clothes) {
		System.out.println();
		System.out.println("=== アイテム名編集 ===");
		while (true) {
			System.out.println("現在：" + clothes.getName());
			System.out.print("変更後のアイテム名を入力してください（30文字以内）。変更しない場合はそのままEnterを押下してください: ");
			String input = scanner.nextLine();

			if (input.isEmpty()) {
				return false;
			}
			if (input.length() <= 30) {
				clothes.setName(input);
				return true;
			} else {
				System.out.println("\nこの入力は無効です。");
			}
		}
	}

	private static boolean editCategory(Scanner scanner, Clothes clothes, List<Category> categories) {
		System.out.println();
		System.out.println("=== カテゴリー編集 ===");
		while (true) {
			Category currentCategory = findCategoryById(clothes.getCategoryId(), categories);
			String currentName = currentCategory.getName();
			System.out.println("現在：" + currentName);
			printCategoryOptions(categories);
			System.out.print("変更後のカテゴリーの番号を数字のみで入力してください。変更しない場合はそのままEnterを押下してください: ");
			String input = scanner.nextLine();
			input = Normalizer.normalize(input, Normalizer.Form.NFKC);

			if (input.isEmpty()) {
				return false;
			}
			try {
				int categoryId = Integer.parseInt(input);
				Category selected = findCategoryById(categoryId, categories);
				if (selected != null) {
					clothes.setCategoryId(selected.getId());
					return true;
				} else {
					System.out.println("\nこの入力は無効です。");
				}
			} catch (NumberFormatException e) {
				System.out.println("\nこの入力は無効です。");
			}
		}
	}

	private static boolean editColor(Scanner scanner, Clothes clothes) {
		System.out.println();
		System.out.println("=== 色編集 ===");
		while (true) {
			String currentColorLabel = (clothes.getColor() != null) ? "現在：" + clothes.getColor() : "現在登録なし";
			System.out.println(currentColorLabel);
			System.out.println(Color.getOptionsString());
			System.out.print("変更後の色の番号を数字のみで入力してください。変更しない場合はそのままEnterを押下、未設定の状態に戻したい場合は大文字でCLEARのみを入力してください: ");
			String input = scanner.nextLine();
			input = Normalizer.normalize(input, Normalizer.Form.NFKC);

			if (input.isEmpty()) {
				return false;
			}
			if (input.equals("CLEAR")) {
				clothes.setColor(null);
				return true;
			}

			Color selectedColor = parseColor(input);
			if (selectedColor != null) {
				clothes.setColor(selectedColor.getName());
				return true;
			} else {
				System.out.println("\nこの入力は無効です。");
			}
		}
	}

	private static boolean editScene(Scanner scanner, Clothes clothes) {
		System.out.println();
		System.out.println("=== シーン編集 ===");
		while (true) {
			String currentSceneLabel = (clothes.getScene() != null) ? "現在：" + clothes.getScene() : "現在登録なし";
			System.out.println(currentSceneLabel);
			System.out.println(Scene.getOptionsString());
			System.out.print("変更後のシーンの番号を数字のみで入力してください。変更しない場合はそのままEnterを押下、未設定の状態に戻したい場合は大文字でCLEARのみを入力してください: ");
			String input = scanner.nextLine();
			input = Normalizer.normalize(input, Normalizer.Form.NFKC);

			if (input.isEmpty()) {
				return false;
			}
			if (input.equals("CLEAR")) {
				clothes.setScene(null);
				return true;
			}

			Scene selectedScene = parseScene(input);
			if (selectedScene != null) {
				clothes.setScene(selectedScene.getName());
				return true;
			} else {
				System.out.println("\nこの入力は無効です。");
			}
		}
	}

	private static boolean editSeason(Scanner scanner, Clothes clothes) {
		System.out.println();
		System.out.println("=== 季節編集 ===");
		while (true) {
			String currentSeasonLabel = (clothes.getSeason() != null) ? "現在：" + clothes.getSeason() : "現在登録なし";
			System.out.println(currentSeasonLabel);
			System.out.println(Season.getOptionsString());
			System.out.print("変更後の季節の番号を数字のみで入力してください。変更しない場合はそのままEnterを押下、未設定の状態に戻したい場合は大文字でCLEARのみを入力してください: ");
			String input = scanner.nextLine();
			input = Normalizer.normalize(input, Normalizer.Form.NFKC);

			if (input.isEmpty()) {
				return false;
			}
			if (input.equals("CLEAR")) {
				clothes.setSeason(null);
				return true;
			}

			Season selectedSeason = parseSeason(input);
			if (selectedSeason != null) {
				clothes.setSeason(selectedSeason.getName());
				return true;
			} else {
				System.out.println("\nこの入力は無効です。");
			}
		}
	}

	private static boolean editSize(Scanner scanner, Clothes clothes) {
		System.out.println();
		System.out.println("=== サイズ編集 ===");
		while (true) {
			String currentSizeLabel = (clothes.getSize() != null) ? "現在：" + clothes.getSize() : "現在登録なし";
			System.out.println(currentSizeLabel);
			System.out.print("変更後のサイズを入力してください（5文字以内）。変更しない場合はそのままEnterを押下、未設定の状態に戻したい場合は大文字でCLEARのみを入力してください: ");

			String input = scanner.nextLine();
			String normalizedInput = Normalizer.normalize(input, Normalizer.Form.NFKC);

			if (input.isEmpty()) {
				return false;
			}
			if (normalizedInput.equals("CLEAR")) {
				clothes.setSize(null);
				return true;
			}

			if (input.length() <= 5) {
				clothes.setSize(input);
				return true;
			} else {
				System.out.println("\nこの入力は無効です。");
			}
		}
	}

	private static boolean editPrice(Scanner scanner, Clothes clothes) {
		System.out.println();
		System.out.println("=== 価格編集 ===");
		while (true) {
			String currentPriceLabel = (clothes.getPrice() != null) ? "現在：" + clothes.getPrice() : "現在登録なし";
			System.out.println(currentPriceLabel);
			System.out.print("変更後の価格を数字のみで入力してください。変更しない場合はそのままEnterを押下、未設定の状態に戻したい場合は大文字でCLEARのみを入力してください: ");

			String input = scanner.nextLine();
			input = Normalizer.normalize(input, Normalizer.Form.NFKC);

			if (input.isEmpty()) {
				return false;
			}
			if (input.equals("CLEAR")) {
				clothes.setPrice(null);
				return true;
			}

			try {
				int price = Integer.parseInt(input);
				if (price >= 0) {
					clothes.setPrice(price);
					return true;
				} else {
					System.out.println("\nこの入力は無効です。");
				}
			} catch (NumberFormatException e) {
				System.out.println("\nこの入力は無効です。");
			}
		}
	}

	private static boolean editRating(Scanner scanner, Clothes clothes) {
		System.out.println();
		System.out.println("=== お気に入り度編集 ===");
		while (true) {
			String currentRatingLabel = (clothes.getRating() != null) ? "現在：" + clothes.getRating() : "現在登録なし";
			System.out.println(currentRatingLabel);
			System.out.print("変更後のお気に入り度を1〜5の数字のみで入力してください。変更しない場合はそのままEnterを押下、未設定の状態に戻したい場合は大文字でCLEARのみを入力してください: ");
			String input = scanner.nextLine();
			input = Normalizer.normalize(input, Normalizer.Form.NFKC);

			if (input.isEmpty()) {
				return false;
			}
			if (input.equals("CLEAR")) {
				clothes.setRating(null);
				return true;
			}

			try {
				int rating = Integer.parseInt(input);
				if (rating >= 1 && rating <= 5) {
					clothes.setRating(rating);
					return true;
				} else {
					System.out.println("\nこの入力は無効です。");
				}
			} catch (NumberFormatException e) {
				System.out.println("\nこの入力は無効です。");
			}
		}
	}

	private static boolean editMemo(Scanner scanner, Clothes clothes) {
		System.out.println();
		System.out.println("=== メモ編集 ===");
		while (true) {
			String currentMemoLabel = (clothes.getMemo() != null) ? "現在：" + clothes.getMemo() : "現在登録なし";
			System.out.println(currentMemoLabel);
			System.out.print("変更後のメモを入力してください（400文字以内）。変更しない場合はそのままEnterを押下、未設定の状態に戻したい場合は大文字でCLEARのみを入力してください: ");
			String input = scanner.nextLine();
			String normalizedInput = Normalizer.normalize(input, Normalizer.Form.NFKC);
			if (input.isEmpty()) {
				return false;
			}
			if (normalizedInput.equals("CLEAR")) {
				clothes.setMemo(null);
				return true;
			}

			if (input.length() <= 400) {
				clothes.setMemo(input);
				return true;
			} else {
				System.out.println("\nこの入力は無効です。");
			}
		}
	}

	private static boolean showTrashDetail(Scanner scanner, Clothes clothes, int no, List<Category> categories)
			throws SQLException {
		ClothesDao clothesDao = new ClothesDao();

		while (true) {
			String categoryName = getCategoryName(clothes.getCategoryId(), categories);
			String priceStr = (clothes.getPrice() != null) ? String.valueOf(clothes.getPrice()) : "";
			String ratingStr = (clothes.getRating() != null) ? String.valueOf(clothes.getRating()) : "";

			System.out.println();
			System.out.println("=== アイテム詳細[ごみ箱] ===");
			System.out.println("No.：" + no);
			System.out.println("アイテム名：" + clothes.getName());
			System.out.println("カテゴリー：" + categoryName);
			System.out.println("色：" + (clothes.getColor() != null ? clothes.getColor() : ""));
			System.out.println("シーン：" + (clothes.getScene() != null ? clothes.getScene() : ""));
			System.out.println("季節：" + (clothes.getSeason() != null ? clothes.getSeason() : ""));
			System.out.println("サイズ：" + (clothes.getSize() != null ? clothes.getSize() : ""));
			System.out.println("価格：" + priceStr);
			System.out.println("お気に入り度：" + ratingStr);
			System.out.println("メモ：" + (clothes.getMemo() != null ? clothes.getMemo() : ""));
			System.out.println();
			System.out.println("1. ごみ箱から復元する");
			System.out.println("2. 完全に削除する");
			System.out.println("3. ごみ箱のアイテム一覧に戻る");
			System.out.print("番号を入力してください: ");

			String input = scanner.nextLine();
			input = Normalizer.normalize(input, Normalizer.Form.NFKC);

			boolean isYes = false;
			String confirm = null;
			switch (input) {
			case "1":
				while (true) {
					System.out.println();
					System.out.println("=== 復元確認 ===");
					System.out.print("アイテム名：「" + clothes.getName() + "」" + "を持ち物に戻しますか？(y/n): ");
					confirm = scanner.nextLine();
					confirm = Normalizer.normalize(confirm, Normalizer.Form.NFKC);

					if (confirm.equalsIgnoreCase("y")) {
						isYes = true;
						break;
					} else if (confirm.equalsIgnoreCase("n")) {
						isYes = false;
						break;
					} else {
						System.out.println("\nこの入力は無効です。");
					}
				}
				if (isYes) {
					clothesDao.restore(clothes.getId());
					System.out.println("\nごみ箱からアイテムを復元しました。");
					return true;// ごみ箱一覧画面に遷移
				} else {
					System.out.println("\nごみ箱からの復元をキャンセルしました。");
					break; // switch抜けで詳細画面に遷移
				}

			case "2":
				while (true) {
					System.out.println();
					System.out.println("=== 完全削除確認 ===");
					System.out.print("アイテム名：「" + clothes.getName() + "」" + "の登録内容を完全削除しますか？完全削除すると元に戻せません。(y/n): ");
					confirm = scanner.nextLine();
					confirm = Normalizer.normalize(confirm, Normalizer.Form.NFKC);
					if (confirm.equalsIgnoreCase("y")) {
						isYes = true;
						break;
					} else if (confirm.equalsIgnoreCase("n")) {
						isYes = false;
						break;
					} else {
						System.out.println("\nこの入力は無効です。");
					}
				}
				if (isYes) {
					clothesDao.deletePermanently(clothes.getId());
					System.out.println("\n完全削除が完了しました。");
					return true;
				} else {
					System.out.println("\n完全削除をキャンセルしました。");
					break;
				}

			case "3":
				return false;
			default:
				System.out.println("\nこの入力は無効です。");
			}
		}
	}

	private static void printCategoryOptions(List<Category> categories) {
		StringBuilder sb = new StringBuilder();
		for (Category category : categories) {
			sb.append(category.getId()).append(".").append(category.getName()).append(" ");
		}
		System.out.println(sb.toString().trim());
	}

	private static Category findCategoryById(int id, List<Category> categories) {
		for (Category category : categories) {
			if (category.getId() == id) {
				return category;
			}
		}
		return null;
	}

	private static Color parseColor(String input) {
		try {
			return Color.getById(Integer.parseInt(input));
		} catch (NumberFormatException e) {
			return null;
		}
	}

	private static Scene parseScene(String input) {
		try {
			return Scene.getById(Integer.parseInt(input));
		} catch (NumberFormatException e) {
			return null;
		}
	}

	private static Season parseSeason(String input) {
		try {
			return Season.getById(Integer.parseInt(input));
		} catch (NumberFormatException e) {
			return null;
		}
	}

	private static String getCategoryName(int categoryId, List<Category> categories) {
		for (Category category : categories) {
			if (category.getId() == categoryId) {
				return category.getName();
			}
		}
		return "";
	}

	private static String getColorName(String colorValue) {
		if (colorValue == null)
			return "";
		return colorValue;
	}

	private static String getSceneName(String sceneValue) {
		if (sceneValue == null)
			return "";
		return sceneValue;
	}

	private static String getSeasonName(String seasonValue) {
		if (seasonValue == null)
			return "";
		return seasonValue;
	}

}