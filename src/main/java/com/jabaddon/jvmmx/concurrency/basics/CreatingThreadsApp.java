package com.jabaddon.jvmmx.concurrency.basics;

public class CreatingThreadsApp {
    public static void main(String[] args) {
        MyThreadFromThread threadFromThread = new MyThreadFromThread();
        threadFromThread.start();

        MyThreadFromRunnable threadFromRunnable = new MyThreadFromRunnable();
        Thread thread = new Thread(threadFromRunnable);
        thread.start();
    }
}

class MyThreadFromThread extends Thread {

    @Override
    public void run() {
        System.out.println("Hello from Thread!");
    }
}

class MyThreadFromRunnable implements Runnable {

    @Override
    public void run() {
        System.out.println("Hello from Runnable!");
    }
}
