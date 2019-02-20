package com.jabaddon.jvmmx.concurrency.examples.msgsenderreceiver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MessageReceiver implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageReceiver.class);
    public static final String END = ".";
    private final Channel channel;

    public MessageReceiver(Channel channel) {
        this.channel = channel;
    }

    @Override
    public void run() {
        String message = channel.receive();
        while (!END.equals(message)) {
            LOGGER.info("Message received: {}", message);

            try {
                TimeUnit.SECONDS.sleep(new Random().nextInt(3));
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                LOGGER.error("MessageSender Thread interrupted", ex);
            }
            
            message = channel.receive();
        }
    }
}
