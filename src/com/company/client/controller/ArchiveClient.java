package com.company.client.controller;

import com.company.entity.Student;
import com.company.entity.User;
import com.company.entity.UserRole;

import java.io.IOException;

public class ArchiveClient {
	public static final String MENU = "Client<< Меню выбора:\r\n"
			+ "\t help       - вывод доступных команд на экран,\r\n"
			+ "\t signin     - создать пользователя,\r\n"
			+ "\t login      - аторизация пользователя,\r\n"
			+ "\t getfile    - получить дело на просмотр,\r\n"
			+ "\t createfile - создать новое дело,\r\n"
			+ "\t editfile   - редактировать дело";
	
	private ConnectionManager connection;
	private Student student;
	private Console console;
	private User user;
	
	public ArchiveClient() {
		connection = new ConnectionManager();
		connection.connect();
		student = new Student();
		console = Console.getInstance();
		user = new User();
	}
	
	public void signIn() {
		Encryptor encryptor = new Encryptor();
		String username;
		String password;
		UserRole userRole;
		
		System.out.println("Client<< Создаем пользователя...");
		try {
			System.out.println("Client<< Введите логин:");
			username = encryptor.encryptMD5(console.nextUsername());
			System.out.println("Client<< Введите пароль:");
			password = encryptor.encryptMD5(console.nextPassword());
			System.out.println("Client<< Администратор? 1 - да, 2 - нет");
			userRole = console.nextBoolean() ? UserRole.ADMIN : UserRole.USER;
			user = new User(username, password, userRole);
			connection.signIn(user);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void login() {
		Encryptor encryptor = new Encryptor();
		String username;
		String password;
		
		System.out.println("Client<< Авторизация...");
		try {
			System.out.println("Client<< Введите логин:");
			username = encryptor.encryptMD5(console.nextUsername());
			System.out.println("Client<< Введите пароль:");
			password = encryptor.encryptMD5(console.nextPassword());
			user = connection.login(username, password);
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void getFile() {
		if (user == null || user.getUsername().equals("")) {
			System.out.println("Client<< Ошибка! Отсутствует авторизация пользователя");
			return;
		}
		
		System.out.println("Client<< Введите номер дела");
		int id = console.nextInt();
		
		try {
			connection.sendMessage("getfile " + id);
			connection.waitForAnswer();
			student = (Student) connection.receiveFile();
			
			if (student.getId() != 0) {
				System.out.println(student);
			} else {
				System.out.println("Client<< Студента с таким ID не существует");
			}
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public void editFile() {
		if (user == null || user.getRole() != UserRole.ADMIN) {
			System.out.println("Client<< Нет прав на редактирование файла");
			return;
		}
		
		getFile();
		
		if (student == null || student.getId() == 0) {
			System.out.println("Client<< Файл отсутствует");
			return;
		}
		
		StudentBuilder sb = new StudentBuilder();
		
		student = sb.edit(student);
		
		try {
			System.out.println("Client<< Отправляем отредактированный файл...");
			connection.sendMessage("createfile");
			connection.sendFile(student);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void createFile() {
		if (user == null || user.getRole() != UserRole.ADMIN) {
			System.out.println("Client<< Ошибка! Отсутствует авторизация пользователя");
			return;
		}
		
		StudentBuilder sb = new StudentBuilder();
		Student student = sb.create();
		
		try {
			System.out.println("Client<< Новый файл...");
			connection.sendMessage("createfile");
			connection.sendFile(student);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void exit() {
		System.out.println("Client<< Завершаю работу...");
		try {
			connection.sendMessage("exit");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
