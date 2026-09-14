package com.rutax;
import java.util.*;

public class Main {
    public static void main(String[] args){
        Map<String, String> mp = new HashMap<>();
        mp.put("name","alex");
        mp.put("age","18");
        mp.put("email","sq@gmail.com");

        for(Map.Entry<String, String> m : mp.entrySet()){
            System.out.print(m.getKey() + " " + m.getValue() + "\n");
        }
    }
}
