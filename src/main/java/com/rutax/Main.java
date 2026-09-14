package com.rutax;
import java.util.*;

public class Main {
    public static void main(String[] args){
        Map<String,String> mp = new HashMap<>();

        mp.put("name", "alex");
        mp.put("nick", "derff");

        for(Map.Entry<String,String>p : mp.entrySet()){
            System.out.println(p.getKey());
            System.out.println(p.getValue());
        }
    }
}
