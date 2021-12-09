package com.company.server.controller;

import java.io.*;
import java.net.Socket;

class ConnectionManager {
	private Socket clientSocket;
	private BufferedReader in;
	private BufferedWriter out;
	
	ConnectionManager(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}
	
	void connect() throws IOException {
		in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
		System.out.println("Info: создано поключение");
	}
	
	void disconnect() {
		try {
			clientSocket.close();
			in.close();
			out.close();
		} catch (IOException e) {
			System.out.println("ERROR: ошибка закрития подключения");
			e.printStackTrace();
		}
		
	}
	
	void waitForMessage() {
		System.out.println("Info<< Жду сообщения от клиента...");
		try {
			while(clientSocket.getInputStream().available() == 0) {
				Thread.sleep(1500);
			}
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	String receiveMessage() {
		StringBuilder message = new StringBuilder();
		System.out.println("Info<< принимаю сообщение");
		try {
			while (in.ready()) {
				System.out.println("Info<< принимаю линию");
				message.append(in.readLine());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return message.toString();
	}
	
	Object receiveFile() throws IOException, ClassNotFoundException {
		System.out.println("Info<< Принимаю файл...");
		ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
		
		Object obj = ois.readObject();
		
		System.out.println("Info<< Файл принят...");
		
		return obj;
	}
	
	void sendFile(Object obj) throws IOException {
		System.out.println("Info<< Отправляю файл...");
		ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
		
		oos.writeObject(obj);
		oos.flush();
		
		System.out.println("Info<< Файл отправлен...");
	}

}
