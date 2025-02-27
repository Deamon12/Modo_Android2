package com.chill.modoapp.ui.home;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.os.Handler;

import com.chill.modoapp.MainActivity;
import com.chill.modoapp.NotificationHelper;
import com.chill.modoapp.Pill;
import com.chill.modoapp.PillEventListener;
import com.chill.modoapp.R;
import com.chill.modoapp.databinding.FragmentHomeBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private static String TAG = "HomeFragment";
    private FragmentHomeBinding binding;
    private CardView pill1Card, pill2Card, pill3Card, pill4Card;
    private TextView timenowText;
    private TextView pill1NameText, pill2NameText, pill3NameText, pill4NameText;
    private TextView pill1DetailsTV, pill2DetailsTV, pill3DetailsTV, pill4DetailsTV;
    private ImageView pill1DetailsIV, pill2DetailsIV, pill3DetailsIV, pill4DetailsIV;
    private ImageView hamburger;

    private MutableLiveData<String> pill1TextLive = new MutableLiveData<>();
    private MutableLiveData<String> pill2TextLive = new MutableLiveData<>();
    private MutableLiveData<String> pill3TextLive = new MutableLiveData<>();
    private MutableLiveData<String> pill4TextLive = new MutableLiveData<>();


    private MutableLiveData<Drawable> pill1ImageLive = new MutableLiveData<>();
    private MutableLiveData<Drawable> pill2ImageLive = new MutableLiveData<>();
    private MutableLiveData<Drawable> pill3ImageLive = new MutableLiveData<>();
    private MutableLiveData<Drawable> pill4ImageLive = new MutableLiveData<>();

    private Drawable waitingDraw = null;
    private Drawable readyDraw = null;

    // Front time looper
    private Handler handler = new Handler();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        waitingDraw = ContextCompat.getDrawable(getActivity(), R.drawable.schedule_blue);
        readyDraw = ContextCompat.getDrawable(getActivity(), R.drawable.check_circle_24dp);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        timenowText = binding.timenowText;
        homeViewModel.getText().observe(getViewLifecycleOwner(), timenowText::setText);
        startTimeLooper();

        Log.d(TAG, "onCreateView: " + ((MainActivity) requireActivity()).pillList.get(0));

        hamburger = binding.hamburger;
        hamburger.setOnClickListener(this);

        pill1NameText = binding.pill1NameText;
        pill2NameText = binding.pill2NameText;
        pill3NameText = binding.pill3NameText;
        pill4NameText = binding.pill4NameText;

        pill1Card = binding.pill1Card;
        pill1Card.setOnClickListener(this);
        pill2Card = binding.pill2Card;
        pill2Card.setOnClickListener(this);
        pill3Card = binding.pill3Card;
        pill3Card.setOnClickListener(this);
        pill4Card = binding.pill4Card;
        pill4Card.setOnClickListener(this);

        pill1DetailsTV = binding.pill1DetailsText;
        pill2DetailsTV = binding.pill2DetailsText;
        pill3DetailsTV = binding.pill3DetailsText;
        pill4DetailsTV = binding.pill4DetailsText;

        pill1TextLive.observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(String newText) {
                pill1DetailsTV.setText(newText);
            }
        });

        pill2TextLive.observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(String newText) {
                pill2DetailsTV.setText(newText);
            }
        });

        pill3TextLive.observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(String newText) {
                pill3DetailsTV.setText(newText);
            }
        });

        pill4TextLive.observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(String newText) {
                pill4DetailsTV.setText(newText);
            }
        });

        pill1DetailsIV = binding.pill1StatusIcon;
        pill2DetailsIV = binding.pill2StatusIcon;
        pill3DetailsIV = binding.pill3StatusIcon;
        pill4DetailsIV = binding.pill4StatusIcon;


        pill1ImageLive.observe(getActivity(), new Observer<Drawable>() {
            @Override
            public void onChanged(Drawable drawable) {
                pill1DetailsIV.setImageDrawable(drawable);
            }
        });

        pill2ImageLive.observe(getActivity(), new Observer<Drawable>() {
            @Override
            public void onChanged(Drawable drawable) {
                pill2DetailsIV.setImageDrawable(drawable);
            }
        });

        pill3ImageLive.observe(getActivity(), new Observer<Drawable>() {
            @Override
            public void onChanged(Drawable drawable) {
                pill3DetailsIV.setImageDrawable(drawable);
            }
        });

        pill4ImageLive.observe(getActivity(), new Observer<Drawable>() {
            @Override
            public void onChanged(Drawable drawable) {
                pill4DetailsIV.setImageDrawable(drawable);
            }
        });

        List<Pill> pillList = ((MainActivity)getActivity()).pillList;

        Pill pill1 = pillList.get(0);
        Pill pill2 = pillList.get(1);
        Pill pill3 = pillList.get(2);
        Pill pill4 = pillList.get(3);

        setupPillEventHandler(pill1, pill1TextLive, pill1ImageLive, getContext());
        setupPillEventHandler(pill2, pill2TextLive, pill2ImageLive, getContext());
        setupPillEventHandler(pill3, pill3TextLive, pill3ImageLive, getContext());
        setupPillEventHandler(pill4, pill4TextLive, pill4ImageLive, getContext());

        pill1NameText.setText(pill1.pillName);
        pill2NameText.setText(pill2.pillName);
        pill3NameText.setText(pill3.pillName);
        pill4NameText.setText(pill4.pillName);

        return root;
    }

    private void setupPillEventHandler(Pill pill, MutableLiveData<String> textLive, MutableLiveData<Drawable> detailsIV, Context context) {
        pill.eventListener = new PillEventListener() {
            @Override
            public void event(String message) {
                Log.d(TAG, pill.pillName + " event: " + message);
                if(message.equalsIgnoreCase("Done")) {
                    pill.currentStatus = Pill.Status.ready;
                    updateText(textLive, "Ready");
                    updateImage(detailsIV, readyDraw);
                    NotificationHelper.showNotification(context, pill);
                } else {
                    pill.currentStatus = Pill.Status.upcoming;
                    updateText(textLive, pill.schedule + " - " + pill.getTimeDetails());
                    updateImage(detailsIV, waitingDraw);
                }
            }
        };
    }

    private void updateText(MutableLiveData<String> textLiveData, String text) {
        textLiveData.postValue(text);
    }

    private void updateImage(MutableLiveData<Drawable> liveView, Drawable draw) {
        liveView.postValue(draw);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void startTimeLooper() {
        handler.post(runnable);
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");

            // Insert custom code here
            timenowText.setText(format1.format(cal.getTime()));

            // Repeat
            handler.postDelayed(runnable, 1000);
        }
    };

    @Override
    public void onClick(View v) {
        if(v == pill1Card) {
            Pill pill1 = ((MainActivity)getActivity()).pillList.get(0);

            if(pill1.currentStatus != Pill.Status.upcoming) {
                pill1DetailsIV.setImageResource(R.drawable.schedule_blue);
                pill1.currentStatus = Pill.Status.upcoming;
                pill1.startTimer();
            }
        }
        else if(v == pill2Card) {
            Pill pill = ((MainActivity)getActivity()).pillList.get(1);
            if(pill.currentStatus != Pill.Status.upcoming) {
                pill1DetailsIV.setImageResource(R.drawable.schedule_blue);
                pill.currentStatus = Pill.Status.upcoming;
                pill.startTimer();
            }
        }
        else if(v == pill3Card) {
            Pill pill = ((MainActivity)getActivity()).pillList.get(2);
            if(pill.currentStatus != Pill.Status.upcoming) {
                pill1DetailsIV.setImageResource(R.drawable.schedule_blue);
                pill.currentStatus = Pill.Status.upcoming;
                pill.startTimer();
            }
        }
        else if(v == pill4Card) {
            Pill pill = ((MainActivity)getActivity()).pillList.get(3);
            if(pill.currentStatus != Pill.Status.upcoming) {
                pill1DetailsIV.setImageResource(R.drawable.schedule_blue);
                pill.currentStatus = Pill.Status.upcoming;
                pill.startTimer();
            }
        }
        else if(v == hamburger) {
            ((MainActivity)getActivity()).drawer.openDrawer(GravityCompat.START);
        }

    }
}