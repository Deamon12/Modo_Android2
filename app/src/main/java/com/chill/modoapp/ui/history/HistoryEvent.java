package com.chill.modoapp.ui.history;

public class HistoryEvent {
    private int id;
    private String title;
    private long timestamp;
    private int state;

    public HistoryEvent(int id, String title, long timestamp, int state) {
        this.id = id;
        this.title = title;
        this.timestamp = timestamp;
        this.state = state;
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
    public int getState() {
        return state;
    }
}
