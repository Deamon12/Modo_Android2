package com.chill.modoapp.ui.share;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.chill.modoapp.databinding.FragmentShareBinding;

public class ShareFragment extends Fragment {

    private FragmentShareBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ShareViewModel slideshowViewModel =
                new ViewModelProvider(this).get(ShareViewModel.class);

        binding = FragmentShareBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //final TextView textView = binding.textSlideshow;
        //slideshowViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}