package com.chill.modoapp.ui.manage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.chill.modoapp.databinding.FragmentManageBinding;
import com.opencsv.CSVReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;

public class ManageFragment extends Fragment {

    private FragmentManageBinding binding;
    private ArrayList pills = new ArrayList();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ManageViewModel slideshowViewModel = new ViewModelProvider(this).get(ManageViewModel.class);

        loadPillsCsv();
        binding = FragmentManageBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, pills);
        final AutoCompleteTextView tv1 = binding.adjustAutocomplete;
        tv1.setAdapter(adapter);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void loadPillsCsv() {
        try {

            Reader targetReader = new InputStreamReader(getActivity().getAssets().open("pills.csv"));
            CSVReader reader = new CSVReader(targetReader);
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                //System.out.println(nextLine[0] + nextLine[1] + "etc...");
                pills.add(nextLine[0]);
            }
        } catch (IOException e) {

        }
    }
}