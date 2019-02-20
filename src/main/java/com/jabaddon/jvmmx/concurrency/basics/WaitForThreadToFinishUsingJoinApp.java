package com.jabaddon.jvmmx.concurrency.basics;

import java.util.concurrent.TimeUnit;

public class WaitForThreadToFinishUsingJoinApp {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();

        thread.join(); // this stops the main thread execution

        System.out.println("Finished!");
    }
}
