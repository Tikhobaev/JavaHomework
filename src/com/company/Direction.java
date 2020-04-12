package com.company;

public enum Direction {
    DOWN(-1), FREE(0), UP(1);
    private int value;

    Direction(int value){
        this.value = value;
    }

    public int getValue(){
        return value;
    }
}
