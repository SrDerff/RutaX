# RutaX — Project Documentation

## What this project is

A Java (Java 25, Maven) practice project with the theme **routes and locations**. It models a road network between named places and applies computing concepts to that domain.

## Current state

```
RutaX/
├── pom.xml                          (Maven, Java 25, UTF-8, no dependencies)
└── src/main/java/com/rutax/
    ├── Main.java                    (entry point: prints "RutaX :)")
    ├── graph/Graph.java             (directed graph, adjacency list)
    └── model/
        ├── Location.java            (vertex: immutable, id + name)
        ├── Road.java                (edge: directed from→to, distance)
        └── Route.java               (result object: id + route list + totalDistance)
```

No tests exist (`src/test` absent, no JUnit dependency). `Main` does not use the graph yet.

### Class semantics

- **`Location`** — immutable. Fields `final int id`, `final String name`. Getters. Has **value semantics** based on `id`: `equals()` compares `id`, `hashCode()` returns `Integer.hashCode(id)`. This makes `Location` safe as a `HashMap`/`HashSet` key.
- **`Road`** — immutable `final Location from`, `final Location to`, `final double distance`. **Directed**: represents only `from → to`, no implicit reverse edge.
- **`Route`** — `final int id`, `final List<Location> route`, `final double totalDistance`. Stores the route list. Getter exposes the list directly (not defensive-copied).
- **`Graph`**:
  - `Map<Location, List<Road>> adjList` — adjacency list (outgoing edges per vertex)
  - `Map<Integer, Location> vertices` — id-indexed lookup
  - `addVertex(Location)`, `addEdge(Road)` — `addEdge` assumes the `from` vertex already exists (`adjList.get(...)` would NPE otherwise)
  - `DFS(Location start)` — iterative, `ArrayDeque` used LIFO + `HashSet<Location>` visited; pushes neighbors in **reverse** so traversal is left-to-right; prints names to stdout
  - `BFS(Location start)` — iterative, `ArrayDeque` used FIFO (push seed, then standard `addLast`/`pop`); prints to stdout
  - `getSize()`, `getNeighbours(Location)`, `getLocation(int id)`
- **`Main`** — entry point, currently a stub.

### Current observations
- `Route.getRoute()` exposes the mutable `ArrayList`.
- `Graph.addEdge` has no guard for missing vertices.
- DFS/BFS print to stdout inside the traversal.
- The domain has `Road.distance` and `Route.totalDistance`, but no shortest-path computation exists yet.