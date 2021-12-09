package com.company.client;

import com.company.client.controller.ArchiveClient;
import com.company.client.controller.Console;

public class Main {

    public static void main(String[] args) {
        Console console = Console.getInstance();
        ArchiveClient client = new ArchiveClient();
        String command;

        System.out.println(ArchiveClient.MENU);

        do {
            System.out.println("Client<< Введите команду");
            command = console.nextCommand().toLowerCase();
            switch (command) {
                case "help":
                    System.out.println(ArchiveClient.MENU);
                    break;
                case "signin":
                    client.signIn();
                    break;
                case "login":
                    client.login();
                    break;
                case "getfile":
                    client.getFile();
                    break;
                case "createfile":
                    client.createFile();
                    break;
                case "editfile":
                    client.editFile();
                    break;
                case "exit":
                    client.exit();
                    continue;
                default:
                    System.out.println("Client<< Неизвестная команда");
                    break;
            }
        } while (!command.equals("exit"));
    }
}
