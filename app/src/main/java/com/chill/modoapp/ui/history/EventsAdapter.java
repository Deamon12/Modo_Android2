package com.chill.modoapp.ui.history;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.chill.modoapp.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EventsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<HistoryListItem> items;

    public EventsAdapter(List<HistoryListItem> items) {
        this.items = items;
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).getType();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == HistoryListItem.TYPE_HEADER) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_header, parent, false);
            return new HeaderViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_event, parent, false);
            return new EventViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = items.get(position).getType();
        if (viewType == HistoryListItem.TYPE_HEADER) {
            HistoryListItem.HeaderItemHistory headerItem = (HistoryListItem.HeaderItemHistory) items.get(position);
            ((HeaderViewHolder) holder).bind(headerItem);
        } else {
            HistoryListItem.EventItemHistory eventItem = (HistoryListItem.EventItemHistory) items.get(position);
            ((EventViewHolder) holder).bind(eventItem);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    // ViewHolder for header items (dates)
    public static class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView headerTextView;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            headerTextView = itemView.findViewById(R.id.headerTextView);
        }

        public void bind(HistoryListItem.HeaderItemHistory item) {
            headerTextView.setText(item.getDate());
        }
    }

    // ViewHolder for event items
    public static class EventViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView, timeTextView;

        public EventViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.eventTitleTextView);
            timeTextView = itemView.findViewById(R.id.eventTimeTextView);
        }

        public void bind(HistoryListItem.EventItemHistory item) {
            titleTextView.setText(item.getEvent().getTitle());
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a", Locale.getDefault());
            String formattedTime = sdf.format(new Date(item.getEvent().getTimestamp()));
            timeTextView.setText(formattedTime);
        }
    }
}
