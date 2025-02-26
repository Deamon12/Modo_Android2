package com.chill.modoapp.ui.manage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.chill.modoapp.MainActivity;
import com.chill.modoapp.Pill;
import com.chill.modoapp.databinding.FragmentManageBinding;
import com.opencsv.CSVReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class ManageFragment extends Fragment {

    private FragmentManageBinding binding;
    private ArrayList pills = new ArrayList();

    private TextView pill1NameTV, pill2NameTV, pill3NameTV, pill4NameTV;
    private TextView pill1InstructTV, pill2InstructTV, pill3InstructTV, pill4InstructTV;
    private TextView pill1QuantityTV, pill2QuantityTV, pill3QuantityTV, pill4QuantityTV;
    private TextView pill1ScheduleTV, pill2ScheduleTV, pill3ScheduleTV, pill4ScheduleTV;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ManageViewModel manageViewModel = new ViewModelProvider(this).get(ManageViewModel.class);

        loadPillsCsv();
        binding = FragmentManageBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        pill1NameTV = binding.pill1NameText;
        pill2NameTV = binding.pill2NameText;
        pill3NameTV = binding.pill3NameText;
        pill4NameTV = binding.pill4NameText;

        pill1InstructTV = binding.pill1DescriptionText;
        pill2InstructTV = binding.pill2DescriptionText;
        pill3InstructTV = binding.pill3DescriptionText;
        pill4InstructTV = binding.pill4DescriptionText;

        pill1QuantityTV = binding.pill1QuantityText;
        pill2QuantityTV = binding.pill2QuantityText;
        pill3QuantityTV = binding.pill3QuantityText;
        pill4QuantityTV = binding.pill4QuantityText;

        pill1ScheduleTV = binding.pill1ScheduleText;
        pill2ScheduleTV = binding.pill2ScheduleText;
        pill3ScheduleTV = binding.pill3ScheduleText;
        pill4ScheduleTV = binding.pill4ScheduleText;

        List<Pill> pillList = ((MainActivity)getActivity()).pillList;

        Pill pill1 = pillList.get(0);
        pill1NameTV.setText(pill1.pillName);
        pill1InstructTV.setText("Instruction: " + pill1.instruction);
        pill1QuantityTV.setText("Quantity: " + pill1.quantity);
        pill1ScheduleTV.setText("Schedule: " + pill1.schedule);

        Pill pill2 = pillList.get(1);
        pill2NameTV.setText(pill2.pillName);
        pill2InstructTV.setText("Instruction: " + pill2.instruction);
        pill2QuantityTV.setText("Quantity: " + pill2.quantity);
        pill2ScheduleTV.setText("Schedule: " + pill2.schedule);

        Pill pill3 = pillList.get(2);
        pill3NameTV.setText(pill3.pillName);
        pill3InstructTV.setText("Instruction: " + pill3.instruction);
        pill3QuantityTV.setText("Quantity: " + pill3.quantity);
        pill3ScheduleTV.setText("Schedule: " + pill3.schedule);

        Pill pill4 = pillList.get(3);
        pill4NameTV.setText(pill4.pillName);
        pill4InstructTV.setText("Instruction: " + pill4.instruction);
        pill4QuantityTV.setText("Quantity: " + pill4.quantity);
        pill4ScheduleTV.setText("Schedule: " + pill4.schedule);



        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, pills);
        //final AutoCompleteTextView tv1 = binding.adjustAutocomplete;
        //tv1.setAdapter(adapter);
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