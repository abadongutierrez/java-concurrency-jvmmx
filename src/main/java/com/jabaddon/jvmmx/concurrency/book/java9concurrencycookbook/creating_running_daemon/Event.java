package com.jabaddon.jvmmx.concurrency.book.java9concurrencycookbook.creating_running_daemon;

import java.util.Date;

public class Event {
    private Date date;
    private String event;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }
}