package com.rutax.model;

public class Road {
    private final Location from;
    private final Location to;
    private final double distance;

    public Road(Location _from, Location _to, double _distance){
       this.from=_from;
       this.to=_to;
       this.distance=_distance;
    }

    public Location getFrom(){ return this.from; }
    public Location getTo(){ return this.to; }
    public double getDistance() { return this.distance; }
}
