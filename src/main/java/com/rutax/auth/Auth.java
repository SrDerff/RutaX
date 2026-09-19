package com.rutax.auth;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class Auth {
    private static final Path USERS_PATH = Path.of("src", "main", "resources", "users", "users_list.txt");

    public static User getUser(String username, String password){
        try{
            if(!Files.exists(USERS_PATH)) return null;

            List<String> lines = Files.readAllLines(USERS_PATH, StandardCharsets.UTF_8);
            for(String line: lines){
                String[] data = line.split(":");

                String storedUsername = data[0];
                String storedPassword = data[1];

                if(storedUsername.equals(username) && storedPassword.equals(password)){
                    String storedName = data.length > 2 ? data[2] : username;
                    return new User(storedUsername, storedPassword, storedName);
                }
            }
        }catch (IOException excep){
            excep.printStackTrace();
        }
        return null;
    }

    public static boolean login(String username, String password){
        return getUser(username, password) != null;
    }

    public static boolean register(String username, String password, String name){
        try{
            Files.createDirectories(USERS_PATH.getParent());
            if(!Files.exists(USERS_PATH))
                Files.createFile(USERS_PATH);

            List<String> lines = Files.readAllLines(USERS_PATH, StandardCharsets.UTF_8);
            for(String line: lines){
                String[] data = line.split(":");

                String storedUsername = data[0];

                if(storedUsername.equals(username)){
                    return false;
                }
            }
            Files.writeString(
                    USERS_PATH,
                    username + ":" + password + ":" + name + System.lineSeparator(),
                    StandardCharsets.UTF_8,
                    StandardOpenOption.APPEND
            );
            return true;
        }catch (IOException excep){
            excep.printStackTrace();
            return false;
        }
    }
}