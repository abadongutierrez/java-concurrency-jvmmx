package com.jabaddon.jvmmx.concurrency.book.java9concurrencycookbook.controlling_interruption;

import java.io.File;

public class FileSearch implements Runnable {
    private String initPath;
    private String fileName;

    public FileSearch(String initPath, String fileName) {
        this.initPath = initPath;
        this.fileName = fileName;
    }

    @Override
    public void run() {
        File file = new File(initPath);
        if (file.isDirectory()) {
            try {
                directoryProcess(file);
            } catch (InterruptedException e) {
                System.out.printf("%s: The search has been interrupted", Thread.currentThread().getName());
            }
        }
    }

    private void directoryProcess(File file) throws InterruptedException {
        System.out.printf("Processing directory: %s\n", file.getName());
        File list[] = file.listFiles();
        if (list != null) {
            for (File aList : list) {
                if (aList.isDirectory()) {
                    directoryProcess(aList);
                } else {
                    fileProcess(aList);
                }
            }
        }
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
    }

    private void fileProcess(File file) throws InterruptedException {
        System.out.printf("Processing file: %s\n", file.getName());
        if (file.getName().equals(fileName)) {
            System.out.printf("%s : %s\n", Thread.currentThread().getName(), file.getAbsolutePath());
        }
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
    }
}
