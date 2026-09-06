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

    @Override
    public boolean equals(Object obj){
        if(this==obj) return true;

        if(obj == null || getClass() != obj.getClass()) return false;

        Location loq = (Location) obj;

        return id==(loq.getId());
    }

    @Override
    public int hashCode(){
        return Integer.hashCode(this.id);
    }

}
