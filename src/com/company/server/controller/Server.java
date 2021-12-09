package com.company.server.controller;

import com.company.entity.Student;
import com.company.entity.User;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
	private static final int PORT = 4888;
	private ConnectionManager connection;
	private JaxbWorker jWorker;
	
	public Server() {
		jWorker = new JaxbWorker();
	}
	
	public void start() {
		try (ServerSocket serverSocket = new ServerSocket(PORT)) {
			System.out.println("Info: сервер запущен");
			connection = new ConnectionManager(serverSocket.accept());
			connection.connect();
			System.out.println("Info: создано подключение с сервером");
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	public String[] receiveCommand() {
		connection.waitForMessage();
		return connection.receiveMessage().split(" ");
	}
	
	public void signIn() {
		User user;
		
		connection.waitForMessage();
		try {
			user = (User) connection.receiveFile();
			jWorker.userToXml(user);
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}

	public void login(String[] message) {
		User user;

		try {
			if (message.length < 3) {
				System.out.println("Info: неверная команда для авторизации");
				connection.sendFile(new User());
				return;
			} 
			
			user = jWorker.xmlToUser(message[1]);
			System.out.println(user);
			
			if (user != null && user.getUsername().equals(message[1]) 
					         && user.getPassword().equals(message[2])) {
				connection.sendFile(user);
			} else {
				connection.sendFile(new User());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	} 
	
	/*
	public void sendResponseCode(String code) {
		System.out.println("Info<< Отправляю код ответа " + code);
		
		try {
			connection.sendMessage(code);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	*/
	
	public void getFile(String[] message) {
		Student file;
		int id;
		
		try {
			
			if (message.length < 2) {
				System.out.println("Info: неверная команда для запроса файла");
				connection.sendFile(new Student());
				return;
			}
			
			id = Integer.parseInt(message[1]);
			file = jWorker.xmlToStudent(id);
			connection.sendFile(file);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
			try {
				connection.sendFile(new Student());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
		}
	}
	
	public void createFile() {
		try {
			connection.waitForMessage();
			Student student = (Student) connection.receiveFile();
			
			jWorker.studentToXml(student);
			
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public void editFile(String[] message) {
		System.out.println("Info: отправляю файл для редактирования");
		getFile(message);
		System.out.println("Info: принимаю отредактированный файл");
		createFile();
	}
	
	public void exit() {
		connection.disconnect();
	}
}
