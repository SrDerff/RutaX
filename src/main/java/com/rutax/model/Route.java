package com.rutax.model;

import java.util.ArrayList;
import java.util.List;

public class Route {
    private final int id;
    private final List<Location> route;
    private final double totalDistance;

    public Route(int _id, List<Location>_route, double _totalDist){
        this.id=_id;
        this.route=_route;
        this.totalDistance=_totalDist;
    }

    public int getId(){ return this.id; }
    public List<Location> getRoute(){ return this.route; }
    public double getTotalDistance() { return this.totalDistance; }
}
