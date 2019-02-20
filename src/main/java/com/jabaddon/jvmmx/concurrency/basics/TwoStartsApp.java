package com.jabaddon.jvmmx.concurrency.basics;

import java.util.concurrent.TimeUnit;

// What happend when you try to start an already started thread?
public class TwoStartsApp {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        thread.start();
        thread.start(); // throws java.lang.IllegalThreadStateException

        Thread.currentThread().join();
        System.out.println("Done!");
    }
}
