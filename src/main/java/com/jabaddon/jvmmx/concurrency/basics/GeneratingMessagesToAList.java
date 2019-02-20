package com.jabaddon.jvmmx.concurrency.basics;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

public class GeneratingMessagesToAList {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();//new Vector<>();
        List<Thread>  threads = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 10; j++) {
                    list.add(Thread.currentThread().getName() + " - Message #" + j);
                    try {
                        TimeUnit.MILLISECONDS.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }, "MyThread-" + i);
            threads.add(thread);
        }

        threads.forEach(Thread::start);

        while (!threads.stream().allMatch(thread -> thread.getState() == Thread.State.TERMINATED));
        //threads.forEach(GeneratingMessagesToAList::join);

        list.forEach(System.out::println);
        System.out.println("Count = " + list.size());
        System.out.println("Current thread name = " + Thread.currentThread().getName());
    }

    private static void join(Thread thread) {
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
