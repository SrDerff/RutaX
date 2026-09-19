# RutaX

A console application that models a Peruvian road network and finds routes between cities using classic **data structures and algorithms** (Graphs, BFS, DFS, Dijkstra). Built in **Java 25** with Maven, zero third-party dependencies.

RutaX lets you register or log in, browse the connected cities, and ask for the shortest path between any two of them. Under the hood it runs a Dijkstra algorithm over a directed weighted graph.

## Features

- **Directed weighted graph** of Peruvian cities, stored as an adjacency list.
  - Vertices: `Location` (id + name), keyed by name.
  - Edges: `Road` (from → to + distance).
- **Graph traversals**
  - Iterative **DFS** and **BFS**, both returning the visited order as a `List<Location>`.
  - **BFS pathfinding** for the shortest path in hops (unweighted).
  - **Dijkstra** for the shortest path by distance (weighted).
- **User authentication**
  - Register and log in, backed by a plain-text user store (`src/main/resources/users/users_list.txt`).
  - Duplicate usernames are rejected at registration.
- **Interactive menu** driven by a small finite state machine (`WELCOME → LOGIN/REGISTER → MENU → LIST_CITIES / FIND_ROUTE → EXIT`).
- **Zero external dependencies** — pure Java 25 (seed graph, algorithms, auth) plus the standard library.

## Road network

The demo network connects five Peruvian cities with six one-way roads:

```
        Lima ──100──▶ Trujillo ──400──▶ Chiclayo
         │                ▲                │
        200              300              250
         ▼                │                ▼
     Arequipa ──150───────┴─────────────▶ Cuzco
```

| From      | To        | Distance |
| --------- | --------- | -------- |
| Lima      | Trujillo  | 100      |
| Lima      | Arequipa  | 200      |
| Trujillo  | Chiclayo  | 400      |
| Arequipa  | Cuzco     | 150      |
| Arequipa  | Trujillo  | 300      |
| Chiclayo  | Cuzco     | 250      |

## Getting started

### Prerequisites

- **JDK 25** or later
- **Maven 3.x**

### Build and run

```bash
git clone https://github.com/<your-username>/RutaX.git
cd RutaX

# compile
mvn compile

# run the console app
java -cp target/classes com.rutax.Main
```

> You can also open the project in IntelliJ IDEA and just run the `main` method in `com.rutax.Main`.

### Default accounts

Some accounts are pre-seeded in `users_list.txt`:

| Username | Password | Name |
| -------- | -------- | ---- |
| `derff`  | `123`    | alex |
| `herz`   | `456`    | rony |
| `alex`   | `123`    | alex |

You can also create your own from the **Registrarse** option in the menu.

> **Note:** passwords are stored in plain text in a local file. This project is a practice exercise, not production-ready security.

## Project structure

```
RutaX/
├── pom.xml                                  (Maven, Java 25, UTF-8, no dependencies)
└── src/
    ├── main/
    │   ├── java/com/rutax/
    │   │   ├── Main.java                    (entry point — runs the RouteService loop)
    │   │   ├── auth/
    │   │   │   ├── Auth.java                (register/login against users_list.txt)
    │   │   │   └── User.java                (username, password, name)
    │   │   ├── service/
    │   │   │   └── RouteService.java        (console state machine, menu & seeding)
    │   │   ├── graph/
    │   │   │   ├── Graph.java               (adjacency-list directed graph)
    │   │   │   └── LocationNotFoundException.java
    │   │   └── model/
    │   │       ├── Location.java            (vertex — id + name, keyed by name)
    │   │       ├── Road.java                (edge — directed from → to, distance)
    │   │       └── Route.java               (result — id, location list, total distance)
    │   └── resources/users/
    │       └── users_list.txt               (plain-text user store)
```

## How the algorithms work

- **Graph** — built as `Map<Location, List<Road>>` (outgoing adjacency list) plus `Map<String, Location>` for name-based lookup. `Location` overrides `equals()`/`hashCode()` based on **name**, so it is safe as a `HashMap`/`HashSet` key.

- **DFS / BFS** — iterative traversals using an `ArrayDeque`. DFS pushes neighbors in reverse so the visit order is left-to-right; BFS uses it as a FIFO queue. Both validate the start vertex and throw `LocationNotFoundException` if it is missing. Traversals return the order list instead of printing, keeping the graph layer UI-free.

- **BFS shortest path** — stores predecessors in a `HashMap` while exploring; a shared `reconstructPath()` walks the predecessor chain back from the target and reverses it.

- **Dijkstra** — priority queue of `(Location, distance)` entries, a `dist[]` map initialized to +∞, and the same predecessor reconstruction for the outcome. Stops as soon as the destination is settled.

- **RouteService** — an `enum State` acts as a tiny state machine; each `run()` tick renders the current screen, collects input, and moves to the next state. The seed graph is built once in the constructor.

## Why this project

This repository was built as a practice exercise to apply core DSA concepts — graphs, BFS/DFS, shortest paths, hashing basics, and iterative traversal — to a concrete, domain-driven problem (route planning), with no external libraries.

## License

This project is for learning purposes. Feel free to use it as a reference for your own DSA practice :).
