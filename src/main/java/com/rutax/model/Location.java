package com.rutax.model;

public class Location {
    private final int id;
    private final String name;

    public Location(int _id, String _name){
        this.id=_id;
        this.name=_name;
    }

    public int getId(){ return this.id; }
    public String getName(){ return this.name; }
}
