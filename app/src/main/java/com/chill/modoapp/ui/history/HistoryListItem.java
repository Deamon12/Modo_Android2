package com.chill.modoapp.ui.history;

public abstract class HistoryListItem {
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_EVENT = 1;

    public abstract int getType();

    public static class HeaderItemHistory extends HistoryListItem {
        private String date;

        public HeaderItemHistory(String date) {
            this.date = date;
        }

        public String getDate() {
            return date;
        }

        @Override
        public int getType() {
            return TYPE_HEADER;
        }
    }

    public static class EventItemHistory extends HistoryListItem {
        private HistoryEvent historyEvent;

        public EventItemHistory(HistoryEvent historyEvent) {
            this.historyEvent = historyEvent;
        }

        public HistoryEvent getEvent() {
            return historyEvent;
        }

        @Override
        public int getType() {
            return TYPE_EVENT;
        }
    }
}
