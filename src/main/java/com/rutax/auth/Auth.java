package com.rutax.auth;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class Auth {
    final static String USERS_PATH=".\\.\\resources\\users\\users_list.txt";

    public static boolean login(String username, String password){
        Path path = Path.of(USERS_PATH);
        try{
            List<String> lines = Files.readAllLines(path);
            for(String line: lines){
                String[] data = line.split(":");

                String storedUsername=data[0];
                String storedPassword=data[1];

                if(storedUsername.equals(username) && storedPassword.equals(password)){
                    return true;
                }
            }
        }catch (IOException excep){
            excep.printStackTrace();
        }
        return false;
    }

    public static boolean register(String username, String password, String name){
        Path path = Path.of(USERS_PATH);
        try{
            List<String> lines = Files.readAllLines(path);
            for(String line: lines){
                String[] data = line.split(":");

                String storedUsername=data[0];

                if(storedUsername.equals(username)){
                    return false;
                }
            }
            Files.writeString(
                    path,
                    username+":"+password+":"+name+System.lineSeparator(),
                    StandardOpenOption.APPEND
            );
            return true;
        }catch (IOException excep){
            excep.printStackTrace();
            return false;
        }
    }
}
