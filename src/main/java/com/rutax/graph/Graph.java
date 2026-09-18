package com.rutax.graph;
import com.rutax.model.Location;
import com.rutax.model.Road;

import java.util.*;

public class Graph {
    private final Map<Location, List<Road>> adjList;
    private final Map<Integer, Location> vertices;

    public Graph(){
        this.vertices=new HashMap<Integer, Location>();
        this.adjList=new HashMap<Location, List<Road>>();
    }

    public void addVertex(Location _place){
        this.vertices.put(_place.getId(), _place);
        this.adjList.put(_place, new LinkedList<Road>());
    }

    public void addEdge(Road _road){
        this.adjList.get(_road.getFrom()).add(_road);
    }

    public List<Location> DFS(Location start){
        if(start == null || !vertices.containsKey(start.getId())){
            throw new LocationNotFoundException("The starting point doesn't exist.");
        }
        Set<Location> visited = new HashSet<Location>();
        Deque<Location> deq = new ArrayDeque<Location>();

        List<Location> order = new ArrayList<>();
        deq.push(start);
        visited.add(start);

        while(!deq.isEmpty()){
            Location loq = deq.pop();
            order.add(loq);

            //replaced by adding it to the order list
            //System.out.println(loq.getName());

            List<Road> roads = this.adjList.get(loq);

            for(int i = roads.size() - 1; i >= 0; i--){
                Location next = roads.get(i).getTo();

                if(!visited.contains(next)){
                    visited.add(next);
                    deq.push(next);
                }
            }
        }

        return order;
    }

    public List<Location> BFS(Location start){
        if(start == null || !vertices.containsKey(start.getId())){
            throw new LocationNotFoundException("The starting point doesn't exist.");
        }
        Set<Location> visited = new HashSet<Location>();
        Deque<Location> deq = new ArrayDeque<Location>();

        List<Location> order = new ArrayList<>();

        deq.push(start);
        visited.add(start);
        order.add(start);

        while(!deq.isEmpty()){
            Location loq = deq.pop();
            //replaced by adding it to the order list
            //System.out.println(loq.getName());

            for(Road _road : this.adjList.get(loq)){
                if(!visited.contains(_road.getTo())){
                    visited.add(_road.getTo());
                    order.add(_road.getTo());
                    deq.addLast(_road.getTo());
                }
            }
        }

        return order;
    }

    public List<Location> findPathBFS(Location start, Location end) {
        if (start == null || !vertices.containsKey(start.getId()))
            throw new LocationNotFoundException("The starting point doesn't exist");
        if (end == null || !vertices.containsKey(end.getId()))
            throw new LocationNotFoundException("The ending point doesn't exist");

        List<Location> path = new ArrayList<>();
        Map<Location, Location> predecessors = new HashMap<>();

        Set<Location> visited = new HashSet<>();
        Deque<Location> dq = new ArrayDeque<>();

        dq.addLast(start);
        visited.add(start);
        predecessors.put(start, null);

        while (!dq.isEmpty()) {
            Location loq = dq.removeFirst();

            if (loq.equals(end)) break;

            for (Road _road : adjList.get(loq)) {
                if (!visited.contains(_road.getTo())) {
                    predecessors.put(_road.getTo(), loq);
                    visited.add(_road.getTo());
                    dq.addLast(_road.getTo());
                }
            }
        }

        return reconstructPath(end, predecessors);
    }

    private List<Location> reconstructPath(Location end, Map<Location, Location> predecessors) {
        List<Location> path = new ArrayList<>();
        if (!predecessors.containsKey(end))
            return path;

        path.add(end);
        Location current = end;
        while (predecessors.get(current) != null) {
            current = predecessors.get(current);
            path.add(current);
        }
        Collections.reverse(path);
        return path;
    }

    private record PQEntry(Location loc, double dist) {}

    public List<Location> dijkstra(Location start, Location end) {
        if (start == null || !vertices.containsKey(start.getId()))
            throw new LocationNotFoundException("The starting point doesn't exist");
        if (end == null || !vertices.containsKey(end.getId()))
            throw new LocationNotFoundException("The ending point doesn't exist");

        Map<Location, Double> dist = new HashMap<>();
        for (Location v : vertices.values())
            dist.put(v, Double.MAX_VALUE);
        dist.put(start, 0.0);

        Map<Location, Location> predecessors = new HashMap<>();
        predecessors.put(start, null);

        PriorityQueue<PQEntry> pq = new PriorityQueue<>(Comparator.comparingDouble(PQEntry::dist));
        pq.add(new PQEntry(start, 0.0));

        while (!pq.isEmpty()) {
            PQEntry entry = pq.poll();
            Location u = entry.loc();
            if (entry.dist() > dist.get(u)) continue;

            if (u.equals(end)) break;

            for (Road _road : adjList.get(u)) {
                Location v = _road.getTo();
                double newDist = dist.get(u) + _road.getDistance();
                if (newDist < dist.get(v)) {
                    dist.put(v, newDist);
                    predecessors.put(v, u);
                    pq.add(new PQEntry(v, newDist));
                }
            }
        }

        return reconstructPath(end, predecessors);
    }

    public int getSize() { return this.vertices.size(); }

    public List<Road> getNeighbours(Location loq){
        return this.adjList.get(loq);
    }

    public Location getLocation(int _id){ return this.vertices.get(_id); }
}
