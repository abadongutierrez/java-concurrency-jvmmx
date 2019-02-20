package com.jabaddon.jvmmx.concurrency.examples.msgsenderreceiver;

import java.util.ArrayList;
import java.util.List;

public class MessageAggregator {

    private boolean receivingMessage;
    private final List<String> list = new ArrayList<>();
    private boolean messageReady;

    public void add(String part) {
        if (receivingMessage) {
            if ("]".equals(part)) {
                messageReady();
            } else {
                list.add(part);
            }
        } else if ("[".equals(part)) {
            receivingMessage = true;
        }

        if (".".equals(part)) {
            messageReady();
            list.clear();
            list.add(part);
        }
    }

    private void messageReady() {
        receivingMessage = false;
        messageReady = true;
    }

    public boolean isMessageReady() {
        return messageReady;
    }

    public String getCompleteMessage() {
        String join = String.join(" ", list);
        list.clear();
        reset();
        return join;
    }

    private void reset() {
        receivingMessage = false;
        messageReady = false;
    }
}
