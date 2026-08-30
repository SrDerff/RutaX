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

    public void DFS(Location start){
        Set<Location> visited = new HashSet<Location>();
        Deque<Location> deq = new ArrayDeque<Location>();

        deq.push(start);
        visited.add(start);

        while(!deq.isEmpty()){
            Location loq = deq.pop();

            System.out.println(loq.getName());

            List<Road> roads = adjList.get(loq);

            for(int i = roads.size() - 1; i >= 0; i--){
                Location next = roads.get(i).getTo();

                if(!visited.contains(next)){
                    visited.add(next);
                    deq.push(next);
                }
            }
        }
    }

    public void BFS(Location start){
        Set<Location> visited = new HashSet<Location>();
        Deque<Location> deq = new ArrayDeque<Location>();

        deq.push(start);
        visited.add(start);

        while(!deq.isEmpty()){
            Location loq = deq.pop();
            System.out.println(loq.getName());

            for(Road _road : adjList.get(loq)){
                if(!visited.contains(_road.getTo())){
                    visited.add(_road.getTo());
                    deq.addLast(_road.getTo());
                }
            }
        }
    }

    public int getSize() { return vertices.size(); }
}
