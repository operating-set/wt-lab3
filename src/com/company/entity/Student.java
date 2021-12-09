package com.company.entity;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@XmlRootElement
@XmlType(propOrder = {"name", "adress", "groupNumber", "grades"})
public class Student implements Serializable {
	private static final long serialVersionUID = 1623140296706573943L;
	private int id;
	private String name;
	private String adress;
	private int groupNumber;
	private Map<String, Integer> grades;
	
	public Student() {
		id = 0;
		name = "";
		adress = "";
		groupNumber = 0;
		grades = new HashMap<String, Integer>();
	}
	
	public Student(int id, String name, String adress, int groupNumber, Map<String, Integer> grades) {
		this.id = id;
		this.name = name;
		this.adress = adress;
		this.groupNumber = groupNumber;
		this.grades = grades;
	}

	public int getId() {
		return id;
	}

	@XmlAttribute
	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	@XmlElement
	public void setName(String name) {
		this.name = name;
	}

	public String getAdress() {
		return adress;
	}

	@XmlElement
	public void setAdress(String adress) {
		this.adress = adress;
	}

	public int getGroupNumber() {
		return groupNumber;
	}

	@XmlElement
	public void setGroupNumber(int groupNumber) {
		this.groupNumber = groupNumber;
	}

	public Map<String, Integer> getGrades() {
		return grades;
	}

	@XmlElement
	public void setGrades(Map<String, Integer> grades) {
		this.grades = grades;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Student other = (Student) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Student [\r\n"
				+ "\tid=" + id + ",\r\n"
				+ "\tname=" + name + ",\r\n"
				+ "\tadress=" + adress + ",\r\n"
				+ "\tgroupNumber=" + groupNumber + ",\r\n"
				+ "\tgrades=" + grades + "]";
	}
	
}
