package com.jabaddon.jvmmx.concurrency.examples.msgsenderreceiver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Channel {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageSender.class);
    private final String name;
    private MessageAggregator messageAggregator = new MessageAggregator();

    public Channel(String name) {
        this.name = name;
    }

    public synchronized void send(String message) {
        LOGGER.info("{} sending message: {}", this, message);
        while (messageAggregator.isMessageReady()) {
            try {
                wait();
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                LOGGER.error("Thread interrupted", ex);
            }
        }
        messageAggregator.add(message);
        notify();
    }

    public synchronized String receive() {
        while (!messageAggregator.isMessageReady()) {
            try {
                wait();
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                LOGGER.error("Thread interrupted", ex);
            }
        }
        notify();
        return messageAggregator.getCompleteMessage();
    }

    @Override
    public String toString() {
        return "Channel{" +
                "name='" + name + '\'' +
                '}';
    }
}
