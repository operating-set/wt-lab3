package com.company.server.controller;

import com.company.entity.Student;
import com.company.entity.User;

import java.io.File;
import java.io.IOException;

class FileSystem {
    private static final String FILE_EXTENSION = ".xml";
    private static final String FILES_FOLDER = "files/";
    private static final String USERS_FOLDER = "users/";

    FileSystem() {
        File files = new File(FILES_FOLDER);
        File users = new File(USERS_FOLDER);

        if (!files.exists()) {
            if (!files.mkdirs()) System.out.println("Ошибка");
        }

        if (!users.exists()) {
            if (!users.mkdirs()) System.out.println("Ошибка");
        }
    }

    File createFileForStudent(Student student) throws IOException {
        String filePath = student.getId() + FILE_EXTENSION;
        File file = new File(FILES_FOLDER, filePath);

        if (!file.setWritable(true)) System.out.println("Ошибка");

        if (!file.exists()) {
            if (!file.createNewFile()) System.out.println("Ошибка");
        }

        return file;
    }

    File getStudentFile(int id) {
        String filePath = FILES_FOLDER + id + FILE_EXTENSION;
        File file = new File(filePath);

        if (!file.exists()) {
            return null;
        }

        return file;
    }

    File createFileForUser(User user) throws IOException {
        String filePath = USERS_FOLDER + user.getUsername() + FILE_EXTENSION;
        File file = new File(filePath);

        if (!file.exists()) {
            if (!file.createNewFile()) System.out.println("Ошибка");
        }

        return file;
    }

    File getUserFile(String username) {
        String filePath = USERS_FOLDER + username + FILE_EXTENSION;
        File file = new File(filePath);

        if (!file.exists()) {
            return null;
        }

        return file;
    }

}
