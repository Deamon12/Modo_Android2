package com.chill.modoapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {

    static String TAG = "MainAct";

    private CardView cardDailyPlan;
    private CardView cardPill1;
    private CardView cardPill2;
    private CardView cardPill3;
    private CardView cardPill4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        cardDailyPlan = findViewById(R.id.plan_card);
        cardDailyPlan.setOnClickListener(this);
        cardDailyPlan.setOnLongClickListener(this);

        cardPill1 = findViewById(R.id.pill1_card);
        cardPill1.setOnClickListener(this);
        cardPill1.setOnLongClickListener(this);

        cardPill2 = findViewById(R.id.pill2_card);
        cardPill2.setOnClickListener(this);
        cardPill2.setOnLongClickListener(this);

        cardPill3 = findViewById(R.id.pill3_card);
        cardPill3.setOnClickListener(this);
        cardPill3.setOnLongClickListener(this);

        cardPill4 = findViewById(R.id.pill4_card);
        cardPill4.setOnClickListener(this);
        cardPill4.setOnLongClickListener(this);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick: ");

        if(v == cardDailyPlan) {
            Toast.makeText(this, "Daily clicked", Toast.LENGTH_SHORT).show();
        }
        else if(v == cardPill1) {
            Toast.makeText(this, "Pill 1 clicked", Toast.LENGTH_SHORT).show();
        }
        else if(v == cardPill2) {
            Toast.makeText(this, "Pill 2 clicked", Toast.LENGTH_SHORT).show();
        }
        else if(v == cardPill3) {
            Toast.makeText(this, "Pill 3 clicked", Toast.LENGTH_SHORT).show();
        }
        else if(v == cardPill4) {
            Toast.makeText(this, "Pill 4 clicked", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if(v == cardDailyPlan) {
            Toast.makeText(this, "Daily longed", Toast.LENGTH_SHORT).show();
        }
        else if(v == cardPill1) {
            Toast.makeText(this, "Pill 1 longed", Toast.LENGTH_SHORT).show();
        }
        else if(v == cardPill2) {
            Toast.makeText(this, "Pill 2 longed", Toast.LENGTH_SHORT).show();
        }
        else if(v == cardPill3) {
            Toast.makeText(this, "Pill 3 longed", Toast.LENGTH_SHORT).show();
        }
        else if(v == cardPill4) {
            Toast.makeText(this, "Pill 4 longed", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}