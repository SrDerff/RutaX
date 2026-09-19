package com.rutax;
import com.rutax.service.RouteService;

public class Main {
    public static void main(String[] args){
        RouteService rutaX = new RouteService();
        while(rutaX.run()){
        }
    }
}