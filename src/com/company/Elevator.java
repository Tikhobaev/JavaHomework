package com.company;

import java.util.concurrent.ConcurrentLinkedQueue;

public class Elevator{
    private int current;
    private int id;
    private Direction direction;
    private ConcurrentLinkedQueue<Passenger> passengers;

    Elevator(int id){
        current = 0;
        this.id = id;
        direction = Direction.FREE;
        passengers = new ConcurrentLinkedQueue<>();
    }

    public void move(){
        current += direction.getValue();
        // check if the elevator should stop on this floor
        for(Passenger p : passengers){
            if(p.getDest() == current){
                // stop on this floor
                passengers.remove(p);
            }
        }
    }

    public int getCurrent() { return current; }

    public Direction getDirection() {
        return direction;
    }

    public int getCount(){
        return passengers.size();
    }

    public void addPassenger(Passenger passenger){
        passengers.add(passenger);
    }

    public void setDirection(Direction direction){ this.direction = direction; }
}
