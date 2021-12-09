package com.company.server;

import java.util.Arrays;

import com.company.server.controller.Server;

public class Main {

    public static void main(String[] args) {
        Server server = new Server();
        server.start();

        String[] command = {""};

        while (!command[0].equals("exit")) {
            command = server.receiveCommand();
            System.out.println("Info: комманда: " + Arrays.toString(command));

            switch (command[0].toLowerCase()) {
                case "signin":
                    server.signIn();
                    break;
                case "login":
                    server.login(command);
                    break;
                case "getfile":
                    server.getFile(command);
                    break;
                case "createfile":
                    server.createFile();
                    break;
                case "editfile":
                    server.editFile(command);
                    break;
                case "exit":
                    server.exit();
                    continue;
                default:
                    System.out.print("Info: неизвестная команда");
                    break;
            }
        }

    }

}

