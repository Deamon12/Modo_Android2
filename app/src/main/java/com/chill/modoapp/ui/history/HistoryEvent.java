package com.chill.modoapp.ui.history;

public class HistoryEvent {
    private int id;
    private String title;
    private long timestamp;

    public HistoryEvent(int id, String title, long timestamp) {
        this.id = id;
        this.title = title;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public long getTimestamp() {
        return timestamp;
    }
}
