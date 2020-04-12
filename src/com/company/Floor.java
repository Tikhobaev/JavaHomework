package com.company;

import java.util.concurrent.ConcurrentLinkedQueue;

public class Floor {
    private int number;  // floor number
    private ConcurrentLinkedQueue<Passenger> upPassengers;
    private ConcurrentLinkedQueue<Passenger> downPassengers;
    Floor(int number){
        upPassengers = new ConcurrentLinkedQueue<>();
        downPassengers = new ConcurrentLinkedQueue<>();
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public void addPassenger(Passenger person){
        if(person.getDirection() == Direction.UP){
            upPassengers.add(person);
        }
        else{
            downPassengers.add(person);
        }
    }

    public Passenger popPassenger(Direction direction){
        if(direction == Direction.DOWN){
            return downPassengers.remove();
        }
        else{
            return upPassengers.remove();
        }
    }

    public int getPassengersNum(Direction direction){
        if(direction == Direction.DOWN){
            return downPassengers.size();
        }
        else{
            return upPassengers.size();
        }
    }

}
