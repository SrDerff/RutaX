package com.rutax.service;
import com.rutax.auth.Auth;
import com.rutax.auth.User;
import com.rutax.graph.Graph;
import com.rutax.model.Location;
import com.rutax.model.Road;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class RouteService {
    private User currUser;
    private Graph nodes;
    private final Scanner scanner;
    private State state;

    private enum State{
        WELCOME,
        LOGIN,
        REGISTER,
        MENU,
        LIST_CITIES,
        FIND_ROUTE,
        EXIT
    }

    private void fillNodes(){
        Location loq1 = new Location(1, "Lima");
        Location loq2 = new Location(2, "Trujillo");
        Location loq3 = new Location(3, "Arequipa");
        Location loq4 = new Location(4, "Chiclayo");
        Location loq5 = new Location(5, "Cuzco");

        Road road1 = new Road(loq1, loq2, 100);
        Road road2 = new Road(loq1, loq3, 200);
        Road road3 = new Road(loq3, loq5, 150);
        Road road4 = new Road(loq2, loq4, 400);
        Road road5 = new Road(loq3, loq2, 300);
        Road road6 = new Road(loq4, loq5, 250);

        nodes.addVertex(loq1);
        nodes.addVertex(loq2);
        nodes.addVertex(loq3);
        nodes.addVertex(loq4);
        nodes.addVertex(loq5);

        nodes.addEdge(road1);
        nodes.addEdge(road2);
        nodes.addEdge(road3);
        nodes.addEdge(road4);
        nodes.addEdge(road5);
        nodes.addEdge(road6);
    }

    public RouteService(){
        this.scanner = new Scanner(System.in);
        this.state = State.WELCOME;
        this.nodes = new Graph();
        fillNodes();
    }

    public boolean run(){
        clearScreen();
        switch(state){
            case WELCOME:{
                int opt = printWelcome();
                if(opt == 1) state = State.LOGIN;
                else if(opt == 2) state = State.REGISTER;
                else if(opt == 3) state = State.EXIT;
                break;
            }
            case LOGIN:{
                login();
                break;
            }
            case REGISTER:{
                register();
                break;
            }
            case MENU:{
                int newState = printMenu();
                if(newState == 1) state = State.LIST_CITIES;
                else if(newState == 2) state = State.FIND_ROUTE;
                else if(newState == 3) state = State.EXIT;
                break;
            }
            case LIST_CITIES:{
                printCities();
                break;
            }
            case FIND_ROUTE:{
                printPath();
                break;
            }
            case EXIT:{
                return false;
            }
        }
        return true;
    }

    private void clearScreen(){
        System.out.println("\n".repeat(50));
    }

    private void waitForEnter(){
        System.out.println("\nPresiona Enter para continuar...");
        scanner.nextLine();
    }

    private int readOption(){
        while(true){
            try{
                return scanner.nextInt();
            }catch (InputMismatchException excep){
                scanner.nextLine();
                System.out.println("Opcion invalida, ingresa un numero.\n");
            }
        }
    }

    private int printWelcome(){
        int opt = -1;
        do {
            System.out.println("=== RutaX ===");
            System.out.println("Bienvenido, inicia sesion o registrate.\n");
            System.out.println("1. Iniciar sesion");
            System.out.println("2. Registrarse");
            System.out.println("3. Salir");
            System.out.print("\nEscoje una opcion: ");
            opt = readOption();
            scanner.nextLine();
        }while(opt < 1 || opt > 3);
        return opt;
    }

    private void login(){
        System.out.println("=== Iniciar sesion ===");
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        User user = Auth.getUser(username, password);
        if(user == null){
            System.out.println("\nCredenciales incorrectas.");
            waitForEnter();
            return;
        }
        this.currUser = user;
        System.out.println("\nBienvenido, " + currUser.getName());
        waitForEnter();
        this.state = State.MENU;
    }

    private void register(){
        System.out.println("=== Registrarse ===");
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        System.out.print("Nombre: ");
        String name = scanner.nextLine();

        if(Auth.register(username, password, name)){
            this.currUser = new User(username, password, name);
            System.out.println("\nRegistro exitoso. Bienvenido, " + name);
            waitForEnter();
            this.state = State.MENU;
        }else{
            System.out.println("\nEl usuario ya existe.");
            waitForEnter();
        }
    }

    private int printMenu(){
        int opt = -1;
        do {
            System.out.println("=== RutaX Menu ===");
            System.out.println("1. Lista ciudades");
            System.out.println("2. Encontrar camino");
            System.out.println("3. Salir");
            System.out.print("\nEscoje una opcion: ");
            opt = readOption();
            scanner.nextLine();
        }while(opt < 1 || opt > 3);
        return opt;
    }

    private void printCities(){
        System.out.println("=== Ciudades ===");
        List<Location> cities = nodes.getAllLocations();
        for(Location loq : cities){
            System.out.println(loq.getId() + ". " + loq.getName());
        }
        waitForEnter();
        this.state = State.MENU;
    }

    private void printPath(){
        System.out.println("=== Encontrar camino ===");
        System.out.print("Ingrese el origen: ");
        String puntoA = scanner.nextLine();
        System.out.print("Ingrese el destino: ");
        String puntoB = scanner.nextLine();

        Location from = nodes.getLocation(puntoA);
        Location to = nodes.getLocation(puntoB);

        if(from == null || to == null){
            System.out.println("\nUna de las ciudades no existe.");
            waitForEnter();
            this.state = State.MENU;
            return;
        }

        List<Location> path = nodes.dijkstra(from, to);
        if(path.isEmpty()){
            System.out.println("\nNo hay un camino entre ambos puntos.");
        }else{
            System.out.println("\nRuta:");
            for(Location loq : path){
                System.out.print(loq.getName() + " -> ");
            }
            System.out.println("Fin");
        }
        waitForEnter();
        this.state = State.MENU;
    }
}