package com.company;

public class RequestGenerator implements Runnable{
    private int floorNum;
    private Schedule schedule;
    RequestGenerator(int floorNum, Schedule schedule){
        this.floorNum = floorNum;
        this.schedule = schedule;
    }
    public void run(){
        while(true){
            // make a request
            int floor = (int)Math.floor(Math.random() * floorNum);
            int count = (int)Math.floor(Math.random() * 7);
            Passenger[] passengers = new Passenger[count];
            for(int i = 0; i < count; i++){
                int dest = (int)Math.floor(Math.random() * floorNum);
                while (dest == floor){
                    dest = (int)Math.floor(Math.random() * floorNum);
                }
                Direction direction = dest > floor ? Direction.UP : Direction.DOWN;
                passengers[i] = new Passenger(floor, dest, direction);
            }
            schedule.putRequest(passengers);
            schedule.print();
            int delay = (int)(Math.random() * 5000) + 1000;
            try{
                Thread.sleep(delay);
            }
            catch (InterruptedException e){
                System.out.println("Requests thread has been interrupted");
            }
        }
    }
}
