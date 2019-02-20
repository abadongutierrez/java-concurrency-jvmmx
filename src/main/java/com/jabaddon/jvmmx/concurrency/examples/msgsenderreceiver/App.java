package com.jabaddon.jvmmx.concurrency.examples.msgsenderreceiver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

public class App {
    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) throws InterruptedException {
        LOGGER.info("App starting...");
        Channel channel = new Channel("MyChannel");
        List<String> messages = Arrays.asList("[", "This", "is", "first", "message", "]", "ignore", "trash", "[", "Second", "message", "]", ".");
        MessageSender messageSender = new MessageSender(messages, channel);
        MessageReceiver messageReceiver = new MessageReceiver(channel);

        Thread threadSender = new Thread(messageSender);
        threadSender.start();
        Thread threadReceiver = new Thread(messageReceiver);
        threadReceiver.start();


        threadSender.join();
        threadReceiver.join();
        LOGGER.info("App end!");
    }
}
