package com.jabaddon.jvmmx.concurrency.examples.msgsenderreceiver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MessageSender implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageSender.class);
    private final List<String> messages;
    private final Channel channel;

    public MessageSender(List<String> messages, Channel channel) {
        this.messages = messages;
        this.channel = channel;
    }

    private List<String> getMessages() {
        return messages;
    }

    @Override
    public void run() {
        for (String message : getMessages()) {
            channel.send(message);

            try {
                TimeUnit.SECONDS.sleep(new Random().nextInt(3));
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                LOGGER.error("MessageSender Thread interrupted", ex);
            }
        }
    }
}
