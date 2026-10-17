package com.example.closetvisualizer.util;

import java.text.Normalizer;
import java.util.Scanner;

public class InputUtil {
	public static boolean confirmYesNo(Scanner scanner, String prompt) {
		while (true) {
			System.out.print(prompt + "(y/n): ");
			String confirm = scanner.nextLine();
			confirm = Normalizer.normalize(confirm, Normalizer.Form.NFKC);
			if (confirm.equalsIgnoreCase("y")) {
				return true;
			} else if (confirm.equalsIgnoreCase("n")) {
				return false;
			} else {
				System.out.println("\nこの入力は無効です。");
			}
		}
	}

	public static String inputFreeText(Scanner scanner, String prompt, int maxLength, boolean required) {
		while (true) {
			System.out.print(prompt + ": ");
			String input = scanner.nextLine();
			if (input.isEmpty()) {
				if (required) {
					System.out.println("\nこの項目の入力は必須です。");
					continue;
				}
				return null;
			}
			if (input.length() <= maxLength) {
				return input;
			} else {
				System.out.println("\nこの入力は無効です。");
			}
		}
	}
}
