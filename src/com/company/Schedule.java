package com.company;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class Schedule{
    ConcurrentHashMap<Integer, Floor> floors;  // use ConcurrentHashMap just for learning
    private ArrayList<Elevator> elevators;
    private int floorNum;
    private int elevNum;
    Schedule(int elevNum, int floorNum){
        this.floorNum = floorNum;
        this.elevNum = elevNum;
        floors = new ConcurrentHashMap<>();
        for(int i = 0; i < floorNum; i++){
            floors.put(i, new Floor(i));
        }
        elevators = new ArrayList<>();
        for(int i = 0; i < elevNum; i++) {
            elevators.add(i, new Elevator(i));
        }
    }

    public void putRequest(Passenger[] passengers){
        for(int i = 0; i < passengers.length; i++){
            floors.get(passengers[i].getFrom()).addPassenger(passengers[i]);
        }
        print();
    }

    synchronized public void print(){
        System.out.println("********************************************************************");
        System.out.printf("%15s%15s%15s", "Floor", "UpPasengers", "DownPassengers");
        for (int i = 0; i < elevNum; i++){
            System.out.printf("%4d", i+1);
        }
        for (int i = floorNum-1; i >= 0; i--) {
            System.out.println();
            System.out.printf("%15d%15d%15d", i+1,
                    floors.get(i).getPassengersNum(Direction.UP),
                    floors.get(i).getPassengersNum(Direction.DOWN));
            for (int j = 0; j < elevNum; j++){
                System.out.printf("%4s", elevators.get(j).getCurrent() == i ? elevators.get(j).getCount() : " ");
            }
        }
        System.out.println();
    }

    public ArrayList<Elevator> getElevators(){
        return elevators;
    }

    public int getUpRequestsCount(int floor){
        int result = 0;
        for(int i = floor; i < floorNum; i++){
            result += floors.get(i).getPassengersNum(Direction.UP) +
                      floors.get(i).getPassengersNum(Direction.DOWN);
        }
        return result;
    }

    public int getDownRequestsCount(int floor){
        int result = 0;
        for(int i = 0; i <= floor; i++){
            result += floors.get(i).getPassengersNum(Direction.DOWN) +
                      floors.get(i).getPassengersNum(Direction.UP);
        }
        return result;
    }

    public Floor getFloor(int num){
        return floors.get(num);
    }
}
