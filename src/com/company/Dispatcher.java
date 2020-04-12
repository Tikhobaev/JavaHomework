package com.company;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Dispatcher implements Runnable{
    private int maxCount;  // max count of people in elevator
    private Schedule schedule;
    private int floorNum;
    private ArrayList<Elevator> elevators;
    private ArrayList<Integer> elevIds;
    Dispatcher(int elevNum, int maxCount, int floornum, Schedule schedule){
        this.floorNum = floornum;
        this.schedule = schedule;
        this.maxCount = maxCount;
        elevators = schedule.getElevators();
        elevIds = new ArrayList<>();
        for(int i = 0; i < elevNum; i++){
            elevIds.add(i);
        }
    }
    public void run(){
        while(true) {
            // sort elevators indexes by count of passengers elevators
            Collections.sort(elevIds, new Comparator<Integer>(){
                public int compare(Integer i1, Integer i2) {
                    return elevators.get(i2).getCount() - elevators.get(i1).getCount();
                }
            });
            for (int index : elevIds){
                elevators.get(index).move();
            }
            System.out.println();

            int counter = 0;  // the lower value, the more passengers (compare to other elevators)
            for(int index : elevIds){
                Elevator elev = elevators.get(index);
                Floor current = schedule.getFloor(elev.getCurrent());
                int freeSpace = maxCount - elev.getCount();
                if(freeSpace > 0){
                    if(freeSpace == maxCount){
                        // if elev is empty
                        if (elev.getDirection() == Direction.UP &&
                                schedule.getUpRequestsCount(elev.getCurrent()) < counter *  maxCount){
                            // if few requests on the upper floors
                            elev.setDirection(Direction.FREE);
                        }
                        else{
                            if (elev.getDirection() == Direction.DOWN &&
                                    schedule.getDownRequestsCount(elev.getCurrent()) < counter *  maxCount){
                                // if few requests on the lower floors
                                elev.setDirection(Direction.FREE);
                            }
                        }
                    }
                    // check the current floor
                    if(current.getNumber() == 0){
                        // if elev at 0 floor
                        if (schedule.getUpRequestsCount(elev.getCurrent()) > counter *  maxCount){
                            // if there are a lot of requests on the upper floors-> going up
                            elev.setDirection(Direction.UP);
                        }
                        else{
                            elev.setDirection(Direction.FREE);
                        }
                    }

                    if(current.getNumber() == floorNum-1){
                        // if elev at the top floor
                        if (schedule.getDownRequestsCount(elev.getCurrent()) > counter * maxCount){
                            // if there are a lot of requests on the lower floors-> going down
                            elev.setDirection(Direction.DOWN);
                        }
                        else{
                            elev.setDirection(Direction.FREE);
                        }
                    }
                    if(elev.getDirection() == Direction.FREE){
                        // set a direction
                        if(current.getPassengersNum(Direction.UP) > current.getPassengersNum(Direction.DOWN)
                            && schedule.getUpRequestsCount(elev.getCurrent()) > counter * maxCount){
                            // if in the current floor "up" passengers more than "down"
                            elev.setDirection(Direction.UP);
                        }
                        else{
                            if (current.getPassengersNum(Direction.UP) <= current.getPassengersNum(Direction.DOWN)
                                  && schedule.getDownRequestsCount(elev.getCurrent()) > counter * maxCount){
                                elev.setDirection(Direction.DOWN);
                            }
                        }
                    }
                    // if there are passengers on the current floor -> pick up them
                    while(current.getPassengersNum(elev.getDirection()) != 0 &&
                            elev.getCount() < maxCount) {
                        elev.addPassenger(current.popPassenger(elev.getDirection()));
                    }
                }
                counter++;
            }
            try{
                Thread.sleep(1000);
            }
            catch (InterruptedException e){
                System.out.println("Requests thread has been interrupted");
            }
            schedule.print();
        }
    }
}
