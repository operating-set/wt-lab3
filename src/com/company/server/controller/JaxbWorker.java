package com.company.server.controller;

import com.company.entity.Student;
import com.company.entity.User;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;

class JaxbWorker {
    private FileSystem fileSystem;

    JaxbWorker() {
        fileSystem = new FileSystem();
    }

    void studentToXml(Student student) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Student.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            File file = fileSystem.createFileForStudent(student);

            if (file != null) {
                marshaller.marshal(student, file);
                System.out.println("Info: создан файл студента " + file.getAbsolutePath());
            } else {
                System.out.println("ERROR: ошибка создания файла ");
            }
        } catch (JAXBException | IOException e) {
            e.printStackTrace();
        }
    }

    Student xmlToStudent(int id) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Student.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            File file = fileSystem.getStudentFile(id);

            Student student;
            if (file != null) {
                student = (Student) unmarshaller.unmarshal(file);
                System.out.println("Info: прочитан файл студента " + file.getAbsolutePath());
            } else {
                System.out.println("ERROR: ошибка чтения файла");
                student = new Student();
            }

            return student;
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return null;
    }

    void userToXml(User user) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(User.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            File file = fileSystem.createFileForUser(user);

            if (file != null) {
                marshaller.marshal(user, file);
                System.out.println("Info: создан файл пользователя " + file.getAbsolutePath());
            } else {
                System.out.println("ERROR: ошибка создания файла ");
            }
        } catch (JAXBException | IOException e) {
            e.printStackTrace();
        }
    }

    User xmlToUser(String username) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(User.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            File file = fileSystem.getUserFile(username);

            User user = new User();
            if (file != null) {
                user = (User) unmarshaller.unmarshal(file);
                System.out.println("Info: прочитан файл пользователя " + file.getAbsolutePath());
            } else {
                System.out.println("ERROR: ошибка чтения файла");
            }

            return user;
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return null;
    }
}
