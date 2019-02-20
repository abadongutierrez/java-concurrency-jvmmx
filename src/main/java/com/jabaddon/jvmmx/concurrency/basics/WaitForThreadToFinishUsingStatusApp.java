package com.jabaddon.jvmmx.concurrency.basics;

import java.util.concurrent.TimeUnit;

public class WaitForThreadToFinishUsingStatusApp {

    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();

        while (thread.getState() != Thread.State.TERMINATED) {
            System.out.println("Status? " + thread.getState());
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Finished!");
    }
}
