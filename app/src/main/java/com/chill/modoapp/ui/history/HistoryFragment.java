package com.chill.modoapp.ui.history;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chill.modoapp.databinding.FragmentHistoryBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class HistoryFragment extends Fragment {

    private FragmentHistoryBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HistoryViewModel historyViewModel =
                new ViewModelProvider(this).get(HistoryViewModel.class);

        binding = FragmentHistoryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        List<HistoryListItem> items = generateItems();
        EventsAdapter adapter = new EventsAdapter(items);
        recyclerView.setAdapter(adapter);

        //final TextView textView = binding.textGallery;
        //historyViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private List<HistoryListItem> generateItems() {
        List<HistoryListItem> items = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM d", Locale.getDefault());
        Random random = new Random();

        // Loop for 5 consecutive days
        for (int i = 0; i < 5; i++) {
            // Add header item for the day
            String dateStr = dateFormat.format(calendar.getTime());
            items.add(new HistoryListItem.HeaderItemHistory(dateStr));

            // Random number of events (between 1 and 5)
            int eventCount = random.nextInt(5) + 1;
            for (int j = 0; j < eventCount; j++) {
                int hour = random.nextInt(24);
                int minute = random.nextInt(60);

                // Clone calendar to set event time without affecting header date
                Calendar eventCalendar = (Calendar) calendar.clone();
                eventCalendar.set(Calendar.HOUR_OF_DAY, hour);
                eventCalendar.set(Calendar.MINUTE, minute);
                eventCalendar.set(Calendar.SECOND, 0);

                String title = String.format("Event at %02d:%02d", hour, minute);
                HistoryEvent event = new HistoryEvent(j, title, eventCalendar.getTimeInMillis());
                items.add(new HistoryListItem.EventItemHistory(event));
            }
            // Move to the next day
            calendar.add(Calendar.DAY_OF_YEAR, -1);
        }
        return items;
    }
}