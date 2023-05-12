package org.example.model;

public class Sleep extends Thread {
    public static void handleSleep(long delay){
        for (int i =1;i<delay/1000;i++){
            try {Thread.sleep(delay);}catch (InterruptedException e){
                System.out.println(e);
            }
        }
    }
}
