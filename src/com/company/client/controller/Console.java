package com.company.client.controller;

import java.util.Scanner;

public class Console {
	private static Console instance;
	private static Scanner scanner;
	
	private Console() {
		scanner = new Scanner(System.in);
	}
	
	public static Console getInstance() {
		if (instance == null) {
			instance = new Console();
		}
		return instance;
	}
	
	public String nextCommand() {
		System.out.print(">> ");
		return scanner.nextLine();
	}
	
	String nextUsername() {
		System.out.print(">> ");
		String s = scanner.nextLine();
		while (s.contains(" ") || s.length() < 6) {
			System.out.println("Client<< Ошибка! Имя пользователя не может содержать пробел или быть короче 6 символов!");
			s = scanner.nextLine();
		}
		return s;
	}
	
	String nextPassword() {
		System.out.print(">> ");
		String s = scanner.nextLine();
		while (s.contains(" ") || s.length() < 6) {
			System.out.println("Client<< Ошибка! Пароль не может содержать пробел или быть короче 6 символов!");
			s = scanner.nextLine();
		}
		return s;
	}
	
	int nextInt() {
		int number;
		System.out.print(">> ");
		while (!scanner.hasNextInt()) {
			scanner.nextLine();
			System.out.print(">> ");
		}
		number = scanner.nextInt();
		scanner.nextLine();
		return number;
	}

	boolean nextBoolean() {
		int i;
		
		do {
			i = nextInt();
		} while(i < 1 || i > 2);
		
		return i == 1;
	}
	

}