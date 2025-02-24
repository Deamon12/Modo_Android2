package com.chill.modoapp.ui.history;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
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
        ImageView iconView;
        ConstraintLayout event_constraint;

        public EventViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.eventTitleTextView);
            timeTextView = itemView.findViewById(R.id.eventTimeTextView);
            iconView = itemView.findViewById(R.id.item_status_icon);
            event_constraint = itemView.findViewById(R.id.event_constraint);
        }

        public void bind(HistoryListItem.EventItemHistory item) {
            titleTextView.setText(item.getEvent().getTitle());
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a", Locale.getDefault());
            String formattedTime = sdf.format(new Date(item.getEvent().getTimestamp()));
            timeTextView.setText(formattedTime);

            if(item.getEvent().getState() == 0) {
                iconView.setImageResource(R.drawable.check_circle_24dp);
                event_constraint.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(iconView.getContext(), "Schedule followed", Toast.LENGTH_SHORT).show();
                    }
                });
            } else if(item.getEvent().getState() == 1) {
                iconView.setImageResource(R.drawable.error_24dp);
                event_constraint.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(iconView.getContext(), "Schedule missed", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                iconView.setImageResource(R.drawable.warning);
                event_constraint.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(iconView.getContext(), "Schedule late", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }
}
