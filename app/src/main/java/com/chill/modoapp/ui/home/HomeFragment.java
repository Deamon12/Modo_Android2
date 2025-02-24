package com.chill.modoapp.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import android.os.Handler;

import com.chill.modoapp.MainActivity2;
import com.chill.modoapp.databinding.FragmentHomeBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class HomeFragment extends Fragment {

    private static String TAG = "HomeFragment";
    private FragmentHomeBinding binding;
    private TextView timenowText;

    // Front time looper
    private Handler handler = new Handler();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        timenowText = binding.timenowText;
        homeViewModel.getText().observe(getViewLifecycleOwner(), timenowText::setText);
        startTimeLooper();

        Log.d(TAG, "onCreateView: " + ((MainActivity2) requireActivity()).pillList.get(0));

        //final TextView textView = binding.textHome;
        //homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
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

}