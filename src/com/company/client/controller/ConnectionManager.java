package com.company.client.controller;

import com.company.entity.User;

import java.io.*;
import java.net.Socket;

class ConnectionManager {
	private static final String HOST = "localhost";
	private static final int PORT = 4888;
	
	private Socket socket;
	private BufferedWriter out;
	
	ConnectionManager() {}
	
	void connect() {
		try {
			System.out.println("Client<< Выполняю подключение к серверу...");
			socket = new Socket(HOST, PORT);
			out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			System.out.println("Client<< Подключение выполненно");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	void signIn(User user) throws IOException {
		System.out.println("Client<< Регестрирую пользователя...");
		sendMessage("signin");
		sendFile(user);
	}
	
	User login(String username, String password) throws IOException, ClassNotFoundException {
		System.out.println("Client<< Отправляю данные авторизации на сервер...");
		sendMessage("login " + username + " " + password);
		waitForAnswer();
		return  (User) receiveFile();
	}
	
	
	void sendMessage(String message) throws IOException {
		out.write(message);
		out.newLine();
		out.flush();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Client<< Сообщение отправлено...");
	}
	
	
	void waitForAnswer() {
		System.out.println("Client<< Жду ответа сервера...");
		try {
			while (socket.getInputStream().available() == 0) {
				System.out.println("Client<< Zzzz...");
				Thread.sleep(1500);
			}
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	Object receiveFile() throws IOException, ClassNotFoundException {
		System.out.println("Client<< Принимаю файл...");
		ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
		
		Object obj = ois.readObject();
		
		System.out.println("Client<< Файл принят...");
		
		return obj;
	}
	
	void sendFile(Object obj) throws IOException {
		System.out.println("Client<< Отправляю файл...");
		ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
		
		oos.writeObject(obj);
		oos.flush();
		System.out.println("Client<< Файл отправлен...");
	}
}
