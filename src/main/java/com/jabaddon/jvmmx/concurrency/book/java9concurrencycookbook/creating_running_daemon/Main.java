package com.jabaddon.jvmmx.concurrency.book.java9concurrencycookbook.creating_running_daemon;

import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;

public class Main {
    public static void main(String[] args) {
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        System.out.printf("Available Processors %d\n", availableProcessors);
        Deque<Event> deque = new ConcurrentLinkedDeque<>();

        WriterTask writer = new WriterTask(deque);
        for (int i = 0; i < availableProcessors; i++) {
            Thread thread = new Thread(writer, "WriterTask-" + i);
            thread.start();
        }
        CleanerTask cleaner = new CleanerTask(deque);
        cleaner.start();
    }
}
