package org.example.model;

public class Sleep extends Thread {
    public static void handleSleep(){
            try {Thread.sleep(5000);}catch (InterruptedException e){
                System.out.println(e);
            }

    }
}