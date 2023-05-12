package org.example.model;

public class Sleep extends Thread {
    public static void handleSleep(long delay,long counter){
        for (int i =1;i<delay/1000;i++){
            try {Thread.sleep(delay/counter);}catch (InterruptedException e){
                System.out.println(e);
            }
        }
    }
}
