package com.company;
import java.util.Scanner;

public class ElevatorsMain {

    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        System.out.println("How many floors?");
        int floorsNum = in.nextInt();
        System.out.println("How many elevators?");
        int elevNum = in.nextInt();
        if (floorsNum > 0 && elevNum > 0){
            Schedule shedule = new Schedule(elevNum, floorsNum);
            RequestGenerator elevSheduler = new RequestGenerator(floorsNum, shedule);
            Dispatcher disp = new Dispatcher(elevNum, 10, floorsNum, shedule);
            Thread requestsThread = new Thread(elevSheduler);
            Thread elevThread = new Thread(disp);
            requestsThread.start();
            elevThread.start();
        }
    }
}
