package com.chill.modoapp.ui.manage;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.chill.modoapp.MainActivity;
import com.chill.modoapp.Pill;
import com.chill.modoapp.R;
import com.chill.modoapp.databinding.FragmentManageBinding;
import com.opencsv.CSVReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class ManageFragment extends Fragment implements View.OnClickListener {

    private FragmentManageBinding binding;
    private ArrayList pillsCompleteList = new ArrayList();

    private CardView pill1Card, pill2Card, pill3Card, pill4Card;
    private TextView pill1NameTV, pill2NameTV, pill3NameTV, pill4NameTV;
    private TextView pill1InstructTV, pill2InstructTV, pill3InstructTV, pill4InstructTV;
    private TextView pill1QuantityTV, pill2QuantityTV, pill3QuantityTV, pill4QuantityTV;
    private TextView pill1ScheduleTV, pill2ScheduleTV, pill3ScheduleTV, pill4ScheduleTV;

    private ImageView pill1Edit, pill2Edit, pill3Edit, pill4Edit;
    private ImageView pill1remove, pill2remove, pill3remove, pill4remove;

    private AutoCompleteTextView pillNameEditText;
    private Spinner spinner;

    private View pill1Slot, pill2Slot, pill3Slot, pill4Slot;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ManageViewModel manageViewModel = new ViewModelProvider(this).get(ManageViewModel.class);

        loadPillsCsv();
        binding = FragmentManageBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ImageView backButton = binding.manageBack;
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_main);
                navController.navigateUp();
            }
        });

        pill1Slot = binding.pillSlot1;
        pill2Slot = binding.pillSlot2;
        pill3Slot = binding.pillSlot3;
        pill4Slot = binding.pillSlot4;

        pill1Card = binding.managePill1Card;
        pill1Card.setOnClickListener(this);
        pill2Card = binding.managePill2Card;
        pill2Card.setOnClickListener(this);
        pill3Card = binding.managePill3Card;
        pill3Card.setOnClickListener(this);
        pill4Card = binding.managePill4Card;
        pill4Card.setOnClickListener(this);

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

        pill1Edit = binding.pill1Edit;
        pill1Edit.setOnClickListener(this);

        pill2Edit = binding.pill2Edit;
        pill2Edit.setOnClickListener(this);

        pill3Edit = binding.pill3Edit;
        pill3Edit.setOnClickListener(this);

        pill4Edit = binding.pill4Edit;
        pill4Edit.setOnClickListener(this);

        drawPillNames();


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

    private void drawPillNames() {
        List<Pill> pillList = ((MainActivity) getActivity()).pillList;

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
    }


    private void loadPillsCsv() {
        try {
            Reader targetReader = new InputStreamReader(getActivity().getAssets().open("pills.csv"));
            CSVReader reader = new CSVReader(targetReader);
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                //System.out.println(nextLine[0] + nextLine[1] + "etc...");
                pillsCompleteList.add(nextLine[0]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {

        if (v == pill1Edit) {
            showDialogForPillEdit(((MainActivity) getActivity()).pillList.get(0));
        } else if (v == pill2Edit) {
            showDialogForPillEdit(((MainActivity) getActivity()).pillList.get(1));
        } else if (v == pill3Edit) {
            showDialogForPillEdit(((MainActivity) getActivity()).pillList.get(2));
        } else if (v == pill4Edit) {
            showDialogForPillEdit(((MainActivity) getActivity()).pillList.get(3));
        }

        else if(v == pill1Card) {
            flashPillSlot(pill1Slot);
        }
        else if(v == pill2Card) {
            flashPillSlot(pill2Slot);
        }
        else if(v == pill3Card) {
            flashPillSlot(pill3Slot);
        }
        else if(v == pill4Card) {
            flashPillSlot(pill4Slot);
        }

    }

    private void flashPillSlot(View whichView) {
        // Flash Effect using Alpha Animation
        ObjectAnimator flash = ObjectAnimator.ofFloat(whichView, "alpha", 1f, 0f, 1f);
        flash.setDuration(500); // 500ms for one cycle
        flash.setRepeatCount(3); // Infinite flashing
        flash.start();

    }

    private void showDialogForPillEdit(Pill thePill) {
        Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.edit_dialog);
        dialog.setCancelable(true);

        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        if (dialog.getWindow() != null) {
            dialog.getWindow().setLayout((int)(width*.95), (int)(height*.95));
            //dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        // Back button
        ImageView backButton = dialog.findViewById(R.id.edit_back);
        backButton.setOnClickListener(view -> dialog.dismiss());

        // Save button
        TextView saveButton = dialog.findViewById(R.id.edit_update_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG", "TODO save pill info: ");
                thePill.pillName = pillNameEditText.getText().toString();
                drawPillNames();
                dialog.dismiss();
            }
        });

        // ---- Setup UI ----

        pillNameEditText = dialog.findViewById(R.id.pill_name_autocomplete);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, pillsCompleteList);
        Log.d("TAG", "adapter: " + adapter);
        Log.d("TAG", "pillNameEditText: " + pillNameEditText);
        pillNameEditText.setAdapter(adapter);
        pillNameEditText.setText(thePill.pillName);

        // Pills Amount
        spinner = dialog.findViewById(R.id.amount_spinner);
        ArrayAdapter<CharSequence> adapter0 = ArrayAdapter.createFromResource(
                getActivity(),
                R.array.pill_amounts,
                android.R.layout.simple_spinner_item
        );
        adapter0.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter0);

        // Days Amount
        ArrayList<String> pillDays = new ArrayList<>();
        for(int a=1;a < 35;a++) {
            pillDays.add(a + " days");
        }
        Spinner days_spinner = dialog.findViewById(R.id.duration_spinner);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, pillDays);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        days_spinner.setAdapter(adapter1);

        // schedule hour
        ArrayList<String> hourList = new ArrayList<>();
        for(int a=0;a < 24;a++) {
            String timeString = "00:00";
            String am_pm = "AM";
            if(a > 11) {
                am_pm = "PM";
            }

            if(a == 0) {
                timeString = "12:00";
                am_pm = "AM";
            }
            else if(a == 12) {
                timeString = "12:00";
                am_pm = "PM";
            }
            else if(a < 10) {
                timeString = "0"+a+":00";
            }  else if(a > 12) {
                // 13 - 23
                timeString = "0" + (a - 12) + ":00";
                if(a > 21) {
                    timeString = (a - 12) + ":00";
                }
            } else {
                // 11, 12
                timeString = a+":00";
            }

            timeString = timeString + " " + am_pm;
            hourList.add(timeString);
        }
        Spinner hour_spinner = dialog.findViewById(R.id.time_spinner);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, hourList);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hour_spinner.setAdapter(adapter2);


        dialog.show();
    }
}