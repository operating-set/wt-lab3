package com.company.client.controller;

import com.company.entity.Student;

import java.util.HashMap;
import java.util.Map;

class StudentBuilder {
	private Console console;
	private Student student;
	
	StudentBuilder() {
		console = Console.getInstance();
		student = new Student();
	}

	Student create() {
		student = new Student();
		System.out.println("Client<< Создаем файл студента...");
		
		inputId();
		inputName();
		inputAdress();
		inputGroupNumber();
		inputGrades();
		
		return student;
	}
	
	Student edit(Student std) {
		this.student = std;
		
		System.out.println("Client<< Меню:\r\n"
				+ "\t1 - ID\r\n"
				+ "\t2 - Имя\r\n"
				+ "\t3 - Адрес\r\n"
				+ "\t4 - Номер группы\r\n"
				+ "\t5 - Оценки\r\n"
				+ "\t6 - Закончить редактирование\r\n");
		System.out.println(student);
		
		while(true) {
			System.out.println("Client<< Введите номер пункта для редактирования:");
			int number = console.nextInt();
			if (number == 6) {
				break;
			}
			switch (number) {
				case 1:
					inputId();
					break;
				case 2:
					inputName();
					break;
				case 3:
					inputAdress();
					break;
				case 4:
					inputGroupNumber();
					break;
				case 5:
					inputGrades();
					break;
				default:
					System.out.println("Client<< Такой команды нет");
			}
		}
		
		return student;
	}
	
	private void inputId() {
		System.out.println("Client<< Введите ID");
		student.setId(console.nextInt());
	}
	
	private void inputName() {
		System.out.println("Client<< Введите имя");
		student.setName(console.nextCommand());
	}
	
	private void inputAdress() {
		System.out.println("Client<< Введите адрес");
		student.setAdress(console.nextCommand());
	}
	
	private void inputGroupNumber() {
		System.out.println("Client<< Введите номер группы");
		student.setGroupNumber(console.nextInt());
	}
	
	private void inputGrades() {
		Map<String, Integer> grades = new HashMap<>();
		
		System.out.println("Client<< Введите оценки");
		System.out.println("Client<< Для завершения введите предмет [end]");
		
		String name;
		Integer grade;
		
		while (true) {
			System.out.println("Client<< Предмет");
			name = console.nextCommand();
			if (name.equals("end")) {
				student.setGrades(grades);
				return;
			}
			System.out.println("Client<< Оценка");
			grade = console.nextInt();
			grades.put(name, grade);
		}
	}
}
