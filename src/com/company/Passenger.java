package com.company;

public class Passenger{
    private int from;
    private int dest;
    private Direction direction;

    public int getFrom() {
        return from;
    }

    public int getDest() {
        return dest;
    }

    public Direction getDirection() {
        return direction;
    }

    Passenger(int from, int dest, Direction direction){
        this.from = from;
        this.dest = dest;
        this.direction = direction;
    }
}
